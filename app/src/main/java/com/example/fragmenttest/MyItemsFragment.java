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

import com.example.fragmenttest.model.Item;
import com.example.fragmenttest.model.Model;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyItemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyItemsFragment extends Fragment {

    List<Item> itemList;

    public static MyItemsFragment newInstance(){
        return new MyItemsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_items, container, false);
        itemList = Model.getInstance().getAllItems();

        RecyclerView itemsList = view.findViewById(R.id.my_items_list);
        itemsList.setHasFixedSize(true);
        itemsList.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemRecyclerAdapter adapter = new ItemRecyclerAdapter(getLayoutInflater(), itemList);
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
}