package com.example.fragmenttest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserSectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserSectionFragment extends Fragment {
    NavController userNavController;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserSectionFragment() {
        // Required empty public constructor
    }

    public static UserSectionFragment newInstance() {
        UserSectionFragment fragment = new UserSectionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserSectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserSectionFragment newInstance(String param1, String param2) {
        UserSectionFragment fragment = new UserSectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavHostFragment navHostFragment = (NavHostFragment)getParentFragmentManager().findFragmentById(R.id.user_section_navhost);
        userNavController = navHostFragment.getNavController();

        BottomNavigationView userSectionNavView = getView().findViewById(R.id.user_section_navbar);
        NavigationUI.setupWithNavController(userSectionNavView, userNavController);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_section, container, false);
    }
}