// این فایل فقط پلاگین‌های استفاده‌شده در کل پروژه رو تعریف می‌کنه؛
// اعمال واقعی‌شون توی app/build.gradle.kts انجام می‌شه
plugins {
    id("com.android.application") version "8.11.0" apply false
    id("org.jetbrains.kotlin.android") version "2.3.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.3.0" apply false
}
