package md.carpion.challenge.ui.locations;

import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import md.carpion.challenge.data.db.repository.LocationRepository;
import md.carpion.challenge.data.db.repository.LocationRepositoryImpl;
import md.carpion.challenge.ui.BaseActivity;
import md.carpion.challenge.utils.GpsTracker;

public class LocationsPresenter implements LocationsContract.Presenter {
    private static final String TAG = LocationsPresenter.class.getName();
    private static final int REQUEST_CHECK_SETTINGS = 100;
    private static final String[] PERMISSIONS = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION};

    private LocationsContract.View mView;
    private BaseActivity mActivity;
    private LocationRepository mLocationRepository;
    private GpsTracker gpsTracker;

    @Override
    public void attachView(LocationsContract.View view, BaseActivity activity) {
        mView = view;
        mActivity = activity;
        getLocations();
        checkPermissions();
    }

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(mActivity,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mActivity,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mView.permissionsRequest(PERMISSIONS, REQUEST_CHECK_SETTINGS);
        } else {
            getLocation();
        }
    }

    @Override
    public void onClickDetails(String label) {
        mActivity.nextScreen(label);
    }

    @Override
    public void onClickAdd() {
        mActivity.nextScreen();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CHECK_SETTINGS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        }
    }

    public void getLocation() {
        gpsTracker = new GpsTracker(mActivity);
        if (gpsTracker.canGetLocation()) {
            mView.setDistance(gpsTracker.getLocation());
            gpsTracker.stopUsingGPS();
        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    private void getLocations() {
        if (mLocationRepository == null) {
            mLocationRepository = new LocationRepositoryImpl();
        }

        mView.showLocations(mLocationRepository.getLocations());
    }
}
