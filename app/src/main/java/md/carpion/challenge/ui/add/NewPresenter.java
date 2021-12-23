package md.carpion.challenge.ui.add;

import android.util.Log;

import md.carpion.challenge.data.db.repository.LocationRepository;
import md.carpion.challenge.data.db.repository.LocationRepositoryImpl;
import md.carpion.challenge.data.network.model.Item;
import md.carpion.challenge.ui.BaseActivity;

public class NewPresenter implements NewContract.Presenter {
    private static final String TAG = NewPresenter.class.getName();

    private NewContract.View mView;
    private BaseActivity mActivity;
    private LocationRepository mLocationRepository;

    @Override
    public void attachView(NewContract.View view, BaseActivity activity) {
        mView = view;
        mActivity = activity;
    }

    @Override
    public void onClickSave() {
        if (mLocationRepository == null) {
            mLocationRepository = new LocationRepositoryImpl();
        }

        String image = mView.getImageUrl() != null ? mView.getImageUrl().toString() : "";
        String label = mView.getLabel().toString();
        String address = mView.getAddress() != null ? mView.getAddress().toString() : "";
        String lat = mView.getLat() != null ? mView.getLat().toString() : "0";
        String lng = mView.getLng() != null ? mView.getLng().toString() : "0";

        Item item = new Item();
        item.setLabel(label);
        item.setImage(image);
        item.setAddress(address);
        item.setLat(Double.parseDouble(lat));
        item.setLng(Double.parseDouble(lng));

        mLocationRepository.insertOrUpdateLocation(
                item,
                () -> {
                    Log.d(TAG, "New location added success");
                    mView.showSavedSuccessfully();
                    mActivity.onBackPressed();
                },
                () -> {
                    Log.d(TAG, "New location added fail");
                    mView.showSaveFailed();
                }
        );
    }
}
