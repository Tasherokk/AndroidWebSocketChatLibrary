# Android WebSocket Chat Library

📡 Простая библиотека чата на WebSocket для Android-приложений. Использует `wss://echo.websocket.org` как тестовый сервер.

## 📦 Подключение

Добавьте репозиторий GitHub Packages в `settings.gradle.kts` проекта:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Tasherokk/AndroidWebSocketChatLibrary")
            credentials {
                username = "Tasherokk"
                password = System.getenv("GITHUB_TOKEN") ?: "YOUR_PERSONAL_ACCESS_TOKEN"
            }
        }
    }
}
```

Затем добавьте зависимость в `build.gradle.kts` модуля `app`:

```kotlin
dependencies {
    implementation("io.github.tasherokk:androidwebsocketchatlibrary:1.0.4")
}
```

> ⚠️ Убедитесь, что у вас есть разрешение на интернет в `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

---

## 🚀 Использование

Запуск чата из любого `Activity`:

```kotlin
ChatLauncher.start(this)
```

Пример:

```kotlin
btnOpenChat.setOnClickListener {
    ChatLauncher.start(this)
}
```

---

## 🧪 Особенности

- Используется `OkHttp` WebSocket
- Сообщения пользователя отображаются справа, ответы сервера — слева
- Поддержка специальных сообщений:
  - `203 = 0xcb` → _Соединение активно (ping ответ от сервера)_
  - `Request served by ...` → _Добро пожаловать в тестовый чат_

---

## 🤝 Автор

**Tasherokk**  
[GitHub Profile](https://github.com/Tasherokk)
