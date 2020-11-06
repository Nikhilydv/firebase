package com.example.mycaone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class Home_Activity extends AppCompatActivity {
EditText inputSearch;
RecyclerView recyclerView;
FirebaseRecyclerOptions<Image> options;
FirebaseRecyclerAdapter<Image,MyViewHolder> adapter;
DatabaseReference DataRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);

        DataRef= FirebaseDatabase.getInstance().getReference().child("images");
        inputSearch=findViewById(R.id.inputSearch);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        LoadData();
    }

    private void LoadData() {

        options=new FirebaseRecyclerOptions.Builder<Image>().setQuery(DataRef,Image.class).build();
        adapter=new FirebaseRecyclerAdapter<Image, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Image model) {
            holder.textView.setText(model.getImgName());
                Picasso.get().load(model.getImgUrl()).into(holder.imageView);
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);

                return new MyViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}