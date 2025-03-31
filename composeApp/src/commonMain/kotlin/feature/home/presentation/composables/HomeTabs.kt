package feature.home.presentation.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import app.theme.Dimen
import common.util.A3DateFormat
import common.util.dateTimeMilliToString
import common.util.dateTimeToDisplay
import common.util.toSmartString
import feature.home.presentation.composables.HomeTab.Daily
import feature.home.presentation.composables.HomeTab.Monthly
import kotlinx.coroutines.flow.StateFlow

enum class HomeTab {
    Daily, Monthly
}

@Composable
fun ColumnScope.HomeTabs(
    dateMilli: Long,
    monthlyTotal: StateFlow<Double>,
    dailyTotal: StateFlow<Double>,
    content: @Composable (HomeTab) -> Unit,
) {
    val currentDate = dateTimeToDisplay(dateMilli, A3DateFormat.DisplayDate)
    val currentMonth by
    remember { derivedStateOf { dateTimeMilliToString(dateMilli, A3DateFormat.MonthFullName) } }
    val currentDateTotal by dailyTotal.collectAsState()
    val currentMonthTotal by monthlyTotal.collectAsState()

    var selectedTab by remember { mutableStateOf(Daily) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimen.smallPadding),
        modifier = Modifier
            .padding(Dimen.largePadding)
    ) {
        Tab(
            date = currentDate,
            value = currentDateTotal,
            selected = selectedTab == Daily,
            onClick = { selectedTab = Daily },
        )
        Tab(
            date = currentMonth,
            value = currentMonthTotal,
            selected = selectedTab == Monthly,
            onClick = { selectedTab = Monthly },
        )
    }

    AnimatedContent(
        targetState = selectedTab,
        transitionSpec = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> if (Daily isTransitioningTo Monthly) fullWidth else -fullWidth },
                animationSpec = tween(durationMillis = 300)
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { fullWidth -> if (Daily isTransitioningTo Monthly) -fullWidth else fullWidth },
                animationSpec = tween(durationMillis = 300)
            )
        }
    ) {
        content(selectedTab)
    }
}

@Composable
private fun RowScope.Tab(
    date: String,
    value: Double,
    selected: Boolean,
    onClick: () -> Unit,
) {
    var tabWidth by remember { mutableStateOf(0) }
    Card(
        modifier = Modifier.weight(.5f)
            .height(IntrinsicSize.Max)
            .onSizeChanged { tabWidth = it.width },
        onClick = onClick,
        colors = CardDefaults.cardColors().run {
            this.copy(containerColor = if (selected) MaterialTheme.colorScheme.secondaryContainer else this.containerColor)
        },
    ) {
        Column(modifier = Modifier.padding(Dimen.mediumPadding)) {
            Text(date, style = MaterialTheme.typography.titleSmall)
            Text(value.toSmartString(), style = MaterialTheme.typography.bodySmall)
        }
    }
}
