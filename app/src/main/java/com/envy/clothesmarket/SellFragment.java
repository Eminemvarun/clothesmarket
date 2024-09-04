package com.envy.clothesmarket;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_MEDIA_IMAGES;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.envy.clothesmarket.databinding.FragmentSellBinding;
import com.envy.clothesmarket.models.Clothes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Document;


public class SellFragment extends Fragment {

    public SellFragment() {
        // Required empty public constructor
    }

    int PICK_FROM_GALLERY;
    Uri imageUri;
    ActivityResultLauncher<String> activityResultLauncher;
    FragmentSellBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String currentUserId;
    String currentUserName;
    FirebaseAuth.AuthStateListener authStateListener;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("Clothes");
    StorageReference storageReference;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sell,container, false);
        //Storage

        storageReference =FirebaseStorage.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser!=null){
            currentUserId= firebaseUser.getUid();
            currentUserName = firebaseUser.getDisplayName();
        }

        binding.postProgressBar.setVisibility(View.INVISIBLE);
        binding.postSaveJournalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveClothes();
            }
        });




        binding.postCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                        if (ActivityCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(getActivity(),READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED)
                        {
                            //None of the permission is granted:
                            Toast.makeText(getActivity(), "Requesting", Toast.LENGTH_SHORT).show();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                Log.e("PACKAGEMINE","Requested new permission");
                                ActivityCompat.requestPermissions(getActivity(),new String[]{READ_MEDIA_IMAGES},PICK_FROM_GALLERY);
                            }else {
                                Log.e("PACKAGEMINE","Requested old permission");
                                ActivityCompat.requestPermissions(getActivity(), new String[]{
                                        READ_EXTERNAL_STORAGE
                                }, PICK_FROM_GALLERY);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Permission Already granted", Toast.LENGTH_SHORT).show();
                            activityResultLauncher.launch("image/*");
                        }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Error: " +  e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        return binding.getRoot();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri o) {
                        binding.postImageView.setImageURI(o);
                        imageUri = o;

                    }
                }
        );

    }

    private void saveClothes() {

        binding.postProgressBar.setVisibility(View.VISIBLE);

        //Upload Image to firestore

        String title = binding.sellPostTitleEt.getText().toString().trim();
        String description = binding.sellDescription.getText().toString().trim();
        String price = binding.sellPostPrice.getText().toString().trim();

        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(price)
        && imageUri !=null){

            final StorageReference imageFilePath = storageReference.child("ClothImages")
                    .child("image" + Timestamp.now().getSeconds());

            imageFilePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageUrl = uri.toString();
                            Clothes cloth = new Clothes();
                            cloth.setDescription(description);
                            cloth.setPrice(price);
                            cloth.setImageUrl(imageUrl);
                            cloth.setTitle(title);
                            cloth.setTimeAdded(Timestamp.now());
                            cloth.setUserName(currentUserName);
                            cloth.setUserId(currentUserId);
                            DocumentReference myRef = collectionReference.document(title);
                            myRef.set(cloth)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            binding.postProgressBar.setVisibility(View.INVISIBLE);
                                            Toast.makeText(getActivity(), "Successfully saved", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getActivity(), ClothesListingActivity.class);
                                            startActivity(i);
                                            getActivity().finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            binding.postProgressBar.setVisibility(View.INVISIBLE);
                                            Toast.makeText(getActivity(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            binding.postProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getActivity(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    binding.postProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            Toast.makeText(getActivity(), "Enter all fields", Toast.LENGTH_SHORT).show();
        }
        binding.postProgressBar.setVisibility(View.INVISIBLE);
    }
}