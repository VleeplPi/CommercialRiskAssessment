package com.labs.commercialriskassessment.presentation.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.labs.commercialriskassessment.presentation.components.ColumnChartComponent
import com.labs.commercialriskassessment.presentation.components.FormModalBottomSheet
import com.labs.commercialriskassessment.presentation.components.InitValueFormComponent
import com.labs.commercialriskassessment.presentation.components.SimpleTable
import com.labs.commercialriskassessment.presentation.theme.componentShapes
import com.labs.commercialriskassessment.presentation.theme.AccentLightGreenColor
import com.labs.commercialriskassessment.util.CommercialRiskCalculator
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.shape.toVicoShape
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.CartesianMarker

private val TAG = "MainScreen"


@Composable
fun MainScreen(){
    Log.d(TAG, "MAIN SCREEN")
    val showModalResult = remember {
        mutableStateOf(false)
    }
    val result = remember {
        mutableStateOf(listOf(""))
    }
    val headersPoolQuality: Array<String> = arrayOf("количество испорченного товара",
        "стоимость товара хорошего качества",
        "скидка на испорченный товар",)
    val resultPoolQuality = remember {
        mutableStateOf(
            arrayOf(
                doubleArrayOf(),
                doubleArrayOf(),
                doubleArrayOf(),
                doubleArrayOf()
            )
        )
    }
    val headersPrepaymentRisk = remember {
        mutableListOf(
            "размер предоплаты",
            "время до фактического получения товара",
            "время фактического получения товара",
            "коэффециент роста капитала"
        )
    }
    val resultPrepaymentRisk = remember {
        mutableStateOf(
            arrayOf(
                doubleArrayOf(),
                doubleArrayOf(),
                doubleArrayOf(),
                doubleArrayOf()
            )
        )
    }

    val riskValues = remember{
        mutableStateOf(listOf(0.0))
    }

    val riskLabels = remember{
        mutableStateOf(listOf(""))
    }

    InitValueFormComponent(
        onClickApplyBtn = {p,n,i,k1,k2,Npq,Pq,iq,m,np,delta, prepayment,t,dt,beta ->
            val commercialRiskCalculator: CommercialRiskCalculator = CommercialRiskCalculator(p,n,i,k1,k2, Npq,Pq,iq,m,np,delta,prepayment,t,dt,beta)
            result.value = commercialRiskCalculator.calculateRisk()
            riskValues.value = commercialRiskCalculator.getRiskValues()
            riskLabels.value = commercialRiskCalculator.getRiskLabels()
            showModalResult.value = true
        },
        onClickMonteCarloPrepaymentRisk= {prepayment_start,prepayment_end,t_start,t_end, dt_start, dt_end, beta_start, beta_end, countSimulation ->
            val commercialRiskCalculator: CommercialRiskCalculator = CommercialRiskCalculator()
            resultPrepaymentRisk.value = commercialRiskCalculator.monteCarloPrepaymentRisk(prepayment_start,prepayment_end,t_start,t_end, dt_start, dt_end, beta_start, beta_end, countSimulation)
            Log.d(TAG, "RESULT PREPAYMENT RISK: ${resultPrepaymentRisk.value[0].size}")
        },
        onClickMonteCarloPoolQualityRisk = {Npq_start,Npq_end,Pq_start,Pq_end,iq_start,iq_end,countSimulation, ->
            val commercialRiskCalculator: CommercialRiskCalculator = CommercialRiskCalculator()
            resultPoolQuality.value = commercialRiskCalculator.monteCarloRiskPoolQuality(Npq_start,Npq_end,Pq_start,Pq_end,iq_start,iq_end,countSimulation)
            Log.d(TAG, "resultPoolQuality: ${resultPoolQuality.value.size}")
        }

    )


    FormModalBottomSheet(
        openBottomSheet = showModalResult.value,
        setStateBottomSheet = { showModalResult.value = it},
    ){
        LazyColumn(
            userScrollEnabled = true,
            contentPadding = PaddingValues(vertical = 10.dp, horizontal = 5.dp),
            ) {
                items(result.value) { row ->
                    val splitedRow: List<String> = row.split(":")
                    Spacer(modifier = Modifier.padding(vertical=10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(2.dp, AccentLightGreenColor),
                                shape = componentShapes.medium
                            )
                    )
                    {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .fillMaxWidth()

                        )
                        {
                            Text(
                                splitedRow[0],
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                letterSpacing = 2.sp,
                                softWrap = true

                            )
                            Text(
                                splitedRow[1],
                                fontSize = 18.sp,
                                modifier = Modifier.padding(8.dp),
                                textAlign = TextAlign.Center

                            )
                        }
                    }
                }
            item{
                ColumnChartComponent(
                    labels = riskLabels.value,
                    values = riskValues.value
                )
            }
//            items(resultPrepaymentRisk.value.size){rowId ->
//                Text(
//                    modifier = Modifier
//                        .border(1.dp, Color.Black)
//                        .width(200.dp)
//                        .padding(5.dp),
//                    text = "${resultPrepaymentRisk.value[rowId]}",
//                    fontSize = 18.sp,
//                    textAlign = TextAlign.Start,
//                    letterSpacing = 2.sp
//                )
//            }
        }
    }
}