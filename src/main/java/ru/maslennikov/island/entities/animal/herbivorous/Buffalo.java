package ru.maslennikov.island.entities.animal.herbivorous;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;

@BasicCharacteristicsOrganism(name = "Buffalo", maxWeight = 700, maxTravelSpeed = 3, requiredAmountOfFoodToSatisfy = 100)
public class Buffalo extends Herbivorous {
    public Buffalo() {
        super();
    }
}
