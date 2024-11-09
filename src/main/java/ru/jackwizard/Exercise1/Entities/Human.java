package ru.jackwizard.Exercise1.Entities;

import ru.jackwizard.Exercise1.Interfaces.Obstacle;
import ru.jackwizard.Exercise1.Interfaces.Overcomeable;

public class Human implements Overcomeable {
    private final String name;
    private final int runDistance;
    private final int jumpHeight;

    public Human(String name, int runDistance, int jumpHeight) {
        this.name = name;
        this.runDistance = runDistance;
        this.jumpHeight = jumpHeight;
    }


    @Override
    public boolean jump(Obstacle wall) {
        if (this.jumpHeight >= wall.getHeight()) {
            System.out.println("Человек " + this.getName() + " перепрыгивает "
                    + wall.getHeight() + " м.");
        } else {
            System.out.println("Человек " + this.getName() + " не смог перепрыгнуть " + wall.getHeight() + " м.");
        }
        return this.jumpHeight >= wall.getHeight();
    }

    @Override
    public boolean run(Obstacle distance) {
        if (this.runDistance >= distance.getLength()) {
            System.out.println("Человек " + this.getName() + " пробежал "
                    + distance.getLength() + " м.");
        } else {
            System.out.println("Человек " + this.getName() + " не смог пробежать " + distance.getLength() + " м.");
        }
        return this.runDistance >= distance.getLength();
    }

    public String getName() {
        return name;
    }

    public int getJumpHeight() {
        return jumpHeight;
    }

    public int getRunDistance() {
        return runDistance;
    }
}
