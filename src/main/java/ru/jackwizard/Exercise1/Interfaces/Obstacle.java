package ru.jackwizard.Exercise1.Interfaces;

public interface Obstacle {
    boolean performAction(Overcomeable participant);
    int getHeight();
    int getLength();
}
