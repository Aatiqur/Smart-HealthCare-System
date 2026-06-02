# 🏥 Smart HealthCare System

> An IoT-based healthcare ecosystem that connects patients, doctors, and hospitals through real-time monitoring, hospital bed management, and cloud-based data sharing.

## 📖 Overview

Smart HealthCare System is designed to make healthcare smarter and safer, especially during contagious disease outbreaks such as COVID-19. The system provides real-time patient health monitoring, hospital bed availability tracking, and remote access to patient information through Android applications and cloud services.

By integrating IoT devices, mobile applications, and Firebase Cloud, the system enables doctors, hospitals, and patient relatives to stay connected and informed at all times.

---

## 🎯 Project Goals

- Keep doctors safe from contagious diseases.
- Provide accurate hospital bed availability information.
- Enable real-time patient monitoring.
- Improve communication between hospitals, doctors, and patients.
- Make the healthcare system smarter and more efficient.

---

## ✨ Key Features

- 📊 Real-time Heart Rate Monitoring
- 🫁 Real-time SpO₂ Monitoring
- 🌡️ Body Temperature Monitoring
- 🏥 Hospital Bed Availability Tracking
- 📱 Android-Based Admin Application
- 👨‍⚕️ Android-Based User Application
- ☁️ Firebase Cloud Integration
- 🔄 Automatic Data Updates Every 10 Seconds
- 📞 Instant Phone Call System
- 📷 QR Code-Based Patient Access
- 🚨 Remote Health Monitoring

---

## 🏗️ System Architecture

```text
MAX30100 Pulse Oximeter
            │
            ▼
      NodeMCU ESP8266
            ▲
            │
 DS18B20 Temperature Sensor
            │
            ▼
      OLED Display
            │
            ▼
     Firebase Cloud
        /      \
       /        \
 Admin App    User App
```

---

## 🔧 Hardware Components

| Component | Purpose |
|-----------|----------|
| NodeMCU ESP8266 | Main Controller & WiFi Communication |
| MAX30100 | Heart Rate & SpO₂ Measurement |
| DS18B20 | Body Temperature Measurement |
| OLED Display | Real-Time Data Display |

---

## 💻 Software Stack

- Android Studio
- Java
- Firebase Realtime Database
- Arduino IDE
- ESP8266 Framework

---

## 📱 Applications

### Admin App
Hospital authorities can:

- Register hospitals
- Login securely
- Add new beds
- Update patient information
- Monitor bed availability
- Manage hospital data

### User App
Doctors and patient relatives can:

- Check hospital bed availability
- Scan QR codes for patient access
- Monitor patient health remotely
- View heart rate, SpO₂, and temperature
- Contact doctors instantly

---

## 📈 Monitored Parameters

| Parameter | Sensor |
|------------|---------|
| Heart Rate (BPM) | MAX30100 |
| Blood Oxygen (SpO₂) | MAX30100 |
| Body Temperature | DS18B20 |

Data is automatically uploaded to Firebase every **10 seconds**.

---

## 🚀 Benefits

- Reduces doctor exposure to infectious diseases.
- Provides real-time patient monitoring.
- Saves time by showing available hospital beds.
- Improves healthcare accessibility.
- Enables remote healthcare management.
- Supports emergency response systems.

---

## 🔮 Future Improvements

- AI-based health prediction
- Ambulance GPS tracking
- Online doctor appointment system
- Medical record management
- Push notification alerts
- Multi-hospital integration

---

## 👨‍💻 Author

**Atiqur Rahman**  
Team: **Captain Nemo**  
Bangladesh

---

## ❤️ Vision

> "Making Healthcare Smarter Through IoT, Cloud Computing, and Real-Time Monitoring."
