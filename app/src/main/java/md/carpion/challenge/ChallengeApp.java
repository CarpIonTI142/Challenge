package md.carpion.challenge;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import md.carpion.challenge.data.network.repository.ChallengeServices;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChallengeApp extends Application {

    private static ChallengeApp sChallengeApp;
    private static Retrofit sRetrofit;
    private static Realm sRealm;

    public static ChallengeApp getInstance() {
        return sChallengeApp;
    }

    public static ChallengeServices getApi() {
        return sRetrofit.create(ChallengeServices.class);
    }

    public static Realm getRealm() {
        return sRealm;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //Set Challenge application instance
        sChallengeApp = this;

        //Init realm
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name("Challenge").build();
        Realm.setDefaultConfiguration(realmConfiguration);
        sRealm = Realm.getInstance(realmConfiguration);

        //Init retrofit
        sRetrofit = new Retrofit.Builder()
                .baseUrl("http://demo6609886.mockable.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
