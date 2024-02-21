package com.exaple.solutionBeforeJava8;
//Print even and odd number using two thread java

import java.util.Objects;

class RunnableImpl implements Runnable{
    Object object;
    static int i =0;
    public RunnableImpl(Object obj){
        this.object = obj;
    }
    @Override
    public void run() {
        while(i<=10){
            if (i%2==0 && Objects.equals(Thread.currentThread().getName(), "even")){
                synchronized (object){
                    System.out.println(Thread.currentThread().getName()+" "+i);
                    i++;
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (i%2 != 0 && Objects.equals(Thread.currentThread().getName(), "odd")){
                synchronized (object){
                    System.out.println(Thread.currentThread().getName()+" "+i);
                    i++;
                    object.notify();
                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args){
        Object object = new Object();
        Runnable runnable1 = new RunnableImpl(object);
        Runnable runnable2 = new RunnableImpl(object);
        new Thread(runnable1,"even").start();
        new Thread(runnable2,"odd").start();
    }
}
