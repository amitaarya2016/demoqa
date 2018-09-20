Demo QA test
==============


This repository contains Tests using TestNG and Webdriver API, with Allure as reporting framework
-------------------------------------------------------------------------------------------------

**Prerequisites (download and install):**
-----------------------------------------
1. JAVA JDK 8+ (Java SE Dev Kit **) (JDK 10 is not supported)
2. [Git Bash]( https://git-scm.com/downloads) - optional, required only for Windows
3. Clone the repository by running -
```$ git clone https://github.com/amitaarya2016/demoqa```
   The project folder will be created with the source code in the directory where this command is run from.
4. HomeBrew (On MAC) http://brew.sh/
5. Chromedriver
  1. Windows- https://sites.google.com/a/chromium.org/chromedriver/downloads
  The following line of code needs to be added to the base class :
  ```System.setProperty("webdriver.chrome.driver", "pathofchromedriver\\chromedriver.exe");```
  2. Mac - `$ brew tap caskroom/cask && brew cask install chromedriver`
6. intelliJ IDE Community Edition (http://www.jetbrains.com/idea/download/)
7. Maven -
  1. Windows- Install maven from https://maven.apache.org/download.cgi.
  2. Mac - open terminal and cd to the project directory created in step 3 and run the following commands-
   `$ brew install maven`  
   `$ mvn clean install` (cd into the project (demoqa) folder to run this command)
8. Install Allure reports (https://github.com/allure-framework/allure2).
   1. For Windows, Allure is available from the Scoop commandline-installer. To install Allure, download and install Scoop and then execute in the Powershell:
    ```scoop install allure```

   2. For MAC using Homebrew by running :
    ```$ brew untap qameta/allure```
    ```$ brew update```
    ```$ brew install allure```
    
**Project Setup**
-----------------
1. Open pom.xml file (present in the demoqa project cloned in step 3 of prerequisites) with IntelliJ, this will ensure that the project is recognized as a maven project
2. If there is an issue with the project setup or modules, go to File > Project Structure > Modules. Make sure there is a module for demoqa, if not add one. Click new > JDK > find your JDK folder (on Mac- usually at /Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home) > apply and it should start indexing
3. If there is an issue with maven go to View > Tool Windows > Maven Projects and reimport or add a project by selecting the pom.xml


**Reporting**
--------------
1. 
 
 To run reports after a test run, run the following command from the project base directory- 
 ```allure serve```
 
 **Build a docker image and run**
 ------------------------
 
 docker build -t testimage .
 
 docker run -d \
   --name=<test_image> \
   -v <host path>/allure-results:/usr/bin/app/allure-results \
   testimage:latest




