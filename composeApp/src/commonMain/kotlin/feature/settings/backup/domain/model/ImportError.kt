package feature.settings.backup.domain.model

sealed interface ImportError {
    data object FileAccessError : ImportError
    data object FileTooLarge : ImportError
    data object UnrecognizedFile: ImportError
}
