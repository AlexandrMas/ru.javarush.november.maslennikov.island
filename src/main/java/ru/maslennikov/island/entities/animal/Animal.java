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
    private double weight = ThreadLocalRandom.current().nextDouble(this.getMaxWeight());
    private Map<Direction, Integer> directionOfMovement;
    private boolean abilityToReproduce = true;

    public String getName() {
        return this.getClass().getAnnotation(BasicCharacteristicsOrganism.class).name();
    }

    public String getIcon() {
        return this.getClass().getAnnotation(BasicCharacteristicsOrganism.class).icon();
    }

    public boolean isAbilityToReproduce() {
        return abilityToReproduce;
    }

    public void setAbilityToReproduce(boolean abilityToReproduce) {
        this.abilityToReproduce = abilityToReproduce;
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
        Handler handler = new Handler();
        if (this instanceof Predator) {
            handler.feedThePredator(location, this);
        } else if (this instanceof Herbivorous) {
            handler.feedTheHerbivorous(location, this);
        }
    }

    public void mate(Location location) {
        Handler handler = new Handler();
        if (this instanceof Predator) {
            handler.increaseNumberOfPredators(location, this);
        } else if (this instanceof Herbivorous) {
            handler.increaseNumberOfHerbivorous(location, this);
        }
    }

    public void go(Location location, Island island) {
        Handler handler = new Handler();
        handler.moveTheAnimal(island, location, this);
    }
}
