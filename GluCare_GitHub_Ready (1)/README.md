# GluCare — Integrated Milestone 3 Prototype

Single Android Studio project combining glucose, onboarding, meals, activity, medications, reminders, reports and settings.

## Open

1. Extract the ZIP. Open **GluCare_M3B_Screens** (the folder containing `settings.gradle.kts`) in Android Studio.
2. Use JDK 17 and install Android SDK 35. Allow Gradle to sync and download dependencies.
3. Run the `app` configuration. The launcher opens Splash, then Login. Sign Up opens the two-step profile setup; Login enters the Home dashboard.
4. The shared bottom navigation opens Home, Glucose, Medications, Meals and More. More contains Activity, Reminders, Medication Schedule, Medication Reminder, Health Charts, Glucose Trends and Settings.

## Source organization

- `com.glucare.MainActivity`: the **only Android launcher activity**.
- `navigation/AppNavigation.kt`: shared navigation graph, entry flow and in-memory activity/glucose state.
- `navigation/GlucoseNavigation.kt`: reusable glucose destination graph and reading routes.
- `ui/screens`: each teammate's screen as a separate Kotlin source file.
- `ui/components`: shared onboarding components (including reconstructed missing GlucoGuideButton).
- `ui/theme`: onboarding colors/typography plus existing glucose palette.
- `ui/screens/GluCareDataStore.kt`: preference persistence for settings and reminder records.

## Scope and limitations

This is an **integrated UI prototype**, not a validated clinical app. Navigation is wired for the major flows, and activity and glucose records use app-local state. The medication screens and some meal screens still contain sample/hardcoded data: medication Save/Edit/Delete callbacks navigate but do **not** persist medication records, and meal form save is still demo behavior. The medication reminder screen does not schedule Android notifications; the separate general Reminders screen persists reminder settings. Reports/charts use placeholders/sample values. Login/Sign Up are UI flows, not real authentication. Do not use the demo data for health decisions.

**Build status:** source was organized and checked statically, but Android SDK/Gradle runtime was not available in the authoring environment; an actual clean build on Android Studio is still required. Fix any reported Gradle sync/compile issues before submission.

**Submission:** verify the app runs from a clean clone, check all Milestone 2 screens, remove any remaining unfinished code, push to GitHub, tag the final commit `milestone-3`, and give the instructor repository access. Do not claim these submission steps are complete until performed.
