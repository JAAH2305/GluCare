package com.glucare.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.glucare.ui.screens.*
import androidx.compose.ui.unit.dp

object AppRoutes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val PROFILE = "profile"
    const val DIABETES = "diabetes"
    const val MEDICATIONS = "medications"
    const val MED_ADD = "medications/add"
    const val MED_DETAILS = "medications/details"
    const val MED_SCHEDULE = "medications/schedule"
    const val MED_REMINDER = "medications/reminder"
    const val MEALS = "meals"
    const val MEAL_FORM = "meals/form?mealId={mealId}"
    const val MEAL_DETAILS = "meals/details/{mealId}"
    const val MEAL_HISTORY = "meals/history"
    const val ACTIVITY = "activity"
    const val ACTIVITY_HISTORY = "activity/history"
    const val ACTIVITY_ADD = "activity/add"
    const val ACTIVITY_DETAILS = "activity/details/{activityId}"
    const val ACTIVITY_EDIT = "activity/edit/{activityId}"
    const val REMINDERS = "reminders"
    const val SETTINGS = "settings"
    const val CHARTS = "reports/charts"
    const val TRENDS = "reports/trends"
}

@Composable
fun AppNavigation() {
    val nav = rememberNavController()
    val context = androidx.compose.ui.platform.LocalContext.current
    val store = remember(context) { GluCareDataStore(context) }
    var readings by rememberSaveable(stateSaver = readingListSaver) { mutableStateOf(demoReadings()) }
    var nextReadingId by rememberSaveable { mutableLongStateOf(10L) }
    val activities = remember { mutableStateListOf<ActivityItem>() }
    var nextActivityId by remember { mutableLongStateOf(1L) }
    val open: (String) -> Unit = { nav.navigate(it) { launchSingleTop = true } }
    val home: () -> Unit = { nav.navigate(GlucoseRoutes.HOME) { popUpTo(GlucoseRoutes.HOME) { inclusive = true }; launchSingleTop = true } }
    val back: () -> Unit = { if (!nav.popBackStack()) home() }
    val tabs: (Int) -> Unit = { index ->
        when(index) {
            0 -> home()
            1 -> open(GlucoseRoutes.HISTORY)
            2 -> open(AppRoutes.MEDICATIONS)
            3 -> open(AppRoutes.MEALS)
            else -> open(AppRoutes.SETTINGS)
        }
    }
    val currentRoute = nav.currentBackStackEntryAsState().value?.destination?.route
    val authRoutes = setOf(AppRoutes.SPLASH, AppRoutes.LOGIN, AppRoutes.SIGNUP, AppRoutes.PROFILE, AppRoutes.DIABETES)
    Scaffold(bottomBar = {
        if (currentRoute !in authRoutes) {
            NavigationBar {
                listOf("Home", "Glucose", "Meds", "Meals", "More").forEachIndexed { index, label ->
                    NavigationBarItem(selected = when(index) {
                        0 -> currentRoute == GlucoseRoutes.HOME
                        1 -> currentRoute?.startsWith("glucare/glucose") == true
                        2 -> currentRoute?.startsWith("medications") == true
                        3 -> currentRoute?.startsWith("meals") == true
                        else -> currentRoute == "more" || currentRoute == AppRoutes.SETTINGS
                    }, onClick = { if (index == 4) open("more") else tabs(index) },
                    icon = { Text(listOf("⌂", "💧", "💊", "🍴", "•••")[index]) },
                    label = { Text(label) })
                }
            }
        }
    }) { padding ->
    NavHost(navController = nav, startDestination = AppRoutes.SPLASH, modifier = Modifier.padding(padding)) {
        composable(AppRoutes.SPLASH) { SplashScreen(onNavigateNext = { open(AppRoutes.LOGIN) }) }
        composable(AppRoutes.LOGIN) { LoginScreen(onNavigateToSignUp = { open(AppRoutes.SIGNUP) }, onLoginSuccess = home, onForgotPasswordClick = { open(AppRoutes.SETTINGS) }) }
        composable(AppRoutes.SIGNUP) { SignUpScreen(onNavigateToLogin = back, onSignUpSuccess = { open(AppRoutes.PROFILE) }) }
        composable(AppRoutes.PROFILE) { CreateProfileScreen(onBackClick = back, onNextClick = { open(AppRoutes.DIABETES) }) }
        composable(AppRoutes.DIABETES) { SelectDiabetesTypeScreen(onBackClick = back, onGetStartedClick = home) }
        glucoseDestinations(navController = nav, readings = { readings },
            onAdd = { value, timestamp, tag, notes ->
                val id = nextReadingId++
                readings = listOf(GlucoseReading(id, value, timestamp, tag, notes)) + readings
                id
            },
            onUpdate = { updated -> readings = readings.map { if (it.id == updated.id) updated else it } },
            onDelete = { id -> readings = readings.filterNot { it.id == id } },
            onMedication = { open(AppRoutes.MEDICATIONS) }, onMeals = { open(AppRoutes.MEALS) },
            onActivity = { open(AppRoutes.ACTIVITY) }, onReminders = { open(AppRoutes.REMINDERS) })
        composable(AppRoutes.MEDICATIONS) {
            MyMedicationsScreen(onAdd = { open(AppRoutes.MED_ADD) }, onDetails = { open(AppRoutes.MED_DETAILS) },
                onSchedule = { open(AppRoutes.MED_SCHEDULE) }, onHome = home,
                onGlucose = { open(GlucoseRoutes.HISTORY) }, onMore = { open(AppRoutes.SETTINGS) })
        }
        composable(AppRoutes.MED_ADD) { AddMedicationScreen(onBack = back, onSave = { open(AppRoutes.MEDICATIONS) }) }
        composable(AppRoutes.MED_DETAILS) { MedicationDetailsScreen(onBack = back, onEdit = { open(AppRoutes.MED_ADD) }, onDelete = back) }
        composable(AppRoutes.MED_SCHEDULE) { MedicationScheduleScreen(onBack = back) }
        composable(AppRoutes.MED_REMINDER) { MedicationReminderScreen(onBack = back, onSave = back) }
        composable(AppRoutes.MEALS) { MealsDashboardScreen(onBackClick = home,
            onNavigateToAddMeal = { open("meals/form?mealId=-1") },
            onNavigateToMealDetails = { open("meals/details/$it") },
            onNavigateToHistory = { open(AppRoutes.MEAL_HISTORY) }) }
        composable(AppRoutes.MEAL_FORM, arguments = listOf(navArgument("mealId") { type = NavType.LongType; defaultValue = -1L })) { entry ->
            val id = entry.arguments?.getLong("mealId")?.takeIf { it >= 0 }
            AddMealScreen(mealId = id, onBackClick = back, onSaveMealSuccess = { open("meals/details/$it") })
        }
        composable(AppRoutes.MEAL_DETAILS, arguments = listOf(navArgument("mealId") { type = NavType.LongType })) { entry ->
            val id = entry.arguments?.getLong("mealId") ?: 1L
            MealDetailsScreen(mealId = id, onBackClick = back,
                onEditClick = { open("meals/form?mealId=$it") }, onDoneClick = { open(AppRoutes.MEALS) })
        }
        composable(AppRoutes.MEAL_HISTORY) { DailyMealsHistoryScreen(onBackClick = back,
            onNavigateToAddMeal = { open("meals/form?mealId=-1") },
            onNavigateToMealDetails = { open("meals/details/$it") }) }
        composable(AppRoutes.ACTIVITY) {
            ActivityHomeScreen(activities = activities, totalSteps = activities.sumOf { it.steps },
                totalDurationMin = activities.sumOf { it.durationMinutes }, totalCalories = activities.sumOf { it.calories },
                onSelectBottomTab = tabs, onViewHistoryClick = { open(AppRoutes.ACTIVITY_HISTORY) },
                onActivityClick = { open("activity/details/${it.id}") }, onAddActivityClick = { open(AppRoutes.ACTIVITY_ADD) })
        }
        composable(AppRoutes.ACTIVITY_HISTORY) {
            ActivityHistoryScreen(activities = activities, onSelectBottomTab = tabs, onBackClick = back,
                onActivityClick = { open("activity/details/${it.id}") })
        }
        composable(AppRoutes.ACTIVITY_ADD) {
            AddActivityScreen(onBackClick = back, onSaveActivity = { item ->
                activities.add(item.copy(id = nextActivityId++)); open(AppRoutes.ACTIVITY)
            })
        }
        composable(AppRoutes.ACTIVITY_DETAILS, arguments = listOf(navArgument("activityId") { type = NavType.LongType })) { entry ->
            val item = activities.firstOrNull { it.id == entry.arguments?.getLong("activityId") }
            if (item != null) ActivityDetailsScreen(activity = item, onBackClick = back,
                onEditClick = { open("activity/edit/${it.id}") },
                onDeleteClick = { activities.removeAll { a -> a.id == it.id }; open(AppRoutes.ACTIVITY) })
            else Text("Activity not found")
        }
        composable(AppRoutes.ACTIVITY_EDIT, arguments = listOf(navArgument("activityId") { type = NavType.LongType })) { entry ->
            val item = activities.firstOrNull { it.id == entry.arguments?.getLong("activityId") }
            AddActivityScreen(initialActivity = item, onBackClick = back, onSaveActivity = { updated ->
                val index = activities.indexOfFirst { it.id == updated.id }
                if (index >= 0) activities[index] = updated
                open(AppRoutes.ACTIVITY)
            })
        }
        composable("more") {
            Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("More services", style = MaterialTheme.typography.headlineMedium)
                listOf("Activity Tracking" to AppRoutes.ACTIVITY,
                    "Reminders" to AppRoutes.REMINDERS,
                    "Medication Schedule" to AppRoutes.MED_SCHEDULE,
                    "Medication Reminder" to AppRoutes.MED_REMINDER,
                    "Health Charts" to AppRoutes.CHARTS,
                    "Glucose Trends" to AppRoutes.TRENDS,
                    "Settings" to AppRoutes.SETTINGS).forEach { (label, route) ->
                    OutlinedButton(onClick = { open(route) }, modifier = Modifier.fillMaxWidth()) { Text(label) }
                }
            }
        }
        composable(AppRoutes.REMINDERS) { Reminders(store) }
        composable(AppRoutes.SETTINGS) { Settings(store) }
        composable(AppRoutes.CHARTS) { HealthCharts() }
        composable(AppRoutes.TRENDS) { GlucoseTrends() }
    }
    }
}
