package com.testdai.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ButtonWidget(
    modifier: Modifier,
    borderColor: Color? = null,
    containerColor: Color,
    roundedCornerSize: Dp = 12.dp,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    style: TextStyle = MaterialTheme.typography.subtitle1,
    text: String,
    textColor: Color,
    textAlign: TextAlign = TextAlign.Center,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(roundedCornerSize),
        border = BorderStroke(1.dp, borderColor ?: containerColor),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor
        ),
        contentPadding = contentPadding,
        onClick = onClick
    ) {
        Text(
            style = style,
            textAlign = textAlign,
            text = text,
            color = textColor
        )
    }
}