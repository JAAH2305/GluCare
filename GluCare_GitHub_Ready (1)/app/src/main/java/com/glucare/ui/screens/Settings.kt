package com.glucare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
private val green = Color(0xFF5F7F72)
private val pale = Color(0xFFE7EEE8)
private val dark = Color(0xFF26332E)

@Composable
fun Settings(dataStore: GluCareDataStore) {

    var page by remember { mutableStateOf("Home") }
    var showLogout by remember { mutableStateOf(false) }
    val savedLanguage by dataStore.language.collectAsState(initial = "English")
    if (page == "Home") {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(18.dp)
        ) {

            Text("Settings", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Text(
                "Manage your account and preferences.",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(Modifier.height(20.dp))

            SectionTitle("Account")

            SettingItem("♙", "Profile") { page = "Profile" }
            SettingItem("🔒", "Privacy & Security") {
                page = "Privacy"
            }

            SectionTitle("Preferences")

            SettingItem("🔔", "Notification") {
                page = "Notification"
            }

            SettingItem(
                "◎",
                "Language",
                savedLanguage
            ) {
                page = "Language"
            }

            SectionTitle("Accessibility")

            SettingItem("◉", "Accessibility Settings") {
                page = "Accessibility"
            }

            SectionTitle("Other")

            SettingItem("ⓘ", "About GluCare") {
                page = "About"
            }

            SettingItem("↪", "Log Out") {
                showLogout = true
            }
        }

        if (showLogout) {
            AlertDialog(
                onDismissRequest = { showLogout = false },
                title = { Text("Log Out") },
                text = { Text("Are you sure you want to log out?") },
                confirmButton = {
                    TextButton(onClick = { showLogout = false }) {
                        Text("Yes", color = green)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogout = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

    } else {

        when (page) {

            "Profile" -> ProfilePage(dataStore) { page = "Home" }

            "Privacy" -> PrivacyPage(dataStore) { page = "Home" }

            "Notification" -> NotificationPage(dataStore) { page = "Home" }

            "Language" -> LanguagePage(dataStore) { page = "Home" }

            "Accessibility" -> AccessibilitySettings(dataStore) {
                page = "Home"
            }

            "About" -> AboutPage { page = "Home" }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        title,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = dark,
        modifier = Modifier.padding(top = 15.dp, bottom = 7.dp)
    )
}

@Composable
fun SettingItem(
    icon: String,
    title: String,
    value: String = "",
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .background(pale, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(icon, fontSize = 17.sp)
        Spacer(Modifier.width(12.dp))

        Text(title, fontSize = 13.sp, modifier = Modifier.weight(1f))

        if (value.isNotEmpty()) {
            Text(value, fontSize = 11.sp, color = Color.Gray)
        }

        Text("›", fontSize = 20.sp)
    }
}

@Composable
fun PageTitle(title: String, onBack: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onBack() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            "←",
            fontSize = 28.sp,
            color = dark
        )

        Spacer(Modifier.width(15.dp))

        Text(
            title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ProfilePage(
    dataStore: GluCareDataStore,
    onBack: () -> Unit
) {
    val savedName by dataStore.userName.collectAsState(initial = "Zainab")
    val savedEmail by dataStore.userEmail.collectAsState(initial = "user@email.com")

    var name by remember { mutableStateOf("Zainab") }
    var email by remember { mutableStateOf("user@email.com") }

    val scope = rememberCoroutineScope()

    LaunchedEffect(savedName) {
        name = savedName
    }

    LaunchedEffect(savedEmail) {
        email = savedEmail
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(18.dp)
    ) {
        PageTitle("Profile", onBack)

        Text("Full Name", fontSize = 13.sp)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(15.dp))

        Text("Email Address", fontSize = 13.sp)

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        MainButton("Save Changes") {
            scope.launch {
                dataStore.saveProfile(name, email)
                onBack()
            }
        }
    }
}

@Composable
fun PrivacyPage(
    dataStore: GluCareDataStore,
    onBack: () -> Unit
) {
    val biometric by dataStore.biometricLogin.collectAsState(initial = false)
    val data by dataStore.shareUsageData.collectAsState(initial = true)

    val scope = rememberCoroutineScope()

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(18.dp)
    ) {
        PageTitle("Privacy & Security", onBack)

        ToggleItem("Biometric Login", biometric) {
            scope.launch {
                dataStore.savePrivacy(it, data)
            }
        }

        ToggleItem("Share Usage Data", data) {
            scope.launch {
                dataStore.savePrivacy(biometric, it)
            }
        }
    }
}
@Composable
fun NotificationPage(
    dataStore: GluCareDataStore,
    onBack: () -> Unit
) {
    val glucose by dataStore.glucoseNotification.collectAsState(initial = true)
    val medication by dataStore.medicationNotification.collectAsState(initial = true)
    val meals by dataStore.mealsNotification.collectAsState(initial = true)
    val activity by dataStore.activityNotification.collectAsState(initial = false)

    val scope = rememberCoroutineScope()

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(18.dp)
    ) {
        PageTitle("Notifications", onBack)

        ToggleItem("Blood Glucose Reminders", glucose) {
            scope.launch {
                dataStore.saveNotifications(
                    it, medication, meals, activity
                )
            }
        }

        ToggleItem("Medication Reminders", medication) {
            scope.launch {
                dataStore.saveNotifications(
                    glucose, it, meals, activity
                )
            }
        }

        ToggleItem("Meal Reminders", meals) {
            scope.launch {
                dataStore.saveNotifications(
                    glucose, medication, it, activity
                )
            }
        }

        ToggleItem("Activity Reminders", activity) {
            scope.launch {
                dataStore.saveNotifications(
                    glucose, medication, meals, it
                )
            }
        }
    }
}
@Composable
fun LanguagePage(
    dataStore: GluCareDataStore,
    onBack: () -> Unit
) {
    val savedLanguage by dataStore.language.collectAsState(initial = "English")
    var language by remember { mutableStateOf("English") }

    val scope = rememberCoroutineScope()

    LaunchedEffect(savedLanguage) {
        language = savedLanguage
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(18.dp)
    ) {
        PageTitle("Language", onBack)

        listOf("English", "العربية").forEach { option ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { language = option }
                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(option, modifier = Modifier.weight(1f))

                RadioButton(
                    selected = language == option,
                    onClick = { language = option }
                )
            }
        }

        MainButton("Save Language") {
            scope.launch {
                dataStore.saveLanguage(language)
                onBack()
            }
        }
    }
}

@Composable
fun AboutPage(onBack: () -> Unit) {

    Column(
        Modifier.fillMaxSize().background(Color.White).padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PageTitle("About GluCare", onBack)

        Spacer(Modifier.height(40.dp))

        Text("GluCare", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = green)

        Text(
            "Your health companion",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(Modifier.height(25.dp))

        Text(
            "GluCare helps users manage their glucose, medication, meals and daily health reminders.",
            fontSize = 14.sp
        )

        Spacer(Modifier.height(20.dp))

        Text("Version 1.0", color = Color.Gray)
    }
}

@Composable
fun AccessibilitySettings(
    dataStore: GluCareDataStore,
    onBack: () -> Unit
) {
    val savedTextSize by dataStore.textSize.collectAsState(initial = "Medium")
    val contrast by dataStore.highContrast.collectAsState(initial = false)
    val buttons by dataStore.largeButtons.collectAsState(initial = true)
    val voice by dataStore.voiceAssistance.collectAsState(initial = false)
    val motion by dataStore.reduceMotion.collectAsState(initial = false)

    val scope = rememberCoroutineScope()

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(18.dp)
    ) {
        PageTitle("Accessibility Settings", onBack)

        Text("Make the app easier to use for everyone.", color = Color.Gray)

        Spacer(Modifier.height(20.dp))

        Text("Text Size", fontWeight = FontWeight.Bold)

        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            listOf("Small", "Medium", "Large").forEach {
                Button(
                    onClick = {
                        scope.launch {
                            dataStore.saveAccessibility(
                                it, contrast, buttons, voice, motion
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (savedTextSize == it) green else pale,
                        contentColor = if (savedTextSize == it) Color.White else dark
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(it, fontSize = 10.sp)
                }
            }
        }

        Spacer(Modifier.height(15.dp))

        ToggleItem("High Contrast", contrast) {
            scope.launch {
                dataStore.saveAccessibility(
                    savedTextSize, it, buttons, voice, motion
                )
            }
        }

        ToggleItem("Large Buttons", buttons) {
            scope.launch {
                dataStore.saveAccessibility(
                    savedTextSize, contrast, it, voice, motion
                )
            }
        }

        ToggleItem("Voice Assistance", voice) {
            scope.launch {
                dataStore.saveAccessibility(
                    savedTextSize, contrast, buttons, it, motion
                )
            }
        }

        ToggleItem("Reduce Motion", motion) {
            scope.launch {
                dataStore.saveAccessibility(
                    savedTextSize, contrast, buttons, voice, it
                )
            }
        }

        Spacer(Modifier.height(15.dp))

        MainButton("Reset to Default") {
            scope.launch {
                dataStore.saveAccessibility(
                    "Medium", false, false, false, false
                )
            }
        }
    }
}
@Composable
fun ToggleItem(
    title: String,
    checked: Boolean,
    onChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 9.dp)
            .background(pale, RoundedCornerShape(10.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, modifier = Modifier.weight(1f), fontSize = 13.sp)

        Switch(
            checked = checked,
            onCheckedChange = onChange
        )
    }
}

@Composable
fun MainButton(title: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = green),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(title)
    }
}

