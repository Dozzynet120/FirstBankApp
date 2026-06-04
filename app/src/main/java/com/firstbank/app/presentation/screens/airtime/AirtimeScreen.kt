package com.firstbank.app.presentation.screens.airtime

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.firstbank.app.presentation.screens.home.FirstBankDeepBlue
import com.firstbank.app.presentation.screens.home.FirstBankGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirtimeScreen(
    onNavigateBack: () -> Unit
) {
    var phoneNumber by remember { mutableStateOf("") }
    var selectedAmount by remember { mutableStateOf("") }
    var selectedNetwork by remember { mutableStateOf("MTN") }
    var showPinDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val networks = listOf("MTN", "Airtel", "Glo", "9mobile")
    val quickAmounts = listOf("₦100", "₦200", "₦500", "₦1000", "₦2000", "₦5000")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buy Airtime", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = FirstBankDeepBlue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Select Network",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = FirstBankDeepBlue
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                networks.forEach { network ->
                    NetworkChip(
                        name = network,
                        isSelected = selectedNetwork == network,
                        onClick = { selectedNetwork = network }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = {
                    if (it.length <= 11 && it.all { c -> c.isDigit() }) phoneNumber = it
                },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                prefix = { Text("+234 ") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Select Amount",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = FirstBankDeepBlue
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                quickAmounts.take(3).forEach { amount ->
                    AmountChip(
                        amount = amount,
                        isSelected = selectedAmount == amount,
                        onClick = { selectedAmount = amount }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                quickAmounts.drop(3).forEach { amount ->
                    AmountChip(
                        amount = amount,
                        isSelected = selectedAmount == amount,
                        onClick = { selectedAmount = amount }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = if (selectedAmount.startsWith("₦")) selectedAmount.removePrefix("₦") else selectedAmount,
                onValueChange = { selectedAmount = it },
                label = { Text("Or enter custom amount") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                prefix = { Text("₦") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { showPinDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = FirstBankGold
                )
            ) {
                Text(
                    text = "Buy Airtime",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = FirstBankDeepBlue
                )
            }
        }
    }

    if (showPinDialog) {
        var pin by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = {
                pin = ""
                showPinDialog = false
            },
            title = { Text("Enter PIN") },
            text = {
                OutlinedTextField(
                    value = pin,
                    onValueChange = {
                        if (it.length <= 4 && it.all { c -> c.isDigit() }) pin = it
                    },
                    label = { Text("4-digit PIN") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        pin = ""
                        showPinDialog = false
                        showSuccessDialog = true
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        pin = ""
                        showPinDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showSuccessDialog) {
        SuccessDialog(
            title = "Airtime Purchase Successful",
            message = "Your ₦${selectedAmount.removePrefix("₦")} airtime has been purchased successfully for $selectedNetwork.",
            onDismiss = {
                showSuccessDialog = false
                onNavigateBack()
            }
        )
    }
}

@Composable
fun NetworkChip(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isSelected) FirstBankDeepBlue else Color.White
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

@Composable
fun AmountChip(
    amount: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isSelected) FirstBankGold else Color.White
            )
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = amount,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) FirstBankDeepBlue else Color.Black
        )
    }
}

@Composable
fun SuccessDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(64.dp)
            )
        },
        title = {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Text(
                text = message,
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = FirstBankGold
                )
            ) {
                Text(
                    text = "Done",
                    color = FirstBankDeepBlue,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}