package ru.jackwizard.Exercise3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Задание 3. Коллекции
 * 1. Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся).
 * Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
 * Посчитать сколько раз встречается каждое слово.
 *
 * 2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров.
 * В этот телефонный справочник с помощью метода add() можно добавлять записи. С помощью метода get() искать
 * номер телефона по фамилии. Следует учесть, что под одной фамилией может быть несколько телефонов
 * (в случае однофамильцев), тогда при запросе такой фамилии должны выводиться все телефоны.
 */

public class Main {

    public static void main(String[] args) {

        wordCounter();

        System.out.println(System.lineSeparator());

        Phonebook phonebook = new Phonebook();
        phonebook.add("Ivanov",  "890999911111");
        phonebook.add("Petrov",  "890999922222");
        phonebook.add("Sidorov",  "8909993333");
        phonebook.add("Petrov",  "890999944449");
        phonebook.add("Petrov",  "8909999555559");
        phonebook.add("Sidorov",  "8909999666669");
        phonebook.add("Sidorov",  "890999977779");
        phonebook.add("Sidorov",  "89099999777669");
        phonebook.add("Ivanov",  "8909999565699");
        phonebook.add("Ivanov",  "890999934569");
        phonebook.add("Ivanov",  "8909999345639");

        phonebook.get("Petrov");

    }

    public static void wordCounter() {
        ArrayList<String> wordList = new ArrayList<>();
        wordList.add("Арбуз");
        wordList.add("Абрикос");
        wordList.add("Персик");
        wordList.add("Дыня");
        wordList.add("Арбуз");
        wordList.add("Киви");
        wordList.add("Дыня");
        wordList.add("Дыня");
        wordList.add("Персик");
        wordList.add("Арбуз");
        wordList.add("Манго");
        wordList.add("Виноград");
        wordList.add("Киви");
        wordList.add("Персик");
        wordList.add("Киви");
        wordList.add("Киви");
        wordList.add("Персик");
        wordList.add("Киви");
        wordList.add("Виноград");
        wordList.add("Абрикос");
        System.out.println(wordList);

        Map<String, Integer> wordMap = new HashMap<>();
        for (String word: wordList) {
            if (wordMap.containsKey(word)) {
                wordMap.put(word, wordMap.get(word) + 1);
            } else {
                wordMap.put(word, 1);
            }
        }
        System.out.println(wordMap);
    }
}
