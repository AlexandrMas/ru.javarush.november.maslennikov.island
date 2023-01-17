package ru.maslennikov.island.entities.animal.predator;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;

@BasicCharacteristicsOrganism(icon = "🐍", name = "Boa", maxWeight = 15, maxTravelSpeed = 1, satiation = 3)
public class Boa extends Predator {
    public Boa() {
        super();
    }
}
