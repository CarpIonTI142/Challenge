package md.carpion.challenge.data.db.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.RealmResults;
import md.carpion.challenge.ChallengeApp;
import md.carpion.challenge.data.db.model.Location;
import md.carpion.challenge.data.network.model.Item;
import md.carpion.challenge.data.network.model.LocationsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationRepositoryImpl implements LocationRepository {
    private static final String TAG = LocationRepositoryImpl.class.getName();

    @Override
    public void updateLocations(Runnable onSuccess, Runnable onError) {
        ChallengeApp.getApi().getLocations().enqueue(new Callback<LocationsResponse>() {
            @Override
            public void onResponse(@NonNull Call<LocationsResponse> call, @NonNull Response<LocationsResponse> response) {
                Log.d(TAG, "get locations success");

                ChallengeApp.getRealm().executeTransactionAsync(
                        r -> {
                            List<Location> locations = new ArrayList<>();

                            if (response.body() != null && response.body().getLocations() != null) {
                                for (Item item : response.body().getLocations()) {
                                    locations.add(new Location(item));
                                }

                                r.insertOrUpdate(locations);
                            } else {
                                onError.run();
                            }
                        },
                        () -> {
                            Log.d(TAG, "saved location success");
                            onSuccess.run();
                        },
                        e -> {
                            Log.d(TAG, "saved location fail");
                            onError.run();
                        }
                );
            }

            @Override
            public void onFailure(@NonNull Call<LocationsResponse> call, @NonNull Throwable t) {
                Log.d(TAG, "get locations fail");
                onError.run();
            }
        });
    }

    @Override
    public void insertOrUpdateLocation(Item item, Runnable onSuccess, Runnable onError) {
        ChallengeApp.getRealm().executeTransactionAsync(
                r -> r.insertOrUpdate(new Location(item)),
                onSuccess::run,
                e -> onError.run()
        );
    }

    @Override
    public RealmResults<Location> getLocations() {
        return ChallengeApp.getRealm().where(Location.class).findAll();
    }

    @Override
    public Location getLocationByLabel(String label) {
        return ChallengeApp.getRealm().where(Location.class).equalTo("label", label, Case.INSENSITIVE).findFirst();
    }
}
