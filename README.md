# periplus-web-test

## Description

This project contains automated tests for the Periplus online bookstore (https://www.periplus.com/). The tests focus on verifying the shopping cart functionality.  Specifically, the tests cover the process of adding a product to the cart and confirming that the product is successfully added.  The tests are implemented using Java, Selenium for web browser automation, and TestNG for test execution.  This project also requires a registered user account on Periplus for proper test execution.

## Getting Started

These instructions will get you up and running with the project on your local machine.

### Prerequisites

* [Java][Java-url] (JDK 21/LTS)
* [Gradle][Gradle-url] (latest)

### Installation

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/farrelayman09/periplus-web-test.git
    cd periplus-web-test
    ```

2.  **Set up the project:**

    * This project uses Gradle to manage dependencies and build the application.  No additional setup steps should be required.  Ensure that your JAVA_HOME environment variable is set correctly.

3.  **Change the username and password that's used to sign in**
    * Inside the ```src/test/java/org.example/AppTest``` file, change the ```EMAIL``` and ```PASSWORD``` variables accordingly to match your Periplus account

4.  **Build the project:**

    ```bash
    ./gradlew build
    ```
    * This command will download dependencies and compile the project.
    * There's no need to run the project since the build process will involve executing the AppTest class

<!-- MARKDOWN LINKS & IMAGES -->
[Java-url]: https://www.oracle.com/id/java/technologies/downloads/
[Gradle-url]: https://github.com/gradle/gradle