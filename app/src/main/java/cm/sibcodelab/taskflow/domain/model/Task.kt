package cm.sibcodelab.taskflow.domain.model


data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val priority: Priority,
    val isCompleted: Boolean = false,
    val dueDate: Long? = null
)

enum class Priority {
    LOW,
    MEDIUM,
    HIGH
}
