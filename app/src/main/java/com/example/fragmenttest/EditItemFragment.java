package com.example.fragmenttest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fragmenttest.model.Item;
import com.example.fragmenttest.model.Model;
import com.example.fragmenttest.model.User;

public class EditItemFragment extends Fragment {

    Item item;
    User user;

    EditText namePt;
    EditText descriptionPt;
    EditText addressPt;

    Button deleteBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_item, container, false);

        namePt = view.findViewById(R.id.edit_item_name_pt);
        descriptionPt = view.findViewById(R.id.edit_item_desc_pt);
        addressPt = view.findViewById(R.id.edit_item_address_pt);

        deleteBtn = view.findViewById(R.id.delete_item_btn);

        // Get the arguments passed to this fragment
        if (getArguments() != null) {
            item = ItemDetailsFragmentArgs.fromBundle(getArguments()).getItem();
            Log.d("TAG", item.userId);
            namePt.setText(item.getName());
            descriptionPt.setText(item.getDescription());
            addressPt.setText(item.getAddress());
        }

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getInstance().deleteItem(item, ()->{
                    Navigation.findNavController(view).popBackStack();
                });
            }
        });

        return view;
    }
}