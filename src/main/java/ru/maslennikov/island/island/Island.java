package ru.maslennikov.island.island;

import ru.maslennikov.island.maker.CreatorOfIsland;
import ru.maslennikov.island.maker.OrganismFactory.HerbivorousFactory;
import ru.maslennikov.island.maker.OrganismFactory.PlantFactory;
import ru.maslennikov.island.maker.OrganismFactory.PredatorFactory;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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
