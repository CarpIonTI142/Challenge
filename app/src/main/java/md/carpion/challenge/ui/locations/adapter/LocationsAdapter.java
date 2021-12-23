package md.carpion.challenge.ui.locations.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DecimalFormat;
import java.util.List;

import md.carpion.challenge.R;
import md.carpion.challenge.data.db.model.Location;
import md.carpion.challenge.databinding.ViewLocationBinding;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {
    private static final String TAG = LocationsAdapter.class.getName();

    private final List<Location> mLocations;
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final Listener mListener;
    private android.location.Location mLocation;

    public LocationsAdapter(List<Location> locations, Listener listener, Context context) {
        mLocations = locations;
        mListener = listener;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewLocationBinding binding = ViewLocationBinding.inflate(mLayoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mLocations.get(position));
    }

    @Override
    public int getItemCount() {
        return mLocations.size();
    }

    public void setLocation(android.location.Location location) {
        mLocation = location;
        notifyDataSetChanged();
    }

    public interface Listener {
        void onClickDetails(String label);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewLocationBinding mBinding;

        public ViewHolder(@NonNull ViewLocationBinding biding) {
            super(biding.getRoot());
            mBinding = biding;
        }

        public void bind(Location location) {
            mBinding.tvLabel.setText(location.getLabel());
            mBinding.tvAddress.setText(location.getAddress());
            mBinding.tvLat.setText(String.valueOf(location.getLat()));
            mBinding.tvLng.setText(String.valueOf(location.getLng()));

            Glide
                    .with(mContext)
                    .load(location.getImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(mBinding.image);

            mBinding.root.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onClickDetails(location.getLabel());
                }
            });

            if (mLocation != null) {
                android.location.Location toLocation = new android.location.Location(location.getLabel());
                toLocation.setLatitude(location.getLat());
                toLocation.setLongitude(location.getLng());
                String distance = new DecimalFormat("#.##").format(mLocation.distanceTo(new android.location.Location(toLocation)) / 1000);
                mBinding.tvDistance.setText(mContext.getString(R.string.distance, distance));
            }
        }
    }
}
