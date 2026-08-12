# API Test Automation Project — Reqres

<p align="center">
  <img src="images/reqres.png" alt="Reqres API Test Automation" width="100%">
</p>

API test automation project for Reqres API using Java, REST Assured, JUnit 5 and Allure Report.

## Description

This project is designed for automated testing of the Reqres REST API. It covers the main user-related API operations, including creating, retrieving, updating, and deleting users, as well as negative scenarios.

### Project Features

- API test automation using `Java`
- `REST Assured` for sending HTTP requests and validating responses
- `JUnit 5` as the test framework
- `Gradle` for project build and dependency management
- DTO models for request body serialization and response deserialization
- `Lombok` for reducing boilerplate code in API models
- `Jackson` for JSON serialization and deserialization
- Reusable `RequestSpecification` and `ResponseSpecification`
- API key configuration through environment variables without storing secrets in the repository
- Positive and negative API test scenarios
- Coverage of the main user operations:
    - `POST` — create a new user
    - `GET` — get users list
    - `GET` — get a single user
    - `PUT` — update a user
    - `DELETE` — delete a user
    - `GET` — verify response for a non-existing user
- Integration of `REST Assured` with `Allure Report`
- - CI test execution using `Jenkins`
- Pipeline configuration using `Jenkinsfile`
- Secure secret management using `Jenkins Credentials`
- Automatic `Allure Report` generation after Jenkins builds
- Automatic Telegram notifications after test execution
- Dynamic test statistics calculation from JUnit XML results
- Automatic test result chart generation for Telegram reports
- Custom `FreeMarker` templates for Allure attachments:
    - `request.ftl` — HTTP request details
    - `response.ftl` — HTTP response details
- Allure metadata for better test organization:
    - Epic
    - Feature
    - Story
    - Severity
    - Owner
- HTTP request and response details are automatically attached to the Allure Report
- All tests can be executed locally with:

```bash
./gradlew clean test
```

- Allure Report can be generated and opened locally with:

```bash
./gradlew allureServe
```
## CI/CD with Jenkins

The project is integrated with Jenkins CI for automated test execution.

The Jenkins pipeline performs the following steps:

- Checks out the project from GitHub
- Loads sensitive data from Jenkins Credentials
- Executes the API test suite using Gradle
- Collects test execution statistics
- Generates an Allure Report
- Generates a test results chart
- Sends test execution results to Telegram

The following secrets are stored securely in Jenkins Credentials and are not committed to the repository:

- `REQRES_API_KEY`
- `TELEGRAM_BOT_TOKEN`
- `TELEGRAM_CHAT_ID`

The pipeline configuration is stored in:

```text
Jenkinsfile
```

### Jenkins Pipeline Flow

```text
GitHub
   ↓
Jenkins
   ↓
Gradle
   ↓
API Tests
   ↓
Allure Report
   ↓
Telegram Notification
```

### Jenkins Build

![Jenkins Build](images/jenkins-allure.png)

---

## Telegram Notifications

After each Jenkins build, the test execution results are automatically sent to Telegram.

The notification contains:

- Environment
- Build number
- Total number of scenarios
- Passed tests
- Failed tests
- Skipped tests
- Pass percentage
- Test result chart
- Link to the Allure Report

Example:

![Telegram Test Report](images/telegram-report.png)
## Technology Stack

<p align="center">
  <img src="https://skillicons.dev/icons?i=java,gradle,git,github,idea" />
</p>

<p align="center">
  <b>REST Assured</b> •
  <b>JUnit 5</b> •
  <b>Allure Report</b> •
  <b>Lombok</b> •
  <b>Jackson</b> •
  <b>FreeMarker</b>
</p>

<p align="center">
  <img width="50" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" title="Java"/>
  &nbsp;&nbsp;
  <img width="50" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/gradle/gradle-original.svg" title="Gradle"/>
  &nbsp;&nbsp;
  <img width="50" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/jenkins/jenkins-original.svg" title="Jenkins"/>
  &nbsp;&nbsp;
  <img width="50" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/github/github-original.svg" title="GitHub"/>
</p>

<p align="center">
  <b>Java</b>&nbsp;&nbsp;&nbsp;
  <b>Gradle</b>&nbsp;&nbsp;&nbsp;
  <b>Jenkins</b>&nbsp;&nbsp;&nbsp;
  <b>GitHub</b>
</p>

## Test Coverage

The project contains automated tests for basic user management API operations.

| Test | Method | Endpoint | Expected Status |
|---|---|---|---|
| Create a new user | POST | `/users` | 201 |
| Get users list | GET | `/users` | 200 |
| Get single user | GET | `/users/2` | 200 |
| Get non-existing user | GET | `/users/99999` | 404 |
| Update user | PUT | `/users/2` | 200 |
| Delete user | DELETE | `/users/2` | 204 |

The suite includes both positive and negative API scenarios.

## Project Structure

```text
src
└── test
    ├── java
    │   └── qa.guru.reqres
    │       ├── models
    │       │   ├── CreateUserRequestDto.java
    │       │   ├── CreateUserResponseDto.java
    │       │   ├── UpdateUserRequestDto.java
    │       │   ├── UpdateUserResponseDto.java
    │       │   ├── UserDto.java
    │       │   └── UserSearchResponseDto.java
    │       │
    │       ├── specs
    │       │   └── ReqresSpec.java
    │       │
    │       └── tests
    │           └── ReqresApiTests.java
    │
    └── resources
        ├── tpl
        │   ├── request.ftl
        │   └── response.ftl
        │
        └── allure.properties
```

## Models

DTO models are used for request serialization and response deserialization.

Lombok is used to reduce boilerplate code, while Jackson handles JSON mapping between API responses and Java objects.

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

## Specifications

Common REST Assured configuration is extracted into reusable request and response specifications.

This keeps API tests clean and avoids configuration duplication.

```java
given()
        .spec(requestSpec)
.when()
        .get("/users/2")
.then()
        .spec(responseSpec)
        .statusCode(200);
```

## Allure Report

The project is integrated with Allure Report.

Allure REST Assured integration automatically attaches HTTP request and response information to test results.

Custom Freemarker templates are used for REST Assured attachments:

```text
src/test/resources/tpl/request.ftl
src/test/resources/tpl/response.ftl
```

Tests also contain Allure metadata such as:

- Epic
- Feature
- Story
- Severity
- Owner

### Allure Report Overview

![Allure Report Overview](images/allure-overview.png)

### Test Details

![Allure Test Details](images/allure-test-details.png)

## Running Tests

Run all tests:

```bash
./gradlew clean test
```

## API Key

## API Key

Reqres requires an API key for requests.

The API key should not be committed to the repository.

Configure your API key according to the project configuration before running the tests.

Example:

```bash
export REQRES_API_KEY=YOUR_API_KEY
./gradlew clean test
```

> Never commit a real API key to GitHub.

## Generate Allure Report

Generate the report:

```bash
./gradlew allureReport
```

Open the report:

```bash
./gradlew allureServe
```

## Test Report

The test suite contains 6 automated API tests covering positive and negative scenarios.

All tests can be viewed in Allure Report together with request and response attachments.

## Author

Aikerim

QA Automation Engineer