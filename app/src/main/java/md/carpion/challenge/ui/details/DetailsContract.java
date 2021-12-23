package md.carpion.challenge.ui.details;

import android.text.Editable;

import md.carpion.challenge.data.db.model.Location;
import md.carpion.challenge.ui.BaseActivity;

public interface DetailsContract {
    interface View {
        void showDetails(Location location);

        void setState(DetailsScreenState state);

        Editable getImage();

        Editable getAddress();

        Editable getLat();

        Editable getLng();
    }

    interface Presenter {
        void attachView(View view, BaseActivity activity, String label);

        void onClickEdit();

        void onClickSave();

        void onClickCancel();
    }
}
