package q2;

import java.util.Scanner;



public class Main {

    private static int numberOfRuns = 0;
    private static int numberOfThreads = 0;

    /**
     * Receive input from the users regarding the number of threads and runs that he would like to execute
     * hold a value in each thread, and increment/decrement it every run, depending on it's neighbors
     * @param args
     */
    public static void main(String[] args) {
        receiveUserInput();

        //set up the worker threads
        Worker[] workers = new Worker[numberOfThreads];

        //set up monitor
        Monitor monitor = new Monitor(numberOfThreads, numberOfRuns, workers);

        //create the workers, include the monitor, and thread number
        for (int j = 0; j < workers.length; j++) {
            workers[j] = new Worker(monitor, j);
        }

        //start the workers
        for (int j = 0; j < workers.length; j++) {
            workers[j].start();
        }

        while (monitor.getNumberOfRuns() !=0) {//run for the specified number of runs
            while (monitor.checkIfStillRunning()) {//check if there are any more threads that still need to run
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("\nDone");

    }


    /**
     * Receive the user's input
     */
    private static void receiveUserInput() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please input the requested number of runs: ");
        numberOfRuns = sc.nextInt();
        System.out.print("Please input the requested number of threads: ");
        numberOfThreads = sc.nextInt();
        sc.close();
    }


}
