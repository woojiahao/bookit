# BookIt!
An open-source project for a book searching application on Android.

BookIt! leverages off of public APIs provided by book services to find the cheapest prices for books as well as to allow users to store books that they like.

***

## Table of Contents:
1. [APIs](https://github.com/woojiahao/bookit/blob/master/Project%20Guide.md#apis)
2. [Git](https://github.com/woojiahao/bookit/blob/master/Project%20Guide.md#git)
3. [Firebase](https://github.com/woojiahao/bookit/blob/master/Project%20Guide.md#firebase)
4. [Contributing](https://github.com/woojiahao/bookit/blob/master/Project%20Guide.md#contributing)

***

### APIs
BookIt utilises [public book APIs](https://www.programmableweb.com/news/53-books-apis-google-books-goodreads-and-sharedbook/2012/03/13) in order to load book data and provide users with information about a book.

#### APIs Used
**Book Information:**
* [Google Books API](https://developers.google.com/books/)

**Best-Sellers:**
* [New York Times API](https://developer.nytimes.com/)

**Prices**
* [GoodReads API](https://www.goodreads.com/api)

#### API resources:
* [What is an API?](https://www.youtube.com/watch?v=s7wmiS2mSXY)
* [REST API Concepts](https://www.youtube.com/watch?v=7YcW25PHnAA)
* [HTTP](https://code.tutsplus.com/tutorials/http-the-protocol-every-web-developer-must-know-part-1--net-31177)
* [HTTP and REST](https://www.youtube.com/watch?v=Q-BpqyOT3a8&t=6s)
* [Fetching data from an API using Java](https://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/)

#### Setting up APIs:
In order to access the previous APIs, you will need to set up accounts on the previous sites as a developer and request for API keys.

**Note:** For the Google Books API, you will need to create a new project, enable the Google Books API, add an API key and restrict it for Android apps. The general steps can be found [here](https://developers.google.com/maps/documentation/android-sdk/signup)

Now that you have API keys for the APIs used, follow the following steps to get the application to use the APIs:

1. Navigate to the `assets` folder in Android Studio
2. Create a new file, called `config.json`
3. Inside of config.json, add the following:
```json
{
    "google-books-api": "enter your key",
    "new-york-times-api": "enter your key",
    "good-reads-api": "enter your key"
}
```
4. Run the application

***

### Git
#### Git Resources:
* [Pro Git](https://git-scm.com/book/en/v2)
* [Git Visualised](http://gitup.co/)
* [Video Tutorial](https://www.youtube.com/watch?v=Gg4bLk8cGNo&t=551s)

#### How to contribute?
1. Make a fork of this repository onto your own GitHub
2. After you have made the fork, clone it into your local machine
3. Then, make a remote to this repository
4. Every time there is a an update made to this repository, fetch and pull from the remote to this repository
5. When you wish to make a change, add the change to your local copy, commit it and then make a Pull Request to this repository, I will take a look before accepting the changes

#### Set up commands:
```bash
git clone https://github.com/<github username>/bookit.git
git remote add upstream https://github.com/woojiahao/bookit.git
git fetch
git pull origin master
```

### Contribution commands:
```bash
git add .
git commit -m "Commit message blah blah"
git push origin master
```

***

### Firebase
#### Firebase Resources:
* [NoSQL Introduction](https://www.mongodb.com/nosql-explained)
* [Firebase](https://firebase.google.com/)

#### Setting up:
The general steps for Firebase is well-written during the Firebase Installation Guide for Android, so I will only be providing the information to fill in (those that aren't specified can be left to the defaults):

**Project Name:** `BookIt`

**Billing Region:** `Singapore`

**Package Name:** `team.android.projects.com.bookit`

**App Nickname:** `BookIt`

**Debug signing certificate SHA-1**: [Follow these instructions to generate it](https://stackoverflow.com/questions/15727912/sha-1-fingerprint-of-keystore-certificate)

Detailed guides:
* [Firebase Guide for Android](https://firebase.google.com/docs/android/setup)

After setting up Firebase, navigate to the console and do the following:
1. Under the `Authentication` tab, enable email address as the sign-in method
2. Under the `Database` tab, create a `Real-time Database` (you will have to scroll down a little), and run it in **test** mode

***

### Contributing
1. Look through the issues and pick one that you can manage. If you are a beginner, you can filter the `good first issue` tag so as to view the issues that are much more manageable for a beginner.
2. Find a feature/part of the code that you think can be improved or implemented an open an issue, I will take a look and if it is ideal, it will be approved and you can begin work on it.
3. Documenting the code

***