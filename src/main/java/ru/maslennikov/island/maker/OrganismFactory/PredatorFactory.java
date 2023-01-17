package ru.maslennikov.island.maker.OrganismFactory;

import ru.maslennikov.island.entities.animal.herbivorous.Rabbit;
import ru.maslennikov.island.entities.animal.predator.Bear;
import ru.maslennikov.island.entities.animal.predator.Boa;
import ru.maslennikov.island.entities.animal.predator.Eagle;
import ru.maslennikov.island.entities.animal.predator.Fox;
import ru.maslennikov.island.entities.animal.predator.Predator;
import ru.maslennikov.island.entities.animal.predator.Wolf;

public class PredatorFactory implements OrganismFactory {
    @Override
    public Predator createOrganism(String name) {
        if (name.equalsIgnoreCase("wolf")) {
            return new Wolf();
        } else if (name.equalsIgnoreCase("boa")) {
            return new Boa();
        } else if (name.equalsIgnoreCase("fox")) {
            return new Fox();
        } else if (name.equalsIgnoreCase("bear")) {
            return new Bear();
        } else if (name.equalsIgnoreCase("eagle")) {
            return new Eagle();
        } else {
            throw new RuntimeException("this predator is absent in the habitat of the island");
        }
    }
}
