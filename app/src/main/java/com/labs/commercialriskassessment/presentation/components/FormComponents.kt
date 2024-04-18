package com.labs.commercialriskassessment.presentation.components

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.labs.commercialriskassessment.presentation.theme.RedColor
import kotlinx.coroutines.launch

private val TAG = "InitValueFormComponent"

@Composable
fun InitValueFormComponent(
    onClickApplyBtn: (
        p:Double,
        n:Double,
        i:Double,
        k1:Double,
        k2:Double,
        Npq:Double,
        Pq:Double,
        iq:Double,
        m: Int,
        np:Int,
        delta:Double,
        prepayment: Double,
        t:Double,
        dt: Double,
        beta:Double
            )-> Unit,
    onClickMonteCarloPrepaymentRisk: (
        prepayment_start:Double,
        prepayment_end:Double,
        t_start:Double,
        t_end:Double,
        dt_start:Double,
        dt_end:Double,
        beta_start:Double,
        beta_end:Double,
        countSimulation: Int
    ) -> Unit,
    onClickMonteCarloPoolQualityRisk: (
        Npq_start:Double,
        Npq_end:Double,
        Pq_start:Double,
        Pq_end:Double,
        iq_start:Double,
        iq_end:Double,
        countSimulation: Int
            ) -> Unit
) {
    Log.d(TAG, "InitFormComponent")
    val isLoading = remember {
        mutableStateOf(false)
    }
    val p = remember{ mutableStateOf("") }
    val n = remember{ mutableStateOf("") }
    val i = remember{ mutableStateOf("") }
    val k1 = remember{ mutableStateOf("") }
    val k2 = remember{ mutableStateOf("") }

    val Npq = remember{ mutableStateOf("") }
    val Pq = remember{ mutableStateOf("") }
    val iq = remember{ mutableStateOf("") }

    val m = remember{ mutableStateOf("") }
    val np = remember{ mutableStateOf("") }
    val delta = remember{ mutableStateOf("") }

    val prepayment = remember{ mutableStateOf("") }
    val t = remember{ mutableStateOf("") }
    val dt = remember{ mutableStateOf("") }
    val beta = remember{ mutableStateOf("") }

    val Npq_start = remember { mutableStateOf("") }
    val Npq_end = remember { mutableStateOf("") }
    val Pq_start = remember { mutableStateOf("") }
    val Pq_end = remember { mutableStateOf("") }
    val iq_start = remember { mutableStateOf("") }
    val iq_end = remember { mutableStateOf("") }

    val  prepayment_start = remember { mutableStateOf("") }
    val prepayment_end = remember { mutableStateOf("") }
    val  t_start = remember { mutableStateOf("") }
    val  t_end = remember { mutableStateOf("") }
    val  dt_start = remember { mutableStateOf("") }
    val  dt_end = remember { mutableStateOf("") }
    val  beta_start = remember { mutableStateOf("") }
    val  beta_end = remember { mutableStateOf("") }
    val  countSimulation = remember { mutableStateOf("") }

    val fields = remember{
        mutableStateOf(
            listOf(
                p,n,i,k1,k2,Npq,Pq,iq,m,np,delta,prepayment,t,dt,beta,countSimulation,prepayment_start,prepayment_end,t_start,t_end,dt_start,dt_end,beta_start,beta_end,countSimulation,
            )
        )
    }

    val isError = remember{mutableStateOf(false)}

    val scrollState = rememberScrollState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .heightIn(450.dp)
            .padding(horizontal = 12.dp)
            .verticalScroll(scrollState)
            .animateContentSize()
    ){
        PrimaryOutlinedTextField(
            textValue = p.value,
            labelValue = "сумма кредита",
            onValueChange = {p.value = it},
            keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number),
            enabled= isLoading.value.not()
        )
        PrimaryOutlinedTextField(
            textValue = n.value,
            labelValue = "срок кредита",
            onValueChange = {n.value = it},
            keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number),
            enabled= isLoading.value.not()
        )
        PrimaryOutlinedTextField(
            textValue = i.value,
            labelValue = "процентная ставка",
            onValueChange = {i.value = it},
            keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number),
            enabled= isLoading.value.not()
        )
        PrimaryOutlinedTextField(
            textValue = k1.value,
            labelValue = "курс валюты на начало операции",
            onValueChange = {k1.value = it},
            keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number),
            enabled= isLoading.value.not()
        )
        PrimaryOutlinedTextField(
            textValue = k2.value,
            labelValue = "курс валюты на момент окончания операции",
            onValueChange = {k2.value = it},
            keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number),
            enabled= isLoading.value.not()
        )
        // Риск потери качества  ===========================================
        PrimaryOutlinedTextField(
            textValue = Npq.value,
            labelValue = "количество испорченного товара",
            onValueChange = {Npq.value = it},
            keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number),
            enabled= isLoading.value.not()
        )
        PrimaryOutlinedTextField(
            textValue = Pq.value,
            labelValue = "стоимость товара хорошего качества",
            onValueChange = {Pq.value = it},
            keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number),
            enabled= isLoading.value.not()
        )
        PrimaryOutlinedTextField(
            textValue = iq.value,
            labelValue = "скидка на испорченный товар",
            onValueChange = {iq.value = it},
            keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number),
            enabled= isLoading.value.not()
        )

        PrimaryOutlinedTextField(
            textValue = m.value,
            labelValue = "количество товара с измененной ценой",
            onValueChange = {m.value = it},
            keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number),
            enabled= isLoading.value.not()
        )

        PrimaryOutlinedTextField(
            textValue = np.value,
            labelValue = "количество групп товара с измененной ценой",
            onValueChange = {np.value = it},
            keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number),
            enabled= isLoading.value.not()
        )
        PrimaryOutlinedTextField(
            textValue = delta.value,
            labelValue = "разница в изменении цены",
            onValueChange = {delta.value = it},
            keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number),
            enabled= isLoading.value.not()
        )
        // Риск, определяемый предоплатой клиента-покупателя =======================================
        PrimaryOutlinedTextField(
            textValue = prepayment.value,
            labelValue = "размер предоплаты",
            onValueChange = {prepayment.value = it},
            keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number),
            enabled= isLoading.value.not()
        )

        PrimaryOutlinedTextField(
            textValue = t.value,
            labelValue = "время до фактического получения товара",
            onValueChange = {t.value = it},
            keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number),
            enabled= isLoading.value.not()
        )

        PrimaryOutlinedTextField(
            textValue = dt.value,
            labelValue = "время фактического получения товара",
            onValueChange = {dt.value = it},
            keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number),
            enabled= isLoading.value.not()
        )

        PrimaryOutlinedTextField(
            textValue = beta.value,
            labelValue = "коэффициент роста капитала",
            onValueChange = {beta.value = it},
            keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number),
            enabled= isLoading.value.not()
        )
        Text(
            text="Для метода Монте-Карло:",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start,
            letterSpacing = 2.sp,
            modifier= Modifier.fillMaxWidth()
        )
        // Риск потери качества  ===========================================
        PrimaryOutlinedTextField(
            textValue = countSimulation.value,
            labelValue = "число итераций",
            onValueChange = { countSimulation.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            enabled = isLoading.value.not(),
            modifier = Modifier.width(150.dp)
        )
        Column(
            modifier = Modifier.padding(10.dp)
        )
        {

            Text(
                text="Риск потери качества:",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                letterSpacing = 2.sp,
                modifier= Modifier.fillMaxWidth()
            )

            Text(
                text = "количество испорченного товара",
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                letterSpacing = 2.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Row()
            {
                PrimaryOutlinedTextField(
                    textValue = Npq_start.value,
                    labelValue = "от",
                    onValueChange = { Npq_start.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = isLoading.value.not(),
                    modifier = Modifier.width(150.dp)
                )
                PrimaryOutlinedTextField(
                    textValue = Npq_end.value,
                    labelValue = "до",
                    onValueChange = { Npq_end.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = isLoading.value.not(),
                    modifier = Modifier.width(150.dp)

                )
            }
            Text(
                text = "стоимость товара хорошего качества",
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                letterSpacing = 2.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Row()
            {
                PrimaryOutlinedTextField(
                    textValue = Pq_start.value,
                    labelValue = "от",
                    onValueChange = { Pq_start.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = isLoading.value.not(),
                    modifier = Modifier.width(150.dp)
                )
                PrimaryOutlinedTextField(
                    textValue = Pq_end.value,
                    labelValue = "до",
                    onValueChange = { Pq_end.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = isLoading.value.not(),
                    modifier = Modifier.width(150.dp)

                )
            }
            Text(
                text = "скидка на испорченный товар",
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                letterSpacing = 2.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Row()
            {
                PrimaryOutlinedTextField(
                    textValue = iq_start.value,
                    labelValue = "от",
                    onValueChange = { iq_start.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = isLoading.value.not(),
                    modifier = Modifier.width(150.dp)
                )
                PrimaryOutlinedTextField(
                    textValue = iq_end.value,
                    labelValue = "до",
                    onValueChange = { iq_end.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = isLoading.value.not(),
                    modifier = Modifier.width(150.dp)

                )
            }
        }
        Column(
            modifier = Modifier.padding(10.dp)
        ){


            Text(
                text="Риск определяемый, предоплатой клиента-покупателя:",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                letterSpacing = 2.sp,
                modifier= Modifier.fillMaxWidth()
            )

            Text(
                text = "размер предоплаты",
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                letterSpacing = 2.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Row()
            {
                PrimaryOutlinedTextField(
                    textValue = prepayment_start.value,
                    labelValue = "от",
                    onValueChange = { prepayment_start.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = isLoading.value.not(),
                    modifier = Modifier.width(150.dp)
                )
                PrimaryOutlinedTextField(
                    textValue = prepayment_end.value,
                    labelValue = "до",
                    onValueChange = { prepayment_end.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = isLoading.value.not(),
                    modifier = Modifier.width(150.dp)

                )
            }
            Text(
                text = "время до фактического получения товара",
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                letterSpacing = 2.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Row()
            {
                PrimaryOutlinedTextField(
                    textValue = t_start.value,
                    labelValue = "от",
                    onValueChange = { t_start.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = isLoading.value.not(),
                    modifier = Modifier.width(150.dp)
                )
                PrimaryOutlinedTextField(
                    textValue =t_end.value,
                    labelValue = "до",
                    onValueChange = { t_end.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = isLoading.value.not(),
                    modifier = Modifier.width(150.dp)

                )
            }
            Text(
                text = "время фактического получения товара",
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                letterSpacing = 2.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Row()
            {
                PrimaryOutlinedTextField(
                    textValue = dt_start.value,
                    labelValue = "от",
                    onValueChange = { dt_start.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = isLoading.value.not(),
                    modifier = Modifier.width(150.dp)
                )
                PrimaryOutlinedTextField(
                    textValue =dt_end.value,
                    labelValue = "до",
                    onValueChange = { dt_end.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = isLoading.value.not(),
                    modifier = Modifier.width(150.dp)

                )
            }
            Text(
                text = "коэффициент роста капитала",
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                letterSpacing = 2.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Row()
            {
                PrimaryOutlinedTextField(
                    textValue = beta_start.value,
                    labelValue = "от",
                    onValueChange = { beta_start.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = isLoading.value.not(),
                    modifier = Modifier.width(150.dp)
                )
                PrimaryOutlinedTextField(
                    textValue =beta_end.value,
                    labelValue = "до",
                    onValueChange = { beta_end.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = isLoading.value.not(),
                    modifier = Modifier.width(150.dp)

                )
            }
        }


        if(isError.value) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Info, contentDescription = "", tint = RedColor)
                Text(
                    "Есть незаполненные поля",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = RedColor
                )
            }
        }
        Spacer(modifier = Modifier.heightIn(20.dp))
        PrimaryGradientButtonComponent(
            value="рассчитать",
            onClickButton = {
                isError.value = false
                isLoading.value = true
                for(field in fields.value){
                    Log.d(TAG, "${field}")
                    if(field.value.isEmpty()){
                        isError.value = true
                        break
                    }
                }

                if(isError.value.not()){
                    onClickApplyBtn(
                        p.value.toDouble(),
                        n.value.toDouble(),
                        i.value.toDouble(),
                        k1.value.toDouble(),
                        k2.value.toDouble(),
                        Npq.value.toDouble(),
                        Pq.value.toDouble(),
                        iq.value.toDouble(),
                        m.value.toInt(),
                        np.value.toInt(),
                        delta.value.toDouble(),
                        prepayment.value.toDouble(),
                        t.value.toDouble(),
                        dt.value.toDouble(),
                        beta.value.toDouble()

                    )
                    onClickMonteCarloPoolQualityRisk(
                    Npq_start.value.toDouble(),
                    Npq_end.value.toDouble(),
                    Pq_start.value.toDouble(),
                    Pq_end.value.toDouble(),
                    iq_start.value.toDouble(),
                    iq_end.value.toDouble(),
                    countSimulation.value.toInt()
                    )
                }
                else{
                    isError.value = true
                }
                isLoading.value = false
            },
            isLoading = isLoading.value,
            modifier = Modifier.width(250.dp)
        )
        PrimaryGradientButtonComponent(
            value = "заполнить",
            onClickButton = {
                isLoading.value = true
                p.value = "270000"
                n.value = "0.33"
                i.value = "0.36"
                k1.value = "27"
                k2.value = "28"
                Npq.value = "2060"
                Pq.value = "40"
                iq.value = "0.05"
                m.value = "10000"
                np.value = "1"
                delta.value = "1"
                prepayment.value = "600000"
                t.value = "2"
                dt.value = "4"
                beta.value = "1"
                prepayment_start.value = "600000"
                prepayment_end.value = "700000"
                t_start.value = "2"
                t_end.value = "4"
                dt_start.value = "4"
                dt_end.value = "8"
                beta_start.value = "0.5"
                beta_end.value = "1"
                countSimulation.value = "20"
                Npq_start.value = "600000"
                Npq_end.value = "700000"
                Pq_start.value = "2"
                Pq_end.value = "4"
                iq_start.value = "4"
                iq_end.value = "8"
                countSimulation.value = "20"
                isLoading.value = false


            },
            isLoading = isLoading.value,
            modifier = Modifier.width(250.dp)

        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormModalBottomSheet(
    openBottomSheet: Boolean,
    setStateBottomSheet: (Boolean) -> Unit,
    onDismissRequest: () -> Unit = {},
    content: @Composable () -> Unit,

    ){
    Log.wtf(TAG, "FormModalBottomSheet")
    val skipPartiallyExpanded by remember { mutableStateOf(true) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded,
        confirmValueChange = {true},

        )
    val scope = rememberCoroutineScope()

    if (openBottomSheet) {
        ModalBottomSheet(
            containerColor = Color.White,
            onDismissRequest = {
                scope.launch{
                    setStateBottomSheet(false)
                    onDismissRequest()
                }

            },
            sheetState = bottomSheetState,
            windowInsets = BottomSheetDefaults.windowInsets,

            ) {
            content()
        }

    }
}