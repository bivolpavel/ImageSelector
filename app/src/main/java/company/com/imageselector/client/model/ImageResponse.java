package company.com.imageselector.client.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Bivol Pavel on 16.08.2017.
 */

public class ImageResponse extends RealmObject{

    @PrimaryKey
    int id;

    RealmList<ImageModel> imageList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<ImageModel> getImageList() {
        return imageList;
    }

    public void setImageList(RealmList<ImageModel> imageList) {
        this.imageList = imageList;
    }
}
