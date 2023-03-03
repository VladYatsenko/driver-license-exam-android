package com.testdai.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.testdai.compose.Fonts
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp

@Composable
fun AppButton(
    modifier: Modifier,
    borderColor: Color? = null,
    containerColor: Color,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    textSize: TextUnit = 16.sp,
    fontFamily: FontFamily = Fonts.medium,
    text: String,
    textColor: Color,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, borderColor ?: containerColor), //colorResource(id = R.color.skeptic)),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor //colorResource(id = R.color.skeptic)
        ),
        contentPadding = contentPadding,//PaddingValues(16.dp),
        onClick = onClick
    ) {
        Text(
            fontSize = textSize,
            textAlign = TextAlign.Center,
            fontFamily = fontFamily,
            text = text,
            color = textColor
        )
    }
}