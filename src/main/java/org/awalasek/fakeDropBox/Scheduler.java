package org.awalasek.fakeDropBox;

import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

class Scheduler implements Runnable {

    private static final int THREAD_POOL_SIZE = 5;
    private static final int SCHEDULER_SLEEP_TIME = 150;

    private static Logger logger;
    private ExecutorService threadPool;
    private Map<String, Queue<Task>> userQueues;

    public Scheduler(Map<String, Queue<Task>> userQueues) {
        logger = Logger.getLogger("Scheduler");
        
        threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        this.userQueues = userQueues;
    }

    @Override
    public void run() {
        while (true) {
            checkQueuesForRequests();
            sleepToNextCheck();
        }
    }

    private void checkQueuesForRequests() {
        for (Entry<String, Queue<Task>> user : userQueues.entrySet()) {
            if (!user.getValue().isEmpty()) {
                submitTaskToThreadPool(user);
            }
        }
    }

    private void submitTaskToThreadPool(Entry<String, Queue<Task>> user) {
        try {
            Queue<Task> tasks = user.getValue();
            threadPool.submit(tasks.poll());
            logger.info("Task submitted, username="
                        + user.getKey()
                        + ", tasksLeft="
                        + tasks.size());
        } catch (NullPointerException e) {

        }
    }

    private void sleepToNextCheck() {
        try {
            Thread.sleep(SCHEDULER_SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
