package com.example.gamegearhaven;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowCart extends AppCompatActivity {
    private RecyclerView rec;
    private Button delete,checkout;
    private ProgressDialog mloadingbar;
    AlertDialog.Builder builder;
    private List<ShowCart_Model>showCart_modelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cart);
        delete = findViewById(R.id.delete);
        checkout = findViewById(R.id.checkout);
        rec = findViewById(R.id.rec);
        mloadingbar = new ProgressDialog(ShowCart.this);

        rec.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        builder = new AlertDialog.Builder(this);

        DatabaseReference show_info = FirebaseDatabase.getInstance().getReference("Cart");

        show_info.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            showCart_modelList.clear();
                            for (DataSnapshot snapshot1: snapshot.getChildren()){
                                ShowCart_Model showCart_model = snapshot1.getValue(ShowCart_Model.class);
                                showCart_modelList.add(showCart_model);
                            }
                            rec.setAdapter(new ShowCart_Adapter(getApplicationContext(),showCart_modelList));
                        }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("ALERT!")
                        .setMessage("Do you want to continue?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference show_info = FirebaseDatabase.getInstance().getReference("Cart");
                                show_info.removeValue();
                                mloadingbar.setTitle("Deleting Orders");
                                mloadingbar.setMessage("Please wait...");
                                mloadingbar.setCanceledOnTouchOutside(false);
                                mloadingbar.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(ShowCart.this,MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        mloadingbar.dismiss();
                                    }
                                },3000);
                                Toast.makeText(ShowCart.this, "Deleting Success", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();


            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("ALERT!")
                        .setMessage("Do you want to continue?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ShowCart.this, FinalOrder.class);
                            startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });




    }
}