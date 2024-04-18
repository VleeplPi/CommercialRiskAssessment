package com.labs.commercialriskassessment.presentation.components

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.labs.commercialriskassessment.presentation.theme.AccentLightGreenColor
import com.labs.commercialriskassessment.presentation.theme.componentShapes
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.shape.toVicoShape
import com.patrykandpatrick.vico.core.cartesian.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.data.ExtraStore

private val TAG = "ChartComponents"
@Composable
fun ColumnChartComponent(labels: List<String>, values:List<Double>){
    val columnChartColors = listOf(AccentLightGreenColor)
    val labelListKey = ExtraStore.Key<List<String>>()
    val modelProducer = remember { CartesianChartModelProducer.build() }

    LaunchedEffect(Unit) {
        modelProducer.tryRunTransaction {
            columnSeries {
                series(
                    y = values,
//                    x = listOf(0,2,4,6)
                )
            }
            updateExtras { it[labelListKey] = labels}

        }
    }

    CartesianChartHost(
        rememberCartesianChart(
            rememberColumnCartesianLayer(
                columnProvider =
                ColumnCartesianLayer.ColumnProvider.series(
                    columnChartColors.map { color ->
                        rememberLineComponent(
                            color = color,
                            thickness = 8.dp,
                            shape = componentShapes.medium.toVicoShape()

                        )
                    },
                ),
            ),
            startAxis = rememberStartAxis(),
            bottomAxis = rememberBottomAxis(
                labelRotationDegrees = 0.9f,
                valueFormatter =CartesianValueFormatter { x, chartValues, _ ->

                    chartValues.model.extraStore[labelListKey][x.toInt()]
                }
            ),
        ),
        modelProducer,
    )
}