package feature.settings.common.di

import feature.settings.backup.di.backupModule
import feature.settings.backup.presentation.SettingsRestoreViewModel
import feature.settings.backup.presentation.SettingsExportViewModel
import feature.settings.presentation.SettingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = backupModule + module {
    viewModelOf(::SettingsViewModel)
    viewModelOf(::SettingsRestoreViewModel)
    viewModelOf(::SettingsExportViewModel)
}
