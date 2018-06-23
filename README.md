# Quick Start for developers - Windows OS

#### Preparing environment

To begin the development process of backend project part you will need few things installed on your computer:
1. Java 8 version (JRE + JDK) - http://www.oracle.com/technetwork/java/javase/downloads/index.html
2. Gradle 4.7 build tool - https://gradle.org/install/
3. NoSQL MongoDB 3.6 Community Server - https://www.mongodb.com/download-center?jmp=nav#community 
4. Git version control system (check also Git bash during installation) - https://git-scm.com/downloads

and some IDE of course. I strongly recommend you IntelliJ IDEA from JetBrains.

Next helpful step is to set environment variables for installed tools. These are **default paths** which should be added to `PATH` environment variable under Windows OS. 
1. Add new environment variable (don't edit `PATH` variable yet) named `JAVA_HOME` to environment variables section with value `C:\Program Files\Java\<version>` (e.g. `jdk1.8.0_161`).
2. Edit `PATH` environment variable and add new value `%JAVA_HOME%\bin` for Java.
2. Add next value `C:\Program Files\Git\cmd` for Git.
4. Add next value `C:\Gradle\bin` for Gradle.
5. Add next value `C:\Program Files\MongoDB\Server\3.6\bin` for Mongo. Moreover it is necessary to provide following path: `C:\data\db`. If there is no such file in your filesystem, you must create it.

Now open Git bash which was installed togehter with Git. Go to your projects directory and paste this command to download repository:
```sh
$ git clone https://gitlab.com/gouomp/gouomp-backend.git
```
You will be requested about your GitLab account credentials. You can also generate your SSH keys, add public key to your GitLab account and download repository via SSH. 

Project uses Lombok to avoid Java boilerplate code. If you use IntelliJ IDEA it is helpful to enable Lombok plugin. Go to `File -> Settings -> Plugins -> Browse repositories`, type `Lombok Plugin` and install it. Now go to `Settings -> Build, Execution, Deployment -> Annotation Processors` and check `Enable annotation processing`. Finally restart IDE.

Now your development environment should work properly.

#### Running web application
When your development environment is ready, you can try to run project. Before you decide to run this web application, you must start mongo server first. Just type anywhere in **Windows CLI**:
```sh
$ mongod
```
If you installed everything correctly, mongod should serve you such information:`[initandlisten] waiting for connections on port 27017`. Don't close this terminal window, otherwise server will be stopped. Now open second terminal, go to main project directory where `gradle.build` file is located and type command:
```sh
$ gradlew build --stacktrace
```
*If build failed, try to run it second time. Sometimes Spring has problems with starting application context for unit tests*

When build will pass successfully type in console:
```sh
$ gradlew bootrun
```
Check if there are no mongo warnings in logs and ensure that last two lines look like this:

`[main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) ...`

`[main] p.e.w.g.GouompBackendApplication         : Started GouompBackendApplication in ...`

#### That's all - happy coding!

