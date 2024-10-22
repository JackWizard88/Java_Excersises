package ru.jackwizard.Exersise1.Entities;

import ru.jackwizard.Exersise1.Interfaces.Obstacle;
import ru.jackwizard.Exersise1.Interfaces.Overcomeable;

public class Wall implements Obstacle {
    private final int id;
    private final int height;
    private static int counter;

    public Wall(int height) {
        this.id = ++counter;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean performAction(Overcomeable participant) {
        return participant.jump(this);
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getLength() {
        return 0;
    }
}
