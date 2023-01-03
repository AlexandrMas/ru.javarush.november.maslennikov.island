package ru.maslennikov.island.entities.animal.herbivorous;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;

@BasicCharacteristicsOrganism(name = "Rabbit", maxWeight = 2, maxTravelSpeed = 2, satiation = 0.45)
public class Rabbit extends Herbivorous {
    public Rabbit() {
        super();
    }
}
