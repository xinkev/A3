package feature.settings.backup.di

import feature.settings.backup.data.A3DataAdapterFactory
import feature.settings.backup.data.ExpenseTaiyakiDataExporter
import feature.settings.backup.data.ExpenseTaiyakiDataImporter
import feature.settings.backup.domain.adapter.DataAdapterFactory
import feature.settings.backup.domain.adapter.DataExporter
import feature.settings.backup.domain.adapter.DataImporter
import org.koin.dsl.module

val backupModule = module {
    factory<DataImporter> {
        ExpenseTaiyakiDataImporter(
            databaseFactory = get(),
            categoryDataSource = get(),
            expenseDataSource = get(),
        )
    }

    factory<DataExporter> {
        ExpenseTaiyakiDataExporter(
            fileManager = get(),
            categoryDataSource = get(),
            expenseDataSource = get()
        )
    }

    factory<DataAdapterFactory> {
        A3DataAdapterFactory(
            exporters = getAll<DataExporter>().toSet(),
            importers = getAll<DataImporter>().toSet(),
        )
    }
}
