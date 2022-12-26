package ru.maslennikov.island.island;

import ru.maslennikov.island.entities.animal.herbivorous.Herbivorous;
import ru.maslennikov.island.entities.animal.predator.Predator;
import ru.maslennikov.island.entities.plant.Plant;
import ru.maslennikov.island.setting.Setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Location {

    Coordinates coordinates;

    private final List<Predator> predatorsOfLocation;
    private final List<Herbivorous> herbivorousOfLocation;
    private final List<Plant> plantsOfLocation;

    private final Lock lock;

    private Map<String, Integer> maxNumberOfOrganismsOfThisTypeInLocation = Setting.get().getMaxNumberOfOrganismsOfThisTypeInLocation();

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Map<String, Integer> getMaxNumberOfOrganismsOfThisTypeInLocation() {
        return maxNumberOfOrganismsOfThisTypeInLocation;
    }

    public Location(int xCoordinate, int yCoordinate) {
        coordinates = new Coordinates(xCoordinate, yCoordinate);
        predatorsOfLocation = new ArrayList<>();
        herbivorousOfLocation = new ArrayList<>();
        plantsOfLocation = new ArrayList<>();
        lock = new ReentrantLock();
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

    public Lock getLock() {
        return lock;
    }
}
