# Muze
![Screen 1](/screenshots/Collage1.png "Screen 1")
![Screen 2](/screenshots/Collage2.png "Screen 2")

Client application for Muze REST Server API
[MuzeServer](https://github.com/andrew-andrushchenko/MuzeServer)

> **Note**  
> Muze was tested in a local network environment, thats why the next line is present in the app's Manifest:
> ```
> android:usesCleartextTraffic="true"
> ```
> Make sure to remove it if you're going to run your server using HTTPS

# Technology stack
* Kotlin
* Jetpack Compose (with Material You)
* Coroutines
* Dagger Hilt
* Retrofit
* Paging 3
