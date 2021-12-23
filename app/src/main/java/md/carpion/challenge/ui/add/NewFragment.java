package md.carpion.challenge.ui.add;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import md.carpion.challenge.R;
import md.carpion.challenge.databinding.FragmentNewBinding;
import md.carpion.challenge.ui.BaseActivity;

public class NewFragment extends Fragment implements NewContract.View {
    private static final String TAG = NewFragment.class.getName();

    private FragmentNewBinding mBinding;
    private NewContract.Presenter mPresenter;
    private BaseActivity mActivity;
    private String mLabel;

    public NewFragment() {
        // Required empty public constructor
    }

    public static NewFragment newInstance() {
        return new NewFragment();
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
        mBinding = FragmentNewBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter = new NewPresenter();
        mPresenter.attachView(this, mActivity);

        initListeners();
    }

    private void initListeners() {
        mBinding.btnSave.setOnClickListener(v -> mPresenter.onClickSave());

        mBinding.tvLabel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                boolean isLabelEmpty = editable.toString().isEmpty();
                mBinding.btnSave.setEnabled(!isLabelEmpty);

                if (isLabelEmpty) {
                    mBinding.tvLabel.setError(getString(R.string.required_field));
                } else if (mBinding.tvLabel.getError() != null) {
                    mBinding.tvLabel.setError(null);
                }
            }
        });
    }

    @Override
    public Editable getImageUrl() {
        return mBinding.tvImage.getText();
    }

    @Override
    public Editable getLabel() {
        return mBinding.tvLabel.getText();
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

    @Override
    public void showSavedSuccessfully() {
        Toast.makeText(mActivity, R.string.location_saved_successfully, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSaveFailed() {
        Toast.makeText(mActivity, R.string.location_save_failed, Toast.LENGTH_LONG).show();
    }
}