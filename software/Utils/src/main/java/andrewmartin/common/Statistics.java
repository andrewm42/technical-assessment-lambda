package andrewmartin.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A utility class for calculating various statistics on a list of numbers.
 */
public class Statistics {

    /**
     * Calculates the mean of a list of numbers.
     *
     * @param numbers The list of numbers for which to calculate the mean.
     * @return The mean of the numbers, or 0.0 if the list is empty.
     */
    public double calculateMean(List<Double> numbers) {
        return numbers.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    /**
     * Calculates the median of a list of numbers.
     *
     * @param numbers The list of numbers for which to calculate the median.
     * @return The median of the numbers, or 0.0 if the list is empty.
     */
    public double calculateMedian(List<Double> numbers) {
        if (numbers.isEmpty()) {
            return 0.0;
        }

        // Sort the list in ascending order
        Collections.sort(numbers);

        // Calculate the index of the middle element
        Integer middleIndex = numbers.size() / 2;

        // Check if the list has an even number of elements
        if (numbers.size() % 2 == 0) {
            // If the list has an even number of elements, calculate the median as the average of the two middle elements.
            // Get the values of the two middle elements and compute their average.
            return (numbers.get(middleIndex - 1) + numbers.get(middleIndex)) / 2.0;
        } else {
            // If the list has an odd number of elements, return the middle element as the median.
            return numbers.get(middleIndex);
        }
    }

    /**
     * Calculates the mode(s) of a list of numbers.
     *
     * @param numbers The list of numbers for which to calculate the mode(s).
     * @return A list containing the mode(s) of the numbers.
     */
    public List<Double> calculateMode(List<Double> numbers) {
        // Author's note: I had a little fun with this method,
        // exploring the utility of java streams. If it's not your
        // preferred style you could write this code more imperatively,
        // but it does the job. You can see the TypeScript version I wrote
        // to get the idea of what that might look like.

        // Create an occurrences map to store the occurrences of each number.
            Map<Double, Long> occurrencesMap = numbers.stream()
            .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        // Find the maximum occurrences among all numbers in the list.
        long maxOccurrences = occurrencesMap.values().stream()
            .max(Long::compareTo)
            .orElse(0L); // If there's no mode, default to 0 occurrences

        // Identify the number(s) with the highest occurrences
        List<Double> modes = occurrencesMap.entrySet().stream()
            .filter(entry -> entry.getValue() == maxOccurrences) // Filter entries with the maximum occurrences
            .map(Map.Entry::getKey) // Extract the numbers with the maximum occurrences
            .collect(Collectors.toList()); // Collect these numbers into a list

        Collections.sort(modes);

        return modes;
    }

}
