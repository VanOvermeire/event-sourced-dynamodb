### Overview

This is part of the code for a blog post I will be publishing, to use dynamodb and lambdas to create
an (albeit not perfect) event-sourced, event-driven system.

This is one of two parts, the other being the troposphere scripts.

It will consist of the following:

- an EventHandler, which will take an event, add the necessary fields, and send it to DynamoDB (first directly, later via API Gateway)
- the code for the 'Mapping Lambda' which takes data from a DynamoDB stream and sends it to the correct handlers (lambdas, sns topics), based on mappings in a table
- the code for a lambda which takes incoming events from API Gateway, adds a timestamp, and puts them in DynamoDB
- a basic event interface and abstract event
