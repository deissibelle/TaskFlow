package cm.sibcodelab.taskflow.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cm.sibcodelab.taskflow.R
import cm.sibcodelab.taskflow.ui.theme.TaskFlowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onThemeClick: () -> Unit = {},
    onLanguageClick: () -> Unit = {},
    onBackupClick: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.settings_title), fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            SettingsSection(title = stringResource(R.string.settings_general)) {
                SettingsRow(
                    icon = Icons.Filled.DarkMode,
                    label = stringResource(R.string.settings_theme),
                    modifier = Modifier.clickable(onClick = onThemeClick)
                ) {
                    TrailingValueChevron(stringResource(R.string.settings_theme_dark))
                }
                SettingsRow(
                    icon = Icons.Filled.Language,
                    label = stringResource(R.string.settings_language),
                    modifier = Modifier.clickable(onClick = onLanguageClick)
                ) {
                    TrailingValueChevron(stringResource(R.string.settings_language_value))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            var remindersEnabled by remember { mutableStateOf(true) }
            var soundsEnabled by remember { mutableStateOf(true) }
            var vibrationsEnabled by remember { mutableStateOf(true) }

            SettingsSection(title = stringResource(R.string.settings_notifications)) {
                SettingsRow(icon = Icons.Filled.Notifications, label = stringResource(R.string.settings_reminders)) {
                    Switch(checked = remindersEnabled, onCheckedChange = { remindersEnabled = it })
                }
                SettingsRow(icon = Icons.Filled.VolumeUp, label = stringResource(R.string.settings_sounds)) {
                    Switch(checked = soundsEnabled, onCheckedChange = { soundsEnabled = it })
                }
                SettingsRow(icon = Icons.Filled.Vibration, label = stringResource(R.string.settings_vibrations)) {
                    Switch(checked = vibrationsEnabled, onCheckedChange = { vibrationsEnabled = it })
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            SettingsSection(title = stringResource(R.string.settings_data)) {
                SettingsRow(
                    icon = Icons.Filled.CloudUpload,
                    label = stringResource(R.string.settings_backup),
                    subtitle = stringResource(R.string.settings_backup_subtitle),
                    modifier = Modifier.clickable(onClick = onBackupClick)
                ) {
                    Icon(Icons.Filled.ChevronRight, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
    )
    Spacer(modifier = Modifier.height(8.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(16.dp))
    ) {
        content()
    }
}

@Composable
private fun SettingsRow(
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    trailing: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }
        }
        trailing()
    }
}

@Composable
private fun TrailingValueChevron(value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        )
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        )
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    TaskFlowTheme {
        SettingsScreen()
    }
}