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
        private final int lo;
        private final int hi;
        private final double[] arr;

        private FJReciprocalSum(final int lo, final int hi, final double[] arr) {
            this.lo = lo;
            this.hi = hi;
            this.arr = arr;
        }

        @Override
        protected Double compute() {
            if (this.hi - this.lo < 10000) {
                double sum = 0;
                for (int i = this.lo; i < this.hi; i++) sum += 1 / this.arr[i];
                return sum;
            } else {
                int mid = this.lo + (this.hi - this.lo) / 2;
                FJReciprocalSum left = new FJReciprocalSum(this.lo, mid, this.arr);
                FJReciprocalSum right = new FJReciprocalSum(mid, this.hi, this.arr);
                right.fork();
                return left.compute() + right.join();
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
        return parManyTaskArraySum(input, 2);
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
        return ForkJoinPool.commonPool().invoke(new FJReciprocalSum(0, input.length, input));
    }
}
