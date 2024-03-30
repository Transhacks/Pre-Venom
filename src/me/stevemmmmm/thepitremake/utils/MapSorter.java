//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

public class MapSorter {
    public MapSorter() {
    }

    public static HashMap<UUID, Integer> sortByValue(HashMap<UUID, Integer> hm) {
        List<Map.Entry<UUID, Integer>> list = new LinkedList(hm.entrySet());
        list.sort(Entry.comparingByValue());
        HashMap<UUID, Integer> temp = new LinkedHashMap();
        Iterator var3 = list.iterator();

        while(var3.hasNext()) {
            Map.Entry<UUID, Integer> aa = (Map.Entry)var3.next();
            temp.put(aa.getKey(), aa.getValue());
        }

        return temp;
    }
}
