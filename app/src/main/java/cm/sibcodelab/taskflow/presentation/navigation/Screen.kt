package cm.sibcodelab.taskflow.presentation.navigation

import androidx.navigation3.runtime.NavKey
import cm.sibcodelab.taskflow.domain.model.Task
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen : NavKey {
    @Serializable data object Onboarding : Screen
    @Serializable data object Login : Screen
    @Serializable data object Register : Screen

    // Écrans "racine" — un par onglet de la bottom bar
    @Serializable data object Home : Screen
    @Serializable data object TaskList : Screen
    @Serializable data object Calendar : Screen
    @Serializable data object Reports : Screen
    @Serializable data object Profile : Screen

    // Écrans secondaires — empilés par-dessus un écran racine
    @Serializable data object AddTask : Screen
    @Serializable data class TaskDetail(val task: Task) : Screen
    @Serializable data object Reminders : Screen
    @Serializable data object Settings : Screen
    @Serializable data object DetailedStats : Screen
}