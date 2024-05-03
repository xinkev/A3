package di

import androidx.compose.runtime.Composable
import org.koin.compose.KoinApplication
import org.koin.dsl.KoinAppDeclaration

@Composable
fun startKoin(
    appDeclaration: KoinAppDeclaration = {},
    content: @Composable () -> Unit
) = KoinApplication(
    application = {
        modules()
        appDeclaration()
    },
    content = content
)