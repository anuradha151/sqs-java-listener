package com.annexify.sqsdemo.listener;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;

import java.util.List;

public class SqsListener {

    private final SqsClient sqsClient;
    private final String queueUrl;

    public SqsListener(String queueUrl) {
        this.sqsClient = SqsClient.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
        this.queueUrl = queueUrl;
    }

    public void listen() {
        while (true) {
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
