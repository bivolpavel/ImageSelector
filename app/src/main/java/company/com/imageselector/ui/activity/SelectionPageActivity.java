package company.com.imageselector.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import company.com.imageselector.client.model.ImageModel;
import company.com.imageselector.R;
import company.com.imageselector.adapters.RecyclerItemClickListener;
import company.com.imageselector.adapters.RecyclerViewAdapter;
import company.com.imageselector.client.model.ImageResponse;
import company.com.imageselector.utils.RealmManager;

import static company.com.imageselector.ui.activity.DetailedPageActivity.IMAGE_ID;
import static company.com.imageselector.ui.activity.DetailedPageActivity.SELECT_IMAGE_RESULT_CODE;

/**
 * Created by Bivol Pavel on 15.08.2017.
 */

public class SelectionPageActivity extends Activity{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<ImageModel> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_page);
        ButterKnife.bind(this);
        getImagesListFromRealm();
        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(imageList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        sendPositionOnResult(position);
                    }
                })
        );
    }

    private void getImagesListFromRealm(){
        ImageResponse imageResponse = (ImageResponse) RealmManager.getRealmObject(ImageResponse.class);
        if(imageResponse != null){
            imageList = imageResponse.getImageList();
        }
    }

    private void sendPositionOnResult(int position){
        Intent returnIntent = new Intent();
        returnIntent.putExtra(IMAGE_ID, imageList.get(position).getImageId());
        setResult(SELECT_IMAGE_RESULT_CODE, returnIntent);
        finish();
    }
}
