# Lambda Functions

This project creates four lambdas, one each to compute the mean, median, and mode of a series of numbers, and one lambda authorizer. Two of the lambdas are in Java (Mean and Median) and two are in TypeScript running on Node.js (Mode and Authorizer). As such, it has a number of installation requirements to work from end-to-end.

I went a bit outside my comfort zone; instead of using the TypeScript CDK, I decided to use the Java one. It was a bit of a (re)learning curve, but I got there after a little bit of effort.

## Prerequisites

Before you begin, make sure you have the following tools installed:

1. [Node.js and npm via nvm](https://github.com/nvm-sh/nvm)
2. [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)
3. [AWS CDK](https://docs.aws.amazon.com/cdk/latest/guide/getting_started.html#getting_started_prerequisites)
4. [AWS SAM CLI](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-install.html)
5. [Java Development Kit (JDK) 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
6. [Maven](https://maven.apache.org/download.cgi)


## Installation

1. **Node.js and npm via nvm:**
    * Install Node.js using nvm (Node Version Manager):
        ```sh
        curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.38.0/install.sh | bash
        nvm install --lts
        ```
    * Alternatively, if on Windows for example, download and install the LTS version of Node.js from the official website. Verify your installation by running:
        ```sh
        node -v
        npm -v
        ```
        
2. **AWS CLI:**
    Follow the instructions in the [AWS CLI Setup Guide](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html) to install the AWS CLI. Verify your installation by running:
    ```sh
    aws --version
    ```

3. **AWS CDK:**
    Follow the instructions in the [AWS CDK Getting Started guide](https://docs.aws.amazon.com/cdk/latest/guide/getting_started.html#getting_started_install) to install the AWS CDK. Verify your installation by running:
    ```sh
    cdk --version
    ```

4. **AWS SAM CLI:** 
    Install the AWS SAM CLI using the instructions provided in the [AWS SAM documentation](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/install-sam-cli.html).Verify your installation by running:
    ```sh
    sam --version
    ```

5. **Java Development Kit (JDK) 11:** 
    Download and install JDK 11. Verify your installation by running:
    ```sh
    java -version
    ```

6. **Maven:**
    Download and install Maven from the [official website](https://maven.apache.org/download.cgi). Verify your installation by running:
    ```sh
    mvn -v
    ```
## Getting started

The project is primarily powered by NPM and series of commands registered in `package.json`. Below is the suggested order, but feel free to play around with them yourself:

1. Install NPM dependecies:
    ```sh
    npm install
    ```

2. Build the project, run tests, and synthesize the CDK into CloudFormation:
    ```sh
    npm run build
    ```
    This may take several minutes as maven installs the necessary java dependencies

3. Invoke the lambdas to test locally:
    ```sh
    npm run invoke:mean
    npm run invoke:median
    npm run invoke:mode
    ```

4. You can also start an API for all three lambdas at localhost:3000
    ```sh
    npm run start-api
    ```

5. Once the server is running you can POST to any of the endpoints (`/mean`, `/median`, `/mode`) with an `Authorization:c346c99b-4216-4ab8-bec6-8a04a5ff2ebe` header and a JSON body array of numbers and get the results:

    ```json
    [1, 2, 5, 4, 5, 7 ]
    ```

6. You can run the unit tests individually or altogther:
    ```sh
    npm run test #runs all the tests
    npm run test:mean
    npm run test:median
    npm run test:mode
    npm run test:infra
    npm run test:auth
    ```

7. If you want to deploy your lambdas to AWS, you can execute the following commands (assuming you have AWS credentials configured):
    ```sh
    npm run bootstrap # deploys a CF template to AWS to bootstrap the CDK
    npm run deploy # deploys the lambdas and their api gateway integrations
    ```
    Once everything is deployed you will see the URL of the API output in the console:

    ```text
    Outputs:
    StatisticsApiStack.HttpApi = https://abc123.execute-api.us-east-1.amazonaws.com   
    ```

    Make sure to copy down _your_ specific URL for use in further manual testing.

9. You can now run the integration tests which will verify that the deployment succeeded:
    ```sh
    npm run test:integration
    ```

10. You can manually test by POSTing to `/mean`, `/median`, `/mode` using the above API Gateway URL the same way you did in local testing. Make sure to include the `Authorization:c346c99b-4216-4ab8-bec6-8a04a5ff2ebe` header in your request.



## Future work

Without spending too much time on this project, I feel like what I have done is a good representation of my skills, though with more time, there are a few things to do that would truly make this production ready:

* Quality gates for code coverage and linting
* Pre-push git hooks that enforce the quality gates
* Build templates for pipeline deployments (CircleCI, Jenkins, Travis CI, etc.)
* Better input validation and error messaging
* More, more, and more unit tests
* Could explore depdendency injection freamworks, but it's not always my go-to for functions as a service due to its (potential) impact on startup times
* [Setup project debugging for various IDEs](https://docs.aws.amazon.com/toolkit-for-vscode/latest/userguide/serverless-apps-run-debug-no-template.html)
  
