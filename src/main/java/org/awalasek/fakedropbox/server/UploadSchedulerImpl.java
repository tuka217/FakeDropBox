package org.awalasek.fakedropbox.server;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

class UploadSchedulerImpl implements UploadScheduler {

    private static Logger logger;
    private Map<String, Queue<UploadTask>> userQueues;

    public UploadSchedulerImpl() {
        logger = Logger.getLogger("UploadSchedulerImpl");
        userQueues = new ConcurrentHashMap<>();

        Thread scheduler = new Thread(new TaskScheduler(userQueues));
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
        String filename = request.getFilename();
        Queue<UploadTask> tasks = userQueues.get(username);
        tasks.add(new UploadTask(username, filename));
    }

}