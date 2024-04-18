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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.labs.commercialriskassessment.presentation.components.FormModalBottomSheet
import com.labs.commercialriskassessment.presentation.components.InitValueFormComponent
import com.labs.commercialriskassessment.presentation.components.TablePrimaryComponent
import com.labs.commercialriskassessment.presentation.theme.componentShapes
import com.labs.commercialriskassessment.presentation.theme.AccentLightGreenColor
import com.labs.commercialriskassessment.util.CommercialRiskCalculator

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
    val headersPoolQuality = remember {
        mutableListOf(
            "количество испорченного товара",
            "стоимость товара хорошего качества",
            "скидка на испорченный товар",
        )
    }
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
    InitValueFormComponent(
        onClickApplyBtn = {p,n,i,k1,k2,Npq,Pq,iq,m,np,delta, prepayment,t,dt,beta ->
            val commercialRiskCalculator: CommercialRiskCalculator = CommercialRiskCalculator(p,n,i,k1,k2, Npq,Pq,iq,m,np,delta,prepayment,t,dt,beta)
            result.value = commercialRiskCalculator.calculateRisk()
            showModalResult.value = true
        },
        onClickMonteCarloPrepaymentRisk= {prepayment_start,prepayment_end,t_start,t_end, dt_start, dt_end, beta_start, beta_end, countSimulation ->
            val commercialRiskCalculator: CommercialRiskCalculator = CommercialRiskCalculator()
            resultPrepaymentRisk.value = commercialRiskCalculator.monteCarloPrepaymentRisk(prepayment_start,prepayment_end,t_start,t_end, dt_start, dt_end, beta_start, beta_end, countSimulation)
        },
        onClickMonteCarloPoolQualityRisk = {Npq_start,Npq_end,Pq_start,Pq_end,iq_start,iq_end,countSimulation, ->
            val commercialRiskCalculator: CommercialRiskCalculator = CommercialRiskCalculator()
            resultPoolQuality.value = commercialRiskCalculator.monteCarloRiskPoolQuality(Npq_start,Npq_end,Pq_start,Pq_end,iq_start,iq_end,countSimulation)
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
            items(1){i ->
                LazyRow(
                    userScrollEnabled = true

                )
                {
                    items(headersPrepaymentRisk) { header ->
                        Text(
                            modifier = Modifier
                                .border(1.dp, Color.Black)
                                .width(200.dp)
                                .padding(5.dp),
                            text = header,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Start,
                            letterSpacing = 2.sp
                        )

                    }
                }

            }
            items(resultPrepaymentRisk.value){row ->
                LazyRow(
                    userScrollEnabled = true
                ){
                    items(row.size){rowIndex ->
                        Text(
                            modifier = Modifier
                                .border(1.dp, Color.Black)
                                .width(200.dp)
                                .padding(5.dp),
                            text = "${row[rowIndex]}",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Start,
                            letterSpacing = 2.sp
                        )

                    }

                }
            }

            items(1){i ->
                LazyRow(
                    userScrollEnabled = true

                )
                {
                    items(headersPoolQuality) { header ->
                        Text(
                            modifier = Modifier
                                .border(1.dp, Color.Black)
                                .width(200.dp)
                                .padding(5.dp),
                            text = header,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Start,
                            letterSpacing = 2.sp
                        )

                    }
                }

            }
            items(resultPoolQuality.value){poolQualityRow ->
                LazyRow(
                    userScrollEnabled = true
                ){
                    items(poolQualityRow.size){rowIndex ->
                        Text(
                            modifier = Modifier
                                .border(1.dp, Color.Black)
                                .width(200.dp)
                                .padding(5.dp),
                            text = "${poolQualityRow[rowIndex]}",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Start,
                            letterSpacing = 2.sp
                        )

                    }

                }
            }
        }
    }
}