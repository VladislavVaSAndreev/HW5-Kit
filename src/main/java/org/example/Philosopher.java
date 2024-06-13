package org.example;
import java.util.concurrent.CountDownLatch;
public class Philosopher extends Thread {
    private final String name;
    private  final Fork forkLeft;
    private final Fork forkRight;
    private boolean philosopherWaiting = true;
    private static int countPhilosopher = 0;
    private int countEating  = 0;
    private final CountDownLatch countDownLatch;
    private RingTable table;


    public Philosopher(Fork forkLeft, Fork forkRight, CountDownLatch countDownLatch, RingTable table)  {
        this.forkLeft = forkLeft;
        this.forkRight = forkRight;
        this.table = table;
        countPhilosopher++;
        this.name = "Philosopher №" + countPhilosopher;
        this.countDownLatch  = countDownLatch;

    }


    @Override
    public void run() {
        System.out.println(getName() + " start");
        try {
            while (true) {
                if(countEating == 3) {
                    System.out.println(name  +  " is full");
                    thinking();
                    countDownLatch.countDown();
                    break;
                }
                if (table.tryGetForks(forkLeft, forkRight)) eating();
                else thinking();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void eating() throws InterruptedException {
        System.out.println(name + " is eating" + " left forkLeft-"+  forkLeft  +  ", rightforkRight "+  forkRight);
        philosopherWaiting = false;
        countEating++;
        sleep(1000);
        philosopherWaiting = true;
        table.setForksFree(forkLeft, forkRight);
        if (countEating == 3) return;
        thinking();

    }

    public void thinking() throws InterruptedException {
        System.out.println(name + " is thinking");
        philosopherWaiting = false;
        sleep(1000);
        philosopherWaiting = true;
    }


    private boolean isReadyToEat()  {
        return forkLeft.isAvailable()  && forkRight.isAvailable() && philosopherWaiting;
    }


    @Override
    public String toString() {
        return name  + ": forkLeft= " + forkLeft + ", forkRight= " + forkRight + "\n";
    }
}

