package com.envy.clothesmarket.viewmodels;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.envy.clothesmarket.models.ChatMessage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MessagesViewModel extends AndroidViewModel {

    MutableLiveData<List<ChatMessage>> chatData;

    FirebaseDatabase database;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    DatabaseReference reference;
    DatabaseReference groupReference;

    public MessagesViewModel(@NonNull Application application) {
        super(application);
        chatData = new MutableLiveData<>();
        database = FirebaseDatabase.getInstance();
        reference =database.getReference("Messages");
        firestore = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<List<ChatMessage>> getChatData(String group_name) {
        CollectionReference groupReference = firestore.collection("Clothes").document(group_name).collection("Chats");
        // Listen for changes in the Chats collection
        groupReference.orderBy("time", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("MessagesViewModel", "Listen failed.", e);
                            return;
                        }
                        List<ChatMessage> list = new ArrayList<>();
                        if (queryDocumentSnapshots != null) {
                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                ChatMessage message = doc.toObject(ChatMessage.class);
                                list.add(message);
                            }
                            chatData.postValue(list);
                        }
                    }
                });


        return chatData;
    }

    public void setChatData(MutableLiveData<List<ChatMessage>> chatData) {
        this.chatData = chatData;
    }

    public void sendMessage(String message, String chatgroup){
        //database = FirebaseDatabase.getInstance();
        //DatabaseReference ref = database.getReference("Messages").child(chatgroup);

        collectionReference = firestore.collection("Clothes").document(chatgroup).collection("Chats");

        if(!message.isEmpty()){
            ChatMessage message1 = new ChatMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(),message,System.currentTimeMillis());
            //String key = ref.push().getKey();
            //if (key != null) {
            //    ref.child(key).setValue(message1);
            //} else {
            //    Log.e("MessagesViewModel", "Failed to generate unique key");
            //}
            collectionReference.add(message1).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplication().getApplicationContext(), "Failed to send message", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
