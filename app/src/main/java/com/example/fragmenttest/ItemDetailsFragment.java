package com.example.fragmenttest;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragmenttest.databinding.FragmentItemCardBinding;
import com.example.fragmenttest.model.Item;
import com.example.fragmenttest.model.Model;
import com.example.fragmenttest.model.User;
import com.squareup.picasso.Picasso;

import java.util.stream.Collectors;

public class ItemDetailsFragment extends Fragment {

    Item item;
    String description;
    User user;

    ItemDetailsFragmentViewModel viewModel;

    TextView nameTv;
    TextView usernameTv;
    ImageView imageIv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_item_details, container, false);

        nameTv = view.findViewById(R.id.item_name_value);
        usernameTv = view.findViewById(R.id.item_username_value);
        imageIv = view.findViewById(R.id.item_imgae_iv);

        // Get the arguments passed to this fragment
        if (getArguments() != null) {
            item = ItemDetailsFragmentArgs.fromBundle(getArguments()).getItem();
            Log.d("TAG", item.userId);
            nameTv.setText(item.getName());
            if(viewModel.getUsersList().getValue() == null) {
                user = new User("", "", "", "" , "","", "");
            }else {
                user = viewModel.getUsersList().getValue().stream().filter(user1 -> {
                    return user1.id.equals(item.userId);
                }).collect(Collectors.toList()).get(0);
            }
            usernameTv.setText(user.username);
            if(item.getImageUrl() != null) {
                Picasso.get().load(item.getImageUrl()).placeholder(R.drawable.item).into(imageIv);
            }else {
                imageIv.setImageResource(R.drawable.item);
            }
        }


        viewModel.getUsersList().observe(getViewLifecycleOwner(),users -> {
            User user = users.stream().filter(user1 -> user1.id.equals(item.userId)).collect(Collectors.toList()).get(0);
            usernameTv.setText(user.username);
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(ItemDetailsFragmentViewModel.class);
    }

    void reloadData() {
        Model.getInstance().refreshAllUsers();
    }
}