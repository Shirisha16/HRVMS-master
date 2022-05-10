package com.cstech.hrvms.Service;

public class RestService {

    private static final String URL = "https://testingwebservices.azurewebsites.net//api/HRVMS/";//testing Envirnoment
    //private static final String URL = "http://hrvmswebapi.azurewebsites.net/api/HRVMS/";//production Envirnoment
    private retrofit.RestAdapter restAdapter;
    private WebServiceAPI apiService;

    public RestService()
    {
        restAdapter = new retrofit.RestAdapter.Builder()
                .setEndpoint(URL)
                .setLogLevel(retrofit.RestAdapter.LogLevel.FULL)
                .build();

        apiService = restAdapter.create(WebServiceAPI.class);
    }

    public WebServiceAPI getService()
    {
        return apiService;
    }
}