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

    public void feedThePredator(Location location, Animal eatingOrganism) {
        Iterator<Predator> iteratorPredators = getOrganismIterator(location.getPredatorsOfLocation());
        Iterator<Herbivorous> iteratorHerbivorouses = getOrganismIterator(location.getHerbivorousOfLocation());
        while (iteratorPredators.hasNext()) {
            Predator nextPredator = iteratorPredators.next();
            if (feedTheBody(eatingOrganism, nextPredator)) {
                iteratorPredators.remove();
                nextPredator = null;
            } else {
                continue;
            }
        }
        while (iteratorHerbivorouses.hasNext()) {
            Herbivorous nextHerbivorous = iteratorHerbivorouses.next();
            if (feedTheBody(eatingOrganism, nextHerbivorous)) {
                iteratorHerbivorouses.remove();
                nextHerbivorous = null;
            } else {
                continue;
            }
        }
    }

    public void feedTheHerbivorous(Location location, Animal eatingOrganism) {
        Iterator<Herbivorous> iteratorHerbivorouses = getOrganismIterator(location.getHerbivorousOfLocation());
        Iterator<Plant> iteratorPlants = getOrganismIterator(location.getPlantsOfLocation());
        int maxNumberOfPlantsEatenByOneAnimal = Setting.get().getMaxNumberOfPlantsEatenByOneAnimal();
        while (iteratorHerbivorouses.hasNext()) {
            Herbivorous nextHerbivorous = iteratorHerbivorouses.next();
            if (feedTheBody(eatingOrganism, nextHerbivorous)) {
                iteratorHerbivorouses.remove();
                nextHerbivorous = null;
            } else {
                continue;
            }
        }
        while (iteratorPlants.hasNext()) {
            Plant nextPlant = iteratorPlants.next();
            if (maxNumberOfPlantsEatenByOneAnimal > 0 && feedTheBody(eatingOrganism, nextPlant)) {
                iteratorPlants.remove();
                maxNumberOfPlantsEatenByOneAnimal--;
                nextPlant = null;
            } else {
                continue;
            }
        }
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

    public void plantLocationWithPlants(Location location, String name) {
        if (name.equalsIgnoreCase("plant")) {
            PlantFactory plantFactory = new PlantFactory();
            for (int i = 0; i < Setting.get().getMaxNumberOfOrganismsOfThisTypeInLocation().get(name); i++) {
                location.getPlantsOfLocation().add(plantFactory.createOrganism(name));
            }
        }
    }

    private boolean establishPossibilityOfHavingOffspring(Location location, Animal animal) {
        int maxNumberOfOrganismsOfThisTypeInLocation = Setting.get().getMaxNumberOfOrganismsOfThisTypeInLocation().get(animal.getName());
        int newNumberOfAnimalsInLocation = location.getStatisticOfLocation().get(animal.getName()) + Setting.get().getMaxNumberOfOffspring().get(animal.getName());
        return getNumberOfThisAnimalInLocation(location, animal) > 1 && maxNumberOfOrganismsOfThisTypeInLocation >= newNumberOfAnimalsInLocation;
    }

    private int getNumberOfThisAnimalInLocation(Location location, Animal animal) {
        if (animal instanceof Predator) {
            return location.getPredatorsOfLocation().stream().filter(e -> e.getName().equalsIgnoreCase(animal.getName())).collect(Collectors.toList()).size();
        }
        return location.getHerbivorousOfLocation().stream().filter(e -> e.getName().equalsIgnoreCase(animal.getName())).collect(Collectors.toList()).size();
    }

    public void increaseNumberOfPredators(Location location, Animal animal) {
        if (establishPossibilityOfHavingOffspring(location, animal)) {
            PredatorFactory predatorFactory = new PredatorFactory();
            for (int i = 0; i < Setting.get().getMaxNumberOfOffspring().get(animal.getName()); i++) {
                location.getPredatorsOfLocation().add(predatorFactory.createOrganism(animal.getName()));
            }
        }
    }

    public void increaseNumberOfHerbivorous(Location location, Animal animal) {
        if (establishPossibilityOfHavingOffspring(location, animal)) {
            HerbivorousFactory herbivorousFactory = new HerbivorousFactory();
            for (int i = 0; i < Setting.get().getMaxNumberOfOffspring().get(animal.getName()); i++) {
                location.getHerbivorousOfLocation().add(herbivorousFactory.createOrganism(animal.getName()));
            }
        }
    }

    public void moveTheAnimal(Island island, Location location, Animal animal) throws ArrayIndexOutOfBoundsException {
        Map<Direction, Integer> directionOfMovement = animal.getDirectionOfMovement();
        int xCoordinate = location.getCoordinates().getxCoordinate();
        int yCoordinate = location.getCoordinates().getyCoordinate();

        if (directionOfMovement.containsKey(Direction.STAY_PUT)) {
            return;
        } else if (directionOfMovement.containsKey(Direction.UP)) {
            yCoordinate -= directionOfMovement.get(Direction.UP);
        } else if (directionOfMovement.containsKey(Direction.DOWN)) {
            yCoordinate += directionOfMovement.get(Direction.DOWN);
        } else if (directionOfMovement.containsKey(Direction.LEFT)) {
            xCoordinate -= directionOfMovement.get(Direction.LEFT);
        } else if (directionOfMovement.containsKey(Direction.RIGHT)) {
            xCoordinate += directionOfMovement.get(Direction.RIGHT);
        }
        try {
            island.getInhabitedIsland()[yCoordinate][xCoordinate].toString();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(animal.getName() + " he left the island where he was eaten by an animal of unknown type");
            return;
        }
        try {
            location.getLock().lock();
            island.getInhabitedIsland()[yCoordinate][xCoordinate].getLock().lock();
            if (animal instanceof Predator) {
                island.getInhabitedIsland()[yCoordinate][xCoordinate].getPredatorsOfLocation().add((Predator) animal);
                location.getPredatorsOfLocation().remove(animal);
            } else if (animal instanceof Herbivorous) {
                island.getInhabitedIsland()[yCoordinate][xCoordinate].getHerbivorousOfLocation().add((Herbivorous) animal);
                location.getHerbivorousOfLocation().remove(animal);
            }
        } finally {
            location.getLock().unlock();
            island.getInhabitedIsland()[yCoordinate][xCoordinate].getLock().unlock();
        }
    }
}
