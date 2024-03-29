package com.example.fragmenttest.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fragmenttest.R;
import com.example.fragmenttest.main.MainActivity;
import com.example.fragmenttest.model.Model;

public class LoginFragment extends Fragment {
    String title;
    Button submitBtn;
    Button signupBtn;
    String password;
    String username;

    public LoginFragment() {}

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().hide();
        title = LoginFragmentArgs.fromBundle(getArguments()).getAppTitle();
        submitBtn = view.findViewById(R.id.login_submit_btn);
        signupBtn = view.findViewById(R.id.login_signup_btn);
        submitBtn.setOnClickListener((view1) -> {
            username = ((EditText)(view.findViewById(R.id.login_username_pt))).getText().toString();
            password = ((EditText)(view.findViewById(R.id.login_password_pt))).getText().toString();
            if(username.equals("") || username.equals("User Name") || password.equals("") || password.equals("Password")) {
                Toast.makeText(view.getContext(), "Please Don't Leave Empty Spaces", Toast.LENGTH_SHORT).show();
            }else {
                Model.getInstance().signIn(username,password,(user,ex)-> {
                    if(user != null) {
                        Navigation.findNavController(view).navigate(LoginFragmentDirections.actionGlobalFeedFragment());
                    }else {
                        Toast.makeText(view.getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        signupBtn.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment());
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
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