package md.carpion.challenge.ui.details;

import android.util.Log;

import md.carpion.challenge.data.db.model.Location;
import md.carpion.challenge.data.db.repository.LocationRepository;
import md.carpion.challenge.data.db.repository.LocationRepositoryImpl;
import md.carpion.challenge.data.network.model.Item;
import md.carpion.challenge.ui.BaseActivity;

public class DetailsPresenter implements DetailsContract.Presenter {
    private static final String TAG = DetailsPresenter.class.getName();

    private DetailsContract.View mView;
    private BaseActivity mActivity;
    private LocationRepository mLocationRepository;
    private DetailsScreenState mState;
    private Location mLocation;

    @Override
    public void attachView(DetailsContract.View view, BaseActivity activity, String label) {
        mView = view;
        mActivity = activity;
        mState = DetailsScreenState.VIEW;

        showDetails(label);
    }

    private void showDetails(String label) {
        if (mLocationRepository == null) {
            mLocationRepository = new LocationRepositoryImpl();
        }

        mLocation = mLocationRepository.getLocationByLabel(label);
        mView.showDetails(mLocation);
        mView.setState(mState);
    }

    @Override
    public void onClickEdit() {
        if (mState == DetailsScreenState.VIEW) {
            mState = DetailsScreenState.EDIT;
        } else {
            mState = DetailsScreenState.VIEW;
        }

        mView.setState(mState);
    }

    @Override
    public void onClickSave() {
        String image = mView.getImage() != null ? mView.getImage().toString() : "";
        String address = mView.getAddress() != null ? mView.getAddress().toString() : "";
        String lat = mView.getLat() != null ? mView.getLat().toString() : "0";
        String lng = mView.getLng() != null ? mView.getLng().toString() : "0";

        Item item = new Item();
        item.setLabel(mLocation.getLabel());
        item.setAddress(address);
        item.setImage(image);
        item.setLat(Double.parseDouble(lat));
        item.setLng(Double.parseDouble(lng));

        mLocationRepository.insertOrUpdateLocation(
                item,
                () -> {
                    Log.d(TAG, "Update location success");
                    onClickCancel();
                },
                () -> Log.d(TAG, "Update location error")
        );
    }

    @Override
    public void onClickCancel() {
        mState = DetailsScreenState.VIEW;
        mView.setState(mState);
    }
}
