package feature.category.di

import feature.category.categories.CategoriesViewModel
import feature.category.editor.CategoryEditorViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val categoriesModule = module {
    viewModelOf(::CategoriesViewModel)
    viewModelOf(::CategoryEditorViewModel)
}
