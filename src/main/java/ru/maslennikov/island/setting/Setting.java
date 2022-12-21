package ru.maslennikov.island.setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Setting {

    private final List<String> listOfObjectsOfIsland = new ArrayList<>();

    {
        listOfObjectsOfIsland.add("wolf");
//        listOfObjectsOfIsland.add("boa");
//        listOfObjectsOfIsland.add("fox");
//        listOfObjectsOfIsland.add("bear");
//        listOfObjectsOfIsland.add("eagle");
        listOfObjectsOfIsland.add("hours");
//        listOfObjectsOfIsland.add("deer");
//        listOfObjectsOfIsland.add("rabbit");
//        listOfObjectsOfIsland.add("mouse");
//        listOfObjectsOfIsland.add("goat");
//        listOfObjectsOfIsland.add("boar");
//        listOfObjectsOfIsland.add("buffalo");
        listOfObjectsOfIsland.add("duck");
        listOfObjectsOfIsland.add("caterpillar");
        listOfObjectsOfIsland.add("grass");
    }

    private final Map<String, Map<String, Integer>> probabilityOfEatingIslandObject = new HashMap<>();

    {
        Map<String, Integer> wolfEatsAnotherAnimalWithProbability = new HashMap<>();
        wolfEatsAnotherAnimalWithProbability.put("wolf", 0);
        wolfEatsAnotherAnimalWithProbability.put("boa", 0);
        wolfEatsAnotherAnimalWithProbability.put("fox", 0);
        wolfEatsAnotherAnimalWithProbability.put("bear", 0);
        wolfEatsAnotherAnimalWithProbability.put("eagle", 0);
        wolfEatsAnotherAnimalWithProbability.put("hours", 10);
        wolfEatsAnotherAnimalWithProbability.put("deer", 15);
        wolfEatsAnotherAnimalWithProbability.put("rabbit", 60);
        wolfEatsAnotherAnimalWithProbability.put("mouse", 80);
        wolfEatsAnotherAnimalWithProbability.put("goat", 60);
        wolfEatsAnotherAnimalWithProbability.put("boar", 15);
        wolfEatsAnotherAnimalWithProbability.put("buffalo", 10);
        wolfEatsAnotherAnimalWithProbability.put("duck", 40);
        wolfEatsAnotherAnimalWithProbability.put("caterpillar", 0);
        wolfEatsAnotherAnimalWithProbability.put("grass", 0);
        probabilityOfEatingIslandObject.put("wolf", wolfEatsAnotherAnimalWithProbability);

        Map<String, Integer> hoursEatsAnotherAnimalWithProbability = new HashMap<>();
        hoursEatsAnotherAnimalWithProbability.put("boa", 0);
        hoursEatsAnotherAnimalWithProbability.put("fox", 0);
        hoursEatsAnotherAnimalWithProbability.put("bear", 0);
        hoursEatsAnotherAnimalWithProbability.put("eagle", 0);
        hoursEatsAnotherAnimalWithProbability.put("hours", 0);
        hoursEatsAnotherAnimalWithProbability.put("deer", 0);
        hoursEatsAnotherAnimalWithProbability.put("rabbit", 0);
        hoursEatsAnotherAnimalWithProbability.put("mouse", 0);
        hoursEatsAnotherAnimalWithProbability.put("goat", 0);
        hoursEatsAnotherAnimalWithProbability.put("boar", 0);
        hoursEatsAnotherAnimalWithProbability.put("buffalo", 0);
        hoursEatsAnotherAnimalWithProbability.put("duck", 0);
        hoursEatsAnotherAnimalWithProbability.put("caterpillar", 0);
        hoursEatsAnotherAnimalWithProbability.put("grass", 100);
        probabilityOfEatingIslandObject.put("hours", hoursEatsAnotherAnimalWithProbability);

        Map<String, Integer> duckEatsAnotherAnimalWithProbability = new HashMap<>();
        duckEatsAnotherAnimalWithProbability.put("boa", 0);
        duckEatsAnotherAnimalWithProbability.put("fox", 0);
        duckEatsAnotherAnimalWithProbability.put("bear", 0);
        duckEatsAnotherAnimalWithProbability.put("eagle", 0);
        duckEatsAnotherAnimalWithProbability.put("hours", 0);
        duckEatsAnotherAnimalWithProbability.put("deer", 0);
        duckEatsAnotherAnimalWithProbability.put("rabbit", 0);
        duckEatsAnotherAnimalWithProbability.put("mouse", 0);
        duckEatsAnotherAnimalWithProbability.put("goat", 0);
        duckEatsAnotherAnimalWithProbability.put("boar", 0);
        duckEatsAnotherAnimalWithProbability.put("buffalo", 0);
        duckEatsAnotherAnimalWithProbability.put("duck", 0);
        duckEatsAnotherAnimalWithProbability.put("caterpillar", 90);
        duckEatsAnotherAnimalWithProbability.put("grass", 100);
        probabilityOfEatingIslandObject.put("duck", duckEatsAnotherAnimalWithProbability);

        Map<String, Integer> caterpillarEatsAnotherAnimalWithProbability = new HashMap<>();
        caterpillarEatsAnotherAnimalWithProbability.put("boa", 0);
        caterpillarEatsAnotherAnimalWithProbability.put("fox", 0);
        caterpillarEatsAnotherAnimalWithProbability.put("bear", 0);
        caterpillarEatsAnotherAnimalWithProbability.put("eagle", 0);
        caterpillarEatsAnotherAnimalWithProbability.put("hours", 0);
        caterpillarEatsAnotherAnimalWithProbability.put("deer", 0);
        caterpillarEatsAnotherAnimalWithProbability.put("rabbit", 0);
        caterpillarEatsAnotherAnimalWithProbability.put("mouse", 0);
        caterpillarEatsAnotherAnimalWithProbability.put("goat", 0);
        caterpillarEatsAnotherAnimalWithProbability.put("boar", 0);
        caterpillarEatsAnotherAnimalWithProbability.put("buffalo", 0);
        caterpillarEatsAnotherAnimalWithProbability.put("duck", 0);
        caterpillarEatsAnotherAnimalWithProbability.put("caterpillar", 0);
        caterpillarEatsAnotherAnimalWithProbability.put("grass", 100);
        probabilityOfEatingIslandObject.put("caterpillar", caterpillarEatsAnotherAnimalWithProbability);
    }
}
