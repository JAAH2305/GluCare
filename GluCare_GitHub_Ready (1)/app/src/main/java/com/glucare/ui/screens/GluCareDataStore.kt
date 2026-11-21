package com.glucare.ui.screens

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject
private val Context.dataStore by preferencesDataStore(name = "glucare_settings")

data class ReminderItem(
    val id: Int,
    val icon: String,
    val name: String,
    val time: String,
    val repeat: String,
    val days: String = "",
    val reminderBefore: String = "At time of reminder",
    val enabled: Boolean = true
)
class GluCareDataStore(private val context: Context) {

    companion object {
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val LANGUAGE = stringPreferencesKey("language")

        private val GLUCOSE = booleanPreferencesKey("notification_glucose")
        private val MEDICATION = booleanPreferencesKey("notification_medication")
        private val MEALS = booleanPreferencesKey("notification_meals")
        private val ACTIVITY = booleanPreferencesKey("notification_activity")
        private val BIOMETRIC = booleanPreferencesKey("biometric_login")
        private val SHARE_DATA = booleanPreferencesKey("share_usage_data")
        private val TEXT_SIZE = stringPreferencesKey("text_size")
        private val HIGH_CONTRAST = booleanPreferencesKey("high_contrast")
        private val LARGE_BUTTONS = booleanPreferencesKey("large_buttons")
        private val VOICE_ASSISTANCE = booleanPreferencesKey("voice_assistance")
        private val REDUCE_MOTION = booleanPreferencesKey("reduce_motion")
    }

    val userName: Flow<String> = context.dataStore.data.map {
        it[USER_NAME] ?: "Zainab"
    }

    val userEmail: Flow<String> = context.dataStore.data.map {
        it[USER_EMAIL] ?: "user@email.com"
    }

    val language: Flow<String> = context.dataStore.data.map {
        it[LANGUAGE] ?: "English"
    }

    val glucoseNotification: Flow<Boolean> = context.dataStore.data.map {
        it[GLUCOSE] ?: true
    }

    val medicationNotification: Flow<Boolean> = context.dataStore.data.map {
        it[MEDICATION] ?: true
    }

    val mealsNotification: Flow<Boolean> = context.dataStore.data.map {
        it[MEALS] ?: true
    }

    val activityNotification: Flow<Boolean> = context.dataStore.data.map {
        it[ACTIVITY] ?: false
    }
    val biometricLogin: Flow<Boolean> = context.dataStore.data.map {
        it[BIOMETRIC] ?: false
    }

    val shareUsageData: Flow<Boolean> = context.dataStore.data.map {
        it[SHARE_DATA] ?: true
    }
    val textSize: Flow<String> = context.dataStore.data.map {
        it[TEXT_SIZE] ?: "Medium"
    }

    val highContrast: Flow<Boolean> = context.dataStore.data.map {
        it[HIGH_CONTRAST] ?: false
    }

    val largeButtons: Flow<Boolean> = context.dataStore.data.map {
        it[LARGE_BUTTONS] ?: true
    }

    val voiceAssistance: Flow<Boolean> = context.dataStore.data.map {
        it[VOICE_ASSISTANCE] ?: false
    }

    val reduceMotion: Flow<Boolean> = context.dataStore.data.map {
        it[REDUCE_MOTION] ?: false
    }
    suspend fun saveProfile(name: String, email: String) {
        context.dataStore.edit {
            it[USER_NAME] = name
            it[USER_EMAIL] = email
        }
    }

    suspend fun saveLanguage(language: String) {
        context.dataStore.edit {
            it[LANGUAGE] = language
        }
    }

    suspend fun saveNotifications(
        glucose: Boolean,
        medication: Boolean,
        meals: Boolean,
        activity: Boolean
    ){
        context.dataStore.edit {
            it[GLUCOSE] = glucose
            it[MEDICATION] = medication
            it[MEALS] = meals
            it[ACTIVITY] = activity
        }
    }
    suspend fun savePrivacy(
        biometric: Boolean,
        shareData: Boolean
    ) {
        context.dataStore.edit {
            it[BIOMETRIC] = biometric
            it[SHARE_DATA] = shareData
        }
    }
    suspend fun saveAccessibility(
        textSize: String,
        highContrast: Boolean,
        largeButtons: Boolean,
        voiceAssistance: Boolean,
        reduceMotion: Boolean
    ) {
        context.dataStore.edit {
            it[TEXT_SIZE] = textSize
            it[HIGH_CONTRAST] = highContrast
            it[LARGE_BUTTONS] = largeButtons
            it[VOICE_ASSISTANCE] = voiceAssistance
            it[REDUCE_MOTION] = reduceMotion
        }
    }
    val reminders: Flow<List<ReminderItem>> =
        context.dataStore.data.map { preferences ->

            val json = preferences[stringPreferencesKey("reminders_list")]
                ?: return@map listOf(
                    ReminderItem(1, "💧", "Check Blood Glucose", "12:00 PM", "Every day"),
                    ReminderItem(2, "💊", "Take Medication", "12:00 PM", "Every day"),
                    ReminderItem(3, "🍴", "Log Your Meal", "12:00 PM", "Every day"),
                    ReminderItem(4, "🏃", "Physical Activity", "12:00 PM", "Every day")
                )

            try {
                val array = JSONArray(json)

                (0 until array.length()).map { index ->
                    val item = array.getJSONObject(index)

                    ReminderItem(
                        id = item.getInt("id"),
                        icon = item.getString("icon"),
                        name = item.getString("name"),
                        time = item.getString("time"),
                        repeat = item.getString("repeat"),
                        days = item.optString("days", ""),
                        reminderBefore = item.optString("reminderBefore", "At time of reminder"),
                        enabled = item.optBoolean("enabled", true)
                    )
                }
            } catch (e: Exception) {
                emptyList()
            }
        }

    suspend fun saveReminders(reminders: List<ReminderItem>) {
        val array = JSONArray()

        reminders.forEach { reminder ->
            array.put(
                JSONObject().apply {
                    put("id", reminder.id)
                    put("icon", reminder.icon)
                    put("name", reminder.name)
                    put("time", reminder.time)
                    put("repeat", reminder.repeat)
                    put("days", reminder.days)
                    put("reminderBefore", reminder.reminderBefore)
                    put("enabled", reminder.enabled)
                }
            )
        }

        context.dataStore.edit {
            it[stringPreferencesKey("reminders_list")] = array.toString()
        }
    }
}