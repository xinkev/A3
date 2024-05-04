package di

import androidx.compose.runtime.Composable
import org.koin.compose.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

@Composable
fun startKoin(
    appDeclaration: KoinAppDeclaration = {},
    modules: List<Module>,
    content: @Composable () -> Unit
) = KoinApplication(
    application = {
        modules(modules)
        appDeclaration()
    },
    content = content
)