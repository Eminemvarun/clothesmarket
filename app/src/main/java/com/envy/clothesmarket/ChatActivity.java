package com.envy.clothesmarket;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.envy.clothesmarket.databinding.ActivityChatBinding;
import com.envy.clothesmarket.models.ChatMessage;
import com.envy.clothesmarket.viewmodels.MessagesViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;
    MessagesViewModel viewModel;
    RecyclerView recyclerView;
    ChatAdapter adapter;
    String GROUP_TITLE;
    Button sendBTN;

    List<ChatMessage> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Binding
        binding = DataBindingUtil.setContentView(this,R.layout.activity_chat);
        //ViewModel
        viewModel = new ViewModelProvider(this).get(MessagesViewModel.class);
        //RecyclerView
        recyclerView = binding.chatRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        messageList = new ArrayList<>();
        adapter = new ChatAdapter(messageList,getApplicationContext());
        recyclerView.setAdapter(adapter);


        //Getting Group Name
        GROUP_TITLE = getIntent().getStringExtra("GROUP_TITLE");
        if(GROUP_TITLE == null){
            Toast.makeText(this, "NO TITLE RECIEVED!", Toast.LENGTH_SHORT).show();
        }
        viewModel.getChatData(GROUP_TITLE).observe(this, new Observer<List<ChatMessage>>() {
            @Override
            public void onChanged(List<ChatMessage> chatMessages) {
                messageList.clear();
                messageList.addAll(chatMessages);
                adapter.notifyDataSetChanged();

                //Scroll to latest position
                int latestPosition = adapter.getItemCount()-1;
                if(latestPosition>0) {
                    recyclerView.smoothScrollToPosition(latestPosition);
                }
            }
        });
        binding.setVModel(viewModel);

        EditText editText =  binding.edittextChatMessage;
        sendBTN = binding.sendBTN;
        sendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editText.getText().toString().isEmpty()){
                    viewModel.sendMessage(editText.getText().toString().trim(),GROUP_TITLE);
                    editText.getText().clear();
                }
                else{
                    Toast.makeText(ChatActivity.this, "Enter a message!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //Title text

        binding.chatTitle.setText(GROUP_TITLE);


    }
}