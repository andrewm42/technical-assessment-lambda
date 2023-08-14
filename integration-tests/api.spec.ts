import { fail } from 'assert';
import * as AWS from 'aws-sdk';
import axios from 'axios';

// Update your desired region
AWS.config.update({
    region: 'us-east-1', // Update with your desired region
});

const cloudFormation = new AWS.CloudFormation();
const httpApiStackName = 'StatisticsApiStack'; // Update with your stack name
const authorizationHeader = 'c346c99b-4216-4ab8-bec6-8a04a5ff2ebe'; // Update with your authorization token

describe('Integration Tests', () => {
    let endpoints: Record<string, string> = {};

    beforeAll(async () => {
        try {
            // Get the HttpApi base endpoint from the CloudFormation stack
            const stackResponse = await cloudFormation.describeStacks({ StackName: httpApiStackName }).promise();
            const stackOutput = stackResponse.Stacks[0].Outputs;
            const baseApiEndpoint = stackOutput.find(output => output.OutputKey === 'HttApi')!.OutputValue;

            // Create endpoint URLs for the specific routes
            endpoints = {
                mean: baseApiEndpoint + '/mean',
                median: baseApiEndpoint + '/median',
                mode: baseApiEndpoint + '/mode',
            };
        } catch (error) {
            console.error('An error occurred during setup:', error);
        }
    });

    it('should test /mean endpoint', async () => {
        const response = await axios.post(endpoints.mean, [1, 2, 5, 6], {
            headers: {
                'Content-Type': 'application/json',
                Authorization: authorizationHeader,
            },
        });

        const responseBody = response.data;
        
        expect(response.status).toBe(200);
        expect(responseBody.mean).toBeCloseTo(3.5);
    });

    it('should test /median endpoint', async () => {
        const response = await axios.post(endpoints.median, [1, 2, 3, 9], {
            headers: {
                'Content-Type': 'application/json',
                Authorization: authorizationHeader,
            },
        });

        const responseBody = response.data;
        
        expect(response.status).toBe(200);
        expect(responseBody.median).toBe(2.5);
    });

    it('should test /mode endpoint', async () => {
        const response = await axios.post(endpoints.mode, [1, 2, 3, 3, 4], {
            headers: {
                'Content-Type': 'application/json',
                Authorization: authorizationHeader,
            },
        });

        const responseBody = response.data;
        
        expect(response.status).toBe(200);
        expect(responseBody.mode).toEqual(expect.arrayContaining([3.0])); 
    });

    it('should test /mean unauthorized endpoint', async () => {
        try {
            await axios.post(endpoints.mean, [1, 2, 3], {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            fail('Should have thrown an exception');
        } catch(err) {
            expect(err.response.status).toBe(401);
            expect(err.response.statusText).toEqual("Unauthorized");  
        }
    });

    it('should test /median unauthorized endpoint', async () => {
        try {
            await axios.post(endpoints.median, [1, 2, 3], {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            fail('Should have thrown an exception');
        } catch(err) {
            expect(err.response.status).toBe(401);
            expect(err.response.statusText).toEqual("Unauthorized");  
        }
    });

    it('should test /mode unauthorized endpoint', async () => {
        try {
            await axios.post(endpoints.mode, [1, 2, 3], {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            fail('Should have thrown an exception');
        } catch(err) {
            expect(err.response.status).toBe(401);
            expect(err.response.statusText).toEqual("Unauthorized");  
        }
    });
});
