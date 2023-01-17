package ru.maslennikov.island.entities.animal.herbivorous;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;

@BasicCharacteristicsOrganism(icon = "🦆", name = "Duck", maxWeight = 1, maxTravelSpeed = 4, satiation = 0.15)
public class Duck extends Herbivorous {
    public Duck() {
        super();
    }
}
