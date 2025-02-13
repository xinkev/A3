package common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource
import kotlin.jvm.JvmInline

sealed interface A3Icon {
    @Composable
    fun vector(): ImageVector

    @JvmInline
    value class MaterialIcon(val icon: ImageVector) : A3Icon {
        @Composable
        override fun vector(): ImageVector = remember { icon }
    }

    @JvmInline
    value class ResourceIcon(val icon: DrawableResource) : A3Icon {
        @Composable
        override fun vector(): ImageVector = vectorResource(icon)
    }
}


