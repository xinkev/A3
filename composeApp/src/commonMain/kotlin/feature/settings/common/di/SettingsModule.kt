package feature.settings.common.di

import feature.settings.backup.common.di.backupModule
import feature.settings.backup.importing.presentation.SettingsRestoreViewModel
import feature.settings.backup.exporting.presentation.SettingsExportViewModel
import feature.settings.presentation.SettingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = backupModule + module {
    viewModelOf(::SettingsViewModel)
    viewModelOf(::SettingsRestoreViewModel)
    viewModelOf(::SettingsExportViewModel)
}
