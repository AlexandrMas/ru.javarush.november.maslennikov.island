package ru.maslennikov.island.island;

import ru.maslennikov.island.maker.CreatorOfIsland;

public class Island {

    private Location[][] inhabitedIsland;

    public Location[][] getInhabitedIsland() {
        return inhabitedIsland;
    }

    public Island() {
        CreatorOfIsland creatorOfIsland = new CreatorOfIsland();
        inhabitedIsland = creatorOfIsland.creatIsland();
    }
}
