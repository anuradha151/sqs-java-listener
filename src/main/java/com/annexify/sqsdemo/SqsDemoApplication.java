package com.annexify.sqsdemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;

import java.util.List;

@SpringBootApplication
public class SqsDemoApplication implements CommandLineRunner {

    private final SqsClient sqsClient;
    private final String queueUrl = "<Your SQS url>";

    public SqsDemoApplication() {
        // Initialize SQS Client
        this.sqsClient = SqsClient.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(SqsDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting SQS Listener...");

        while (true) {
            // Poll messages from the SQS queue
            ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .maxNumberOfMessages(10)
                    .waitTimeSeconds(10) // Long polling
                    .build();

            List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();

            for (Message message : messages) {
                System.out.println("Received message: " + message.body());

                // Process the message
                processMessage(message);

                // Delete the message after processing
                deleteMessage(message);
            }
        }
    }

    private void processMessage(Message message) {
        // Add your custom message processing logic here
        System.out.println("Processing message: " + message.body());
    }

    private void deleteMessage(Message message) {
        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(message.receiptHandle())
                .build();
        sqsClient.deleteMessage(deleteMessageRequest);
        System.out.println("Deleted message: " + message.body());
    }
}
