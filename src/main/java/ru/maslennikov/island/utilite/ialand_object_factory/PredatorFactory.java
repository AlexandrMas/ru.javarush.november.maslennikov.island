package ru.maslennikov.island.utilite.ialand_object_factory;

import ru.maslennikov.island.domain.animal.predator.Predator;
import ru.maslennikov.island.domain.animal.predator.Wolf;

public class PredatorFactory implements IslandObjectFactory {
    @Override
    public Predator createIslandObject(String name) {
        if (name.equalsIgnoreCase("wolf")) {
            return new Wolf();
        } else {
            throw new RuntimeException("this predator is absent in the habitat of the island");
        }
    }
}
