package ru.maslennikov.island.maker.OrganismFactory;

import ru.maslennikov.island.entities.animal.herbivorous.Boar;
import ru.maslennikov.island.entities.animal.herbivorous.Buffalo;
import ru.maslennikov.island.entities.animal.herbivorous.Caterpillar;
import ru.maslennikov.island.entities.animal.herbivorous.Deer;
import ru.maslennikov.island.entities.animal.herbivorous.Duck;
import ru.maslennikov.island.entities.animal.herbivorous.Goat;
import ru.maslennikov.island.entities.animal.herbivorous.Herbivorous;
import ru.maslennikov.island.entities.animal.herbivorous.Horse;
import ru.maslennikov.island.entities.animal.herbivorous.Mouse;
import ru.maslennikov.island.entities.animal.herbivorous.Rabbit;
import ru.maslennikov.island.entities.animal.herbivorous.Sheep;

public class HerbivorousFactory implements OrganismFactory {
    @Override
    public Herbivorous createOrganism(String name) {
        if (name.equalsIgnoreCase("horse")) {
            return new Horse();
        } else if (name.equalsIgnoreCase("deer")) {
            return new Deer();
        } else if (name.equalsIgnoreCase("rabbit")) {
            return new Rabbit();
        } else if (name.equalsIgnoreCase("mouse")) {
            return new Mouse();
        } else if (name.equalsIgnoreCase("goat")) {
            return new Goat();
        } else if (name.equalsIgnoreCase("sheep")) {
            return new Sheep();
        } else if (name.equalsIgnoreCase("boar")) {
            return new Boar();
        } else if (name.equalsIgnoreCase("buffalo")) {
            return new Buffalo();
        } else if (name.equalsIgnoreCase("duck")) {
            return new Duck();
        } else if (name.equalsIgnoreCase("caterpillar")) {
            return new Caterpillar();
        } else {
            throw new RuntimeException("this herbivorous is absent in the habitat of the island");
        }
    }
}
