// app/src/main/java/com/firstbank/app/presentation/MainActivity.kt
package com.firstbank.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.firstbank.app.presentation.screens.addmoney.AddMoneyScreen
import com.firstbank.app.presentation.screens.airtime.AirtimeScreen
import com.firstbank.app.presentation.screens.auth.LoginScreen
import com.firstbank.app.presentation.screens.auth.SignUpScreen
import com.firstbank.app.presentation.screens.beneficiaries.BeneficiariesScreen
import com.firstbank.app.presentation.screens.bills.BillScreen
import com.firstbank.app.presentation.screens.card.CardScreen
import com.firstbank.app.presentation.screens.data.DataScreen
import com.firstbank.app.presentation.screens.history.TransactionHistoryScreen
import com.firstbank.app.presentation.screens.home.HomeScreen
import com.firstbank.app.presentation.screens.loan.LoanScreen
import com.firstbank.app.presentation.screens.logo.LogoScreen
import com.firstbank.app.presentation.screens.more.MoreScreen
import com.firstbank.app.presentation.screens.profile.ProfileScreen
import com.firstbank.app.presentation.screens.qr.QRScreen
import com.firstbank.app.presentation.screens.scan.ScanScreen
import com.firstbank.app.presentation.screens.settings.SettingsScreen
import com.firstbank.app.presentation.screens.support.SupportScreen
import com.firstbank.app.presentation.screens.transfer.TransferScreen
import com.firstbank.app.presentation.screens.splash.SplashScreen
import com.firstbank.app.presentation.screens.splash.SplashViewModel
import com.firstbank.app.ui.theme.FirstBankTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstBankTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "logo"
                    ) {
                        // Splash & Auth Flow
                        composable("logo") {
                            LogoScreen(
                                onNavigateToSplash = {
                                    navController.navigate("splash") {
                                        popUpTo("logo") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("splash") {
                            SplashScreen(
                                viewModel = SplashViewModel(),
                                onNavigateToLogin = { navController.navigate("login") },
                                onNavigateToSignUp = { navController.navigate("signup") },
                                onNavigateToHome = { navController.navigate("home") }
                            )
                        }
                        composable("login") {
                            LoginScreen(
                                onNavigateToHome = {
                                    navController.navigate("home") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                onNavigateToSignUp = { navController.navigate("signup") }
                            )
                        }
                        composable("signup") {
                            SignUpScreen(
                                onNavigateToLogin = { navController.navigate("login") },
                                onNavigateToHome = {
                                    navController.navigate("home") {
                                        popUpTo("signup") { inclusive = true }
                                    }
                                }
                            )
                        }

                        // Home & Main Features
                        composable("home") {
                            HomeScreen(
                                onNavigateToTransfer = { navController.navigate("transfer") },
                                onNavigateToAirtime = { navController.navigate("airtime") },
                                onNavigateToData = { navController.navigate("data") },
                                onNavigateToBills = { navController.navigate("bills") },
                                onNavigateToHistory = { navController.navigate("history") },
                                onNavigateToMore = { navController.navigate("more") },
                                onNavigateToCards = { navController.navigate("cards") },
                                onNavigateToLoans = { navController.navigate("loans") },
                                onNavigateToQR = { navController.navigate("qr") },
                                onNavigateToScan = { navController.navigate("scan") },
                                onNavigateToProfile = { navController.navigate("profile") },
                                onNavigateToBeneficiaries = { navController.navigate("beneficiaries") },
                                onNavigateToSupport = { navController.navigate("support") },
                                onNavigateToAddMoney = { navController.navigate("add_money") }
                            )
                        }

                        // Core Banking Screens
                        composable("transfer") {
                            TransferScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable("airtime") {
                            AirtimeScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable("data") {
                            DataScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable("bills") {
                            BillScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable("cards") {
                            CardScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable("loans") {
                            LoanScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable("qr") {
                            QRScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable("scan") {
                            ScanScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onPaymentSuccess = { navController.popBackStack() }
                            )
                        }
                        composable("history") {
                            TransactionHistoryScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable("add_money") {
                            AddMoneyScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }

                        // More Menu & Sub-screens
                        composable("more") {
                            MoreScreen(
                                onNavigateToSettings = { navController.navigate("settings") },
                                onNavigateToProfile = { navController.navigate("profile") },
                                onNavigateToSupport = { navController.navigate("support") },
                                onNavigateToBeneficiaries = { navController.navigate("beneficiaries") },
                                onNavigateToHistory = { navController.navigate("history") },
                                onNavigateToCards = { navController.navigate("cards") },
                                onNavigateToLoans = { navController.navigate("loans") },
                                onNavigateToScan = { navController.navigate("scan") },
                                onLogout = {
                                    navController.navigate("login") {
                                        popUpTo("home") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("settings") {
                            SettingsScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable("profile") {
                            ProfileScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateToSettings = { navController.navigate("settings") }
                            )
                        }
                        composable("support") {
                            SupportScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable("beneficiaries") {
                            BeneficiariesScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateToTransfer = {
                                    navController.navigate("transfer")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}