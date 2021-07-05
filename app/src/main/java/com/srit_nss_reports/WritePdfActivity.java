package com.srit_nss_reports;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

public class WritePdfActivity extends Activity
{
    private static String FILE = "mnt/sdcard/FirstPdf.pdf";

    static Image image;
    static ImageView img;
    Bitmap bmp;
    static Bitmap bt;
    static byte[] bArray;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        img=(ImageView)findViewById(R.id.imageView);

        try
        {
            Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();

            addImage(document);
            document.close();
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    private static void addImage(Document document)
    {

        try
        {
            image = Image.getInstance(bArray);  ///Here i set byte array..you can do bitmap to byte array and set in image...
        }
        catch (BadElementException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (MalformedURLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // image.scaleAbsolute(150f, 150f);
        try
        {
            document.add(image);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}