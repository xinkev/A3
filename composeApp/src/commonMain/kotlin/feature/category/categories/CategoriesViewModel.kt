package feature.category.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.event.EventBus
import core.event.NavigationEvent
import feature.category.common.domain.model.Category
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val eventBus: EventBus
) : ViewModel(), ICategoriesViewModel {
    override fun onClickAdd() {
        viewModelScope.launch {
            eventBus.send(NavigationEvent.NavigateToCategoryEditor(null))
        }
    }

    override fun onClickCategory(category: Category?) {
        if (category == null) return

        viewModelScope.launch {
            eventBus.send(NavigationEvent.NavigateToCategoryEditor(category))
        }
    }
}
