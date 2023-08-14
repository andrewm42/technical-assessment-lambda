import type { ValidatedEventAPIGatewayProxyEvent } from '@libs/api-gateway';
import { formatJSONResponse } from '@libs/api-gateway';
import { middyfy } from '@libs/lambda';
import { StatisticsService } from '@libs/statistics.service'
import { Response } from './response';

import schema from './schema';

const svc = new StatisticsService();

const handler: ValidatedEventAPIGatewayProxyEvent<typeof schema> = async (event) => {
  return formatJSONResponse(calculateMode(event.body));
};

const calculateMode = (numbers: number[]) => {
  const response: Response = {
    mode: svc.calculateMode(numbers),
  };

  return response;
};

export const main = middyfy(handler);
