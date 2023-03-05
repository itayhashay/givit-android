package com.example.fragmenttest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fragmenttest.model.Item;
import com.example.fragmenttest.model.Model;

public class NewItemFragment extends Fragment {

    EditText nameEt;
    EditText descriptionEt;
    EditText addressEt;
    Button sumbitBtn;
//    EditText lastNameEt;
//    User user;
//    String Password;
    String id;
    String name;
    String description;
    String address;
    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_item, container, false);

        sumbitBtn = view.findViewById(R.id.edit_user_btn);
        nameEt = view.findViewById(R.id.item_title_pt);
        descriptionEt = view.findViewById(R.id.edit_item_desc_pt);
        addressEt = view.findViewById(R.id.edit_item_address_pt);



        sumbitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEt.getText().toString();
                description = descriptionEt.getText().toString();
                address = addressEt.getText().toString();
                if(name.equals("") || name.equals("Title") || description.equals("") || description.equals("Description") || address.equals("") || address.equals("Address")) {
                    Toast.makeText(view.getContext(), "Please Don't Leave Empty Spaces", Toast.LENGTH_SHORT).show();
                }else {
                    Item item = new Item(name,description,address,Model.getInstance().getCurrentUserUID());
                    Model.getInstance().addItem(item, ()->{
                        Navigation.findNavController(view).popBackStack();
                    });
                }
            }
        });

        return view;
    }
}