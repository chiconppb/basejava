package com.basejava.webapp;

import com.basejava.webapp.model.Resume;

public class DeadLock {
    private final Resume resume1 = new Resume("resume1");
    private final Resume resume2 = new Resume("resume2");
    private int counter;

    public void doDeadLock() {
        getThread(resume1, resume2).start();
        getThread(resume2, resume1).start();
    }

    private Thread getThread(Resume resume1, Resume resume2) {
        return new Thread(() -> {
            synchronized (resume1) {
                int threadNumber = ++counter;
                System.out.println("Thread " + threadNumber + " lock " + resume1.getFullName());
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
                System.out.println("Thread " + threadNumber + " trying to lock " + resume2.getFullName());
                synchronized (resume2) {
                    System.out.println("Thread " + threadNumber + "lock " + resume1.getFullName() + " and" + resume2.getFullName());
                }
            }
        });
    }
}
