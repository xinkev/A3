package mapper

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.apparel
import a3.composeapp.generated.resources.gift_box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.AirplaneTicket
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Chair
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material.icons.outlined.PhoneIphone
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.Stroller
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material.icons.outlined.WbIridescent
import presentation.model.A3Icon
import presentation.model.Category
import presentation.model.MaterialIcon
import presentation.model.ResourceIcon

fun mapSqlResultToCategory(name: String, icon: String): Category {
    return Category(
        name, mapIcon(icon)
    )
}

private fun mapIcon(icon: String): A3Icon? = when (icon) {
    "tag" -> MaterialIcon(Icons.Outlined.Tag)
    "food" -> MaterialIcon(Icons.Outlined.Fastfood)
    "bus" -> MaterialIcon(Icons.Outlined.DirectionsBus)
    "shopping" -> MaterialIcon(Icons.Outlined.ShoppingBag)
    "tshirt" -> ResourceIcon(Res.drawable.apparel)
    "iphone" -> MaterialIcon(Icons.Outlined.PhoneIphone)
    "book" -> MaterialIcon(Icons.Outlined.Book)
    "house" -> MaterialIcon(Icons.Outlined.WbIridescent)
    "map" -> MaterialIcon(Icons.AutoMirrored.Outlined.AirplaneTicket)
    "medical" -> MaterialIcon(Icons.Outlined.MedicalServices)
    "mortar-board" -> MaterialIcon(Icons.Outlined.School)
    "coffee" -> MaterialIcon(Icons.Outlined.Coffee)
    "gift" -> ResourceIcon(Res.drawable.gift_box)
    "office-chair" -> MaterialIcon(Icons.Outlined.Chair)
    "stroller" -> MaterialIcon(Icons.Outlined.Stroller)
    "credit-card" -> MaterialIcon(Icons.Outlined.CreditCard)
    else -> null
}