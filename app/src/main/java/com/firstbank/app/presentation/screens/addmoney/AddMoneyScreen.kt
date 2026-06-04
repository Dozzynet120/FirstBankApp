package com.firstbank.app.presentation.screens.addmoney

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.firstbank.app.presentation.screens.home.FirstBankDeepBlue
import com.firstbank.app.presentation.screens.home.FirstBankGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMoneyScreen(
    onNavigateBack: () -> Unit,
    onNavigateToBankTransfer: () -> Unit = {},
    onNavigateToCardFunding: () -> Unit = {},
    onNavigateToUSSD: () -> Unit = {},
    onNavigateToQR: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Money", fontWeight = FontWeight.Bold) },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Account Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = FirstBankDeepBlue)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Account Number",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "3045832435",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = { /* Copy to clipboard */ }) {
                            Icon(
                                imageVector = Icons.Default.AccountBalance,
                                contentDescription = "Copy",
                                tint = FirstBankGold,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Text(
                        text = "David Morrison · First Bank",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Choose Method",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = FirstBankDeepBlue
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Bank Transfer
            AddMoneyMethodCard(
                icon = Icons.Default.AccountBalance,
                title = "Bank Transfer",
                subtitle = "Transfer from another bank",
                onClick = onNavigateToBankTransfer
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Card Funding
            AddMoneyMethodCard(
                icon = Icons.Default.CreditCard,
                title = "Fund with Card",
                subtitle = "Use debit/credit card",
                onClick = onNavigateToCardFunding
            )

            Spacer(modifier = Modifier.height(8.dp))

            // USSD
            AddMoneyMethodCard(
                icon = Icons.Default.PhoneAndroid,
                title = "USSD",
                subtitle = "*894*Amount*Account#",
                onClick = onNavigateToUSSD
            )

            Spacer(modifier = Modifier.height(8.dp))

            // QR Code
            AddMoneyMethodCard(
                icon = Icons.Default.QrCode,
                title = "QR Code",
                subtitle = "Scan to deposit",
                onClick = onNavigateToQR
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Recent Deposits
            Text(
                text = "Recent Deposits",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = FirstBankDeepBlue
            )
            Spacer(modifier = Modifier.height(12.dp))

            RecentDepositItem(
                title = "From GTBank",
                amount = "+₦50,000",
                date = "Today, 09:15 AM",
                isCredit = true
            )
            RecentDepositItem(
                title = "Card Funding",
                amount = "+₦100,000",
                date = "Yesterday, 02:30 PM",
                isCredit = true
            )
        }
    }
}

@Composable
fun AddMoneyMethodCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
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
                    .background(FirstBankGold.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = FirstBankDeepBlue,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Open",
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun RecentDepositItem(
    title: String,
    amount: String,
    date: String,
    isCredit: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(
                    if (isCredit) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                    RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountBalance,
                contentDescription = null,
                tint = if (isCredit) Color(0xFF2E7D32) else Color(0xFFB3261E),
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = date,
                fontSize = 11.sp,
                color = Color.Gray
            )
        }
        Text(
            text = amount,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = if (isCredit) Color(0xFF2E7D32) else Color.Black
        )
    }
}