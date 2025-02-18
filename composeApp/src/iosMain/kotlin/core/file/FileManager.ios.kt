package core.file

import com.xinkev.logger.log
import core.Outcome
import io.github.vinceglb.filekit.core.PlatformDirectory
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.value
import platform.Foundation.NSError
import platform.Foundation.NSFileCoordinator
import platform.Foundation.NSFileCoordinatorWritingForReplacing
import platform.Foundation.dataWithBytes
import platform.Foundation.writeToURL

actual object FileManager {
    @OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
    actual fun writeFile(
        data: ByteArray,
        directory: PlatformDirectory,
        fileName: String,
    ): Outcome<FileWriteError, Unit> {
        val url = directory.nsUrl

        if (!url.startAccessingSecurityScopedResource()) {
            return Outcome.Error(FileWriteError.AccessDenied)
        }
        val outputUrl = url.URLByAppendingPathComponent(fileName, false)
            ?: return Outcome.Error(FileWriteError.DirectoryCreationFailed)

        try {
            memScoped {
                val error = alloc<ObjCObjectVar<NSError?>>()
                val nsData = data.usePinned {
                    platform.Foundation.NSData
                        .dataWithBytes(it.addressOf(0), data.size.toULong())
                }
                var success = false
                NSFileCoordinator().coordinateWritingItemAtURL(
                    url = outputUrl,
                    options = NSFileCoordinatorWritingForReplacing,
                    error = error.ptr,
                ) {
                    success = nsData.writeToURL(outputUrl, atomically = true)
                }
                if (success) {
                    log.i { "File written successfully to ${outputUrl.path}" }
                    return Outcome.Success(Unit)
                } else {
                    log.w {
                        "Failed to write file to ${outputUrl.path}: ${error.value?.localizedDescription}"
                    }
                    return Outcome.Error(FileWriteError.FileWriteFailed)
                }
            }
        } catch (e: Exception) {
            log.w {
                "Failed to write file to ${outputUrl.path}: ${e.message}"
            }
            return Outcome.Error(FileWriteError.Unknown)
        } finally {
            url.stopAccessingSecurityScopedResource()
        }
        // TODO: Bookmark URL for future access
    }
}
