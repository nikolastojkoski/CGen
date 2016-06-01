package com.company;

import org.json.JSONObject;

/**
 * Created by Nikola on 5/8/2016.
 */
public class WikipediaFetcher {

    private String characterLimit = "400";
    private String base = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&explaintext&exchars="+characterLimit+"&titles=";
    private String title;
    private String finalSummary;

    public WikipediaFetcher(){};
    public void fetchSummary(String link)
    {
        this.title = link.substring(link.indexOf("wiki/") + 5);
        finalSummary = extractSummary(getJsonResponse());
        selectSummary();
        refine();
        addPCVersion();
    }
    public String getSummary()
    {
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
    private void selectSummary()
    {
        if(finalSummary.contains("\n"))
            finalSummary = finalSummary.substring(0,finalSummary.indexOf("\n"));
        else if(finalSummary.contains("\r"))
            finalSummary = finalSummary.substring(0,finalSummary.indexOf("\r"));
    }
    private void addPCVersion()
    {
        String temp = finalSummary.toLowerCase();

        String[] variations = {" pc","windows"};
        for(String variation : variations)
            if(temp.contains(variation))
                return;

        int lowestIndex = temp.length();
        boolean found = false;
        String[] consoleVariations = {"play station","playstation", "xbox", "nintendo", " wii"," ios ","android"};
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
    private void refine()
    {
        if(!finalSummary.endsWith("."))
            finalSummary += "...";
    }

}
