package org.tn.esprit.service.impl;


import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.tn.esprit.client.PhotosRestClient;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class UnsplashPhotoService {


    @Inject
    @RestClient
    PhotosRestClient photosRestClient;


    public List<String> getPhotos(String word) {
        String key = "KX7qfI8aVANHS4XzvjtroESUV3-BLa8ccimzJ3MeIic";
        String json = photosRestClient.getImagesUrl(word, key);
        List<String> list = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray results = jsonObject.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
           JSONObject urls = results.getJSONObject(i).getJSONObject("urls");
           String url = urls.getString("small");
           list.add(url);
        }
        return  list;
    }
}
