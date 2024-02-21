package com.example.solutionJava8;

//Print even and odd number in two thread using java 8

import java.util.concurrent.CompletableFuture;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class Main {
    private static Object object = new Object();
    private static IntPredicate evenPredicate = x->x%2==0;
    private static IntPredicate oddPredicate = x->x%2!=0;

    public static void main(String[] args) throws InterruptedException {
        CompletableFuture.runAsync(()->Main.printAll(evenPredicate));
        CompletableFuture.runAsync(()->Main.printAll(oddPredicate));
        Thread.sleep(1000);
    }
    public static void printAll(IntPredicate condition){
        IntStream.rangeClosed(0,10).filter(condition).forEach(Main::execute);
    }

    public static void execute(int no){
        synchronized (object){
            try {
                System.out.println(Thread.currentThread().getName() +" "+ no);
                object.notify();
                object.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
