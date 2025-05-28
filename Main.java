public class Main {

    public static void main(String[] args) {
        int dim = 10_000_000;
        int threadNum = 2;
        ArrClass arrClass = new ArrClass(dim, threadNum);

        Result result = arrClass.threadMin();

        System.out.println(" Min element : " + result.value);
        System.out.println(" Index : " + result.index);
    }
}
