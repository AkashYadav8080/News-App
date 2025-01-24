package com.example.newsapp.Fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapp.Adapter.NewsAdapter;
import com.example.newsapp.Helper.Constants;
import com.example.newsapp.Helper.GetRetrofitClients;
import com.example.newsapp.Models.NewsModel;
import com.example.newsapp.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EverythingFragment extends Fragment {

    RecyclerView newsRecycler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_everything, container, false);

        newsRecycler = view.findViewById(R.id.newsRecycler);

        // Shimmer effect
        ShimmerFrameLayout shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        ShimmerFrameLayout shimmerFrameLayout2 = view.findViewById(R.id.shimmer_view_container2);
        ShimmerFrameLayout shimmerFrameLayout3 = view.findViewById(R.id.shimmer_view_container3);

        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout2.startShimmer();
        shimmerFrameLayout3.startShimmer();

        new Handler().postDelayed(() -> {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);

            shimmerFrameLayout2.stopShimmer();
            shimmerFrameLayout2.setVisibility(View.GONE);

            shimmerFrameLayout3.stopShimmer();
            shimmerFrameLayout3.setVisibility(View.GONE);
            newsRecycler.setVisibility(View.VISIBLE);

        }, 3000);


        getAllNews();
        return view;
    }


    void getAllNews() {

        List<NewsModel> newsModelList = new ArrayList<>();

        GetRetrofitClients.getClients().getEverything(Constants.Q,Constants.API_KEY).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d("getAllNews", "API called successfully: "+response.body());

                        JsonObject jsonObject = response.body();
                        JsonArray jsonArray = jsonObject.get("articles").getAsJsonArray();

                        for (int i=0;i<jsonArray.size();i++){
                            JsonObject articlesObject = jsonArray.get(i).getAsJsonObject();

                            NewsModel newsModel = new Gson().fromJson(articlesObject, NewsModel.class);

                            if (newsModel.getAuthor()!=null){
                                newsModelList.add(newsModel);
                            }

                        }

                        NewsAdapter newsAdapter = new NewsAdapter(getContext(),newsModelList);
                        newsRecycler.setAdapter(newsAdapter);
                        newsRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                    }
                    else
                    {
                        Log.d("getAllNews", "API call failed");
                    }
                } catch (Exception e) {
                    Log.d("getAllNews", "Exception: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("getAllNews", "API call failed: " + t.getLocalizedMessage());
            }
        });
    }

}