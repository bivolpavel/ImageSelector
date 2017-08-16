package company.com.imageselector.client.model;

import io.realm.RealmObject;

/**
 * Created by user on 15.08.2017.
 */

public class ImageModel extends RealmObject{

    private String imageUrl;

    private String imageId;

    private String imageName;

    //Empty constructor for realm
    public ImageModel() {
    }

    public ImageModel(String imageUrl, String imageId, String imageName) {
        this.imageUrl = imageUrl;
        this.imageId = imageId;
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
