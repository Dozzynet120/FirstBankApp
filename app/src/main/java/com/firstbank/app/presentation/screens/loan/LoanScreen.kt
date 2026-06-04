package com.firstbank.app.presentation.screens.loan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.firstbank.app.presentation.screens.home.FirstBankDeepBlue
import com.firstbank.app.presentation.screens.home.FirstBankGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanScreen(
    onNavigateBack: () -> Unit
) {
    var selectedLoan by remember { mutableStateOf<LoanType?>(null) }
    var showApplyDialog by remember { mutableStateOf(false) }

    val loanTypes = listOf(
        LoanType("Personal Loan", "Up to ₦5M", "9.5%", "12-60 months", Icons.Default.Person, Color(0xFF2196F3)),
        LoanType("Business Loan", "Up to ₦50M", "12%", "6-36 months", Icons.Default.Business, Color(0xFF4CAF50)),
        LoanType("Salary Advance", "Up to ₦500K", "7.5%", "1-3 months", Icons.Default.AttachMoney, Color(0xFFFF9800)),
        LoanType("Asset Finance", "Up to ₦20M", "11%", "12-48 months", Icons.Default.DirectionsCar, Color(0xFF9C27B0)),
        LoanType("Mortgage", "Up to ₦100M", "15%", "120-240 months", Icons.Default.Home, Color(0xFF795548))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Loans", fontWeight = FontWeight.Bold) },
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
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = FirstBankDeepBlue)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Loan Eligibility",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                    Text(
                        text = "₦5,000,000",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Maximum loan amount available",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LinearProgressIndicator(
                        progress = { 0.75f },
                        modifier = Modifier.fillMaxWidth(),
                        color = FirstBankGold,
                        trackColor = Color.White.copy(alpha = 0.3f),
                        strokeCap = StrokeCap.Round
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Available Loans",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = FirstBankDeepBlue
            )
            Spacer(modifier = Modifier.height(12.dp))

            loanTypes.forEach { loan ->
                LoanCard(
                    loan = loan,
                    onClick = {
                        selectedLoan = loan
                        showApplyDialog = true
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    if (showApplyDialog && selectedLoan != null) {
        var amount by remember { mutableStateOf("") }
        var tenure by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = {
                amount = ""
                tenure = ""
                selectedLoan = null
                showApplyDialog = false
            },
            title = { Text("Apply for ${selectedLoan!!.name}") },
            text = {
                Column {
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        label = { Text("Amount (Max: ${selectedLoan!!.maxAmount})") },
                        prefix = { Text("₦") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tenure,
                        onValueChange = { tenure = it },
                        label = { Text("Tenure (${selectedLoan!!.tenure})") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Interest Rate: ${selectedLoan!!.rate} per annum",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        amount = ""
                        tenure = ""
                        selectedLoan = null
                        showApplyDialog = false
                    }
                ) {
                    Text("Apply")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        amount = ""
                        tenure = ""
                        selectedLoan = null
                        showApplyDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun LoanCard(
    loan: LoanType,
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
                    .background(loan.iconBg.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = loan.icon,
                    contentDescription = null,
                    tint = loan.iconBg,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = loan.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Max: ${loan.maxAmount} · ${loan.tenure}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = loan.rate,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = FirstBankGold
                )
                Text(
                    text = "per annum",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

data class LoanType(
    val name: String,
    val maxAmount: String,
    val rate: String,
    val tenure: String,
    val icon: ImageVector,
    val iconBg: Color
)