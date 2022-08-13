# web-crawler-text-search
A web crawler that ingests a list of URLs and we can serach a text in it

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

# Pre-requisite -

- Download and set up git 2.21 or above.


- Require cmd/gitbash on your system.


- Download maven 3.8.6.


- Download eclipse 2018-12 or above.


- Download and install jdk 1.8 or above.


- Access to the remote repositories. You need to ask the admins if you don't have access.

# Setup Instruction -

Open cmd/gitbash.

Clone this project on your local drive.

- git clone "https://github.com/Kakoli45/web-crawler-text-search.git"


Then cd into the cloned folder and open cmd/gitbash inside, checkout the branch you require.

- git checkout -b "master"   -> (If you need to create a new branch for your task.)

# Run the application on your local on port 8080 -

- Open cmd in the project folder and run the maven command -> mvn clean install


- Wait for the successful completion of the build.


- Import the project in eclipse as existing maven project.


- Go to the DemoWebCrawlerApplication.java class and run as java application.


- The application will run on http://localhost:8080  (If that port is free). Tou can define another port too.

# Git commit process -

- git status   -> (To check all the files list where you have made changes)


- git stash save   -> (To stash your files before taking latest pull)


- git pull     -> (To take latest changes on your local. Also you can run -> "git pull origin master" depending on which branch you need to take a pull)


- git stash apply   -> (To apply your stashed files back to your local)


- Resolve the conflicts if any


- git status   -> (To check which are the tracked files, which are staged, which are unstaged.)


- git add .    -> (To add all the files)   OR


- git add 'filePath/fileName'   -> (To add only the required files)


- git commit -m "HB-yourJiraTaskId : a meaningful commit description"


- git push    -> (If your branch is existing)   OR


- git push --set-upstream origin yourBranchName


- Review your pushed changes in gitlab to verify.

# Run the API on your local on port 8080 -

curl --location --request GET 'http://localhost:8080/api/ingestUrl/searchText?ingestedUrlsArray=http://arstechnica.com/&searchTextsArray=computer' \
--header 'Content-Type: application/x-www-form-urlencoded'
