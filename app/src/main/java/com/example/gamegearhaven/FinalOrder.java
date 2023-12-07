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
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FinalOrder extends AppCompatActivity {

    EditText location,Contact;
    Button Done;
    private ProgressDialog mloadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_order);

        Contact = findViewById(R.id.contact);
        location = findViewById(R.id.location);
        Done = findViewById(R.id.Done);
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        mloadingbar = new ProgressDialog(FinalOrder.this);



        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();
            }

            private void checkCredentials() {
             String locationn = location.getText().toString();
             String contacts = Contact.getText().toString();

             if(locationn.isEmpty()){
                 showError(location,"You need to input your Location first!");
             }
             else if(locationn.length()<10){
                 showError(location,"Please try again");
             }
             else if(contacts.isEmpty()){
                 showError(Contact,"You need to input your Contact Number!");
             }
             else if (contacts.length()>11) {
                 showError(Contact,"Check your Number and try again!");
             }
             else if (contacts.length()<10) {
                 showError(Contact, "Check your Number and try again!");
             }
             else{
                 builder.setTitle("Order Successfully!")
                        .setMessage(" Wait for the agent to verify the confirmation of your order, thankyou! :>")
                         .setCancelable(false)
                         .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog, int id) {
                                 mloadingbar.setMessage("Please wait....");
                                 mloadingbar.setCanceledOnTouchOutside(false);
                                 mloadingbar.show();
                                 new Handler().postDelayed(new Runnable() {
                                     @Override
                                     public void run() {
                                         Intent intent = new Intent(FinalOrder.this,MainActivity.class);
                                         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                         startActivity(intent);
                                         mloadingbar.dismiss();
                                     }
                                 },3000);
                             }
                         });
                 AlertDialog alert = builder.create();
                 alert.show();
             }

            }

            private void showError(EditText input, String s) {
                input.setError(s);
                input.requestFocus();
            }
        });



    }
}