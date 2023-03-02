package com.example.fragmenttest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragmenttest.databinding.FragmentFeedBinding;
import com.example.fragmenttest.model.Item;
import com.example.fragmenttest.model.Model;

import java.util.LinkedList;
import java.util.List;

public class FeedFragment extends Fragment {

    List<Item> itemList = new LinkedList<>();
    ItemRecyclerAdapter adapter;
    FragmentFeedBinding binding;

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        binding = FragmentFeedBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        reloadData();
        RecyclerView itemsList = binding.itemListFeed;
        itemsList.setHasFixedSize(true);
        itemsList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ItemRecyclerAdapter(getLayoutInflater(), itemList);
        itemsList.setAdapter(adapter);

        adapter.setOnItemClickListener(new ItemRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Item clickedItem = itemList.get(position);
                Log.d("TAG", clickedItem.name + " clicked");
                FeedFragmentDirections.ActionFeedFragmentToItemDetailsFragment action = FeedFragmentDirections.actionFeedFragmentToItemDetailsFragment(clickedItem);
                Navigation.findNavController(view).navigate(action);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadData();
    }

    void reloadData() {
        binding.progressBar2.setVisibility(View.VISIBLE);
        Model.getInstance().getAllItems((lst) -> {
            itemList = lst;
            adapter.setItemList(itemList);
            binding.progressBar2.setVisibility(View.GONE);
        });
    }
}