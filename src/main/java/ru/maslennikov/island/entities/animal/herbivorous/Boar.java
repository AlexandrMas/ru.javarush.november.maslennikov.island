package ru.maslennikov.island.entities.animal.herbivorous;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;

@BasicCharacteristicsOrganism(icon = "🐗", name = "Boar", maxWeight = 400, maxTravelSpeed = 2, satiation = 50)
public class Boar extends Herbivorous {
    public Boar() {
        super();
    }
}
