package com.example.fragmenttest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.fragmenttest.model.Model;

public class PersonalInfoFragment extends Fragment {

    EditText firstNameEt;
    EditText lastNameEt;
    EditText usernameEt;
    EditText phoneEt;
    Button submitBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);

        firstNameEt = view.findViewById(R.id.user_firstname_pt);
        lastNameEt = view.findViewById(R.id.user_lastname_pt);
        usernameEt = view.findViewById(R.id.user_username_pt);
        phoneEt = view.findViewById(R.id.user_phone_pt);
        submitBtn = view.findViewById(R.id.edit_user_btn);

        Model.getInstance().getUserById(Model.getInstance().getCurrentUserUID(), user -> {
            firstNameEt.setText(user.firstName);
            lastNameEt.setText(user.lastName);
            usernameEt.setText(user.username);
            phoneEt.setText(user.phone);
        });

        submitBtn.setOnClickListener(v -> {
            String firstName = firstNameEt.getText().toString();
            String lastName = lastNameEt.getText().toString();
            String username = usernameEt.getText().toString();
            String phone = phoneEt.getText().toString();
            Model.getInstance().editUser(Model.getInstance().getCurrentUserUID(), username, phone, firstName,lastName,() -> {
                Navigation.findNavController(view).popBackStack();
            });
        });
        return view;
    }
}