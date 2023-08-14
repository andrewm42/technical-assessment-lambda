package andrewmartin.common;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatisticsTest {

    @Test
    public void testCalculateMean_EmptyList_ReturnsZero() {
        Statistics statistics = new Statistics();
        List<Double> emptyList = Collections.emptyList();
        double mean = statistics.calculateMean(emptyList);
        assertEquals(0.0, mean, 0.001);
    }

    @Test
    public void testCalculateMean_PositiveValues_ReturnsCorrectMean() {
        Statistics statistics = new Statistics();
        List<Double> positiveValues = Arrays.asList(1.5, 2.0, 3.5, 4.0, 5.5);
        double mean = statistics.calculateMean(positiveValues);
        assertEquals(3.3, mean, 0.001);
    }

    @Test
    public void testCalculateMean_NegativeValues_ReturnsCorrectMean() {
        Statistics statistics = new Statistics();
        List<Double> negativeValues = Arrays.asList(-1.0, -2.5, -1.5, -0.5, -1.0);
        double mean = statistics.calculateMean(negativeValues);
        assertEquals(-1.3, mean, 0.001);
    }

    @Test
    public void testCalculateMean_PositiveAndNegativeValues_ReturnsCorrectMean() {
        Statistics statistics = new Statistics();
        List<Double> negativeValues = Arrays.asList(-1.0, -2.5, 1.5, -0.5, 3.3);
        double mean = statistics.calculateMean(negativeValues);
        assertEquals(0.16, mean, 0.001);
    }

    @Test
    public void testCalculateMedian_EmptyList_ReturnsZero() {
        Statistics statistics = new Statistics();
        List<Double> emptyList = Collections.emptyList();
        double median = statistics.calculateMedian(emptyList);
        assertEquals(0.0, median, 0.001);
    }

    @Test
    public void testCalculateMedian_PositiveNumbers_OddNumberOfValues_ReturnsMiddleValue() {
        Statistics statistics = new Statistics();
        List<Double> oddValues = Arrays.asList(1.5, 2.0, 3.5, 4.0, 5.5);
        double median = statistics.calculateMedian(oddValues);
        assertEquals(3.5, median, 0.001);
    }

    @Test
    public void testCalculateMedian_PositiveNumbers_EvenNumberOfValues_ReturnsAverageOfMiddleValues() {
        Statistics statistics = new Statistics();
        List<Double> evenValues = Arrays.asList(1.0, 2.0, 3.0, 4.0);
        double median = statistics.calculateMedian(evenValues);
        assertEquals(2.5, median, 0.001);
    }

    @Test
    public void testCalculateMedian_NegativeNumbers_OddNumberOfValues_ReturnsMiddleValue() {
        Statistics statistics = new Statistics();
        List<Double> oddValues = Arrays.asList(-1.5, -2.0, -3.5, -4.0, -5.5);
        double median = statistics.calculateMedian(oddValues);
        assertEquals(-3.5, median, 0.001);
    }

    @Test
    public void testCalculateMedian_NegativeNumbers_EvenNumberOfValues_ReturnsAverageOfMiddleValues() {
        Statistics statistics = new Statistics();
        List<Double> evenValues = Arrays.asList(-1.0, -2.0, -3.0, -4.0);
        double median = statistics.calculateMedian(evenValues);
        assertEquals(-2.5, median, 0.001);
    }

    @Test
    public void testCalculateMedian_PostiveAndNegativeNumbers_OddNumberOfValues_ReturnsMiddleValue() {
        Statistics statistics = new Statistics();
        List<Double> oddValues = Arrays.asList(-1.5, -2.0, -3.5, -4.0, 5.5);
        double median = statistics.calculateMedian(oddValues);
        assertEquals(-2.0, median, 0.001);
    }

    @Test
    public void testCalculateMedian_PostiveAndNegativeNumbers_EvenNumberOfValues_ReturnsAverageOfMiddleValues() {
        Statistics statistics = new Statistics();
        List<Double> evenValues = Arrays.asList(-1.0, -2.0, -3.0, 4.0);
        double median = statistics.calculateMedian(evenValues);
        assertEquals(-1.5, median, 0.001);
    }


    @Test
    public void testCalculateMode_EmptyList_ReturnsEmptyList() {
        Statistics statistics = new Statistics();
        List<Double> emptyList = Collections.emptyList();
        List<Double> modes = statistics.calculateMode(emptyList);
        assertEquals(Collections.emptyList(), modes);
    }

    @Test
    public void testCalculateMode_SingleValue_ReturnsSingleValue() {
        Statistics statistics = new Statistics();
        List<Double> singleValue = Collections.singletonList(42.0);
        List<Double> modes = statistics.calculateMode(singleValue);
        assertEquals(Collections.singletonList(42.0), modes);
    }

    @Test
    public void testCalculateMode_MultipleValues_ReturnsSingleValue() {
        Statistics statistics = new Statistics();
        List<Double> multipleValues = Arrays.asList(1.5, 2.0, 2.0, 3.5, 3.5, 4.0, 4.0, 5.5, 5.5, 5.5);
        List<Double> modes = statistics.calculateMode(multipleValues);
        assertEquals(Arrays.asList(5.5), modes);
    }

    @Test
    public void testCalculateMode_MultipleValues_ReturnsMultipleValues() {
        Statistics statistics = new Statistics();
        List<Double> multipleValues = Arrays.asList(1.5, 2.0, 2.0, 2.0, 3.5, 3.5, 4.0, 4.0, 5.5, 5.5, 5.5);
        List<Double> modes = statistics.calculateMode(multipleValues);
        assertEquals(Arrays.asList(2.0, 5.5), modes);
    }

    @Test
    public void testCalculateMode_MultipleValues_ReturnsAllValues() {
        Statistics statistics = new Statistics();
        List<Double> multipleValues = Arrays.asList(1.1, 2.2, 5.5, 4.4, 6.6);
        List<Double> modes = statistics.calculateMode(multipleValues);
        assertEquals(Arrays.asList(1.1, 2.2, 4.4, 5.5, 6.6), modes);
    }
}
