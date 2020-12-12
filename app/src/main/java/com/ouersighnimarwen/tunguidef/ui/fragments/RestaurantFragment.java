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
import com.ouersighnimarwen.tunguidef.adapters.MenuAdapterR;
import com.ouersighnimarwen.tunguidef.entity.MenuModel;
import com.ouersighnimarwen.tunguidef.entity.Restaurant;
import com.ouersighnimarwen.tunguidef.entity.RetrofitClient;
import com.ouersighnimarwen.tunguidef.ui.activities.RestaurantActivity;

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


public class RestaurantFragment extends Fragment {
    View view;
    ImageView addRest;
    private Context context;
    List<Restaurant> data = new ArrayList<>();

    public RestaurantFragment(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        final RecyclerView recyclerViewR = view.findViewById(R.id.rvMenuR);

        addRest = view.findViewById(R.id.add_rest_image);

        addRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RestaurantActivity.class);
                context.startActivity(intent);
            }
        });

        //----------------------------- Menu Restaurants -------------------------------------
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().getRestaurants();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for(int i=0; i<jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Restaurant r = new Restaurant(jsonObject.getInt("resto_id"),
                                jsonObject.getString("resto_name"),
                                jsonObject.getString("resto_adresse"),
                                jsonObject.getString("resto_image"),
                                jsonObject.getInt("resto_star"));
                        //  jsonObject.getDouble("hotel_price"));

                        data.add(r);

                        MenuModel mydata[] = new MenuModel[data.size()];

                        for (int j = 0; j< data.size(); j++)
                        {
                            mydata[j] = new MenuModel(data.get(j).getImage(), data.get(j).getName());
                        }

                        MenuAdapterR menuAdapterR = new MenuAdapterR(mydata,context);

                        // set up the RecyclerView

                        recyclerViewR.setHasFixedSize(true);
                        recyclerViewR.setLayoutManager(new LinearLayoutManager(context));
                        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                        recyclerViewR.setLayoutManager(horizontalLayoutManager);
                        recyclerViewR.setAdapter(menuAdapterR);
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
