package ru.maslennikov.island.entities.animal;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;
import ru.maslennikov.island.entities.Organism;
import ru.maslennikov.island.entities.animal.herbivorous.Herbivorous;
import ru.maslennikov.island.entities.animal.predator.Predator;
import ru.maslennikov.island.entities.plant.Plant;
import ru.maslennikov.island.handler.Handler;
import ru.maslennikov.island.island.Direction;
import ru.maslennikov.island.island.Location;
import ru.maslennikov.island.setting.Setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal implements Organism {

    private double saturationLevel;
    private double weight = ThreadLocalRandom.current().nextDouble((this.getMaxWeight() / 2), this.getMaxWeight());
    private HashMap<Direction, Integer> directionOfMovement;

    public String getName() {
        return this.getClass().getAnnotation(BasicCharacteristicsOrganism.class).name();
    }

    public void setDirectionOfMovement(HashMap<Direction, Integer> directionOfMovement) {
        this.directionOfMovement = directionOfMovement;
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

    private void chooseDirectionOfTravel(Location location) {
        List<Animal> generalListAnimals = new ArrayList<>(location.getPredatorsOfLocation());
        generalListAnimals.addAll(location.getHerbivorousOfLocation());
        Direction[] directions = Direction.values();
        int randomDirection = ThreadLocalRandom.current().nextInt(directions.length);
        for (Animal animal : generalListAnimals) {
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

    public void eat(Location location) {
        location.getLock().lock();
        Handler handler = new Handler();
        try {
            Iterator<Predator> iteratorPredators = handler.getOrganismIterator(location.getPredatorsOfLocation());
            Iterator<Herbivorous> iteratorHerbivorouses = handler.getOrganismIterator(location.getHerbivorousOfLocation());
            Iterator<Plant> iteratorPlants = handler.getOrganismIterator(location.getPlantsOfLocation());
            int maxNumberOfPlantsEatenByOneAnimal = Setting.get().getMaxNumberOfPlantsEatenByOneAnimal();
            if (this instanceof Predator) {
                while (iteratorPredators.hasNext()) {
                    Predator nextPredator = iteratorPredators.next();
                    if (handler.feedTheBody(this, nextPredator)) {
                        iteratorPredators.remove();
                        nextPredator = null;
                    } else {
                        continue;
                    }
                }
                while (iteratorHerbivorouses.hasNext()) {
                    Herbivorous nextHerbivorous = iteratorHerbivorouses.next();
                    if (handler.feedTheBody(this, nextHerbivorous)) {
                        iteratorHerbivorouses.remove();
                        nextHerbivorous = null;
                    } else {
                        continue;
                    }
                }
            } else if (this instanceof Herbivorous) {
                while (iteratorHerbivorouses.hasNext()) {
                    Herbivorous nextHerbivorous = iteratorHerbivorouses.next();
                    if (handler.feedTheBody(this, nextHerbivorous)) {
                        iteratorHerbivorouses.remove();
                        nextHerbivorous = null;
                    } else {
                        continue;
                    }
                }
                while (iteratorPlants.hasNext()) {
                    Plant nextPlant = iteratorPlants.next();
                    if (maxNumberOfPlantsEatenByOneAnimal > 0 && handler.feedTheBody(this, nextPlant)) {
                        iteratorPlants.remove();
                        maxNumberOfPlantsEatenByOneAnimal--;
                        nextPlant = null;
                    } else {
                        continue;
                    }
                }
            }
        } finally {
            location.getLock().unlock();
        }
    }

    public void mate(Location location) {
        location.getLock().lock();
        Handler handler = new Handler();
        try {
            if (this instanceof Predator) {
                for (int i = 0; i < location.getPredatorsOfLocation().size(); i++) {
                    if (handler.establishPossibilityOfHavingOffspring(location, this, location.getPredatorsOfLocation().get(i))) {
                        handler.populateLocationWithPredators(location, this.getName(), Setting.get().getMaxNumberOfOffspring().get(this.getName()));
                    } else {
                        continue;
                    }
                }

            } else if (this instanceof Herbivorous) {
                for (int i = 0; i < location.getHerbivorousOfLocation().size(); i++) {
                    if (handler.establishPossibilityOfHavingOffspring(location, this, location.getHerbivorousOfLocation().get(i))) {
                        handler.populateLocationWithHerbivorous(location, this.getName(), Setting.get().getMaxNumberOfOffspring().get(this.getName()));
                    } else {
                        continue;
                    }
                }
            }
        } finally {
            location.getLock().unlock();
        }
    }

    public HashMap<Direction, Integer> getDirectionOfMovement() {
        return directionOfMovement;
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
