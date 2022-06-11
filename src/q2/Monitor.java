package q2;

public class Monitor {

    private int counter = 0;
    private int numberOfThread = 0;
    private int numberOfRuns = 0;
    private Worker[] workers = null;


    public Monitor(int numberOfThread, int numberOfRuns, Worker[] workers) {
        this.numberOfThread = numberOfThread;
        this.numberOfRuns = numberOfRuns;
        this.workers = workers;
    }

    public int getNumberOfRuns() {
        return numberOfRuns;
    }

    /**
     * check if there are still any threads running neighbor checks based on an incremental counter
     * return true if there are still checks in progress.
     *
     * when all threads finish:
     * - print the thread's status
     * - reset the counter and increment the number of runs
     * - notify all the workers they can start adding the value and check neighbors again
     *
     * @return if the states are still running neighbor checks
     */
    public synchronized boolean checkIfStillRunning() {
        if (counter < numberOfThread) {
            return true;
        }

        printWorkers(workers);
        counter = 0;
        numberOfRuns--;

        for (Worker worker:workers
             ) {
            synchronized (worker) {
                worker.notify();
            }
        }
        return false;
    }

    /**
     * increase the monitor's counter of how many threads ran
     */
    public synchronized void increaseCounter() {
        counter++;
    }

    /**
     * check if the neighboring threads have a higher or lower number and change own value based on it
     *
     * @param threadNumber the number of the thread
     * @return 1 if needs to increase, -1 if needs to decease
     */
    public int checkNeighbors(int threadNumber) {
        int above = threadNumber + 1;
        int below = threadNumber - 1;
        int diff = 0;

        if (threadNumber + 1 == numberOfThread) { //cycle to start of array if at end
            above = 0;
        }
        if (threadNumber - 1 == -1) {//cycle to end of array if at start
            below = numberOfThread - 1;
        }

        if (workers[above].getValue() > workers[threadNumber].getValue()
                && workers[below].getValue() > workers[threadNumber].getValue()) {
            diff = -1;
        } else if (workers[above].getValue() < workers[threadNumber].getValue()
                && workers[below].getValue() < workers[threadNumber].getValue()) {
            diff = 1;
        }
        return diff;
    }

    /**
     * print the worker's values
     * @param workers worker's array
     */
    public void printWorkers(Worker[] workers) {
        System.out.print("\nThe array that was created: [");
        for (int i = 0; i < workers.length - 1; i++) {
            System.out.print("\"" + workers[i].getValue() + ",");
        }
        System.out.print("\"" + workers[workers.length - 1].getValue() + "\"]");

    }

}
