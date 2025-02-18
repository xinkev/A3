package core.file

import core.Outcome
import io.github.vinceglb.filekit.core.PlatformDirectory

expect object FileManager {
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
    ): Outcome<FileWriteError, Unit>
}


