package core

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import android.webkit.MimeTypeMap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.xinkev.logger.log
import io.github.vinceglb.filekit.core.PlatformDirectory
import java.io.IOException

/**
 * Android-specific implementation of FileManager that handles file operations
 * using Android's Storage Access Framework (SAF).
 */
actual class FileManager(context: Context) {
    private val contentResolver = context.contentResolver

    /**
     * Writes a file to the specified directory using Android's Storage Access Framework.
     * If file exists, it will be overwritten. If not, a new file will be created.
     */
    actual fun writeFile(
        data: ByteArray,
        directory: PlatformDirectory,
        fileName: String,
    ): Boolean {
        // Get or create the file URI, handling existence check
        val fileUri = getOrCreateFile(directory, fileName) ?: return false

        // Write the data to the file using "wt" mode (write + truncate)
        return writeDataToFile(fileUri, data)
    }

    /**
     * Gets existing file URI or creates a new file and returns its URI.
     * This prevents duplicate files with (1), (2) etc. suffixes.
     */
    private fun getOrCreateFile(
        directory: PlatformDirectory,
        fileName: String
    ): Uri? {
        // Get the URI for the parent directory
        // - directory.uri points to a directory user granted access to via SAF
        // - getTreeDocumentId extracts the document ID from the tree URI
        // - buildDocumentUriUsingTree combines them to get the actual directory URI
        val parentUri = DocumentsContract.buildDocumentUriUsingTree(
            directory.uri,
            DocumentsContract.getTreeDocumentId(directory.uri)
        )

        return findExistingFile(parentUri, fileName) ?: createNewFile(parentUri, fileName)
    }

    /**
     * Searches for an existing file in the given directory using SAF's query API.
     * Uses content resolver to search through directory contents.
     */
    private fun findExistingFile(
        parentUri: Uri,
        fileName: String
    ): Uri? {
        // Query child documents using DocumentsContract
        val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
            parentUri,
            DocumentsContract.getTreeDocumentId(parentUri)
        )
        
        return contentResolver.query(
            childrenUri,  // Use childrenUri instead of parentUri
            arrayOf(
                DocumentsContract.Document.COLUMN_DOCUMENT_ID,
                DocumentsContract.Document.COLUMN_DISPLAY_NAME
            ),
            null, null, null
        )?.use { cursor ->
            val nameColumn = cursor.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME)
            val idColumn = cursor.getColumnIndex(DocumentsContract.Document.COLUMN_DOCUMENT_ID)

            while (cursor.moveToNext()) {
                if (cursor.getString(nameColumn) == fileName) {
                    val documentId = cursor.getString(idColumn)
                    return@use DocumentsContract.buildDocumentUriUsingTree(
                        parentUri,
                        documentId
                    )
                }
            }
            null
        }
    }

    /**
     * Creates a new file in the given directory using SAF's createDocument API.
     * - Needs ContentResolver to interact with document provider
     * - Uses parent URI to know where to create the file
     * - Requires MIME type derived from file extension
     * - Uses provided file name for the new document
     */
    private fun createNewFile(
        parentUri: Uri,
        fileName: String
    ): Uri? {
        val mimeType = getMimeTypeFromFileName(fileName) ?: "*/*"
        return DocumentsContract.createDocument(contentResolver, parentUri, mimeType, fileName)
    }

    /**
     * Writes data to a file using SAF's openOutputStream.
     * Uses "wt" mode where:
     * - 'w' means write mode
     * - 't' means truncate (overwrite existing content)
     */
    private fun writeDataToFile(fileUri: Uri, data: ByteArray): Boolean {
        return try {
            contentResolver.openOutputStream(fileUri, "wt")?.use {
                it.write(data)
            }
            log.i { "File written successfully to ${fileUri.path}" }
            true
        } catch (e: IOException) {
            log.w { "Failed to write file: ${e.localizedMessage}" }
            false
        }
    }
}

/**
 * Determines the MIME type of a file based on its extension.
 *
 * @param fileName The name of the file including extension
 * @return The MIME type string or null if unknown
 */
private fun getMimeTypeFromFileName(fileName: String): String? {
    val extension = fileName.substringAfterLast('.', "").lowercase()
    return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
}

@Composable
actual fun rememberFileManager(): FileManager {
    val context = LocalContext.current
    return remember { FileManager(context) }
}
