# 4156-Miniproject-2025

This is the GitHub repository for the service portion of the Team Project associated with COMS 4156 Advanced Software Engineering.


## About This Repository

This repo contains the code for Individual Miniproject.

## Building and Running a Local Instance

To build and use this service, you must install the following 

- **Maven 3.9.5:**  
  [Download Maven](https://maven.apache.org/download.cgi) and follow the installation instructions. Be sure to set the `bin` directory in your system's PATH variable.

- **JDK 17:**  
  [Download JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) and install.

- **IDE:**  
  We recommend IntelliJ IDEA ([Download](https://www.jetbrains.com/idea/download/?section=windows)), but you may use any IDE you prefer.

**Cloning the Repo:**  
Use the green "Code" button on GitHub to copy the HTTPS link and clone in your IDE.

**Building the Project:**  
- Run `mvn compile` to compile.
- Run `mvn spring-boot:run` to run.
- To run tests:  
  `mvn clean test`
- To get test reports:  
  `mvn jacoco:report`

**Style Checking:**  
- Run `mvn checkstyle:check` or `mvn checkstyle:checkstyle` to generate a report.

**PMD Static Analysis:**  
- Install using `mvn install`
- Check command: `mvn pmd:check`
- Report generation: `mvn pmd:pmd`

---

## Endpoints

Below are the main endpoints provided by the service in `RouteController.java`:

- **GET `/books/available`**  
  **Description:** Returns a list of all books that currently have at least one available copy.  
  **Input:** None  
  **Upon Success:** HTTP 200 with a JSON array of available books  
  **Upon Failure:** HTTP 500 with error message

- **GET `/book/{id}`**  
  **Description:** Returns details of the book with the specified ID.  
  **Input:** Path variable `id` (integer)  
  **Upon Success:** HTTP 200 with the book's JSON details  
  **Upon Failure:** HTTP 404 if book not found; HTTP 500 with error message

- **PATCH `/book/{id}/add`**  
  **Description:** Adds a copy to the book with the specified ID.  
  **Input:** Path variable `id` (integer)  
  **Upon Success:** HTTP 200 with updated book JSON  
  **Upon Failure:** HTTP 404 if book not found; HTTP 500 with error message

- **GET `/books/recommendation`**  
  **Description:** Returns a list of 10 recommended books (5 most popular, 5 random).  
  **Input:** None  
  **Upon Success:** HTTP 200 with a JSON array of 10 unique recommended books  
  **Upon Failure:** HTTP 500 with error message

- **GET `/checkout`**  
  **Description:** Checks out a copy of the book specified by the `id` query parameter.  
  **Input:** Query parameter `id` (integer)  
  **Upon Success:** HTTP 200 with updated book JSON  
  **Upon Failure:** HTTP 400 if no copies available; HTTP 404 if book not found; HTTP 500 with error message

---

## Unit Test Coverage Report

Below is a screenshot of the unit test coverage report for this project, showing both line and branch coverage as measured by JaCoCo:

<img width="1555" height="307" alt="image" src="https://github.com/user-attachments/assets/6cfd7d30-9a38-4b04-bab5-6769905690f5" />


This report demonstrates the effectiveness of our test suite in covering the main logic and branches of the codebase.

---

## Checkstyle Violations

As evident from the screenshot after running mvn checkstyle:check, there are 0 violations.

<img width="1208" height="141" alt="image" src="https://github.com/user-attachments/assets/4bf6d5fc-87d5-4b2e-bec0-c211e89f0555" />

---

## Continuous Integration (CI)

This repository uses **GitHub Actions** for continuous integration.  
Every push and pull request triggers a build, test, and static analysis run.

- See the latest results in the "Actions" tab on GitHub.

References for CI setup:
- [GitHub Actions Workflow Syntax](https://docs.github.com/en/actions/reference/workflows-and-actions/workflow-syntax)
- [Continuous Integration with Java and GitHub Actions](https://faun.pub/continuous-integration-of-java-project-with-github-actions-7a8a0e8246ef)

---

## Running Tests

Unit tests are located under `src/test`.  
To run tests in IntelliJ (Java 17 required), build the project, then right-click any test class and select "Run".

---

## Tools Used

- **Maven Package Manager**
- **GitHub Actions CI**
- **Checkstyle** (for code style)
- **PMD** (for static analysis)
- **JUnit** (for unit testing)
- **JaCoCo** (for code coverage)

---

## Additional References

- [Maven PMD Plugin Setup](https://maven.apache.org/plugins/maven-pmd-plugin/)
- [Platform as a Service Guide](https://medium.com/%40anurag.webtel/platform-as-a-service-a-comprehensive-guide-to-paas-16f3f319387e)
- [Cloud Computing: PaaS and its Types](https://www.geeksforgeeks.org/cloud-computing/platform-as-a-service-paas-and-its-types)

---

## Citations

- To understand `@RequestParam`, see [Baeldung: Spring Request Param](https://www.baeldung.com/spring-request-param)
- For `@SuppressWarnings` annotation usage, see [GeeksforGeeks: SuppressWarnings](https://www.geeksforgeeks.org/java/the-suppresswarnings-annotation-in-java/)

---
