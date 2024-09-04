package com.envy.clothesmarket;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.envy.clothesmarket.models.Clothes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyListingsActivity extends AppCompatActivity {

    ArrayList<Clothes> data;
    RecyclerView recyclerView;
    ClothesListingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_listings);


        //Recycler View Setup
        data=new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_myListings);
        adapter = new ClothesListingAdapter(this,data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        FirebaseFirestore.getInstance().collection("Clothes")
                .whereEqualTo("userId",FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        data.clear();
                        for (QueryDocumentSnapshot q : queryDocumentSnapshots){
                            data.add(q.toObject(Clothes.class));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MyListingsActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}