# Jakarta EE Starter

This is a scaffolding application based on Jakarta EE 9.1.

When starting a Jakarta EE project, it is time-consuming to build the environment. With this project, you can start your Jakarta EE project immediately. And provide some example application implementations.

The application only depends on the Jakarta EE API.


## Usage

To start the application, execute the following command:

```shell
$ ./gradlew run
```

This application uses Java17. If not installed, Gradle's toolchain support will automatically install Java17.

To exit the application, press Ctrl + C.


## Detection of source changes

Once the application is launched, changes to the source code are monitored. Changes to web resources under web/src/main/webapp are immediately reflected in the application.

Changes to the Java code are reflected by updating the archive. Execute the following commands in another terminal.

```shell
$ ./gradlew war
```

When archive file changes are detected, application is automatically redeployed


## Using EAR

By default, WAR files are handled. If you want to use the EAR module, change the settings.gradle.kts file as follows:

```kotlin
rootProject.name = "jakartaee-starter"
// include("domain", "app", "web", "dev", "modelgen-enhancer")
include("domain", "app", "web", "ear", "dev", "modelgen-enhancer")
```

With the above change, EAR files will be handled. However, some CDI examples will no longer work.

The EAR module is reflected by the following command:

```shell
$ ./gradlew ear
```


## Hello Examples

Some example of Hello World.

|Name| Description                                             |
|---|---------------------------------------------------------|
|Hello Servlet| Simplest servlet example. See `HelloServlet.java`       |
|Hello Resource| Simplest JAX-RS example. See `HelloResource.java`       |
|Hello EJB Resource| JAX-RS with EJB service example. See `HelloResource.java` |
|Hello CDI Resource| JAX-RS with CDI service example. See `HelloResource.java` |


## Basic Examples

Some example of basic JavaEE usage.

For more information of persistence definition, see `DataSourceInitializer.java` and `persistence.xml`.

|Name| Description                                                                                                  |
|---|--------------------------------------------------------------------------------------------------------------|
|Simple EJB service| Usage of EJB and JSF(html5 friendly markup) with JPA. See `CustomerModel.java`                               |
|Simple CDI service| Usage of CDI and JSF(ajax and composition template) with JPA. See `ProjectModel.java`                        |
|Paging query| Usage of Paging Query library. See `EmployeeModel.java`|
|Form Authentication| Usage of Security API with `@CustomFormAuthenticationMechanismDefinition` `@DatabaseIdentityStoreDefinition` |


## Advanced Examples


|Name| Description                                                           |
|---|-----------------------------------------------------------------------|
|File Upload| File Upload Download example. See `UploadFileModel.java`                |
|Simple Web Socket| Simple WebSocket endpoint example. See `WebSocketEndPoint.java`       |
|CDI Event| CDI Event example. See `CdiEventService.java` `CdiEventObserver.java` |

