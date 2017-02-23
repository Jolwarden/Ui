package com.eisc.claryo.swamdrones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEORESOLUTIONS_TYPE_ENUM;

import static com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_ANTIFLICKERING_SETMODE_MODE_ENUM.ARCOMMANDS_ARDRONE3_ANTIFLICKERING_SETMODE_MODE_AUTO;
import static com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_ANTIFLICKERING_SETMODE_MODE_ENUM.ARCOMMANDS_ARDRONE3_ANTIFLICKERING_SETMODE_MODE_FIXEDFIFTYHERTZ;
import static com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_ANTIFLICKERING_SETMODE_MODE_ENUM.ARCOMMANDS_ARDRONE3_ANTIFLICKERING_SETMODE_MODE_FIXEDSIXTYHERTZ;
import static com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOFRAMERATE_FRAMERATE_ENUM.ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOFRAMERATE_FRAMERATE_24_FPS;
import static com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOFRAMERATE_FRAMERATE_ENUM.ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOFRAMERATE_FRAMERATE_25_FPS;
import static com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOFRAMERATE_FRAMERATE_ENUM.ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOFRAMERATE_FRAMERATE_30_FPS;
import static com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOSTABILIZATIONMODE_MODE_ENUM.ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOSTABILIZATIONMODE_MODE_NONE;
import static com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOSTABILIZATIONMODE_MODE_ENUM.ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOSTABILIZATIONMODE_MODE_ROLL_PITCH;

/**
 * Classe permettant de régler les paramètres vidéo d'un/des drone(s)
 */

public class VideoConf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);

        setContentView(R.layout.video_conf);

        Intent VideoConfActivity = new Intent();
        setResult(RESULT_OK, VideoConfActivity);

        ImageButton btnRetour = (ImageButton) findViewById(R.id.btnRetour);
        TextView txtDroneNom = (TextView) findViewById(R.id.txtDroneNom);
        Button btnReset = (Button) findViewById(R.id.btnResetVideoDrones);

        ImageButton btnPositConf = (ImageButton) findViewById(R.id.btnPositConf);
        ImageButton btnInfoConf = (ImageButton) findViewById(R.id.btnInfoConf);

        final ToggleButton btn720p = (ToggleButton) findViewById(R.id.btnVid720);
        final ToggleButton btn1080p = (ToggleButton) findViewById(R.id.btnVid1080);
        final ToggleButton btnStdr = (ToggleButton) findViewById(R.id.btnStdr);
        final ToggleButton btnHigh = (ToggleButton) findViewById(R.id.btnHigh);
        final ToggleButton btn24fps = (ToggleButton) findViewById(R.id.btn24);
        final ToggleButton btn25fps = (ToggleButton) findViewById(R.id.btn25);
        final ToggleButton btn30fps = (ToggleButton) findViewById(R.id.btn30);
        final ToggleButton btnStabNon = (ToggleButton) findViewById(R.id.btnStablN);
        final ToggleButton btnStabOui = (ToggleButton) findViewById(R.id.btnStabO);
        final ToggleButton btn50Hz = (ToggleButton) findViewById(R.id.btn50Hz);
        final ToggleButton btn60Hz = (ToggleButton) findViewById(R.id.btn60Hz);
        final ToggleButton btnAutoHz = (ToggleButton) findViewById(R.id.btnAutoHz);

        Bundle extras = getIntent().getExtras();
        final int correspondant = extras.getInt(MessageKEY.POSITIONCOUPLE);
        switch (GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().getVideo_resolution()) {
            case ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEORESOLUTIONSCHANGED_TYPE_MAX:
            case ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEORESOLUTIONSCHANGED_TYPE_REC1080_STREAM480:
                btn1080p.setChecked(true);
                btn1080p.setClickable(false);
                btn720p.setChecked(false);
                break;
            case ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEORESOLUTIONSCHANGED_TYPE_REC720_STREAM720:
                btn1080p.setChecked(false);
                btn720p.setChecked(true);
                btn720p.setClickable(false);
                break;
            default:
            case eARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEORESOLUTIONSCHANGED_TYPE_UNKNOWN_ENUM_VALUE:
                btn1080p.setChecked(false);
                btn720p.setChecked(false);
                break;
        }

        switch (GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().getFramerate()) {
            case ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEOFRAMERATECHANGED_FRAMERATE_24_FPS:
                btn24fps.setChecked(true);
                btn24fps.setClickable(false);

                btn25fps.setChecked(false);
                btn30fps.setChecked(false);
                break;
            case ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEOFRAMERATECHANGED_FRAMERATE_25_FPS:
                btn25fps.setChecked(true);
                btn25fps.setClickable(false);

                btn24fps.setChecked(false);
                btn30fps.setChecked(false);
                break;
            case ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEOFRAMERATECHANGED_FRAMERATE_MAX:
            case ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEOFRAMERATECHANGED_FRAMERATE_30_FPS:
                btn30fps.setChecked(true);
                btn30fps.setClickable(false);

                btn24fps.setChecked(false);
                btn25fps.setChecked(false);
                break;
            default:
            case eARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEOFRAMERATECHANGED_FRAMERATE_UNKNOWN_ENUM_VALUE:
                btn24fps.setChecked(false);
                btn25fps.setChecked(false);
                btn30fps.setChecked(false);
        }

        //Nous avons le type pitch et roll, mais on decide que la stabilisation c'est pitch et roll
        switch (GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().getVideostabilization()) {
            case ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEOSTABILIZATIONMODECHANGED_MODE_MAX:
            case ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEOSTABILIZATIONMODECHANGED_MODE_ROLL_PITCH:
                btnStabOui.setChecked(true);
                btnStabOui.setClickable(false);
                btnStabNon.setChecked(false);
                break;
            case ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEOSTABILIZATIONMODECHANGED_MODE_ROLL:
            case ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEOSTABILIZATIONMODECHANGED_MODE_PITCH:
                //TODO je ne sais pas quoi faire dans cette situation
                //break;
            case ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEOSTABILIZATIONMODECHANGED_MODE_NONE:
                btnStabNon.setChecked(true);
                btnStabNon.setClickable(false);
                btnStabOui.setChecked(false);
                break;
            default:
            case eARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEOSTABILIZATIONMODECHANGED_MODE_UNKNOWN_ENUM_VALUE:

        }

        //Anti-scintillement
        switch (GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().getAntiflickering()) {
            case ARCOMMANDS_ARDRONE3_ANTIFLICKERINGSTATE_MODECHANGED_MODE_AUTO:
                btnAutoHz.setChecked(true);
                btnAutoHz.setClickable(false);
                btn50Hz.setChecked(false);
                btn60Hz.setChecked(false);
                break;
            case ARCOMMANDS_ARDRONE3_ANTIFLICKERINGSTATE_MODECHANGED_MODE_FIXEDFIFTYHERTZ:
                btn50Hz.setChecked(true);
                btn50Hz.setClickable(false);
                btnAutoHz.setChecked(false);
                btn60Hz.setChecked(false);
                break;
            case ARCOMMANDS_ARDRONE3_ANTIFLICKERINGSTATE_MODECHANGED_MODE_MAX:
            case ARCOMMANDS_ARDRONE3_ANTIFLICKERINGSTATE_MODECHANGED_MODE_FIXEDSIXTYHERTZ:
                btn60Hz.setChecked(true);
                btn60Hz.setClickable(false);
                btn50Hz.setChecked(false);
                btnAutoHz.setChecked(false);
                break;
            default:
            case eARCOMMANDS_ARDRONE3_ANTIFLICKERINGSTATE_MODECHANGED_MODE_UNKNOWN_ENUM_VALUE:

        }

        //Résolution de l'enregistrement

        btn1080p.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btn1080p.isChecked()) {
                    boolean test = GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().setVideo_resolution(ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEORESOLUTIONS_TYPE_ENUM.ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEORESOLUTIONS_TYPE_REC1080_STREAM480);
                    if (test == true) {
                        btn720p.setChecked(false);
                        btn1080p.setClickable(false);
                    } else {
                        btn1080p.setChecked(false);
                    }
                } else {
                    btn720p.setChecked(true);
                    btn1080p.setClickable(true);
                }
            }
        });

        btn720p.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btn720p.isChecked()) {
                    boolean test = GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().setVideo_resolution(ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEORESOLUTIONS_TYPE_ENUM.ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEORESOLUTIONS_TYPE_REC720_STREAM720);
                    if (test == true) {
                        btn1080p.setChecked(false);
                        btn720p.setClickable(false);
                    } else {
                        btn720p.setChecked(false);
                    }
                } else {
                    btn1080p.setChecked(true);
                    btn720p.setClickable(true);
                }
            }
        });

        //Qualité de l'enregistrement
        //TODO Cela ne sert à rien c'est de l'illusion
        btnStdr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnStdr.isChecked()) {
                    btnHigh.setChecked(false);
                    btnStdr.setClickable(false);
                } else {
                    btnHigh.setChecked(true);
                    btnStdr.setClickable(true);
                }
            }
        });
        //TODO Cela ne sert à rien c'est de l'illusion
        btnHigh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnHigh.isChecked()) {
                    btnStdr.setChecked(false);
                    btnHigh.setClickable(false);
                } else {
                    btnStdr.setChecked(true);
                    btnHigh.setClickable(true);
                }
            }
        });

        //Fréquence d'images

        btn24fps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btn24fps.isChecked()) {
                    boolean test = GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().setFramerate(ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOFRAMERATE_FRAMERATE_24_FPS);
                    if (test == true) {
                        btn25fps.setChecked(false);
                        btn30fps.setChecked(false);
                        btn24fps.setClickable(false);
                    } else {
                        btn24fps.setChecked(false);
                    }
                } else {
                    btn24fps.setClickable(true);
                }
            }
        });

        btn25fps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btn25fps.isChecked()) {
                    boolean test = GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().setFramerate(ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOFRAMERATE_FRAMERATE_25_FPS);
                    if (test == true) {
                        btn24fps.setChecked(false);
                        btn30fps.setChecked(false);
                        btn25fps.setClickable(false);
                    } else {
                        btn25fps.setChecked(false);
                    }
                } else {
                    btn25fps.setClickable(true);
                }
            }
        });

        btn30fps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btn30fps.isChecked()) {
                    boolean test = GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().setFramerate(ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOFRAMERATE_FRAMERATE_30_FPS);
                    if (test == true) {
                        btn24fps.setChecked(false);
                        btn25fps.setChecked(false);
                        btn30fps.setClickable(false);
                    } else {
                        btn30fps.setChecked(false);
                    }
                } else {
                    btn30fps.setClickable(true);
                }
            }
        });

        //Stabilisation d'image

        btnStabNon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnStabNon.isChecked()) {
                    boolean test = GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().setVideostabilization(ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOSTABILIZATIONMODE_MODE_NONE);
                    if (test == true) {
                        btnStabOui.setChecked(false);
                        btnStabNon.setClickable(false);
                    } else {
                        btnStabNon.setChecked(false);
                    }
                } else {
                    btnStabOui.setChecked(true);
                    btnStabNon.setClickable(true);
                }
            }
        });

        btnStabOui.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnStabOui.isChecked()) {
                    boolean test = GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().setVideostabilization(ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOSTABILIZATIONMODE_MODE_ROLL_PITCH);
                    if (test == true) {
                        btnStabNon.setChecked(false);
                        btnStabOui.setClickable(false);
                    } else {
                        btnStabOui.setChecked(false);
                    }
                } else {
                    btnStabNon.setChecked(true);
                    btnStabOui.setClickable(true);
                }
            }
        });

        //Mode d'anti-scintillement

        btn50Hz.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btn50Hz.isChecked()) {
                    boolean test = GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().setAntiflickering(ARCOMMANDS_ARDRONE3_ANTIFLICKERING_SETMODE_MODE_FIXEDFIFTYHERTZ);
                    if (test == true) {
                        btn60Hz.setChecked(false);
                        btnAutoHz.setChecked(false);
                        btn50Hz.setClickable(false);
                    } else {
                        btn50Hz.setChecked(false);
                    }
                } else {
                    btn50Hz.setClickable(true);
                }
            }
        });

        btn60Hz.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btn60Hz.isChecked()) {
                    boolean test = GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().setAntiflickering(ARCOMMANDS_ARDRONE3_ANTIFLICKERING_SETMODE_MODE_FIXEDSIXTYHERTZ);
                    if (test == true) {
                        btn50Hz.setChecked(false);
                        btnAutoHz.setChecked(false);
                        btn50Hz.setClickable(false);
                    }
                    else {
                        btn60Hz.setChecked(false);
                    }
                } else {
                    btn50Hz.setClickable(true);
                }
            }
        });

        btnAutoHz.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnAutoHz.isChecked()) {
                    boolean test = GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().setAntiflickering(ARCOMMANDS_ARDRONE3_ANTIFLICKERING_SETMODE_MODE_AUTO);
                    if (test == true){
                        btn50Hz.setChecked(false);
                        btn60Hz.setChecked(false);
                        btnAutoHz.setClickable(false);
                    }
                    else {
                        btnAutoHz.setChecked(false);
                    }
                } else {
                    btnAutoHz.setClickable(true);
                }
            }
        });

        //Gerer le nom du drone à afficher dans le réglages des paramètres

        txtDroneNom.setText(GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().getDroneName());


        //On gère le reset des paramétrages

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1080p.setChecked(true);
                btnStdr.setChecked(true);
                btn30fps.setChecked(true);
                btnStabOui.setChecked(true);
                btnAutoHz.setChecked(true);
            }
        });

        //On gère le passage aux autres menus

        btnPositConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PositionConfActivity = new Intent(VideoConf.this, PositionConf.class);
                PositionConfActivity.putExtra(MessageKEY.POSITIONCOUPLE, correspondant);
                startActivity(PositionConfActivity);
            }
        });

        btnInfoConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent InfoConfActivity = new Intent(VideoConf.this, InfoConf.class);
                InfoConfActivity.putExtra(MessageKEY.POSITIONCOUPLE, correspondant);
                startActivity(InfoConfActivity);
            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoConf.this.finish();
                Intent EssaimConfigActivity = new Intent(VideoConf.this, EssaimConfig.class);
                startActivity(EssaimConfigActivity);
            }
        });
    }

}
