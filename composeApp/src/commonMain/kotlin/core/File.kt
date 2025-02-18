package core

import androidx.compose.runtime.Composable
import io.github.vinceglb.filekit.core.PlatformDirectory

expect class FileManager {
    /**
     * Writes data to a file in the specified directory.
     * 
     * @param data The byte array to write to the file
     * @param directory The target directory (must be accessible via SAF)
     * @param fileName Name of the file to create/write
     * @return true if write was successful, false otherwise
     */
    fun writeFile(
        data: ByteArray,
        directory: PlatformDirectory,
        fileName: String,
    ): Boolean
}

/**
 * Composable function that provides a FileManager instance.
 * The instance is remembered across recompositions for efficiency.
 */
@Composable
expect fun rememberFileManager(): FileManager


