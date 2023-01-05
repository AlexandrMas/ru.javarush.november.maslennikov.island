package ru.maslennikov.island.entities.animal;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;
import ru.maslennikov.island.entities.Organism;
import ru.maslennikov.island.entities.animal.herbivorous.Herbivorous;
import ru.maslennikov.island.entities.animal.predator.Predator;
import ru.maslennikov.island.handler.Handler;
import ru.maslennikov.island.island.Direction;
import ru.maslennikov.island.island.Island;
import ru.maslennikov.island.island.Location;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal implements Organism {

    private double saturationLevel;
    private double weight = ThreadLocalRandom.current().nextDouble((this.getMaxWeight() / 2), this.getMaxWeight());
    private Map<Direction, Integer> directionOfMovement;

    public String getName() {
        return this.getClass().getAnnotation(BasicCharacteristicsOrganism.class).name();
    }

    public double getSaturationLevel() {
        return saturationLevel;
    }

    public void setSaturationLevel(double saturationLevel) {
        this.saturationLevel = saturationLevel;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getMaxWeight() {
        return this.getClass().getAnnotation(BasicCharacteristicsOrganism.class).maxWeight();
    }

    public double getRequiredAmountOfFoodToSatisfy() {
        return this.getClass().getAnnotation(BasicCharacteristicsOrganism.class).satiation();
    }

    public int getMaxTravelSpeed() {
        return this.getClass().getAnnotation(BasicCharacteristicsOrganism.class).maxTravelSpeed();
    }

    public Map<Direction, Integer> getDirectionOfMovement() {
        return directionOfMovement;
    }

    public void chooseDirectionOfTravel() {
        Handler handler = new Handler();
        directionOfMovement = handler.getDirectionOfTravel(this);
    }

    public void eat(Location location) {
        location.getLock().lock();
        try {
            Handler handler = new Handler();
            if (this instanceof Predator) {
                handler.feedThePredator(location, this);
            } else if (this instanceof Herbivorous) {
                handler.feedTheHerbivorous(location, this);
            }
        } finally {
            location.getLock().unlock();
        }
    }

    public void mate(Location location) {
        location.getLock().lock();
        try {
            Handler handler = new Handler();
            if (this instanceof Predator) {
                handler.increaseNumberOfPredators(location, this);
            } else if (this instanceof Herbivorous) {
                handler.increaseNumberOfHerbivorous(location, this);
            }
        } finally {
            location.getLock().unlock();
        }
    }

    public void go(Location location, Island island) {
        Handler handler = new Handler();
        handler.moveTheAnimal(island, location, this);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "saturationLevel=" + saturationLevel +
                ", weight=" + weight +
                ", directionOfMovement=" + directionOfMovement +
                '}';
    }
}
