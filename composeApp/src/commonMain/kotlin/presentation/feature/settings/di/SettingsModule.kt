package presentation.feature.settings.di

import backup.A3BackupManager
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import presentation.feature.settings.SettingsViewModel

val settingsModule = module {
    factory { A3BackupManager(get()) }
    viewModelOf(::SettingsViewModel)
}