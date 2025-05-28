import java.util.Random;

public class ArrClass {
    private final int dim;
    private final int threadNum;
    public final int[] arr;

    public ArrClass(int dim, int threadNum) {
        this.dim = dim;
        this.threadNum = threadNum;
        this.arr = new int[dim];
        generateArray();
    }

    private void generateArray() {
        Random rand = new Random();
        for (int i = 0; i < dim; i++) {
            arr[i] = rand.nextInt(10_000);
        }

        int negIndex = rand.nextInt(dim);            // java.util.Random [+] -> [-]
        arr[negIndex] = -rand.nextInt(10_000);
    }

    private Result globalMin = new Result(Integer.MAX_VALUE, -1);
    private int threadCount = 0;

    synchronized public void updateMin(Result localMin) {
        if (localMin.value < globalMin.value) {
            globalMin = localMin;
        }
    }

    synchronized public void incThreadCount() {
        threadCount++;
        notifyAll();
    }

    synchronized private Result waitForAllThreads() {
        while (threadCount < threadNum) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return globalMin;
    }

    public Result threadMin() {
        ThreadMin[] threads = new ThreadMin[threadNum];
        int partSize = dim / threadNum;
        int remainder = dim % threadNum;

        int start = 0;
        for (int i = 0; i < threadNum; i++) {
            int end = start + partSize + (i < remainder ? 1 : 0);
            threads[i] = new ThreadMin(start, end, this);
            threads[i].start();
            start = end;
        }

        return waitForAllThreads();
    }
}

