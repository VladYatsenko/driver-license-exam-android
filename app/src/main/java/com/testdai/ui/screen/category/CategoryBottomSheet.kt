package com.testdai.ui.screen.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.testdai.R
import com.testdai.compose.Fonts
import com.testdai.ui.screen.home.HomeViewModel
import com.testdai.widget.AppButton

@Composable
fun CategoryBottomSheet(
    viewModel: HomeViewModel,
    dismiss: () -> Unit = {}
) {

    val categorySelector by viewModel.categorySelector.observeAsState(CategorySelectorState())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(40.dp, 5.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(id = R.color.gray))
        )
        Text(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 4.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontFamily = Fonts.medium,
            text = stringResource(id = R.string.choose_category),
            color = colorResource(id = R.color.white)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            contentPadding = PaddingValues(horizontal = 28.dp, vertical = 8.dp),
            content = {
                items(categorySelector.categories.size) { index ->
                    when (val item = categorySelector.categories[index]) {
                        is CategoryItem.Item -> CategoryCard(item) {
                            viewModel.onCategoryClicked(item)
                        }
                        CategoryItem.Empty -> EmptyCard()
                    }
                }
            }
        )
        AppButton(
            modifier = Modifier
                .padding(bottom = 6.dp, start = 6.dp, end = 6.dp),
            borderColor = colorResource(id = R.color.skeptic),
            containerColor = colorResource(
                id = if (categorySelector.selectedCategories.isEmpty()) R.color.shark else R.color.skeptic
            ),
            text = stringResource(id = R.string.button_save),
            textColor = colorResource(
                id = if (categorySelector.selectedCategories.isEmpty()) R.color.skeptic else R.color.black
            ),
            onClick = {
                if (categorySelector.selectedCategories.isNotEmpty()) {
                    viewModel.saveSelectedCategories()
                    dismiss()
                }
            }
        )
    }
}

@Composable
fun CategoryCard(
    item: CategoryItem.Item,
    onClick: () -> Unit
) {

    val textColor = when (item.state) {
        CategoryItem.SelectionState.Common -> Color.White
        CategoryItem.SelectionState.Selected -> colorResource(id = R.color.black)
        CategoryItem.SelectionState.Disabled -> Color.Gray
    }

    val borderColor = when (item.state) {
        CategoryItem.SelectionState.Common -> Color.White
        CategoryItem.SelectionState.Selected -> colorResource(id = R.color.skeptic)
        CategoryItem.SelectionState.Disabled -> Color.Gray
    }

    val containerColorColor = when (item.state) {
        CategoryItem.SelectionState.Selected -> colorResource(id = R.color.skeptic)
        else -> colorResource(id = R.color.shark)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppButton(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
                .padding(4.dp)
                .clickable {
                    onClick()
                },
            borderColor = borderColor,
            containerColor = containerColorColor,
            contentPadding = PaddingValues(0.dp),
            textSize = 16.sp,
            fontFamily = Fonts.medium,
            text = item.category.name,
            textColor = textColor,
            onClick = onClick
        )
    }
}

@Composable
fun EmptyCard() {
    Row {
        Box(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
        )
    }
}

@Preview
@Composable
fun CategorySheetPreview() {
    CategoryBottomSheet(viewModel = viewModel(factory = HomeViewModel.Factory))
}