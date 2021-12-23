package md.carpion.challenge.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import md.carpion.challenge.R;
import md.carpion.challenge.data.db.repository.LocationRepository;
import md.carpion.challenge.data.db.repository.LocationRepositoryImpl;
import md.carpion.challenge.databinding.ActivityBaseBinding;
import md.carpion.challenge.ui.add.NewFragment;
import md.carpion.challenge.ui.details.DetailsFragment;
import md.carpion.challenge.ui.locations.LocationsFragment;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getName();

    private ActivityBaseBinding mBiding;
    private ActionBar mActionBar;
    private LocationRepository mLocationRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBiding = ActivityBaseBinding.inflate(getLayoutInflater());
        View view = mBiding.getRoot();
        setContentView(view);

        setOnBackStackChangedListener();
        nextScreen();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateData() {
        if (mLocationRepository == null) {
            mLocationRepository = new LocationRepositoryImpl();
            mLocationRepository.updateLocations(
                    () -> {
                        Log.d(TAG, "Update data success");
                        nextScreen();
                    },
                    () -> Log.d(TAG, "Update data failed")
            );
        }
    }

    private void setActionBar(@StringRes int title) {
        if (mActionBar == null) {
            mActionBar = getSupportActionBar();
        }

        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setTitle(title);

            if (!mActionBar.isShowing()) {
                mActionBar.show();
            }
        }
    }

    private void setOnBackStackChangedListener() {
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            Fragment fragment = getCurrentFragment();

            if (fragment != null) {
                updateTitleAndDrawer(fragment);
            }
        });
    }

    private void updateTitleAndDrawer(Fragment fragment) {
        if (fragment instanceof LocationsFragment) {
            setActionBar(R.string.locations);
        } else if (fragment instanceof DetailsFragment) {
            setActionBar(R.string.details);
        } else {
            setActionBar(R.string.add_new_location);
        }
    }

    public void nextScreen() {
        nextScreen("");
    }

    public void nextScreen(String label) {
        Fragment fragment = getCurrentFragment();

        if (fragment == null) {
            replaceFragment(LocationsFragment.newInstance());
        } else if (fragment instanceof LocationsFragment && !label.isEmpty()) {
            replaceFragment(DetailsFragment.newInstance(label));
        } else {
            replaceFragment(NewFragment.newInstance());
        }
    }

    private void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        FragmentTransaction ft = manager.beginTransaction();

        if (!fragmentPopped) {
            ft.replace(R.id.container, fragment);
        }

        ft.addToBackStack(backStateName);
        ft.commit();
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.container);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}