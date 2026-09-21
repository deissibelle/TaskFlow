package cm.sibcodelab.taskflow.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import cm.sibcodelab.taskflow.domain.model.BottomNavTab
import cm.sibcodelab.taskflow.domain.model.DayCompletion
import cm.sibcodelab.taskflow.domain.model.HomeStats
import cm.sibcodelab.taskflow.domain.model.Priority
import cm.sibcodelab.taskflow.domain.model.PrioritySlice
import cm.sibcodelab.taskflow.domain.model.ReminderItem
import cm.sibcodelab.taskflow.domain.model.Task
import cm.sibcodelab.taskflow.presentation.auth.LoginScreen
import cm.sibcodelab.taskflow.presentation.auth.RegisterScreen
import cm.sibcodelab.taskflow.presentation.calendar.CalendarScreen
import cm.sibcodelab.taskflow.presentation.home.HomeScreen
import cm.sibcodelab.taskflow.presentation.onboarding.OnboardingScreen
import cm.sibcodelab.taskflow.presentation.profile.ProfileScreen
import cm.sibcodelab.taskflow.presentation.reminders.RemindersScreen
import cm.sibcodelab.taskflow.presentation.reports.CategoryBreakdown
import cm.sibcodelab.taskflow.presentation.reports.DetailedStatsScreen
import cm.sibcodelab.taskflow.presentation.reports.ReportsScreen
import cm.sibcodelab.taskflow.presentation.settings.SettingsScreen
import cm.sibcodelab.taskflow.presentation.taskform.AddTaskScreen
import cm.sibcodelab.taskflow.presentation.tasklist.TaskDetailScreen
import cm.sibcodelab.taskflow.presentation.tasklist.TaskListScreen
import cm.sibcodelab.taskflow.ui.theme.PriorityHigh
import cm.sibcodelab.taskflow.ui.theme.PriorityLow
import cm.sibcodelab.taskflow.ui.theme.PriorityMedium
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cm.sibcodelab.taskflow.presentation.viewmodel.TaskViewModel

private fun BottomNavTab.toScreen(): Screen = when (this) {
    BottomNavTab.HOME -> Screen.Home
    BottomNavTab.TASKS -> Screen.TaskList
    BottomNavTab.CALENDAR -> Screen.Calendar
    BottomNavTab.REPORTS -> Screen.Reports
    BottomNavTab.PROFILE -> Screen.Profile
}

private fun NavBackStack<NavKey>.switchToTab(tab: BottomNavTab) {
    clear()
    add(tab.toScreen())
}

@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(Screen.Onboarding)
    val taskViewModel: TaskViewModel = viewModel()
    val tasks by taskViewModel.tasks.collectAsStateWithLifecycle()

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Screen.Onboarding> {
                OnboardingScreen(
                    onGetStartedClick = { backStack.add(Screen.Register) },
                    onLoginClick = { backStack.add(Screen.Login) }
                )
            }

            entry<Screen.Login> {
                LoginScreen(
                    onLoginClick = { _, _ -> backStack.switchToTab(BottomNavTab.HOME) },
                    onSignUpClick = { backStack.add(Screen.Register) }
                )
            }

            entry<Screen.Register> {
                RegisterScreen(
                    onRegisterClick = { _, _, _ -> backStack.switchToTab(BottomNavTab.HOME) },
                    onLoginClick = { backStack.add(Screen.Login) }
                )
            }

            entry<Screen.Home> {
                HomeScreen(
                    userName = "Sibelle",
                    stats = HomeStats(
                        total = tasks.size,
                        completed = tasks.count { it.isCompleted },
                        overdue = 0 // vrai calcul viendra avec les vraies dates, Phase 9
                    ),
                    todayTasks = tasks.take(3),
                    selectedTab = BottomNavTab.HOME,
                    onTabSelected = { backStack.switchToTab(it) },
                    onAddTaskClick = { backStack.add(Screen.AddTask) },
                    onTaskCheckedChange = { task, checked -> taskViewModel.toggleTaskCompletion(task, checked) },
                    onNotificationsClick = { backStack.add(Screen.Reminders) }
                )
            }

            entry<Screen.TaskList> {
                TaskListScreen(
                    tasks = tasks,
                    selectedTab = BottomNavTab.TASKS,
                    onTabSelected = { backStack.switchToTab(it) },
                    onAddTaskClick = { backStack.add(Screen.AddTask) },
                    onTaskClick = { task -> backStack.add(Screen.TaskDetail(task)) },
                    onTaskCheckedChange = { task, checked -> taskViewModel.toggleTaskCompletion(task, checked) }
                )
            }

            entry<Screen.Calendar> {
                CalendarScreen(
                    tasksByDay = mapOf(26 to tasks.take(2), 28 to tasks.takeLast(1)),
                    selectedTab = BottomNavTab.CALENDAR,
                    onTabSelected = { backStack.switchToTab(it) },
                    onAddTaskClick = { backStack.add(Screen.AddTask) },
                    onTaskCheckedChange = { task, checked -> taskViewModel.toggleTaskCompletion(task, checked) }
                )
            }

            entry<Screen.AddTask> {
                AddTaskScreen(
                    onBackClick = { backStack.removeLastOrNull() },
                    onSaveClick = { title, description, priority ->
                        taskViewModel.addTask(title, description, priority)
                        backStack.removeLastOrNull()
                    }
                )
            }

            entry<Screen.TaskDetail> { key ->
                TaskDetailScreen(
                    task = key.task,
                    onBackClick = { backStack.removeLastOrNull() },
                    onCheckedChange = { checked -> taskViewModel.toggleTaskCompletion(key.task, checked) },
                    onDeleteClick = {
                        taskViewModel.deleteTask(key.task)
                        backStack.removeLastOrNull()
                    }
                )
            }

            entry<Screen.Reminders> {
                RemindersScreen(
                    reminders = listOf(
                        ReminderItem(tasks[0], "13:30", "Aujourd'hui"),
                        ReminderItem(tasks[1], "16:30", "Aujourd'hui")
                    ),
                    onBackClick = { backStack.removeLastOrNull() }
                )
            }
            entry<Screen.Settings> {
                SettingsScreen()
            }

            entry<Screen.DetailedStats> {
                DetailedStatsScreen(
                    productivityPercent = 78,
                    weeklyProductivity = listOf(65, 70, 55, 80, 75, 90, 78),
                    timeSpent = "18h 30m",
                    tasksCreated = 32,
                    tasksCreatedDelta = "+8 vs semaine dernière",
                    categories = listOf(
                        CategoryBreakdown("Études", 12, 37, PriorityMedium),
                        CategoryBreakdown("Travail", 10, 31, PriorityHigh),
                        CategoryBreakdown("Personnel", 6, 19, PriorityLow)
                    ),
                    onBackClick = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}