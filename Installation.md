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
You do not need to include any of the libraries in the gradle files since the master repo that you will be pulling from will have the latest version of the libraries needed

After setting up Firebase, navigate to the console and do the following:
1. Under the `Authentication` tab, enable email address as the sign-in method
2. Under the `Database` tab, create a `Real-time Database` (you will have to scroll down a little), and run it in **test** mode

To test that the application is working, run the app and try making an account, you will see that a user is added and the database gets a new entry.