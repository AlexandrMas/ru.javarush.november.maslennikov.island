package ru.maslennikov.island.island;

import ru.maslennikov.island.entities.Organism;
import ru.maslennikov.island.entities.animal.herbivorous.Herbivorous;
import ru.maslennikov.island.entities.animal.predator.Predator;
import ru.maslennikov.island.entities.plant.Plant;
import ru.maslennikov.island.setting.Setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Location {

    Coordinates coordinates;
    private final List<Predator> predatorsOfLocation;
    private final List<Herbivorous> herbivorousOfLocation;
    private final List<Plant> plantsOfLocation;
    private final Map<String, Integer> statisticOfLocation;
    private final Lock lock;

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Location(int xCoordinate, int yCoordinate) {
        coordinates = new Coordinates(xCoordinate, yCoordinate);
        predatorsOfLocation = new ArrayList<>();
        herbivorousOfLocation = new ArrayList<>();
        plantsOfLocation = new ArrayList<>();
        statisticOfLocation = new HashMap<>();
        lock = new ReentrantLock(true);
    }

    public Map<String, Integer> getStatisticOfLocation() {
        this.lock.lock();
        Setting.get().getMaxNumberOfOrganismsOfThisTypeInLocation().keySet().stream().forEach(nameAnimal -> statisticOfLocation.put(nameAnimal, 0));
        List<Organism> generalListOfLocation = new ArrayList<>(this.getPredatorsOfLocation());
        generalListOfLocation.addAll(this.herbivorousOfLocation);
        generalListOfLocation.addAll(this.getPlantsOfLocation());
        generalListOfLocation.stream().forEach(animal -> {
            statisticOfLocation.put(animal.getName(), statisticOfLocation.get(animal.getName()) + 1);
        });
        this.lock.unlock();
        return statisticOfLocation;
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
