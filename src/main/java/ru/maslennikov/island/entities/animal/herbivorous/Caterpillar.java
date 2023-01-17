package ru.maslennikov.island.entities.animal.herbivorous;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;

@BasicCharacteristicsOrganism(icon = "🐛", name = "Caterpillar", maxWeight = 0.01, maxTravelSpeed = 0, satiation = 0)
public class Caterpillar extends Herbivorous {
    public Caterpillar() {
        super();
    }
}
