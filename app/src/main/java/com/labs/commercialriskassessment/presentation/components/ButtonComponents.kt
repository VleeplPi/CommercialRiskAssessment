package com.labs.commercialriskassessment.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.labs.commercialriskassessment.presentation.theme.GreenGradient
import com.labs.commercialriskassessment.presentation.theme.SmoothBlackGradient

@Composable
fun PrimaryGradientButtonComponent(value: String, onClickButton: () -> Unit, isLoading: Boolean = false, modifier: Modifier = Modifier.fillMaxWidth(), gradientColors: List<Color> = SmoothBlackGradient){
    Button(
        onClick=onClickButton,
        modifier= modifier
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(),
    ){
        Box(
            modifier = modifier
                .heightIn(48.dp)
                .background(
                    brush = Brush.horizontalGradient(gradientColors),
                    shape = RoundedCornerShape(50.dp)
                )
                .border(
                    border = BorderStroke(
                        2.dp,
                        brush = Brush.horizontalGradient(GreenGradient)
                    ),
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ){
            if(isLoading){
                CircularProgressIndicator(color = Color.White)
            }
            else{
                Text(
                    text=value,
                    fontSize=18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

            }

        }
    }
}