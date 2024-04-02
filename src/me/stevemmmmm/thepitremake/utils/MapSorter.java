package me.stevemmmmm.thepitremake.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class MapSorter {
    public MapSorter() {
    }

    public static HashMap<UUID, Integer> sortByValue(HashMap<UUID, Integer> hm) {
        return hm.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }
}

