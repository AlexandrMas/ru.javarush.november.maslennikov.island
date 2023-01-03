package ru.maslennikov.island.handler;

import ru.maslennikov.island.entities.Organism;
import ru.maslennikov.island.entities.animal.Animal;
import ru.maslennikov.island.island.Location;
import ru.maslennikov.island.maker.OrganismFactory.HerbivorousFactory;
import ru.maslennikov.island.maker.OrganismFactory.PlantFactory;
import ru.maslennikov.island.maker.OrganismFactory.PredatorFactory;
import ru.maslennikov.island.setting.Setting;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Handler {

    public boolean feedTheBody(Animal eatingOrganism, Organism eatenOrganism) {
        if (avoidPossibilityOfBeingEaten(eatingOrganism, eatenOrganism)) {
            return false;
        }
        double weight = eatingOrganism.getWeight() + eatenOrganism.getWeight();
        double saturationLevel = eatingOrganism.getSaturationLevel() + eatenOrganism.getWeight();
        if (weight <= eatingOrganism.getMaxWeight()) {
            eatingOrganism.setWeight(weight);
        } else {
            eatingOrganism.setWeight(eatingOrganism.getMaxWeight());
        }
        if (saturationLevel >= eatingOrganism.getSaturationLevel()
                && saturationLevel <= eatingOrganism.getRequiredAmountOfFoodToSatisfy()) {
            eatingOrganism.setSaturationLevel(saturationLevel);
        } else {
            eatingOrganism.setSaturationLevel(eatingOrganism.getRequiredAmountOfFoodToSatisfy());
        }
        return true;
    }

    private boolean avoidPossibilityOfBeingEaten(Animal eatingOrganism, Organism eatenOrganism) {
        int probabilityOfStayingHungry = ThreadLocalRandom.current().nextInt(0, 100);
        if (Objects.isNull(eatenOrganism)
                || Objects.isNull(eatingOrganism)
                || eatenOrganism == eatingOrganism
                || eatenOrganism.getName().equalsIgnoreCase(eatingOrganism.getName())
                || !Setting.get().getProbabilityOfEatingOrganism().keySet().contains(eatingOrganism.getName())
                || eatingOrganism.getSaturationLevel() == eatingOrganism.getRequiredAmountOfFoodToSatisfy()
                || eatingOrganism.getWeight() == eatingOrganism.getMaxWeight()
                || Objects.isNull(Setting.get().getProbabilityOfEatingOrganism().get(eatingOrganism.getName()).containsKey((eatenOrganism.getName())))
                || Objects.isNull(Setting.get().getProbabilityOfEatingOrganism().get(eatingOrganism.getName()).get(eatenOrganism.getName()))
                || !Setting.get().getProbabilityOfEatingOrganism().get(eatingOrganism.getName()).containsKey(eatenOrganism.getName())
                || probabilityOfStayingHungry > Setting.get().getProbabilityOfEatingOrganism().get(eatingOrganism.getName()).get(eatenOrganism.getName())) {
            return true;
        }
        return false;
    }

    public <T> Iterator<T> getOrganismIterator(List<T> organismOfLocation) {
        int minNumberOrganismOfLocation = 5;
        if (organismOfLocation.size() <= minNumberOrganismOfLocation) {
            return organismOfLocation.iterator();
        }
        int fromIndex = ThreadLocalRandom.current().nextInt(0, organismOfLocation.size() / 2);
        int toIndex = ThreadLocalRandom.current().nextInt((organismOfLocation.size() / 2) + 1, organismOfLocation.size() - 1);
        return organismOfLocation.subList(fromIndex, toIndex).iterator();
    }

    public boolean establishPossibilityOfHavingOffspring(Location location, Animal firstAnimal, Animal secondAnimal) {
        int newNumberOfAnimalsInLocation = location.getStatisticOfLocation().get(firstAnimal.getName()) + Setting.get().getMaxNumberOfOffspring().get(firstAnimal.getName());
        if (Objects.nonNull(firstAnimal)
                && Objects.nonNull(secondAnimal)
                && firstAnimal.getName().equalsIgnoreCase(secondAnimal.getName())
                && firstAnimal != secondAnimal
                && Setting.get().getMaxNumberOfOrganismsOfThisTypeInLocation().get(firstAnimal.getName()) >= newNumberOfAnimalsInLocation) {
            return true;
        }
        return false;
    }


    public void populateLocationWithPredators(Location location, String name, int quantity) {
        if (name.equalsIgnoreCase("bear")
                || name.equalsIgnoreCase("boa")
                || name.equalsIgnoreCase("eagle")
                || name.equalsIgnoreCase("fox")
                || name.equalsIgnoreCase("wolf")) {
            PredatorFactory predatorFactory = new PredatorFactory();
            for (int i = 0; i < quantity; i++) {
                location.getPredatorsOfLocation().add(predatorFactory.createOrganism(name));
            }
            Collections.shuffle(location.getPredatorsOfLocation());
        } else {
            return;
        }
    }

    public void plantLocationWithPlants(Location location, String name) {
        if (name.equalsIgnoreCase("plant")) {
            PlantFactory plantFactory = new PlantFactory();
            for (int i = 0; i < Setting.get().getMaxNumberOfOrganismsOfThisTypeInLocation().get(name); i++) {
                location.getPlantsOfLocation().add(plantFactory.createOrganism(name));
            }
        }
    }

    public void populateLocationWithHerbivorous(Location location, String name, int quantity) {
        if (name.equalsIgnoreCase("boar")
                || name.equalsIgnoreCase("mouse")
                || name.equalsIgnoreCase("duck")
                || name.equalsIgnoreCase("buffalo")
                || name.equalsIgnoreCase("deer")
                || name.equalsIgnoreCase("rabbit")
                || name.equalsIgnoreCase("goat")
                || name.equalsIgnoreCase("sheep")
                || name.equalsIgnoreCase("horse")
                || name.equalsIgnoreCase("caterpillar")) {
            HerbivorousFactory herbivorousFactory = new HerbivorousFactory();
            for (int i = 0; i < quantity; i++) {
                location.getHerbivorousOfLocation().add(herbivorousFactory.createOrganism(name));
            }
            Collections.shuffle(location.getHerbivorousOfLocation());
        } else {
            return;
        }
    }

}
