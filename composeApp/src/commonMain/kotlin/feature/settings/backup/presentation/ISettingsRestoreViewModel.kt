package feature.settings.backup.presentation

import feature.settings.backup.domain.model.ImportError
import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ISettingsRestoreViewModel {
    val supportedFormats: Set<String>
    val inProgress: StateFlow<Boolean>
    val error: StateFlow<ImportError?>
    fun onFilePicked(file: PlatformFile)
    fun clearError()
}

object PreviewSettingsRestoreViewModel: ISettingsRestoreViewModel {
    override val supportedFormats: Set<String> = setOf("json")
    override val inProgress: StateFlow<Boolean> = MutableStateFlow(true)
    override val error: MutableStateFlow<ImportError?> = MutableStateFlow(null)

    override fun onFilePicked(file: PlatformFile) {}
    override fun clearError() {}
}
