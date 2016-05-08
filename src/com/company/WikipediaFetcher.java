package com.company;

import org.json.JSONObject;

/**
 * Created by Nikola on 5/8/2016.
 */
public class WikipediaFetcher {

    private String characterLimit = "550";
    private String base = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&explaintext&exchars="+characterLimit+"&titles=";
    private String title;
    private String finalSummary;

    public WikipediaFetcher(){};
    public String getSummary(String title)
    {
        this.title = title;
        finalSummary = extractSummary(getJsonResponse());
        addPCVersion();
        return finalSummary;
    }

    private String extractSummary(String response)
    {
        String summary = "";
        try{
            JSONObject jsonObject = new JSONObject(response);
            jsonObject = jsonObject.getJSONObject("query").getJSONObject("pages");
            String id = jsonObject.names().getString(0);
            summary = jsonObject.getJSONObject(id).getString("extract");
        }catch (Exception e){e.printStackTrace();}

        return summary;
    }
    private String getJsonResponse()
    {
        ApiConnection connection = new ApiConnection();
        connection.setQuery(base + title);
        return connection.getResponse();
    }
    private void addPCVersion()
    {
        String temp = finalSummary.toLowerCase();

        String[] variations = {"pc","windows"};
        for(String variation : variations)
            if(temp.contains(variation))
                return;

        int lowestIndex = temp.length();
        boolean found = false;
        String[] consoleVariations = {"playstation", "xbox", "nintendo", " wii "};
        for(String variation: consoleVariations)
        {
            int index = temp.indexOf(variation);
            if(index != -1 && index < lowestIndex)
            {
                lowestIndex = index;
                found = true;
            }
        }

        if(found)
        {
            finalSummary = new StringBuilder(finalSummary).insert(lowestIndex, "Windows (PC), ").toString();
        }

    }

}
