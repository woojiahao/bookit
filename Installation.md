# Project Guide
Tutorial for contributing to this project

## Table of Contents:
1. [Git](https://github.com/woojiahao/bookit/blob/master/Installation.md#what-is-git)
2. [Firebase](https://github.com/woojiahao/bookit/blob/master/Installation.md#what-is-firebase)

## What is Git?
Refer to these resources if you are in doubt about using Git:

* [Pro Git](https://git-scm.com/book/en/v2)
* [Git Visualised](http://gitup.co/)
* [Video Tutorial](https://www.youtube.com/watch?v=Gg4bLk8cGNo&t=551s)

### Why are we using Git? 
It's good training for SEP and Git is one of the most widely used Version Control Systems (VCS) used in the industry right now.

### How to contribute?
1. Make a fork of this repository onto your own GitHub
2. After you have made the fork, clone it into your local machine 
3. Then, make a remote to this repository
4. Every time there is a an update made to this repository, fetch and pull from the remote to this repository
5. When you wish to make a change, add the change to your local copy, commit it and then make a Pull Request to this repository, I will take a look before accepting the changes

**Important note!**
 
When adding files, if you wish to add all the files at once, please use `git add .`, not `git add *`.

The first one takes into consideration the `.gitignore` file whilst the second doesn't. If you accidentally make a pull request with the wrong type of add, I will decline the request.

### Set up commands:
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

## What is Firebase
Firebase is a gay way to store data that gives me endles headaches, but regardless, it will be the method of database storage as well as user authentication that we will be using for the application.

Firebase is something known as a NoSQL database, which means that it does not use conventional SQL queries to store/retrieve data, instead, firebase uses JSON to store the data.

* [NoSQL Introduction](https://www.mongodb.com/nosql-explained)
* [Firebase](https://firebase.google.com/)

## How to setup Firebase
Firebase requires the use of a unique API key, however, it is not advisible to store your API keys on a public repository on GitHub as that can be hijacked by malicious users and used for unwanted reasons, therefore, you will need to create a unique set of credentials for your own copy of the application in order for this to work.

Before beginning, ensure that you have the latest version of the code:

```bash
git fetch upstream master
git merge upstream/master

# OR

git pull upstream master
```

### Steps:
The general steps for Firebase is well-written during the Firebase Installation Guide for Android, so I will only be providing the information to fill in (those that aren't specified can be left to the defaults):

**Project Name:** `BookIt`

**Billing Region:** `Singapore`

**Package Name:** `team.android.projects.com.bookit`

**App Nickname:** `BookIt`

**Debug signing certificate SHA-1**: [Follow these instructions to generate it](https://stackoverflow.com/questions/15727912/sha-1-fingerprint-of-keystore-certificate)

Detailed guides:
* [Firebase Guide for Android](https://firebase.google.com/docs/android/setup)

**Important Note:**
* In addition to the library you included in the `app` folder's `build.gradle` file, you will need these libraries:

```
dependencies {
    // ...
    implementation 'com.google.firebase:firebase-core:16.0.1'
    implementation "com.google.firebase:firebase-auth:16.0.2"
    implementation "com.google.firebase:firebase-database:16.0.1"
    implementation 'com.google.firebase:firebase-crash:16.0.1'
    // ...
}
```

* When you are in the Firebase instance console for the project, navigate to `Database` and select `Real-time Database` and find the tab for `Rules`, after that, copy over the following code into the editing area:

```json
{
  /* Visit https://firebase.google.com/docs/database/security to learn more about security rules. */
  "rules": {
    ".read": true,
    ".write": true
  }
}
```

After this setting up firebase, you will be able to use the application on your own device and begin to work on the other components as well.