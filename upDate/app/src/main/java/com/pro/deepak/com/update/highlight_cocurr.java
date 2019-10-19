package com.pro.deepak.com.update;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pro.deepak.com.update.mRecycler.fieldsAdapter;
import com.pro.deepak.com.update.mRecycler.fieldsData;

import java.util.ArrayList;
import java.util.List;

public class highlight_cocurr extends Fragment {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    ListView dataLV;
    ArrayList<String> listKeys = new ArrayList<String>();
    List<fieldsData> elementList;
    fieldsAdapter adapterLV;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cocurr, container, false);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("Co Curricular");
        dataLV = rootView.findViewById(R.id.cocurr_LV);



        elementList = new ArrayList<>();
        adapterLV = new fieldsAdapter(getActivity(), R.layout.model_event ,elementList);
        dataLV.setAdapter(adapterLV);

        addChildEventListener();

        FloatingActionButton addFAB = rootView.findViewById(R.id.add_fab1);
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addInt = new Intent(getActivity(),add_data.class);
                addInt.putExtra("title","Co Curricular");
                startActivity(addInt);
            }
        });

        dataLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), results_activity.class);
                intent.putExtra("Subject",elementList.get(position).getSubject());
                intent.putExtra("Deadline",elementList.get(position).getDeadline());
                intent.putExtra("Description",elementList.get(position).getDesc());
                startActivity(intent);
                //Toast.makeText(vainglory.this, "Position : "+position, Toast.LENGTH_SHORT).show();//
            }
        });


        return  rootView;
    }


    private void addChildEventListener()
    {

        ChildEventListener childListener = new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fieldsData model = dataSnapshot.getValue(fieldsData.class);
                model.setSubject((String)dataSnapshot.child("Subject").getValue());
                model.setDeadline((String)dataSnapshot.child("Due Date").getValue());
                model.setDesc((String)dataSnapshot.child("Description").getValue());

                listKeys.add(dataSnapshot.getKey());
                adapterLV.add(model);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int index = listKeys.indexOf(key);

                if (index != -1) {
                    elementList.remove(index);
                    listKeys.remove(index);
                    adapterLV.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabaseReference.addChildEventListener(childListener);

    }

}
