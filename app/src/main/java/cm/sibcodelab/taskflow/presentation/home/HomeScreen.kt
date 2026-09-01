package cm.sibcodelab.taskflow.presentation.home


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cm.sibcodelab.taskflow.R
import cm.sibcodelab.taskflow.domain.model.BottomNavTab
import cm.sibcodelab.taskflow.domain.model.HomeStats
import cm.sibcodelab.taskflow.domain.model.Priority
import cm.sibcodelab.taskflow.domain.model.Task
import cm.sibcodelab.taskflow.presentation.components.TaskFlowBottomBar
import cm.sibcodelab.taskflow.ui.theme.PriorityHigh
import cm.sibcodelab.taskflow.ui.theme.PriorityLow
import cm.sibcodelab.taskflow.ui.theme.PriorityMedium
import cm.sibcodelab.taskflow.ui.theme.TaskFlowTheme



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier=Modifier,
    userName: String,
    stats: HomeStats,
    todayTasks: List<Task>,
    onAddTaskClick: () -> Unit = {},
    onSeeAllClick: () -> Unit = {},
    onTaskCheckedChange: (Task, Boolean) -> Unit = { _, _ -> },
    onMenuClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(BottomNavTab.HOME) }

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = onNotificationsClick) {
                        Icon(Icons.Filled.Notifications, contentDescription = null)
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTaskClick,
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = null, tint = Color.White)
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(vertical = 24.dp)
        ) {
            item {
                Text(
                    text = stringResource(R.string.home_greeting, userName),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(R.string.home_subtitle),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    item {
                        StatCard(
                            value = stats.total.toString(),
                            label = stringResource(R.string.home_stat_total),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    item {
                        StatCard(
                            value = stats.completed.toString(),
                            label = stringResource(R.string.home_stat_completed),
                            color = PriorityLow
                        )
                    }
                    item {
                        StatCard(
                            value = stats.overdue.toString(),
                            label = stringResource(R.string.home_stat_overdue),
                            color = PriorityHigh
                        )
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.home_today_section),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = stringResource(R.string.home_see_all),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable(onClick = onSeeAllClick)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            items(todayTasks, key = { it.id }) { task ->
                HomeTaskRow(
                    task = task,
                    onCheckedChange = { checked -> onTaskCheckedChange(task, checked) }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun StatCard(value: String, label: String, color: Color) {
    Column(
        modifier = Modifier
            .width(98.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            color = color,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 2
        )
    }
}

@Composable
private fun HomeTaskRow(
    task: Task,
    onCheckedChange: (Boolean) -> Unit
) {
    val priorityColor = when (task.priority) {
        Priority.HIGH -> PriorityHigh
        Priority.MEDIUM -> PriorityMedium
        Priority.LOW -> PriorityLow
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(14.dp))
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = task.isCompleted, onCheckedChange = onCheckedChange)

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = task.title,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
            )
        }

        Box(
            modifier = Modifier
                .background(priorityColor.copy(alpha = 0.15f), CircleShape)
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                text = task.priority.name.lowercase(),
                style = MaterialTheme.typography.labelSmall,
                color = priorityColor
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    TaskFlowTheme {
        HomeScreen(
            userName = "Sibelle",
            stats = HomeStats(total = 12, completed = 5, overdue = 3),
            todayTasks = listOf(
                Task(1, "Finir le rapport de stage", "", Priority.HIGH),
                Task(2, "Réunion avec l'équipe", "", Priority.MEDIUM),
                Task(3, "Réviser le cours de Kotlin", "", Priority.LOW, isCompleted = true)
            )
        )
    }
}