# Spring Gradle Example with Dependency Management

This project demonstrates how to structure a Spring Boot project with Gradle that allows for:

1. Providing a baseline set of dependencies and their Spring configurations in a jackson module
2. Allowing service modules to override specific dependency versions while still using the jackson module's configurations

## Project Structure

```
spring-gradle-example/
├── build.gradle                 # Root project build file with common configurations
├── settings.gradle              # Project settings file
├── jackson-module/              # Jackson module with baseline dependencies and configurations
│   ├── build.gradle             # Jackson module build file with Jackson dependencies
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/example/jackson/config/
│           │       └── JacksonConfig.java  # Auto-configured Jackson configuration
│           └── resources/
│               └── META-INF/
│                   └── spring.factories    # Auto-configuration registration
└── service-module/              # Service module that uses the jackson module
    ├── build.gradle             # Service build file that overrides Jackson versions
    └── src/
        └── main/
            └── java/
                └── com/example/service/
                    └── ServiceApplication.java  # Spring Boot application
```

## How It Works

### Root Project

The root `build.gradle` file sets up common configurations for all modules:

- Applies the Spring Boot dependency management plugin
- Sets up Java compatibility
- Configures repositories
- Imports the Spring Boot BOM (Bill of Materials)

### Jackson Module

The jackson module:

- Uses the `java-library` plugin to expose dependencies to consumers
- Declares Jackson dependencies with specific versions using the `api` configuration
- Provides a Spring configuration class (`JacksonConfig`) that configures Jackson
- Uses Spring Boot's auto-configuration mechanism to automatically apply the configuration

### Service Module

The service module:

- Depends on the jackson module
- Overrides the Jackson versions with newer versions
- Uses the jackson module's Spring configurations automatically
- Can add its own dependencies and configurations

## Dependency Override Mechanism

When the service module includes both the jackson module and its own Jackson dependencies:

1. Gradle resolves the dependency conflict by using the newest version
2. The service's Jackson version (2.14.0) takes precedence over the jackson module's version (2.13.4)
3. The jackson module's Spring configurations still apply, but they use the service's Jackson version

## Running the Application

To run the application:

```bash
./gradlew :service-module:bootRun
```

Visit http://localhost:8080/test to see the response with the timestamp formatted according to the jackson module's configuration but using the service's Jackson version.

## Benefits

This approach allows you to:

1. Create reusable libraries with pre-configured dependencies and Spring configurations
2. Override specific dependency versions in services without duplicating configuration code
3. Maintain consistent configuration across multiple services
4. Upgrade dependencies independently in each service

## Notes

- This example uses Spring Boot 2.7.5, but the same approach works with other versions
- The same pattern can be applied to other dependencies beyond Jackson
- For more complex scenarios, consider using a platform BOM in the jackson module
