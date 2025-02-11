package feature.category.categories

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
import common.domain.model.A3Icon
import common.domain.model.A3Icon.MaterialIcon
import common.domain.model.A3Icon.ResourceIcon
import feature.category.common.domain.model.CategoryIconName
import feature.category.common.domain.model.CategoryIconName.Book
import feature.category.common.domain.model.CategoryIconName.Bus
import feature.category.common.domain.model.CategoryIconName.Car
import feature.category.common.domain.model.CategoryIconName.Charity
import feature.category.common.domain.model.CategoryIconName.Coffee
import feature.category.common.domain.model.CategoryIconName.CreditCard
import feature.category.common.domain.model.CategoryIconName.Electricity
import feature.category.common.domain.model.CategoryIconName.Entertainment
import feature.category.common.domain.model.CategoryIconName.Food
import feature.category.common.domain.model.CategoryIconName.Gift
import feature.category.common.domain.model.CategoryIconName.Grocery
import feature.category.common.domain.model.CategoryIconName.Gym
import feature.category.common.domain.model.CategoryIconName.House
import feature.category.common.domain.model.CategoryIconName.IPhone
import feature.category.common.domain.model.CategoryIconName.Insurance
import feature.category.common.domain.model.CategoryIconName.Loan
import feature.category.common.domain.model.CategoryIconName.Map
import feature.category.common.domain.model.CategoryIconName.Medical
import feature.category.common.domain.model.CategoryIconName.MortarBoard
import feature.category.common.domain.model.CategoryIconName.OfficeChair
import feature.category.common.domain.model.CategoryIconName.Rent
import feature.category.common.domain.model.CategoryIconName.Salary
import feature.category.common.domain.model.CategoryIconName.Shopping
import feature.category.common.domain.model.CategoryIconName.Stroller
import feature.category.common.domain.model.CategoryIconName.Subscription
import feature.category.common.domain.model.CategoryIconName.TShirt
import feature.category.common.domain.model.CategoryIconName.Tag
import feature.category.common.domain.model.CategoryIconName.Tax
import feature.category.common.domain.model.CategoryIconName.Transportation
import feature.category.common.domain.model.CategoryIconName.Vacation

typealias CategoryIcon = Pair<CategoryIconName, A3Icon>

val categoryIcons = mapOf(
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
