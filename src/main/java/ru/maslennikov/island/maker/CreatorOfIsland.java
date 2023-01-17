package ru.maslennikov.island.maker;

import ru.maslennikov.island.handler.Handler;
import ru.maslennikov.island.island.Location;
import ru.maslennikov.island.setting.Setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class CreatorOfIsland {

    public List<Location> creatIsland() {
        int maxWidthOfIsland = Setting.get().getMaxWidthOfIsland();
        int maxHeightOfIsland = Setting.get().getMaxHeightOfIsland();
        List<Location> island = new ArrayList<>();
        for (int x = 0; x < maxWidthOfIsland; x++) {
            for (int y = 0; y < maxHeightOfIsland; y++) {
                island.add(new Location((x + 1), (y + 1)));
            }
        }
        fillIslandsWithObjects(island);
        return island;
    }

    private void fillIslandsWithObjects(List<Location> emptyIsland) {
        for (Location location : emptyIsland) {
            determineMaxNumberOfOrganismsInLocation(location);
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
        int bound = ThreadLocalRandom.current().nextInt(origin + 1, Setting.get().getMaxNumberOfOrganismsOfThisTypeInLocation().get(name));
        int minNumberOfIndividualsInLocation = ThreadLocalRandom.current().nextInt(origin, bound);
        handler.populateLocationWithPredators(location, name, minNumberOfIndividualsInLocation);
        handler.populateLocationWithHerbivorous(location, name, minNumberOfIndividualsInLocation);
        handler.plantLocationWithPlants(location, name);
    }
}
