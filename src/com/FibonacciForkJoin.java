package com;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class FibonacciForkJoin {

	public static void main(String[] args) {

		ForkJoinPool threadPool = ForkJoinPool.commonPool();

		BigInteger result = threadPool.invoke(new FibonacciForkJoinTask(100));
		System.out.println("This is the factorial of 10 using forkjoin : " + result);
		int normalWayResult = 1;
		for (int i = 1; i <= 3; i++) {
			normalWayResult *= i;
		}
		System.out.println("This is the factorial of 10 using normal way : " + normalWayResult);

		ForkJoinPool threadPool2 = ForkJoinPool.commonPool();
		BigInteger result2 = threadPool2.invoke(new Task(100));
		System.out.println("This is the factorial of 10 using my class : " + result2);

	}
}

class Task extends RecursiveTask<BigInteger> {
	int n;
	int start=1;
	int Threshold = 20;

	public Task(int n) {
		super();
		this.n = n;
	}

	public Task(int start, int n) {
		super();
		this.n = n;
		this.start = start;
	}

	@Override
	protected BigInteger compute() {
		if ((n - start) >= Threshold)
			return ForkJoinTask.invokeAll(subtasks()).stream().map(ForkJoinTask::join).reduce(BigInteger.ONE,
					BigInteger::multiply);
		else
			return calculate(start, n);

	}

	Collection<Task> subtasks() {
		List<Task> subtasks = new ArrayList<Task>();
		int mid = (n + start) / 2;
		subtasks.add(new Task(start, mid));
		subtasks.add(new Task(mid + 1, n));
		return subtasks;
	}

	BigInteger calculate(int start, int n) {

		System.out.println("calculating "+start+" to "+n);
		return IntStream.rangeClosed(start, n).mapToObj(BigInteger::valueOf).reduce(BigInteger.ONE,
				BigInteger::multiply);
	}

}

class FibonacciForkJoinTask extends RecursiveTask<BigInteger> {
	int num;
	int start = 1;
	int threshold = 20;

	public FibonacciForkJoinTask(int num) {
		super();
		this.num = num;
	}

	public FibonacciForkJoinTask(int start, int num) {
		super();
		this.start = start;
		this.num = num;
	}

	@Override
	protected BigInteger compute() {
		if ((num - start) >= threshold) {
			return ForkJoinTask.invokeAll(subTasks()).stream().map(ForkJoinTask::join).reduce(BigInteger.ONE,
					BigInteger::multiply);
		} else {
			return calculate(start, num);
		}

	}

	Collection<FibonacciForkJoinTask> subTasks() {
		List<FibonacciForkJoinTask> subTasksList = new ArrayList<FibonacciForkJoinTask>();
		int mid = (num + start) / 2;
		subTasksList.add(new FibonacciForkJoinTask(start, mid));
		subTasksList.add(new FibonacciForkJoinTask(mid + 1, num));
		return subTasksList;
	}

	BigInteger calculate(int start, int num) {
		System.out.println("calculating from "+start+" to "+num);
		return IntStream.rangeClosed(start, num).mapToObj(BigInteger::valueOf).reduce(BigInteger.ONE,
				BigInteger::multiply);

	}

}
