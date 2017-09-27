package com.molps.matchdetails;


import android.content.Context;
import android.util.SparseArray;

public final class ImageResources {

    private static final String LOG_TAG = ImageResources.class.getSimpleName();

    private static SparseArray<String> itemsMap;
    private static SparseArray<String> heroesMap;

    private ImageResources() {

    }

    public static String getItem(Context c, int id) {
        if (itemsMap == null) {
            itemsMap = new SparseArray<>();
            String[] array = c.getResources().getStringArray(R.array.odota_items);
            for (String x : array) {
                String[] splitter = x.split("#");
                itemsMap.put(Integer.valueOf(splitter[0]), splitter[1]);
            }
        }
        return itemsMap.get(id);
    }

    public static String getHero(Context c, int id) {
        if (heroesMap == null) {
            heroesMap = new SparseArray<>();
            String[] array = c.getResources().getStringArray(R.array.odota_heroes);
            for (String x : array) {
                String[] splitter = x.split("#");
                heroesMap.put(Integer.valueOf(splitter[0]), splitter[1]);
            }
        }
        return heroesMap.get(id);
    }

}
