package ru.maslennikov.island.entities.animal.herbivorous;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;

@BasicCharacteristicsOrganism(icon = "🐃", name = "Buffalo", maxWeight = 700, maxTravelSpeed = 3, satiation = 100)
public class Buffalo extends Herbivorous {
    public Buffalo() {
        super();
    }
}
