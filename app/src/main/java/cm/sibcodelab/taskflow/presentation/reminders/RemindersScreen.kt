package cm.sibcodelab.taskflow.presentation.reminders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cm.sibcodelab.taskflow.R
import cm.sibcodelab.taskflow.domain.model.BottomNavTab
import cm.sibcodelab.taskflow.domain.model.Priority
import cm.sibcodelab.taskflow.domain.model.ReminderItem
import cm.sibcodelab.taskflow.domain.model.Task
import cm.sibcodelab.taskflow.presentation.components.TaskFlowBottomBar
import cm.sibcodelab.taskflow.ui.theme.PriorityHigh
import cm.sibcodelab.taskflow.ui.theme.PriorityMedium
import cm.sibcodelab.taskflow.ui.theme.TaskFlowTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(
    reminders: List<ReminderItem>,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    var remindersEnabled by remember { mutableStateOf(true) }
    val groupedReminders = reminders.groupBy { it.sectionLabel }
    var selectedTab by remember { mutableStateOf(BottomNavTab.HOME) }


    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.reminders_title), fontWeight = FontWeight.SemiBold)
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            TaskFlowBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(14.dp))
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.reminders_enable),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Switch(
                        checked = remindersEnabled,
                        onCheckedChange = { remindersEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.primary,
                            checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                        )
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            groupedReminders.forEach { (sectionLabel, items) ->
                item {
                    Text(
                        text = sectionLabel,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                items(items, key = { it.task.id }) { reminder ->
                    ReminderRow(reminder)
                    Spacer(modifier = Modifier.height(10.dp))
                }

                item { Spacer(modifier = Modifier.height(10.dp)) }
            }
        }
    }
}

@Composable
private fun ReminderRow(reminder: ReminderItem) {
    val priorityColor = when (reminder.task.priority) {
        Priority.HIGH -> PriorityHigh
        Priority.MEDIUM -> PriorityMedium
        Priority.LOW -> androidx.compose.ui.graphics.Color(0xFF4ADE80)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(14.dp))
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(priorityColor.copy(alpha = 0.15f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = null,
                tint = priorityColor,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = reminder.task.title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = reminder.time,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }

        Box(
            modifier = Modifier
                .background(priorityColor.copy(alpha = 0.15f), CircleShape)
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                text = reminder.task.priority.name.lowercase(),
                style = MaterialTheme.typography.labelSmall,
                color = priorityColor
            )
        }
    }
}

@Preview
@Composable
private fun RemindersScreenPreview() {
    TaskFlowTheme {
        RemindersScreen(
            reminders = listOf(
                ReminderItem(Task(1, "Finir le rapport de stage", "", Priority.HIGH), "13:30", "Aujourd'hui"),
                ReminderItem(Task(2, "Réunion avec l'équipe", "", Priority.MEDIUM), "16:30", "Aujourd'hui"),
                ReminderItem(Task(3, "Appeler le client", "", Priority.HIGH), "10:00", "Demain")
            )
        )
    }
}