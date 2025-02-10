package feature.settings.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import theme.Dimen

@Composable
fun SettingsEntry(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leftContent: @Composable RowScope.() -> Unit,
    rightContent: @Composable RowScope.() -> Unit = {}
) {
    Box(
        modifier = Modifier
            .height(56.dp)
            .clickable(onClick = onClick)
            .then(modifier)
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = Dimen.largePadding,
                    vertical = Dimen.smallPadding
                )
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            leftContent()
            Spacer(Modifier.padding(Dimen.mediumPadding))
            rightContent()
        }
    }
}
