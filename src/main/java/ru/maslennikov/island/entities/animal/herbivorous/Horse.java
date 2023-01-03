package ru.maslennikov.island.entities.animal.herbivorous;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;

@BasicCharacteristicsOrganism(name = "Horse", maxWeight = 400, maxTravelSpeed = 4, satiation = 60)
public class Horse extends Herbivorous {
    public Horse() {
        super();
    }
}
