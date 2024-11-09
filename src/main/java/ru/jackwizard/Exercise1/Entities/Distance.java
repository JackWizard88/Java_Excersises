package ru.jackwizard.Exercise1.Entities;

import ru.jackwizard.Exercise1.Interfaces.Obstacle;
import ru.jackwizard.Exercise1.Interfaces.Overcomeable;

public class Distance implements Obstacle {
    private final int id;
    private final int length;
    private static int counter;

    public Distance(int length) {
        this.id = ++counter;
        this.length = length;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean performAction(Overcomeable participant) {
        return participant.run(this);
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getLength() {
        return length;
    }
}
