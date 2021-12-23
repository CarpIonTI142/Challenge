package md.carpion.challenge.ui.details;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import md.carpion.challenge.data.db.model.Location;
import md.carpion.challenge.databinding.FragmentDetailsBinding;
import md.carpion.challenge.ui.BaseActivity;

public class DetailsFragment extends Fragment implements DetailsContract.View {
    private static final String TAG = DetailsFragment.class.getName();
    private static final String ARG_LABEL = "label";

    private FragmentDetailsBinding mBinding;
    private DetailsContract.Presenter mPresenter;
    private BaseActivity mActivity;
    private String mLabel;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(String label) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LABEL, label);
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
        if (getArguments() != null) {
            mLabel = getArguments().getString(ARG_LABEL);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentDetailsBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter = new DetailsPresenter();
        mPresenter.attachView(this, mActivity, mLabel);

        mBinding.btnEdit.setOnClickListener(v -> mPresenter.onClickEdit());
        mBinding.btnSave.setOnClickListener(v -> mPresenter.onClickSave());
        mBinding.btnCancel.setOnClickListener(v -> mPresenter.onClickCancel());
    }

    @Override
    public void showDetails(Location location) {
        mBinding.tvImage.setText(location.getImage());
        mBinding.tvLabel.setText(location.getLabel());
        mBinding.tvAddress.setText(location.getAddress());
        mBinding.tvLat.setText(String.valueOf(location.getLat()));
        mBinding.tvLng.setText(String.valueOf(location.getLng()));

        Glide
                .with(mActivity)
                .load(location.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mBinding.image);
    }

    @Override
    public void setState(DetailsScreenState state) {
        mBinding.tvImage.setEnabled(state == DetailsScreenState.EDIT);
        mBinding.tvAddress.setEnabled(state == DetailsScreenState.EDIT);
        mBinding.tvLat.setEnabled(state == DetailsScreenState.EDIT);
        mBinding.tvLng.setEnabled(state == DetailsScreenState.EDIT);
        mBinding.btnEdit.setVisibility(state == DetailsScreenState.VIEW ? View.VISIBLE : View.GONE);
        mBinding.btnSave.setVisibility(state == DetailsScreenState.VIEW ? View.GONE : View.VISIBLE);
        mBinding.btnCancel.setVisibility(state == DetailsScreenState.VIEW ? View.GONE : View.VISIBLE);
    }

    @Override
    public Editable getImage() {
        return mBinding.tvImage.getText();
    }

    @Override
    public Editable getAddress() {
        return mBinding.tvAddress.getText();
    }

    @Override
    public Editable getLat() {
        return mBinding.tvLat.getText();
    }

    @Override
    public Editable getLng() {
        return mBinding.tvLng.getText();
    }
}