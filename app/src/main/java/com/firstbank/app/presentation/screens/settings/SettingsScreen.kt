package com.firstbank.app.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSecurity: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToAppearance: () -> Unit = {}
) {
    var biometricEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }
    var transactionAlerts by remember { mutableStateOf(true) }
    var emailStatements by remember { mutableStateOf(false) }
    var quickLogin by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            // Account Tier Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = FirstBankDeepBlue)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Account Tier",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 12.sp
                            )
                            Text(
                                text = "Tier 3 - Premium",
                                color = FirstBankGold,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Premium",
                            tint = FirstBankGold,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Daily Limit: ₦5,000,000 · Single Transfer: ₦5,000,000",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { /* Upgrade flow */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = FirstBankGold
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Upgrade to Tier 4",
                            color = FirstBankDeepBlue,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Security Section
            SettingsSectionHeader("Security")
            Spacer(modifier = Modifier.height(8.dp))

            SettingsToggleItem(
                icon = Icons.Default.Fingerprint,
                title = "Biometric Login",
                subtitle = "Use fingerprint or face ID",
                checked = biometricEnabled,
                onCheckedChange = { biometricEnabled = it }
            )

            SettingsToggleItem(
                icon = Icons.Default.Lock,
                title = "Quick Login",
                subtitle = "Stay logged in for 5 minutes",
                checked = quickLogin,
                onCheckedChange = { quickLogin = it }
            )

            SettingsNavigationItem(
                icon = Icons.Default.Password,
                title = "Change Password",
                subtitle = "Update your login password",
                onClick = onNavigateToSecurity
            )

            SettingsNavigationItem(
                icon = Icons.Default.Pin,
                title = "Change Transaction PIN",
                subtitle = "Update your 4-digit PIN",
                onClick = {}
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Notifications Section
            SettingsSectionHeader("Notifications")
            Spacer(modifier = Modifier.height(8.dp))

            SettingsToggleItem(
                icon = Icons.Default.Notifications,
                title = "Transaction Alerts",
                subtitle = "SMS and push notifications",
                checked = transactionAlerts,
                onCheckedChange = { transactionAlerts = it }
            )

            SettingsToggleItem(
                icon = Icons.Default.Email,
                title = "Email Statements",
                subtitle = "Monthly account statements",
                checked = emailStatements,
                onCheckedChange = { emailStatements = it }
            )

            SettingsNavigationItem(
                icon = Icons.Default.Tune,
                title = "Notification Preferences",
                subtitle = "Customize alert settings",
                onClick = onNavigateToNotifications
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Appearance Section
            SettingsSectionHeader("Appearance")
            Spacer(modifier = Modifier.height(8.dp))

            SettingsToggleItem(
                icon = Icons.Default.DarkMode,
                title = "Dark Mode",
                subtitle = "Switch to dark theme",
                checked = darkModeEnabled,
                onCheckedChange = { darkModeEnabled = it }
            )

            SettingsNavigationItem(
                icon = Icons.Default.Language,
                title = "Language",
                subtitle = "English (UK)",
                onClick = {}
            )

            Spacer(modifier = Modifier.height(24.dp))

            // General Section
            SettingsSectionHeader("General")
            Spacer(modifier = Modifier.height(8.dp))

            SettingsNavigationItem(
                icon = Icons.Default.HelpOutline,
                title = "Help & Support",
                subtitle = "FAQs and contact support",
                onClick = {}
            )

            SettingsNavigationItem(
                icon = Icons.Default.Info,
                title = "About",
                subtitle = "Version 1.0.0 · Build 2024.06",
                onClick = {}
            )

            SettingsNavigationItem(
                icon = Icons.Default.Description,
                title = "Terms & Privacy",
                subtitle = "Legal information",
                onClick = {}
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Reset Button
            OutlinedButton(
                onClick = { /* Reset settings */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFB3261E)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.RestartAlt,
                    contentDescription = "Reset"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Reset to Defaults")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Delete Account
            TextButton(
                onClick = { /* Delete account flow */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color(0xFFB3261E)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.DeleteForever,
                    contentDescription = "Delete"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Delete Account", fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = FirstBankDeepBlue,
        modifier = Modifier.padding(horizontal = 4.dp)
    )
}

@Composable
fun SettingsToggleItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
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
            Column(
                modifier = Modifier.weight(1f)
            ) {
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
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = FirstBankGold,
                    checkedTrackColor = FirstBankDeepBlue.copy(alpha = 0.5f)
                )
            )
        }
    }
}

@Composable
fun SettingsNavigationItem(
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
            Column(
                modifier = Modifier.weight(1f)
            ) {
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