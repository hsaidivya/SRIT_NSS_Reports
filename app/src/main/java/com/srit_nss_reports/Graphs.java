package com.srit_nss_reports;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class Graphs extends AppCompatActivity {
    GraphView graphView;
    public static ArrayList<Integer> list
            = new ArrayList<Integer>(3);
    ArrayList<String> xAxisLables = new ArrayList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);
        xAxisLables.add("JAN-MAR");
        xAxisLables.add("APR-JUN");
        xAxisLables.add("JUL-SEP");
        xAxisLables.add("OCT-DEC");


        graphView = findViewById(R.id.idGraphView);
        list.add(0,0);
        list.add(1,0);
        list.add(2,0);
        list.add(3,0);

        // on below line we are adding data to our graph view.
        Button plot = findViewById(R.id.button);
        plot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query fbDb = null;


                if (fbDb == null) {
                    fbDb = FirebaseDatabase.getInstance().getReference().child("Events").orderByChild("ts").startAt(20210101).endAt(20210301);
                }


                fbDb
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // get total available quest
                                int size = (int) dataSnapshot.getChildrenCount();
                                list.set(0,size);
                                Toast.makeText(Graphs.this, String.valueOf(size), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                Query nx3 = null;


                if (nx3 == null) {
                    nx3 = FirebaseDatabase.getInstance().getReference().child("Events").orderByChild("ts").startAt(20210401).endAt(20210601);
                }


                nx3
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // get total available quest
                                int size = (int) dataSnapshot.getChildrenCount();
                                list.set(1,size);
                                Toast.makeText(Graphs.this, String.valueOf(size), Toast.LENGTH_SHORT).show();


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                Query nxnx3 = null;


                if (nxnx3 == null) {
                    nxnx3 = FirebaseDatabase.getInstance().getReference().child("Events").orderByChild("ts").startAt(20210701).endAt(20210901);
                }


                nxnx3
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // get total available quest
                                int size = (int) dataSnapshot.getChildrenCount();
                                list.set(2,size);
                                Toast.makeText(Graphs.this, String.valueOf(size), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                Query nxnx4 = null;


                if (nxnx4 == null) {
                    nxnx4 = FirebaseDatabase.getInstance().getReference().child("Events").orderByChild("ts").startAt(20211001).endAt(20211201);
                }


                nxnx4
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // get total available quest
                                int size = (int) dataSnapshot.getChildrenCount();
                                list.set(3,size);
                                Toast.makeText(Graphs.this, String.valueOf(size), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                        new DataPoint(1, list.get(0)),
                        new DataPoint(2, list.get(1)),
                        new DataPoint(3, list.get(2)),
                        new DataPoint(4,list.get(3))


                });
                graphView.setTitle("Graphical view");
                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
                staticLabelsFormatter.setHorizontalLabels(new String[] {"JAN-MAR", "APR-JUN", "JUL-SEP"});


                // on below line we are setting
                // text color to our graph view.
                graphView.setTitleColor(R.color.purple_200);



                // on below line we are setting
                // our title text size.
                graphView.setTitleTextSize(18);

                // on below line we are adding
                // data series to our graph view.
                graphView.addSeries(series);



            }
        });




        // after adding data to our line graph series.
        // on below line we are setting
        // title for our graph view.

    }
}