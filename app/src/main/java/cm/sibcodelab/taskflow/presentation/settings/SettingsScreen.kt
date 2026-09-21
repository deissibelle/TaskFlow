package cm.sibcodelab.taskflow.presentation.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Vibration
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
import cm.sibcodelab.taskflow.presentation.components.SettingsNavRow
import cm.sibcodelab.taskflow.presentation.components.SettingsSection
import cm.sibcodelab.taskflow.ui.theme.TaskFlowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onThemeClick: () -> Unit = {},
    onLanguageClick: () -> Unit = {},
    onBackupClick: () -> Unit = {}
) {
    var remindersEnabled by remember { mutableStateOf(true) }
    var soundsEnabled by remember { mutableStateOf(true) }
    var vibrationsEnabled by remember { mutableStateOf(true) }

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
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            SettingsSection(title = stringResource(R.string.settings_general)) {
                SettingsNavRow(
                    icon = Icons.Filled.DarkMode,
                    label = stringResource(R.string.settings_theme),
                    value = stringResource(R.string.settings_theme_value),
                    onClick = onThemeClick
                )
                SettingsNavRow(
                    icon = Icons.Filled.Language,
                    label = stringResource(R.string.settings_language),
                    value = stringResource(R.string.settings_language_value),
                    onClick = onLanguageClick
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            SettingsSection(title = stringResource(R.string.settings_notifications)) {
                SettingsSwitchRow(
                    icon = Icons.Filled.Notifications,
                    label = stringResource(R.string.settings_reminders),
                    checked = remindersEnabled,
                    onCheckedChange = { remindersEnabled = it }
                )
                SettingsSwitchRow(
                    icon = Icons.Filled.VolumeUp,
                    label = stringResource(R.string.settings_sounds),
                    checked = soundsEnabled,
                    onCheckedChange = { soundsEnabled = it }
                )
                SettingsSwitchRow(
                    icon = Icons.Filled.Vibration,
                    label = stringResource(R.string.settings_vibrations),
                    checked = vibrationsEnabled,
                    onCheckedChange = { vibrationsEnabled = it }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            SettingsSection(title = stringResource(R.string.settings_data)) {
                SettingsNavRow(
                    icon = Icons.Filled.CloudUpload,
                    label = stringResource(R.string.settings_backup),
                    value = stringResource(R.string.settings_backup_value),
                    onClick = onBackupClick
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SettingsSwitchRow(
    icon: ImageVector,
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
            )
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