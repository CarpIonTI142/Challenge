package md.carpion.challenge.data.network.repository;

import md.carpion.challenge.data.network.model.LocationsResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ChallengeServices {
    @GET("locations")
    Call<LocationsResponse> getLocations();
}
