package feature.category.categories

import feature.category.common.domain.model.Category

interface ICategoriesViewModel {
    fun onClickAdd()
    fun onClickCategory(category: Category?)
}

object PreviewCategoriesViewModel: ICategoriesViewModel {
    override fun onClickAdd() {
    }

    override fun onClickCategory(category: Category?) {
    }
}
