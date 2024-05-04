package home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import model.Backup.Expense
import util.verticallyCenteredTextStyle

class HomeScreen() : Screen {
    @Composable
    override fun Content() {
        val vm = koinScreenModel<HomeViewModel>()
        val data = vm.data.collectAsState()

        LifecycleEffect(
            onStarted = vm::onStart
        )

        Body(data.value)
    }

    companion object {
        @OptIn(ExperimentalMaterialApi::class)
        @Composable
        fun Body(expenses: List<Expense>) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(Color.Gray.copy(alpha = 0.1f))
                        .padding(8.dp)
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Surface(
                        elevation = 1.dp,
                        contentColor = Color.White,
                        color = Color(0xffc46935),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.height(38.dp),
                        onClick = { }
                    ) {
                        Row(modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .weight(1f),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Expense",
                                style = verticallyCenteredTextStyle,
                            )

                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add icon",
                                tint = Color.White
                            )
                        }
                    }
                }
                Divider()
                LazyColumn {
                    items(expenses) { expense ->
                        TransactionItem(expense)
                        Divider()
                    }
                }
            }
        }

        @Composable
        fun TransactionItem(expense: Expense) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = expense.detail,
                    modifier = Modifier.weight(0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${expense.cost}",
                    modifier = Modifier.weight(0.3f),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}