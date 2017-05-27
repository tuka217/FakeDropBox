package org.awalasek.fakedropbox.server;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

class UploadSchedulerImpl implements UploadScheduler {

    private static Logger logger;
    private Map<String, Queue<AbstractTask>> userQueues;
    private TaskFactory taskFactory;

    public UploadSchedulerImpl() {
        logger = Logger.getLogger(this.getClass().getName());
        userQueues = new ConcurrentHashMap<>();
        taskFactory = new TaskFactory();

        Thread scheduler = new Thread(new TaskScheduler(userQueues));
        scheduler.start();
    }

    @Override
    public void addNewUpload(FileUploadRequest request) {
        createUserQueueIfDoesNotExist(request);
        pushTasksToQueue(request);
    }

    private void createUserQueueIfDoesNotExist(FileUploadRequest request) {
        String username = request.getUsername();
        if (!userQueues.containsKey(username)) {
            userQueues.put(username, new ConcurrentLinkedQueue<>());
            logger.info("New user created, username=" + username);
        }
    }

    private void pushTasksToQueue(FileUploadRequest request) {
        String username = request.getUsername();
        Queue<AbstractTask> tasks = userQueues.get(username);
        tasks.add(taskFactory.getTask(request));
    }

}
