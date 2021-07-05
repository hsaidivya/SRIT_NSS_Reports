package com.srit_nss_reports;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class Newsletters extends AppCompatActivity {
    RecyclerView recview;
    myadapter2 adapter;
    Button pdf2;
    Context mContext;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsletters);

        recview=(RecyclerView)findViewById(R.id.recview2);
        recview.setLayoutManager(new LinearLayoutManager(this));


        Date dNow = new Date( );
        SimpleDateFormat ft =
                new SimpleDateFormat ("yyyy/MM/dd");

        String today=ft.format(dNow);

        String[] td_splt=today.split("/");

        String td_stamp=td_splt[0]+td_splt[1]+td_splt[2];
        int today_time=Integer.parseInt(td_stamp);

        pdf2=findViewById(R.id.pdf2);

        mContext=recview.getContext();
        int month=Integer.parseInt(td_splt[1])-3;
        String mn=String.valueOf(month);
        if(mn.length()==1){
            mn="0"+mn;
        }
        String past=td_splt[0]+mn+td_splt[2];
        int last3=Integer.parseInt(past);

        //Toast.makeText(this, last3+" "+today_time, Toast.LENGTH_SHORT).show();



        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Events").orderByChild("ts").startAt(last3).endAt(today_time), model.class)
                        .build();

        adapter=new myadapter2(options);
        recview.setAdapter(adapter);

        pdf2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdf2.setVisibility(View.INVISIBLE);
                takeScreenShot();
                pdf2.setVisibility(View.VISIBLE);

            }
        });



    }

    //news letters pdf code...~~~
    private void takeScreenShot() {

        try {

            //here getScroll is my scrollview id

            View u = ((Activity) mContext).findViewById(R.id.views2);

            ScrollView z = (ScrollView) ((Activity) mContext).findViewById(R.id.views2);
            int totalHeight = z.getChildAt(0).getHeight();
            int totalWidth = z.getChildAt(0).getWidth();

            Bitmap bitmap = getBitmapFromView(u,totalHeight,totalWidth);

            Image image;

            //Save bitmap
            String path = Environment.getExternalStorageDirectory()+"/";
            String fileName = "newsletter.pdf";

            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();

            Log.v("PDFCreator", "PDF Path: " + path);

            File myPath = new File(path, fileName);
            Toast.makeText(mContext, "pdf exported successfully", Toast.LENGTH_SHORT).show();


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            image = Image.getInstance(stream.toByteArray());
            image.setAbsolutePosition(0, 0);
            Document document = new Document(image);
            PdfWriter.getInstance(document, new FileOutputStream(myPath));
            document.open();
            document.add(image);
            document.close();




        } catch (Exception i1) {
            i1.printStackTrace();
        }



    }


    public Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth,totalHeight , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}