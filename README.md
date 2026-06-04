# 🏦 First Bank App

A modern Android banking application built with **Jetpack Compose** featuring a clean UI inspired by First Bank of Nigeria. This app demonstrates best practices for building fintech applications with mock data patterns.

## ✨ Features

| Feature | Description |
|---------|-------------|
| 🔐 **Authentication** | Login & Sign Up screens |
| 🏠 **Home Dashboard** | Account balance, quick actions, recent transactions |
| 💸 **Transfers** | Send money to any Nigerian bank |
| 📱 **Airtime** | Buy airtime for MTN, Airtel, Glo, 9mobile |
| 🌐 **Data** | Purchase data bundles |
| 🧾 **Bills** | Pay electricity, TV, internet, water bills |
| 💳 **Cards** | Card management screen |
| 💰 **Loans** | Loan offers and applications |
| 📷 **QR/Scan** | QR code payments & scanner |
| 👤 **Profile** | User profile management |
| 📜 **History** | Transaction history |
| ⭐ **More** | Additional banking services |

## 🎨 Screenshots

| Splash | Login | Home |
|--------|-------|------|
| ![Splash](screenshots/splash.png) | ![Login](screenshots/login.png) | ![Home](screenshots/home.png) |

| Transfer | Airtime | Data |
|----------|---------|------|
| ![Transfer](screenshots/transfer.png) | ![Airtime](screenshots/airtime.png) | ![Data](screenshots/data.png) |

| Bills | Cards | Profile |
|-------|-------|---------|
| ![Bills](screenshots/bills.png) | ![Cards](screenshots/cards.png) | ![Profile](screenshots/profile.png) |

> 📸 *Add your screenshots to a `screenshots/` folder and update the paths above*

## 🛠 Tech Stack

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Architecture:** MVVM (Model-View-ViewModel)
- **Navigation:** Jetpack Navigation Compose
- **State Management:** Compose State & ViewModel
- **Design:** Material Design 3

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- Minimum SDK: 24
- Target SDK: 34
- Kotlin: 1.9.0+

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Dozzynet120/FirstBankApp.git
Open in Android Studio
Sync project with Gradle files
Run on emulator or physical device
📁 Project Structure
plain
com.firstbank.app/
├── presentation/
│   ├── screens/
│   │   ├── auth/           # Login & SignUp
│   │   ├── home/           # Dashboard
│   │   ├── transfer/       # Money transfer
│   │   ├── airtime/        # Airtime purchase
│   │   ├── data/           # Data bundles
│   │   ├── bills/          # Bill payments
│   │   ├── card/           # Card management
│   │   ├── loan/           # Loans
│   │   ├── profile/        # User profile
│   │   └── ...             # More screens
│   └── MainActivity.kt
├── ui/theme/               # App theme & colors
└── FirstBankApplication.kt
