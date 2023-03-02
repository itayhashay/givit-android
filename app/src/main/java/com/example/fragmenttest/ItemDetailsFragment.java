package com.example.fragmenttest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fragmenttest.databinding.FragmentItemCardBinding;
import com.example.fragmenttest.model.Item;
import com.example.fragmenttest.model.User;

public class ItemDetailsFragment extends Fragment {

    Item item;
    String description;
    User user;

    TextView nameTv;
    TextView usernameTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_item_details, container, false);

        nameTv = view.findViewById(R.id.item_name_value);
        usernameTv = view.findViewById(R.id.item_username_value);

        // Get the arguments passed to this fragment
        if (getArguments() != null) {
            item = ItemDetailsFragmentArgs.fromBundle(getArguments()).getItem();
            Log.d("TAG", item.userId);
            nameTv.setText(item.getName());
            usernameTv.setText(item.getUserId());
        }
        return view;
    }
}