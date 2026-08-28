package cm.sibcodelab.taskflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import cm.sibcodelab.taskflow.presentation.auth.LoginScreen
import cm.sibcodelab.taskflow.presentation.auth.RegisterScreen
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
                    RegisterScreen(modifier = Modifier.padding(padding))

                }
            }
        }
    }
}