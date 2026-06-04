package com.firstbank.app.presentation.screens.transfer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
fun TransferScreen(
    onNavigateBack: () -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var accountNumber by remember { mutableStateOf("") }
    var selectedBank by remember { mutableStateOf("First Bank") }
    var showBankDropdown by remember { mutableStateOf(false) }
    var narration by remember { mutableStateOf("") }
    var showPinDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transfer", fontWeight = FontWeight.Bold) },
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
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Available Balance",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                    Text(
                        text = "₦30,700,000.00",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                prefix = { Text("₦") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = accountNumber,
                onValueChange = {
                    if (it.length <= 10 && it.all { c -> c.isDigit() }) accountNumber = it
                },
                label = { Text("Account Number") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Bank Dropdown with expanded list
            ExposedDropdownMenuBox(
                expanded = showBankDropdown,
                onExpandedChange = { showBankDropdown = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedBank,
                    onValueChange = { },
                    label = { Text("Select Bank") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showBankDropdown) }
                )
                ExposedDropdownMenu(
                    expanded = showBankDropdown,
                    onDismissRequest = { showBankDropdown = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp)
                ) {
                    // Commercial Banks
                    DropdownMenuItem(
                        text = { Text("--- Commercial Banks ---", fontWeight = FontWeight.Bold, color = FirstBankDeepBlue) },
                        onClick = { },
                        enabled = false
                    )
                    commercialBanks.forEach { bank ->
                        DropdownMenuItem(
                            text = { Text(bank) },
                            onClick = {
                                selectedBank = bank
                                showBankDropdown = false
                            }
                        )
                    }

                    // Microfinance Banks
                    DropdownMenuItem(
                        text = { Text("--- Microfinance Banks ---", fontWeight = FontWeight.Bold, color = FirstBankDeepBlue) },
                        onClick = { },
                        enabled = false
                    )
                    microfinanceBanks.forEach { bank ->
                        DropdownMenuItem(
                            text = { Text(bank) },
                            onClick = {
                                selectedBank = bank
                                showBankDropdown = false
                            }
                        )
                    }

                    // Fintech/Mobile Banks
                    DropdownMenuItem(
                        text = { Text("--- Fintech / Mobile Banks ---", fontWeight = FontWeight.Bold, color = FirstBankDeepBlue) },
                        onClick = { },
                        enabled = false
                    )
                    fintechBanks.forEach { bank ->
                        DropdownMenuItem(
                            text = { Text(bank) },
                            onClick = {
                                selectedBank = bank
                                showBankDropdown = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = narration,
                onValueChange = { narration = it },
                label = { Text("Narration (Optional)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 2
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { showPinDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = FirstBankGold
                ),
                enabled = amount.isNotBlank() && accountNumber.length == 10
            ) {
                Text(
                    text = "Transfer",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = FirstBankDeepBlue
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Recent Beneficiaries",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = FirstBankDeepBlue
            )

            Spacer(modifier = Modifier.height(8.dp))

            sampleBeneficiaries.forEach { beneficiary ->
                BeneficiaryItem(beneficiary = beneficiary)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    // PIN Dialog
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
                    },
                    enabled = pin.length == 4
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

    // Success Dialog
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            icon = {
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
            },
            title = {
                Text(
                    text = "Transfer Successful!",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "₦$amount",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = FirstBankDeepBlue
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "has been sent to",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = accountNumber,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                    Text(
                        text = selectedBank,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Transaction Reference: TRX${System.currentTimeMillis()}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        amount = ""
                        accountNumber = ""
                        narration = ""
                        selectedBank = "First Bank"
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = FirstBankGold),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Done",
                        color = FirstBankDeepBlue,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        )
    }
}

@Composable
fun BeneficiaryItem(beneficiary: Beneficiary) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
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
                    .clip(CircleShape)
                    .background(FirstBankGold.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = beneficiary.name.take(2).uppercase(),
                    fontWeight = FontWeight.Bold,
                    color = FirstBankDeepBlue,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = beneficiary.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Text(
                    text = "${beneficiary.bank} · ${beneficiary.accountNumber}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

data class Beneficiary(
    val name: String,
    val bank: String,
    val accountNumber: String
)

val sampleBeneficiaries = listOf(
    Beneficiary("Adebola Johnson", "GTBank", "0123456789"),
    Beneficiary("Amara Falobi", "Zenith Bank", "0987654321"),
    Beneficiary("David Morrison", "First Bank", "3045832435")
)

// Commercial Banks
val commercialBanks = listOf(
    "First Bank",
    "Access Bank",
    "GTBank",
    "Zenith Bank",
    "UBA",
    "Fidelity Bank",
    "Union Bank",
    "Sterling Bank",
    "Wema Bank",
    "FCMB",
    "Ecobank",
    "Polaris Bank",
    "Keystone Bank",
    "Unity Bank",
    "Heritage Bank",
    "Stanbic IBTC Bank",
    "Standard Chartered",
    "Citibank Nigeria",
    "Jaiz Bank",
    "Titan Trust Bank",
    "Globus Bank",
    "Premium Trust Bank",
    "Parallex Bank",
    "Providus Bank",
    "SunTrust Bank",
    "Coronation Merchant Bank",
    "FBNQuest Merchant Bank",
    "FSDH Merchant Bank",
    "Rand Merchant Bank",
    "Nova Merchant Bank"
)

// Microfinance Banks
val microfinanceBanks = listOf(
    "AB Microfinance Bank",
    "Accion Microfinance Bank",
    "Addosser Microfinance Bank",
    "Advans La Fayette Microfinance Bank",
    "Alekun Microfinance Bank",
    "Allworkers Microfinance Bank",
    "Alpha Kapital Microfinance Bank",
    "Astrapolaris Microfinance Bank",
    "Baines Credit Microfinance Bank",
    "Bluewhales Microfinance Bank",
    "Boctrust Microfinance Bank",
    "Bosak Microfinance Bank",
    "CASHCONNECT Microfinance Bank",
    "CEMCS Microfinance Bank",
    "Chikum Microfinance Bank",
    "CIT Microfinance Bank",
    "Consumer Microfinance Bank",
    "Corestep Microfinance Bank",
    "Covenant Microfinance Bank",
    "Credit Afrique Microfinance Bank",
    "Daylight Microfinance Bank",
    "E-Barcs Microfinance Bank",
    "Eagle Flight Microfinance Bank",
    "Eartholeum Microfinance Bank",
    "Ecobank Xpress",
    "Ekondo Microfinance Bank",
    "Empire Trust Microfinance Bank",
    "ESAN Microfinance Bank",
    "Eso-E Microfinance Bank",
    "Evangel Microfinance Bank",
    "Fast Microfinance Bank",
    "FBN Mortgages",
    "FET Microfinance Bank",
    "FFS Microfinance Bank",
    "Fidelity Mobile",
    "Finatrust Microfinance Bank",
    "First Gen Mortgage Bank",
    "First Mortgage",
    "First Option Microfinance Bank",
    "Fortis Microfinance Bank",
    "Fullrange Microfinance Bank",
    "Futo Microfinance Bank",
    "Gashua Microfinance Bank",
    "GATE Microfinance Bank",
    "Giant Stride Microfinance Bank",
    "Girei Microfinance Bank",
    "Green Energy Microfinance Bank",
    "Greenbank Microfinance Bank",
    "Grooming Microfinance Bank",
    "GS Microfinance Bank",
    "Hackman Microfinance Bank",
    "Hasal Microfinance Bank",
    "Highland Microfinance Bank",
    "HomeBase Mortgage Bank",
    "IBILE Microfinance Bank",
    "Ikire Microfinance Bank",
    "Infinity Microfinance Bank",
    "Infinity Trust Mortgage Bank",
    "Innovation Microfinance Bank",
    "Jubilee Life Mortgage Bank",
    "Kadpoly Microfinance Bank",
    "KCMB Microfinance Bank",
    "Kenechukwu Microfinance Bank",
    "Kingdom College Microfinance Bank",
    "La Fayette Microfinance Bank",
    "Lagoon Microfinance Bank",
    "Landgold Microfinance Bank",
    "LAPO Microfinance Bank",
    "Lavender Microfinance Bank",
    "Lifegate Microfinance Bank",
    "Light Microfinance Bank",
    "Links Microfinance Bank",
    "Living Trust Mortgage Bank",
    "M36",
    "Mainland Microfinance Bank",
    "Mainstreet Microfinance Bank",
    "Malachy Microfinance Bank",
    "Microvis Microfinance Bank",
    "Midland Microfinance Bank",
    "Mimoney Microfinance Bank",
    "Mint-Finex MICROFINANCE BANK",
    "Mkudi",
    "Moneyfield Microfinance Bank",
    "Mutual Benefits Microfinance Bank",
    "Mutual Trust Microfinance Bank",
    "Nargata Microfinance Bank",
    "Ndiorah Microfinance Bank",
    "Neptune Microfinance Bank",
    "New Prudential Bank",
    "Nigerian Navy Microfinance Bank",
    "NIRSAL National Microfinance Bank",
    "Nnew Women Microfinance Bank",
    "Noah Microfinance Bank",
    "Novire Microfinance Bank",
    "NPF Microfinance Bank",
    "Ohafia Microfinance Bank",
    "Okpoga Microfinance Bank",
    "Olabisi Onabanjo University Microfinance Bank",
    "Olowolagba Microfinance Bank",
    "Omoluabi Mortgage Bank",
    "Omoluabi Savings and Loans",
    "Onima Microfinance Bank",
    "Optimum Trust Microfinance Bank",
    "Oscotech Microfinance Bank",
    "Osprey Microfinance Bank",
    "Paga",
    "Page Financials",
    "Palmpay",
    "Parralex Microfinance Bank",
    "Partsinvest Microfinance Bank",
    "Peace Microfinance Bank",
    "PecanTrust Microfinance Bank",
    "Personal Trust Microfinance Bank",
    "Petra Microfinance Bank",
    "Platinum Mortgage Bank",
    "PoloPay",
    "Prestige Microfinance Bank",
    "Quickfund Microfinance Bank",
    "Rahama Microfinance Bank",
    "Randalpha Microfinance Bank",
    "Refuge Mortgage Bank",
    "Regent Microfinance Bank",
    "Reliance Microfinance Bank",
    "RenMoney Microfinance Bank",
    "Richway Microfinance Bank",
    "Royal Exchange Microfinance Bank",
    "Sage Grey Finance",
    "Safegate Microfinance Bank",
    "Seedvest Microfinance Bank",
    "Solid Allianze Microfinance Bank",
    "Solid Rock Microfinance Bank",
    "Spectrum Microfinance Bank",
    "Stanford Microfinance Bank",
    "Stellas Microfinance Bank",
    "Sulspap Microfinance Bank",
    "Sunbeam Microfinance Bank",
    "Supreme Microfinance Bank",
    "Taj Bank",
    "Tanadi Microfinance Bank",
    "Tangerine Money",
    "Triple A Microfinance Bank",
    "Trust Microfinance Bank",
    "Trustbond Mortgage Bank",
    "Trustfund Microfinance Bank",
    "U & C Microfinance Bank",
    "Unaab Microfinance Bank",
    "Uniben Microfinance Bank",
    "Unical Microfinance Bank",
    "Unilag Microfinance Bank",
    "Unimaid Microfinance Bank",
    "Union Homes",
    "United Mortgage",
    "VFD Microfinance Bank",
    "Verite Microfinance Bank",
    "Virtue Microfinance Bank",
    "Visa Microfinance Bank",
    "Waya Microfinance Bank",
    "Wetland Microfinance Bank",
    "XSLNCE Microfinance Bank",
    "Yes Microfinance Bank",
    "Yobe Microfinance Bank",
    "Zikora Microfinance Bank",
    "Zwallet Microfinance Bank"
)

// Fintech / Mobile Banks
val fintechBanks = listOf(
    "Opay",
    "Palmpay",
    "Kuda Bank",
    "Moniepoint",
    "FairMoney",
    "Carbon",
    "Branch",
    "RenMoney",
    "Aella Credit",
    "QuickCheck",
    "PiggyVest",
    "Cowrywise",
    "Flutterwave",
    "Paystack",
    "Interswitch",
    "eTranzact",
    "SystemSpecs",
    "Remita",
    "Paga",
    "Cellulant",
    "TeamApt (Moniepoint)",
    "BudPay",
    "Seerbit",
    "Fincra",
    "Nomba (Kudi)",
    "Busha",
    "Trove",
    "Chaka",
    "RiseVest",
    "Bamboo",
    "GetEquity",
    "Releaf",
    "Helium Health",
    "Paylater (Carbon)",
    "Migo",
    "Lidya",
    "Indicina",
    "Okra",
    "Mono",
    "OnePipe",
    "Sudo Africa",
    "Bloc",
    "JUMO",
    "Tala",
    "Lendigo",
    "SMEasy",
    "Prospa",
    "Kippa",
    "Oze",
    "Bumpa",
    "Venti",
    "Aladdin",
    "NowNow",
    "Bitsika",
    "Chipper Cash",
    "Eversend",
    "IntaSend",
    "Payhippo",
    "CredPal",
    "Kiakia",
    "C24 Capital",
    "Rosabon Financial Services",
    "Page Financials",
    "Newedge Finance",
    "Stanford Finance",
    "First City Monument Bank (FCMB) Flex",
    "GTBank Habari",
    "UBA Leo",
    "Zenith Bank ZiVA",
    "Access Bank Closa",
    "First Bank FirstMobile",
    "Union Bank UnionMobile",
    "Sterling Bank Specta",
    "Wema Bank ALAT",
    "Polaris Bank VULTe",
    "Fidelity Bank Fidelity Online",
    "Ecobank Xpress Account",
    "Unity Bank UniFi",
    "Keystone Bank Oxygene",
    "Heritage Bank Octiplus",
    "Stanbic IBTC Mobile",
    "Jaiz Bank JaizMobile",
    "Titan Trust Bank TitanPay",
    "Globus Bank GlobusMobile",
    "Premium Trust Bank PremiumMobile",
    "Parallex Bank ParallexMobile",
    "Providus Bank ProvidusMobile",
    "SunTrust Bank SunTrustMobile",
    "Nova Merchant Bank NovaPay"
)