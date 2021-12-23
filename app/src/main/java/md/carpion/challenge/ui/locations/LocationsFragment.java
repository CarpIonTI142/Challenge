package md.carpion.challenge.ui.locations;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.List;

import md.carpion.challenge.data.db.model.Location;
import md.carpion.challenge.databinding.FragmentLocationsBinding;
import md.carpion.challenge.ui.BaseActivity;
import md.carpion.challenge.ui.locations.adapter.LocationsAdapter;

public class LocationsFragment extends Fragment implements LocationsContract.View {
    private static final String TAG = LocationsFragment.class.getName();

    private FragmentLocationsBinding mBiding;
    private LocationsContract.Presenter mPresenter;
    private BaseActivity mActivity;
    private List<Location> mLocations;
    private LocationsAdapter mLocationsAdapter;

    public LocationsFragment() {
        // Required empty public constructor
    }

    public static LocationsFragment newInstance() {
        LocationsFragment fragment = new LocationsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBiding = FragmentLocationsBinding.inflate(inflater, container, false);
        return mBiding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter = new LocationsPresenter();
        mPresenter.attachView(this, mActivity);

        setListeners();
    }

    private void setListeners() {
        mBiding.btnAdd.setOnClickListener(v -> mPresenter.onClickAdd());
    }

    @Override
    public void showLocations(List<Location> locations) {
        mLocations = locations;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false);
        mBiding.recyclerView.setLayoutManager(layoutManager);
        mLocationsAdapter = new LocationsAdapter(mLocations, label -> mPresenter.onClickDetails(label), mActivity);
        mBiding.recyclerView.setAdapter(mLocationsAdapter);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mBiding.recyclerView);
    }

    @Override
    public void permissionsRequest(String[] permissions, int requestCode) {
        requestPermissions(permissions, requestCode);
    }

    @Override
    public void setDistance(android.location.Location location) {
        mLocationsAdapter.setLocation(location);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        mPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}