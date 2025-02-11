package feature.settings

import a3.composeapp.generated.resources.Res
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.event.EventBus
import core.event.NavigationEvent
import feature.backup.data.A3BackupManager
import feature.backup.data.ExpenseTaiyakiRestoreAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi

class SettingsViewModel(
    private val backupManager: A3BackupManager,
    private val eventBus: EventBus,
) : ViewModel(), ISettingsViewModel {
    private val _loading = MutableStateFlow(false)
    override val loading = _loading.asStateFlow()

    init {
        backupManager.setAdapter(ExpenseTaiyakiRestoreAdapter())
    }

    @OptIn(ExperimentalResourceApi::class)
    override fun onClickRestore() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.update { true }
            val json = Res.readBytes("files/sample.json")
            backupManager.restore(json.decodeToString())
            _loading.update { false }
        }
    }

    override fun onClickAddCategory() {
        viewModelScope.launch {
            eventBus.send(NavigationEvent.NavigateToCategories)
        }
    }
}
