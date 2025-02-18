package core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.xinkev.logger.log
import io.github.vinceglb.filekit.core.PlatformDirectory
import java.io.FileNotFoundException

actual class FileManager {
    actual fun writeFile(
        data: ByteArray,
        directory: PlatformDirectory,
        fileName: String,
    ): Boolean {
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
            return true
        } catch (e: FileNotFoundException) {
            log.w { "Failed to write file: ${e.localizedMessage}" }
            return false
        }
    }
}

@Composable
actual fun rememberFileManager(): FileManager {
    return remember { FileManager() }
}
