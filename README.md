# RubyPurrs — Accesibilidad + minijuegos (Jetpack Compose)

RubyPurrs es una app Android escrita en **Kotlin** con **Jetpack Compose**. Nació como un proyecto
para acercar la lectura y escritura a personas con discapacidad visual, sin dejar de lado
la parte lúdica: incluye un pequeño “gatito virtual” con ronroneo, vibración y tres minijuegos.



## ¿Qué hace?

- **Modo Accesibilidad**
  - *Dictado a texto* con el servicio de reconocimiento del dispositivo.
  - *Lectura en voz alta* de cualquier texto que ingrese la persona (control de velocidad).
  - *Abrir .txt* desde el almacenamiento y leer su contenido.
- **Minijuegos accesibles**: caricias, seguir a Ruby y atrapar al ratón, con feedback háptico y sonido.
- **Autenticación local** con un arreglo de hasta 5 usuarios (solo con fines didácticos).
- **Navegación simple**: login, registro, recuperar contraseña, home, minijuegos y `access_hub`.



## Requisitos y versiones

- Android Studio (Narwhal / Ladybug o más nuevo) usando **JBR 21**.
- `compileSdk = 34`, `minSdk = 21`.
- Permisos en `AndroidManifest.xml`: `RECORD_AUDIO`, `INTERNET`, `VIBRATE`.
- Emulador sugerido: Pixel 6, API 36.



## Estructura rápida

```
app/
 └─ src/main/java/cl/kath/rubypurrs/
    ├─ core/            # Audio (purr), TTS, STT, Haptics
    ├─ data/            # AuthStore (usuarios en memoria)
    ├─ ui/
    │   ├─ AccessNav.kt # ACCESS_HUB_ROUTE + AccessHubScreen()
    │   └─ screens/     # Login, Register, Forgot, Home y minijuegos
    └─ NavGraph.kt


## Cómo ejecutar

1. Clonar el repo y abrir el proyecto en Android Studio.
2. Verificar que el **Gradle JDK** sea *jbr-21* (Settings ▸ Build Tools ▸ Gradle).
3. Sincronizar y compilar.
4. Ejecutar en emulador o dispositivo.



## Hub de Accesibilidad

- Ruta de navegación: **`access_hub`**.
- En la pantalla se puede:
  - Dictar con el micrófono → el resultado aparece en el campo de texto.
  - Tocar **“Leer en voz alta”** para usar TTS.
  - Abrir un archivo **.txt** y leerlo en pantalla (y también con TTS).

> Nota: el dictado depende de que el dispositivo tenga instalada una app de reconocimiento.



## Licencia

Uso educativo (MIT).
