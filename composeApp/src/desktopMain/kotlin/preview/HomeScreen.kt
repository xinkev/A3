package preview

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import home.HomeScreen
import model.Backup.Expense

@Preview()
@Composable
fun PreviewHomeScreen() {
    HomeScreen.Body(
        expenses = listOf(
            Expense(detail = "Gift", cost = 62.33),
            Expense(detail = "GitHub Payment", cost = 7.00),
            Expense(detail = "Inboard", cost = 19.99),
            Expense(detail = "Chipotle", cost = 9.50)
        )
    )
}