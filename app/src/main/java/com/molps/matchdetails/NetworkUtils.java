package com.molps.matchdetails;


import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class NetworkUtils {

    private static final String BASE_URL_OPEN_DOTA = "https://api.opendota.com/api";
    private static final String PATH_MATCHES = "matches";
    private static OkHttpClient client;

    private NetworkUtils() {
    }

    public static List<List<PlayerStats>> fetchData(long matchId) {
        return filterJsonResponse(getJsonResponse(matchId));
    }


    private static String buildUrl(long matchId) {
        Uri uri = Uri.parse(BASE_URL_OPEN_DOTA).buildUpon()
                .appendPath(PATH_MATCHES)
                .appendPath(String.valueOf(matchId)).build();

        return uri.toString();
    }

    private static String getJsonResponse(long matchId) {
        if (client == null)
            client = new OkHttpClient();

        String url = buildUrl(matchId);
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    private static List<List<PlayerStats>> filterJsonResponse(String response) {
        List<List<PlayerStats>> list = new ArrayList<>();
        List<PlayerStats> basicData = new ArrayList<>();
        List<PlayerStats> detailData = new ArrayList<>();
        List<PlayerStats> nameData = new ArrayList<>();

        String name;
        boolean radiantWin;
        long duration;
        int radiantScore;
        int direScore;

        long heroDamage;
        long buildingDamage;
        int lastHits;
        int denies;
        try {
            JSONObject jsonObject = new JSONObject(response);

            JSONArray jsonArray = jsonObject.getJSONArray("players");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject player = jsonArray.getJSONObject(i);
                name = player.optString("personaname");
                heroDamage = player.getLong("hero_damage");
                buildingDamage = player.getLong("tower_damage");
                lastHits = player.getInt("last_hits");
                denies = player.getInt("denies");
                nameData.add(new PlayerName(name));
                detailData.add(new PlayerDetail(
                        heroDamage,
                        buildingDamage,
                        lastHits,
                        denies,
                        player.getInt("gold_per_min"),
                        player.getInt("xp_per_min"),
                        player.optInt("purchase_ward_observer"),
                        player.optInt("purchase_ward_sentry"),
                        player.getInt("backpack_0"),
                        player.getInt("backpack_1"),
                        player.getInt("backpack_2")
                ));

                basicData.add(
                        new PlayerBasic(
                                player.optString("personaname"),
                                player.getInt("hero_id"),
                                player.getInt("level"),
                                player.getInt("kills"),
                                player.getInt("deaths"),
                                player.getInt("assists"),
                                player.getInt("item_0"),
                                player.getInt("item_1"),
                                player.getInt("item_2"),
                                player.getInt("item_3"),
                                player.getInt("item_4"),
                                player.getInt("item_5")
                        ));
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }

        list.add(basicData);
        list.add(nameData);
        list.add(detailData);
        return list;
    }

}
