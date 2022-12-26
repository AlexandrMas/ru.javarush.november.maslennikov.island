package ru.maslennikov.island.entities.animal.herbivorous;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;

@BasicCharacteristicsOrganism(name = "Caterpillar", maxWeight = 0.01, maxTravelSpeed = 0, requiredAmountOfFoodToSatisfy = 0)
public class Caterpillar extends Herbivorous {
    public Caterpillar() {
        super();
    }
}
