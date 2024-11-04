package ru.jackwizard.Exercise4;

import java.util.Arrays;

/**
 * Упражнение 5. Многопоточность
 * 1. Необходимо написать два метода, которые делают следующее:
 * 1) Создают одномерный длинный массив *
 * 2) Заполняют этот массив единицами;
 * 3) Засекают время выполнения: long a = System.currentTimeMillis();
 * 4) Проходят по всему массиву и для каждой ячейки считают новое значение по формуле:
 *      arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
 * 5) Проверяется время окончания метода System.currentTimeMillis();
 * 6) В консоль выводится время работы: System.out.println(System.currentTimeMillis() - a);
 */

public class Main {

    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;
    static float[] arr = new float[SIZE];
    static float[] arr1 = new float[SIZE];
    static float[] arr2 = new float[SIZE];

    public static void main(String[] args) {

        Arrays.fill(arr, 1.0f);
        calcMethod1(arr);
        Arrays.fill(arr1, 1.0f);
        calcMethod2(arr1);
        Arrays.fill(arr2, 1.0f);
        calcMethod3(arr2);

        System.out.println("Arrays 1 and 2 are equal: " + Arrays.equals(arr, arr1));
        System.out.println("Arrays 2 and 3 are equal: " + Arrays.equals(arr1, arr2));
        System.out.println("Arrays 1 and 3 are equal: " + Arrays.equals(arr, arr2));
    }

    //метод однопоточного расчета
    public static void calcMethod1(float[] arr) {

        long a = System.currentTimeMillis();
        calculation(arr, 0, SIZE);
        System.out.println("Estimated time (single thread), ms: " + (System.currentTimeMillis() - a));
    }

    //метод двух-поточного расчета
    public static void calcMethod2(float[] arr) {

        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];

        long a = System.currentTimeMillis();

        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);

        Thread thread1 =  new Thread(() -> calculation(a1, 0, HALF));
        Thread thread2 =  new Thread(() -> calculation(a2, HALF , HALF));

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);

        System.out.println("Estimated time (multithreading x2), ms: " + (System.currentTimeMillis() - a));

    }

    //метод четырех-поточного расчета
    public static void calcMethod3(float[] arr) {

        float[] a1 = new float[SIZE/4];
        float[] a2 = new float[SIZE/4];
        float[] a3 = new float[SIZE/4];
        float[] a4 = new float[SIZE/4];

        long a = System.currentTimeMillis();

        System.arraycopy(arr, 0, a1, 0, SIZE/4);
        System.arraycopy(arr, SIZE/4, a2, 0, SIZE/4);
        System.arraycopy(arr, SIZE/2, a3, 0, SIZE/4);
        System.arraycopy(arr, SIZE/2 + SIZE/4, a4, 0, SIZE/4);

        Thread thread1 =  new Thread(() -> calculation(a1, 0, SIZE/4));
        Thread thread2 =  new Thread(() -> calculation(a2, SIZE/4, SIZE/4));
        Thread thread3 =  new Thread(() -> calculation(a3, SIZE/2, SIZE/4));
        Thread thread4 =  new Thread(() -> calculation(a4, SIZE*3/4, SIZE/4));

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, SIZE/4);
        System.arraycopy(a2, 0, arr, SIZE/4, SIZE/4);
        System.arraycopy(a3, 0, arr, HALF, SIZE/4);
        System.arraycopy(a4, 0, arr, SIZE * 3/4, SIZE/4);

        System.out.println("Estimated time (multithreading x4), ms: " + (System.currentTimeMillis() - a));

    }

    //метод вычислений по формуле из задания
    public static void calculation(float[] arr, int shift, int length) {
        for (int i = 0; i < length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + (i + shift) / 5) * Math.cos(0.2f + (i + shift) / 5) * Math.cos(0.4f + (i + shift) / 2));
        }
    }
}
