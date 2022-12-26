package ru.maslennikov.island.entities.animal;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;
import ru.maslennikov.island.entities.Organism;
import ru.maslennikov.island.island.Direction;
import ru.maslennikov.island.island.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal implements Organism {

    private double weight = ThreadLocalRandom.current().nextDouble(getMaxWeight(this));
    private HashMap<Direction, Integer> directionOfMovement = this.getDirectionOfMovement();

    public String getName(Animal animal) {
        String name = animal.getClass().getAnnotation(BasicCharacteristicsOrganism.class).name();
        return name;
    }

    public double getMaxWeight(Animal animal) {
        double maxWeight = animal.getClass().getAnnotation(BasicCharacteristicsOrganism.class).maxWeight();
        return maxWeight;
    }

    public double getrequiredAmountOfFoodToSatisfy(Animal animal) {
        double requiredAmountOfFoodToSatisfy = animal.getClass().getAnnotation(BasicCharacteristicsOrganism.class).requiredAmountOfFoodToSatisfy();
        return requiredAmountOfFoodToSatisfy;
    }

    public int getMaxTravelSpeed(Animal animal) {
        int maxTravelSpeed = animal.getClass().getAnnotation(BasicCharacteristicsOrganism.class).maxTravelSpeed();
        return maxTravelSpeed;
    }

    private void chooseDirectionOfTravel(Location location) {
        List<Animal> generalListAnimal = new ArrayList<>();
        generalListAnimal.addAll(location.getPredatorsOfLocation());
        generalListAnimal.addAll(location.getHerbivorousOfLocation());
        Direction[] directions = Direction.values();
        int randomDirection = ThreadLocalRandom.current().nextInt(directions.length);
        for (Animal animal : generalListAnimal) {
            if (animal.getClass().getSimpleName().equalsIgnoreCase("caterpillar")) {
                continue;
            }
            animal.directionOfMovement = new HashMap<>();
            if (directions[randomDirection] == Direction.STAY_PUT) {
                animal.directionOfMovement.put(directions[randomDirection], 0);
            } else {
                animal.directionOfMovement.put(directions[randomDirection], Direction.DOWN.getNumberOfSteps(animal));
            }
        }
    }

    public void mate() {

    }

    public void eat() {

    }

    public HashMap<Direction, Integer> getDirectionOfMovement() {
        return directionOfMovement;
    }
}
