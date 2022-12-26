package ru.maslennikov.island.maker;

import ru.maslennikov.island.island.Location;
import ru.maslennikov.island.maker.OrganismFactory.HerbivorousFactory;
import ru.maslennikov.island.maker.OrganismFactory.PlantFactory;
import ru.maslennikov.island.maker.OrganismFactory.PredatorFactory;
import ru.maslennikov.island.setting.Setting;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class CreatorOfIsland {

    public Location[][] creatIsland() {
        int maxWidthOfIsland = Setting.get().getMaxWidthOfIsland();
        int maxHeightOfIsland = Setting.get().getMaxHeightOfIsland();
        Location[][] island = new Location[maxHeightOfIsland][maxWidthOfIsland];
        for (int x = 0; x < island.length; x++) {
            for (int y = 0; y < island[x].length; y++) {
                island[x][y] = new Location(x, y);
            }
        }
        fillIslandsWithObjects(island);
        return island;
    }

    private void fillIslandsWithObjects(Location[][] emptyIsland) {
        for (Location[] locations : emptyIsland) {
            for (Location location : locations) {
                determineMaxNumberOfOrganismsInLocation(location);
            }
        }
    }

    private void determineMaxNumberOfOrganismsInLocation(Location location) {
        for (Map.Entry<String, Integer> organism : location.getMaxNumberOfOrganismsOfThisTypeInLocation().entrySet()) {
            fillingLocationWithOrganisms(location, organism);
        }
    }

    private void fillingLocationWithOrganisms(Location location, Map.Entry<String, Integer> organisms) {
        PredatorFactory predatorFactory = new PredatorFactory();
        HerbivorousFactory herbivorousFactory = new HerbivorousFactory();
        PlantFactory plantFactory = new PlantFactory();
        for (int i = 0; i < ThreadLocalRandom.current().nextInt(organisms.getValue()); i++) {
            if (organisms.getKey().equalsIgnoreCase("wolf")
                    || organisms.getKey().equalsIgnoreCase("boa")
                    || organisms.getKey().equalsIgnoreCase("fox")
                    || organisms.getKey().equalsIgnoreCase("bear")
                    || organisms.getKey().equalsIgnoreCase("eagle")) {
                location.getPredatorsOfLocation().add(predatorFactory.createOrganism(organisms.getKey()));
            } else if (organisms.getKey().equalsIgnoreCase("hours")
                    || organisms.getKey().equalsIgnoreCase("deer")
                    || organisms.getKey().equalsIgnoreCase("rabbit")
                    || organisms.getKey().equalsIgnoreCase("mouse")
                    || organisms.getKey().equalsIgnoreCase("goat")
                    || organisms.getKey().equalsIgnoreCase("sheep")
                    || organisms.getKey().equalsIgnoreCase("boar")
                    || organisms.getKey().equalsIgnoreCase("buffalo")
                    || organisms.getKey().equalsIgnoreCase("duck")
                    || organisms.getKey().equalsIgnoreCase("caterpillar")) {
                location.getHerbivorousOfLocation().add(herbivorousFactory.createOrganism(organisms.getKey()));
            } else if (organisms.getKey().equalsIgnoreCase("plant")) {
                location.getPlantsOfLocation().add(plantFactory.createOrganism(organisms.getKey()));
            }
        }
    }
}
