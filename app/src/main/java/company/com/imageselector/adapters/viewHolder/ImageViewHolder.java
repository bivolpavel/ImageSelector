package company.com.imageselector.adapters.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import company.com.imageselector.R;

/**
 * Created by Bivol Pavel on 16.08.2017.
 */

public class ImageViewHolder extends RecyclerView.ViewHolder{

    public TextView imageIndex;
    public TextView imageTitle;
    public ImageView image;

    public ImageViewHolder(View itemView) {
        super(itemView);
        this.imageIndex = (TextView) itemView.findViewById(R.id.item_index);
        this.imageTitle = (TextView) itemView.findViewById(R.id.image_title);
        this.image = (ImageView) itemView.findViewById(R.id.image);
    }
}
