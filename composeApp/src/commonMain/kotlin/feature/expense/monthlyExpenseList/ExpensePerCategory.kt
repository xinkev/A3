package feature.expense.monthlyExpenseList

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
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
import common.domain.model.IconName
import common.mapper.categoryIconMap
import common.util.toSmartString
import feature.expense.common.domain.model.TotalExpensePerCategory

private val lineHeightStyle = LineHeightStyle(
    alignment = LineHeightStyle.Alignment.Center,
    trim = LineHeightStyle.Trim.Both
)

@Composable
fun ExpensePerCategory(
    expense: TotalExpensePerCategory,
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimen.smallPadding),
            modifier = Modifier.weight(0.7f),
        ) {
            LeadingIcon(expense.categoryIcon)
            Category(name = expense.category)
        }

        Amount(expense.totalAmount.toSmartString())
    }
}

@Composable
private fun Category(name: String) {
    Text(
        text = name,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.bodyLarge.copy(
            lineHeightStyle = lineHeightStyle
        )
    )
}


@Composable
private fun LeadingIcon(
    name: IconName?
) {
    categoryIconMap[name]?.let {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(end = Dimen.largePadding),
        ) {
            Icon(
                imageVector = it.vector(),
                contentDescription = name?.realName,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(Dimen.smallPadding)
            )
        }
    }
}

@Composable
private fun RowScope.Amount(
    value: String
) {
    Text(
        text = value,
        modifier = Modifier.weight(1f),
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
    ExpensePerCategory(
        expense = TotalExpensePerCategory(
            category = "Food",
            categoryIcon = IconName.Food,
            totalAmount = 12.0,
        )
    )
}
