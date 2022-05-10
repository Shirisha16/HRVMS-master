package com.cstech.hrvms.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cstech.hrvms.Adapters.AddEventImages;
import com.cstech.hrvms.Adapters.EventsGalleryAdapter;
import com.cstech.hrvms.Models.DeleteEmployeeWorkInfoModel;
import com.cstech.hrvms.Models.EventsImages;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Service.RestService;
import com.cstech.hrvms.Supporters.PreferenceConnector;
import com.cstech.hrvms.Supporters.Utils;
import com.cstech.hrvms.UserFragments.NewsFragments;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EventsGallery extends AppCompatActivity {

    GridView galleryGridView;
    TextView backButton,addPhoto;
    RestService service;
    int NewsId;
    ImageView eventPhoto;
    String encodedImage;
    private final static int SELECT_PHOTO = 12345;
    List<EventsImages.EventsImagesList> eventsImagesLists;
    boolean clickFlag ;
    String mRequiredMenu="";
    int pose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_gallery);

        eventsImagesLists=new ArrayList<>();
        galleryGridView=(GridView)findViewById(R.id.galleryGridView);
        backButton=(TextView) findViewById(R.id.backButton);
        addPhoto=(TextView) findViewById(R.id.addPhoto);
        service=new RestService();

        NewsId=getIntent().getIntExtra("NewsId",0);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        String Designation= PreferenceConnector.readString(this, "Type","");
        if (Designation.equalsIgnoreCase("Admin")) {
            addPhoto.setVisibility(View.VISIBLE);

            galleryGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                    clickFlag=false;
                    mRequiredMenu = "options";

                    LoadLong();
                    pose=position;
                    return true;
                }
            });

        }else {
            addPhoto.setVisibility(View.GONE);
        }

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(EventsGallery.this);
                LayoutInflater inflater=LayoutInflater.from(EventsGallery.this);
                View view=inflater.inflate(R.layout.uploadimage,null);
                 eventPhoto=(ImageView)view.findViewById(R.id.eventPhoto) ;
                TextView upload=(TextView)view.findViewById(R.id.upload);
                TextView cancel=(TextView)view.findViewById(R.id.cancel);

                eventPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new
                                Intent(Intent.ACTION_PICK, android.provider.MediaStore
                                .Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(i, SELECT_PHOTO);
                    }
                });
                builder.setView(view);
                final AlertDialog dialog=builder.create();
                dialog.show();

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        encodedImage=null;

                    }
                });


                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (encodedImage==null)
                        {
                            Utils.showAlertDialog(EventsGallery.this, "Please select image",false);
                        }else {
                            Utils.showProgressDialog(EventsGallery.this);
                            AddEventImages eventImages=new AddEventImages();
                            eventImages.setId(NewsId);
                            eventImages.setPhoto(encodedImage);
                            service.getService().addEventImages(NewsId, eventImages, new Callback<DeleteEmployeeWorkInfoModel>() {
                                @Override
                                public void success(DeleteEmployeeWorkInfoModel deleteEmployeeWorkInfoModel, Response response) {

                                    if (deleteEmployeeWorkInfoModel.getMessage()!=null){
                                        encodedImage=null;
                                        dialog.dismiss();
                                        Utils.dismissProgressDialog();
                                        Utils.showAlertDialog(EventsGallery.this, deleteEmployeeWorkInfoModel.getMessage(),false);
                                        LoadGallery();
                                    }else {
                                        Utils.dismissProgressDialog();
                                        encodedImage=null;
                                        Utils.showAlertDialog(EventsGallery.this, deleteEmployeeWorkInfoModel.getErrorMessage(),false);
                                    }

                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    encodedImage=null;
                                    Utils.showAlertDialog(EventsGallery.this, error.getMessage(),false);
                                    Utils.dismissProgressDialog();

                                }
                            });
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        galleryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                clickFlag = true;
                mRequiredMenu = "standard";
                pose=position;
                LoadLong();


            }
        });

        LoadGallery();

    }

    private void LoadLong() {
        if(clickFlag){
            if(mRequiredMenu.equals("standard")){
                //just click event
                //parent.showContextMenuForChild(v);

                AlertDialog.Builder builder=new AlertDialog.Builder(EventsGallery.this);

                LayoutInflater inflater=LayoutInflater.from(EventsGallery.this);
                View view1=inflater.inflate(R.layout.imageview,null);
                ImageView singleImage=(ImageView)view1.findViewById(R.id.singleImage);
                TextView cancel=(TextView)view1.findViewById(R.id.cancel);
                TextView delete=(TextView)view1.findViewById(R.id.delete);
                builder.setView(view1);
                delete.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);

                byte[] decodeString= Base64.decode(eventsImagesLists.get(pose).getPhoto(),Base64.NO_PADDING);
                // Bitmap profilePic= BitmapFactory.decodeByteArray(decodeString,0,decodeString.length);
                Glide.with(view1)
                        .asBitmap()
                        .load(decodeString)
                        .into(singleImage);
                final AlertDialog dialog=builder.create();
                dialog.show();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }else{}
        }else{
            if(mRequiredMenu.equals("options")){
                //just Long click event
                //parent.showContextMenuForChild(v);

                AlertDialog.Builder deleteImage=new AlertDialog.Builder(EventsGallery.this);
                deleteImage.setMessage("Do You Want To Delete?");
                deleteImage.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dia, int which) {
                        dia.dismiss();


                        service.getService().deleteEventPhoto(eventsImagesLists.get(pose).getId(), new Callback<DeleteEmployeeWorkInfoModel>() {
                            @Override
                            public void success(DeleteEmployeeWorkInfoModel deleteEmployeeWorkInfoModel, Response response) {

                                if (deleteEmployeeWorkInfoModel.getMessage()!=null)
                                {
                                    Utils.showAlertDialog(EventsGallery.this, deleteEmployeeWorkInfoModel.getMessage(),false);
                                }else {

                                    Utils.showAlertDialog(EventsGallery.this, deleteEmployeeWorkInfoModel.getErrorMessage(),false);
                                }
                                LoadGallery();

                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Utils.showAlertDialog(EventsGallery.this, error.getMessage(),false);
                            }
                        });
                    }
                });
                deleteImage.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog=deleteImage.create();
                alertDialog.show();

            }else{}

        }
    }

    private void LoadGallery() {
        Utils.showProgressDialog(this);
        service.getService().getEventsImages(NewsId,new Callback<EventsImages>() {
            @Override
            public void success(EventsImages eventsImages, Response response) {

                eventsImagesLists=eventsImages.getModel();

                if (eventsImagesLists.size()>0)
                {
                    EventsGalleryAdapter adapter=new EventsGalleryAdapter(EventsGallery.this, eventsImagesLists);
                    galleryGridView.setAdapter(adapter);
                    Utils.dismissProgressDialog();
                }
                else {
                    eventsImagesLists.clear();
                    EventsGalleryAdapter adapter=new EventsGalleryAdapter(EventsGallery.this, eventsImagesLists);
                    galleryGridView.setAdapter(adapter);
                    Utils.showAlertDialog(EventsGallery.this,eventsImages.getErrorMessage(),false);
                    Utils.dismissProgressDialog();

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.showAlertDialog(EventsGallery.this,error.getMessage(),false);
                Utils.dismissProgressDialog();
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = data.getData();
                        String imagepath = imageUri.getPath().toString();

                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(imageUri,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imagepath = cursor.getString(columnIndex);
                        cursor.close();
                        //Toast.makeText(PersonalDetails.this,imagepath, Toast.LENGTH_SHORT).show();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        eventPhoto.setImageBitmap(selectedImage);

                        encodedImage = BitMapToString(selectedImage);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

}
