package feature.category.common.di

import feature.category.categoryList.CategoriesViewModel
import feature.category.categoryEditor.CategoryEditorViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val categoriesModule = module {
    viewModelOf(::CategoriesViewModel)
    viewModelOf(::CategoryEditorViewModel)
}
