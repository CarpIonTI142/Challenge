package md.carpion.challenge.ui.add;

import android.text.Editable;

import md.carpion.challenge.ui.BaseActivity;

public interface NewContract {
    interface View {
        Editable getImageUrl();

        Editable getLabel();

        Editable getAddress();

        Editable getLat();

        Editable getLng();

        void showSavedSuccessfully();

        void showSaveFailed();
    }

    interface Presenter {
        void attachView(View view, BaseActivity activity);

        void onClickSave();
    }
}
