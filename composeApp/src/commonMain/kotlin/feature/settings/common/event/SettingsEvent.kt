package feature.settings.common.event

import core.event.A3Event

sealed interface SettingsEvent: A3Event {
    data object RestoreSuccess: SettingsEvent
    data object ExportSuccess: SettingsEvent
}
