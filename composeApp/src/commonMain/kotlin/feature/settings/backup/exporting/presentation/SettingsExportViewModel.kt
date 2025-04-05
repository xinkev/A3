package feature.settings.backup.exporting.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.event.EventBus
import core.onError
import core.onSuccess
import feature.settings.backup.common.domain.adapter.DataAdapterFactory
import feature.settings.backup.exporting.domain.model.ExportError
import feature.settings.common.event.SettingsEvent.ExportSuccess
import io.github.vinceglb.filekit.core.PlatformDirectory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsExportViewModel(
    private val dataAdapterFactory: DataAdapterFactory,
    private val eventBus: EventBus,
) : ISettingsExportViewModel, ViewModel() {
    override val supportedFormats = dataAdapterFactory.supportedFormats
    private val _destDirectory = MutableStateFlow<PlatformDirectory?>(null)
    override val destDirectory = _destDirectory.asStateFlow()
    private val _inProgress = MutableStateFlow(false)
    override val inProgress: StateFlow<Boolean> = _inProgress.asStateFlow()
    private val _error = MutableStateFlow<ExportError?>(null)
    override val error = _error.asStateFlow()

    override fun onConfirmation() {
        destDirectory.value?.let { destDir ->
            // Hardcoded for now since there's only one source and one format.
            val exporter = dataAdapterFactory.getExporter("taiyaki", "json")!!
            _inProgress.value = true
            viewModelScope.launch {
                val outcome = exporter.export(destDir)
                withContext(Dispatchers.Main.immediate) {
                    outcome.onError { _error.value = it }
                    outcome.onSuccess {
                        _destDirectory.value = null
                        eventBus.send(ExportSuccess)
                    }
                    _inProgress.value = false
                }
            }
        }

    }

    override fun onDirectoryChosen(directory: PlatformDirectory) {
        _destDirectory.update { directory }
    }

    override fun onCancel() {
        _destDirectory.update { null }
    }

    override fun clearError() {
        _error.value = null
    }
}
