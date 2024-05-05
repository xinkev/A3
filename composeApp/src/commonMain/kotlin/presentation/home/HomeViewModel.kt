package presentation.home

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import model.Expense

class HomeViewModel(
) : ScreenModel {
    private val _expenses = MutableStateFlow(emptyList<Expense>())
//    val expenses = _data.asStateFlow()


}