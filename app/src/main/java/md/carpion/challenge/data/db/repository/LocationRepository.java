package md.carpion.challenge.data.db.repository;

import io.realm.RealmResults;
import md.carpion.challenge.data.db.model.Location;
import md.carpion.challenge.data.network.model.Item;

public interface LocationRepository {
    void updateLocations(Runnable onSuccess, Runnable onError);

    void insertOrUpdateLocation(Item item, Runnable onSuccess, Runnable onError);

    RealmResults<Location> getLocations();

    Location getLocationByLabel(String label);
}
