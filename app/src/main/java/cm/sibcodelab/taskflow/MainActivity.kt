package cm.sibcodelab.taskflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.stringResource
import cm.sibcodelab.taskflow.domain.model.DayCompletion
import cm.sibcodelab.taskflow.domain.model.PrioritySlice
import cm.sibcodelab.taskflow.presentation.reports.CategoryBreakdown
import cm.sibcodelab.taskflow.presentation.reports.DetailedStatsScreen
import cm.sibcodelab.taskflow.presentation.reports.ReportsScreen
import cm.sibcodelab.taskflow.presentation.settings.SettingsScreen
import cm.sibcodelab.taskflow.ui.theme.PriorityHigh
import cm.sibcodelab.taskflow.ui.theme.PriorityLow
import cm.sibcodelab.taskflow.ui.theme.PriorityMedium
import cm.sibcodelab.taskflow.ui.theme.TaskFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskFlowTheme {
//                HomeScreen(
//                    userName = "Sibelle",
//                    stats = HomeStats(total = 12, completed = 5, overdue = 3),
//                    todayTasks = listOf(
//                        Task(1, "Finir le rapport de stage", "", Priority.HIGH),
//                        Task(2, "Réunion avec l'équipe", "", Priority.MEDIUM),
//                        Task(3, "Réviser le cours de Kotlin", "", Priority.LOW, isCompleted = true),
//                    )
//                )
//                TaskListScreen(
//                    tasks = listOf(
//                        Task(1, "Finir le rapport de stage", "Détails du rapport", Priority.HIGH),
//                        Task(2, "Réunion avec l'équipe", "", Priority.MEDIUM),
//                        Task(3, "Réviser le cours de Kotlin", "", Priority.LOW, isCompleted = true),
//                        Task(4, "Acheter des livres", "", Priority.MEDIUM),
//                        Task(5, "Planifier le sprint", "", Priority.LOW)
//                    )
//                )
//                AddTaskScreen()
//
//                TaskDetailScreen(
//                    task = Task(
//                        id = 1,
//                        title = "Finir le rapport de stage",
//                        description = "Finaliser toutes les sections et préparer la soutenance.",
//                        priority = Priority.HIGH
//                    )
//                )
//                ReportsScreen(
//                    percent = 78,
//                    completedCount = 24,
//                    inProgressCount = 8,
//                    overdueCount = 3,
//                    weeklyData = listOf(
//                        DayCompletion("Lun", 4),
//                        DayCompletion("Mar", 7),
//                        DayCompletion("Mer", 3),
//                        DayCompletion("Jeu", 9),
//                        DayCompletion("Ven", 5),
//                        DayCompletion("Sam", 8),
//                        DayCompletion("Dim", 2)
//                    ),
//                    priorityData = listOf(
//                        PrioritySlice("Haute", 35, PriorityHigh),
//                        PrioritySlice("Moyenne", 45, PriorityMedium),
//                        PrioritySlice("Basse", 20, PriorityLow)
//                    ),
//                    delta = stringResource(R.string.reports_delta),
//                )
//                DetailedStatsScreen(
//                    productivityPercent = 78,
//                    weeklyProductivity = listOf(65, 70, 55, 80, 75, 90, 78),
//                    timeSpent = "18h 30m",
//                    tasksCreated = 32,
//                    tasksCreatedDelta = "+8 vs semaine dernière",
//                    categories = listOf(
//                        CategoryBreakdown("Études", 12, 37, cm.sibcodelab.taskflow.ui.theme.PriorityMedium),
//                        CategoryBreakdown("Travail", 10, 31, MaterialTheme.colorScheme.primary),
//                        CategoryBreakdown("Personnel", 6, 19, PriorityLow)
//                    )
//                )
                SettingsScreen()

            }
        }
    }
}