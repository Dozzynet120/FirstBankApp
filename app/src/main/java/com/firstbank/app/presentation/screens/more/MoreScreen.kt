package com.firstbank.app.presentation.screens.more

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.HistoryToggleOff
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
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
fun MoreScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToSupport: () -> Unit,
    onNavigateToBeneficiaries: () -> Unit,
    onNavigateToHistory: () -> Unit = {},
    onNavigateToCards: () -> Unit = {},
    onNavigateToLoans: () -> Unit = {},
    onNavigateToScan: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("More", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = FirstBankDeepBlue,
                    titleContentColor = Color.White
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
            // Quick Actions Grid
            Text(
                text = "Quick Actions",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = FirstBankDeepBlue
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                QuickActionTile("Profile", Icons.Default.Person, onNavigateToProfile)
                QuickActionTile("Settings", Icons.Default.Settings, onNavigateToSettings)
                QuickActionTile("Support", Icons.AutoMirrored.Filled.HelpOutline, onNavigateToSupport)
                QuickActionTile("Beneficiaries", Icons.Default.People, onNavigateToBeneficiaries)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // All Services
            Text(
                text = "All Services",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = FirstBankDeepBlue
            )
            Spacer(modifier = Modifier.height(12.dp))

            MoreMenuItem(
                title = "Account Statement",
                icon = Icons.Default.Description,
                subtitle = "View and download statements",
                onClick = {}
            )
            MoreMenuItem(
                title = "Transaction History",
                icon = Icons.Default.History,
                subtitle = "View all transactions",
                onClick = onNavigateToHistory
            )
            MoreMenuItem(
                title = "Scheduled Payments",
                icon = Icons.Default.Schedule,
                subtitle = "Manage recurring payments",
                onClick = {}
            )
            MoreMenuItem(
                title = "Standing Orders",
                icon = Icons.Default.Repeat,
                subtitle = "Auto-transfer setup",
                onClick = {}
            )
            MoreMenuItem(
                title = "Cheque Services",
                icon = Icons.Default.Book,
                subtitle = "Request and manage cheques",
                onClick = {}
            )
            MoreMenuItem(
                title = "Foreign Exchange",
                icon = Icons.Default.CurrencyExchange,
                subtitle = "FX rates and transfers",
                onClick = {}
            )
            MoreMenuItem(
                title = "Investment",
                icon = Icons.Default.TrendingUp,
                subtitle = "Mutual funds and bonds",
                onClick = {}
            )
            MoreMenuItem(
                title = "Insurance",
                icon = Icons.Default.Shield,
                subtitle = "Life and asset insurance",
                onClick = {}
            )
            MoreMenuItem(
                title = "Tax Payment",
                icon = Icons.Default.AccountBalance,
                subtitle = "Pay taxes and levies",
                onClick = {}
            )
            MoreMenuItem(
                title = "Pension",
                icon = Icons.Default.Savings,
                subtitle = "Pension account management",
                onClick = {}
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Security Section
            Text(
                text = "Security",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = FirstBankDeepBlue
            )
            Spacer(modifier = Modifier.height(12.dp))
            MoreMenuItem(
                title = "Change Password",
                icon = Icons.Default.Lock,
                subtitle = "Update login password",
                onClick = {}
            )
            MoreMenuItem(
                title = "Change PIN",
                icon = Icons.Default.Pin,
                subtitle = "Update transaction PIN",
                onClick = {}
            )
            MoreMenuItem(
                title = "Biometric Login",
                icon = Icons.Default.Fingerprint,
                subtitle = "Face ID / Fingerprint",
                onClick = {}
            )
            MoreMenuItem(
                title = "Device Management",
                icon = Icons.Default.Devices,
                subtitle = "Manage linked devices",
                onClick = {}
            )
            MoreMenuItem(
                title = "Login History",
                icon = Icons.Default.HistoryToggleOff,
                subtitle = "View recent logins",
                onClick = {}
            )

            Spacer(modifier = Modifier.height(24.dp))

            // About
            Text(
                text = "About",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = FirstBankDeepBlue
            )
            Spacer(modifier = Modifier.height(12.dp))
            MoreMenuItem(
                title = "About FirstBank",
                icon = Icons.Default.Info,
                subtitle = "App version 1.0.0",
                onClick = {}
            )
            MoreMenuItem(
                title = "Terms & Conditions",
                icon = Icons.Default.Description,
                subtitle = "Legal terms",
                onClick = {}
            )
            MoreMenuItem(
                title = "Privacy Policy",
                icon = Icons.Default.PrivacyTip,
                subtitle = "Data privacy info",
                onClick = {}
            )
            MoreMenuItem(
                title = "Rate App",
                icon = Icons.Default.Star,
                subtitle = "Rate us on Play Store",
                onClick = {}
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Logout Button
            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFEBEE)
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Logout",
                    tint = Color(0xFFB3261E)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Logout",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFB3261E)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun QuickActionTile(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(FirstBankDeepBlue, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun MoreMenuItem(
    title: String,
    icon: ImageVector,
    subtitle: String,
    onClick: () -> Unit = {}
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
                contentDescription = "Open",
                tint = Color.Gray
            )
        }
    }
}