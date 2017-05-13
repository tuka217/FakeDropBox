package org.awalasek.fakeDropBox;

import java.util.Random;
import java.util.logging.Logger;

class Task implements Runnable {

    private static Logger logger;

    public Task(String username) {
        logger = Logger.getLogger("Task");
        this.username = username;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(3) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("Upload finished, username=" + username);
    }

    private String username;
}
