## Set up your IntelliJ workspace

In the exercises that follow we are going to use IntelliJ Community edition. 
You can download it from: https://www.jetbrains.com/idea/download/


1.	On the opening screen "Check out from version control" (with git): 
git URL is https://github.com/vargadan/sprg-v-bank.git
![](images_IntelliJSetup/Picture%201.png)
1.	Enter the URL of the git repository: 
![](images_IntelliJSetup/Picture%202.png)
1.	Click 'Yes' to create project based on the pom.xml:
![](images_IntelliJSetup/Picture%203.png)
1.	Click on "Add Configuration" in the right-upper corner:
![](images_IntelliJSetup/Picture%204.png)
1.	Add a new maven task configuration to build and run the project
![](images_IntelliJSetup/Picture%205.png) 
(make sure that you click on add new configuration instead of modifying the template):
1.	Change name (v-bank run and build) and command line (package spring-boot:run):
![](images_IntelliJSetup/Picture%206.png) 
1.	Build and start the app in debug mode:
![](images_IntelliJSetup/Picture%207.png)
1.	The IntelliJ screen should now look like:
![](images_IntelliJSetup/Picture%208.png)
1.	And if you open http://localhost:8080/ in your browser you should see.
![](images_IntelliJSetup/Picture%209.png)
 
