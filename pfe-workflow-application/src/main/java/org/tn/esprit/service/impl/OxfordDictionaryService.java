package org.tn.esprit.service.impl;


import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tn.esprit.client.OxfordDictionaryRestClient;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class OxfordDictionaryService {


    @Inject
    @RestClient
    OxfordDictionaryRestClient oxfordDictionaryRestClient;

    public List<String> getWordSynonyme(String word) {
        String key = "99d3d210-f078-4d2a-bcab-771d00564bd6";
        String json = oxfordDictionaryRestClient.getSynonymeWord(word, key);

        List<String> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONArray(json).getJSONObject(0);
            JSONArray syns = jsonObject.getJSONObject("meta").getJSONArray("syns");
            System.out.println(syns);
            for (int i = 0; i < syns.length(); i++) {
                System.out.println("after" + syns.getJSONArray(i));
                for (int j = 0; j < syns.getJSONArray(i).length(); j++) {
                    list.add(syns.getJSONArray(i).getString(j));
                }
            }

        } catch (JSONException e) {
            JSONArray jsonObject = new JSONArray(json);
            for (int i = 0; i < jsonObject.length(); i++) {
                list.add(jsonObject.getString(i));
            }
        }
        return list;

    }
}
