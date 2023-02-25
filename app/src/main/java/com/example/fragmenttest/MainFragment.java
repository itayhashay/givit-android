package com.example.fragmenttest;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainFragment extends Fragment {
    TextView titleTv;
    String title;

    LoginFragment loginFrag;
    UserSectionFragment userSectionFrag;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        loginFrag = LoginFragment.newInstance();
        userSectionFrag = UserSectionFragment.newInstance();

        Button loginBtn = view.findViewById(R.id.main_login_btn);
        MainFragmentDirections.ActionMainFragmentToLoginFragment action = MainFragmentDirections.actionMainFragmentToLoginFragment("GIVIT");
        loginBtn.setOnClickListener((view1) -> {
            Navigation.findNavController(view).navigate(action);
        });

        TextView signupTv = view.findViewById(R.id.main_signup_tv);
        signupTv.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.signupFragment));

//        Button userBtn = view.findViewById(R.id.main_user_btn);

//        userBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.user));
//        userBtn.setOnClickListener((view1) -> {
//            Navigation.findNavController(view).navigate(R.id.action_global_feedFragment);
//        });


        TextView titleTv = view.findViewById(R.id.add_item_title_tv);
        if (title != null) {
            titleTv.setText(title);
        }
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