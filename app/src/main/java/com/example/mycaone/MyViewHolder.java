package com.example.mycaone;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textView;
    View v;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.imageView2);
        textView=itemView.findViewById(R.id.textView2);
        v=itemView;
    }
}
