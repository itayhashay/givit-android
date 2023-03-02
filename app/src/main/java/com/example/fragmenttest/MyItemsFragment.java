package com.example.fragmenttest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyItemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyItemsFragment extends Fragment {

    List<Item> itemList = new LinkedList<>();
    ItemRecyclerAdapter adapter;
    FragmentMyItemsBinding binding;

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
            itemList = lst;
            adapter.setItemList(itemList);
        });

        RecyclerView itemsList = binding.myItemsList;
        itemsList.setHasFixedSize(true);
        itemsList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ItemRecyclerAdapter(getLayoutInflater(), itemList);
        itemsList.setAdapter(adapter);

        adapter.setOnItemClickListener(new ItemRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Item clickedItem = itemList.get(position);
                Log.d("TAG", clickedItem.name + " clicked");
                MyItemsFragmentDirections.ActionMyItemsFragmentToItemDetailsFragment action = MyItemsFragmentDirections.actionMyItemsFragmentToItemDetailsFragment(clickedItem);
                Navigation.findNavController(view).navigate(action);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.progressBar.setVisibility(View.VISIBLE);
        Model.getInstance().getAllItems((lst) -> {
            itemList = lst;
            adapter.setItemList(itemList);
            binding.progressBar.setVisibility(View.GONE);
        });
    }
}