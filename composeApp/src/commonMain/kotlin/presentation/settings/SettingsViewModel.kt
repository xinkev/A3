package presentation.settings

import a3.composeapp.generated.resources.Res
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import feature.backup.A3BackupManager
import feature.backup.ExpenseTaiyakiBackupAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi

class SettingsViewModel(
    private val backupManager: A3BackupManager
) : ViewModel() {
    private val _loading = MutableStateFlow<Boolean>(false)
    val loading = _loading.asStateFlow()

    init {
        backupManager.setAdapter(ExpenseTaiyakiBackupAdapter())
    }

    @OptIn(ExperimentalResourceApi::class)
    fun onClickRestore() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.update { true }
            val json = Res.readBytes("files/sample.json")
            backupManager.restore(json.decodeToString())
            _loading.update { false }
        }
    }
}