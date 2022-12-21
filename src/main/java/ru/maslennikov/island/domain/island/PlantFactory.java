package ru.maslennikov.island.domain.island;

import ru.maslennikov.island.domain.plant.Grass;
import ru.maslennikov.island.domain.plant.Plant;
import ru.maslennikov.island.utilite.ialand_object_factory.IslandObjectFactory;

public class PlantFactory implements IslandObjectFactory {
    @Override
    public Plant createIslandObject(String name) {
        if (name.equalsIgnoreCase("grass")) {
            return new Grass();
        } else {
            throw new RuntimeException("this plant is absent in the habitat of the island");
        }
    }
}
