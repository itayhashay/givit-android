package com.example.fragmenttest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fragmenttest.model.User;


public class SignupFragment extends Fragment {

    EditText usernameEt;
    EditText passwordEt;
    EditText firstNameEt;
    EditText lastNameEt;
    EditText phoneEt;
    EditText emailEt;
    Button submitBtn;
    User user;
    String Password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        usernameEt = view.findViewById(R.id.signup_username_pt);
        emailEt = view.findViewById(R.id.signup_email_pt);
        phoneEt = view.findViewById(R.id.signup_phone_pt);
        firstNameEt = view.findViewById(R.id.signup_firstname_pt);
        lastNameEt = view.findViewById(R.id.signup_lastname_pt);
        passwordEt = view.findViewById(R.id.signup_password_pt);
        submitBtn = view.findViewById(R.id.login_submit_btn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User("1", usernameEt.getText().toString(), phoneEt.getText().toString(), firstNameEt.getText().toString(), lastNameEt.getText().toString(),emailEt.getText().toString());
                Log.d("TAG", user.toString() + " " + passwordEt.getText().toString());
                if(user.firstName.equals("") || user.firstName.equals("First Name") || user.lastName.equals("") || user.lastName.equals("Last Name") || user.username.equals("") || user.username.equals("User Name") || user.phone.equals("") || user.phone.equals("Phone") || user.email.equals("") || user.email.equals("Email") || passwordEt.getText().toString().equals("") || passwordEt.getText().toString().equals("Password")) {
                    Toast.makeText(view.getContext(), "Please Don't Leave Empty Spaces", Toast.LENGTH_SHORT).show();
                }else {
                    Navigation.findNavController(view).navigate(SignupFragmentDirections.actionGlobalFeedFragment());
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity)getActivity()).getSupportActionBar().show();
    }
}