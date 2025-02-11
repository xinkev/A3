package feature.category.editor

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.util.textAsFlow
import core.event.EventBus
import core.event.NavigationEvent
import feature.category.data.CategoryDataSource
import feature.category.domain.model.CategoryIconName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryEditorViewModel(
    private val eventBus: EventBus,
    private val categoryDataSource: CategoryDataSource,
) : ViewModel(), ICategoryEditorViewModel {
    override val nameInputState = TextFieldState()
    private val _selectedIconName = MutableStateFlow<CategoryIconName?>(null)
    override val selectedIconName = _selectedIconName.asStateFlow()
    override val enableAddButton: StateFlow<Boolean> = combine(
        nameInputState.textAsFlow(),
        selectedIconName
    ) { name, icon ->
        name.isNotEmpty() && icon != null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    override fun onIconClick(name: CategoryIconName) {
        _selectedIconName.update {
            if (it == name) null else name
        }
    }

    override fun onClickAdd() {
        if (nameInputState.text.isEmpty() && selectedIconName.value == null) return
        viewModelScope.launch {
            categoryDataSource.addCategory(
                name = nameInputState.text.toString(),
                iconName = selectedIconName.value!!
            )
            eventBus.send(NavigationEvent.NavigateUp)
        }
    }
}
