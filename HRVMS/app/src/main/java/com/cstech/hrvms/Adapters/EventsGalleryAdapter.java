package com.cstech.hrvms.Adapters;

import android.app.Activity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cstech.hrvms.Activities.EventsGallery;
import com.cstech.hrvms.Models.EventsImages;
import com.cstech.hrvms.R;

import java.util.List;

import retrofit.Callback;

public class EventsGalleryAdapter extends BaseAdapter {

    Activity eventsGallery;
    List<EventsImages.EventsImagesList> eventsImagesLists;
    LayoutInflater inflater;

    public EventsGalleryAdapter(Activity eventsGallery, List<EventsImages.EventsImagesList> eventsImagesLists) {

        this.eventsGallery=eventsGallery;
        this.eventsImagesLists=eventsImagesLists;
        inflater=LayoutInflater.from(eventsGallery);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return eventsImagesLists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(R.layout.childeventimages, null);
        ImageView galleryImage=(ImageView)view.findViewById(R.id.galleryImage);


        byte[] decodeString= Base64.decode(eventsImagesLists.get(position).getPhoto(),Base64.NO_PADDING);
        // Bitmap profilePic= BitmapFactory.decodeByteArray(decodeString,0,decodeString.length);
        Glide.with(eventsGallery)
                .asBitmap()
                .load(decodeString)
                .into(galleryImage);


        return view;
    }
}
