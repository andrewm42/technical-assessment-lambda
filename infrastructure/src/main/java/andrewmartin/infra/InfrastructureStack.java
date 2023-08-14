package andrewmartin.infra;

import java.util.Arrays;
import java.util.List;

import software.amazon.awscdk.App;
import software.amazon.awscdk.BundlingOptions;
import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.CfnOutputProps;
import software.constructs.Construct;
import software.amazon.awscdk.DockerVolume;
import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.apigatewayv2.alpha.AddRoutesOptions;
import software.amazon.awscdk.services.apigatewayv2.alpha.HttpApi;
import software.amazon.awscdk.services.apigatewayv2.alpha.HttpApiProps;
import software.amazon.awscdk.services.apigatewayv2.alpha.HttpMethod;
import software.amazon.awscdk.services.apigatewayv2.alpha.PayloadFormatVersion;
import software.amazon.awscdk.services.apigatewayv2.authorizers.alpha.HttpLambdaAuthorizer;
 import software.amazon.awscdk.services.apigatewayv2.authorizers.alpha.HttpLambdaResponseType;
import software.amazon.awscdk.services.apigatewayv2.integrations.alpha.HttpLambdaIntegration;
import software.amazon.awscdk.services.apigatewayv2.integrations.alpha.HttpLambdaIntegrationProps;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.FunctionProps;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.nodejs.NodejsFunction;
import software.amazon.awscdk.services.lambda.nodejs.NodejsFunctionProps;
import software.amazon.awscdk.services.logs.RetentionDays;
import software.amazon.awscdk.services.s3.assets.AssetOptions;

import static java.util.Collections.singletonList;
import static software.amazon.awscdk.BundlingOutput.ARCHIVED;

public class InfrastructureStack extends Stack {
    public InfrastructureStack(final App parent, final String id) {
        this(parent, id, null);
    }

    /**
     * Constructs a new InfrastructureStack.
     *
     * @param parent The parent construct.
     * @param id The ID of the stack.
     * @param props The stack properties.
     */
    public InfrastructureStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        final String API_NAME = "andrewmartin-api";

        // Create an HTTP API
        HttpApi httpApi = new HttpApi(this, API_NAME, HttpApiProps.builder()
                .apiName(API_NAME)
                .build());

        // Create a the authorizer function
        Function authorizerFunction = createNodeFunction("Authorizer");

        // Create a simple lambda authorizer to protect the statistics endpoints
        HttpLambdaAuthorizer authorizer = HttpLambdaAuthorizer.Builder.create("StatisticsAuthorizer", authorizerFunction)
         .responseTypes(List.of(HttpLambdaResponseType.SIMPLE))
         .build();

        // Add routes to the API
        httpApi.addRoutes(createRoute("median", createJavaFunction("Median"), HttpMethod.POST, authorizer));
        httpApi.addRoutes(createRoute("mean", createJavaFunction("Mean"), HttpMethod.POST, authorizer));
        httpApi.addRoutes(createRoute("mode", createNodeFunction("Mode"), HttpMethod.POST, authorizer));

        // Define an output for the API endpoint URL
        new CfnOutput(this, "HttApi", CfnOutputProps.builder()
                .description("Url for Http Api")
                .value(httpApi.getApiEndpoint())
                .build());
    }

    /**
     * Creates a Java Lambda function.
     *
     * @param name The name of the function.
     * @return The created Java Lambda function.
     */
    private Function createJavaFunction(String name) {
        String functionName = String.format("%sFunction", name);
        String lowercaseFunctionName = String.format("%sfunction", name.toLowerCase());

        // Create the packaging instructions used to build the JAR file
        List<String> packagingInstructions = Arrays.asList(
                "/bin/sh",
                "-c",
                String.format("cd %s && mvn clean install && cp /asset-input/%s/target/%s.jar /asset-output/",
                        functionName, functionName, lowercaseFunctionName)
        );


        // Setup the lambda builder
        BundlingOptions.Builder builderOptions = BundlingOptions.builder()
                .command(packagingInstructions)
                .image(Runtime.JAVA_11.getBundlingImage())
                .volumes(singletonList(
                        // Mount local .m2 repo to avoid download all the dependencies again
                        // inside the container
                        DockerVolume.builder()
                                .hostPath(System.getProperty("user.home") + "/.m2/")
                                .containerPath("/root/.m2/")
                                .build()))
                .user("root")
                .outputType(ARCHIVED);

        // Create the function from the build assets
        Function function = new Function(this, functionName, FunctionProps.builder()
                .runtime(Runtime.JAVA_11)
                .code(Code.fromAsset("../software/", AssetOptions.builder()
                        .bundling(builderOptions
                                .command(packagingInstructions)
                                .build())
                        .build()))
                .handler("andrewmartin.lambda.App")
                .memorySize(512)
                .timeout(Duration.seconds(10))
                .logRetention(RetentionDays.ONE_WEEK)
                .build());

        return function;
    }

    /**
     * Creates an HTTP route.
     *
     * @param route The route path.
     * @param function The Lambda function to integrate with the route.
     * @return The options for creating the route.
     */
    private AddRoutesOptions createRoute(String route, Function function, HttpMethod method, HttpLambdaAuthorizer authorizer) {
        return AddRoutesOptions.builder()
                .path(String.format("/%s", route))
                .methods(singletonList(method))
                .integration(new HttpLambdaIntegration(String.format("%sFunction", route), function,
                        HttpLambdaIntegrationProps.builder()
                                .payloadFormatVersion(PayloadFormatVersion.VERSION_2_0)
                                .build()))
                .authorizer(authorizer)
                .build();
    }

    /**
     * Creates a Node.js Lambda function.
     *
     * @param name The name of the function.
     * @return The created Node.js Lambda function.
     */
    private Function createNodeFunction(String name) {
        String functionName = String.format("%sFunction", name);

        return new NodejsFunction(this, functionName, NodejsFunctionProps.builder()
                .runtime(Runtime.NODEJS_18_X)
                .entry(String.format("../software/%s/src/functions/%s/handler.ts", functionName, name.toLowerCase()))
                .handler("main")
                .memorySize(512)
                .timeout(Duration.seconds(10))
                .logRetention(RetentionDays.ONE_WEEK)
                .build());
    }
}
