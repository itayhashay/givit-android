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
import android.widget.Toast;

import com.example.fragmenttest.databinding.FragmentNewItemBinding;
import com.example.fragmenttest.databinding.FragmentSignupBinding;
import com.example.fragmenttest.model.Model;
import com.example.fragmenttest.model.User;


public class SignupFragment extends Fragment {

    FragmentSignupBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    Boolean isAvatarSelected = false;

    EditText usernameEt;
    EditText passwordEt;
    EditText firstNameEt;
    EditText lastNameEt;
    EditText phoneEt;
    EditText emailEt;
    Button submitBtn;
    ImageView imageIv;
    User user;
    String Password;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if(result!=null) {
                    binding.avatarImgSignup.setImageBitmap(result);
                    isAvatarSelected =true;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        usernameEt = view.findViewById(R.id.signup_username_pt);
        emailEt = view.findViewById(R.id.signup_email_pt);
        phoneEt = view.findViewById(R.id.signup_phone_pt);
        firstNameEt = view.findViewById(R.id.signup_firstname_pt);
        lastNameEt = view.findViewById(R.id.signup_lastname_pt);
        passwordEt = view.findViewById(R.id.signup_password_pt);
        submitBtn = view.findViewById(R.id.login_submit_btn);
        imageIv =binding.avatarImgSignup;

        imageIv.setOnClickListener(v -> {
            cameraLauncher.launch(null);
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEt.getText().toString();
                String lastName = lastNameEt.getText().toString();
                String username = usernameEt.getText().toString();
                String phone = phoneEt.getText().toString();
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();
                imageIv.setDrawingCacheEnabled(true);
                imageIv.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imageIv.getDrawable()).getBitmap();
                if(firstName.equals("") || firstName.equals("First Name") || lastName.equals("") || lastName.equals("Last Name") || username.equals("") || username.equals("User Name") || phone.equals("") || phone.equals("Phone") || email.equals("") || email.equals("Email") || password.equals("") || password.equals("Password")) {
                    Toast.makeText(view.getContext(), "Please Don't Leave Empty Spaces", Toast.LENGTH_SHORT).show();
                }else {
                    Model.getInstance().signUp(email, password, (firebaseUser, ex) -> {
                        if(firebaseUser != null) {
                            Model.getInstance().addUser(new User(Model.getInstance().getCurrentUserUID(), username, phone,firstName,lastName,email), () -> {
                                if(isAvatarSelected) {
                                    Model.getInstance().uploadImage("user/"+Model.getInstance().getCurrentUserUID(), bitmap, url -> {
                                        Navigation.findNavController(view).navigate(SignupFragmentDirections.actionGlobalFeedFragment());
                                    });
                                }else {
                                    Navigation.findNavController(view).navigate(SignupFragmentDirections.actionGlobalFeedFragment());
                                }
                            });
                        }else {
                            Log.d("TAG", ex.getMessage());
                            Toast.makeText(view.getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
//                    Navigation.findNavController(view).navigate(SignupFragmentDirections.actionGlobalFeedFragment());
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity)getActivity()).getSupportActionBar().show();
    }
}