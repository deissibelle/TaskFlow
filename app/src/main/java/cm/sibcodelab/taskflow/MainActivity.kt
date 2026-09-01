package cm.sibcodelab.taskflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cm.sibcodelab.taskflow.domain.model.HomeStats
import cm.sibcodelab.taskflow.domain.model.Priority
import cm.sibcodelab.taskflow.domain.model.Task
import cm.sibcodelab.taskflow.presentation.home.HomeScreen
import cm.sibcodelab.taskflow.presentation.taskform.AddTaskScreen
import cm.sibcodelab.taskflow.presentation.tasklist.TaskListScreen
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
                TaskListScreen(
                    tasks = listOf(
                        Task(1, "Finir le rapport de stage", "Détails du rapport", Priority.HIGH),
                        Task(2, "Réunion avec l'équipe", "", Priority.MEDIUM),
                        Task(3, "Réviser le cours de Kotlin", "", Priority.LOW, isCompleted = true),
                        Task(4, "Acheter des livres", "", Priority.MEDIUM),
                        Task(5, "Planifier le sprint", "", Priority.LOW)
                    )
                )
//                AddTaskScreen()
            }
        }
    }
}