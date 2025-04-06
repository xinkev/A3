package feature.category.common.di

import feature.category.categoryEditor.CategoryEditorViewModel
import feature.category.categoryList.CategoriesViewModel
import feature.category.common.data.CategoryDataSource
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val categoriesModule = module {
    viewModelOf(::CategoriesViewModel)
    viewModelOf(::CategoryEditorViewModel)
    factory { CategoryDataSource(get(), get()) }
}
