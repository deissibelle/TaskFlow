package cm.sibcodelab.taskflow.presentation.taskform

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cm.sibcodelab.taskflow.R
import cm.sibcodelab.taskflow.domain.model.Priority
import cm.sibcodelab.taskflow.ui.theme.PriorityHigh
import cm.sibcodelab.taskflow.ui.theme.PriorityLow
import cm.sibcodelab.taskflow.ui.theme.PriorityMedium
import cm.sibcodelab.taskflow.ui.theme.TaskFlowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onSaveClick: (title: String, description: String, priority: Priority) -> Unit = { _, _, _ -> }
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedPriority by remember { mutableStateOf(Priority.MEDIUM) }

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.add_task_title),
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(
                        onClick = { onSaveClick(title, description, selectedPriority) },
                        enabled = title.isNotBlank()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = if (title.isNotBlank())
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                        )
                    }
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
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            FieldLabel(stringResource(R.string.add_task_title_label))
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text(stringResource(R.string.add_task_title_placeholder)) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            FieldLabel(stringResource(R.string.add_task_description_label))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text(stringResource(R.string.add_task_description_placeholder)) },
                minLines = 3,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            FieldLabel(stringResource(R.string.add_task_due_date_label))
            OutlinedTextField(
                value = "26 mai 2024",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { Icon(Icons.Filled.DateRange, contentDescription = null) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            FieldLabel(stringResource(R.string.add_task_priority_label))
            PrioritySelector(
                selected = selectedPriority,
                onSelect = { selectedPriority = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { onSaveClick(title, description, selectedPriority) },
                enabled = title.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(stringResource(R.string.add_task_save))
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun FieldLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PrioritySelector(
    selected: Priority,
    onSelect: (Priority) -> Unit
) {
    val options = listOf(
        Triple(Priority.LOW, stringResource(R.string.add_task_priority_low), PriorityLow),
        Triple(Priority.MEDIUM, stringResource(R.string.add_task_priority_medium), PriorityMedium),
        Triple(Priority.HIGH, stringResource(R.string.add_task_priority_high), PriorityHigh)
    )

    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
        options.forEachIndexed { index, (priority, label, color) ->
            SegmentedButton(
                selected = selected == priority,
                onClick = { onSelect(priority) },
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = color.copy(alpha = 0.15f),
                    activeContentColor = color,
                    activeBorderColor = color
                )
            ) {
                Text(label)
            }
        }
    }
}

@Preview
@Composable
private fun AddTaskScreenPreview() {
    TaskFlowTheme {
        AddTaskScreen()
    }
}