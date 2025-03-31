package common.mapper

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.apparel
import a3.composeapp.generated.resources.gift_box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.AirplaneTicket
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Chair
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.HomeWork
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material.icons.outlined.Money
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.PhoneIphone
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material.icons.outlined.Stroller
import androidx.compose.material.icons.outlined.Subscriptions
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material.icons.outlined.Theaters
import androidx.compose.material.icons.outlined.Train
import androidx.compose.material.icons.outlined.WbIridescent
import androidx.compose.material.icons.outlined.Weekend
import common.A3Icon.MaterialIcon
import common.A3Icon.ResourceIcon
import common.domain.model.IconName
import common.domain.model.IconName.Book
import common.domain.model.IconName.Bus
import common.domain.model.IconName.Car
import common.domain.model.IconName.Charity
import common.domain.model.IconName.Coffee
import common.domain.model.IconName.CreditCard
import common.domain.model.IconName.Electricity
import common.domain.model.IconName.Entertainment
import common.domain.model.IconName.Food
import common.domain.model.IconName.Gift
import common.domain.model.IconName.Grocery
import common.domain.model.IconName.Gym
import common.domain.model.IconName.House
import common.domain.model.IconName.IPhone
import common.domain.model.IconName.Insurance
import common.domain.model.IconName.Loan
import common.domain.model.IconName.Map
import common.domain.model.IconName.Medical
import common.domain.model.IconName.MortarBoard
import common.domain.model.IconName.OfficeChair
import common.domain.model.IconName.Rent
import common.domain.model.IconName.Salary
import common.domain.model.IconName.Shopping
import common.domain.model.IconName.Stroller
import common.domain.model.IconName.Subscription
import common.domain.model.IconName.TShirt
import common.domain.model.IconName.Tag
import common.domain.model.IconName.Tax
import common.domain.model.IconName.Transportation
import common.domain.model.IconName.Vacation

val categoryIconMap = mapOf(
    Tag to MaterialIcon(Icons.Outlined.Tag),
    Food to MaterialIcon(Icons.Outlined.Fastfood),
    Bus to MaterialIcon(Icons.Outlined.DirectionsBus),
    Shopping to MaterialIcon(Icons.Outlined.ShoppingBag),
    TShirt to ResourceIcon(Res.drawable.apparel),
    IPhone to MaterialIcon(Icons.Outlined.PhoneIphone),
    Book to MaterialIcon(Icons.Outlined.Book),
    House to MaterialIcon(Icons.Outlined.WbIridescent),
    Map to MaterialIcon(Icons.AutoMirrored.Outlined.AirplaneTicket),
    Medical to MaterialIcon(Icons.Outlined.MedicalServices),
    MortarBoard to MaterialIcon(Icons.Outlined.School),
    Coffee to MaterialIcon(Icons.Outlined.Coffee),
    Gift to ResourceIcon(Res.drawable.gift_box),
    OfficeChair to MaterialIcon(Icons.Outlined.Chair),
    Stroller to MaterialIcon(Icons.Outlined.Stroller),
    CreditCard to MaterialIcon(Icons.Outlined.CreditCard),
    Transportation to MaterialIcon(Icons.Outlined.Train),
    Electricity to MaterialIcon(Icons.Outlined.Bolt),
    Rent to MaterialIcon(Icons.Outlined.HomeWork),
    Entertainment to MaterialIcon(Icons.Outlined.Theaters),
    Gym to MaterialIcon(Icons.Outlined.FitnessCenter),
    Insurance to MaterialIcon(Icons.Outlined.Shield),
    Loan to MaterialIcon(Icons.Outlined.Money),
    Salary to MaterialIcon(Icons.Outlined.Payments),
    Vacation to MaterialIcon(Icons.Outlined.Weekend),
    Car to MaterialIcon(Icons.Outlined.DirectionsCar),
    Grocery to MaterialIcon(Icons.Outlined.Storefront),
    Subscription to MaterialIcon(Icons.Outlined.Subscriptions),
    Charity to MaterialIcon(Icons.Outlined.Favorite),
    Tax to MaterialIcon(Icons.Outlined.Assessment)
)

fun stringToIconName(icon: String): IconName? {
    return IconName.entries.firstOrNull { it.realName == icon }
}
