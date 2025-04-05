package feature.settings.backup.importing.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.event.EventBus
import core.onError
import core.onSuccess
import feature.settings.backup.common.domain.adapter.DataAdapterFactory
import feature.settings.backup.importing.domain.model.ImportError
import feature.settings.common.event.SettingsEvent.RestoreSuccess
import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsRestoreViewModel(
    private val dataAdapterFactory: DataAdapterFactory,
    private val eventBus: EventBus,
) : ViewModel(), ISettingsRestoreViewModel {
    override val supportedFormats = dataAdapterFactory.supportedFormats
    private val _inProgress = MutableStateFlow(false)
    override val inProgress: StateFlow<Boolean> = _inProgress.asStateFlow()
    private val _error = MutableStateFlow<ImportError?>(null)
    override val error = _error.asStateFlow()

    override fun onFilePicked(file: PlatformFile) {
        // Hardcoded for now since there's only one source and one format.
        val importer = dataAdapterFactory.getImporter("taiyaki", "json")!!
        _inProgress.value = true
        viewModelScope.launch {
            val outcome = importer.import(readData = { file.readBytes() })
            withContext(Dispatchers.Main.immediate) {
                outcome.onError { _error.value = it }
                outcome.onSuccess {
                    eventBus.send(RestoreSuccess)
                }
                _inProgress.value = false
            }
        }
    }

    override fun clearError() {
        _error.value = null
    }
}

