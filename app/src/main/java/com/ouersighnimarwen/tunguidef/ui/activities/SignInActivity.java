package com.ouersighnimarwen.tunguidef.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ouersighnimarwen.tunguidef.R;
import com.ouersighnimarwen.tunguidef.Retrofit.INodeJs;
import com.ouersighnimarwen.tunguidef.Session;
import com.ouersighnimarwen.tunguidef.entity.RetrofitClient;
import com.ouersighnimarwen.tunguidef.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity
{

    INodeJs myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    EditText edSignInEmail;
    EditText edSignInPassword;
    Button btn_signIn;
    Button btn_signUp;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Init API
      //  Retrofit retrofit = RetrofitClient.getInstance();
       // myAPI = retrofit.create(INodeJs.class);

        //View
        btn_signIn = findViewById(R.id.btn_signIn);
        btn_signUp = findViewById(R.id.btn_signUp);
        edSignInEmail = findViewById(R.id.edSignInEmail);
        edSignInPassword = findViewById(R.id.edSignInPassword);
        //Button action
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(edSignInEmail.getText().toString(),edSignInPassword.getText().toString());
            }
        });
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });


    }

    private void loginUser(String email, String password)
    {

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .loginUser(email,password);


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.code() == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        User u = new User(jsonObject.getInt("id"),
                                jsonObject.getString("unique_id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("lastName"),
                                jsonObject.getString("email"),
                                jsonObject.getInt("phoneNumber"),
                                jsonObject.getString("encrypted_password"),
                                jsonObject.getString("image_url"));
                        // add user to session and move to next activity
                        Session.getInstance().setUser(u);

                        Toast.makeText(SignInActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    }
                    catch (IOException e)
                    {

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });

       /* compositeDisposable.add(myAPI.loginUser(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (s.contains("encrypted_password"))
                        {
                            Toast.makeText(SignInActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignInActivity.this, MainActivity.class));

                        //
                        }
                        else
                            Toast.makeText(SignInActivity.this,""+s,Toast.LENGTH_SHORT).show(); //Show error from API

                    }
                })
        );*/
    }



}
