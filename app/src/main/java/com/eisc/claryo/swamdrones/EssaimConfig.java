package com.eisc.claryo.swamdrones;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL;

/**
 * Classe permettant la configuration de l'essaim
 * - paramètres communs ou pas
 * - Liste des drones et choix du drone "maitre" ainsi que possibilité d'acceder
 * aux paramètres des drones
 */

public class EssaimConfig extends AppCompatActivity {

    static int NumeroDrone = 0;

    @Override
    @TargetApi(android.os.Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);

        setContentView(R.layout.essaim_config);

        Intent EssaimConfigActivity = new Intent();
        setResult(RESULT_OK, EssaimConfigActivity);

        ImageButton btnRetour = (ImageButton) findViewById(R.id.btnRetour);
        /*final ToggleButton btnDrone1 = (ToggleButton) findViewById(R.id.tglBtnDrone1);
        final ToggleButton btnDrone2 = (ToggleButton) findViewById(R.id.tglBtnDrone2);
        final ToggleButton btnDrone3 = (ToggleButton) findViewById(R.id.tglBtnDrone3);
        final ToggleButton btnDrone4 = (ToggleButton) findViewById(R.id.tglBtnDrone4);
        final ToggleButton btnDrone5 = (ToggleButton) findViewById(R.id.tglBtnDrone5);*/
        final Button btnReset = (Button) findViewById(R.id.btnResetConfDrones);
        /*final ImageButton btnSet1 = (ImageButton) findViewById(R.id.btn_Drone1_Setting);
        final ImageButton btnSet2 = (ImageButton) findViewById(R.id.btn_Drone2_Setting);
        final ImageButton btnSet3 = (ImageButton) findViewById(R.id.btn_Drone3_Setting);
        final ImageButton btnSet4 = (ImageButton) findViewById(R.id.btn_Drone4_Setting);
        final ImageButton btnSet5 = (ImageButton) findViewById(R.id.btn_Drone5_Setting);*/

        // Récupérer l'AssetManager
        /*AssetManager manager = getAssets();
        Bitmap bitmap = null;
        try {
            InputStream img_setting = manager.open("ic_settings");
            bitmap = BitmapFactory.decodeStream(img_setting);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.ic_settings);

        //LinearLayout listtoggledrone = (LinearLayout) findViewById(R.id.listtoggledrone);
        //LinearLayout listbutton = (LinearLayout) findViewById(R.id.listbutton);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        LinearLayout linearLayoutB = (LinearLayout) findViewById(R.id.linearLayoutB);
        LinearLayout linearLayoutH = (LinearLayout) findViewById(R.id.linearLayoutH);

        /*for(int i = 0;i<GlobalCouple.couples.size();i++){
            if(GlobalCouple.couples.get(i).getBebopDrone() != null){
                ToggleButton drone = new ToggleButton(this);
                String name = GlobalCouple.couples.get(i).getBebopDrone().getdeviceService().getName();
                drone.setText(name);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(500,150);
                layoutParams.setMargins(5,3,0,0);
                drone.setLayoutParams(layoutParams);
                listtoggledrone.addView(drone);
            }
        }
        */
        /*LinearLayout.LayoutParams layoutParamsD = new LinearLayout.LayoutParams(500, 150);
        layoutParamsD.setMargins(5, 3, 0, 0);
        LinearLayout.LayoutParams layoutParamsR = new LinearLayout.LayoutParams(500, 150);
        layoutParamsR.setMarginStart(layoutParamsD.getMarginEnd());
*/
        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                RadioButton radiodrone = new RadioButton(this);
                radiodrone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            //TODO Le maitre
                            //GlobalCouple.couples.get(i).getBebopDrone().setMaster(true);
                            buttonView.getText();
                        } else {
                            //TODO Il n'est plus maitre
                            //GlobalCouple.couples.get(finalI).getBebopDrone().setMaster(false);
                        }
                    }
                });
                ImageButton setting = new ImageButton(this);
                setting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO lancer l'activity correspondant
                        Intent PilotageConfActivity = new Intent(EssaimConfig.this, PilotageConf.class);
                        startActivity(PilotageConfActivity);
                        //TODO ne pas oublier le putextra extra
                    }
                });
                //Bitmap resizedBitmap = Bitmap.createScaledBitmap(b,drone.getWidth(),drone.getHeight(),false);
                //drone.setText(GlobalCouple.couples.get(i).getBebopDrone().getdeviceService().getName());
                radiodrone.setText(GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().getDroneName());
                setting.setImageBitmap(b);
                linearLayoutB.addView(setting);
                ViewGroup.LayoutParams params = setting.getLayoutParams();
                //params.height = setting.getHeight();
                radiodrone.setLayoutParams(params);

                radioGroup.addView(radiodrone);

            }
        }

        //On gère le retour à l'état initial de l'interface
/*
//TODO Le reset est important
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDrone1.setChecked(true);
            }
        });

        //On gère le passage au paramétrage de l'essaim

        btnSettingsSwarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PilotageConfActivity = new Intent(EssaimConfig.this, PilotageConf.class);
                startActivity(PilotageConfActivity);
            }
        });

        //On gère le passage au paramétrage des drones

        btnSet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PilotageConfActivity = new Intent(EssaimConfig.this, PositionConf.class);
                startActivity(PilotageConfActivity);
                NumeroDrone = 0;
            }
        });

        btnSet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PilotageConfActivity = new Intent(EssaimConfig.this, PositionConf.class);
                startActivity(PilotageConfActivity);
                NumeroDrone = 1;
            }
        });

        btnSet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PilotageConfActivity = new Intent(EssaimConfig.this, PositionConf.class);
                startActivity(PilotageConfActivity);
                NumeroDrone = 2;
            }
        });

        btnSet4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PilotageConfActivity = new Intent(EssaimConfig.this, PositionConf.class);
                startActivity(PilotageConfActivity);
                NumeroDrone = 3;
            }
        });

        btnSet5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PilotageConfActivity = new Intent(EssaimConfig.this, PositionConf.class);
                startActivity(PilotageConfActivity);
                NumeroDrone = 4;
            }
        });
*/
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EssaimConfig.this.finish();
                Intent ControlActivity = new Intent(EssaimConfig.this, Control.class);
                startActivity(ControlActivity);
            }
        });
    }

}
