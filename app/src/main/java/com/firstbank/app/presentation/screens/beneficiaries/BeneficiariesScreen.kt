package com.firstbank.app.presentation.screens.beneficiaries

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PeopleOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.firstbank.app.presentation.screens.home.FirstBankDeepBlue
import com.firstbank.app.presentation.screens.home.FirstBankGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeneficiariesScreen(
    onNavigateBack: () -> Unit,
    onNavigateToTransfer: (Beneficiary) -> Unit = {}
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }

    var beneficiaries by remember {
        mutableStateOf(
            listOf(
                Beneficiary("Adebola Johnson", "GTBank", "0123456789", "Recent"),
                Beneficiary("Amara Falobi", "Zenith Bank", "0987654321", "Frequent"),
                Beneficiary("David Morrison", "First Bank", "3045832435", "Frequent"),
                Beneficiary("Chioma Okonkwo", "UBA", "1122334455", "Recent"),
                Beneficiary("Emmanuel Adeyemi", "Access Bank", "5566778899", "Recent"),
                Beneficiary("Fatima Bello", "FCMB", "2233445566", "Other"),
                Beneficiary("John Okafor", "Fidelity Bank", "3344556677", "Other"),
                Beneficiary("Ngozi Eze", "Union Bank", "4455667788", "Other")
            )
        )
    }

    val filters = listOf("All", "Frequent", "Recent", "Other")

    val filteredBeneficiaries = beneficiaries.filter { beneficiary ->
        val matchesSearch = beneficiary.name.contains(searchQuery, ignoreCase = true) ||
                beneficiary.accountNumber.contains(searchQuery) ||
                beneficiary.bank.contains(searchQuery, ignoreCase = true)
        val matchesFilter = selectedFilter == "All" || beneficiary.category == selectedFilter
        matchesSearch && matchesFilter
    }.sortedBy { it.name }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Beneficiaries", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = FirstBankDeepBlue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFF5F5F5),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = FirstBankGold,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Beneficiary",
                    tint = FirstBankDeepBlue
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search beneficiaries...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                singleLine = true
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
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
                            containerColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BeneficiaryStat("Total", beneficiaries.size.toString())
                BeneficiaryStat("Frequent", beneficiaries.count { it.category == "Frequent" }.toString())
                BeneficiaryStat("Recent", beneficiaries.count { it.category == "Recent" }.toString())
            }

            if (filteredBeneficiaries.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.PeopleOutline,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No beneficiaries found",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                        TextButton(onClick = { showAddDialog = true }) {
                            Text("Add your first beneficiary")
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(filteredBeneficiaries) { beneficiary ->
                        BeneficiaryListItem(
                            beneficiary = beneficiary,
                            onTransferClick = { onNavigateToTransfer(beneficiary) },
                            onDeleteClick = {
                                beneficiaries = beneficiaries.filter { it != beneficiary }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddBeneficiaryDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { name, bank, accountNumber ->
                beneficiaries = beneficiaries + Beneficiary(
                    name = name,
                    bank = bank,
                    accountNumber = accountNumber,
                    category = "Other"
                )
                showAddDialog = false
            }
        )
    }
}

@Composable
fun BeneficiaryStat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = FirstBankDeepBlue
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun BeneficiaryListItem(
    beneficiary: Beneficiary,
    onTransferClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
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
                    .background(
                        when (beneficiary.category) {
                            "Frequent" -> FirstBankGold.copy(alpha = 0.3f)
                            "Recent" -> FirstBankDeepBlue.copy(alpha = 0.2f)
                            else -> Color.LightGray.copy(alpha = 0.5f)
                        }
                    ),
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

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = beneficiary.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
                Text(
                    text = "${beneficiary.bank} · ${beneficiary.accountNumber}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                if (beneficiary.category == "Frequent") {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = FirstBankGold,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Frequent",
                            fontSize = 11.sp,
                            color = FirstBankGold,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = Color.Gray
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Transfer") },
                        leadingIcon = { Icon(Icons.AutoMirrored.Filled.Send, null) },
                        onClick = {
                            showMenu = false
                            onTransferClick()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Delete") },
                        leadingIcon = { Icon(Icons.Default.Delete, null) },
                        onClick = {
                            showMenu = false
                            onDeleteClick()
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            OutlinedButton(
                onClick = onTransferClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = FirstBankDeepBlue
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Quick Transfer", fontSize = 13.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBeneficiaryDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var accountNumber by remember { mutableStateOf("") }
    var selectedBank by remember { mutableStateOf("First Bank") }
    var showBankDropdown by remember { mutableStateOf(false) }

    val banks = listOf(
        "First Bank", "GTBank", "Zenith Bank", "UBA", "Access Bank",
        "FCMB", "Fidelity Bank", "Union Bank", "Sterling Bank", "Wema Bank"
    )

    AlertDialog(
        onDismissRequest = {
            name = ""
            accountNumber = ""
            selectedBank = "First Bank"
            showBankDropdown = false
            onDismiss()
        },
        title = { Text("Add Beneficiary", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Account Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = accountNumber,
                    onValueChange = {
                        if (it.length <= 10 && it.all { c -> c.isDigit() }) accountNumber = it
                    },
                    label = { Text("Account Number") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = showBankDropdown,
                    onExpandedChange = { showBankDropdown = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedBank,
                        onValueChange = { },
                        label = { Text("Bank") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showBankDropdown) }
                    )
                    ExposedDropdownMenu(
                        expanded = showBankDropdown,
                        onDismissRequest = { showBankDropdown = false }
                    ) {
                        banks.forEach { bank ->
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
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onAdd(name, selectedBank, accountNumber)
                    name = ""
                    accountNumber = ""
                    selectedBank = "First Bank"
                },
                enabled = name.isNotBlank() && accountNumber.length == 10,
                colors = ButtonDefaults.buttonColors(containerColor = FirstBankGold)
            ) {
                Text("Add", color = FirstBankDeepBlue)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    name = ""
                    accountNumber = ""
                    selectedBank = "First Bank"
                    showBankDropdown = false
                    onDismiss()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}

data class Beneficiary(
    val name: String,
    val bank: String,
    val accountNumber: String,
    val category: String = "Other"
)