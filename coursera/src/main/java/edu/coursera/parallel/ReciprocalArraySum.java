package edu.coursera.parallel;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Class wrapping methods for implementing reciprocal array sum in parallel.
 */
public final class ReciprocalArraySum {
    private ReciprocalArraySum() {
    }

    /**
     * Sequentially compute the sum of the reciprocal values for a given array.
     *
     * @param input Input array
     * @return The sum of the reciprocals of the array input
     */
    protected static double seqArraySum(final double[] input) {
        double sum = 0;

        // Compute sum of reciprocals of array elements
        for (int i = 0; i < input.length; i++) {
            sum += 1 / input[i];
        }

        return sum;
    }

    /**
     * This class stub can be filled in to implement the body of each task
     * created to perform reciprocal array sum in parallel.
     */
    private static class FJReciprocalSum extends RecursiveTask<Double> {
        private static int THRESH_HOLD = 1000;
        private final int splitFactor;
        private final int lo;
        private final int hi;
        private final double[] arr;

        private FJReciprocalSum(final int lo, final int hi, final double[] arr, final int splitFactor) {
            this.splitFactor = splitFactor;
            this.lo = lo;
            this.hi = hi;
            this.arr = arr;
        }

        @Override
        protected Double compute() {
            if (hi - lo < THRESH_HOLD) {
                double sum = 0;
                for (int i = this.lo; i < this.hi; i++) sum += 1 / arr[i];
                return sum;
            } else {
                FJReciprocalSum[] tasks = new FJReciprocalSum[splitFactor];
                for (int i = 0, chunk = (hi - lo + splitFactor) / splitFactor; this.lo + i * chunk < this.hi; i++) {
                    tasks[i] = new FJReciprocalSum(this.lo + i * chunk, Math.min(this.hi, this.lo + (i + 1) * chunk), this.arr, this.splitFactor);
                }
                for (int i = 1; i < splitFactor; i++) tasks[i].fork();
                double sum = tasks[0].compute();
                for (int i = 1; i < this.splitFactor; i++) sum += tasks[i].join();
                return sum;
            }
        }
    }

    /**
     * TODO: Modify this method to compute the same reciprocal sum as
     * seqArraySum, but use two tasks running in parallel under the Java Fork
     * Join framework. You may assume that the length of the input array is
     * evenly divisible by 2.
     *
     * @param input Input array
     * @return The sum of the reciprocals of the array input
     */
    protected static double parArraySum(final double[] input) {
        return new FJReciprocalSum(0, input.length, input, 2).compute();
    }

    /**
     * TODO: Extend the work you did to implement parArraySum to use a set
     * number of tasks to compute the reciprocal array sum. You may find the
     * above utilities getChunkStartInclusive and getChunkEndExclusive helpful
     * in computing the range of element indices that belong to each chunk.
     *
     * @param input    Input array
     * @param numTasks The number of tasks to create
     * @return The sum of the reciprocals of the array input
     */
    protected static double parManyTaskArraySum(final double[] input,
                                                final int numTasks) {
        ForkJoinPool pool = new ForkJoinPool(numTasks);
        return pool.invoke(new FJReciprocalSum(0, input.length, input, numTasks));
    }
}
