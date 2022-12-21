package ru.maslennikov.island.utilite.ialand_object_factory;

import ru.maslennikov.island.domain.animal.herbivorous.Caterpillar;
import ru.maslennikov.island.domain.animal.herbivorous.Duck;
import ru.maslennikov.island.domain.animal.herbivorous.Herbivorous;
import ru.maslennikov.island.domain.animal.herbivorous.Hours;

public class HerbivorousFactory implements IslandObjectFactory {
    @Override
    public Herbivorous createIslandObject(String name) {
        if (name.equalsIgnoreCase("hours")) {
            return new Hours();
        } else if (name.equalsIgnoreCase("duck")) {
            return new Duck();
        } else if (name.equalsIgnoreCase("caterpillar")) {
            return new Caterpillar();
        } else {
            throw new RuntimeException("this herbivorous is absent in the habitat of the island");
        }
    }
}
