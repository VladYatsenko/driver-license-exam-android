package com.testdai.ui.screen.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.testdai.R
import com.testdai.compose.Fonts
import com.testdai.core.navigation.NavActions

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory),
    navigate: (NavActions.Destination) -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.black))
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontFamily = Fonts.bold,
            text = stringResource(id = R.string.app_name),
            color = colorResource(id = R.color.white)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {}

    }
}

@Composable
fun CategorySelector(
    modifier: Modifier = Modifier,
    categories: String = "",
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.shark), RoundedCornerShape(12.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(align = Alignment.CenterVertically)
                .padding(start = 4.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                fontFamily = Fonts.medium,
                text = stringResource(id = R.string.category),
                color = colorResource(id = R.color.white)
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
                textAlign = TextAlign.Start,
                fontFamily = Fonts.medium,
                text = categories,
                color = colorResource(id = R.color.gray)
            )
        }
        OutlinedButton(
            modifier = Modifier,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(0.dp, colorResource(id = R.color.selago)),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = colorResource(id = R.color.selago)
            ),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
            onClick = onClick
        ) {
            Text(
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                fontFamily = Fonts.medium,
                text = stringResource(id = R.string.change),
                color = colorResource(id = R.color.black)
            )
        }
    }
}


@Preview(showSystemUi = false, showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}