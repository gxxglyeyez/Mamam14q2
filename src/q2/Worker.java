package q2;

import java.util.Random;

public class Worker extends Thread {


    private final int MIN_NUMBER = 1;
    private final int MAX_NUMBER = 100;

    private final Monitor monitor;
    private final int threadNumber;
    private int value = 0;
    private int diff = 0;


    /**
     * Create a worker instance with a random number
     */
    public Worker(Monitor monitor, int threadNumber) {
        getRandomNumberUsingNextInt(MIN_NUMBER, MAX_NUMBER);
        this.monitor = monitor;
        this.threadNumber = threadNumber;
    }

    /**
     * getter for value
     * @return the value in the thread
     */
    public int getValue() {
        return value;
    }

    /**
     * run as long as the number of runs does not expire
     * check the neighboring threads if there needs to be an update to it's value
     * wait for all threads to run
     * then add the value to what needs to change
     */
    @Override
    public void run() {
        super.run();
        while (monitor.getNumberOfRuns() !=0) {
            diff = monitor.checkNeighbors(threadNumber);
            monitor.increaseCounter();
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            value = value+diff;

        }
    }


    /**
     * generate random numbers
     * @param min min number
     * @param max max number
     */
    private void getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        value = random.nextInt(max - min) + min;
    }
}
