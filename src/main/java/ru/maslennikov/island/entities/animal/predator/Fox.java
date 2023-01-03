package ru.maslennikov.island.entities.animal.predator;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;

@BasicCharacteristicsOrganism(name = "Fox", maxWeight = 8, maxTravelSpeed = 2, satiation = 2)
public class Fox extends Predator {
    public Fox() {
        super();
    }
}
