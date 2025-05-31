package TreadLearn;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

class Seller implements Runnable {
    private static int tickets = 10000;
    private static Object lock = new Object();
    int cnt;
    Seller() {
        cnt = 0;
    }
    public void run() {
        while(true) {
            synchronized (lock) {
                if (Seller.tickets > 0) {
                    Seller.tickets--;
                    cnt ++;
                }
                else break;
            }
        }
        System.out.println(Thread.currentThread().getName() + ": " + cnt);
    }
}

public class SellTicketDemo {
    static public void main(String[] args) {
        Seller seller = new Seller();
        Thread thread1 = new Thread(seller, "seller1");
        Thread thread2 = new Thread(seller, "seller2");
        Thread thread3 = new Thread(seller, "seller3");
        thread1.start();
        thread2.start();
        thread3.start();

        FutureTask<Integer> task = new FutureTask<Integer>(
                (Callable<Integer>) () -> {
                    return 1;
                }uu
        );
    }
}


