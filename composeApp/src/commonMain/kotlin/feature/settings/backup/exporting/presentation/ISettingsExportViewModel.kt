package feature.settings.backup.exporting.presentation

import feature.settings.backup.exporting.domain.model.ExportError
import io.github.vinceglb.filekit.core.PlatformDirectory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ISettingsExportViewModel {
    val supportedFormats: Set<String>
    val inProgress: StateFlow<Boolean>
    val error: StateFlow<ExportError?>
    val destDirectory: StateFlow<PlatformDirectory?>

    fun onDirectoryChosen(directory: PlatformDirectory)
    fun onConfirmation()
    fun clearError()
    fun onCancel()
}

object PreviewSettingsExportViewModel: ISettingsExportViewModel {
    override val supportedFormats: Set<String> = setOf("json")
    override val inProgress: StateFlow<Boolean> = MutableStateFlow(true)
    override val error: MutableStateFlow<ExportError?> = MutableStateFlow(null)
    override val destDirectory: StateFlow<PlatformDirectory?> = MutableStateFlow(null)

    override fun onConfirmation() {}
    override fun clearError() {}
    override fun onDirectoryChosen(directory: PlatformDirectory) {}
    override fun onCancel() {}
}
