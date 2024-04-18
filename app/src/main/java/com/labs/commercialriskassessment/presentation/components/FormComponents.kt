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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
            )-> Unit) {
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
        Spacer(modifier = Modifier.heightIn(20.dp))
        PrimaryGradientButtonComponent(
            value="рассчитать",
            onClickButton = {
                isError.value = false
                isLoading.value = true
                if(p.value.isNotEmpty()
                    && n.value.isNotEmpty()
                    && i.value.isNotEmpty()
                    && k1.value.isNotEmpty()
                    && k2.value.isNotEmpty()
                    && Npq.value.isNotEmpty()
                    && Pq.value.isNotEmpty()
                    && iq.value.isNotEmpty()
                    && m.value.isNotEmpty()
                    && np.value.isNotEmpty()
                    && delta.value.isNotEmpty()
                    && prepayment.value.isNotEmpty()
                    && t.value.isNotEmpty()
                    && dt.value.isNotEmpty()
                    && beta.value.isNotEmpty()
                    )

                {
                    onClickApplyBtn(
                        p.value.toDouble(),
                        n.value.toDouble(),
                        i.value.toDouble() / 100,
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
                }
                else{
                    isError.value = true
                }
                isLoading.value = false
            },
            isLoading = isLoading.value,
            modifier = Modifier.width(250.dp))

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