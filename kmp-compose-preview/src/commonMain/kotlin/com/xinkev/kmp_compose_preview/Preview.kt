@file:Suppress("PackageDirectoryMismatch")

package androidx.compose.desktop.ui.tooling.preview

@Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.FUNCTION
)
annotation class Preview

// Workaround for previews not working commonApp
