package data

interface KVStorage {
    var seeded: Boolean?

    fun clear()
}