# RenewGen - Renewable Energy Companion App


![prakashy](https://github.com/user-attachments/assets/a0536d47-f2c4-4b18-bc1d-bb0e8f44280a)

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple.svg)](https://kotlinlang.org)

RenewGen is a comprehensive Android application designed to educate users about renewable energy sources and facilitate sustainable energy solutions. The app provides detailed information about various renewable energy technologies including Solar, Wind, Hydro, Geothermal, Biomass, and Wave energy, along with practical tools for energy management and community engagement.

## 🌟 Features

### Energy Education
- **Comprehensive Coverage**: Detailed information about 6 major renewable energy sources
- **Interactive Learning**: Overview, Technology, Production, Efficiency, and Impact sections for each energy type
- **Visual Content**: Rich graphics and illustrations explaining complex concepts
- **Statistics & Facts**: Up-to-date data on global renewable energy trends

### Smart Features
- **AI-Powered Chat**: Integrated chatbot for energy-related queries using ReGen AI
- **GPS Integration**: Location-based services for energy resource mapping
- **Real-time Tracking**: Order and delivery tracking for energy products
- **Multi-language Support**: Available in English, Hindi, Spanish, and French

### Community & Commerce
- **Social Platform**: Community forums and discussion boards
- **E-commerce**: Purchase renewable energy products and services
- **Payment Integration**: Secure payment processing with multiple options
- **Feedback System**: User feedback and rating system

### User Management
- **Firebase Authentication**: Secure login and registration
- **Profile Management**: User profiles with customizable settings
- **Notifications**: Push notifications for updates and alerts
- **Privacy Controls**: Comprehensive privacy policy and data protection

## 🛠️ Technology Stack

### Core Technologies
- **Language**: Kotlin
- **Platform**: Android (Min SDK 24, Target SDK 34)
- **Architecture**: MVVM with View Binding

### Libraries & Frameworks
- **UI**: Material Design 3, ConstraintLayout, GridLayout
- **Database**: Room Database for local storage
- **Networking**: Retrofit with Gson converter
- **Authentication**: Firebase Auth with UI
- **Backend**: Firebase Firestore, Firebase Analytics
- **Location**: Google Play Services Location
- **Charts**: MPAndroidChart for data visualization
- **Barcode**: ZXing for QR code scanning
- **WebRTC**: Real-time communication capabilities

### Development Tools
- **Build System**: Gradle with Kotlin DSL
- **Dependency Management**: Version Catalogs (libs.versions.toml)
- **Code Quality**: ProGuard for release builds
- **Testing**: JUnit, Espresso for unit and UI testing

## 🚀 Installation

### Prerequisites
- Android Studio Arctic Fox or later
- JDK 11 or higher
- Android device or emulator with API level 24+

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/RenewGen.git
   cd RenewGen
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the cloned directory and select it

3. **Configure Firebase**
   - Create a Firebase project at [Firebase Console](https://console.firebase.google.com)
   - Add your Android app with package name `com.example.re`
   - Download `google-services.json` and place it in `app/` directory
   - Enable Authentication, Firestore, and Analytics in Firebase

4. **Build and Run**
   - Sync project with Gradle files
   - Build the project
   - Run on device or emulator

## 📱 Usage

### First Time Setup
1. Launch the app
2. Create an account or sign in with existing credentials
3. Set your language preference
4. Grant necessary permissions (Location, Notifications)

### Exploring Energy Sources
- Navigate through different energy categories
- Read detailed information and statistics
- View interactive charts and data visualizations
- Use the AI chat for specific queries

### Community Features
- Join discussions in community forums
- Share posts and connect with other users
- Access educational content and resources

### Commerce & Services
- Browse renewable energy products
- Place orders with integrated payment system
- Track deliveries in real-time
- Provide feedback on purchases

## 📁 Project Structure

```
app/
├── src/main/
│   ├── java/com/example/re/
│   │   ├── activities/          # Activity classes for different screens
│   │   ├── services/            # Background services (Tracking, Notifications)
│   │   ├── receivers/           # Broadcast receivers
│   │   └── *Activity.kt         # Main activity files
│   ├── res/
│   │   ├── drawable/            # Icons, images, and vector graphics
│   │   ├── layout/              # XML layout files
│   │   ├── menu/                # Menu resources
│   │   ├── navigation/          # Navigation graph files
│   │   ├── values/              # Strings, colors, themes
│   │   └── xml/                 # Configuration files
│   ├── assets/
│   │   ├── bot/                 # AI bot resources
│   │   └── knowledge/           # Knowledge base files
│   └── AndroidManifest.xml      # App manifest
├── src/test/                    # Unit tests
├── src/androidTest/             # Instrumentation tests
└── build.gradle.kts             # Module build configuration
```

## 🔧 Configuration

### Build Variants
- **Debug**: Development build with debugging enabled
- **Release**: Production build with ProGuard optimization

### Environment Setup
- Configure API keys in `local.properties` for development
- Set up Firebase configuration for different environments
- Configure Google Maps API key for location services

## 🤝 Contributing

We welcome contributions to RenewGen! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Development Guidelines
- Follow Kotlin coding standards
- Write unit tests for new features
- Update documentation for API changes
- Ensure compatibility with minimum SDK version

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Icons and graphics from Material Design Icons
- Educational content sourced from renewable energy research
- Open-source libraries and frameworks used in development

## 📞 Support

For support, email support@renewgen.com or join our community forum.

---

**Made with ❤️ for a sustainable future**

