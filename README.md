# SQS Listener Demo

A Java-based AWS SQS Listener implementation using the AWS SDK for Java v2 and Spring Boot. This application demonstrates how to poll messages from an SQS queue, process them, and delete them upon successful processing. It utilizes long polling for efficient message retrieval.

---

## Features

- Poll messages from an AWS SQS queue.
- Long polling for efficient message retrieval.
- Process and delete messages after successful processing.
- Built with Java 17, Spring Boot, and AWS SDK for Java v2.

---

## Prerequisites

Before running this project, ensure you have the following:

1. **Java 17** or later installed.
2. **Maven** installed.
3. **AWS CLI** configured with valid credentials to access SQS.
4. An **SQS queue URL** that the application will connect to.

---

## Setup and Configuration

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd sqs-demo
   ```

2. **Configure the SQS Queue URL**:
   In the `SqsListener` class, replace the placeholder `your-sqs-queue-url` with your actual SQS queue URL:
   ```java
   private final String queueUrl = "your-sqs-queue-url";
   ```

3. **Ensure AWS Credentials are Available**:
   Set up AWS credentials using one of the following methods:
    - **Environment Variables**: `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY`.
    - **AWS Credentials File**: Typically located at `~/.aws/credentials`.
    - **AWS CLI**: Use `aws configure` to set up credentials.

---

## Build and Run

1. **Build the Project**:
   Use Maven to clean and build the project:
   ```bash
   mvn clean install
   ```

2. **Run the Application**:
   Start the application using the Spring Boot Maven plugin:
   ```bash
   mvn spring-boot:run
   ```

---

## Project Structure

The project follows the standard Maven project structure:

```
src/main/java
├── com.annexify.sqsdemo
│   └── listener
│       └── SqsListener.java
└── Application.java
```

---

## Dependencies

This project includes the following dependencies:

1. **Spring Boot Starter Web**: To enable the application to run as a Spring Boot project.
2. **AWS SDK for Java v2 (SQS)**: To interact with AWS SQS services.
3. **Spring Boot Starter Test**: To enable unit testing (optional).

All dependencies are managed via the `pom.xml` file. Notable dependencies:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>software.amazon.awssdk</groupId>
    <artifactId>sqs</artifactId>
    <version>2.29.37</version>
</dependency>
```

---

## Usage

The application continuously polls the specified SQS queue. Messages are retrieved in batches of up to 10, processed, and deleted after successful processing.

### Customizing Message Processing
You can customize how messages are processed by modifying the `processMessage()` method in the `SqsListener` class:
```java
private void processMessage(Message message) {
    // Add your custom message processing logic here
    System.out.println("Processing message: " + message.body());
}
```

---

## Key Features and Configuration

- **Long Polling**: Reduces costs by waiting up to 10 seconds for messages to arrive before returning an empty response.
- **Batch Processing**: Retrieves up to 10 messages per request for efficiency.

---

## License

This project is licensed under the MIT License. See the LICENSE file for details.

---

## Author

Developed by [Your Name/Team Name]. Contributions and feedback are welcome!

---

## Contributing

1. Fork the repository.
2. Create a new branch for your feature or bug fix:
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. Commit your changes and push the branch:
   ```bash
   git push origin feature/your-feature-name
   ```
4. Open a pull request.

---

## Support

If you encounter any issues, feel free to open an issue in the repository or contact the developer.

---

## Acknowledgments

- AWS SDK for Java for seamless integration with AWS services.
- Spring Boot for its robust and easy-to-use framework.

