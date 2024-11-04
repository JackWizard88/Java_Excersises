package ru.jackwizard.Exercise1.Entities;

import ru.jackwizard.Exercise1.Interfaces.Obstacle;
import ru.jackwizard.Exercise1.Interfaces.Overcomeable;

public class Robot implements Overcomeable {
    private final String name;
    private final int runDistance;
    private final int jumpHeight;

    public Robot(String name, int runDistance, int jumpHeight) {
        this.name = name;
        this.runDistance = runDistance;
        this.jumpHeight = jumpHeight;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean jump(Obstacle wall) {
        if (this.jumpHeight >= wall.getHeight()) {
            System.out.println("Робот " + this.getName() + " перепрыгивает "
                    + wall.getHeight() + " м.");
        } else {
            System.out.println("Робот " + this.getName() + " не смог перепрыгнуть " + wall.getHeight() + " м.");
        }
        return this.jumpHeight >= wall.getHeight();
    }

    @Override
    public boolean run(Obstacle distance) {
        if (this.runDistance >= distance.getLength()) {
            System.out.println("Робот " + this.getName() + " пробежал "
                    + distance.getLength() + " м.");
        } else {
            System.out.println("Робот " + this.getName() + " не смог пробежать " + distance.getLength() + " м.");
        }
        return this.runDistance >= distance.getLength();
    }
}
