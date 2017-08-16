package company.com.imageselector.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import company.com.imageselector.mock.MockService;
import company.com.imageselector.R;
import company.com.imageselector.client.model.ImageModel;
import company.com.imageselector.client.model.ImageResponse;
import company.com.imageselector.utils.DateUtils;
import company.com.imageselector.utils.RealmManager;
import io.realm.Realm;

/**
 * Created by Bivol Pavel on 16.08.2017.
 */

public class DetailedPageActivity extends Activity {

    public static int SELECT_IMAGE_RESULT_CODE = 99;
    public static String IMAGE_ID = "imageId";

    private List<ImageModel> imageList;
    private ImageModel selectedImage;

    @BindView(R.id.close_button)
    ImageView closeButton;

    @BindView(R.id.image)
    ImageView imageView;

    @BindView(R.id.image_title)
    EditText imageTitle;

    @BindView(R.id.random_button)
    Button randomButton;

    @BindView(R.id.select_button)
    Button selectButton;

    @BindView(R.id.save_button)
    LinearLayout saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_page);
        ButterKnife.bind(this);
        setOnClickListners();
        loadImageList();
        displaySelectedImage(getRandomImage());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null){
            if(resultCode == SELECT_IMAGE_RESULT_CODE){
                String id = data.getStringExtra(IMAGE_ID);
                displaySelectedImage(getImageById(id));
            }
        }
    }

    private void setOnClickListners(){
        closeButton.setOnClickListener(closeButtonClickListner);
        randomButton.setOnClickListener(randomButtonClickListner);
        selectButton.setOnClickListener(selectButtonOnclickListner);
        saveButton.setOnClickListener(saveButtonClickListner);
    }

    private void loadImageList(){
        ImageResponse imageResponse = (ImageResponse) RealmManager.getRealmObject(ImageResponse.class);

        if(imageResponse == null){
            Gson gson = new Gson();
            imageResponse = gson.fromJson(MockService.getImageListJson(), ImageResponse.class);
            RealmManager.update(imageResponse);
        }

        imageList = imageResponse.getImageList();
    }

    private void displaySelectedImage(ImageModel imageModel){
        if(imageModel == null){
            displayNotFoundImage();
            return;
        }

        selectedImage = imageModel;
        imageTitle.setText(imageModel.getImageName());

        try {
            Picasso.with(this)
                    .load(imageModel.getImageUrl())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.image_not_found)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            displayNotFoundImage();
        }
    }

    private void displayNotFoundImage(){
        imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.image_not_found));
    }

    private ImageModel getRandomImage(){
        return imageList.get((new Random()).nextInt(imageList.size()));
    }

    private ImageModel getImageById(String imageId){
        if(imageId == null)
            return null;

        Realm realm = Realm.getDefaultInstance();
        return realm.where(ImageModel.class)
                .contains("imageId", imageId)
                .findFirst();
    }

    private void saveImageTitle(String title){
        if(!title.equals("")){
            if(selectedImage.isValid()){
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                selectedImage.setImageName(imageTitle.getText().toString());
                realm.commitTransaction();
                loadImageList();
                Toast.makeText(DetailedPageActivity.this, "Image name is saved!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(DetailedPageActivity.this, "Image name field is empty!", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayDialog(){

        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Created by Bivol Pavel");

        TextView time = (TextView) dialog.findViewById(R.id.time_label);
        Button dialogButton = (Button) dialog.findViewById(R.id.ok_button);

        time.setText(DateUtils.getCurrentTime());

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        hideDialogAfterDelay(dialog, 10000);
    }


    private void hideDialogAfterDelay(final Dialog dialog, int delayTime){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, delayTime);
    }

    View.OnClickListener closeButtonClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            displayDialog();
        }
    };

    View.OnClickListener randomButtonClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            displaySelectedImage(getRandomImage());
        }
    };

    View.OnClickListener selectButtonOnclickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivityForResult(new Intent(DetailedPageActivity.this, SelectionPageActivity.class),
                    SELECT_IMAGE_RESULT_CODE);
        }
    };

    View.OnClickListener saveButtonClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            saveImageTitle(imageTitle.getText().toString());
        }
    };
}
