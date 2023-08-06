import java.math.BigInteger;

public class a {
    public static class MyThread extends Thread {
        private BigInteger start;
        private BigInteger end;
        private BigInteger result;

        public MyThread(BigInteger start, BigInteger end) {
            this.start = start;
            this.end = end;
            this.result = BigInteger.ZERO;
        }

        public BigInteger getResult() {
            return result;
        }

        @Override
        public void run() {
            for (BigInteger i = start; i.compareTo(end) <= 0; i = i.add(BigInteger.ONE)) {
                result = result.add(i);
            }
        }
    }

    public static void main(String[] args) {
        BigInteger n = BigInteger.ZERO;
        int numThreads = 1;

        if (args.length >= 1) {
            n = new BigInteger(args[0]);
        }
        if (args.length >= 2) {
            numThreads = Integer.parseInt(args[1]);
        }

        BigInteger a = BigInteger.ZERO;
        BigInteger step = n.divide(BigInteger.valueOf(numThreads));

        MyThread[] threads = new MyThread[numThreads];
        BigInteger start = BigInteger.ONE;
        BigInteger end = step;

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            if (i == numThreads - 1) {
                end = n; // 最后一个线程需要计算剩余的范围
            }

            threads[i] = new MyThread(start, end);
            threads[i].start();

            start = end.add(BigInteger.ONE);
            end = end.add(step);
        }

        try {
            for (int i = 0; i < numThreads; i++) {
                threads[i].join();
                a = a.add(threads[i].getResult());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("结果:" + a + " 耗时:" + totalTime + "ms");
    }
}
