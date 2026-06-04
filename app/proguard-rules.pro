# app/proguard-rules.pro
# Keep model classes for Gson serialization
-keep class com.firstbank.app.data.remote.dto.** { *; }
-keep class com.firstbank.app.domain.model.** { *; }

# Keep Room entities
-keep class com.firstbank.app.data.local.entity.** { *; }

# Retrofit
-keepattributes Signature
-keepattributes Exceptions
-keep class retrofit2.** { *; }
-keepclassmembers,allowobfuscation class * {
    @retrofit2.http.* <methods>;
}

# Hilt
-keep class dagger.hilt.** { *; }

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}