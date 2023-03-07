package com.example.fragmenttest;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fragmenttest.model.Model;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainFragment extends Fragment {
    TextView titleTv;
    String title;

    LoginFragment loginFrag;

    public static MainFragment newInstance(String title) {
        MainFragment frag = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null) {
            this.title = bundle.getString("TITLE");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        loginFrag = LoginFragment.newInstance();

        Button loginBtn = view.findViewById(R.id.main_login_btn);
        MainFragmentDirections.ActionMainFragmentToLoginFragment action = MainFragmentDirections.actionMainFragmentToLoginFragment("GIVIT");
        loginBtn.setOnClickListener((view1) -> {
            Navigation.findNavController(view).navigate(action);
        });

        TextView signupTv = view.findViewById(R.id.main_signup_tv);
        signupTv.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.signupFragment));

        Model.getInstance().getUserById("1", user -> {
            if(Model.getInstance().isSignedIn()) {
                Navigation.findNavController(getView()).navigate(MainFragmentDirections.actionMainFragmentToFeedFragment());
            }
        });

        TextView titleTv = view.findViewById(R.id.personal_info_title_tv);
        if (title != null) {
            titleTv.setText(title);
        }
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

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