package com.eisc.claryo.swamdrones;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String MSG_ANY_DRONES = "Aucun";
    public static DroneListeConnecte[] items;
    private ArrayAdapter<String> adapter;
    private ListView list;
    private TextView textViewNbDrones;
    private TextView textViewDrones;
    static private String[] listDrone;
    private Button btnFly;
    private DiscoveryDrone discoveryDrone;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            listDrone = msg.getData().getStringArray(MessageKEY.LISTDRONEUPDATE);
            ShowDroneList();
        }
    };

    private void ShowDroneList() {
        if (listDrone != null) {
            if (listDrone[0].equals(MSG_ANY_DRONES)) {
                textViewNbDrones.setText(MSG_ANY_DRONES);
                textViewDrones.setText("drone");
                list.setVisibility(View.INVISIBLE);
                btnFly.setVisibility(View.INVISIBLE);
            } else {
                textViewNbDrones.setText("" + listDrone.length);
                ArrayAdapter<String> listitems = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listDrone);
                list.setAdapter(listitems);
                list.setVisibility(View.VISIBLE);
                btnFly.setVisibility(View.VISIBLE);
            }
            if (listDrone.length == 1)
                textViewDrones.setText("drone");
            else if (listDrone.length > 1)
                textViewDrones.setText("drones");

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.i("onCreate", "create");
        if (GlobalCouple.couples == null) {
            GlobalCouple.couples = new ArrayList<>();
            if(MessageKEY.FLAG_FIRSTUSE) {
                new ServerUDP(getApplicationContext());
//                Log.i("newServerUDP", "new Serveur UDP");
                MessageKEY.FLAG_FIRSTUSE = false;
            }
        }

        list = (ListView) findViewById(R.id.listViewConnectedDrones);
        btnFly = (Button) findViewById(R.id.btnFly);
        btnFly.setVisibility(View.INVISIBLE);
        Button btnABout = (Button) findViewById(R.id.btnAbout);
        Button btnNotice = (Button) findViewById(R.id.btnNotice);
        textViewNbDrones = (TextView) findViewById(R.id.textViewNbDrones);
        textViewDrones = (TextView) findViewById(R.id.textViewDrones);
        ImageButton btnRefresh = (ImageButton) findViewById(R.id.btnMainActivityRefresh);

        ShowDroneList();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Si aucun drone n'est connecté, cliquer sur l'item ne fera rien
                if(!list.getAdapter().getItem(0).equals("Aucun drone connecter")){
                    String droneClicked = (String)list.getAdapter().getItem(position);
                    int droneSelected=-1;
                    for(int i=0; i<GlobalCouple.couples.size(); i++){
                        if(GlobalCouple.couples.get(i).getBebopDrone() != null){
                            if(droneClicked.equals(GlobalCouple.couples.get(i).getBebopDrone().getdeviceService().getName()))
                                droneSelected=i;
                        }
                    }

                    Intent DroneDetailsActivity = new Intent(MainActivity.this, DroneDetails.class);
                    if(droneSelected !=-1){

                        DroneDetailsActivity.putExtra("Name", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getDroneName())
                                .putExtra("Battery", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getBattery())
                                .putExtra("HardVersion", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getHardwareVersion())
                                .putExtra("SerialID", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getSerialID())
                                .putExtra("SoftVersion", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getSoftwareVersion())
                                .putExtra("GPSVersion", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getSoftwareGPSVersion())
                                .putExtra("nbFlight", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getNbFlights())
                                .putExtra("LastFlight", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getDurationLastFlight())
                                .putExtra("TotalFlight", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getDurationTotalFlights());

                    } else {
                        DroneDetailsActivity.putExtra("Name", "null");
                    }

                    startActivity(DroneDetailsActivity);
                }

            }

            @SuppressWarnings("unused")
            public void onClick(View v) {
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                if (discoveryDrone == null) {
                    discoveryDrone = new DiscoveryDrone(getApplication(), handler);
                } else {
                    discoveryDrone.onServicesDevicesListUpdated();
                }
                Toast.makeText(getApplicationContext(), "Liste mise à jour", Toast.LENGTH_SHORT).show();

            }
        });
        btnFly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ControlActivity = new Intent(MainActivity.this, Control.class);
                startActivity(ControlActivity);
            }
        });
        btnABout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AboutActivity = new Intent(MainActivity.this, APropos.class);
                startActivity(AboutActivity);
            }
        });

        btnNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NoticeActivity = new Intent(MainActivity.this, Notice.class);
                startActivity(NoticeActivity);
            }
        });
        discoveryDrone = new DiscoveryDrone(getApplicationContext(), handler);
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.i("onPause", "pause");
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.i("onStart", "start");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.i("onStop", "stop");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.i("onResume", "resume");
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.i("OnRestart", "restart");
//    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("onDestroy", "Destruction");
        byte[] buf_send = "Deconnexion\n".getBytes(Charset.forName("UTF-8"));
        DatagramPacket envoi = new DatagramPacket(buf_send, buf_send.length);
        DatagramSocket socket;
        try {
            socket = new DatagramSocket(ServerUDP.port);
            for (int i = 0;i < GlobalCouple.couples.size();i++){
                //On envoie le signal de déconnexion à la raspberry
                if(GlobalCouple.couples.get(i).getRaspberry() != null){
                    envoi.setAddress(GlobalCouple.couples.get(i).getRaspberry().getAddress());
                    envoi.setPort(GlobalCouple.couples.get(i).getRaspberry().getPort());
                    socket.send(envoi);

                    //on libère tout
                    socket.close();
                    envoi = null;
                    socket = null;
                    GlobalCouple.couples.get(i).setRaspberry(null);
                }
                //on détruit les drones de l'application
                if(GlobalCouple.couples.get(i).getBebopDrone() != null){
                    GlobalCouple.couples.get(i).getBebopDrone().getmDeviceController().stop();
                    GlobalCouple.couples.get(i).setBebopDrone(null);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

