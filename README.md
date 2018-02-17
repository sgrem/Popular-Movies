# API key required

To fetch popular movies, this app uses the API from themoviedb.org.
* If you don’t already have an account, you will need to create one in order to request an API Key.
* In your request for a key, state that your usage will be for educational/non-commercial use. You will also need to provide some personal information to complete the request. Once you submit your request, you should receive your key via email shortly after.
* Add the api to the app as a buildconfig property API_KEY
	* gradle.properties: API_KEY="TooManySecrets"
	* app/build.gradle: in defaultConfig add
		* buildConfigField("String", "API_KEY", API_KEY)

* Before committing this change, add gradle.properties to .gitignore to prevent the key from being pushed and shared on github


# Project Overview
The following is the project overview from Udacity "Welcome to Popular Movies, Stage 1"

Most of us can relate to kicking back on the couch and enjoying a movie with friends and family. In this project, you’ll build an app to allow users to discover the most popular movies playing. We will split the development of this app in two stages. First, let's talk about stage 1.

In this stage you’ll build the core experience of your movies app.

Your app will:

* Present the user with a grid arrangement of movie posters upon launch.
* Allow your user to change sort order via a setting:
	* The sort order can be by most popular or by highest-rated
* Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
	* original title
	* movie poster image thumbnail
	* A plot synopsis (called overview in the api)
	* user rating (called vote_average in the api)
	* release date
## Why this Project?
To become an Android developer, you must know how to bring particular mobile experiences to life. Specifically, you need to know how to build clean and compelling user interfaces (UIs), fetch data from network services, and optimize the experience for various mobile devices. You will hone these fundamental skills in this project.

By building this app, you will demonstrate your understanding of the foundational elements of programming for Android. Your app will communicate with the Internet and provide a responsive and delightful user experience.

## What Will I Learn After Stage 1?
* You will fetch data from the Internet with theMovieDB API.
* You will use adapters and custom list layouts to populate list views.
* You will incorporate libraries to simplify the amount of code you need to write