package org.awalasek.fakedropbox.server;

import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.awalasek.fakedropbox.server.tasks.AbstractTask;

public class TaskScheduler implements Runnable {

    public static final int THREAD_POOL_SIZE = 5;
    private static final int SCHEDULER_SLEEP_TIME_MILLIS = 150;

    private static Logger logger;
    private ExecutorService threadPool;
    private Map<String, Queue<AbstractTask>> userQueues;

    public TaskScheduler(Map<String, Queue<AbstractTask>> userQueues) {
        logger = Logger.getLogger(this.getClass().getName());

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
        for (Entry<String, Queue<AbstractTask>> user : userQueues.entrySet()) {
            if (!user.getValue().isEmpty()) {
                submitTaskToThreadPool(user);
            }
        }
    }

    private void submitTaskToThreadPool(Entry<String, Queue<AbstractTask>> user) {
        try {
            Queue<AbstractTask> tasks = user.getValue();
            threadPool.submit(tasks.poll());
            logger.info("Task submitted, username=" + user.getKey() + ", tasksLeft=" + tasks.size());
        } catch (NullPointerException e) {
        }
    }

    private void sleepToNextCheck() {
        try {
            Thread.sleep(SCHEDULER_SLEEP_TIME_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
