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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.labs.commercialriskassessment.presentation.components.FormModalBottomSheet
import com.labs.commercialriskassessment.presentation.components.InitValueFormComponent
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
    InitValueFormComponent(
        onClickApplyBtn = {p,n,i,k1,k2 ->
            val commercialRiskCalculator: CommercialRiskCalculator = CommercialRiskCalculator(p,n,i,k1,k2)
            result.value = commercialRiskCalculator.calculateRisk()
            showModalResult.value = true
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
                            modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth()

                        )
                        {
                            Text(
                                splitedRow[0],
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
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
//                    Spacer(modifier = Modifier.padding(vertical=10.dp))
                }
            }

    }
}