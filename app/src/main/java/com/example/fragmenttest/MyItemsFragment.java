package com.example.fragmenttest;

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

import com.example.fragmenttest.databinding.FragmentFeedBinding;
import com.example.fragmenttest.databinding.FragmentItemCardBinding;
import com.example.fragmenttest.databinding.FragmentMyItemsBinding;
import com.example.fragmenttest.model.Item;
import com.example.fragmenttest.model.Model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MyItemsFragment extends Fragment {

    ItemRecyclerAdapter adapter;
    FragmentMyItemsBinding binding;
    MyItemsFragmentViewModel viewModel;

    public static MyItemsFragment newInstance(){
        return new MyItemsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyItemsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        RecyclerView itemsList = binding.myItemsList;
        itemsList.setHasFixedSize(true);
        itemsList.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ItemRecyclerAdapter(getLayoutInflater(), viewModel.getData().getValue()!=null ?  viewModel.getData().getValue().stream().filter(item -> item.getUserId().equals(Model.getInstance()
                .getCurrentUserUID())).collect(Collectors.toList()) : new LinkedList<>());
        itemsList.setAdapter(adapter);

        adapter.setOnItemClickListener(new ItemRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Item clickedItem = viewModel.getData().getValue().get(position);
                Log.d("TAG", clickedItem.name + " clicked");
                MyItemsFragmentDirections.ActionMyItemsFragmentToEditItemFragment action = MyItemsFragmentDirections.actionMyItemsFragmentToEditItemFragment(clickedItem);
                Navigation.findNavController(view).navigate(action);
            }
        });
        binding.progressBar.setVisibility(View.GONE);

        viewModel.getData().observe(getViewLifecycleOwner(),items -> {
            if(items == null) {
                items = new LinkedList<>();
            }
            adapter.setItemList(items.stream().filter(item -> {
                    return item.getUserId().equals(Model.getInstance().getCurrentUserUID());}
            ).collect(Collectors.toList()));

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
        viewModel = new ViewModelProvider(this).get(MyItemsFragmentViewModel.class);
    }

    void reloadData() {
        Model.getInstance().refreshAllItems();
    }
}