import java.util.Random;

class Summation extends Thread {

private int[] arr;

private int low, high, partial;

public Summation(int[] arr, int low, int high){

this.arr = arr;

this.low = low;

this.high = Math.min(high, arr.length);

}

public int getPartialSum(){

return partial;

}

public void run(){

partial = sum(arr, low, high);

} // end of run 

public static int sum(int[] arr){

return sum(arr, 0, arr.length);

} // end of sum

public static int sum(int[] arr, int low, int high){

int total = 0;

for (int i = low; i < high; i++) {

total += arr[i];

}

return total;

} // adding up all the random numbers into array

public static int parallelSum(int[] arr){

return parallelSum(arr, Runtime.getRuntime().availableProcessors());

}

public static int parallelSum(int[] arr, int threads){

int size = (int) Math.ceil(arr.length * 1.0 / threads);

Summation[] sums = new Summation[threads];

for (int i = 0; i < threads; i++) {

sums[i] = new Summation(arr, i * size, (i + 1) * size);

sums[i].start();

}

try {

for (Summation sum : sums) {

sum.join();

}

} catch (InterruptedException e) { }

int total = 0;

for (Summation sum : sums) {

total += sum.getPartialSum();

}

return total;

}

} // end of summation class

public class Main{

public static void main(String[] args){

Random rand = new Random();

int[] arr = new int[200000000];

for (int i = 0; i < arr.length; i++) {

arr[i] = rand.nextInt(10) + 1;

}

long start = System.currentTimeMillis();

System.out.println("Time in Milliseconds for single thread: " + Summation.sum(arr));

System.out.println("Single Array: " + (System.currentTimeMillis() - start) + "\n");

start = System.currentTimeMillis();

System.out.println("Time in Milliseconds for multiple threads: " + Summation.parallelSum(arr));

System.out.println("Parallel Array: " + (System.currentTimeMillis() - start));

} //end of main

} //end of Main