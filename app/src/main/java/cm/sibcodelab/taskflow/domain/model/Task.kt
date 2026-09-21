package cm.sibcodelab.taskflow.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val priority: Priority,
    val isCompleted: Boolean = false,
    val dueDate: Long? = null
)

@Serializable
enum class Priority {
    LOW,
    MEDIUM,
    HIGH
}