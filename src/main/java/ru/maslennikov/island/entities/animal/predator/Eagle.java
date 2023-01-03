package ru.maslennikov.island.entities.animal.predator;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;

@BasicCharacteristicsOrganism(name = "Eagle", maxWeight = 6, maxTravelSpeed = 3, satiation = 1)
public class Eagle extends Predator {
    public Eagle() {
        super();
    }
}
