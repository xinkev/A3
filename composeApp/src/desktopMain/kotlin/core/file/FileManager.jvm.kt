package core.file

import com.xinkev.logger.log
import core.Outcome
import io.github.vinceglb.filekit.core.PlatformDirectory
import java.io.IOException

actual object FileManager {
    actual fun writeFile(
        data: ByteArray,
        directory: PlatformDirectory,
        fileName: String,
    ): Outcome<FileWriteError, Unit> {
        try {
            val filePath = directory.file.resolve(fileName)
            // Find existing file or create a new one
            val file = if (filePath.exists()) {
                filePath
            } else {
                filePath.createNewFile()
                filePath
            }
            // Write data to the file
            file.writeBytes(data)
            return Outcome.Success(Unit)
        } catch (e: IOException) {
            log.w { "Failed to write file: ${e.localizedMessage}" }
            return Outcome.Error(FileWriteError.FileWriteFailed)
        } catch (e: SecurityException) {
            log.w { "Failed to write file: ${e.localizedMessage}" }
            return Outcome.Error(FileWriteError.AccessDenied)
        }
    }
}
