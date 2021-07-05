package com.srit_nss_reports;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import com.squareup.picasso.Picasso;

public class myadapter2 extends FirebaseRecyclerAdapter<model,myadapter2.myviewholder> {

    Context ctx;
    public myadapter2(@NonNull  FirebaseRecyclerOptions<model> options) {

        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull  myadapter2.myviewholder holder, int position, @NonNull  model model) {


        //sektmg
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Events");

        databaseReference.child(String.valueOf(model.getTitle())).addValueEventListener(new ValueEventListener() {
@Override
public void onDataChange(@NonNull DataSnapshot snapshot) {
   String value=String.valueOf(snapshot.child("des").getValue());
   StorageReference storageRef = FirebaseStorage.getInstance().getReference("images/"+model.getTitle().toString());
   StorageReference dateRef = storageRef.child("eventimage0");
    dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
      {
                                         @RequiresApi(api = Build.VERSION_CODES.O)
                                         @Override
                                                                                                    public void onSuccess(Uri downloadUrl)
                                                                                                    {
                                                                                                        String downloadurl=downloadUrl.toString();



                                                                                                        //do something with downloadurl
                                                                                                        holder.date.setText(model.getDate());
                                                                                                        holder.title.setText(model.getTitle());
                                                                                                        holder.description.setText(value);
                                                                                                        Picasso.get().load(downloadurl).into(holder.img);
                                                                                                    }});

                                                                                            }

                                                                                            @Override
                                                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                                                            }
                                                                                        });




    }



    @Override
    public myviewholder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singleletter,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView title,date,description;
        ImageView img;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            title=(TextView)itemView.findViewById(R.id.title);
            description=(TextView)itemView.findViewById(R.id.desciption);
            img=(ImageView)itemView.findViewById(R.id.imageView2);
            date=(TextView)itemView.findViewById(R.id.date);
        }
    }
}
