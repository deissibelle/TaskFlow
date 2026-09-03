package cm.sibcodelab.taskflow.presentation.reports

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cm.sibcodelab.taskflow.R
import cm.sibcodelab.taskflow.domain.model.BottomNavTab
import cm.sibcodelab.taskflow.domain.model.DayCompletion
import cm.sibcodelab.taskflow.domain.model.PrioritySlice
import cm.sibcodelab.taskflow.presentation.components.TaskFlowBottomBar
import cm.sibcodelab.taskflow.ui.theme.PriorityHigh
import cm.sibcodelab.taskflow.ui.theme.PriorityLow
import cm.sibcodelab.taskflow.ui.theme.PriorityMedium
import cm.sibcodelab.taskflow.ui.theme.TaskFlowTheme



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    percent: Int,
    delta: String,
    completedCount: Int,
    inProgressCount: Int,
    overdueCount: Int,
    weeklyData: List<DayCompletion>,
    priorityData: List<PrioritySlice>,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(BottomNavTab.HOME) }

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.reports_title), fontWeight = FontWeight.Bold)
                },
                actions = {
                    PeriodSelector()
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            SuccessRateCard(
                percent = percent,
                delta = delta,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                MiniStat(stringResource(R.string.reports_completed), completedCount.toString(), MaterialTheme.colorScheme.primary, Modifier.weight(1f))
                MiniStat(stringResource(R.string.reports_in_progress), inProgressCount.toString(), PriorityMedium, Modifier.weight(1f))
                MiniStat(stringResource(R.string.reports_overdue), overdueCount.toString(), PriorityHigh, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))

            SectionCard(title = stringResource(R.string.reports_completed_by_day)) {
                BarChart(weeklyData)
            }

            Spacer(modifier = Modifier.height(16.dp))

            SectionCard(title = stringResource(R.string.reports_priority_distribution)) {
                DonutChartWithLegend(priorityData)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SuccessRateCard(percent: Int, delta: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(20.dp))
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = stringResource(R.string.reports_success_rate),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "$percent%",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = delta,
                style = MaterialTheme.typography.labelMedium,
                color = PriorityLow,
                fontWeight = FontWeight.Medium
            )
        }

        CircularProgress(percent = percent)
    }
}
@Composable
private fun CircularProgress(percent: Int) {
    val progressColor = MaterialTheme.colorScheme.primary
    val trackColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.15f)

    Canvas(modifier = Modifier.size(90.dp)) {
        val strokeWidth = 10.dp.toPx()
        drawArc(
            color = trackColor,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            size = Size(size.width - strokeWidth, size.height - strokeWidth),
            topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
        )
        drawArc(
            color = progressColor,
            startAngle = -90f,
            sweepAngle = 360f * (percent / 100f),
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            size = Size(size.width - strokeWidth, size.height - strokeWidth),
            topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
        )
    }
}

@Composable
private fun MiniStat(label: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = color)
        Spacer(modifier = Modifier.height(2.dp))
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun SectionCard(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))
        content()
    }
}

@Composable
private fun PeriodSelector() {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf(
        stringResource(R.string.reports_period),
        "Ce mois",
        "Cette année"
    )
    var selected by remember { mutableStateOf(options[0]) }

    Box {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { expanded = true }
                .padding(end = 16.dp)
        ) {
            Text(
                text = selected,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selected = option
                        expanded = false
                    }
                )
            }
        }
    }
}
@Composable
private fun BarChart(data: List<DayCompletion>) {
    val maxValue = (data.maxOfOrNull { it.count } ?: 1).coerceAtLeast(1)
    val topScale = (kotlin.math.ceil(maxValue / 5.0) * 5).toInt().coerceAtLeast(5)
    val yLabels = (topScale downTo 0 step 5).toList() // ex: [15, 10, 5, 0]
    val chartHeight = 110.dp
    val barColor = MaterialTheme.colorScheme.primary
    val gridColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.15f)

    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.height(chartHeight),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                yLabels.forEach { value ->
                    Text(
                        text = value.toString(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(chartHeight)
            ) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    val stepY = size.height / (yLabels.size - 1)
                    yLabels.indices.forEach { i ->
                        val y = stepY * i
                        drawLine(
                            color = gridColor,
                            start = androidx.compose.ui.geometry.Offset(0f, y),
                            end = androidx.compose.ui.geometry.Offset(size.width, y),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxSize()
                ) {
                    data.forEach { day ->
                        val ratio = (day.count / topScale.toFloat()).coerceIn(0.02f, 1f)
                        Box(
                            modifier = Modifier
                                .width(18.dp)
                                .fillMaxHeight(fraction = ratio)
                                .background(
                                    brush = Brush.verticalGradient(
                                        listOf(barColor, barColor.copy(alpha = 0.5f))
                                    ),
                                    shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                                )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 28.dp)
        ) {
            data.forEach { day ->
                Text(
                    text = day.label,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}@Composable
private fun DonutChartWithLegend(slices: List<PrioritySlice>) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Canvas(modifier = Modifier.size(100.dp)) {
            val strokeWidth = 20.dp.toPx()
            var startAngle = -90f
            slices.forEach { slice ->
                val sweep = 360f * (slice.percent / 100f)
                drawArc(
                    color = slice.color,
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = false,
                    style = Stroke(width = strokeWidth),
                    size = Size(size.width - strokeWidth, size.height - strokeWidth),
                    topLeft = androidx.compose.ui.geometry.Offset(strokeWidth / 2, strokeWidth / 2)
                )
                startAngle += sweep
            }
        }

        Spacer(modifier = Modifier.width(28.dp))

        // Légende : liste verticale à droite du donut
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            slices.forEach { slice ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(slice.color, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = slice.label,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.width(70.dp)
                    )
                    Text(
                        text = "${slice.percent}%",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ReportsScreenPreview() {
    TaskFlowTheme {
        ReportsScreen(
            percent = 78,
            completedCount = 24,
            inProgressCount = 8,
            overdueCount = 3,
            weeklyData = listOf(
                DayCompletion("Lun", 4),
                DayCompletion("Mar", 7),
                DayCompletion("Mer", 3),
                DayCompletion("Jeu", 9),
                DayCompletion("Ven", 5),
                DayCompletion("Sam", 8),
                DayCompletion("Dim", 2)
            ),
            priorityData = listOf(
                PrioritySlice("Haute", 35, PriorityHigh),
                PrioritySlice("Moyenne", 45, PriorityMedium),
                PrioritySlice("Basse", 20, PriorityLow)
            ),
            delta = stringResource(R.string.reports_delta),

        )
    }
}