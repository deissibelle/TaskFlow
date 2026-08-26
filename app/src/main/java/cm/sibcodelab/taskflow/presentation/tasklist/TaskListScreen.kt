package cm.sibcodelab.taskflow.presentation.tasklist


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cm.sibcodelab.taskflow.domain.model.Priority
import cm.sibcodelab.taskflow.domain.model.Task

@Composable
fun TaskListScreen(    modifier: Modifier = Modifier
) {

    var tasks by remember {
        mutableStateOf(
            listOf(
                Task(1, "Apprendre Kotlin", "Comprendre les data classes et Compose", Priority.HIGH),
                Task(2, "Faire les courses", "Lait, pain, œufs", Priority.LOW, isCompleted = true),
                Task(3, "Réviser Compose", "State hoisting", Priority.MEDIUM)
            )
        )
    }

    LazyColumn {
        items(tasks, key = { it.id }) { task ->
            TaskItem(
                task = task,
                onCheckedChange = { checked ->
                    tasks = tasks.map {
                        if (it.id == task.id) it.copy(isCompleted = checked) else it
                    }
                }
            )
        }
    }
}