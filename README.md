# Upcoming Movies App

Android app that displays the upcoming movies using The Movie Database (TMDb) API. 

# Setup

## Introducing the TMDb API
This app was developed using __The Movie Database (TMDb) API__. The API is very extensive and very well documented and
provides for us developers some endpoints to retrieve info from movies like title, release date, overview, poster image,
backdrop image and much more. I'll encourage you to read the [documentation](https://developers.themoviedb.org/) to see more details
about the endpoints, authorization and other stuffs.
### Getting TMDb API Key
But first, you will need to create an developer account to get the API Key
that must be sent on every request to API's endpoints.
- Go to [TMDb Developer Portal](https://developers.themoviedb.org/) and read from there
how to get your API Key.
- Fill all your information and then will be redirected to your account page. Please copy the
API Key because you will need them on your requests.
### Cloning
Now you can clone the repo. You can use any Git clients or just type this in command line:

    git clone git https://github.com/ramonrabello/Upcoming-Movies-App.git

### Setting up API keys
Go to the `build.gradle` file and search for these two build config fields:

    buildConfigField "String", "API_KEY", '"GET_YOUR_API_KEY_AT_TDMB_DEVELOPER_PORTAL"'
   

Just copy/paste your API keys and sync your project. Congratulations, now you can run the app!

# Project Architecture

The project was built using the __Model-View-ViewModel (MVVM)__ presentation pattern that eases
the development by decoupling the app layers by providing more testability and promoting more
reuse. The main difference between the other presentation patterns like the Model-View-Presenter (MVP) is that the MVVM has a reactive approach and
implements the Observer pattern, where the UI observes model changes that are notified by ViewModel
to update the UI. The overview of the MVVM presentation pattern is shown in the following image.
![MVVM Presentation Pattern](https://github.com/ramonrabello/Marvel-Favorite-Hero-App/blob/master/screenshots/mvvm-architecture.png)

# Project Structure
The project is entirely written using [Kotlin language](https://kotlinlang.org) and it was created based on _feature package_ convention:
- __core:__ All classes related the the listing of movies.
- __core/ktx:__ Some Kotlin Extension Functions for overall project.
- __data:__ All classes related to _data_ like repository implementations (local and/or remote).
- __details:__ All classes related the _details_ of a movie.
- __di:__ All classes related to Dependency Injection (DI) using Dagger 2 like `@Module`s, `@Component`s, `@ContributesAndroidInjector`s, etc.
- __home:__ All classes related the the _listing_ of movies.
- __model:__ Package that holds all JSON models. 
for `Adapter`, `ViewHolder`, etc.
- __view:__ Contains all classes related to the view layer like 
`Activities`, `Fragments`, `Adapters`, ...

The following image shows how the project is structured in terms of components level.
![Project Architecture](https://github.com/ramonrabello/Marvel-Favorite-Hero-App/blob/master/screenshots/arch-components-architecture.png)

# Libraries

The file `buildsystem/dependencies.gradle` contains all the library dependencies used
in the project. It is organized by `region`s according to context like support library,
architecture, UI, network, testing (unit and instrumentation).

## Kotlin
- Kotlin Standard Library 1.2.51

## Support Library 
- appcompat-v7 27.1.1
- Design 27.1.1
- cardview-v7 27.1.1
- support-fragment 27.1.1
- constraint-layout 1.1.0

## UI
- Glide 4.7.1
- Glide Compiler 4.7.1 (for kapt)

## Network
- Moshi
- OkHttp 3
- Retrofit 2
- RxJava 2
- Okio 1.14.0

## Architecture
- Architecture Components (Lifecycle, Room & LiveData) 
- Dagger 2
## Testing
### Unit Testing
- JUnit 4.1.2
- Mockito-Android 2.19.0
- Hamcrest 1.3
### Instrumentation Tests
- espresso-core 3.0.2
- espresso-intents 3.0.2
- espresso-contrib 3.0.2
- espresso-idling-resource 3.0.2

# License
    Copyright 2018 Ramon Rabello
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.