package com.srit_nss_reports;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder> {

Context ctx;

    public myadapter(@NonNull  FirebaseRecyclerOptions<model> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull  myadapter.myviewholder holder, int position, @NonNull  model model) {

        holder.date.setText(model.getDate());
        holder.title.setText(model.getTitle());

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Events");




                databaseReference.child(String.valueOf(model.getTitle())).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value=String.valueOf(snapshot.child("des").getValue());
                        StorageReference storageRef = FirebaseStorage.getInstance().getReference("images/"+model.getTitle().toString());
                        StorageReference storageRef2 = FirebaseStorage.getInstance().getReference("images/"+model.getTitle().toString());
                        StorageReference storageRef3 = FirebaseStorage.getInstance().getReference("images/"+model.getTitle().toString());

                        StorageReference dateRef = storageRef.child("eventimage0");
                        dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                        {
                            @Override
                            public void onSuccess(Uri downloadUrl)
                            {
                              String  downloadurl=downloadUrl.toString();

                                //do something with downloadurl

                        StorageReference dateRef2 = storageRef.child("eventimage1");
                        dateRef2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                        {
                            @Override
                            public void onSuccess(Uri downloadUrl2)
                            {
                                String downloadurl2=downloadUrl2.toString();
                                StorageReference dateRef3=storageRef3.child("eventimage2");
                                dateRef3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String downloadurl3=uri.toString();
                                        Intent intent=new Intent(context,EventDetails.class);
                                        intent.putExtra("title",model.getTitle());
                                        intent.putExtra("date",model.getDate());
                                        intent.putExtra("Description",value);
                                        intent.putExtra("url",downloadurl);
                                        intent.putExtra("url2",downloadurl2);
                                        intent.putExtra("url3",downloadurl3);



                                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent);
                                    }
                                });
                                //do something with downloadurl
                            }});  }});

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });


    }

    @NonNull
Context context;
    @Override
    public myviewholder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
    return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView title,date;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            title=(TextView)itemView.findViewById(R.id.title);
            date=(TextView)itemView.findViewById(R.id.date);
        }
    }
}
