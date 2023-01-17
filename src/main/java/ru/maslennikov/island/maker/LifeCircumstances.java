package ru.maslennikov.island.maker;

import ru.maslennikov.island.entities.animal.Animal;
import ru.maslennikov.island.entities.animal.herbivorous.Herbivorous;
import ru.maslennikov.island.entities.animal.predator.Predator;
import ru.maslennikov.island.island.Location;

public class LifeCircumstances {

    public void starve(Location location) {
        location.getLock().lock();
        try {
            for (int i = 0; i < location.getPredatorsOfLocation().size(); i++) {
                Animal animal = location.getPredatorsOfLocation().get(i);
                if (healthCheck(animal, location)) {
                    loseWeight(animal);
                    healthCheck(animal, location);
                }
            }
            for (int i = 0; i < location.getHerbivorousOfLocation().size(); i++) {
                Animal animal = location.getHerbivorousOfLocation().get(i);
                if (healthCheck(animal, location)) {
                    loseWeight(animal);
                    healthCheck(animal, location);
                }
            }
        } finally {
            location.getLock().unlock();
        }
    }

    private void loseWeight(Animal animal) {
        double caterpillarWeightLossoefficient = 10;
        if (animal.getClass().getSimpleName().equalsIgnoreCase("caterpillar")) {
            animal.setWeight(animal.getWeight() - (animal.getMaxWeight() / caterpillarWeightLossoefficient));
            animal.setSaturationLevel(0);
        } else {
            animal.setWeight(animal.getWeight() - animal.getRequiredAmountOfFoodToSatisfy());
            animal.setSaturationLevel(0);
        }
    }

    public boolean healthCheck(Animal animal, Location location) {
        location.getLock().lock();
        try{
            double minWeight = animal.getMaxWeight() / 3;
            if (animal.getWeight() <= minWeight) {
                die(animal, location);
                return false;
            }
            return true;
        }finally {
            location.getLock().unlock();
        }
    }

    private void die(Animal animal, Location location) {
        if (animal instanceof Predator) {
            location.getPredatorsOfLocation().remove(animal);
        } else if (animal instanceof Herbivorous) {
            location.getHerbivorousOfLocation().remove(animal);
        }
    }
}
