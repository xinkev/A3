package feature.settings.backup.common.di

import feature.settings.backup.common.data.A3DataAdapterFactory
import feature.settings.backup.exporting.data.ExpenseTaiyakiDataExporter
import feature.settings.backup.importing.data.ExpenseTaiyakiDataImporter
import feature.settings.backup.common.domain.adapter.DataAdapterFactory
import feature.settings.backup.exporting.domain.adapter.DataExporter
import feature.settings.backup.importing.domain.adapter.DataImporter
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
