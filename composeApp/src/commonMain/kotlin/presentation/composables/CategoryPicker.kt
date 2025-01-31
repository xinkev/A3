package presentation.composables

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.cancel
import a3.composeapp.generated.resources.ok
import a3.composeapp.generated.resources.select_category
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.stringResource
import presentation.model.Category
import presentation.model.MaterialIcon
import presentation.theme.Dimen


@Composable
fun CategoryPicker(
    modifier: Modifier = Modifier,
    onSelect: (Category?) -> Unit,
    selectedCategory: Category?
) {
    var openDialog by remember { mutableStateOf(false) }

    if (openDialog) {
        CategoryPickerDialog(
            onSelect = onSelect,
            onDismissRequest = { openDialog = false },
            selectedCategory = selectedCategory
        )
    }

    OutlinedButton(
        modifier = modifier,
        onClick = { openDialog = true },
    ) {
        if (selectedCategory != null) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimen.xSmallPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (selectedCategory.icon != null) {
                    Icon(imageVector = selectedCategory.icon.vector(), contentDescription = null)
                }
                Text(selectedCategory.name)
            }
        } else {
            Text(stringResource(Res.string.select_category))
        }
    }
}

@Composable
private fun CategoryPickerDialog(
    onDismissRequest: () -> Unit,
    selectedCategory: Category?,
    onSelect: (Category?) -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.background)
        ) {
            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onBackground) {
                Categories(
                    modifier = Modifier.padding(Dimen.largePadding),
                    selected = selectedCategory,
                    onSelect = onSelect,
                    hasBorder = false
                )
                Row(modifier = Modifier.align(Alignment.End)) {
                    TextButton(
                        onClick = onDismissRequest
                    ) {
                        Text(stringResource(Res.string.cancel))
                    }
                    TextButton(
                        onClick = {
                            onDismissRequest.invoke()
                        }
                    ) {
                        Text(stringResource(Res.string.ok))
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun CategoryPickerDialogPreview() {
    CompositionLocalProvider(LocalInspectionMode provides true) {
        CategoryPickerDialog(
            onDismissRequest = {},
            selectedCategory = null,
            onSelect = {}
        )
    }
}

@Preview
@Composable
private fun CategoryPickerPreview() {
    CompositionLocalProvider(LocalInspectionMode provides true) {
        CategoryPicker(
            selectedCategory = Category("Test", icon = MaterialIcon(Icons.Default.Info)),
            onSelect = {}
        )
    }
}