package com.example.newsapp.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.newsapp.Adapter.NewsAdapter;
import com.example.newsapp.Helper.Constants;
import com.example.newsapp.Helper.GetRetrofitClients;
import com.example.newsapp.Models.NewsModel;
import com.example.newsapp.Models.SourceModel;
import com.example.newsapp.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SourceActivity extends AppCompatActivity {

    TextView txtId,txtName,txtDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_source);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtId = findViewById(R.id.txtId);
        txtName = findViewById(R.id.txtName);
        txtDesc = findViewById(R.id.txtDesc);

        getSource();

    }

    void getSource(){

        GetRetrofitClients.getClients().getSource(Constants.API_KEY).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d("getSource", "API called successfully: "+response.body());

                        JsonObject jsonObject = response.body();

                        SourceModel sourceModel = new Gson().fromJson(jsonObject, SourceModel.class);




                        txtId.setText(sourceModel.getId());
                        txtName.setText(sourceModel.getName());
                        txtDesc.setText(sourceModel.getDescription());

//                            if (newsModel.getAuthor()!=null){
//                                newsModelList.add(newsModel);
//                            }

                    }else
                    {
                        Log.d("getSource", "API call failed");
                    }
                } catch (Exception e) {
                    Log.d("getSource", "Exception: " + e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("onFailure", "Exception: " + t.getMessage());

            }
        });
    }
}