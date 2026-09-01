package cm.sibcodelab.taskflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import cm.sibcodelab.taskflow.domain.model.HomeStats
import cm.sibcodelab.taskflow.domain.model.Priority
import cm.sibcodelab.taskflow.domain.model.Task
import cm.sibcodelab.taskflow.presentation.auth.LoginScreen
import cm.sibcodelab.taskflow.presentation.auth.RegisterScreen
import cm.sibcodelab.taskflow.presentation.home.HomeScreen
import cm.sibcodelab.taskflow.presentation.onboarding.AppIntroScreen
import cm.sibcodelab.taskflow.presentation.onboarding.OnboardingScreen
import cm.sibcodelab.taskflow.ui.theme.TaskFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskFlowTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { padding ->
                      HomeScreen (
                          modifier = Modifier.padding(padding),
                          userName = "Sibelle",
                          stats = HomeStats(total = 12, completed = 5, overdue = 3),
                          todayTasks = listOf(
                              Task(1, "Finir le rapport de stage", "", Priority.HIGH),
                              Task(2, "Réunion avec l'équipe", "", Priority.MEDIUM),
                              Task(3, "Réviser le cours de Kotlin", "", Priority.LOW, isCompleted = true),
                          )
                      )
                }
            }
        }
    }
}