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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyItemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_my_items, container, false);
        binding = FragmentMyItemsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Model.getInstance().getAllItems((lst)->{
            String userId = Model.getInstance().getCurrentUserUID();
            viewModel.setData(lst.stream().filter(item -> item.userId == userId).collect(Collectors.toList()));
            adapter.setItemList(viewModel.getData());
        });

        RecyclerView itemsList = binding.myItemsList;
        itemsList.setHasFixedSize(true);
        itemsList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ItemRecyclerAdapter(getLayoutInflater(), viewModel.getData());
        itemsList.setAdapter(adapter);

        adapter.setOnItemClickListener(new ItemRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Item clickedItem = viewModel.getData().get(position);
                Log.d("TAG", clickedItem.name + " clicked");
                MyItemsFragmentDirections.ActionMyItemsFragmentToEditItemFragment action = MyItemsFragmentDirections.actionMyItemsFragmentToEditItemFragment(clickedItem);
                Navigation.findNavController(view).navigate(action);
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(MyItemsFragmentViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.progressBar.setVisibility(View.VISIBLE);
        Model.getInstance().getAllItems((lst) -> {
            String userId = Model.getInstance().getCurrentUserUID();
            viewModel.setData(lst.stream().filter(item -> item.userId == userId).collect(Collectors.toList()));
            adapter.setItemList(viewModel.getData());
            binding.progressBar.setVisibility(View.GONE);
        });
    }
}