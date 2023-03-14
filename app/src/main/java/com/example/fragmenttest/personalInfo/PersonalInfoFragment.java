package com.example.fragmenttest.personalInfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.fragmenttest.R;
import com.example.fragmenttest.databinding.FragmentPersonalInfoBinding;
import com.example.fragmenttest.model.Model;
import com.squareup.picasso.Picasso;

public class PersonalInfoFragment extends Fragment {

    EditText firstNameEt;
    EditText lastNameEt;
    EditText usernameEt;
    EditText phoneEt;
    Button submitBtn;
    Button logoutBtn;
    ImageView imageIv;
    String imageUrl;

    PersonalInfoFragmentViewModel viewModel;

    FragmentPersonalInfoBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    Boolean isAvatarSelected = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if(result!=null) {
                    binding.personalAvatarImageIv.setImageBitmap(result);
                    isAvatarSelected =true;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         binding = FragmentPersonalInfoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        firstNameEt = view.findViewById(R.id.user_firstname_pt);
        lastNameEt = view.findViewById(R.id.user_lastname_pt);
        usernameEt = view.findViewById(R.id.user_username_pt);
        phoneEt = view.findViewById(R.id.user_phone_pt);
        submitBtn = view.findViewById(R.id.edit_user_btn);
        imageIv =view.findViewById(R.id.personal_avatar_image_iv);
        logoutBtn = binding.logoutUserBtn;

        imageIv.setOnClickListener(v -> {
            cameraLauncher.launch(null);
        });

        Model.getInstance().getUserById(Model.getInstance().getCurrentUserUID(), userFirebase -> {
            viewModel.setUser(userFirebase);
            firstNameEt.setText(viewModel.getUser().getFirstName());
            lastNameEt.setText(viewModel.getUser().getLastName());
            usernameEt.setText(viewModel.getUser().getUsername());
            phoneEt.setText(viewModel.getUser().getPhone());
            if(userFirebase.getImageUrl()!=null)  {
                imageUrl = userFirebase.getImageUrl();
                Picasso.get().load(imageUrl).placeholder(R.drawable.user).into(imageIv);
            }else {
                imageIv.setImageResource(R.drawable.user);
            }
        });

        submitBtn.setOnClickListener(v -> {
            String firstName = firstNameEt.getText().toString();
            String lastName = lastNameEt.getText().toString();
            String username = usernameEt.getText().toString();
            String phone = phoneEt.getText().toString();
            imageIv.setDrawingCacheEnabled(true);
            imageIv.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) imageIv.getDrawable()).getBitmap();

            if(isAvatarSelected) {
                Model.getInstance().uploadImage("user/" + Model.getInstance().getCurrentUserUID(), bitmap, url -> {
                    Model.getInstance().editUser(Model.getInstance().getCurrentUserUID(), username, phone, firstName,lastName,url,() -> {
                        Navigation.findNavController(view).popBackStack();
                    });
                });
            }else {
                Model.getInstance().editUser(Model.getInstance().getCurrentUserUID(), username, phone, firstName,lastName,imageUrl,() -> {
                    Navigation.findNavController(view).popBackStack();
                });
            }
        });

        logoutBtn.setOnClickListener(v -> {
            Model.getInstance().signOut(() -> {
                Navigation.findNavController(view).navigate(PersonalInfoFragmentDirections.actionPersonalInfoFragmentToMainFragment());
            });
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(PersonalInfoFragmentViewModel.class);
    }
}