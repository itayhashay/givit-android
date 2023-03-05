package com.example.fragmenttest;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.fragmenttest.databinding.FragmentEditItemBinding;
import com.example.fragmenttest.model.Item;
import com.example.fragmenttest.model.Model;
import com.example.fragmenttest.model.User;

public class EditItemFragment extends Fragment {

    Item item;
    User user;

    FragmentEditItemBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    Boolean isAvatarSelected = false;

    EditText namePt;
    EditText descriptionPt;
    EditText addressPt;
    ImageView imageIv;

    Button deleteBtn;
    Button editBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if(result!=null) {
                    binding.avatarImageIv.setImageBitmap(result);
                    isAvatarSelected =true;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentEditItemBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        namePt = view.findViewById(R.id.edit_item_name_pt);
        descriptionPt = view.findViewById(R.id.edit_item_desc_pt);
        addressPt = view.findViewById(R.id.edit_item_address_pt);

        deleteBtn = view.findViewById(R.id.delete_item_btn);
        editBtn = view.findViewById(R.id.edit_user_btn);
        imageIv = view.findViewById(R.id.avatar_image_iv);

        // Get the arguments passed to this fragment
        if (getArguments() != null) {
            item = ItemDetailsFragmentArgs.fromBundle(getArguments()).getItem();
            Log.d("TAG", item.userId);
            namePt.setText(item.getName());
            descriptionPt.setText(item.getDescription());
            addressPt.setText(item.getAddress());
        }

        imageIv.setOnClickListener(v -> {
            cameraLauncher.launch(null);
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = namePt.getText().toString();
                String description = descriptionPt.getText().toString();
                String address = addressPt.getText().toString();
                imageIv.setDrawingCacheEnabled(true);
                imageIv.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imageIv.getDrawable()).getBitmap();
                Model.getInstance().editItem(item.getId(), name, description, address, ()->{
                    if(isAvatarSelected){
                        Model.getInstance().uploadImage("item/"+item.getId(), bitmap, url -> {
                            Navigation.findNavController(view).popBackStack();
                        });
                    } else {
                        Navigation.findNavController(view).popBackStack();
                    }
                });
            }
        });

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