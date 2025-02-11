package feature.category.editor

import androidx.compose.foundation.text.input.TextFieldState
import feature.category.common.domain.model.CategoryIconName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ICategoryEditorViewModel {
    val nameInputState: TextFieldState
    val selectedIconName: StateFlow<CategoryIconName?>
    val enableAddButton: StateFlow<Boolean>
    fun onIconClick(name: CategoryIconName)
    fun onClickAdd()
}

object PreviewCategoryEditorViewModel : ICategoryEditorViewModel {
    override val nameInputState: TextFieldState
        get() = TextFieldState()
    override val selectedIconName: StateFlow<CategoryIconName?>
        get() = MutableStateFlow(null)
    override val enableAddButton: StateFlow<Boolean>
        get() = MutableStateFlow(false)

    override fun onIconClick(name: CategoryIconName) {
    }
    override fun onClickAdd() {
    }
}
