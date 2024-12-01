
import org.therapist.bot.api.APIClient;

public class APIClientTest {
    public static void main(String[] args) {
        APIClient apiClient = new APIClient();
        try {
            String response = apiClient.fetchResponse("EN", "HAPPY");
            System.out.println("Generated Response: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
