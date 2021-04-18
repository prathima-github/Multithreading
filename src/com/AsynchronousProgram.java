package com;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsynchronousProgram {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		CompletableFuture.runAsync(() -> process());
		CompletableFuture<String> str = CompletableFuture.supplyAsync(() -> processData());
		try {
			System.out.println(str.get() + "..is the return statement");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// CompletableFuture.runAsync(() → System.out.println("Run async in completable
		// future " + Thread.currentThread()));
		/*
		 * (new Thread(()->{ System.out.println(" this is from new thread");
		 * })).start();
		 */
		ExecutorService threadPool = Executors.newFixedThreadPool(2);
		CompletableFuture<String> string =CompletableFuture.supplyAsync(()->processData(),threadPool);
		try {
			System.out.println(string.get() + "...is the new count");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		string.join();
	}

	static void process() {
		System.out.println("From process");
	}

	static String processData() {
		System.out.println("From process");
		return "this is process2";
	}
	
	

}
