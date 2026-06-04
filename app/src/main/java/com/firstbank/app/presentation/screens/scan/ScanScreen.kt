package com.firstbank.app.presentation.screens.scan

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
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
fun ScanScreen(
    onNavigateBack: () -> Unit,
    onPaymentSuccess: () -> Unit = {}
) {
    var scanState by remember { mutableStateOf(ScanState.SCANNING) }
    var detectedCode by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    var showPinDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scan & Pay", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black.copy(alpha = 0.7f),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color.Black
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (scanState) {
                ScanState.SCANNING -> {
                    ScanningView(
                        onCodeDetected = { code ->
                            detectedCode = code
                            scanState = ScanState.DETECTED
                        }
                    )
                }
                ScanState.DETECTED -> {
                    CodeDetectedView(
                        code = detectedCode,
                        onConfirm = { scanState = ScanState.PAYMENT },
                        onRescan = {
                            detectedCode = ""
                            scanState = ScanState.SCANNING
                        }
                    )
                }
                ScanState.PAYMENT -> {
                    PaymentView(
                        code = detectedCode,
                        amount = amount,
                        onAmountChange = { amount = it },
                        onPay = { showPinDialog = true },
                        onCancel = {
                            amount = ""
                            scanState = ScanState.SCANNING
                        }
                    )
                }
                ScanState.SUCCESS -> {
                    SuccessView(onDone = onPaymentSuccess)
                }
            }

            if (scanState == ScanState.SCANNING) {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Align QR code within the frame",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(32.dp)
                    ) {
                        ScanControlButton(
                            icon = Icons.Default.FlashOn,
                            label = "Flash"
                        )
                        ScanControlButton(
                            icon = Icons.Default.PhotoLibrary,
                            label = "Gallery"
                        )
                        ScanControlButton(
                            icon = Icons.Default.Keyboard,
                            label = "Enter Code"
                        )
                    }
                }
            }
        }
    }

    if (showPinDialog) {
        AlertDialog(
            onDismissRequest = {
                pin = ""
                showPinDialog = false
            },
            title = { Text("Confirm Payment", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text(
                        text = "Amount: ₦$amount",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = FirstBankDeepBlue
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "To: Merchant #$detectedCode",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = pin,
                        onValueChange = {
                            if (it.length <= 4 && it.all { c -> c.isDigit() }) pin = it
                        },
                        label = { Text("Enter 4-digit PIN") },
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
                Button(
                    onClick = {
                        pin = ""
                        showPinDialog = false
                        scanState = ScanState.SUCCESS
                    },
                    enabled = pin.length == 4,
                    colors = ButtonDefaults.buttonColors(containerColor = FirstBankGold)
                ) {
                    Text("Pay", color = FirstBankDeepBlue)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    pin = ""
                    showPinDialog = false
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ScanningView(onCodeDetected: (String) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .border(
                        width = 2.dp,
                        color = FirstBankGold,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.TopStart)
                            .border(
                                width = 4.dp,
                                color = FirstBankGold,
                                shape = RoundedCornerShape(topStart = 16.dp)
                            )
                    )
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.TopEnd)
                            .border(
                                width = 4.dp,
                                color = FirstBankGold,
                                shape = RoundedCornerShape(topEnd = 16.dp)
                            )
                    )
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.BottomStart)
                            .border(
                                width = 4.dp,
                                color = FirstBankGold,
                                shape = RoundedCornerShape(bottomStart = 16.dp)
                            )
                    )
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.BottomEnd)
                            .border(
                                width = 4.dp,
                                color = FirstBankGold,
                                shape = RoundedCornerShape(bottomEnd = 16.dp)
                            )
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(FirstBankGold)
                        .align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { onCodeDetected("FB-MERCHANT-8842") },
                colors = ButtonDefaults.buttonColors(containerColor = FirstBankGold)
            ) {
                Text("Simulate QR Detection", color = FirstBankDeepBlue)
            }
        }
    }
}

@Composable
fun CodeDetectedView(
    code: String,
    onConfirm: () -> Unit,
    onRescan: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.QrCodeScanner,
                    contentDescription = null,
                    tint = FirstBankDeepBlue,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "QR Code Detected",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = FirstBankDeepBlue
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Merchant ID: $code",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onConfirm,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = FirstBankGold),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Proceed to Payment",
                        color = FirstBankDeepBlue,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = onRescan) {
                    Text("Scan Again")
                }
            }
        }
    }
}

@Composable
fun PaymentView(
    code: String,
    amount: String,
    onAmountChange: (String) -> Unit,
    onPay: () -> Unit,
    onCancel: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "Enter Amount",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = FirstBankDeepBlue
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Paying to Merchant: $code",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(16.dp))
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (amount.isEmpty()) "₦0.00" else "₦$amount",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = FirstBankDeepBlue
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = amount,
                    onValueChange = onAmountChange,
                    label = { Text("Amount") },
                    modifier = Modifier.fillMaxWidth(),
                    prefix = { Text("₦") },
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf("500", "1000", "2000", "5000").forEach { quickAmount ->
                        AssistChip(
                            onClick = { onAmountChange(quickAmount) },
                            label = { Text("₦$quickAmount") },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = FirstBankGold.copy(alpha = 0.2f)
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onPay,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    enabled = amount.isNotEmpty() && amount.toDoubleOrNull() != null,
                    colors = ButtonDefaults.buttonColors(containerColor = FirstBankGold),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Continue",
                        color = FirstBankDeepBlue,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    onClick = onCancel,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}

@Composable
fun SuccessView(onDone: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color(0xFF4CAF50), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Success",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Payment Successful!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Your transaction has been completed",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = onDone,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = FirstBankGold),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Done",
                        color = FirstBankDeepBlue,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun ScanControlButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = { },
            modifier = Modifier
                .size(56.dp)
                .background(Color.White.copy(alpha = 0.2f), CircleShape)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.White
        )
    }
}

enum class ScanState {
    SCANNING, DETECTED, PAYMENT, SUCCESS
}