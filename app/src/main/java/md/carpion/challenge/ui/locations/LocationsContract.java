package md.carpion.challenge.ui.locations;

import androidx.annotation.NonNull;

import java.util.List;

import md.carpion.challenge.data.db.model.Location;
import md.carpion.challenge.ui.BaseActivity;

public interface LocationsContract {
    interface View {
        void showLocations(List<Location> locations);

        void permissionsRequest(String[] permissions, int requestCode);

        void setDistance(android.location.Location location);
    }

    interface Presenter {
        void attachView(View view, BaseActivity activity);

        void onClickDetails(String label);

        void onClickAdd();

        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
    }
}
