package com.envy.clothesmarket;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.envy.clothesmarket.models.MessagesGroup;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MessagesFragment extends Fragment {

    RecyclerView recyclerView;
    CollectionReference collectionReference =FirebaseFirestore.getInstance().collection("Messages");
    MessagesFragmentRecyclerViewAdapter adapter;
    ArrayList<MessagesGroup> data;
    TextView title;



    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        data = new ArrayList<>();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        recyclerView = view.findViewById(R.id.messages_list_recyclerView);
        title = view.findViewById(R.id.messages_title);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<MessagesGroup> messagesGroup = queryDocumentSnapshots.toObjects(MessagesGroup.class);
                data.clear();
                data.addAll(messagesGroup);
                adapter = new MessagesFragmentRecyclerViewAdapter(data, getContext());
                if(adapter.getItemCount() !=0){
                    title.setVisibility(View.GONE);
                }
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }
}