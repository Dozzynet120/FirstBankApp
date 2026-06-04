package com.firstbank.app.presentation.screens.data

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun DataScreen(
    onNavigateBack: () -> Unit
) {
    var phoneNumber by remember { mutableStateOf("") }
    var selectedPlan by remember { mutableStateOf<DataPlan?>(null) }
    var selectedNetwork by remember { mutableStateOf("MTN") }
    var showPinDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val networks = listOf("MTN", "Airtel", "Glo", "9mobile")
    val dataPlans = listOf(
        DataPlan("50MB", "₦100", "1 Day"),
        DataPlan("150MB", "₦200", "1 Day"),
        DataPlan("350MB", "₦300", "7 Days"),
        DataPlan("1GB", "₦500", "7 Days"),
        DataPlan("2GB", "₦1000", "30 Days"),
        DataPlan("3GB", "₦1500", "30 Days"),
        DataPlan("5GB", "₦2500", "30 Days"),
        DataPlan("10GB", "₦5000", "30 Days")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buy Data", fontWeight = FontWeight.Bold) },
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
                .verticalScroll(rememberScrollState())
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

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Select Data Plan",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = FirstBankDeepBlue
            )
            Spacer(modifier = Modifier.height(8.dp))

            dataPlans.forEach { plan ->
                DataPlanCard(
                    plan = plan,
                    isSelected = selectedPlan == plan,
                    onClick = { selectedPlan = plan }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            val currentPlan = selectedPlan
            Button(
                onClick = { showPinDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = FirstBankGold
                ),
                enabled = currentPlan != null
            ) {
                Text(
                    text = if (currentPlan != null) "Buy ${currentPlan.data}" else "Select a Plan",
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
            title = "Data Purchase Successful",
            message = "Your ${selectedPlan?.data ?: ""} data plan has been purchased successfully.",
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
            .background(
                if (isSelected) FirstBankDeepBlue else Color.White,
                RoundedCornerShape(12.dp)
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
fun DataPlanCard(
    plan: DataPlan,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) FirstBankDeepBlue else Color.White
        ),
        border = if (isSelected) null else BorderStroke(
            1.dp,
            Color.LightGray
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = plan.data,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color.White else FirstBankDeepBlue
                )
                Text(
                    text = plan.validity,
                    fontSize = 12.sp,
                    color = if (isSelected) Color.White.copy(alpha = 0.7f) else Color.Gray
                )
            }
            Text(
                text = plan.price,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) FirstBankGold else FirstBankDeepBlue
            )
        }
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

data class DataPlan(
    val data: String,
    val price: String,
    val validity: String
)