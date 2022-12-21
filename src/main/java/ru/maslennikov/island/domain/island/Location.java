package ru.maslennikov.island.domain.island;

import ru.maslennikov.island.domain.animal.herbivorous.Herbivorous;
import ru.maslennikov.island.domain.animal.predator.Predator;
import ru.maslennikov.island.domain.plant.Plant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Location {

    private final int xCoordinate;
    private final int yCoordinate;
    List<Predator> predatorsOfLocation;
    List<Herbivorous> herbivorousOfLocation;
    List<Plant> plantsOfLocation;

    public Lock lock;

    public static final Map<String, Integer> MAX_NUMBER_OF_OBJECTS_OF_THIS_TYPE_IN_LOCATION = new HashMap<>();

    static {
        MAX_NUMBER_OF_OBJECTS_OF_THIS_TYPE_IN_LOCATION.put("wolf", 30);
        MAX_NUMBER_OF_OBJECTS_OF_THIS_TYPE_IN_LOCATION.put("hours", 20);
        MAX_NUMBER_OF_OBJECTS_OF_THIS_TYPE_IN_LOCATION.put("duck", 200);
        MAX_NUMBER_OF_OBJECTS_OF_THIS_TYPE_IN_LOCATION.put("caterpillar", 1000);
        MAX_NUMBER_OF_OBJECTS_OF_THIS_TYPE_IN_LOCATION.put("grass", 200);
    }

    public Location(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        predatorsOfLocation = new ArrayList<>();
        herbivorousOfLocation = new ArrayList<>();
        plantsOfLocation = new ArrayList<>();
        lock = new ReentrantLock();
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public List<Predator> getPredatorsOfLocation() {
        return predatorsOfLocation;
    }

    public List<Herbivorous> getHerbivorousOfLocation() {
        return herbivorousOfLocation;
    }

    public List<Plant> getPlantsOfLocation() {
        return plantsOfLocation;
    }

}
