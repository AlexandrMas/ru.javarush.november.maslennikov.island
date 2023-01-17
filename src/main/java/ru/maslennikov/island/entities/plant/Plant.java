package ru.maslennikov.island.entities.plant;

import ru.maslennikov.island.annotation.BasicCharacteristicsOrganism;
import ru.maslennikov.island.entities.Organism;

@BasicCharacteristicsOrganism(icon = "🌿", name = "Plant", maxWeight = 1, maxTravelSpeed = 0, satiation = 0)
public class Plant implements Organism {
    public Plant() {
        super();
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    private double weight = this.getClass().getAnnotation(BasicCharacteristicsOrganism.class).maxWeight();

    public String getName() {
        return this.getClass().getAnnotation(BasicCharacteristicsOrganism.class).name();
    }

    @Override
    public String getIcon() {
        return this.getClass().getAnnotation(BasicCharacteristicsOrganism.class).icon();
    }


    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "weight=" + weight +
                '}';
    }
}
