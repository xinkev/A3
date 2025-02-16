-dontwarn org.slf4j.**
-keep class kotlinx.coroutines.swing.SwingDispatcherFactory
-keep class org.sqlite.** { *; }

#FileKit
-keep class com.sun.jna.** { *; }
-keep class * implements com.sun.jna.** { *; }

-keep class navigation.Route { *; }
-keep class navigation.Route$* { *; }
-keepnames class * implements navigation.Route {
    *;
}
-keepnames class com.notkamui.keval.KevalOperator
# Keep `INSTANCE.serializer()` of serializable objects.
-if @kotlinx.serialization.Serializable class ** {
    public static ** INSTANCE;
}
-keep @kotlinx.serialization.Serializable class * {*;}
