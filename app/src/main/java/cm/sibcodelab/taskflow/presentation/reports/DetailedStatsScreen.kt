package cm.sibcodelab.taskflow.presentation.reports

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cm.sibcodelab.taskflow.R
import cm.sibcodelab.taskflow.ui.theme.PriorityLow
import cm.sibcodelab.taskflow.ui.theme.TaskFlowTheme

data class CategoryBreakdown(val name: String, val count: Int, val percent: Int, val color: Color)
private val chartWeekDays = listOf("Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim")


private enum class StatsPeriod(val labelRes: Int) {
    WEEK(R.string.stats_period_week),
    MONTH(R.string.stats_period_month),
    YEAR(R.string.stats_period_year)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedStatsScreen(
    productivityPercent: Int,
    weeklyProductivity: List<Int>,
    timeSpent: String,
    tasksCreated: Int,
    tasksCreatedDelta: String,
    categories: List<CategoryBreakdown>,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    var selectedPeriod by remember { mutableStateOf(StatsPeriod.WEEK) }

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.stats_title), fontWeight = FontWeight.SemiBold)
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            TabRow(
                selectedTabIndex = selectedPeriod.ordinal,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                StatsPeriod.entries.forEach { period ->
                    Tab(
                        selected = period == selectedPeriod,
                        onClick = { selectedPeriod = period },
                        text = { Text(stringResource(period.labelRes)) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            ProductivityCard(productivityPercent, weeklyProductivity)

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SimpleStatCard(
                    label = stringResource(R.string.stats_time_spent),
                    value = timeSpent,
                    delta = tasksCreatedDelta,
                    modifier = Modifier.weight(1f)
                )
                SimpleStatCard(
                    label = stringResource(R.string.stats_tasks_created),
                    value = tasksCreated.toString(),
                    delta = tasksCreatedDelta,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.stats_categories),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                categories.forEach { category ->
                    CategoryBar(category)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ProductivityCard(percent: Int, weeklyData: List<Int>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(20.dp))
            .padding(20.dp)
    ) {
        Text(
            text = stringResource(R.string.stats_global_productivity),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "$percent%",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = stringResource(R.string.stats_status_good),
            style = MaterialTheme.typography.labelMedium,
            color = PriorityLow
        )

        Spacer(modifier = Modifier.height(16.dp))

        LineChart(weeklyData)
    }
}

@Composable
private fun LineChart(data: List<Int>) {
    val lineColor = MaterialTheme.colorScheme.primary
    val gridColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f)
    val labelColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)

    Row(modifier = Modifier.fillMaxWidth()) {
        // Axe Y
        Column(
            modifier = Modifier.height(120.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            listOf(100, 75, 50, 25, 0).forEach { value ->
                Text("$value%", style = MaterialTheme.typography.labelSmall, color = labelColor)
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                repeat(5) { i ->
                    val y = size.height * i / 4
                    drawLine(gridColor, Offset(0f, y), Offset(size.width, y), strokeWidth = 1.dp.toPx())
                }

                if (data.isEmpty()) return@Canvas

                val maxValue = 100f
                val stepX = size.width / (data.size - 1).coerceAtLeast(1)

                val points = data.mapIndexed { index, value ->
                    Offset(
                        x = index * stepX,
                        y = size.height - (value / maxValue) * size.height
                    )
                }

                // Zone remplie sous la courbe (dégradé)
                val fillPath = Path().apply {
                    moveTo(points.first().x, size.height)
                    points.forEach { point -> lineTo(point.x, point.y) }
                    lineTo(points.last().x, size.height)
                    close()
                }
                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(lineColor.copy(alpha = 0.3f), Color.Transparent)
                    )
                )

                // Ligne de la courbe
                val linePath = Path().apply {
                    moveTo(points.first().x, points.first().y)
                    points.drop(1).forEach { point -> lineTo(point.x, point.y) }
                }
                drawPath(path = linePath, color = lineColor, style = Stroke(width = 3.dp.toPx()))

                points.forEach { point ->
                    drawCircle(color = lineColor, radius = 4.dp.toPx(), center = point)
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Axe X
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                chartWeekDays.forEach { day ->
                    Text(day, style = MaterialTheme.typography.labelSmall, color = labelColor)
                }
            }
        }
    }
}

@Composable
private fun SimpleStatCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    delta: String? = null
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
        if (delta != null) {
            Text(delta, style = MaterialTheme.typography.labelSmall, color = PriorityLow)
        }
    }
}

@Composable
private fun CategoryBar(category: CategoryBreakdown) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "${category.count} (${category.percent}%)",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(4.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = category.percent / 100f)
                    .fillMaxHeight()
                    .background(category.color, RoundedCornerShape(4.dp))
            )
        }
    }
}

@Preview
@Composable
private fun DetailedStatsScreenPreview() {
    TaskFlowTheme {
        DetailedStatsScreen(
            productivityPercent = 78,
            weeklyProductivity = listOf(65, 70, 55, 80, 75, 90, 78),
            timeSpent = "18h 30m",
            tasksCreated = 32,
            tasksCreatedDelta = "+8 vs semaine dernière",
            categories = listOf(
                CategoryBreakdown("Études", 12, 37, cm.sibcodelab.taskflow.ui.theme.PriorityMedium),
                CategoryBreakdown("Travail", 10, 31, MaterialTheme.colorScheme.primary),
                CategoryBreakdown("Personnel", 6, 19, PriorityLow)
            )
        )
    }
}