import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherApp {
    public static void main(String[] args) {
        String apiKey = "YOUR_API_KEY"; // Replace with your OpenWeatherMap API key
        String city = "London"; // Change to any city
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";

        try {
            // Create URL object
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Check response code
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            // Read API response
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output, response = "";
            while ((output = br.readLine()) != null) {
                response += output;
            }
            conn.disconnect();

            // Parse JSON response
            JSONObject json = new JSONObject(response);
            JSONObject main = json.getJSONObject("main");
            double temp = main.getDouble("temp");
            double feelsLike = main.getDouble("feels_like");
            int humidity = main.getInt("humidity");
            String weather = json.getJSONArray("weather").getJSONObject(0).getString("description");

            // Display structured output
            System.out.println("Weather in " + city + ":");
            System.out.println("-----------------------------");
            System.out.println("Temperature    : " + temp + "°C");
            System.out.println("Feels Like     : " + feelsLike + "°C");
            System.out.println("Humidity       : " + humidity + "%");
            System.out.println("Condition      : " + weather);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
