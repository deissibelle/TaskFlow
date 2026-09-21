package cm.sibcodelab.taskflow.presentation.viewmodel


import androidx.lifecycle.ViewModel
import cm.sibcodelab.taskflow.domain.model.Priority
import cm.sibcodelab.taskflow.domain.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskViewModel : ViewModel() {

    private val _tasks = MutableStateFlow(
        listOf(
            Task(1, "Finir le rapport de stage", "Détails du rapport", Priority.HIGH),
            Task(2, "Réunion avec l'équipe", "", Priority.MEDIUM),
            Task(3, "Réviser le cours de Kotlin", "", Priority.LOW, isCompleted = true),
            Task(4, "Acheter des livres", "", Priority.MEDIUM),
            Task(5, "Planifier le sprint", "", Priority.LOW)
        )
    )
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    fun toggleTaskCompletion(task: Task, isCompleted: Boolean) {
        _tasks.value = _tasks.value.map {
            if (it.id == task.id) it.copy(isCompleted = isCompleted) else it
        }
    }

    fun addTask(title: String, description: String, priority: Priority) {
        val newTask = Task(
            id = (_tasks.value.maxOfOrNull { it.id } ?: 0) + 1,
            title = title,
            description = description,
            priority = priority
        )
        _tasks.value = _tasks.value + newTask
    }

    fun deleteTask(task: Task) {
        _tasks.value = _tasks.value.filter { it.id != task.id }
    }
}