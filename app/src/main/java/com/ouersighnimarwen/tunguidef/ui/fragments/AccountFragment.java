package com.ouersighnimarwen.tunguidef.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ouersighnimarwen.tunguidef.R;
import com.ouersighnimarwen.tunguidef.Session;


public class AccountFragment extends Fragment {


    View view;
    ImageView photo;
    private Context context;
    TextView nom,prenom,changeImage,mail,phone;

    public AccountFragment(Context context) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        nom = view.findViewById(R.id.lastName);
        prenom = view.findViewById(R.id.firstName);
        mail = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phoneNumber);

        nom.setText(Session.getInstance().getUser().getLastName());
        prenom.setText(Session.getInstance().getUser().getName());
        mail.setText(Session.getInstance().getUser().getEmail());
        phone.setText(Session.getInstance().getUser().getPhoneNumber());

        return view;
    }

}
