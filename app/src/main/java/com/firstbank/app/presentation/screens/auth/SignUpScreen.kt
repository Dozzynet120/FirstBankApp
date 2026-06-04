// app/src/main/java/com/firstbank/app/presentation/screens/auth/SignUpScreen.kt
package com.firstbank.app.presentation.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.firstbank.app.presentation.screens.home.FirstBankDeepBlue
import com.firstbank.app.presentation.screens.home.FirstBankGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    // Personal Information
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var placeOfBirth by remember { mutableStateOf("") }

    // Identity Documents
    var bvn by remember { mutableStateOf("") }
    var nin by remember { mutableStateOf("") }

    // Address Information
    var residentialAddress by remember { mutableStateOf("") }
    var stateOfResidence by remember { mutableStateOf("") }
    var stateOfOrigin by remember { mutableStateOf("") }
    var lgaOfOrigin by remember { mutableStateOf("") }

    // Next of Kin
    var nextOfKinName by remember { mutableStateOf("") }
    var nextOfKinRelationship by remember { mutableStateOf("") }
    var nextOfKinPhone by remember { mutableStateOf("") }
    var nextOfKinAddress by remember { mutableStateOf("") }

    // Security
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var termsAccepted by remember { mutableStateOf(false) }

    // Dropdown states
    var showDatePicker by remember { mutableStateOf(false) }
    var showStateResidenceDropdown by remember { mutableStateOf(false) }
    var showStateOriginDropdown by remember { mutableStateOf(false) }
    var showRelationshipDropdown by remember { mutableStateOf(false) }

    // Lists for dropdowns
    val nigerianStates = listOf(
        "Abia", "Adamawa", "Akwa Ibom", "Anambra", "Bauchi", "Bayelsa", "Benue",
        "Borno", "Cross River", "Delta", "Ebonyi", "Edo", "Ekiti", "Enugu", "FCT",
        "Gombe", "Imo", "Jigawa", "Kaduna", "Kano", "Katsina", "Kebbi", "Kogi",
        "Kwara", "Lagos", "Nasarawa", "Niger", "Ogun", "Ondo", "Osun", "Oyo",
        "Plateau", "Rivers", "Sokoto", "Taraba", "Yobe", "Zamfara"
    )

    val relationships = listOf(
        "Spouse", "Parent", "Child", "Sibling", "Relative", "Friend", "Colleague"
    )

    // Date Picker Dialog
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val calendar = java.util.Calendar.getInstance()
                        calendar.timeInMillis = millis
                        val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)
                        val month = calendar.get(java.util.Calendar.MONTH) + 1
                        val year = calendar.get(java.util.Calendar.YEAR)
                        dateOfBirth = "$day/$month/$year"
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Header
        Text(
            text = "Create Account",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = FirstBankDeepBlue
        )

        Text(
            text = "Open a FirstBank account in minutes",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Section: Personal Information
        SectionHeader("Personal Information")

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name (as on BVN)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            prefix = { Text("+234 ") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Date of Birth - Dropdown
        OutlinedTextField(
            value = dateOfBirth,
            onValueChange = { },
            label = { Text("Date of Birth") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(Icons.Default.CalendarToday, contentDescription = "Select Date")
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = placeOfBirth,
            onValueChange = { placeOfBirth = it },
            label = { Text("Place of Birth") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Section: Identity Documents
        SectionHeader("Identity Verification")

        OutlinedTextField(
            value = bvn,
            onValueChange = {
                if (it.length <= 11 && it.all { char -> char.isDigit() }) bvn = it
            },
            label = { Text("Bank Verification Number (BVN)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            supportingText = { Text("11 digits required", fontSize = 11.sp) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = nin,
            onValueChange = {
                if (it.length <= 11 && it.all { char -> char.isDigit() }) nin = it
            },
            label = { Text("National Identity Number (NIN)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            supportingText = { Text("11 digits required", fontSize = 11.sp) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Section: Address Information
        SectionHeader("Address Information")

        OutlinedTextField(
            value = residentialAddress,
            onValueChange = { residentialAddress = it },
            label = { Text("Residential Address") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            minLines = 2
        )

        Spacer(modifier = Modifier.height(12.dp))

        // State of Residence Dropdown
        ExposedDropdownMenuBox(
            expanded = showStateResidenceDropdown,
            onExpandedChange = { showStateResidenceDropdown = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = stateOfResidence,
                onValueChange = { },
                label = { Text("State of Residence") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showStateResidenceDropdown) }
            )
            ExposedDropdownMenu(
                expanded = showStateResidenceDropdown,
                onDismissRequest = { showStateResidenceDropdown = false }
            ) {
                nigerianStates.forEach { state ->
                    DropdownMenuItem(
                        text = { Text(state) },
                        onClick = {
                            stateOfResidence = state
                            showStateResidenceDropdown = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // State of Origin Dropdown
        ExposedDropdownMenuBox(
            expanded = showStateOriginDropdown,
            onExpandedChange = { showStateOriginDropdown = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = stateOfOrigin,
                onValueChange = { },
                label = { Text("State of Origin") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showStateOriginDropdown) }
            )
            ExposedDropdownMenu(
                expanded = showStateOriginDropdown,
                onDismissRequest = { showStateOriginDropdown = false }
            ) {
                nigerianStates.forEach { state ->
                    DropdownMenuItem(
                        text = { Text(state) },
                        onClick = {
                            stateOfOrigin = state
                            showStateOriginDropdown = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = lgaOfOrigin,
            onValueChange = { lgaOfOrigin = it },
            label = { Text("Local Government Area (LGA) of Origin") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Section: Next of Kin
        SectionHeader("Next of Kin Information")

        OutlinedTextField(
            value = nextOfKinName,
            onValueChange = { nextOfKinName = it },
            label = { Text("Next of Kin Full Name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Relationship Dropdown
        ExposedDropdownMenuBox(
            expanded = showRelationshipDropdown,
            onExpandedChange = { showRelationshipDropdown = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = nextOfKinRelationship,
                onValueChange = { },
                label = { Text("Relationship") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showRelationshipDropdown) }
            )
            ExposedDropdownMenu(
                expanded = showRelationshipDropdown,
                onDismissRequest = { showRelationshipDropdown = false }
            ) {
                relationships.forEach { relationship ->
                    DropdownMenuItem(
                        text = { Text(relationship) },
                        onClick = {
                            nextOfKinRelationship = relationship
                            showRelationshipDropdown = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = nextOfKinPhone,
            onValueChange = { nextOfKinPhone = it },
            label = { Text("Next of Kin Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            prefix = { Text("+234 ") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = nextOfKinAddress,
            onValueChange = { nextOfKinAddress = it },
            label = { Text("Next of Kin Address") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            minLines = 2
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Section: Security
        SectionHeader("Security")

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            supportingText = { Text("Minimum 8 characters", fontSize = 11.sp) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Terms
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = termsAccepted,
                onCheckedChange = { termsAccepted = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = FirstBankDeepBlue
                )
            )
            Text(
                text = "I agree to the Terms & Conditions and Privacy Policy",
                fontSize = 11.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sign Up Button
        Button(
            onClick = onNavigateToHome,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(26.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = FirstBankGold
            )
        ) {
            Text(
                text = "Create Account",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = FirstBankDeepBlue
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Already a customer? Login
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNavigateToLogin() },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF5F5F5)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Already a FirstBank customer?",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Log in to your account",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = FirstBankDeepBlue
                    )
                }
                Text(
                    text = "Login →",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = FirstBankGold
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun SectionHeader(title: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = FirstBankDeepBlue
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = FirstBankGold.copy(alpha = 0.3f)
        )
    }
}