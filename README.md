<p align="center">
  <img src="https://github.com/Devansh-Maurya/TMDb/blob/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" />
  <h1 align="center">TMDb</h1>
</p>

#### An Android app to show movies information from The Movie Database (TMDb) API. The app also supports local data caching to show information when offline.

#### Download APK : [Google Drive Link](https://drive.google.com/file/d/13q8wkfU-GVgQLscUBdrTwzjGZqmGOHSQ/view?usp=sharing)

### Screenshots

| Trending Movies        | Movie Details           |
| ------------- |:-------------:|
| ![Trending Movies](https://github.com/Devansh-Maurya/TMDb/blob/master/screenshots/trending.png "Trending Movies") | ![Movie Details](https://github.com/Devansh-Maurya/TMDb/blob/master/screenshots/details.png "Movie Details") |

| Bookmarks        | Search           |
| ------------- |:-------------:|
| ![Bookmarks](https://github.com/Devansh-Maurya/TMDb/blob/master/screenshots/bookmarks.png "Bookmarks") | ![Search](https://github.com/Devansh-Maurya/TMDb/blob/master/screenshots/search.png "Search") |

### Technologies used 

* Java
* RxJava2
* Dagger2
* Room
* Retrofit, OkHttp, Gson
* Navigation Components
* MVVM Architecture Pattern with ViewModels and LiveData
* Pagination using Paging3 library
* Glide image loading library
* Deeplink to open movie details screen using deeplink **tmdbapp://movie/detail/{movieId}**
* Movie sharing functionality with movie poster, info and a deeplink
* Debounced search query on movie search screen
