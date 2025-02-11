package feature.category.presentation

interface ICategoriesViewModel {
    fun onClickAdd()
}

object PreviewCategoriesViewModel: ICategoriesViewModel {
    override fun onClickAdd() {
    }
}
