package com.eisc.claryo.swamdrones;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.Charset;

/**
 * Created by sofiane on 14/02/17.
 */

public class ServerUDP {

    final static int port = 55555;
    final static int taille = 1024;
    static byte buffer[] = new byte[taille];
    final String RPI_SMARTPHONE = "Telephone";
    final String RPI_SENSORS = "Sensor";
    final String RPI_REPONSE = "Oui\n";
    final String DECOUPE_SENSOR = ",";
    private final String TAG = "ServerUDP";

    ServerUDP(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DatagramSocket socket = new DatagramSocket(port);
                    DatagramPacket paquet = new DatagramPacket(buffer, buffer.length);
                    DatagramPacket envoi = null;
                    String str;
                    while (true) {
                        do {
                            socket.receive(paquet);
                            str = new String(paquet.getData());
                        } while (str.indexOf("\n") == -1);

                        String sframe = str.substring(0, str.indexOf("\n"));
                        String scmd = sframe.substring(0, sframe.indexOf(" "));
                        switch (scmd) {
                            case RPI_SENSORS:
                                String north;
                                String west;
                                String south;
                                String est;

                                Log.e(TAG, "Message str :" + str);
                                Log.e(TAG, "Message sframe :" + sframe);
                                String ssensor = sframe.substring(sframe.lastIndexOf(" "), sframe.length());

                                if (ssensor != null) {
                                    String[] listSensor = ssensor.split(DECOUPE_SENSOR);
                                    Log.e(TAG, "Message sensor :" + ssensor);

                                    for (int i = 0; i < listSensor.length; i++) {

                                        Log.e(TAG, "Message vsensor : " + listSensor[i]);
                                        String val = listSensor[i].substring(1, listSensor[i].indexOf(":"));
                                        String capt = listSensor[i].substring(listSensor[i].lastIndexOf(":") + 1, listSensor[i].length());
                                        Log.e(TAG, "Message val : " + val);
                                        Log.e(TAG, "Message capt : " + capt);
                                        int index = GlobalCouple.raspberryIPCorrespondante(paquet.getAddress());

                                        if (index != -1) {
                                            if (capt.equals("n")) {
                                                north = val;
                                                Log.e(TAG, "Capteur nord : " + north);
                                                if (index != -1)
                                                    GlobalCouple.couples.get(index).getRaspberry().getObstacle().setNorth(Integer.valueOf(val));
                                            }

                                            if (capt.equals("w")) {
                                                west = val;
                                                Log.e(TAG, "Capteur ouest : " + west);
                                                if (index != -1)
                                                    GlobalCouple.couples.get(index).getRaspberry().getObstacle().setWest(Integer.valueOf(val));
                                            }

                                            if (capt.equals("s")) {
                                                south = val;
                                                Log.e(TAG, "Capteur sud : " + south);
                                                if (index != -1)
                                                    GlobalCouple.couples.get(index).getRaspberry().getObstacle().setSouth(Integer.valueOf(val));
                                            }

                                            if (capt.equals("e")) {
                                                est = val;
                                                Log.e(TAG, "Capteur est : " + est);
                                                if (index != -1)
                                                    GlobalCouple.couples.get(index).getRaspberry().getObstacle().setEst(Integer.valueOf(val));
                                            }
                                            if (Integer.valueOf(val) <= 50) {
                                                for (int j = 0; j < GlobalCouple.couples.size(); j++) {
                                                    if (GlobalCouple.couples.get(j).getBebopDrone() != null) {
                                                        GlobalCouple.couples.get(j).getBebopDrone().stationnaire();
                                                    }
                                                }
                                            }
                                        }
                                        else {//sinon reconstructon//TODO realiser une focntion car bout de code utiliser deux fois
                                            if (!GlobalCouple.raspberryExist(paquet.getAddress())) {
                                                Raspberry rpi = new Raspberry(paquet.getAddress());
                                                int in = GlobalCouple.droneCorrespondant(rpi);
                                                if (in != -1)//on a trouvé un drone correspondant à la raspberry
                                                    GlobalCouple.couples.get(in).setRaspberry(rpi);
                                                else
                                                    GlobalCouple.couples.add(new Couple(null, rpi));
                                            }
                                        }
                                    }
                                }

                                //Toast.ma{keText(context,sframe,Toast.LENGTH_SHORT);

                                break;
                            case RPI_SMARTPHONE:
                                //Toast.makeText(context,sframe,Toast.LENGTH_SHORT);
                                //Log.d(TAG,"Message reçu : "+sframe);
                                byte[] buf_send = RPI_REPONSE.getBytes(Charset.forName("UTF-8"));
                                envoi = new DatagramPacket(buf_send, buf_send.length);
                                envoi.setAddress(paquet.getAddress());
                                envoi.setPort(paquet.getPort());
                                socket.send(envoi);

                                //Création de l'objet Raspberry s'il n'existe pas dans la liste des couples
                                //et insertion de l'objet dans la liste des couples
                                if (!GlobalCouple.raspberryExist(paquet.getAddress())) {
                                    Raspberry rpi = new Raspberry(paquet.getAddress());
                                    int index = GlobalCouple.droneCorrespondant(rpi);
                                    if (index != -1)//on a trouvé un drone correspondant à la raspberry
                                        GlobalCouple.couples.get(index).setRaspberry(rpi);
                                    else
                                        GlobalCouple.couples.add(new Couple(null, rpi));
                                }

                                break;
                        }
                        paquet.setLength(buffer.length);
                    }


                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();
/* new Thread(new Runnable() {
@Override
public void run() {
    try {
        byte []buf = new byte[1024];
        DatagramSocket socket = new DatagramSocket(5300);
        DatagramPacket packet = new DatagramPacket(buf,buf.length);
        System.arraycopy("salut".getBytes(),0,buf,0,"salut".getBytes().length);
        packet.setAddress(InetAddress.getByName("192.168.2.30"));
        socket.send(packet);
        int i = 0;
        i+=20;
        int A = 4;
        A+=i;
    } catch (SocketException e) {
        e.printStackTrace();
    } catch (UnknownHostException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

}
}).start();*/

    }

}
