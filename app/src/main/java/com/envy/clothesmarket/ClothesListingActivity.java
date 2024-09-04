package com.envy.clothesmarket;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

//Listing Activity
public class ClothesListingActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionreference = db.collection("Clothes");
    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_listing);
        //Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        //Widgets

        //ViewPager Setup
        viewPager2 = findViewById(R.id.viewPager2);
        ClothesListingFragmentAdapter adapter = new ClothesListingFragmentAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(adapter);
        viewPager2.setUserInputEnabled(false);

        //Bottom Menu Setup
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.menu_profile) {
                    viewPager2.setCurrentItem(3);
                    return true;
                }
                if (menuItem.getItemId() == R.id.menu_add) {
                    viewPager2.setCurrentItem(1);
                    return true;
                }
                if (menuItem.getItemId() == R.id.menu_home) {
                    viewPager2.setCurrentItem(0);
                    return true;
                }
                if(menuItem.getItemId() == R.id.menu_messages){
                    viewPager2.setCurrentItem(2);
                    return true;
                }
                return false;
            }
        });

    }
}