package feature.category.editor

import androidx.compose.foundation.text.input.TextFieldState
import common.domain.model.IconName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ICategoryEditorViewModel {
    val nameInputState: TextFieldState
    val selectedIconName: StateFlow<IconName?>
    val enableAddButton: StateFlow<Boolean>
    val nameIsTaken: StateFlow<Boolean>

    /**
     * whether or not the editor is in edit mode
     */
    val isEdit: Boolean
    fun onIconClick(name: IconName)
    fun onClickAdd()
    fun onDeleteConfirmed()
}

object PreviewCategoryEditorViewModel : ICategoryEditorViewModel {
    override val nameInputState: TextFieldState
        get() = TextFieldState()
    override val selectedIconName: StateFlow<IconName?>
        get() = MutableStateFlow(null)
    override val enableAddButton: StateFlow<Boolean>
        get() = MutableStateFlow(false)
    override val isEdit: Boolean
        get() = false
    override val nameIsTaken: StateFlow<Boolean>
        get() = MutableStateFlow(true)

    override fun onIconClick(name: IconName) {
    }
    override fun onClickAdd() {
    }

    override fun onDeleteConfirmed() {
    }
}
