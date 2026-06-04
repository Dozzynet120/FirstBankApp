package com.firstbank.app.presentation.screens.bills

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
fun BillScreen(
    onNavigateBack: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf("All") }
    var showPinDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var selectedBill by remember { mutableStateOf<BillItem?>(null) }

    val categories = listOf("All", "Electricity", "TV", "Internet", "Water", "Tax")
    val bills = listOf(
        BillItem("IKEDC", "Electricity", "Postpaid", Icons.Default.ElectricBolt, Color(0xFFFF9800)),
        BillItem("EKEDC", "Electricity", "Prepaid", Icons.Default.ElectricBolt, Color(0xFFFF9800)),
        BillItem("DSTV", "TV", "Premium", Icons.Default.Tv, Color(0xFF2196F3)),
        BillItem("GOTV", "TV", "Max", Icons.Default.Tv, Color(0xFF2196F3)),
        BillItem("Startimes", "TV", "Classic", Icons.Default.Tv, Color(0xFF2196F3)),
        BillItem("Smile", "Internet", "Unlimited", Icons.Default.Wifi, Color(0xFF4CAF50)),
        BillItem("Spectranet", "Internet", "Lite", Icons.Default.Wifi, Color(0xFF4CAF50)),
        BillItem("Lagos Water", "Water", "Monthly", Icons.Default.WaterDrop, Color(0xFF03A9F4))
    )

    val filteredBills = if (selectedCategory == "All") bills
    else bills.filter { it.category == selectedCategory }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pay Bills", fontWeight = FontWeight.Bold) },
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
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = FirstBankDeepBlue,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White
                        )
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(filteredBills) { bill ->
                    BillCard(
                        bill = bill,
                        onClick = {
                            selectedBill = bill
                            showPinDialog = true
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    if (showPinDialog && selectedBill != null) {
        var pin by remember { mutableStateOf("") }
        var customerId by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = {
                pin = ""
                customerId = ""
                showPinDialog = false
                selectedBill = null
            },
            title = { Text("Pay ${selectedBill!!.name}") },
            text = {
                Column {
                    OutlinedTextField(
                        value = customerId,
                        onValueChange = { customerId = it },
                        label = { Text("Customer ID / Meter Number") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
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
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        pin = ""
                        customerId = ""
                        showPinDialog = false
                        showSuccessDialog = true
                    }
                ) {
                    Text("Pay")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        pin = ""
                        customerId = ""
                        showPinDialog = false
                        selectedBill = null
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showSuccessDialog) {
        SuccessDialog(
            title = "Payment Successful",
            message = "Your ${selectedBill?.name ?: ""} bill payment was successful.",
            onDismiss = {
                showSuccessDialog = false
                selectedBill = null
            }
        )
    }
}

@Composable
fun BillCard(
    bill: BillItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(bill.iconBg.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = bill.icon,
                    contentDescription = null,
                    tint = bill.iconBg,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = bill.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${bill.category} · ${bill.plan}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Select",
                tint = Color.Gray
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

data class BillItem(
    val name: String,
    val category: String,
    val plan: String,
    val icon: ImageVector,
    val iconBg: Color
)