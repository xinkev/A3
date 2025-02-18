package core.file

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import android.webkit.MimeTypeMap
import com.xinkev.logger.log
import core.Outcome
import io.github.vinceglb.filekit.core.PlatformDirectory
import java.io.IOException

/**
 * Android-specific implementation of FileManager that handles file operations
 * using Android's Storage Access Framework (SAF).
 */
actual object FileManager {
    private lateinit var contentResolver: ContentResolver

    fun init(context: Context) {
        contentResolver = context.contentResolver
    }
    /**
     * Writes a file to the specified directory using Android's Storage Access Framework.
     * If file exists, it will be overwritten. If not, a new file will be created.
     */
    actual fun writeFile(
        data: ByteArray,
        directory: PlatformDirectory,
        fileName: String,
    ): Outcome<FileWriteError, Unit> {
        try {
            // Get or create file URI
            val fileUri = getOrCreateFile(directory, fileName) ?: 
                return Outcome.Error(FileWriteError.FileCreateFailed)

            // Write data to file
            return writeDataToFile(fileUri, data)
        } catch (e: SecurityException) {
            log.w { "Access denied: ${e.localizedMessage}" }
            return Outcome.Error(FileWriteError.AccessDenied)
        } catch (e: Exception) {
            log.w { "Unknown error: ${e.localizedMessage}" }
            return Outcome.Error(FileWriteError.Unknown)
        }
    }

    /**
     * Gets existing file URI or creates a new file and returns its URI.
     * This prevents duplicate files with (1), (2) etc. suffixes.
     */
    private fun getOrCreateFile(
        directory: PlatformDirectory,
        fileName: String
    ): Uri? {
        try {
            // Get the URI for the parent directory
            // - directory.uri points to a directory user granted access to via SAF
            // - getTreeDocumentId extracts the document ID from the tree URI
            // - buildDocumentUriUsingTree combines them to get the actual directory URI
            val parentUri = DocumentsContract.buildDocumentUriUsingTree(
                directory.uri,
                DocumentsContract.getTreeDocumentId(directory.uri)
            )

            return findExistingFile(parentUri, fileName) ?: createNewFile(parentUri, fileName)
        } catch (e: Exception) {
            log.w { "Failed to access/create file: ${e.localizedMessage}" }
            return null
        }
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
    private fun writeDataToFile(fileUri: Uri, data: ByteArray): Outcome<FileWriteError, Unit> {
        return try {
            contentResolver.openOutputStream(fileUri, "wt")?.use {
                it.write(data)
            } ?: return Outcome.Error(FileWriteError.AccessDenied)
            
            log.i { "File written successfully to ${fileUri.path}" }
            Outcome.Success(Unit)
        } catch (e: IOException) {
            log.w { "Failed to write file: ${e.localizedMessage}" }
            when {
                e.message?.contains("No space") == true -> 
                    Outcome.Error(FileWriteError.StorageSpaceInsufficient)
                else -> Outcome.Error(FileWriteError.FileWriteFailed)
            }
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
