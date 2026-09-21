package cm.sibcodelab.taskflow.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cm.sibcodelab.taskflow.R
import cm.sibcodelab.taskflow.domain.model.BottomNavTab
import cm.sibcodelab.taskflow.presentation.components.SettingsNavRow
import cm.sibcodelab.taskflow.presentation.components.SettingsSection
import cm.sibcodelab.taskflow.presentation.components.TaskFlowBottomBar
import cm.sibcodelab.taskflow.ui.theme.TaskFlowTheme

@Composable
fun ProfileScreen(
    userName: String,
    userEmail: String,
    completedTasks: Int,
    productiveDays: Int,
    currentStreak: Int,
    modifier: Modifier = Modifier,
    selectedTab: BottomNavTab = BottomNavTab.PROFILE,
    onTabSelected: (BottomNavTab) -> Unit = {},
    onCategoriesClick: () -> Unit = {},
    onStatisticsClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onAboutClick: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            TaskFlowBottomBar(selectedTab = selectedTab, onTabSelected = onTabSelected)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .size(88.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(44.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = userName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = userEmail,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                ProfileStat(completedTasks.toString(), stringResource(R.string.profile_completed_tasks), Modifier.weight(1f))
                ProfileStat(productiveDays.toString(), stringResource(R.string.profile_productive_days), Modifier.weight(1f))
                ProfileStat(currentStreak.toString(), stringResource(R.string.profile_current_streak), Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(28.dp))

            SettingsSection(title = "") {
                SettingsNavRow(Icons.Filled.Category, stringResource(R.string.profile_categories), onClick = onCategoriesClick)
                SettingsNavRow(Icons.Filled.QueryStats, stringResource(R.string.profile_statistics), onClick = onStatisticsClick)
                SettingsNavRow(Icons.Filled.Settings, stringResource(R.string.profile_settings), onClick = onSettingsClick)
                SettingsNavRow(Icons.Filled.Info, stringResource(R.string.profile_about), onClick = onAboutClick)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ProfileStat(value: String, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(14.dp))
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(2.dp))
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    TaskFlowTheme {
        ProfileScreen(
            userName = "Sibelle Djumeghe",
            userEmail = "sibelle.djumeghe@gmail.com",
            completedTasks = 142,
            productiveDays = 28,
            currentStreak = 11
        )
    }
}