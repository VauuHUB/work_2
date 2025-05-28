public class ThreadMin extends Thread {
    private final int startIndex;
    private final int endIndex;
    private final ArrClass arrClass;

    public ThreadMin(int startIndex, int endIndex, ArrClass arrClass) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.arrClass = arrClass;
    }

    @Override
    public void run() {
        int[] arr = arrClass.arr;
        int minVal = Integer.MAX_VALUE;
        int minIdx = -1;

        for (int i = startIndex; i < endIndex; i++) {
            if (arr[i] < minVal) {
                minVal = arr[i];
                minIdx = i;
            }
        }
        arrClass.updateMin(new Result(minVal, minIdx));
        arrClass.incThreadCount();
    }
}

