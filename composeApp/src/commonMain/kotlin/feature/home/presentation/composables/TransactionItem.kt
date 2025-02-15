package feature.home.presentation.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.theme.Dimen
import common.util.toSmartString
import feature.category.categories.categoryIconMap
import feature.category.common.domain.model.Category
import feature.category.common.domain.model.CategoryIconName
import feature.expense.common.domain.model.Expense
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

private val lineHeightStyle = LineHeightStyle(
    alignment = LineHeightStyle.Alignment.Center,
    trim = LineHeightStyle.Trim.Both
)

@Composable
fun TransactionItem(
    expense: Expense,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimen.smallPadding),
            modifier = Modifier.weight(0.7f),
        ) {
            LeadingIcon(expense.category)
            Detail(expense.detail)
        }
        Amount(expense.cost.toSmartString())
    }
}

@Composable
private fun LeadingIcon(
    category: Category,
) {
    categoryIconMap[category.iconName]?.let {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(end = Dimen.largePadding),
        ) {
            Icon(
                imageVector = it.vector(),
                contentDescription = category.name,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(Dimen.smallPadding)
            )
        }
    }
}

@Composable
private fun Detail(detail: String?) {
    Text(
        text = detail ?: "",
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.bodyLarge.copy(
            lineHeightStyle = lineHeightStyle
        )
    )
}

@Composable
private fun RowScope.Amount(
    value: String
) {
    Text(
        text = value,
        modifier = Modifier.weight(0.3f),
        textAlign = TextAlign.End,
        style = MaterialTheme.typography.bodyMedium.copy(
            lineHeightStyle = lineHeightStyle
        ),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}


@Preview
@Composable
private fun Preview() {
    TransactionItem(
        expense = Expense(
            uuid = "23",
            detail = "Costco",
            datetime = LocalDateTime(LocalDate(2023, 1, 1), LocalTime(1, 1)),
            category = Category(
                uuid = "213",
                name = "Grocery",
                iconName = CategoryIconName.Grocery,
            ),
            cost = 123.0
        ),
        onClick = {}
    )
}
