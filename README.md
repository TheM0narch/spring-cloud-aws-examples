# Spring Cloud AWS Examples

This is a project dedicated to make the work with the new [Spring Cloud AWS](https://github.com/awspring/spring-cloud-aws)
easier by providing examples. The library is compatible with Java 17 and above and Spring Boot 3 and above.

## Table of content

| AWS Service                                                                                                                                           |
|-------------------------------------------------------------------------------------------------------------------------------------------------------|
| [SQS]()                        |
| [SNS]()                        |
| [SES]()                        |
| [S3]()                          |
| [DynamoDB]()              |
| [Parameter Store]() |
| [Cognito Security]()       |

## Prerequisites
### AWS Account
In order to test the integration of the different modules you would need to have an account for aws and generate access and secret access keys.
You can find more information about it [here](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_credentials_access-keys.html).

### DynamoDB Table
This project uses the following table:

| Name: Movie  |        |             |
|--------------|--------|-------------|
| Field        | Type   |             |
| movie_name   | String | Primary Key |
| release_date | String | Sort Key    |
| director     | String | Attribute   |
| imdb_rating  | String | Attribute   |

### General Advice
I would suggest you to use the template (e.g S3Template) instead of the corresponding clients (e.g S3Client) for the simple tasks.
You should use the Clients when you need more fine-grain work because the clients are fully customizable.

### SES
In order the SES to work you would need to verify the emails through the aws console.

### Cognito security
The token to be used for auth is the "idToken" instead of the "accessToken".

## How to test manually

### Sqs
Prerequisite:
1. Created queue in aws.
2. Inside ```application.yaml``` add the name of the queue to ```application.aws.sqsProperties.queueName```

**Rest:**

**URL** : `/sqs`

**Method** : `Post`

**Auth required** : No

**Body** :
```json
{
  "message" : "Hello world?"
}
```

---

### Sns
Prerequisite:
1. Created sns in aws.
2. Easiest way to get the notification is to add email subscription to given sns topic
3. Inside ```application.yaml``` add the sns arn to ```application.aws.sns.arn```

**Rest:**

**URL** : `/sns/template`

**Method** : `Post`

**Auth required** : No

**Body** : No

---

**URL** : `/sns/client`

**Method** : `Post`

**Auth required** : No

**Body** : No

---

### S3
Prerequisite:
1. Created a s3 bucket.
2. Easiest way to get the notification is to add email subscription to given sns topic
3. Inside ```application.yaml``` add bucket name to ```application.aws.s3.bucket```
4. Execute the post request first in order to upload the file to s3

**Rest:**

**URL** : `/s3/client`

**Method** : `Get`

**Auth required** : No

**Body** : No

---

**URL** : `/s3/client`

**Method** : `Post`

**Auth required** : No

**Body** : No

---

**URL** : `/s3/client`

**Method** : `Delete`

**Auth required** : No

**Body** : No

---

**URL** : `/s3/template`

**Method** : `Get`

**Auth required** : No

**Body** : No

---

**URL** : `/s3/template`

**Method** : `Post`

**Auth required** : No

**Body** : No

---

**URL** : `/s3/template`

**Method** : `Delete`

**Auth required** : No

**Body** : No

---

**URL** : `/s3/resource`

**Method** : `Get`

**Auth required** : No

**Body** : No

---

### Ses
Prerequisite:
1. Subscribe both the sender and receiver emails from aws console
2. Add the emails to the `.setTo()` and `setFrom()` methods inside the [MailSendingService]()

**Rest:**

**URL** : `/ses/simple`

**Method** : `Post`

**Auth required** : No

**Body** : No

---

**URL** : `/ses/mime`

**Method** : `Post`

**Auth required** : No

**Body** : No

---

### DynameDB
Prerequisite:
1. Create a table
2. Execute the post request first so you have a record in the db

**Rest:**

**URL** : `/dynamodb`

**Method** : `Get`

**Auth required** : No

**RequestParam** movieName = testFromJava

**RequestParam** releaseDate = testFromJava

---

**URL** : `/dynamodb`

**Method** : `Post`

**Auth required** : No

**Body** :
```json
{
  "movieName": "testFromJava",
  "releaseDate": "23/07/2010",
  "director": "testFromJava",
  "imdbRating": "2"
}
```
---

**Rest:**

**URL** : `/dynamodb`

**Method** : `Delete`

**Auth required** : No

**RequestParam** movieName = testFromJava

**RequestParam** releaseDate = testFromJava

---

### Parameter Store
Prerequisite:
1. Parameters inside the parameter store
2. Add the path to the parameter store inside the application.config

**Rest:**

**URL** : `/parameterstore/client`

**Method** : `Get`

**Auth required** : No

**Body** : No

---

**Rest:**

**URL** : `/parameterstore/injected`

**Method** : `Get`

**Auth required** : No

**Body** : No

---

### Cognito
Prerequisite:
1. Create user pool and add user
2. Use the aws generate UI or create a lambda to get the idToken by username and password

**Rest:**

**URL** : `/cognito`

**Method** : `Get`

**Auth required** : Yes, Cognito idToken

**Body** : No

