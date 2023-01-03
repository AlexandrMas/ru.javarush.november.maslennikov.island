package ru.maslennikov.island.entities.animal.predator;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;

@BasicCharacteristicsOrganism(name = "Bear", maxWeight = 500, maxTravelSpeed = 2, satiation = 80)
public class Bear extends Predator {
    public Bear() {
        super();
    }
}
