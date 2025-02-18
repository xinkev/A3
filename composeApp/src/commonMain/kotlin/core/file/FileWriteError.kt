package core.file

enum class FileWriteError {
    AccessDenied,          // Permission/access issues
    DirectoryCreationFailed,
    FileWriteFailed,      // Generic write failure
    FileCreateFailed,     // Failed to create new file
    StorageSpaceInsufficient, // Not enough space
    Unknown,
}
