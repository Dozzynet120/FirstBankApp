package com.firstbank.app.presentation.screens.support

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.firstbank.app.presentation.screens.home.FirstBankDeepBlue
import com.firstbank.app.presentation.screens.home.FirstBankGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportScreen(
    onNavigateBack: () -> Unit,
    onNavigateToChat: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var expandedFaq by remember { mutableStateOf<String?>(null) }

    val categories = listOf("All", "Account", "Transfer", "Card", "Loan", "Security")

    val faqs = listOf(
        FaqItem(
            "How do I reset my password?",
            "Go to Settings > Security > Change Password. You'll need your current password and a verification code sent to your registered phone number.",
            "Account"
        ),
        FaqItem(
            "What is the daily transfer limit?",
            "Tier 1: ₦50,000\nTier 2: ₦500,000\nTier 3: ₦5,000,000\nTier 4: ₦10,000,000\nYou can upgrade your tier in Settings.",
            "Transfer"
        ),
        FaqItem(
            "How do I block my card?",
            "Go to Cards > Block Card, or call our 24/7 hotline immediately. You can unblock it later from the same menu.",
            "Card"
        ),
        FaqItem(
            "Why did my transfer fail?",
            "Common reasons: insufficient funds, wrong account details, network issues, or exceeding your daily limit. Check your balance and try again.",
            "Transfer"
        ),
        FaqItem(
            "How do I apply for a loan?",
            "Go to Loans section, select a loan type, enter amount and tenure, then submit. Approval takes 24-48 hours.",
            "Loan"
        ),
        FaqItem(
            "Is my data secure?",
            "Yes. We use 256-bit encryption, biometric authentication, and PCI-DSS compliant systems. Never share your PIN or OTP.",
            "Security"
        ),
        FaqItem(
            "How do I update my BVN?",
            "Visit any FirstBank branch with valid ID. BVN updates cannot be done via the app for security reasons.",
            "Account"
        ),
        FaqItem(
            "What are the card delivery timelines?",
            "Instant cards: Available immediately at branch\nStandard delivery: 3-5 business days\nExpress delivery: 24-48 hours (₦2,000 fee)",
            "Card"
        )
    )

    val filteredFaqs = if (selectedCategory == "All") {
        faqs.filter { it.question.contains(searchQuery, ignoreCase = true) }
    } else {
        faqs.filter {
            it.category == selectedCategory &&
                    it.question.contains(searchQuery, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Help & Support", fontWeight = FontWeight.Bold) },
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
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search FAQs...") },
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
                categories.forEach { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
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
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickSupportCard(
                    icon = Icons.AutoMirrored.Filled.Chat,
                    title = "Live Chat",
                    subtitle = "Talk to agent",
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToChat
                )
                QuickSupportCard(
                    icon = Icons.Default.Phone,
                    title = "Call Us",
                    subtitle = "0700-FIRSTBANK",
                    modifier = Modifier.weight(1f),
                    onClick = {}
                )
                QuickSupportCard(
                    icon = Icons.Default.Email,
                    title = "Email",
                    subtitle = "support@firstbank.com",
                    modifier = Modifier.weight(1f),
                    onClick = {}
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Frequently Asked Questions",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = FirstBankDeepBlue,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(filteredFaqs) { faq ->
                    FaqCard(
                        faq = faq,
                        isExpanded = expandedFaq == faq.question,
                        onClick = {
                            expandedFaq = if (expandedFaq == faq.question) null else faq.question
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun QuickSupportCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = FirstBankDeepBlue,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun FaqCard(
    faq: FaqItem,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isExpanded) FirstBankDeepBlue else Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = faq.question,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isExpanded) Color.White else Color.Black,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = if (isExpanded) Color.White else Color.Gray
                )
            }
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = faq.answer,
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    lineHeight = 20.sp
                )
            }
        }
    }
}

data class FaqItem(
    val question: String,
    val answer: String,
    val category: String
)