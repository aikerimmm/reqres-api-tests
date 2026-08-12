# Reqres API Test Automation

<p align="center">
  <img src="images/reqres.png" alt="Reqres API Test Automation" width="100%">
</p>

<p align="center">
  API test automation project built with Java, REST Assured, JUnit 5, Gradle, Allure Report, Jenkins and Telegram notifications.
</p>

---

## Technology Stack

<p align="center">
  <img width="55" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" title="Java"/>
  &nbsp;&nbsp;
  <img width="55" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/gradle/gradle-original.svg" title="Gradle"/>
  &nbsp;&nbsp;
  <img width="55" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/jenkins/jenkins-original.svg" title="Jenkins"/>
  &nbsp;&nbsp;
  <img width="55" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/github/github-original.svg" title="GitHub"/>
  &nbsp;&nbsp;
  <img width="55" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/intellij/intellij-original.svg" title="IntelliJ IDEA"/>
</p>

<p align="center">
  <b>REST Assured</b> •
  <b>JUnit 5</b> •
  <b>Jackson</b> •
  <b>Lombok</b> •
  <b>Allure Report</b> •
  <b>FreeMarker</b> •
  <b>Telegram Bot API</b>
</p>

---

## Description

This project is designed for automated testing of the Reqres REST API.

It covers the main user-related API operations, including creating, retrieving, updating and deleting users, as well as negative scenarios.

The project demonstrates API test automation architecture using reusable specifications, DTO models, Allure reporting, CI execution through Jenkins and automated Telegram notifications.

### Project Features

* API test automation using `Java`
* `REST Assured` for HTTP requests and response validation
* `JUnit 5` as the test framework
* `Gradle` for project build and dependency management
* DTO models for request serialization and response deserialization
* `Lombok` for reducing boilerplate code
* `Jackson` for JSON serialization and deserialization
* Reusable `RequestSpecification` and `ResponseSpecification`
* Positive and negative API scenarios
* API key configuration through environment variables
* Sensitive data stored securely in Jenkins Credentials
* Integration with `Allure Report`
* `Allure REST Assured` listener with custom FreeMarker templates
* Automated test execution using `Jenkins`
* Pipeline configuration stored in `Jenkinsfile`
* Automatic Telegram notifications after Jenkins builds
* Dynamic test result statistics
* Automatic test result chart generation for Telegram reports

---

## Test Coverage

The project contains automated tests for the main user management API operations.

| Test Scenario         | HTTP Method | Endpoint       | Expected Status |
| --------------------- | ----------- | -------------- | --------------: |
| Create a new user     | POST        | `/users`       |             201 |
| Get users list        | GET         | `/users`       |             200 |
| Get single user       | GET         | `/users/2`     |             200 |
| Get non-existing user | GET         | `/users/99999` |             404 |
| Update user           | PUT         | `/users/2`     |             200 |
| Delete user           | DELETE      | `/users/2`     |             204 |

The suite contains both positive and negative API scenarios.

---

## Project Structure

```text
reqres-api-tests
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
│
├── images
│   ├── reqres.png
│   ├── allure-overview.png
│   ├── allure-test-details.png
│   ├── jenkins-allure.png
│   └── telegram-report.png
│
├── src
│   └── test
│       ├── java
│       │   └── qa
│       │       └── guru
│       │           └── reqres
│       │               ├── models
│       │               │   ├── CreateUserRequestDto.java
│       │               │   ├── CreateUserResponseDto.java
│       │               │   ├── UpdateUserRequestDto.java
│       │               │   ├── UpdateUserResponseDto.java
│       │               │   ├── UserDto.java
│       │               │   └── UserSearchResponseDto.java
│       │               │
│       │               ├── specs
│       │               │   └── ReqresSpec.java
│       │               │
│       │               └── tests
│       │                   └── ReqresApiTests.java
│       │
│       └── resources
│           ├── tpl
│           │   ├── request.ftl
│           │   └── response.ftl
│           │
│           └── allure.properties
│
├── .gitignore
├── build.gradle
├── gradlew
├── gradlew.bat
├── Jenkinsfile
├── README.md
└── settings.gradle
```

---

## API Models

DTO models are used for request body serialization and response deserialization.

### Request Models

Examples:

```text
CreateUserRequestDto
UpdateUserRequestDto
```

These models are used for generating request bodies for `POST` and `PUT` requests.

Example:

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDto {

    private String name;
    private String job;
}
```

### Response Models

Response DTOs are used to deserialize JSON responses into Java objects.

Examples:

```text
CreateUserResponseDto
UpdateUserResponseDto
UserDto
UserSearchResponseDto
```

`Jackson` handles JSON mapping, while `Lombok` reduces boilerplate code in DTO classes.

---

## REST Assured Specifications

Common REST Assured configuration is extracted into reusable specifications.

The specification contains:

* Base URI
* Base path
* Content type
* API key header
* Request logging
* Response logging
* Allure REST Assured filter
* Custom request template
* Custom response template

Example usage:

```java
given()
        .spec(requestSpec)
.when()
        .get("/users/2")
.then()
        .spec(responseSpec)
        .statusCode(200);
```

This approach keeps tests readable and prevents configuration duplication.

---

## API Key

Reqres requires an API key for requests.

The real API key must **not** be committed to the repository.

Set the key as an environment variable before running tests locally:

```bash
export REQRES_API_KEY=YOUR_API_KEY
```

Then run:

```bash
./gradlew clean test
```

The test framework reads the key using:

```java
System.getenv("REQRES_API_KEY")
```

In Jenkins, the real API key is stored securely in Jenkins Credentials.

---

## Allure Report

The project is integrated with Allure Report.

The Allure REST Assured integration automatically attaches HTTP request and response information to test results.

Custom FreeMarker templates are used for REST Assured attachments:

```text
src/test/resources/tpl/request.ftl
src/test/resources/tpl/response.ftl
```

Tests also contain Allure metadata such as:

* Epic
* Feature
* Story
* Severity
* Owner

### Allure Report Overview

![Allure Report Overview](images/allure-overview.png)

### Test Details

![Allure Test Details](images/allure-test-details.png)

### Run Allure Locally

Run tests:

```bash
./gradlew clean test
```

Open Allure Report:

```bash
./gradlew allureServe
```

---

## CI/CD with Jenkins

The project is integrated with Jenkins for automated test execution.

The Jenkins pipeline performs the following flow:

```text
GitHub
   ↓
Jenkins
   ↓
Checkout
   ↓
Gradle
   ↓
API Tests
   ↓
Test Statistics
   ↓
Allure Report
   ↓
Telegram Notification
```

The pipeline configuration is stored in:

```text
Jenkinsfile
```

### Jenkins Credentials

Sensitive values are stored in Jenkins Credentials and are not committed to GitHub:

```text
REQRES_API_KEY
TELEGRAM_BOT_TOKEN
TELEGRAM_CHAT_ID
```

### Jenkins Pipeline Responsibilities

The pipeline:

* Checks out the project from GitHub
* Loads secrets from Jenkins Credentials
* Runs the API test suite
* Generates JUnit XML results
* Calculates test statistics
* Publishes an Allure Report
* Generates a test result chart
* Sends build results to Telegram

### Jenkins Build

![Jenkins Build](images/jenkins-allure.png)

---

## Telegram Notifications

After each Jenkins build, test execution results are automatically sent to Telegram.

The notification contains:

* Environment
* Jenkins build number
* Total number of test scenarios
* Passed tests
* Failed tests
* Skipped tests
* Pass percentage
* Test result chart
* Link to the Jenkins Allure Report

### Telegram Report

![Telegram Test Report](images/telegram-report.png)

Example result:

```text
Reqres API Tests

Results:
Environment: main
Comment: Regression run

Total scenarios: 6
Total passed: 6 (100%)
Total failed: 0
Total skipped: 0
```

---

## Running Tests

### Run all tests

```bash
./gradlew clean test
```

### Run tests with an API key

macOS / Linux:

```bash
export REQRES_API_KEY=YOUR_API_KEY
./gradlew clean test
```

### Generate Allure Report

```bash
./gradlew allureReport
```

### Open Allure Report

```bash
./gradlew allureServe
```

---

## Security

Secrets must never be stored directly in source code.

The project uses:

```text
Environment Variables
        ↓
Local Test Execution

Jenkins Credentials
        ↓
CI Test Execution
```

The following values must never be committed:

```text
Real Reqres API keys
Telegram Bot tokens
Telegram Chat IDs
GitHub Personal Access Tokens
```

---

## Test Execution Result

Current automated API suite:

```text
Total tests: 6
Passed: 6
Failed: 0
```

The complete execution details are available through Allure Report and Jenkins.

---

## Author

**Aikerim**

QA Automation Engineer
