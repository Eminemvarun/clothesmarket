package com.envy.clothesmarket;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.envy.clothesmarket.models.MessagesGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class MessagesFragmentRecyclerViewAdapter extends RecyclerView.Adapter<MessagesFragmentRecyclerViewAdapter.MyViewHolder>{

    ArrayList<MessagesGroup> rdata;
    ArrayList<MessagesGroup> processedData;
    Context context;
    public MessagesFragmentRecyclerViewAdapter(ArrayList<MessagesGroup> data, Context context) {
        this.rdata = data;
        this.context = context;

        String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        processedData = new ArrayList<>();
        for(MessagesGroup m : rdata){
            Log.e("debug","User1:" +m.getUserOneID()+" User 2:"+ m.getUserTwoId());
            if(currentUid.equals(m.getUserOneID()) || currentUid.equals(m.getUserTwoId())){
                processedData.add(m);
            }
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.messages_group_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv.setText(processedData.get(position).getTitle());

        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,ChatActivity.class);
                i.putExtra("GROUP_TITLE",processedData.get(holder.getAdapterPosition()).getTitle());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return processedData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.messages_rowTV);
        }
    }
}


