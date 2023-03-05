package com.example.fragmenttest;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fragmenttest.databinding.FragmentEditItemBinding;
import com.example.fragmenttest.databinding.FragmentNewItemBinding;
import com.example.fragmenttest.model.Item;
import com.example.fragmenttest.model.Model;

public class NewItemFragment extends Fragment {

    FragmentNewItemBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    Boolean isAvatarSelected = false;

    EditText nameEt;
    EditText descriptionEt;
    EditText addressEt;
    Button sumbitBtn;
    Button cameraBtn;
    ImageView imageIv;
//    EditText lastNameEt;
//    User user;
//    String Password;
    String id;
    String name;
    String description;
    String address;
    String userId;

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
        // Inflate the layout for this fragment
        binding = FragmentNewItemBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        imageIv = binding.avatarImageIv;
        sumbitBtn = view.findViewById(R.id.edit_user_btn);
        nameEt = view.findViewById(R.id.item_title_pt);
        descriptionEt = view.findViewById(R.id.edit_item_desc_pt);
        addressEt = view.findViewById(R.id.edit_item_address_pt);
//        cameraBtn = view.findViewById(R.id.newitem_add_photo_btn);

        imageIv.setOnClickListener(v -> {
            cameraLauncher.launch(null);
        });



        sumbitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEt.getText().toString();
                description = descriptionEt.getText().toString();
                address = addressEt.getText().toString();
                imageIv.setDrawingCacheEnabled(true);
                imageIv.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imageIv.getDrawable()).getBitmap();
                if(name.equals("") || name.equals("Title") || description.equals("") || description.equals("Description") || address.equals("") || address.equals("Address")) {
                    Toast.makeText(view.getContext(), "Please Don't Leave Empty Spaces", Toast.LENGTH_SHORT).show();
                }else {
                    Item item = new Item(name,description,address,Model.getInstance().getCurrentUserUID());
                    Model.getInstance().addItem(item, ()->{
                        if(isAvatarSelected){
                            Model.getInstance().uploadImage("item/"+item.getId(),bitmap,url -> {
                                Navigation.findNavController(view).popBackStack();
                            });
                        }else {
                            Navigation.findNavController(view).popBackStack();
                        }

                    });
                }
            }
        });

        return view;
    }
}