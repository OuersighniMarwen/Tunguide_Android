package com.ouersighnimarwen.tunguidef.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ouersighnimarwen.tunguidef.R;
import com.ouersighnimarwen.tunguidef.adapters.MenuAdapter;
import com.ouersighnimarwen.tunguidef.entity.Activityy;
import com.ouersighnimarwen.tunguidef.entity.MenuModel;
import com.ouersighnimarwen.tunguidef.entity.RetrofitClient;
import com.ouersighnimarwen.tunguidef.ui.activities.ActActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitiesFragment extends Fragment
{

    View view;
    ImageView addActivity;
    private Context context;
    List<Activityy> data = new ArrayList<>();

    public ActivitiesFragment(Context context)
    {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_activities, container, false);
        final RecyclerView recyclerViewA= view.findViewById(R.id.rvMenuA);

        addActivity = view.findViewById(R.id.add_act_image);

        addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActActivity.class);
                context.startActivity(intent);
            }
        });
        // ----------------- MENU HOTELS ------------------------
        // data to populate the RecyclerView with

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().getActivities();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for(int i=0; i<jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Activityy a = new Activityy(jsonObject.getInt("activity_id"),
                                jsonObject.getString("activity_name"),
                                jsonObject.getString("activity_adresse"),
                                jsonObject.getString("activity_image"),
                                jsonObject.getInt("activity_price"));

                        data.add(a);

                        MenuModel mydata[] = new MenuModel[data.size()];

                        for (int j = 0; j< data.size(); j++)
                        {
                            mydata[j] = new MenuModel(data.get(j).getImage(), data.get(j).getName());
                        }

                        MenuAdapter menuAdapter = new MenuAdapter(mydata,context);

                        // set up the RecyclerView

                        recyclerViewA.setHasFixedSize(true);
                        recyclerViewA.setLayoutManager(new LinearLayoutManager(context));
                        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                        recyclerViewA.setLayoutManager(horizontalLayoutManager);
                        recyclerViewA.setAdapter(menuAdapter);
                    }



                }
                catch (IOException e)
                {
                } catch (JSONException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });




        // Inflate the layout for this fragment
        return view;
    }

}
