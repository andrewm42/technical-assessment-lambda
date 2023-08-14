import { APIGatewaySimpleAuthorizerResult, APIGatewayRequestAuthorizerEventV2 } from 'aws-lambda';

export const handler = async (event: APIGatewayRequestAuthorizerEventV2): Promise<APIGatewaySimpleAuthorizerResult> => {
  // This is a weird quirk of how SAM local uppercases all headers, 
  // while AWS lowercases them 
  const authToken = event.headers.authorization || event.headers.Authorization;

  return {
    isAuthorized: authToken === 'c346c99b-4216-4ab8-bec6-8a04a5ff2ebe',
  };
};

export const main = handler;
