package com.example.bluetoothadapter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ListActivity extends AppCompatActivity {
    private ListView listView;
    private BTadapter adapter;
    private BluetoothAdapter bluetoothAdapter;
    private List<LIstItem> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void init(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        ActionBar ab = getSupportActionBar();
        if(ab == null)
            return;
        ab.setDisplayHomeAsUpEnabled(true);

        adapter = new BTadapter(this, R.layout.list_item, list );
        listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        getPairedDevices();
    }

    private void getPairedDevices(){
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0){
            list.clear();
            for (BluetoothDevice device: pairedDevices) {
                LIstItem listItem = new LIstItem();
                listItem.setName(device.getName());
                listItem.setMac(device.getAddress());
                list.add(listItem);
            }
            adapter.notifyDataSetChanged();
        }
    }
}