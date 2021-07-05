package com.srit_nss_reports;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class EventDetails extends AppCompatActivity {
TextView  title,des,date;
Context mContext;
Button pdf;
ImageView eventimage,eventimage2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        date = (TextView) findViewById(R.id.date);
        title = (TextView) findViewById(R.id.TITLE);
        pdf=findViewById(R.id.pdf);
        des = (TextView) findViewById(R.id.DESCRIPTION);
        ScrollView sv=findViewById(R.id.views);
        mContext=sv.getContext();

        eventimage2=(ImageView)findViewById(R.id.eventimage2);
        ImageView eventimage3=(ImageView)findViewById(R.id.eventimage3);

        eventimage = (ImageView) findViewById(R.id.eventimage);
        Picasso.get().load(getIntent().getStringExtra("url")).into(eventimage);
        Picasso.get().load(getIntent().getStringExtra("url2")).into(eventimage2);
        Picasso.get().load(getIntent().getStringExtra("url3")).into(eventimage3);


        title.setText(getIntent().getStringExtra("title"));


        date.setText("Date:" + getIntent().getStringExtra("date"));

        des.setText(getIntent().getStringExtra("Description"));
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Events");
        View pd=findViewById(R.id.pdf);

       pdf.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               pd.setVisibility(View.INVISIBLE);
               takeScreenShot();
               pd.setVisibility(View.VISIBLE);


           }

       }

       );


    }
        private void takeScreenShot() {

            try {

                //here getScroll is my scrollview id

                View u = ((Activity) mContext).findViewById(R.id.views);

                ScrollView z = (ScrollView) ((Activity) mContext).findViewById(R.id.views);
                int totalHeight = z.getChildAt(0).getHeight();
                int totalWidth = z.getChildAt(0).getWidth();

                Bitmap bitmap = getBitmapFromView(u,totalHeight,totalWidth);

                Image image;

                //Save bitmap
                String path = Environment.getExternalStorageDirectory()+"/";
                String fileName = title.getText()+".pdf";

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
    }}
