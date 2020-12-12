package com.ouersighnimarwen.tunguidef.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ouersighnimarwen.tunguidef.R;
import com.ouersighnimarwen.tunguidef.Session;
import com.ouersighnimarwen.tunguidef.entity.RetrofitClient;
import com.ouersighnimarwen.tunguidef.ui.fragments.AccountFragment;
import com.ouersighnimarwen.tunguidef.ui.fragments.ActivitiesFragment;
import com.ouersighnimarwen.tunguidef.ui.fragments.BootFragment;
import com.ouersighnimarwen.tunguidef.ui.fragments.ForYouFragment;
import com.ouersighnimarwen.tunguidef.ui.fragments.HomeFragment;
import com.ouersighnimarwen.tunguidef.ui.fragments.HotelsFragment;
import com.ouersighnimarwen.tunguidef.ui.fragments.RestaurantFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{

    TextView nom, prenom, changeImage;
    ImageButton photo;
    String BASE_URL = "TODO";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nom = findViewById(R.id.txtMainLastName);
        prenom = findViewById(R.id.txtMainFirstName);
        photo = findViewById(R.id.photo);
        changeImage = findViewById(R.id.changeImage);

        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });
        nom.setText(Session.getInstance().getUser().getLastName());
        prenom.setText(Session.getInstance().getUser().getName());
        showFragment(new HomeFragment());

        DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(photo);
        downloadTask.execute("http://10.0.2.2:1337/images/"+Session.getInstance().getUser().getImageUrl());

    }


    public void showFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,fragment)
                .commit();
    }

    public void openFragHome(View view) {
        showFragment(new HomeFragment());
    }


    public void openFragAccount(View view) {
        showFragment(new AccountFragment(this));
    }

    public void openFragAssistant(View view) {
        showFragment(new BootFragment());
    }


    public void openFragForYou(View view) {
        showFragment(new ForYouFragment());
    }

    public void openFragHotels(View view) {
        showFragment(new HotelsFragment(this));
    }

    public void openFragRestaurants(View view)
    {
        showFragment(new RestaurantFragment(this));
    }

    public void openFragActivities(View view) {
        showFragment(new ActivitiesFragment(this));
    }




    // Load image from server url
    private class DownloadImageWithURLTask extends AsyncTask<String, Void, Bitmap> {
        ImageButton bmImage;

        public DownloadImageWithURLTask(ImageButton bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String pathToFile = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(pathToFile).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            photo.setImageBitmap(result);
        }
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
                    this.photo.setImageURI(photo);

                    //Create a file object using file path
                    File file = new File(getPathFromURI(photo));
                    RequestBody filePart = RequestBody.create(MediaType.parse(getContentResolver().getType(photo)),
                            file);
                    MultipartBody.Part mfile = MultipartBody.Part.createFormData("profile_image", file.getName(), filePart);

                    uploadProfilePhoto(mfile);



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
    /* Update user profile photo */
    private boolean updateImageUrl(int id, String imageUrl){
        result = false;
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().updateImageUrl(id, imageUrl);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 204) result = true;
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) { }
        });

        return result;
    }

    public boolean uploadProfilePhoto(MultipartBody.Part body)
    {
        result = false;
        Call<ResponseBody> call =RetrofitClient.getInstance().getApi().uploadPhoto(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        updateImageUrl(Session.getInstance().getUser().getId(), jsonObject.getString("filename"));
                        result = true;
                        Session.getInstance().getUser().setImageUrl(jsonObject.getString("filename"));
                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                    }
                    catch (IOException e)
                    {
                    } catch (JSONException e) {

                    }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return result;
    }

}
