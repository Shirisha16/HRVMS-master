package com.cstech.hrvms.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.cstech.hrvms.Activities.EventsGallery;
import com.cstech.hrvms.Interfaces.LoadDetails;
import com.cstech.hrvms.Models.DeleteEmployeeWorkInfoModel;
import com.cstech.hrvms.Models.EventsImages;
import com.cstech.hrvms.Models.News;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Service.RestService;
import com.cstech.hrvms.Supporters.PreferenceConnector;
import com.cstech.hrvms.Supporters.Utils;
import com.cstech.hrvms.UserFragments.NewsFragments;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewsAdapter extends BaseAdapter {

    String UserId;
    FragmentActivity activity;
    List<News.NewsList> newsLists;
    LayoutInflater inflater;
    LoadDetails loadDetails;
    public NewsAdapter(FragmentActivity activity, List<News.NewsList> newsLists, LoadDetails loadDetails) {

        this.activity=activity;
        this.newsLists=newsLists;
        inflater=LayoutInflater.from(activity);
        this.loadDetails=loadDetails;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return newsLists.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(R.layout.new_fields,null);

        TextView title=(TextView)view.findViewById(R.id.title);
        TextView description=(TextView)view.findViewById(R.id.description);
        TextView viewEventPhotos=(TextView)view.findViewById(R.id.viewEventPhotos);
        TextView updateNews=(TextView)view.findViewById(R.id.updateNews);
        updateNews.setVisibility(View.GONE);
        String Designation= PreferenceConnector.readString(activity, "Type","");
        if (Designation.equalsIgnoreCase("Admin")) {

            updateNews.setVisibility(View.VISIBLE);
        }

        title.setText(newsLists.get(position).getTitle());
        String Description=newsLists.get(position).getDescription();

        if (Description.contains("/n")||Description.contains("<p>")||Description.contains("<br>"))
        {
            description.setText(Html.fromHtml(Description));
        }else {
            description.setText(Description);
        }


        viewEventPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(activity, EventsGallery.class);
                intent.putExtra("NewsId",newsLists.get(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
            }
        });

        updateNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserId = PreferenceConnector.readString(activity, "UserId","");
                AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                View view1=inflater.inflate(R.layout.addnew,null);
                final EditText title=(EditText)view1.findViewById(R.id.title);
                final EditText description=(EditText)view1.findViewById(R.id.description);
                TextView submit=(TextView)view1.findViewById(R.id.submit);
                title.setText(newsLists.get(position).getTitle());
                description.setText(newsLists.get(position).getDescription());
                builder.setView(view1);
                final AlertDialog dialog=builder.create();
                dialog.show();
                submit.setText("Update");
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (title.getText().toString().trim().equalsIgnoreCase(""))
                        {
                            title.setError("Title should not be empty");
                            title.requestFocus();
                        }else if (description.getText().toString().equalsIgnoreCase(""))
                        {

                            description.setError("Description should not be empty");
                            description.requestFocus();
                        }
                        else {

                            News.NewsList news = new News.NewsList();
                            news.setId(newsLists.get(position).getId());
                            news.setTitle(title.getText().toString().trim());
                            news.setDescription(description.getText().toString().trim());

                            RestService service = new RestService();

                            service.getService().updateNews(Integer.parseInt(UserId), news, new Callback<DeleteEmployeeWorkInfoModel>() {
                                @Override
                                public void success(DeleteEmployeeWorkInfoModel deleteEmployeeWorkInfoModel, Response response) {

                                    if (deleteEmployeeWorkInfoModel.getMessage() != null) {

                                        Utils.showAlertDialog(activity, deleteEmployeeWorkInfoModel.getMessage(), false);
                                        loadDetails.onMethodCallback();
                                    } else {
                                        Utils.showAlertDialog(activity, deleteEmployeeWorkInfoModel.getErrorMessage(), false);
                                    }

                                    dialog.dismiss();


                                }

                                @Override
                                public void failure(RetrofitError error) {

                                    Utils.showAlertDialog(activity, error.getMessage(), false);
                                    dialog.dismiss();
                                }
                            });
                        }

                    }
                });
            }
        });



        return view;
    }
}
