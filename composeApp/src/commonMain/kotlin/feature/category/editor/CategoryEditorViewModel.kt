package feature.category.editor

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import common.util.textAsFlow
import core.event.EventBus
import core.event.NavigationEvent
import feature.category.common.data.CategoryDataSource
import feature.category.common.domain.model.CategoryIconName
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import navigation.Route.SettingsGraph.CategoryEditor
import navigation.type.categoryNavTypeMap

class CategoryEditorViewModel(
    private val eventBus: EventBus,
    private val categoryDataSource: CategoryDataSource,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), ICategoryEditorViewModel {
    private val selectedCategory = savedStateHandle
        .toRoute<CategoryEditor>(categoryNavTypeMap)
        .category
    override val nameInputState = TextFieldState(selectedCategory?.name ?: "")
    private val _selectedIconName = MutableStateFlow(selectedCategory?.iconName)
    override val selectedIconName = _selectedIconName.asStateFlow()
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override val nameIsTaken: StateFlow<Boolean> = nameInputState
        .textAsFlow()
        .debounce(500)
        .mapLatest {
            categoryDataSource.selectCategoryBy(it.toString()) != null && it != selectedCategory?.name
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

    override val enableAddButton: StateFlow<Boolean> = combine(
        nameInputState.textAsFlow(),
        selectedIconName,
        nameIsTaken,
    ) { name, icon, nameTaken ->
        name.isNotEmpty() && icon != null && !nameTaken
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)
    override val isEdit: Boolean = selectedCategory != null

    init {
        println(savedStateHandle)
    }

    override fun onIconClick(name: CategoryIconName) {
        _selectedIconName.update {
            if (it == name) null else name
        }
    }

    override fun onClickAdd() {
        if (nameInputState.text.isEmpty() && selectedIconName.value == null) return
        viewModelScope.launch {
            if (isEdit) {
                categoryDataSource.updateCategory(
                    selectedCategory!!.name,
                    nameInputState.text.toString(),
                    selectedIconName.value!!.realName,
                )
            } else {
                categoryDataSource.addCategory(
                    name = nameInputState.text.toString(),
                    iconName = selectedIconName.value!!.realName
                )
            }
            eventBus.send(NavigationEvent.NavigateUp)
        }
    }
}
