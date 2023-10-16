package mainproject.musicforecast.domain.spotify;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SpotifyService {

    @Value("${spotify.client.id}")
    private String CLIENT_ID;
    @Value("${spotify.client.secret}")
    private String CLIENT_SECRET;

    public String accessToken() {
        SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(CLIENT_ID).setClientSecret(CLIENT_SECRET).build();
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            return spotifyApi.getAccessToken();
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return "error";
        }
    }

    public SpotifyApi setSpotifyApi() {
        return new SpotifyApi.Builder()
                .setAccessToken(accessToken())
                .build();
    }
}
