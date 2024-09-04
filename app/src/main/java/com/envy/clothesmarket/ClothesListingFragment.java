package com.envy.clothesmarket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.envy.clothesmarket.models.Clothes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ClothesListingFragment extends Fragment {

    public ClothesListingFragment(){

    }


    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionreference = db.collection("Clothes");
    ArrayList<Clothes> data;
    RecyclerView recyclerView;
    ClothesListingAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clothlisting,container, false);
        //Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        //Widgets

        data = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);

        collectionreference
                .orderBy("timeAdded", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                    Clothes clothes = snapshot.toObject(Clothes.class);
                    data.add(clothes);

                }

                adapter = new ClothesListingAdapter(getActivity(), data);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(e -> Toast.makeText(getActivity(), "Something went wrong :(", Toast.LENGTH_SHORT).show());

        return view;
    }

}