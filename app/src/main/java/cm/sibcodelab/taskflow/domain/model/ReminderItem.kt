package cm.sibcodelab.taskflow.domain.model

data class ReminderItem(
    val task: Task,
    val time: String,
    val sectionLabel: String
)
