package ru.maslennikov.island.island;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;
import ru.maslennikov.island.entities.animal.Animal;

import java.util.concurrent.ThreadLocalRandom;

public enum Direction {
    UP, DOWN, LEFT, RIGHT, STAY_PUT;

    public int getNumberOfSteps(Animal animal) {
        int e = ThreadLocalRandom.current().nextInt(animal.getClass().getAnnotation(BasicCharacteristicsOrganism.class).maxTravelSpeed() + 1);
        return e > 0 ? e : e + 1;
    }
}
