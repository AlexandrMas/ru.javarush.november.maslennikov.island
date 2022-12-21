package ru.maslennikov.island.domain.island;

import ru.maslennikov.island.utilite.ialand_object_factory.HerbivorousFactory;
import ru.maslennikov.island.utilite.ialand_object_factory.PlantFactory;
import ru.maslennikov.island.utilite.ialand_object_factory.PredatorFactory;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Island {
    private Location[][] inhabitedIsland;

    public Island() {
    }

    {
        creatIsland();
    }

    public Location[][] getInhabitedIsland() {
        return inhabitedIsland;
    }

    private void creatIsland() {
        Scanner console = new Scanner(System.in);
        System.out.println("enter maximum height of the island");
        int xCoordinate = Integer.parseInt(console.nextLine());
        System.out.println("enter maximum weigh of the island");
        int yCoordinate = Integer.parseInt(console.nextLine());
        inhabitedIsland = new Location[xCoordinate][yCoordinate];
        for (int x = 0; x < inhabitedIsland.length; x++) {
            for (int y = 0; y < inhabitedIsland[x].length; y++) {
                inhabitedIsland[x][y] = new Location(x, y);
            }
        }
        fillIslandsWithObjects();
    }

    private void fillIslandsWithObjects() {
        for (Location[] locations : inhabitedIsland) {
            for (Location location : locations) {
                determineMaxNumberOfIndividualsInLocation(location);
            }
        }
    }

    private void determineMaxNumberOfIndividualsInLocation(Location location) {
        for (Map.Entry<String, Integer> objectIsland : Location.MAX_NUMBER_OF_OBJECTS_OF_THIS_TYPE_IN_LOCATION.entrySet()) {
            fillLocationWithObjects(location, objectIsland);
        }
    }

    private void fillLocationWithObjects(Location location, Map.Entry<String, Integer> objectIsland) {
        PredatorFactory predatorFactory = new PredatorFactory();
        HerbivorousFactory herbivorousFactory = new HerbivorousFactory();
        PlantFactory plantFactory = new PlantFactory();
        int random = ThreadLocalRandom.current().nextInt(objectIsland.getValue());
        for (int i = 0; i < random; i++) {
            if (objectIsland.getKey().equalsIgnoreCase("wolf")) {
                location.getPredatorsOfLocation().add(predatorFactory.createIslandObject(objectIsland.getKey()));
            } else if (objectIsland.getKey().equalsIgnoreCase("duck")
                    || objectIsland.getKey().equalsIgnoreCase("hours")
                    || objectIsland.getKey().equalsIgnoreCase("caterpillar")) {
                location.getHerbivorousOfLocation().add(herbivorousFactory.createIslandObject(objectIsland.getKey()));
            } else if (objectIsland.getKey().equalsIgnoreCase("grass")) {
                location.getPlantsOfLocation().add(plantFactory.createIslandObject(objectIsland.getKey()));
            }
        }
    }
}
