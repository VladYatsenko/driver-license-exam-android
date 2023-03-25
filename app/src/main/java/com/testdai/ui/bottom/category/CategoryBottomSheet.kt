package com.testdai.ui.bottom.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.testdai.R
import com.testdai.ui.screen.home.HomeViewModel
import com.testdai.ui.theme.Black
import com.testdai.ui.theme.Skeptic
import com.testdai.widget.BottomSheetWidget
import com.testdai.widget.ButtonWidget

@Composable
fun CategoryBottomSheet(
    viewModel: HomeViewModel,
    dismiss: () -> Unit = {}
) {

    val categorySelector by viewModel.categorySelector.observeAsState(CategorySelectorState())

    val isCategoriesEmpty = categorySelector.selectedCategories.isEmpty()

    val textColor = when {
        isCategoriesEmpty -> MaterialTheme.colors.primaryVariant
        else -> Black
    }
    val borderColor = when {
        isCategoriesEmpty -> MaterialTheme.colors.primaryVariant
        else -> Skeptic
    }
    val containerColor = when {
        isCategoriesEmpty -> MaterialTheme.colors.background
        else -> Skeptic
    }

    BottomSheetWidget(
        title = stringResource(id = R.string.choose_category)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            contentPadding = PaddingValues(horizontal = 48.dp, vertical = 8.dp),
            content = {
                items(categorySelector.categories.size) { index ->
                    when (val item = categorySelector.categories[index]) {
                        is CategoryItem.Item -> CategoryItemWidget(item) {
                            viewModel.onCategoryClicked(item)
                        }
                        CategoryItem.Empty -> EmptyCard()
                    }
                }
            }
        )
        ButtonWidget(
            modifier = Modifier
                .padding(top = 6.dp, bottom = 6.dp, start = 16.dp, end = 16.dp),
            borderColor = borderColor,
            containerColor = containerColor,
            text = stringResource(id = R.string.button_save),
            textColor = textColor,
            onClick = {
                if (!isCategoriesEmpty) {
                    viewModel.saveSelectedCategories()
                    dismiss()
                }
            }
        )
    }
}

@Composable
fun CategoryItemWidget(
    item: CategoryItem.Item,
    onClick: () -> Unit
) {

    val textColor = when (item.state) {
        CategoryItem.SelectionState.Common -> MaterialTheme.colors.primary
        CategoryItem.SelectionState.Selected -> Black
        CategoryItem.SelectionState.Disabled -> MaterialTheme.colors.primaryVariant
    }

    val borderColor = when (item.state) {
        CategoryItem.SelectionState.Common -> MaterialTheme.colors.primary
        CategoryItem.SelectionState.Selected -> MaterialTheme.colors.primary
        CategoryItem.SelectionState.Disabled -> MaterialTheme.colors.primaryVariant
    }

    val containerColor = when (item.state) {
        CategoryItem.SelectionState.Selected -> Skeptic
        else -> MaterialTheme.colors.surface
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ButtonWidget(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
                .padding(4.dp)
                .clickable {
                    onClick()
                },
            borderColor = borderColor,
            containerColor = containerColor,
            contentPadding = PaddingValues(0.dp),
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