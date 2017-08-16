package company.com.imageselector.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import company.com.imageselector.R;
import company.com.imageselector.adapters.viewHolder.ImageViewHolder;
import company.com.imageselector.client.model.ImageModel;

/**
 * Created by user on 16.08.2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private Context mContext;
    private List<ImageModel> imageList;


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public RecyclerViewAdapter(List<ImageModel> imageList, Context context) {
        this.imageList = imageList;
        this.mContext = context;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.image_list_item, parent, false);

        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        if (holder == null) {
            return;
        }

        ImageModel imageModel = imageList.get(position);
        holder.imageIndex.setText(String.valueOf(position));
        holder.imageTitle.setText(imageModel.getImageName());

        try {
            Picasso.with(mContext)
                    .load(imageModel.getImageUrl())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.image_not_found)
                    .into(holder.image);
        } catch (Exception e) {
            e.printStackTrace();
            holder.image.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.image_not_found));
        }

    }

    @Override
    public int getItemCount() {
        return imageList != null ? imageList.size() : 0;
    }
}
