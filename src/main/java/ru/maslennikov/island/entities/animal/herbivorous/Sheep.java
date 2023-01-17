package ru.maslennikov.island.entities.animal.herbivorous;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;

@BasicCharacteristicsOrganism(icon = "🐑", name = "Sheep", maxWeight = 70, maxTravelSpeed = 3, satiation = 15)
public class Sheep extends Herbivorous {
    public Sheep() {
        super();
    }
}
