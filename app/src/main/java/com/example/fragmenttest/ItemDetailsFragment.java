package com.example.fragmenttest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragmenttest.databinding.FragmentItemDetailsBinding;
import com.example.fragmenttest.model.Item;
import com.example.fragmenttest.model.Model;
import com.example.fragmenttest.model.User;
import com.example.fragmenttest.model.retrofit.Joke;
import com.example.fragmenttest.model.retrofit.JokeModel;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;
import java.util.stream.Collectors;

public class ItemDetailsFragment extends Fragment {

    Item item;
    User user;

    ItemDetailsFragmentViewModel viewModel;


    TextView titleTv;
    TextView descTv;
    TextView addressTv;
    ImageView itemIv;

    TextView firstNameTv;
    TextView lastNameTv;
    TextView phoneNumberTv;
    ImageView userIv;

    SwipeRefreshLayout swipeRefresh;

    Button hideShowBtn;
    String BUTTON_SHOW_TEXT = "SHOW PHONE NUMBER";
    String BUTTON_SHOW_COLOR = "#4CAF50";
    String BUTTON_HIDE_TEXT = "HIDE PHONE NUMBER";
    String BUTTON_HIDE_COLOR = "#C32A1F";

    TextView jokeSetupTv;
    TextView jokePunchTv;

    ConstraintLayout userSectionCl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_item_details, container, false);

        titleTv = view.findViewById(R.id.item_details_title_tv);
        descTv = view.findViewById(R.id.item_details_desc_tv);
        addressTv = view.findViewById(R.id.item_details_address_value_tv);
        itemIv = view.findViewById(R.id.item_details_image_iv);

        swipeRefresh = view.findViewById(R.id.swipeRefresh);

        firstNameTv = view.findViewById(R.id.user_first_name_tv);
        lastNameTv = view.findViewById(R.id.user_last_name_tv);
        phoneNumberTv = view.findViewById(R.id.user_phone_number_tv);
        userIv = view.findViewById(R.id.details_user_avatar_image_iv);

        hideShowBtn = view.findViewById(R.id.show_hide_user_btn);

        userSectionCl = view.findViewById(R.id.details_user_Cl);
        userSectionCl.setVisibility(View.INVISIBLE);

        jokeSetupTv = view.findViewById(R.id.joke_setup_tv);
        jokePunchTv = view.findViewById(R.id.joke_punch_tv);

        // Get the arguments passed to this fragment
        if (getArguments() != null) {
            item = ItemDetailsFragmentArgs.fromBundle(getArguments()).getItem();
            Log.d("TAG", item.userId);
            titleTv.setText(item.getName());
            descTv.setText(item.getDescription());
            addressTv.setText(item.getAddress());
            if(item.getImageUrl() != null) {
                Picasso.get().load(item.getImageUrl()).placeholder(R.drawable.item).into(itemIv);
            }else {
                itemIv.setImageResource(R.drawable.item);
            }
        }

        viewModel.getItemsList().observe(getViewLifecycleOwner(),items -> {
            List<Item> itemsLst = items.stream().filter(item1 -> item1.id.equals(item.id)).collect(Collectors.toList());
            if(itemsLst.size() == 0){
                Navigation.findNavController(view).popBackStack();
            } else {
                item = itemsLst.get(0);
                titleTv.setText(item.getName());
                descTv.setText(item.getDescription());
                addressTv.setText(item.getAddress());
            }
            if(item.getImageUrl() != null) {
                Picasso.get().load(item.getImageUrl()).placeholder(R.drawable.item).into(itemIv);
            }else {
                itemIv.setImageResource(R.drawable.item);
            }
        });


        viewModel.getUsersList().observe(getViewLifecycleOwner(),users -> {
            User user = users.stream().filter(user1 -> user1.id.equals(item.userId)).collect(Collectors.toList()).get(0);
            firstNameTv.setText(user.getFirstName());
            lastNameTv.setText(user.getLastName());
            phoneNumberTv.setText(user.getPhone());
            if(user.getImageUrl() != null) {
                Picasso.get().load(user.getImageUrl()).placeholder(R.drawable.user).into(userIv);
            }else {
                itemIv.setImageResource(R.drawable.item);
            }
        });

        hideShowBtn.setOnClickListener(v -> {
            if (userSectionCl.getVisibility() == View.VISIBLE) {
                userSectionCl.setVisibility(View.INVISIBLE);
                hideShowBtn.setText(BUTTON_SHOW_TEXT);
                hideShowBtn.setBackgroundColor(Color.parseColor(BUTTON_SHOW_COLOR));
            } else {
                userSectionCl.setVisibility(View.VISIBLE);
                hideShowBtn.setText(BUTTON_HIDE_TEXT);
                hideShowBtn.setBackgroundColor(Color.parseColor(BUTTON_HIDE_COLOR));
            }
        });

        LiveData<Joke> joke = JokeModel.instance.getRandomJoke();
        joke.observe(getViewLifecycleOwner(),j -> {
            if(joke != null) {
                jokeSetupTv.setText(j.getSetup());
                jokePunchTv.setText(j.getPunchline());
            }
        });

        Model.getInstance().EventItemsListLoadingState.observe(getViewLifecycleOwner(), loadingStatus -> {
            swipeRefresh.setRefreshing(loadingStatus == Model.LoadingStatus.LOADING);
        });

        swipeRefresh.setOnRefreshListener(() -> {
            reloadData();
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
        Model.getInstance().refreshAllItems();
    }
}