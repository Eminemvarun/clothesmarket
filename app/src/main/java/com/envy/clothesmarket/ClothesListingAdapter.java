package com.envy.clothesmarket;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.envy.clothesmarket.models.Clothes;
import com.envy.clothesmarket.models.MessagesGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ClothesListingAdapter extends RecyclerView.Adapter<ClothesListingAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Clothes> recycler_array;

    public ClothesListingAdapter(Context context, ArrayList<Clothes> recycler_array) {
        this.context = context;
        this.recycler_array = recycler_array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_clothlisting,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Clothes current_cloth = recycler_array.get(position);
        holder.userId = current_cloth.getUserId();
        holder.username.setText(current_cloth.getUserName());
        holder.userId = current_cloth.getUserId();
        holder.title.setText(current_cloth.getTitle());
        holder.description.setText(current_cloth.getDescription());
        holder.price.setText("£" +current_cloth.getPrice());
        String imageurl = current_cloth.getImageUrl();
        String timeago = DateUtils.getRelativeTimeSpanString(current_cloth.getTimeAdded().getSeconds() *1000L).toString();
        holder.timestamp.setText(timeago);
        Glide.with(context).load(imageurl).into(holder.image);
        //Display send message to only others listings
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(current_cloth.getUserId())){
            holder.sendMessage.setVisibility(View.GONE);
        }

        holder.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add new messages group
                MessagesGroup messagesGroup = new MessagesGroup(FirebaseAuth.getInstance().getUid(),
                        holder.userId,holder.title.getText().toString());
                FirebaseFirestore.getInstance().collection("Messages").add(messagesGroup);

                Intent i = new Intent(context,ChatActivity.class);
                i.putExtra("GROUP_TITLE",holder.title.getText().toString());
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return recycler_array.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title, price,description,timestamp,username;
        ImageView image,shareButton;
        String userId;
        Button sendMessage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.journal_title_list);
            price = itemView.findViewById(R.id.journal_row_price);
            description = itemView.findViewById(R.id.journal_description);
            timestamp = itemView.findViewById(R.id.journal_timestamp_list);
            image = itemView.findViewById(R.id.journal_image_list);
            shareButton = itemView.findViewById(R.id.journal_row_share_button);
            username = itemView.findViewById(R.id.journal_row_username);
            sendMessage = itemView.findViewById(R.id.journal_send_message);

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });

            sendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StartChat(title.getText().toString());
                }
            });


        }

        private void StartChat(String title) {
            //Add new messages group
            MessagesGroup messagesGroup = new MessagesGroup(FirebaseAuth.getInstance().getUid(),userId,title);
            FirebaseFirestore.getInstance().collection("Messages").add(messagesGroup);

        }
    }
}
