package com.basejava.webapp;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private int counter;
//    private static final Object LOCK = new Object();

    public static void main(String[] args) /*throws InterruptedException*/ {

        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        thread0.start();

        new Thread(() -> System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState())).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threads.add(thread);
        }
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
//        Thread.sleep(500);

        System.out.println(mainConcurrency.counter);
        getDeadLock();
    }

    private synchronized void inc() {
//        synchronized (this);
//        synchronized (MainConcurrency.class) {
//        double a = Math.sin(13.);
//        synchronized (this) {
        counter++;
//        }

    }

    private static void getDeadLock() {
        Resume resume1 = new Resume();
        Resume resume2 = new Resume();
        Thread thread1 = new Thread(() -> {
            synchronized (resume1) {
                System.out.println("Thread 1 lock resume1");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
                System.out.println("Thread 1 trying to lock resume2");
                synchronized (resume2) {
                    System.out.println("Thread 1 lock resume1 and resume2");
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            synchronized (resume2) {
                System.out.println("Thread 2 lock resume2");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
                System.out.println("Thread 2 trying to lock resume1");
                synchronized (resume1) {
                    System.out.println("Thread 2 lock resume2 and resume1");
                }
            }
        });
        thread1.start();
        thread2.start();
    }
}
