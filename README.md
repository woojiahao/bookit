# BookIt!
A book discovery and search application tailored just for you!

This application leverages off the many public APIs that book stores have so as to find the books at the cheapest prices and display those for you, reducing your hassle when finding books at their cheapest!

## What is Git?
Refer to these resources if you are in doubt about using Git:

* [Pro Git](https://git-scm.com/book/en/v2)
* [Git Visualised](http://gitup.co/)
* [Video Tutorial](https://www.youtube.com/watch?v=Gg4bLk8cGNo&t=551s)

## Why are we using Git? 
It's good training for SEP and Git is one of the most widely used Version Control Systems (VCS) used in the industry right now.

## How to contribute?
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