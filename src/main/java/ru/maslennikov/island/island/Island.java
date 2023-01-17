package ru.maslennikov.island.island;

import ru.maslennikov.island.maker.CreatorOfIsland;

import java.util.List;

public class Island {

    private List<Location> inhabitedIsland;

    public List<Location> getInhabitedIsland() {
        return inhabitedIsland;
    }

    public Island() {
        CreatorOfIsland creatorOfIsland = new CreatorOfIsland();
        inhabitedIsland = creatorOfIsland.creatIsland();
    }

    public Location getLocationByCoordinates(int xCoordinate, int yCoordinate) {
        for (Location location : inhabitedIsland) {
            if (location.getCoordinates().getxCoordinate() == xCoordinate
                    && location.getCoordinates().getyCoordinate() == yCoordinate) {
                return location;
            }
        }
        return null;
    }
}
