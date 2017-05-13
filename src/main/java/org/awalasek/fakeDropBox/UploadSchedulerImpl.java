package org.awalasek.fakeDropBox;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public class UploadSchedulerImpl implements UploadScheduler {

    private static Logger logger;
    private Map<String, Queue<Task>> userQueues;

    public UploadSchedulerImpl() {
        logger = Logger.getLogger("UploadSchedulerImpl");

        userQueues = new ConcurrentHashMap<>();

        Thread scheduler = new Thread(new Scheduler(userQueues));
        scheduler.start();
    }

    @Override
    public void addNewUpload(FileUploadRequest request) {
        createUserQueue(request);
        pushTasksToQueue(request);
    }

    private void createUserQueue(FileUploadRequest request) {
        String username = request.getUsername();
        if (!userQueues.containsKey(username)) {
            userQueues.put(username, new ConcurrentLinkedQueue<>());
            logger.info("New user created, username=" + username);
        }
    }

    private void pushTasksToQueue(FileUploadRequest request) {
        String username = request.getUsername();
        Queue<Task> tasks = userQueues.get(username);
        for (int i = 0; i < request.getFileAmount(); ++i) {
            tasks.add(new Task(username));
        }
    }

}
