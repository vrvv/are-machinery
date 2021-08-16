package com.are.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.are.R;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class ImageAdapterExample extends
        SliderViewAdapter<ImageAdapterExample.SliderAdapterVH> {

    private Context context;
    private List<String> mSliderItems = new ArrayList<>();

    public ImageAdapterExample(Context context, List<String> mSliderItems) {
        this.context = context;
        this.mSliderItems = mSliderItems;
    }

    public void renewItems(List<String> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(String sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_images, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
        Glide.with(context)
                .load("http://aremachinery.com/items/images/" + mSliderItems.get(position))
                .placeholder(R.drawable.img_machine)
                .transition(withCrossFade())
                .into(viewHolder.image);

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    public class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        public ImageView image;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);

        }
    }

}