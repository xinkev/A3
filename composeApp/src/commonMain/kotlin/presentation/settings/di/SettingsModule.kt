package presentation.settings.di

import feature.backup.A3BackupManager
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import presentation.settings.SettingsViewModel

val settingsModule = module {
    factory { A3BackupManager(get()) }
    viewModelOf(::SettingsViewModel)
}