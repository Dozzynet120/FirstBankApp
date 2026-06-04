package com.firstbank.app.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.firstbank.app.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// First Bank Colors
val FirstBankDeepBlue = Color(0xFF003B5C)
val FirstBankGold = Color(0xFFB4975A)
val FirstBankLightBlue = Color(0xFF1A5A7A)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToTransfer: () -> Unit,
    onNavigateToAirtime: () -> Unit,
    onNavigateToData: () -> Unit,
    onNavigateToBills: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToMore: () -> Unit,
    onNavigateToCards: () -> Unit,
    onNavigateToLoans: () -> Unit,
    onNavigateToQR: () -> Unit,
    onNavigateToScan: () -> Unit,
    onNavigateToProfile: () -> Unit = {},
    onNavigateToBeneficiaries: () -> Unit = {},
    onNavigateToSupport: () -> Unit = {},
    onNavigateToAddMoney: () -> Unit = {}
) {
    var balanceVisible by remember { mutableStateOf(true) }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            BottomNavBar(
                onNavigateToHome = {},
                onNavigateToBeneficiaries = onNavigateToBeneficiaries,
                onNavigateToScan = onNavigateToScan,
                onNavigateToSupport = onNavigateToSupport,
                onNavigateToProfile = onNavigateToProfile
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                BalanceCard(
                    balance = "₦30,700,000.00",
                    accountNumber = "3045832435",
                    bankName = "First Bank",
                    balanceVisible = balanceVisible,
                    onToggleVisibility = { balanceVisible = !balanceVisible },
                    onAddMoney = onNavigateToAddMoney,
                    onNavigateToProfile = onNavigateToProfile,
                    snackbarHostState = snackbarHostState
                )
            }

            item {
                QuickActionsRow(
                    onTransfer = onNavigateToTransfer,
                    onAirtime = onNavigateToAirtime,
                    onData = onNavigateToData,
                    onBills = onNavigateToBills
                )
            }

            item {
                MoreActionsGrid(
                    onCards = onNavigateToCards,
                    onQR = onNavigateToQR,
                    onLoans = onNavigateToLoans,
                    onMore = onNavigateToMore
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Transactions",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "See All",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = FirstBankGold,
                        modifier = Modifier.clickable { onNavigateToHistory() }
                    )
                }
            }

            items(sampleTransactions) { transaction ->
                TransactionItem(transaction = transaction)
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                BannerSliderSection()
            }
        }
    }
}

@Composable
fun BalanceCard(
    balance: String,
    accountNumber: String,
    bankName: String,
    balanceVisible: Boolean,
    onToggleVisibility: () -> Unit,
    onAddMoney: () -> Unit,
    onNavigateToProfile: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(FirstBankDeepBlue)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onNavigateToProfile() }
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(FirstBankGold),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "DM",
                        color = FirstBankDeepBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "David Morrison",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Business Name",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 11.sp
                    )
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Notification sent",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(FirstBankGold)
                        .clickable { onAddMoney() }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "+ Add Money",
                        color = FirstBankDeepBlue,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Total Balance",
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 12.sp
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (balanceVisible) balance else "₦****",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = if (balanceVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                contentDescription = "Toggle visibility",
                tint = Color.White.copy(alpha = 0.7f),
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onToggleVisibility() }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$accountNumber · $bankName",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = "Copy account",
                tint = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
fun QuickActionsRow(
    onTransfer: () -> Unit,
    onAirtime: () -> Unit,
    onData: () -> Unit,
    onBills: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        QuickActionItem(
            icon = Icons.AutoMirrored.Filled.Send,
            label = "Transfer",
            iconBg = Color(0xFFFFF3E0),
            iconTint = FirstBankGold,
            onClick = onTransfer
        )
        QuickActionItem(
            icon = Icons.Default.Flight,
            label = "Airtime",
            iconBg = Color(0xFFE3F2FD),
            iconTint = FirstBankLightBlue,
            onClick = onAirtime
        )
        QuickActionItem(
            icon = Icons.Default.Wifi,
            label = "Data",
            iconBg = Color(0xFFF3E5F5),
            iconTint = Color(0xFF7B1FA2),
            onClick = onData
        )
        QuickActionItem(
            icon = Icons.Default.Receipt,
            label = "Bills",
            iconBg = Color(0xFFE8F5E9),
            iconTint = Color(0xFF2E7D32),
            onClick = onBills
        )
    }
}

@Composable
fun QuickActionItem(
    icon: ImageVector,
    label: String,
    iconBg: Color,
    iconTint: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MoreActionsGrid(
    onCards: () -> Unit,
    onQR: () -> Unit,
    onLoans: () -> Unit,
    onMore: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        MoreActionItem(
            icon = Icons.Default.CreditCard,
            label = "Cards",
            iconBg = Color(0xFFFFF8E1),
            iconTint = Color(0xFFF9A825),
            onClick = onCards
        )
        MoreActionItem(
            icon = Icons.Default.QrCode,
            label = "QR Pay",
            iconBg = Color(0xFFE0F7FA),
            iconTint = Color(0xFF00838F),
            onClick = onQR
        )
        MoreActionItem(
            icon = Icons.Default.AccountBalance,
            label = "Loans",
            iconBg = Color(0xFFFCE4EC),
            iconTint = Color(0xFFC2185B),
            onClick = onLoans
        )
        MoreActionItem(
            icon = Icons.Default.MoreHoriz,
            label = "More",
            iconBg = Color(0xFFEEEEEE),
            iconTint = Color.Gray,
            onClick = onMore
        )
    }
}

@Composable
fun MoreActionItem(
    icon: ImageVector,
    label: String,
    iconBg: Color,
    iconTint: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { },
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
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Text(
                text = transaction.subtitle,
                fontSize = 11.sp,
                color = Color.Gray
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
                text = transaction.date,
                fontSize = 10.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun BannerSliderSection() {
    val banners = listOf(
        R.drawable.banner2,
        R.drawable.banner3,
        R.drawable.banner6,
        R.drawable.banner8
    )

    var currentPage by remember { mutableIntStateOf(0) }

    LaunchedEffect(currentPage) {
        delay(4000)
        currentPage = (currentPage + 1) % banners.size
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Promotions & Offers",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painterResource(id = banners[currentPage]),
                contentDescription = "Promo Banner ${currentPage + 1}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            banners.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == currentPage) FirstBankGold else Color.LightGray
                        )
                        .clickable { currentPage = index }
                )
                if (index < banners.lastIndex) {
                    Spacer(modifier = Modifier.width(6.dp))
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(
    onNavigateToHome: () -> Unit,
    onNavigateToBeneficiaries: () -> Unit,
    onNavigateToScan: () -> Unit,
    onNavigateToSupport: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, 0),
        BottomNavItem("Beneficiaries", Icons.Default.People, 1),
        BottomNavItem("Scan", Icons.Default.QrCodeScanner, 2),
        BottomNavItem("Support", Icons.AutoMirrored.Filled.HelpOutline, 3),
        BottomNavItem("Profile", Icons.Default.Person, 4)
    )

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = { Text(item.label, fontSize = 10.sp) },
                selected = selectedIndex == item.index,
                onClick = {
                    selectedIndex = item.index
                    when (item.index) {
                        0 -> onNavigateToHome()
                        1 -> onNavigateToBeneficiaries()
                        2 -> onNavigateToScan()
                        3 -> onNavigateToSupport()
                        4 -> onNavigateToProfile()
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = FirstBankDeepBlue,
                    selectedTextColor = FirstBankDeepBlue,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val index: Int
)

data class Transaction(
    val title: String,
    val subtitle: String,
    val amount: String,
    val date: String,
    val isCredit: Boolean,
    val icon: ImageVector,
    val iconBg: Color,
    val iconTint: Color
)

val sampleTransactions = listOf(
    Transaction(
        title = "MTN Airtime",
        subtitle = "Airtime Purchase",
        amount = "-₦5,000",
        date = "Today, 10:23 AM",
        isCredit = false,
        icon = Icons.Default.Smartphone,
        iconBg = Color(0xFFFFF3E0),
        iconTint = Color(0xFFEF6C00)
    ),
    Transaction(
        title = "SMS Alert Charge",
        subtitle = "Bank Charges",
        amount = "-₦50",
        date = "May 20, 02:14 PM",
        isCredit = false,
        icon = Icons.AutoMirrored.Filled.Message,
        iconBg = Color(0xFFE3F2FD),
        iconTint = FirstBankLightBlue
    ),
    Transaction(
        title = "Salary Credit",
        subtitle = "From: ABC Company",
        amount = "+₦500,000",
        date = "May 19, 09:00 AM",
        isCredit = true,
        icon = Icons.Default.AccountBalance,
        iconBg = Color(0xFFE8F5E9),
        iconTint = Color(0xFF2E7D32)
    ),
    Transaction(
        title = "Adebola Transfer",
        subtitle = "To: 3045832435",
        amount = "-₦25,000",
        date = "May 18, 04:30 PM",
        isCredit = false,
        icon = Icons.AutoMirrored.Filled.Send,
        iconBg = Color(0xFFFCE4EC),
        iconTint = Color(0xFFC2185B)
    ),
    Transaction(
        title = "Electricity Bill",
        subtitle = "IKEDC Payment",
        amount = "-₦12,500",
        date = "May 15, 11:00 AM",
        isCredit = false,
        icon = Icons.Default.ElectricBolt,
        iconBg = Color(0xFFFFF8E1),
        iconTint = Color(0xFFF9A825)
    )
)