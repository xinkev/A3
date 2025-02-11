package feature.settings

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import navigation.Route

interface ISettingsViewModel {
    val loading: StateFlow<Boolean>

    fun onClickRestore()
    fun onClickAddCategory()
}

internal object PreviewSettingsViewModel : ISettingsViewModel {
    override val loading: StateFlow<Boolean>
        get() = MutableStateFlow(false)

    override fun onClickRestore() {}

    override fun onClickAddCategory() {}
}


