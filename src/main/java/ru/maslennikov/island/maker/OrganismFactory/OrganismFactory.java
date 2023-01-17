package ru.maslennikov.island.maker.OrganismFactory;

import ru.maslennikov.island.entities.Organism;

public interface OrganismFactory {
    Organism createOrganism(String name);
}
