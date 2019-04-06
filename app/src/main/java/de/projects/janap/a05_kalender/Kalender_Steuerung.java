package de.projects.janap.a05_kalender;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Kalender_Steuerung extends AppCompatActivity {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    /*-------------------------Darstellung--------------------------------------------------------*/
    private TextView txtMonatAnzeige, txtHeutigerTag, txtMomentanesDatum;
    private ImageView neuerTermin;
    private GridView tabelleAktuellerMonat;
    private Button btnZuvor, btnWeiter;
    private LinearLayout wochentage;
    private ConstraintLayout trennbalken;


    /*-------------------------Andere Variablen---------------------------------------------------*/
    private String[] bezeichnungen = new String[11];
    private String[] farben = {"#FBC765", "#F08563", "#E76062", "#E53C6E", "#DC276B", "#9D286C", "#742964", "#562363", "#292563", "#153D6B", "#2A6C7C", "#40BD9C"};
    private int[] zellenMax = new int[42]; //42 ist die maximale Anzahl der Zellen die die Tabelle brauchen koennte
    private String aktuelleFarbe;
    private Termin[] termine;
    private int anzahlTermine = 0;


    /*-------------------------Kalender-----------------------------------------------------------*/
    private Calendar kalender = Calendar.getInstance(); //erstellt einen Kalender mit aktuellen Datum Angaben
    static Calendar heute = Calendar.getInstance(); //Kalender mit heutigem Datum


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden
    /*-------------------------Set Methoden-------------------------------------------------------*/
    public void setTxtMonatAnzeige(String pNeuerMonat) {
        txtMonatAnzeige.setText(pNeuerMonat);
    }
    public void setTxtMomentanesDatum(int pTag) {
        kalender.set(Calendar.DAY_OF_MONTH, pTag);
        txtMomentanesDatum.setText(getString(R.string.datum, kalender.get(Calendar.DAY_OF_MONTH), kalender.get(Calendar.MONTH) + 1, kalender.get(Calendar.YEAR) ));
    }

    /*-------------------------Get Methoden-------------------------------------------------------*/
    public String getAktuelleFarbe() {
        aktuelleFarbe = farben[kalender.get(Calendar.MONTH)];
        return aktuelleFarbe;
    }

    /*-------------------------Andere Methoden----------------------------------------------------*/
    public void oeffneActifityNeuerTermin(){
        Intent intent = new Intent(this, TerminErstellung.class);
        startActivity(intent);
    }
    public void setNeuerTermin(Termin pTermin){
        termine[anzahlTermine] = pTermin;
        anzahlTermine++;
    }

    public void aktualisiereKalender() {
        Kalender_Adapter adapterAktuellerMonat = new Kalender_Adapter(this, zellenMax, kalender, this); //KalenderAdapter um den Kalender in der Tabelle darzustellen


        setTxtMonatAnzeige(bezeichnungen[kalender.get(Calendar.MONTH)]);  //setzt die neue Monatsbezeichnung fest

        txtMomentanesDatum.setText("" + (kalender.get(Calendar.YEAR)));
        tabelleAktuellerMonat.setAdapter(adapterAktuellerMonat);    //Kalender wird dargestellt

        aendereFarbe(kalender.get(Calendar.MONTH));
    }
    public void aendereFarbe(int pMonat) {
        int farbe = Color.parseColor(farben[pMonat]);

        txtMonatAnzeige.setBackgroundColor(farbe);
        txtMomentanesDatum.setBackgroundColor(farbe);
        btnZuvor.setBackgroundColor(farbe);
        btnWeiter.setBackgroundColor(farbe);
        wochentage.setBackgroundColor(farbe);
        trennbalken.setBackgroundColor(farbe);

    }
    public void initialisieren(){
        tabelleAktuellerMonat = findViewById(R.id.gridView_Kalender_Tabelle_AktuellerMonat);

        txtMonatAnzeige = findViewById(R.id.txt_Kalender_Monat);
        txtHeutigerTag = findViewById(R.id.txt_Kalender_HeutigerTag);
        txtMomentanesDatum = findViewById(R.id.txt_Kalender_Momentanes_Datum);
        btnZuvor = findViewById(R.id.btn_Kalender_Zuvor);
        btnWeiter = findViewById(R.id.btn_Kalender_Weiter);
        wochentage = findViewById(R.id.layout_Kalender_Wochentage);
        trennbalken = findViewById(R.id.trennbalken);
        neuerTermin = findViewById(R.id.image_neuerTermin);
        bezeichnungen = getResources().getStringArray(R.array.monate);

        txtHeutigerTag.setText("" + (kalender.get(Calendar.DAY_OF_MONTH)));
        termine = new Termin[100];
    }
    public void setztenDerOnClickListener(){
        btnZuvor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kalender.add(Calendar.MONTH, -1);    //der Monat des Kalenders wird um eins reduziert
                aktualisiereKalender(); //der Monat wird mit den momentanen Daten des Kalenders dargestellt
            }
        });

        btnWeiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kalender.add(Calendar.MONTH, 1); //der Monat des Kalenders wird um eins addiert
                aktualisiereKalender(); //der Monat wird mit den momentanen Daten des Kalenders dargestellt
            }
        });

        txtHeutigerTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kalender.set(Calendar.YEAR, heute.get(Calendar.YEAR));
                kalender.set(Calendar.MONTH, heute.get(Calendar.MONTH));
                aktualisiereKalender();
                txtMomentanesDatum.setText(getString(R.string.datum, heute.get(Calendar.DAY_OF_MONTH), heute.get(Calendar.MONTH) + 1, heute.get(Calendar.YEAR) ));

            }
        });

        neuerTermin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeffneActifityNeuerTermin();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    } //App wird beendet wenn die Rücktaste benutzt wurde

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Erstellung
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalender__gui);

        initialisieren();   //Allen Variablen wird ihr Wert zugeordnet
        aktualisiereKalender();     //der Monat wird mit den momentanen Daten des Kalenders dargestellt

        txtMomentanesDatum.setText(getString(R.string.datum, heute.get(Calendar.DAY_OF_MONTH), heute.get(Calendar.MONTH) + 1, heute.get(Calendar.YEAR) ));

        setztenDerOnClickListener();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ende der Klasse
}

