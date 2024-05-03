package uicomponents

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIconHidden: Boolean = false,
    navigateBack: () -> Unit = {}
) {

    TopAppBar(
        modifier = modifier,
        title = { Text(title) },
//        colors = TopAppBarDefaults.topAppBarColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer
//        ),
//        contentColor = MaterialTheme.colors.onPrimary,
//        elevation = 0.dp,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenteredTopBar(
    title: String,
    modifier: Modifier = Modifier,
    rightContent: @Composable () -> Unit = {},
    leftContent: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(title) },
        navigationIcon = rightContent,
        actions = leftContent
    )
}