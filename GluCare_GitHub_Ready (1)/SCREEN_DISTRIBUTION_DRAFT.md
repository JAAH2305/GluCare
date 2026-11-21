# Milestone 3 — Screen distribution draft

| Screens | Owner/group | Routes | Work |
|---|---|---|---|
| Home, Add Glucose, Result, History, Details/Edit | Base glucose screens | `glucare/home`, `glucare/glucose/*` | Reuse glucose state and route graph |
| Splash, Login, Sign Up, Create Profile, Select Diabetes Type | Zahra Al-Muqbil | `splash`, `login`, `signup`, `profile`, `diabetes` | Integrate onboarding and shared components |
| Meals Dashboard, Add Meal, Meal Details, Daily Meals History | Hawra | `meals`, `meals/form`, `meals/details/{mealId}`, `meals/history` | Connect navigation; demo meal state |
| Activity Home, Add Activity, Details, History | Zainab Al-Sada | `activity`, `activity/add`, `activity/details/{activityId}`, `activity/history` | Shared activity state, edit/delete routes |
| My Medications, Add Medication, Details, Schedule, Medication Reminder | Malath | `medications`, `medications/add`, `medications/details`, `medications/schedule`, `medications/reminder` | Connect navigation; medication persistence still needed |
| Settings, Reminders, Health Charts, Glucose Trends | Zahra Al-Sada | `settings`, `reminders`, `reports/charts`, `reports/trends` | DataStore preference support; charts are placeholders |

Verify teammate spellings and screen ownership before submission. This is a draft, not a completed instructor template.
