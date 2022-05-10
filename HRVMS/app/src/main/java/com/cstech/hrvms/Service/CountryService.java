package com.cstech.hrvms.Service;

public class CountryService {

    private static final String URL = "http://ip-api.com/";

    private retrofit.RestAdapter restAdapter;
    private WebServiceAPI apiService;

    public CountryService()
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
