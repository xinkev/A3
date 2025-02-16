package feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.event.EventBus
import core.event.NavigationEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val eventBus: EventBus,
) : ViewModel(), ISettingsViewModel {
    private val _loading = MutableStateFlow(false)
    override val loading = _loading.asStateFlow()

    override fun onClickAddCategory() {
        viewModelScope.launch {
            eventBus.send(NavigationEvent.NavigateToCategories)
        }
    }
}
