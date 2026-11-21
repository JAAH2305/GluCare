package com.glucare.navigation

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.glucare.ui.screens.*
import com.glucare.ui.theme.GlucoseColors as Palette

object GlucoseRoutes {
    const val HOME = "glucare/home"
    const val ADD = "glucare/glucose/add"
    const val HISTORY = "glucare/glucose/history"
    internal const val READING_ID = "readingId"
    const val RESULT = "glucare/glucose/result/{readingId}"
    const val DETAILS = "glucare/glucose/details/{readingId}"
    const val EDIT = "glucare/glucose/edit/{readingId}"

    fun result(id: Long): String = "glucare/glucose/result/$id"
    fun details(id: Long): String = "glucare/glucose/details/$id"
    fun edit(id: Long): String = "glucare/glucose/edit/$id"
}

val readingListSaver = listSaver<List<GlucoseReading>, Bundle>(
    save = { readings ->
        readings.map { reading ->
            Bundle().apply {
                putLong("id", reading.id)
                putInt("value", reading.value)
                putLong("timestamp", reading.timestamp)
                putString("tag", reading.tag)
                putString("notes", reading.notes)
            }
        }
    },
    restore = { entries ->
        entries.map { entry ->
            GlucoseReading(
                entry.getLong("id"), entry.getInt("value"), entry.getLong("timestamp"),
                entry.getString("tag"), entry.getString("notes").orEmpty(),
            )
        }
    },
)

@Composable
fun GluCareGlucoseApp(
    modifier: Modifier = Modifier,
    onMedication: (() -> Unit)? = null,
    onMeals: (() -> Unit)? = null,
    onActivity: (() -> Unit)? = null,
    onReminders: (() -> Unit)? = null,
    onMore: (() -> Unit)? = null,
) {
    val navController = rememberNavController()
    var readings by rememberSaveable(stateSaver = readingListSaver) { mutableStateOf(demoReadings()) }
    var nextId by rememberSaveable { mutableLongStateOf(10L) }
    val entry by navController.currentBackStackEntryAsState()

    val graphBuilder: NavGraphBuilder.() -> Unit = remember(
        navController, onMedication, onMeals, onActivity, onReminders,
    ) {
        {
            glucoseDestinations(
                navController = navController,
                readings = { readings },
                onAdd = { value, timestamp, tag, notes ->
                    val id = nextId++
                    readings = listOf(GlucoseReading(id, value, timestamp, tag, notes)) + readings
                    id
                },
                onUpdate = { updated -> readings = readings.map { if (it.id == updated.id) updated else it } },
                onDelete = { id -> readings = readings.filterNot { it.id == id } },
                onMedication = onMedication,
                onMeals = onMeals,
                onActivity = onActivity,
                onReminders = onReminders,
            )
        }
    }

    Scaffold(
        modifier = modifier,
        containerColor = Palette.Background,
        bottomBar = {
            GlucoseBottomBar(
                onHomePage = entry?.destination?.route == GlucoseRoutes.HOME,
                onHome = { navController.openTopLevel(GlucoseRoutes.HOME) },
                onHistory = { navController.openTopLevel(GlucoseRoutes.HISTORY) },
                onMedication = onMedication,
                onMeals = onMeals,
                onMore = onMore,
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = GlucoseRoutes.HOME,
            modifier = Modifier.padding(innerPadding).consumeWindowInsets(innerPadding).imePadding(),
            builder = graphBuilder,
        )
    }
}

fun NavGraphBuilder.glucoseDestinations(
    navController: NavHostController,
    readings: () -> List<GlucoseReading>,
    onAdd: (Int, Long, String?, String) -> Long,
    onUpdate: (GlucoseReading) -> Unit,
    onDelete: (Long) -> Unit,
    onMedication: (() -> Unit)? = null,
    onMeals: (() -> Unit)? = null,
    onActivity: (() -> Unit)? = null,
    onReminders: (() -> Unit)? = null,
) {
    val goBack: () -> Unit = {
        if (!navController.popBackStack()) navController.openTopLevel(GlucoseRoutes.HOME)
    }
    val openDetails: (Long) -> Unit = { navController.navigate(GlucoseRoutes.details(it)) }
    val readingArguments = listOf(navArgument(GlucoseRoutes.READING_ID) { type = NavType.LongType })

    composable(GlucoseRoutes.HOME) {
        HomeDashboardScreen(
            latestReading = readings().maxWithOrNull(compareBy<GlucoseReading> { it.timestamp }.thenBy { it.id }),
            onLogGlucose = { navController.navigate(GlucoseRoutes.ADD) },
            onOpenReading = openDetails,
            onMedication = onMedication,
            onMeals = onMeals,
            onActivity = onActivity,
            onReminders = onReminders,
        )
    }
    composable(GlucoseRoutes.ADD) {
        AddGlucoseReadingScreen(onBack = goBack, onSave = { value, timestamp, tag, notes ->
            val id = onAdd(value, timestamp, tag, notes)
            navController.navigate(GlucoseRoutes.result(id)) {
                popUpTo(GlucoseRoutes.ADD) { inclusive = true }
                launchSingleTop = true
            }
        })
    }
    composable(GlucoseRoutes.RESULT, arguments = readingArguments) { entry ->
        val id = entry.arguments?.getLong(GlucoseRoutes.READING_ID)
        val reading = readings().find { it.id == id }
        if (reading == null) {
            MissingReading(goBack)
        } else {
            ReadingResultScreen(
                reading = reading,
                onBack = goBack,
                onDone = { navController.openTopLevel(GlucoseRoutes.HOME) },
                onViewHistory = { navController.openTopLevel(GlucoseRoutes.HISTORY) },
            )
        }
    }
    composable(GlucoseRoutes.HISTORY) {
        GlucoseHistoryScreen(readings(), goBack, openDetails)
    }
    composable(GlucoseRoutes.DETAILS, arguments = readingArguments) { entry ->
        val id = entry.arguments?.getLong(GlucoseRoutes.READING_ID)
        val reading = readings().find { it.id == id }
        if (reading == null) {
            MissingReading(goBack)
        } else {
            ReadingDetailsScreen(
                reading, goBack,
                onEdit = { navController.navigate(GlucoseRoutes.edit(reading.id)) },
                onDelete = {
                    onDelete(reading.id)
                    goBack()
                },
            )
        }
    }
    composable(GlucoseRoutes.EDIT, arguments = readingArguments) { entry ->
        val id = entry.arguments?.getLong(GlucoseRoutes.READING_ID)
        val reading = readings().find { it.id == id }
        if (reading == null) {
            MissingReading(goBack)
        } else {
            AddGlucoseReadingScreen(
                onBack = goBack,
                onSave = { value, timestamp, tag, notes ->
                    onUpdate(reading.copy(value = value, timestamp = timestamp, tag = tag, notes = notes))
                    goBack()
                },
                existing = reading,
            )
        }
    }
}

private fun NavHostController.openTopLevel(route: String) {
    navigate(route) {
        popUpTo(GlucoseRoutes.HOME)
        launchSingleTop = true
    }
}

@Composable
private fun MissingReading(onBack: () -> Unit) {
    GlucosePage(title = "Reading unavailable", onBack = onBack) {
        SupportingText("This reading could not be found.")
    }
}

@Composable
private fun GlucoseBottomBar(
    onHomePage: Boolean,
    onHome: () -> Unit,
    onHistory: () -> Unit,
    onMedication: (() -> Unit)?,
    onMeals: (() -> Unit)?,
    onMore: (() -> Unit)?,
) {
    Surface(color = Palette.Sage) {
        Row(
            Modifier.fillMaxWidth().navigationBarsPadding().padding(horizontal = 5.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BottomTab("⌂", "Home", onHomePage, onHome, Modifier.weight(1f))
            BottomTab("💧", "Glucose", !onHomePage, onHistory, Modifier.weight(1f))
            BottomTab("💊", "Medication", false, onMedication, Modifier.weight(1.25f))
            BottomTab("🍴", "Meals", false, onMeals, Modifier.weight(1f))
            BottomTab("•••", "More", false, onMore, Modifier.weight(1f))
        }
    }
}

@Composable
private fun BottomTab(
    icon: String, label: String, selected: Boolean, onClick: (() -> Unit)?, modifier: Modifier,
) {
    TextButton(
        onClick = { onClick?.invoke() },
        enabled = onClick != null,
        modifier = modifier.heightIn(min = 48.dp),
        contentPadding = PaddingValues(2.dp),
        colors = ButtonDefaults.textButtonColors(
            contentColor = if (selected) Palette.Accent else Palette.Ink,
            disabledContentColor = Palette.SecondaryText,
        ),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(icon, fontSize = 13.sp)
            Text(label, fontSize = 10.sp)
        }
    }
}
