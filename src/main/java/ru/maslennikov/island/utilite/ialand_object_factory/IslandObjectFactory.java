package ru.maslennikov.island.utilite.ialand_object_factory;

import ru.maslennikov.island.domain.island.IslandObject;

public interface IslandObjectFactory {
    IslandObject createIslandObject(String name);
}
