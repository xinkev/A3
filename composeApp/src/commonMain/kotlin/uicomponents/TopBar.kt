package uicomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun TopBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIconHidden: Boolean = false,
    navigateBack: () -> Unit = {}
) {
//    val density = LocalDensity.current

    TopAppBar(
//        modifier = Modifier
//            .drawBehind {
//                val strokeWidthPx = density.run { Dp.Hairline.toPx() }
//                val width = size.width
//                val height = size.height - strokeWidthPx / 2
//
//                drawLine(
//                    color = topBarBorderColor,
//                    start = Offset(x = 0f, y = height),
//                    end = Offset(x = width, y = height),
//                    strokeWidth = strokeWidthPx
//                )
//            }
//            .then(modifier),
        modifier = modifier,
        title = { Text(title) },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        elevation = 0.dp,
        navigationIcon = {
            if (!navigationIconHidden) {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Navigate back"
                    )
                }
            }
        }
    )
}

@Composable
fun CenteredTopBar(
    title: String,
    modifier: Modifier = Modifier,
    rightContent: @Composable () -> Unit = {},
    leftContent: @Composable () -> Unit = {}
) {
    val topBarHeight = 32.dp

    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        elevation = 0.dp,
        modifier = modifier
    ) {
        //TopAppBar Content
        Box(Modifier.height(topBarHeight)) {
            //Left Content
            Row(verticalAlignment = Alignment.CenterVertically) {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                ) {
                    rightContent()
                }
            }

            //Title
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProvideTextStyle(value = MaterialTheme.typography.h6) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            text = title,
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }

            //Right Content
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Row(
                    Modifier.fillMaxHeight(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    content = { leftContent() }
                )
            }
        }
    }
}