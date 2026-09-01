package cm.sibcodelab.taskflow.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import cm.sibcodelab.taskflow.R

enum class BottomNavTab(
    val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    HOME(R.string.nav_home, Icons.Filled.Home, Icons.Outlined.Home),
    TASKS(R.string.nav_tasks, Icons.Filled.Checklist, Icons.Outlined.Checklist),
    CALENDAR(R.string.nav_calendar, Icons.Filled.CalendarMonth, Icons.Outlined.CalendarMonth),
    REPORTS(R.string.nav_reports, Icons.Filled.BarChart, Icons.Outlined.BarChart),
    PROFILE(R.string.nav_profile, Icons.Filled.Person, Icons.Outlined.Person)
}