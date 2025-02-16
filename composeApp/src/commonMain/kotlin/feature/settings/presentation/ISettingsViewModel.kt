package feature.settings.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ISettingsViewModel {
    val loading: StateFlow<Boolean>

    fun onClickAddCategory()
}

internal object PreviewSettingsViewModel : ISettingsViewModel {
    override val loading: StateFlow<Boolean>
        get() = MutableStateFlow(false)

    override fun onClickAddCategory() {}
}


