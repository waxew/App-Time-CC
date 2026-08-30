# قوانین ProGuard/R8 برای نسخه release
# https://developer.android.com/build/shrink-code

# مدل‌های دیتا رو از obfuscation مستثنی می‌کنیم تا Gson درست کار کنه
-keep class com.bookingsystem.app.data.model.** { *; }
