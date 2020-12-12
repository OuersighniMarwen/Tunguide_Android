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
import com.ouersighnimarwen.tunguidef.Retrofit.RetrofitClient;

import java.io.IOException;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity
{
    INodeJs myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    Button btn_signUp;
    Button btn_signUpCancel;
    EditText edSignUpLastName;
    EditText edSignUpFirstName;
    EditText edSignUpEmail;
    EditText edSignUpPhoneNumber;
    EditText edSignUpPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btn_signUp = findViewById(R.id.btn_signUp);
        btn_signUpCancel = findViewById(R.id.btn_signUpCancel);
        edSignUpFirstName = findViewById(R.id.edSignUpFirstName);
        edSignUpLastName=findViewById(R.id.edSignUpLastName);
        edSignUpEmail = findViewById(R.id.edSignUpEmail);
        edSignUpPhoneNumber = findViewById(R.id.edSignUpPhoneNumber);
        edSignUpPassword = findViewById(R.id.edSignUpPassword);
        compositeDisposable = new CompositeDisposable();

        //Init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJs.class);

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddUsers(edSignUpFirstName.getText().toString(),
                         edSignUpLastName.getText().toString(),
                         edSignUpEmail.getText().toString(),
                         edSignUpPhoneNumber.getText().toString(),
                         edSignUpPassword.getText().toString());
                //Toast.makeText(AddHotelActivity.this,hotel_price.getText().toString(),Toast.LENGTH_SHORT).show();
               startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });

        btn_signUpCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));

            }
        });

    }


    public void AddUsers(final String name,final String lastName,final String email,final String phoneNumber,final String password)
    {
        Call<ResponseBody> call = com.ouersighnimarwen.tunguidef.entity.RetrofitClient
                .getInstance()
                .getApi()
                .registerUser(name,lastName,email,phoneNumber,password);


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {


                    Toast.makeText(SignUpActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
                }
                catch (IOException e)
                {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        /*compositeDisposable.add(myAPI.registerUser(name,lastName,email,phoneNumber,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(SignUpActivity.this,""+s,Toast.LENGTH_SHORT).show();
                    }
                })
        );*/
    }


}
