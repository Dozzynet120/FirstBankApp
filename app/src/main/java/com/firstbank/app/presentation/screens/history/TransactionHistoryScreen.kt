package com.firstbank.app.presentation.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.firstbank.app.presentation.screens.home.FirstBankDeepBlue
import com.firstbank.app.presentation.screens.home.FirstBankGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHistoryScreen(
    onNavigateBack: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Credit", "Debit")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transaction History", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
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
            // Filter chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filters.forEach { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = FirstBankDeepBlue,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White,
                            labelColor = Color.Black
                        )
                    )
                }
            }

            // Summary card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = FirstBankDeepBlue)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Total In",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                        Text(
                            text = "+₦505,000",
                            color = Color(0xFF4CAF50),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Total Out",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                        Text(
                            text = "-₦42,550",
                            color = Color(0xFFFF5252),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Transaction list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(sampleHistoryTransactions) { transaction ->
                    HistoryTransactionItem(transaction = transaction)
                }
            }
        }
    }
}

@Composable
fun HistoryTransactionItem(transaction: HistoryTransaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(transaction.iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = transaction.icon,
                    contentDescription = null,
                    tint = transaction.iconTint,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = transaction.subtitle,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
                Text(
                    text = transaction.date,
                    fontSize = 10.sp,
                    color = Color.LightGray
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = transaction.amount,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (transaction.isCredit) Color(0xFF2E7D32) else Color.Black
                )
                Text(
                    text = transaction.status,
                    fontSize = 10.sp,
                    color = if (transaction.status == "Successful") Color(0xFF4CAF50) else Color.Gray
                )
            }
        }
    }
}

data class HistoryTransaction(
    val title: String,
    val subtitle: String,
    val amount: String,
    val date: String,
    val status: String,
    val isCredit: Boolean,
    val icon: ImageVector,
    val iconBg: Color,
    val iconTint: Color
)

val sampleHistoryTransactions = listOf(
    HistoryTransaction(
        title = "MTN Airtime",
        subtitle = "Airtime Purchase · 08031234567",
        amount = "-₦5,000",
        date = "Today, 10:23 AM",
        status = "Successful",
        isCredit = false,
        icon = Icons.Default.Smartphone,
        iconBg = Color(0xFFFFF3E0),
        iconTint = Color(0xFFEF6C00)
    ),
    HistoryTransaction(
        title = "SMS Alert Charge",
        subtitle = "Bank Charges",
        amount = "-₦50",
        date = "May 20, 02:14 PM",
        status = "Successful",
        isCredit = false,
        icon = Icons.Default.Message,
        iconBg = Color(0xFFE3F2FD),
        iconTint = Color(0xFF1565C0)
    ),
    HistoryTransaction(
        title = "Salary Credit",
        subtitle = "From: ABC Company Ltd",
        amount = "+₦500,000",
        date = "May 19, 09:00 AM",
        status = "Successful",
        isCredit = true,
        icon = Icons.Default.AccountBalance,
        iconBg = Color(0xFFE8F5E9),
        iconTint = Color(0xFF2E7D32)
    ),
    HistoryTransaction(
        title = "Adebola Transfer",
        subtitle = "To: 3045832435 · First Bank",
        amount = "-₦25,000",
        date = "May 18, 04:30 PM",
        status = "Successful",
        isCredit = false,
        icon = Icons.Default.Send,
        iconBg = Color(0xFFFCE4EC),
        iconTint = Color(0xFFC2185B)
    ),
    HistoryTransaction(
        title = "Electricity Bill",
        subtitle = "IKEDC · Meter: 45238901",
        amount = "-₦12,500",
        date = "May 15, 11:00 AM",
        status = "Successful",
        isCredit = false,
        icon = Icons.Default.ElectricBolt,
        iconBg = Color(0xFFFFF8E1),
        iconTint = Color(0xFFF9A825)
    ),
    HistoryTransaction(
        title = "Netflix Subscription",
        subtitle = "Auto-renewal",
        amount = "-₦4,400",
        date = "May 14, 03:22 AM",
        status = "Successful",
        isCredit = false,
        icon = Icons.Default.Tv,
        iconBg = Color(0xFFF3E5F5),
        iconTint = Color(0xFF7B1FA2)
    ),
    HistoryTransaction(
        title = "Transfer from John",
        subtitle = "From: 2087654321 · GTBank",
        amount = "+₦50,000",
        date = "May 12, 01:45 PM",
        status = "Successful",
        isCredit = true,
        icon = Icons.Default.ArrowDownward,
        iconBg = Color(0xFFE8F5E9),
        iconTint = Color(0xFF2E7D32)
    ),
    HistoryTransaction(
        title = "Uber Ride",
        subtitle = "Trip to Lekki Phase 1",
        amount = "-₦3,500",
        date = "May 10, 07:30 PM",
        status = "Successful",
        isCredit = false,
        icon = Icons.Default.LocalTaxi,
        iconBg = Color(0xFFFFF3E0),
        iconTint = Color(0xFFEF6C00)
    )
)