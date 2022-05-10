package com.cstech.hrvms.UserFragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cstech.hrvms.Activities.AdminSlideMenu;
import com.cstech.hrvms.Activities.UserSlideMenu;
import com.cstech.hrvms.Adapters.NewsAdapter;
import com.cstech.hrvms.Interfaces.LoadDetails;
import com.cstech.hrvms.Models.DeleteEmployeeWorkInfoModel;
import com.cstech.hrvms.Models.News;
import com.cstech.hrvms.R;
import com.cstech.hrvms.Service.RestService;
import com.cstech.hrvms.Service.WebServiceAPI;
import com.cstech.hrvms.Supporters.PreferenceConnector;
import com.cstech.hrvms.Supporters.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragments extends Fragment implements LoadDetails {


    TextView addNews;
    ListView newsListView;
    List<News.NewsList> newsLists;
    String UserId;
    public NewsFragments() {
        // Required empty public constructor
    }

    RestService service;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_news_fragments, container, false);

        newsListView=(ListView)view.findViewById(R.id.newsListView);
        newsLists=new ArrayList<>();
        service=new RestService();
        addNews=(TextView)view.findViewById(R.id.addNews);
        addNews.setVisibility(View.GONE);




        String Designation= PreferenceConnector.readString(getContext(), "Type","");
        if (Designation.equalsIgnoreCase("Admin")) {

            addNews.setVisibility(View.VISIBLE);

            addNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    View view1=inflater.inflate(R.layout.addnew,null);
                    final EditText title=(EditText)view1.findViewById(R.id.title);
                    final EditText description=(EditText)view1.findViewById(R.id.description);
                    TextView submit=(TextView)view1.findViewById(R.id.submit);
                    builder.setView(view1);
                    final AlertDialog dialog=builder.create();
                    dialog.show();

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

                                News.NewsList news=new News.NewsList();
                                news.setTitle(title.getText().toString());
                                news.setDescription(description.getText().toString());


                                service.getService().addNews(Integer.parseInt(UserId), news, new Callback<DeleteEmployeeWorkInfoModel>() {
                                    @Override
                                    public void success(DeleteEmployeeWorkInfoModel deleteEmployeeWorkInfoModel, Response response) {

                                        if (deleteEmployeeWorkInfoModel.getMessage()!=null)
                                        {
                                            LoadNews();
                                            Utils.showAlertDialog(getContext(), deleteEmployeeWorkInfoModel.getMessage(),false);



                                        }else {

                                            Utils.showAlertDialog(getContext(), deleteEmployeeWorkInfoModel.getErrorMessage(),false);
                                        }

                                        dialog.dismiss();


                                    }

                                    @Override
                                    public void failure(RetrofitError error) {

                                        Utils.showAlertDialog(getContext(), error.getMessage(),false);

                                        dialog.dismiss();
                                    }
                                });

                                dialog.dismiss();

                            }

                        }
                    });


                }
            });

            newsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                    AlertDialog.Builder delete=new AlertDialog.Builder(getContext());
                    delete.setMessage("Do You Want To Delete?");
                    delete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            service.getService().deleteNews(newsLists.get(position).getId(), new Callback<DeleteEmployeeWorkInfoModel>() {
                                @Override
                                public void success(DeleteEmployeeWorkInfoModel deleteEmployeeWorkInfoModel, Response response) {

                                    if (deleteEmployeeWorkInfoModel.getMessage()!=null)
                                    {
                                        LoadNews();
                                        Utils.showAlertDialog(getContext(), deleteEmployeeWorkInfoModel.getMessage(),false);
                                    }else {
                                        Utils.showAlertDialog(getContext(), deleteEmployeeWorkInfoModel.getErrorMessage(),false);
                                    }
                                    dialog.dismiss();
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    dialog.dismiss();
                                    Utils.showAlertDialog(getContext(), error.getMessage(),false);
                                }
                            });

                        }
                    });



                    delete.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog=delete.create();
                    dialog.show();

                    return true;
                }
            });
            UserId = PreferenceConnector.readString(getActivity(), "UserId","");


        }

        LoadNews();

        return view;
    }

    private void LoadNews() {

        Utils.showProgressDialog(getActivity());
        service.getService().getNews(new Callback<News>() {
            @Override
            public void success(News news, Response response) {

                newsLists=news.getModel();

                if (newsLists.size()>0)
                {
                    NewsAdapter adapter=new NewsAdapter(getActivity(),newsLists,NewsFragments.this);
                    newsListView.setAdapter(adapter);
                    Utils.dismissProgressDialog();
                    //Utils.showAlertDialog(getContext(),news.getMessage(),false);
                }else {
                    Utils.showAlertDialog(getContext(),news.getErrorMessage(),false);
                    Utils.dismissProgressDialog();
                }
                Utils.dismissProgressDialog();


            }

            @Override
            public void failure(RetrofitError error) {

                Utils.dismissProgressDialog();
                Utils.showAlertDialog(getContext(), error.getMessage(),false);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       String Designation= PreferenceConnector.readString(getContext(), "Type","");
        if (Designation.equalsIgnoreCase("Admin")) {
            ((AdminSlideMenu) getActivity())
                    .setActionBarTitle("News & Events");

        }else {

            ((UserSlideMenu) getActivity())
                    .setActionBarTitle("News & Events");
        }
    }

    @Override
    public void onMethodCallback() {
        LoadNews();
    }
}
