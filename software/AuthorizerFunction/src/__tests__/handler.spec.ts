import { APIGatewayRequestAuthorizerEventV2 } from 'aws-lambda';
import { handler } from '../functions/authorizer/handler';

describe('Authorization Handler', () => {
  it('should authorize valid token, lowercase', async () => {
    const validTokenEvent: APIGatewayRequestAuthorizerEventV2 = {
        headers: {
            authorization: 'c346c99b-4216-4ab8-bec6-8a04a5ff2ebe',
        },
        version: '',
        type: 'REQUEST',
        routeArn: '',
        identitySource: [],
        routeKey: '',
        rawPath: '',
        rawQueryString: '',
        cookies: [],
        requestContext: undefined
    };

    const result = await handler(validTokenEvent);

    expect(result).toEqual({
      isAuthorized: true,
    });
  });

  it('should not authorize invalid token, lowercase', async () => {
    const invalidTokenEvent: APIGatewayRequestAuthorizerEventV2 = {
        headers: {
            authorization: 'invalid-token',
        },
        version: '',
        type: 'REQUEST',
        routeArn: '',
        identitySource: [],
        routeKey: '',
        rawPath: '',
        rawQueryString: '',
        cookies: [],
        requestContext: undefined
    };

    const result = await handler(invalidTokenEvent);

    expect(result).toEqual({
      isAuthorized: false,
    });
  });

  it('should authorize valid token, uppercase', async () => {
    const validTokenEvent: APIGatewayRequestAuthorizerEventV2 = {
        headers: {
            Authorization: 'c346c99b-4216-4ab8-bec6-8a04a5ff2ebe',
        },
        version: '',
        type: 'REQUEST',
        routeArn: '',
        identitySource: [],
        routeKey: '',
        rawPath: '',
        rawQueryString: '',
        cookies: [],
        requestContext: undefined
    };

    const result = await handler(validTokenEvent);

    expect(result).toEqual({
      isAuthorized: true,
    });
  });

  it('should not authorize invalid token, uppercase', async () => {
    const invalidTokenEvent: APIGatewayRequestAuthorizerEventV2 = {
        headers: {
            Authorization: 'invalid-token',
        },
        version: '',
        type: 'REQUEST',
        routeArn: '',
        identitySource: [],
        routeKey: '',
        rawPath: '',
        rawQueryString: '',
        cookies: [],
        requestContext: undefined
    };

    const result = await handler(invalidTokenEvent);

    expect(result).toEqual({
      isAuthorized: false,
    });
  });
});
