package it10.com.playit10;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import java.io.Serializable;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;
public class Principal extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener, FragmentInicio.OnFragmentInteractionListener, FragmentKamishi.OnFragmentInteractionListener {
            String address = null;
            ProgressDialog progress;
            BluetoothAdapter myBluetooth = null;
            BluetoothSocket btSocket = null;
            private boolean isBtConnected = false;
            static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent newint = getIntent();
        address = newint.getStringExtra(Bluetooth.EXTRA_ADDRESS); //recivimos la mac address obtenida en la actividad anterior


        if(address!=null){
            new ConnectBT().execute();
            Toast.makeText(getApplicationContext(), address, Toast.LENGTH_LONG).show();

        }else{

            Toast.makeText(getApplicationContext(), "Sin conexion Bluetooth", Toast.LENGTH_LONG).show();
        }




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_led_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent nuevofrom= new Intent(Principal.this,Bluetooth.class);

            startActivity(nuevofrom);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean FragmentTransation = false;
        Fragment fragment = null;
        String ag = "abc";
        if (id == R.id.inicio) {
            Bundle arguments = new Bundle();
            arguments.putString("param1", address);
            FragmentInicio fragmentIn = new FragmentInicio();
            fragmentIn.setArguments(arguments);
            FragmentManager  fragmentManager = getSupportFragmentManager();
            FragmentTransaction transation = fragmentManager.beginTransaction();
            transation.add(R.id.Contenedor, fragmentIn);
            transation.commit();




        } else if (id == R.id.kamishi) {
            FragmentKamishi fragmentKamishi = new FragmentKamishi();
            FragmentManager manager1 = getSupportFragmentManager();
            manager1.beginTransaction().replace(
                    R.id.Contenedor,fragmentKamishi,fragmentKamishi.getTag()
            ).commit();
        } else if (id == R.id.eva) {
            FragmentTransation=false;
        } else if (id == R.id.otto) {
            FragmentTransation=false;
        } else if (id == R.id.autito) {
            FragmentTransation=false;
        } else if (id == R.id.araña) {
            FragmentTransation=false;
        }

        /*if (FragmentTransation){
            //getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor,fragment).commit();
            //item.setChecked(true);
            //getSupportActionBar().setTitle(item.getTitle());
        }*/

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void Disconnect()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.close();
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish();

    }
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }




    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(Principal.this, "Connecting...", "Please wait!!!");
        }

        @Override
        protected Void doInBackground(Void... devices)
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//conectamos al dispositivo y chequeamos si esta disponible
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Conexión Fallida");
                finish();
            }
            else
            {
                msg("Conectado");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }









}
