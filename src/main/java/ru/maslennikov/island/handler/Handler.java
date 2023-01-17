package ru.maslennikov.island.handler;

import ru.maslennikov.island.entities.Organism;
import ru.maslennikov.island.entities.animal.Animal;
import ru.maslennikov.island.entities.animal.herbivorous.Herbivorous;
import ru.maslennikov.island.entities.animal.predator.Predator;
import ru.maslennikov.island.entities.plant.Plant;
import ru.maslennikov.island.island.Direction;
import ru.maslennikov.island.island.Island;
import ru.maslennikov.island.island.Location;
import ru.maslennikov.island.maker.OrganismFactory.HerbivorousFactory;
import ru.maslennikov.island.maker.OrganismFactory.OrganismFactory;
import ru.maslennikov.island.maker.OrganismFactory.PlantFactory;
import ru.maslennikov.island.maker.OrganismFactory.PredatorFactory;
import ru.maslennikov.island.setting.Setting;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

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
        if (isNull(eatenOrganism)
                || isNull(eatingOrganism)
                || eatenOrganism == eatingOrganism
                || eatenOrganism.getName().equalsIgnoreCase(eatingOrganism.getName())
                || !Setting.get().getProbabilityOfEatingOrganism().keySet().contains(eatingOrganism.getName())
                && eatingOrganism.getSaturationLevel() == eatingOrganism.getRequiredAmountOfFoodToSatisfy()
                && eatingOrganism.getWeight() == eatingOrganism.getMaxWeight()
                || isNull(Setting.get().getProbabilityOfEatingOrganism().get(eatingOrganism.getName())
                .containsKey((eatenOrganism.getName())))
                || isNull(Setting.get().getProbabilityOfEatingOrganism().get(eatingOrganism.getName())
                .get(eatenOrganism.getName()))
                || !Setting.get().getProbabilityOfEatingOrganism().get(eatingOrganism.getName())
                .containsKey(eatenOrganism.getName())
                || probabilityOfStayingHungry > Setting.get().getProbabilityOfEatingOrganism()
                .get(eatingOrganism.getName()).get(eatenOrganism.getName())) {
            eatingOrganism.setAbilityToReproduce(false);
            return true;
        }
        eatingOrganism.setAbilityToReproduce(true);
        return false;
    }

    public void feedThePredator(Location location, Animal eatingOrganism) {
        location.getLock().lock();
        try {
            Iterator<Predator> iteratorPredators = getOrganismIterator(location.getPredatorsOfLocation());
            Iterator<Herbivorous> iteratorHerbivorouses = getOrganismIterator(location.getHerbivorousOfLocation());
            while (iteratorPredators.hasNext()) {
                if (feedTheBody(eatingOrganism, iteratorPredators.next())) {
                    iteratorPredators.remove();
                    break;
                } else {
                    continue;
                }
            }
            while (iteratorHerbivorouses.hasNext()) {
                if (feedTheBody(eatingOrganism, iteratorHerbivorouses.next())) {
                    iteratorHerbivorouses.remove();
                    break;
                } else {
                    continue;
                }
            }
        } finally {
            location.getLock().unlock();
        }
    }

    public void feedTheHerbivorous(Location location, Animal eatingOrganism) {
        location.getLock().lock();
        try {
            Iterator<Herbivorous> iteratorHerbivorouses = getOrganismIterator(location.getHerbivorousOfLocation());
            Iterator<Plant> iteratorPlants = getOrganismIterator(location.getPlantsOfLocation());
            while (iteratorHerbivorouses.hasNext()) {
                if (feedTheBody(eatingOrganism, iteratorHerbivorouses.next())) {
                    iteratorHerbivorouses.remove();
                    break;
                } else {
                    continue;
                }
            }
            while (iteratorPlants.hasNext()) {
                if (feedTheBody(eatingOrganism, iteratorPlants.next())) {
                    iteratorPlants.remove();
                    break;
                } else {
                    continue;
                }
            }
        } finally {
            location.getLock().unlock();
        }
    }

    public <T> Iterator<T> getOrganismIterator(List<T> organismOfLocation) {
        int minNumberOrganismOfLocation = Setting.get().getMinNumberOrganismOfLocation();
        if (organismOfLocation.size() <= minNumberOrganismOfLocation) {
            return organismOfLocation.iterator();
        }
        int fromIndex = ThreadLocalRandom.current().nextInt(0, organismOfLocation.size() / 2);
        int toIndex = ThreadLocalRandom.current().nextInt((organismOfLocation.size() / 2)
                + 1, organismOfLocation.size() - 1);
        return organismOfLocation.subList(fromIndex, toIndex).iterator();
    }

    public Map<Direction, Integer> getDirectionOfTravel(Animal animal) {
        Map<Direction, Integer> directionOfTravel = new HashMap<>();
        Direction[] directions = Direction.values();
        int randomDirection = ThreadLocalRandom.current().nextInt(directions.length);
        if (animal.getClass().getSimpleName().equalsIgnoreCase("caterpillar")) {
            return null;
        }
        if (directions[randomDirection] == Direction.STAY_PUT) {
            directionOfTravel.put(directions[randomDirection], 0);
        } else {
            directionOfTravel.put(directions[randomDirection], Direction.DOWN.getNumberOfSteps(animal));
        }
        return directionOfTravel;
    }

    public void populateLocationWithPredators(Location location, String name, int quantity) {
        location.getLock().lock();
        try {
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
        } finally {
            location.getLock().unlock();
        }
    }

    public void populateLocationWithHerbivorous(Location location, String name, int quantity) {
        location.getLock().lock();
        try {
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
        } finally {
            location.getLock().unlock();
        }
    }

    public void plantLocationWithPlants(Location location, String name) {
        location.getLock().lock();
        try {
            if (name.equalsIgnoreCase("plant")) {
                PlantFactory plantFactory = new PlantFactory();
                for (int i = 0; i < Setting.get().getMaxNumberOfOrganismsOfThisTypeInLocation().get(name); i++) {
                    location.getPlantsOfLocation().add(plantFactory.createOrganism(name));
                }
            }
        } finally {
            location.getLock().unlock();
        }

    }

    private boolean establishPossibilityOfHavingOffspring(Location location, Animal animal) {
        int maxNumberOfOrganismsOfThisTypeInLocation = Setting.get().getMaxNumberOfOrganismsOfThisTypeInLocation()
                .get(animal.getName());
        int newNumberOfAnimalsInLocation = location.getStatisticOfLocation().get(animal.getIcon())
                + Setting.get().getMaxNumberOfOffspring().get(animal.getName());
        return getNumberOfThisAnimalInLocation(location, animal) > 1
                && maxNumberOfOrganismsOfThisTypeInLocation >= newNumberOfAnimalsInLocation;
    }

    private int getNumberOfThisAnimalInLocation(Location location, Animal animal) {
        if (animal instanceof Predator) {
            return location.getPredatorsOfLocation().stream()
                    .filter(e -> e.getName().equalsIgnoreCase(animal.getName()))
                    .collect(Collectors.toList())
                    .size();
        }
        return location.getHerbivorousOfLocation().stream()
                .filter(e -> e.getName().equalsIgnoreCase(animal.getName()))
                .collect(Collectors.toList())
                .size();
    }

    public void increaseNumberOfPredators(Location location, Animal animal) {
        location.getLock().lock();
        try {
            boolean abilityToHavOffspring = ThreadLocalRandom.current().nextInt(0, 100) > 50;
            int numberOfDescendants = ThreadLocalRandom.current().nextInt(Setting.get().getMaxNumberOfOffspring().get(animal.getName()));
            if (establishPossibilityOfHavingOffspring(location, animal)
                    && abilityToHavOffspring
                    && animal.isAbilityToReproduce()) {
                PredatorFactory predatorFactory = new PredatorFactory();
                for (int i = 0; i < numberOfDescendants; i++) {
                    location.getPredatorsOfLocation().add(predatorFactory.createOrganism(animal.getName()));
                }
            }
        } finally {
            location.getLock().unlock();
        }

    }

    public void increaseNumberOfHerbivorous(Location location, Animal animal) {
        location.getLock().lock();
        try {
            boolean abilityToHavOffspring = ThreadLocalRandom.current().nextInt(0, 100) > 50;
            int numberOfDescendants = ThreadLocalRandom.current().nextInt(Setting.get().getMaxNumberOfOffspring().get(animal.getName()));
            if (establishPossibilityOfHavingOffspring(location, animal)
                    && abilityToHavOffspring
                    && animal.isAbilityToReproduce()) {
                HerbivorousFactory herbivorousFactory = new HerbivorousFactory();
                for (int i = 0; i < numberOfDescendants; i++) {
                    location.getHerbivorousOfLocation().add(herbivorousFactory.createOrganism(animal.getName()));
                }
            }
        } finally {
            location.getLock().unlock();
        }
    }

    private Location determineDestinationLocation(Island island, Location thisLocation, Animal animal) {
        Map<Direction, Integer> directionOfMovement = animal.getDirectionOfMovement();
        int xCoordinate = thisLocation.getCoordinates().getxCoordinate();
        int yCoordinate = thisLocation.getCoordinates().getyCoordinate();
        if (isNull(directionOfMovement)) {
            throw new RuntimeException("the animal did not choose the direction of movement");
        }
        if (directionOfMovement.containsKey(Direction.UP)) {
            yCoordinate -= directionOfMovement.get(Direction.UP);
        }
        if (directionOfMovement.containsKey(Direction.DOWN)) {
            yCoordinate += directionOfMovement.get(Direction.DOWN);
        } else if (directionOfMovement.containsKey(Direction.LEFT)) {
            xCoordinate -= directionOfMovement.get(Direction.LEFT);
        } else if (directionOfMovement.containsKey(Direction.RIGHT)) {
            xCoordinate += directionOfMovement.get(Direction.RIGHT);
        }
        Location destinationLocation = island.getLocationByCoordinates(xCoordinate, yCoordinate);
        return destinationLocation;
    }

    public void moveTheAnimal(Island island, Location thisLocation, Animal animal) throws ArrayIndexOutOfBoundsException {
        thisLocation.getLock().lock();
        if (isNull(animal.getDirectionOfMovement())) {
            return;
        }
        Location destinationLocation = determineDestinationLocation(island, thisLocation, animal);
        if (isNull(destinationLocation)) {
            if (animal instanceof Predator) {
                thisLocation.getPredatorsOfLocation().remove(animal);
            } else if (animal instanceof Herbivorous) {
                thisLocation.getHerbivorousOfLocation().remove(animal);
            }
            return;
        }
        int maxNumberOfOrganismsOfThisTypeInLocation = Setting.get()
                .getMaxNumberOfOrganismsOfThisTypeInLocation().get(animal.getName());
        int numberOfOrganismOfThisTypeInLocation = destinationLocation
                .getStatisticOfLocation().get(animal.getIcon());
        if (maxNumberOfOrganismsOfThisTypeInLocation == numberOfOrganismOfThisTypeInLocation
                || thisLocation == destinationLocation) {
            return;
        }
        try {
            destinationLocation.getLock().lock();
            if (animal instanceof Predator) {
                destinationLocation.getPredatorsOfLocation().add((Predator) animal);
                thisLocation.getPredatorsOfLocation().remove(animal);
            } else if (animal instanceof Herbivorous) {
                destinationLocation.getHerbivorousOfLocation().add((Herbivorous) animal);
                thisLocation.getHerbivorousOfLocation().remove(animal);
            }
        } finally {
            thisLocation.getLock().unlock();
            destinationLocation.getLock().unlock();
        }
    }
}
