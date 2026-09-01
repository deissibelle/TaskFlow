package cm.sibcodelab.taskflow.presentation.tasklist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cm.sibcodelab.taskflow.R
import cm.sibcodelab.taskflow.domain.model.BottomNavTab
import cm.sibcodelab.taskflow.domain.model.Priority
import cm.sibcodelab.taskflow.domain.model.Task
import cm.sibcodelab.taskflow.presentation.components.TaskFlowBottomBar
import cm.sibcodelab.taskflow.ui.theme.TaskFlowTheme

private enum class TaskFilter(val labelRes: Int) {
    ALL(R.string.tasks_filter_all),
    ACTIVE(R.string.tasks_filter_active),
    DONE(R.string.tasks_filter_done)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    tasks: List<Task>,
    modifier: Modifier = Modifier,
    onAddTaskClick: () -> Unit = {},
    onTaskCheckedChange: (Task, Boolean) -> Unit = { _, _ -> }
) {
    var selectedFilter by remember { mutableStateOf(TaskFilter.ALL) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(BottomNavTab.TASKS) }

    val filteredTasks = tasks
        .filter { task ->
            when (selectedFilter) {
                TaskFilter.ALL -> true
                TaskFilter.ACTIVE -> !task.isCompleted
                TaskFilter.DONE -> task.isCompleted
            }
        }
        .filter { task ->
            searchQuery.isBlank() || task.title.contains(searchQuery, ignoreCase = true)
        }

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.tasks_title),
                        fontWeight = FontWeight.Bold
                    )
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TabRow(
                selectedTabIndex = selectedFilter.ordinal,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                TaskFilter.entries.forEach { filter ->
                    Tab(
                        selected = filter == selectedFilter,
                        onClick = { selectedFilter = filter },
                        text = { Text(stringResource(filter.labelRes)) }
                    )
                }
            }

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text(stringResource(R.string.tasks_search_placeholder)) },
                singleLine = true,
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                trailingIcon = { Icon(Icons.Filled.FilterList, contentDescription = null) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(filteredTasks, key = { it.id }) { task ->
                    TaskItem(
                        task = task,
                        onCheckedChange = { checked -> onTaskCheckedChange(task, checked) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TaskListScreenPreview() {
    TaskFlowTheme {
        TaskListScreen(
            tasks = listOf(
                Task(1, "Finir le rapport de stage", "Détails du rapport", Priority.HIGH),
                Task(2, "Réunion avec l'équipe", "", Priority.MEDIUM),
                Task(3, "Réviser le cours de Kotlin", "", Priority.LOW, isCompleted = true),
                Task(4, "Acheter des livres", "", Priority.MEDIUM),
                Task(5, "Planifier le sprint", "", Priority.LOW)
            )
        )
    }
}