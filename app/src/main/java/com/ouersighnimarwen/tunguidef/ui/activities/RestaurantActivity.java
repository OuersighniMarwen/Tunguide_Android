package com.ouersighnimarwen.tunguidef.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ouersighnimarwen.tunguidef.R;
import com.ouersighnimarwen.tunguidef.entity.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantActivity extends AppCompatActivity
{
    EditText name,adresse,star;
    TextView upload_image;
    String image_url = "tmp";
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        add = findViewById(R.id.add_rest);
        name = findViewById(R.id.rest_name);
        adresse = findViewById(R.id.rest_adresse);
        star = findViewById(R.id.rest_star);
        upload_image = findViewById(R.id.upload_rest);

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Call<ResponseBody> call = RetrofitClient.getInstance().getApi().addRestaurant(name.getText().toString(),
                        adresse.getText().toString(),
                        image_url,
                        star.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());


                        }
                        catch (IOException e)
                        {
                        } catch (JSONException e) {

                        }

                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });

                finish();

            }
        });
    }
    private void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,1);
    }
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 1:
                    //data.getData returns the content URI for the selected Image
                    Uri photo = data.getData();

                    //Create a file object using file path
                    File file = new File(getPathFromURI(photo));
                    RequestBody filePart = RequestBody.create(MediaType.parse(getContentResolver().getType(photo)),
                            file);
                    MultipartBody.Part mfile = MultipartBody.Part.createFormData("profile_image", file.getName(), filePart);

                    uploadRestPhoto(mfile);



                    break;
            }
    }
    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    boolean result;
    public boolean uploadRestPhoto(MultipartBody.Part body)
    {
        result = false;
        Call<ResponseBody> call =RetrofitClient.getInstance().getApi().uploadPhoto(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    image_url = jsonObject.getString("filename");

                }
                catch (IOException e)
                {
                } catch (JSONException e) {

                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
        return result;
    }

}
