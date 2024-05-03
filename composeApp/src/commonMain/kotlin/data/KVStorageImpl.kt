package data

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

private enum class KVStorageKey {
    Seeded
}

class KVStorageImpl(
    private val settings: Settings = Settings()
) : KVStorage {
    private val observableSettings: ObservableSettings = settings as ObservableSettings
    override var seeded: Boolean?
        get() = settings[KVStorageKey.Seeded.name]
        set(value) {
            settings[KVStorageKey.Seeded.name] = value
        }

    override fun clear() {
        settings.clear()
    }
}
