import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

/**
 * This Java application fetches weather data from a public REST API
 * and displays it in a structured format.
 * Created as part of an internship project at CODTECH.
 */
public class WeatherApp {
    public static void main(String[] args) {
        String apiKey = "your_api_key_here"; // Replace with your API key
        String city = "Mumbai"; // Change the city as needed
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
        
        // Fetch and display weather data
        fetchWeatherData(apiUrl);
    }

    /**
     * Fetches weather data from the API and prints it in a readable format.
     */
    public static void fetchWeatherData(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                String inline = "";
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }
                scanner.close();
                
                JSONObject data = new JSONObject(inline);
                displayWeatherInfo(data);
            } else {
                System.out.println("Oops! Couldn't fetch weather data. HTTP Response Code: " + responseCode);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong while fetching data: " + e.getMessage());
        }
    }

    /**
     * Parses and displays weather information in a user-friendly format.
     */
    public static void displayWeatherInfo(JSONObject data) {
        String cityName = data.getString("name");
        JSONObject main = data.getJSONObject("main");
        double temperature = main.getDouble("temp");
        int humidity = main.getInt("humidity");
        
        System.out.println("\nWeather Report for " + cityName + ":");
        System.out.println("Temperature: " + temperature + "°C");
        System.out.println("Humidity: " + humidity + "%");
    }
          }
