package ru.maslennikov.island.entities.animal.herbivorous;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;

@BasicCharacteristicsOrganism(icon = "🐐", name = "Goat", maxWeight = 60, maxTravelSpeed = 3, satiation = 10)
public class Goat extends Herbivorous {
    public Goat() {
        super();
    }
}
