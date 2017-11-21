package com.github.vanovermeire.lambdas;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.AmazonSNSAsyncClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This lambda receives events from the database and sends them to the right destination,
 * depending on information in the mapping table.
 */
public class MappingLambda implements RequestHandler<DynamodbEvent, String> {

    // TODO move more stuff to separate object, so we can test better
    private LambdaLogger logger = null;
    private EnvironmentChecker environmentChecker = new EnvironmentChecker(Lists.newArrayList("mappings-table"), System.getenv());

    private DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.standard().build());
    private AmazonSNSAsync snsClient = AmazonSNSAsyncClient.asyncBuilder().build();

    @Override
    public String handleRequest(DynamodbEvent input, Context context) {
        logger = context.getLogger();

        String mapping = environmentChecker.getEnvironmentVariable("mappings-table")
                .orElseThrow(() -> new RuntimeException("Missing mappings table name!"));
        Table mappingTable = dynamoDB.getTable(mapping);

        input.getRecords().forEach(record -> {
            Map<String, AttributeValue> newImage = record.getDynamodb().getNewImage();
            String name = newImage.get("Name").getS();
            String data = newImage.get("Data").getS();

            Item eventMapping = mappingTable.getItem("HashId", name);
            List<String> arns = eventMapping.getList("Listeners").stream()
                    .map(Object::toString).collect(Collectors.toList());

            arns.forEach(a -> {
                PublishRequest publishRequest = new PublishRequest().withMessage(data).withTopicArn(a);
                PublishResult publish = snsClient.publish(publishRequest);
                logger.log("Published " + publish + " to " + a);
            });
        });

        return "{ \"result\": \"success\" }";
    }
}