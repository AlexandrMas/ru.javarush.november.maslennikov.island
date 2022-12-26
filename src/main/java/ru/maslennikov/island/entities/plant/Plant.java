package ru.maslennikov.island.entities.plant;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;
import ru.maslennikov.island.entities.Organism;
@BasicCharacteristicsOrganism(name = "Plant", maxWeight = 1, maxTravelSpeed = 0, requiredAmountOfFoodToSatisfy = 0)
public class Plant implements Organism {
    public Plant() {
        super();
    }
}
