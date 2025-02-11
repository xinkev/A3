package feature.category.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.event.EventBus
import core.event.NavigationEvent
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val eventBus: EventBus
) : ViewModel(), ICategoriesViewModel {
    override fun onClickAdd() {
        viewModelScope.launch {
            eventBus.send(NavigationEvent.NavigateToCategoryEditor)
        }
    }
}
