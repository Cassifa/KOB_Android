// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}
buildscript {
    dependencies {
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.38.1")// 导入gradle-plugin 字节码插庄
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
