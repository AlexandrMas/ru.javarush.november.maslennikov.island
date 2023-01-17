package ru.maslennikov.island.maker.OrganismFactory;

import ru.maslennikov.island.entities.plant.Plant;

public class PlantFactory implements OrganismFactory {
    @Override
    public Plant createOrganism(String name) {
        if (name.equalsIgnoreCase("plant")) {
            return new Plant();
        } else {
            throw new RuntimeException("this plant is absent in the habitat of the island");
        }
    }
}
