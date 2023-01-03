package ru.maslennikov.island.entities.animal.herbivorous;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;

@BasicCharacteristicsOrganism(name = "Mouse", maxWeight = 0.05, maxTravelSpeed = 1, satiation = 0.01)
public class Mouse extends Herbivorous {
    public Mouse() {
        super();
    }
}
