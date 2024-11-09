package ru.jackwizard.Exercise2;

import ru.jackwizard.Exercise2.MyExceptions.MyArrayDataException;
import ru.jackwizard.Exercise2.MyExceptions.MyArraySizeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/** Задание 2. Исключения
 1. Напишите метод, на вход которого подаётся двумерный строковый массив размером 4х4, при подаче массива другого
 размера необходимо бросить исключение MyArraySizeException.
 2. Далее метод должен пройтись по всем элементам массива, преобразовать в int, и просуммировать. Если в каком-то
 элементе массива преобразование не удалось (например, в ячейке лежит символ или текст вместо числа), должно быть
 брошено исключение MyArrayDataException, с детализацией в какой именно ячейке лежат неверные данные.
 3. В методе main() вызвать полученный метод, обработать возможные исключения MySizeArrayException и
 MyArrayDataException, и вывести результат расчета.
*/

public class Main {

    public static void main(String[] args) {

        String[][] array = createArray();
        try {
            System.out.println(arraySumm(array));
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.println(e.getMessage());
        }
    }

    //метод для создания массива вручную или тестовым генератором

    private static String[][] createArray() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String generateArrError = "Введите \"1\" для создания и заполнения массива вручную. \n" +
                "Введите \"0\" чтобы сгенерировать тестовый массив размерностью int[4...5][3...4] наполненный случайными числами от 0 до 100. ";
        System.out.println(generateArrError);
        int k = 0,l = 0; // размерности массива
        String[][] array = null;

        while (array == null) {
            try {
                int x = Integer.parseInt(reader.readLine());

                switch (x) {
                    case (1):
                        while (array == null) {
                            try {
                                System.out.println("Введите размерность массива (два числа через пробел)");
                                System.out.println("Для корректной работы массив должен иметь размерность 4х4");
                                String[] size = reader.readLine().split(" ");
                                k = Integer.parseInt(size[0]);
                                if (k < 1) throw new NumberFormatException();
                                l = Integer.parseInt(size[1]);
                                if (l < 1) throw new NumberFormatException();
                                array = new String[k][l];
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (NumberFormatException e) {
                                System.out.println("Указанны неверные размерности массива.");
                            }
                        }

                        for (int i = 0; i < k; i++) {
                            for (int j = 0; j < l; j++) {
                                System.out.printf("Элемент %d,%d ", i, j);
                                array[i][j] = (reader.readLine());
                            }
                        }
                        break;
                    case (0):
                        Random random = new Random();
                        k = random.nextInt(2) + 3;
                        l = random.nextInt(2) + 3;
                        array = new String[k][l];
                        for (int i = 0; i < array.length; i++) {
                            for (int j = 0; j < array[i].length; j++) {
                                array[i][j] = ("" + random.nextInt(100));
                            }
                        }
                        System.out.println("Сгенерирован случайный массив: ");
                        printArray(array);
                        break;
                    default: throw new NumberFormatException();
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println(generateArrError);
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    //метод для вывода массива
    public static void printArray(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    //метод для суммирования всех элементов массива в случае валидности
    public static String arraySumm(String[][] stringArray) throws MyArraySizeException, MyArrayDataException {
        int summ = 0;
        if (stringArray.length != 4) throw new MyArraySizeException();  //исключение если столбец больше 4

        for (int i = 0; i < stringArray.length; i++) {
                if (i == 1 && stringArray[i].length != 4) throw new MyArraySizeException();  //исключение если стока больше 4
                for (int j = 0; j < stringArray[i].length; j++) {

                    try {
                        summ += Integer.parseInt(stringArray[i][j]);
                    } catch (NumberFormatException | NullPointerException e) {
                        throw new MyArrayDataException(i, j);  //кидаем исключение если элемент имеет неверный формат
                    }
                }
            }

        return "Сумма всех элементов массива = " + summ;
    }
}
