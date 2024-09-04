package com.envy.clothesmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class ProfileFragment extends Fragment {

    public ProfileFragment() {
    }

    Button logoutBTN;
    TextView profileTV;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        logoutBTN = view.findViewById(R.id.profile_logout);
        profileTV = view.findViewById(R.id.profile_text);
        Button myListingsBTN = view.findViewById(R.id.profile_myListings);

        //Display Name
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        if (user != null) {
            if(user.getDisplayName().isEmpty()){
                profileTV.setText("Hi " + user.getEmail());
            }
            else{
                profileTV.setText("Hi "+ user.getDisplayName());
            }
        }

        //Logout Listener
        logoutBTN.setOnClickListener(v -> {
            if (user != null) {
                firebaseAuth.signOut();
                Intent i = new Intent(getActivity(), MainLoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        myListingsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),MyListingsActivity.class));
            }
        });


        return view;
    }
}
