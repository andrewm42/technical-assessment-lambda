import { StatisticsService } from '../../libs/statistics.service';

describe('Statistics', () => {
  describe('calculateMean', () => {
    it('Empty list returns zero', () => {
      const statistics = new StatisticsService();
      const mean = statistics.calculateMean([]);
      expect(mean).toBe(0.0);
    });

    it('Positive values return correct mean', () => {
      const statistics = new StatisticsService();
      const positiveValues = [1.5, 2.0, 2, 3, 4.0, 5.5];
      const mean = statistics.calculateMean(positiveValues);
      expect(mean).toBe(3);
    });

    it('Negative values return correct mean', () => {
      const statistics = new StatisticsService();
      const negativeValues = [-1.0, -2.5, -1.5, -0.5, -1.0];
      const mean = statistics.calculateMean(negativeValues);
      expect(mean).toBe(-1.3);
    });

    it('Positive and negative values return correct mean', () => {
      const statistics = new StatisticsService();
      const mixedValues = [-1.0, -2.5, 1.5, -0.5, 3.3];
      const mean = statistics.calculateMean(mixedValues);
      expect(mean).toBe(0.16);
    });
  });

  describe('calculateMedian', () => {
    it('Empty list returns zero', () => {
      const statistics = new StatisticsService();
      const median = statistics.calculateMedian([]);
      expect(median).toBe(0.0);
    });

    it('Positive numbers odd number of values returns middle value', () => {
      const statistics = new StatisticsService();
      const oddValues = [1.5, 2.0, 3.5, 4.0, 5.5];
      const median = statistics.calculateMedian(oddValues);
      expect(median).toBe(3.5);
    });

    it('Positive numbers even number of values returns average of middle values', () => {
      const statistics = new StatisticsService();
      const evenValues = [1.0, 2.0, 3.0, 4.0];
      const median = statistics.calculateMedian(evenValues);
      expect(median).toBe(2.5);
    });

    it('Negative numbers odd number of values returns middle value', () => {
      const statistics = new StatisticsService();
      const oddValues = [-1.5, -2.0, -3.5, -4.0, -5.5];
      const median = statistics.calculateMedian(oddValues);
      expect(median).toBe(-3.5);
    });

    it('Negative numbers even number of values returns average of middle values', () => {
      const statistics = new StatisticsService();
      const evenValues = [-1.0, -2.0, -3.0, -4.0];
      const median = statistics.calculateMedian(evenValues);
      expect(median).toBe(-2.5);
    });

    it('Mixed numbers odd number of values returns middle value', () => {
      const statistics = new StatisticsService();
      const mixedValues = [-1.5, -2.0, -3.5, -4.0, 5.5];
      const median = statistics.calculateMedian(mixedValues);
      expect(median).toBe(-2.0);
    });

    it('Mixed numbers even number of values returns average of middle values', () => {
      const statistics = new StatisticsService();
      const mixedValues = [-1.0, -2.0, -3.0, 4.0];
      const median = statistics.calculateMedian(mixedValues);
      expect(median).toBe(-1.5);
    });
  });

  describe('calculateMode', () => {
    it('Empty list returns empty list', () => {
      const statistics = new StatisticsService();
      const emptyList = [];
      const modes = statistics.calculateMode(emptyList);
      expect(modes).toEqual([]);
    });

    it('Single value returns single value', () => {
      const statistics = new StatisticsService();
      const singleValue = [1.0];
      const modes = statistics.calculateMode(singleValue);
      expect(modes).toEqual([1.0]);
    });

    it('Multiple values returns single value', () => {
      const statistics = new StatisticsService();
      const multipleValues = [1.5, 2.0, 2.0, 3.5, 3.5, 4.0, 4.0, 5.5, 5.5, 5.5];
      const modes = statistics.calculateMode(multipleValues);
      expect(modes).toEqual([5.5]);
    });

    it('Multiple values returns multiple values', () => {
      const statistics = new StatisticsService();
      const multipleValues = [1, 1, 1.0, 1.5, 2.0, 2.0, 2.0, 3.5, 3.5, 4.0, 4.0, 5.5, 5.5, 5.5,];
      const modes = statistics.calculateMode(multipleValues);
      expect(modes).toEqual([1, 2.0, 5.5]);
    });

    it('Multiple values returns all values', () => {
      const statistics = new StatisticsService();
      const multipleValues = [1.1, 2.2, 5, 5.5, 4.4, 7, 6.6];
      const modes = statistics.calculateMode(multipleValues);
      expect(modes).toEqual([1.1, 2.2, 4.4, 5, 5.5, 6.6, 7]);
    });
  });

});
