package feature.settings.di

import feature.backup.data.A3BackupManager
import feature.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = module {
    factory { A3BackupManager(get()) }
    viewModelOf(::SettingsViewModel)
}
