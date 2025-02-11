package feature.category.categories

interface ICategoriesViewModel {
    fun onClickAdd()
}

object PreviewCategoriesViewModel: ICategoriesViewModel {
    override fun onClickAdd() {
    }
}
