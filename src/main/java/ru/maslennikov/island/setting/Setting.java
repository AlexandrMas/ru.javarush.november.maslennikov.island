package ru.maslennikov.island.setting;


import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

public class Setting {

    private static final String SETTING_FILE = "settingFile.json";

    private static volatile Setting settings;

    private int maxWidthOfIsland;

    private int maxHeightOfIsland;

    private int minNumberOrganismOfLocation;

    private Map<String, Integer> maxNumberOfOffspring;

    private Map<String, Map<String, Integer>> probabilityOfEatingOrganism;

    private Map<String, Integer> maxNumberOfOrganismsOfThisTypeInLocation;

    private Setting() {
        try {
            URL resource = Setting.class.getClassLoader().getResource(SETTING_FILE);
            ObjectReader objectReader = new JsonMapper().readerForUpdating(this);
            if (Objects.nonNull(resource)) {
                objectReader.readValue(resource.openStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Map<String, Integer>> getProbabilityOfEatingOrganism() {
        return probabilityOfEatingOrganism;
    }

    public int getMinNumberOrganismOfLocation() {
        return minNumberOrganismOfLocation;
    }

    public Map<String, Integer> getMaxNumberOfOffspring() {
        return maxNumberOfOffspring;
    }

    public int getMaxWidthOfIsland() {
        return maxWidthOfIsland;
    }

    public int getMaxHeightOfIsland() {
        return maxHeightOfIsland;
    }

    public Map<String, Integer> getMaxNumberOfOrganismsOfThisTypeInLocation() {
        return maxNumberOfOrganismsOfThisTypeInLocation;
    }

    public static Setting get() {
        if (Objects.isNull(settings)) {
            synchronized (Setting.class) {
                if (Objects.isNull(settings)) {
                    settings = new Setting();
                }
            }
        }
        return settings;
    }
}
