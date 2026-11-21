package com.glucare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.glucare.navigation.AppNavigation
import com.glucare.ui.theme.GlucoGuideLogInTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { GlucoGuideLogInTheme { AppNavigation() } }
    }
}
