package com.example.BK_SB_project;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

public class JSONObject {

    private class Location {
        private float latitude;
        private float longitude;

        public Location(float longitude, float latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }
    }

    private final String _type = "Position";
    private int _id;
    private final String key = null;
    private String name;
    private String fullName;
    private final String iata_airport_code = null;
    private final String type = "location";
    private String country;
    private Location geo_position;
    private int location_id;
    private boolean inEurope;
    private String countryCode;
    private boolean coreCountry;
    private final String distance = null;

    public int get_id() {
        return _id;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void set_id(int id) {
        _id = id;
    }

    public void setLocation_id(int locationId) {
        location_id = locationId;
    }

    public float getLongitude() {
        return geo_position.longitude;
    }

    public float getLatitude() {
        return geo_position.latitude;
    }

    private void determineCountryAndCode(float latitude, float longitude) {
        String tCountry = "Unknown";
        String tCountryCode = "Unknown";
        String continent = "Unknown";
        String username = "bartekklimiuk";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String url = String.format(
                    "http://api.geonames.org/countryCodeJSON?lat=%f&lng=%f&username=%s",
                    latitude, longitude, username
            );
            HttpGet request = new HttpGet(url);
            request.addHeader("User-Agent", "Mozilla/5.0");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String json = EntityUtils.toString(response.getEntity());
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(json);
                tCountry = rootNode.path("countryName").asText("Unknown");
                tCountryCode = rootNode.path("countryCode").asText("Unknown");
                continent = rootNode.path("continent").asText("Unknown");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        country = tCountry;
        countryCode = tCountryCode;
        inEurope = "EU".equals(continent);
    }

    public JSONObject() {
        Random rand = new Random();
        Faker faker = new Faker();
        float lon, lat;

        do {
            lon = BigDecimal.valueOf(rand.nextFloat(-180, 180)).
                    setScale(6, RoundingMode.HALF_UP).floatValue();

            lat = BigDecimal.valueOf(rand.nextFloat(-90, 90)).
                    setScale(6, RoundingMode.HALF_UP).floatValue();

            determineCountryAndCode(lat, lon);
        } while(this.country == null || this.country.equals("Unknown"));

        this.geo_position = new Location(lon, lat);

        this.coreCountry = this.countryCode.equals("PL");

        this.name = faker.name().lastName();

        this.fullName = this.name + ", " + this.country;
    }
}
