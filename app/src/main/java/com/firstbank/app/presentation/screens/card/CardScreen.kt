package com.firstbank.app.presentation.screens.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.firstbank.app.presentation.screens.home.FirstBankDeepBlue
import com.firstbank.app.presentation.screens.home.FirstBankGold

// Define FirstBankLightBlue locally if not available in home package
val FirstBankLightBlue = Color(0xFF1565C0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardScreen(
    onNavigateBack: () -> Unit
) {
    var cardNumberVisible by remember { mutableStateOf(false) }
    var showPinDialog by remember { mutableStateOf(false) }
    var selectedAction by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Cards", fontWeight = FontWeight.Bold) },
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
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(FirstBankDeepBlue, FirstBankLightBlue)
                            )
                        )
                        .padding(24.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "VISA",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = FirstBankGold
                            )
                            IconButton(onClick = { cardNumberVisible = !cardNumberVisible }) {
                                Icon(
                                    imageVector = if (cardNumberVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Toggle visibility",
                                    tint = Color.White
                                )
                            }
                        }

                        Text(
                            text = if (cardNumberVisible) "5399 1234 5678 9012" else "5399 **** **** 9012",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = 2.sp
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "CARD HOLDER",
                                    fontSize = 10.sp,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                                Text(
                                    text = "DAVID MORRISON",
                                    fontSize = 14.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Column {
                                Text(
                                    text = "EXPIRES",
                                    fontSize = 10.sp,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                                Text(
                                    text = "12/28",
                                    fontSize = 14.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Column {
                                Text(
                                    text = "CVV",
                                    fontSize = 10.sp,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                                Text(
                                    text = if (cardNumberVisible) "123" else "***",
                                    fontSize = 14.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Card Actions",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = FirstBankDeepBlue
            )
            Spacer(modifier = Modifier.height(12.dp))

            CardActionItem(
                icon = Icons.Default.Lock,
                title = "Block Card",
                subtitle = "Temporarily block your card",
                onClick = {
                    selectedAction = "Block"
                    showPinDialog = true
                }
            )
            CardActionItem(
                icon = Icons.Default.LockOpen,
                title = "Unblock Card",
                subtitle = "Unblock your card",
                onClick = {
                    selectedAction = "Unblock"
                    showPinDialog = true
                }
            )
            CardActionItem(
                icon = Icons.Default.Pin,
                title = "Change PIN",
                subtitle = "Set a new card PIN",
                onClick = {
                    selectedAction = "Change PIN"
                    showPinDialog = true
                }
            )
            CardActionItem(
                icon = Icons.Default.CreditCard,
                title = "Request New Card",
                subtitle = "Order a replacement card",
                onClick = {
                    selectedAction = "New Card"
                    showPinDialog = true
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Card Limits",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = FirstBankDeepBlue
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LimitRow("Daily Withdrawal", "₦500,000", "₦150,000 used")
                    LimitRow("Daily Transfer", "₦5,000,000", "₦2,300,000 used")
                    LimitRow("Online Purchase", "₦1,000,000", "₦450,000 used")
                }
            }
        }
    }

    if (showPinDialog) {
        var pin by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = {
                pin = ""
                showPinDialog = false
                selectedAction = ""
            },
            title = { Text("$selectedAction Card") },
            text = {
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
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        pin = ""
                        showPinDialog = false
                        selectedAction = ""
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
                        selectedAction = ""
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun CardActionItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
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
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = FirstBankDeepBlue,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitle,
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
fun LimitRow(label: String, limit: String, used: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = label,
                fontSize = 13.sp,
                color = Color.Gray
            )
            Text(
                text = used,
                fontSize = 11.sp,
                color = Color.Gray.copy(alpha = 0.7f)
            )
        }
        Text(
            text = limit,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = FirstBankDeepBlue
        )
    }
}