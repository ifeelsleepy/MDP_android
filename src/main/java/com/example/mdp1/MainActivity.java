package com.example.mdp1;

import android.content.Intent;
import android.os.Bundle;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;

import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    //for connection to BluetoothService
    private boolean bound = false;
    private BluetoothService bluetoothService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart(){
        super.onStart();
        //Bind this activity to BluetoothService
        Intent intent = new Intent(this, BluetoothService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (bluetoothService!= null)
            bluetoothService.startToast();
    }

    @Override
    protected void onPause(){
        super.onPause();
        //bluetoothService.stopToast();
    }

    @Override
    protected void onStop(){
        super.onStop();
        //bluetoothService.stopToast();
        if (bound){
            unbindService(connection);
            bound = false;
        }
    }


    public void manageBTconn(View v){

        Intent intent = new Intent(this, BluetoothConnection.class);
        startActivity(intent);
    }

    public void manageBTcomm(View v){

        Intent intent = new Intent(this, BluetoothCommunication.class);
        startActivity(intent);
    }

    public void mapUI(View v){
        Intent intent = new Intent(this, mapInterface.class);
        startActivity(intent);
    }

    public void end(View v){

        this.onStop();
        this.finish();
    }

    private ServiceConnection connection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder){
            BluetoothService.BluetoothBinder bluetoothBinder =
                    (BluetoothService.BluetoothBinder) binder;
            bluetoothService = bluetoothBinder.getBluetooth();
            bound = true;
            bluetoothService.startToast();
        }
        @Override
        public void onServiceDisconnected(ComponentName name){
            bound = false;
        }
    };
}