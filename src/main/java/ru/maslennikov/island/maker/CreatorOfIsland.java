package ru.maslennikov.island.maker;

import ru.maslennikov.island.handler.Handler;
import ru.maslennikov.island.island.Location;
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
        for (Map.Entry<String, Integer> organismInLocation : Setting.get().getMaxNumberOfOrganismsOfThisTypeInLocation().entrySet()) {
            fillingLocationWithOrganisms(location, organismInLocation.getKey());
        }
    }


    private void fillingLocationWithOrganisms(Location location, String name) {
        Handler handler = new Handler();
        int origin = Setting.get().getMaxNumberOfOrganismsOfThisTypeInLocation().get(name) / 2;
        int bound = ThreadLocalRandom.current().nextInt(origin + 1, Setting.get().getMaxNumberOfOrganismsOfThisTypeInLocation().get(name) - 1);
        int minNumberOfIndividualsInLocation = ThreadLocalRandom.current().nextInt(origin, bound);
        handler.populateLocationWithPredators(location, name, minNumberOfIndividualsInLocation);
        handler.populateLocationWithHerbivorous(location, name, minNumberOfIndividualsInLocation);
        handler.plantLocationWithPlants(location, name);
    }
}
