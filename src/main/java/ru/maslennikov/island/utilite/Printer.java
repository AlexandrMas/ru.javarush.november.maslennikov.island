package ru.maslennikov.island.utilite;

import ru.maslennikov.island.island.Island;
import ru.maslennikov.island.island.Location;

public class Printer {
    public void printStstisticOfIsland(Island island) {
        for (Location location : island.getInhabitedIsland()) {
            location.getLock().lock();
            try {
                System.out.println(location.getCoordinates().getxCoordinate()
                        + "x" + location.getCoordinates().getyCoordinate() + " "
                        + location.getStatisticOfLocation());
            } finally {
                location.getLock().unlock();
            }
        }
        System.out.println("-".repeat(200));
    }
}
