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
