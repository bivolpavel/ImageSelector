package company.com.imageselector.utils;

import android.content.Context;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * Created by Bivol Pavel on 16/08/2017.
 */

public class RealmManager  {

    public static void update(final RealmObject realmObject){
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction()) {
            realm.beginTransaction();
        }
        realm.copyToRealmOrUpdate(realmObject);
        realm.commitTransaction();
        realm.close();
    }

    public static RealmModel getRealmObject(Class neededClass){
        RealmModel realmObject =null;
        try {
            Realm realm = Realm.getDefaultInstance();
            realmObject = realm.where(neededClass).findFirst();
        }catch (Exception e){
            e.printStackTrace();
        }
        return realmObject;
    }

    /**
     * Initialization of Realm. Use it in application class.
     * @param context of application
     */
    public static void initRealm(Context context) {
        try {
            try {
                Realm.init(context);
                Realm.setDefaultConfiguration(buildRealmConfiguration(context));
                //try the configuration, not just init it
                Realm.getDefaultInstance().close();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Realm.deleteRealm(Realm.getDefaultInstance().getConfiguration());
                } catch (Exception ex) {
                    try {
                        final RealmConfiguration configuration = buildRealmConfiguration(context);
                        new File(configuration.getRealmDirectory(), configuration.getRealmFileName()).delete();
                        Realm.init(context);
                    } catch (Exception exc){
                        exc.printStackTrace();
                        //probably file isn't there
                    }
                }
                Realm.setDefaultConfiguration(buildRealmConfiguration(context));
                //try the configuration, not just init it
                Realm.getDefaultInstance().close();
            }
        }catch (Exception ex){
            ex.printStackTrace();

        }
    }

    public static RealmConfiguration buildRealmConfiguration(Context context){
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("myrealm.realm")
                .schemaVersion(0)
                .build();

        return config;
    }


}