package com.example.fragmenttest.feed;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragmenttest.recycleAdapter.ItemRecyclerAdapter;
import com.example.fragmenttest.databinding.FragmentFeedBinding;
import com.example.fragmenttest.model.Item;
import com.example.fragmenttest.model.Model;

public class FeedFragment extends Fragment {

    ItemRecyclerAdapter adapter;
    FragmentFeedBinding binding;
    FeedFragmentViewModel viewModel;

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFeedBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        RecyclerView itemsList = binding.itemListFeed;
        itemsList.setHasFixedSize(true);
        itemsList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ItemRecyclerAdapter(getLayoutInflater(), viewModel.getData().getValue());
        itemsList.setAdapter(adapter);

        adapter.setOnItemClickListener(new ItemRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Item clickedItem = viewModel.getData().getValue().get(position);
                Log.d("TAG", clickedItem.name + " clicked");
                FeedFragmentDirections.ActionFeedFragmentToItemDetailsFragment action = FeedFragmentDirections.actionFeedFragmentToItemDetailsFragment(clickedItem);
                Navigation.findNavController(view).navigate(action);
            }
        });
        binding.progressBar2.setVisibility(View.GONE);

        viewModel.getData().observe(getViewLifecycleOwner(),items -> {
            adapter.setItemList(items);

        });

        Model.getInstance().EventItemsListLoadingState.observe(getViewLifecycleOwner(), loadingStatus -> {
            binding.swipeRefresh.setRefreshing(loadingStatus == Model.LoadingStatus.LOADING);
        });

        binding.swipeRefresh.setOnRefreshListener(() -> {
            reloadData();
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(FeedFragmentViewModel.class);
    }

    void reloadData() {
        Model.getInstance().refreshAllItems();
        Model.getInstance().refreshAllUsers();
    }
}