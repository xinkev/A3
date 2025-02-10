package feature.settings

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import navigation.Route

interface ISettingsViewModel {
    val loading: StateFlow<Boolean>
    val navigation: Flow<Route?>

    fun onClickRestore()
    fun onClickAddCategory()
}

internal object PreviewSettingsViewModel : ISettingsViewModel {
    override val loading: StateFlow<Boolean>
        get() = MutableStateFlow(false)
    override val navigation: Flow<Route?>
        get() = MutableStateFlow(null)

    override fun onClickRestore() {}

    override fun onClickAddCategory() {}
}


