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

import com.example.fragmenttest.model.Model;
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
//                user = new User(usernameEt.getText().toString(), )
                String firstName = firstNameEt.getText().toString();
                String lastName = lastNameEt.getText().toString();
                String username = usernameEt.getText().toString();
                String phone = phoneEt.getText().toString();
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();
                if(firstName.equals("") || firstName.equals("First Name") || lastName.equals("") || lastName.equals("Last Name") || username.equals("") || username.equals("User Name") || phone.equals("") || phone.equals("Phone") || email.equals("") || email.equals("Email") || password.equals("") || password.equals("Password")) {
                    Toast.makeText(view.getContext(), "Please Don't Leave Empty Spaces", Toast.LENGTH_SHORT).show();
                }else {
                    Model.getInstance().signUp(email, password, (firebaseUser, ex) -> {
                        if(firebaseUser != null) {
                            Model.getInstance().addUser(new User(Model.getInstance().getCurrentUserUID(), username, phone,firstName,lastName,email), () -> {
                                Navigation.findNavController(view).navigate(SignupFragmentDirections.actionGlobalFeedFragment());
                            });
//                            Navigation.findNavController(view).navigate(SignupFragmentDirections.actionGlobalFeedFragment());
                        }else {
                            Log.d("TAG", ex.getMessage());
                            Toast.makeText(view.getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
//                    Navigation.findNavController(view).navigate(SignupFragmentDirections.actionGlobalFeedFragment());
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