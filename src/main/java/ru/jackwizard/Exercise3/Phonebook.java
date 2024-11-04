package ru.jackwizard.Exercise3;

import java.util.HashMap;
import java.util.Map;

public class Phonebook {

    private final HashMap<String, String> data;

    public Phonebook() {
        data = new HashMap<>();
    }

    public void add(String name, String phoneNumber) {
        data.put(phoneNumber, name);
    }

    public void get(String name) {
        System.out.printf("Searching by name: %s%n", name);

        for (Map.Entry<String, String> entry : data.entrySet()) {
            if ((entry.getValue()).equals(name)) {
                System.out.println(entry.getValue() + " : " + entry.getKey());
            }
        }

    }


}
