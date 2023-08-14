import { Decimal } from 'decimal.js';

export class StatisticsService {

    /**
     * Calculates the mean of an array of numbers.
     *
     * @param numbers The array of numbers for which to calculate the mean.
     * @return The mean of the numbers, or 0 if the array is empty.
     */
    calculateMean(numbers: number[]): number {
        if (numbers.length === 0) {
            return 0;
        }

        // Using Decimal.js to handle precision issues with floating-point numbers
        const reduction = numbers.reduce((sum, num) =>
            new Decimal(sum).add(new Decimal(num)).toNumber(), 0);

        return new Decimal(reduction).dividedBy(new Decimal(numbers.length)).toNumber();
    }

    /**
     * Calculates the median of an array of numbers.
     *
     * @param numbers The array of numbers for which to calculate the median.
     * @return The array of the numbers, or 0 if the list is empty.
     */
    calculateMedian(numbers: number[]): number {
        if (numbers.length === 0) {
            return 0;
        }

        // Sort the array in ascending order
        numbers.sort((a, b) => a - b);

        // Calculate the index of the middle element
        const middleIndex = Math.floor(numbers.length / 2);

        // Check if the array has an even number of elements
        if (numbers.length % 2 === 0) {
            // If the array has an even number of elements, calculate the median as the average of the two middle elements.
            // Get the values of the two middle elements and compute their average.
            return (numbers[middleIndex - 1] + numbers[middleIndex]) / 2;
        } else {
            // If the array has an odd number of elements, return the middle element as the median.
            return numbers[middleIndex];
        }
    }

    /**
     * Calculates the mode(s) of an array of numbers.
     *
     * @param numbers The array of numbers for which to calculate the mode(s).
     * @returns An array containing the mode(s) of the numbers.
     */
    calculateMode(numbers: number[]): number[] {
        // Create a map to store the occurrences of each number
        const occurrencesMap = new Map<number, number>();

        // Initialize variables to track the maximum occurrences and modes
        let maxOccurrences = 0;
        const modes: number[] = [];

        // Iterate through the numbers array
        numbers.forEach(num => {
            // Calculate the occurrences of the current number
            const occurrences = (occurrencesMap.get(num) || 0) + 1;

            // Update the occurrences in the map
            occurrencesMap.set(num, occurrences);

            // Check if the current occurrences is greater than the maxOccurrences
            if (occurrences > maxOccurrences) {
                // Update the maxOccurrences and clear the existing modes
                maxOccurrences = occurrences;
                modes.length = 0; // Clear existing modes array
                modes.push(num);
            } else if (occurrences === maxOccurrences) {
                // If the occurrences matches maxOccurrences, add the number to modes
                modes.push(num);
            }
        });

        // Sort the modes array in ascending order
        modes.sort((a, b) => a - b);

        // Return the modes array
        return modes;
    }
}
