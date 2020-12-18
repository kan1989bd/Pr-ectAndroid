package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.loginandregister.EditProfile;
import com.example.loginandregister.MainActivity;
import com.example.loginandregister.MainScreen;
import com.example.loginandregister.R;
import com.example.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainProfileFragment extends Fragment {
    Button btnLogout;
    TextView txtUserName;
    TextView txtInfoName;
    TextView txtInfoEmail;
    TextView txtUserSetting;
    ImageView imageAvatar;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile_main,container,false);
        btnLogout=view.findViewById(R.id.btn_profile_logout);
        txtUserName=view.findViewById(R.id.txt_profile_user_name);
        txtInfoName=view.findViewById(R.id.txt_profile_infor_nam);
        txtInfoEmail=view.findViewById(R.id.txt_profile_infor_email);
        txtUserSetting=view.findViewById(R.id.txt_setting_account);
        imageAvatar=view.findViewById(R.id.img_profile_avatar);

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("User").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                assert user!=null;
                txtUserName.setText(user.getUserName());
                txtInfoName.setText(user.getUserName());
                txtInfoEmail.setText(firebaseUser.getEmail());
                String imgUrl=user.getImgUrl();

                if(imgUrl!="default"){
                    Picasso.get().load(imgUrl).into(imageAvatar);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });

        txtUserSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),EditProfile.class));
            }
        });


        return view;
    }

}
