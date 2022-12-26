package ru.maslennikov.island.entities.animal.herbivorous;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;

@BasicCharacteristicsOrganism(name = "Deer", maxWeight = 300, maxTravelSpeed = 4, requiredAmountOfFoodToSatisfy = 50)
public class Deer extends Herbivorous {
    public Deer() {
        super();
    }
}
