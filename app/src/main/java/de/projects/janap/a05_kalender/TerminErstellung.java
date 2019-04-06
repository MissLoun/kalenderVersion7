package de.projects.janap.a05_kalender;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Calendar;


public class TerminErstellung extends AppCompatActivity {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    /*-------------------------Darstellung--------------------------------------------------------*/
    private EditText titel,startDatum, startZeit, enddatum, endzeit;
    private Switch ganztaegigJaNein;
    private Button speichern, abbruch;
    private TextView ueberpruefung;

    /*-------------------------Andere Variablen---------------------------------------------------*/
    private Termin neuerTermin;

    private Kalender_Steuerung strg = new Kalender_Steuerung();

    private Boolean titelVorhanden = false;
    private Boolean startDatumVorhanden = false;
    private Boolean startZeitVorhanden = false;
    private Boolean enddatumVorhanden = false;
    private Boolean endzeitVorhanden = false;
    private Boolean ganztaegigJaNeinVorhanden = false;

    private Boolean ganztaegig = false;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden
    /*-------------------------Set Methoden-------------------------------------------------------*/


    /*-------------------------Get Methoden-------------------------------------------------------*/


    /*-------------------------Andere Methoden----------------------------------------------------*/
    public void oeffneActivityKalenderMonatsUebersicht(){
        Intent intent = new Intent(this, Kalender_Steuerung.class);
        startActivity(intent);
        finish();
    }
    public void initialisieren(){
        titel = findViewById(R.id.txt_Terminerstellung_Eingabe_Titel);
        ganztaegigJaNein = findViewById(R.id.switch_Terminerstellung_Eingabe_JaNein);
        startDatum = findViewById(R.id.txt_Terminerstellung_Eingabe_Datum_Start);
        startZeit = findViewById(R.id.txt_Terminerstellung_Eingabe_Zeit_Start);
        enddatum = findViewById(R.id.txt_Terminerstellung_Eingabe_Datum_Ende);
        endzeit = findViewById(R.id.txt_Terminerstellung_Eingabe_Zeit_Ende);
        speichern = findViewById(R.id.btn_Terminerstellung_Speichern);
        abbruch = findViewById(R.id.btn_Terminerstellung_Abbrechen);
        ueberpruefung = findViewById(R.id.txt_Gespeichert);

        //setzten der beispielhaften Daten
        startDatum.setText(getString(R.string.datum, Kalender_Steuerung.heute.get(Calendar.DAY_OF_MONTH)+1, Kalender_Steuerung.heute.get(Calendar.MONTH), Kalender_Steuerung.heute.get(Calendar.YEAR) ));
        enddatum.setText(getString(R.string.datum, Kalender_Steuerung.heute.get(Calendar.DAY_OF_MONTH)+1, Kalender_Steuerung.heute.get(Calendar.MONTH), Kalender_Steuerung.heute.get(Calendar.YEAR) ));

        Date jetzigerTag = new Date();
        startZeit.setText(new SimpleDateFormat("HH:mm").format(jetzigerTag));
        endzeit.setText(new SimpleDateFormat("HH:mm").format(jetzigerTag));

    }
    public void setztenDerOnClickListener(){
        abbruch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeffneActivityKalenderMonatsUebersicht();
            }
        });
        speichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terminDatenSpeichern();
            }
        });
    }
    public void terminDatenSpeichern(){
        neuerTermin = new Termin(titel.getText().toString(), startDatum.getText().toString(), startZeit.getText().toString(), enddatum.getText().toString(), endzeit.getText().toString(), ganztaegig);
        ueberpruefung.setText(neuerTermin.getTermin());
        strg.setNeuerTermin(neuerTermin);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Erstellung
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_erstellung);

        initialisieren();
        setztenDerOnClickListener();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ende der Klasse

}
