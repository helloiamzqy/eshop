package com.item.eshop.util.payment;

import java.util.*;
import java.util.Map.Entry;

public class MapUtils {

    /**
     * 对map根据key进行排序 ASCII 顺序
     *
     * @param map
     * @return
     */
    public static SortedMap<String, Object> sortMap(Map<String, Object> map) {

        List<Entry<String, Object>> infoIds = new ArrayList<Entry<String, Object>>(
                map.entrySet());
        Collections.sort(infoIds, new Comparator<Entry<String, Object>>() {
            public int compare(Entry<String, Object> o1,
                               Entry<String, Object> o2) {
                // return (o2.getValue() - o1.getValue());//value处理
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });
        SortedMap<String, Object> sortmap = new TreeMap<String, Object>();
        for (int i = 0; i < infoIds.size(); i++) {
            String[] split = infoIds.get(i).toString().split("=");
            sortmap.put(split[0], split[1]);
        }
        return sortmap;
    }
}