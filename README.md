# Muze
![Screen 1](/screenshots/Collage1.png "Screen 1")
![Screen 2](/screenshots/Collage2.png "Screen 2")

Art gallery client for
[Muze REST Server](https://github.com/andrew-andrushchenko/MuzeServer) API.

# Build
Add these lines to the project's **local.properties** file:
```
base_debug_url="[YOUR SERVER DEBUG API URL]"
base_release_url="[YOUR SERVER RELEASE API URL]"
```

> **Note**  
> Muze was tested in a local network environment not using HTTPS, thats why the next line is present in the app's Manifest:
> ```
> android:usesCleartextTraffic="true"
> ```
> Make sure you remove it if you're going to run your server using HTTPS

# Technology stack
* Kotlin
* Jetpack Compose (with Material You)
* Coroutines
* Dagger Hilt
* Retrofit 2
* Paging 3

# Special thanks
[Albert Chang](https://github.com/mxalbert1996) and [Tlaster](https://github.com/Tlaster)
for the [Zoomable compostable](https://github.com/mxalbert1996/Zoomable)
# License
```
MIT License
Copyright (c) 2021 Tlaster
Copyright (c) 2022 Albert Chang
Copyright (c) 2023 Andrii Andrushchenko

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
