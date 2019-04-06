package de.projects.janap.a05_kalender;

public class Termin {
    private String id;
    private String titel, startDatum, endDatum, startZeit, endZeit;
    private Boolean intervall;

    public Termin (String pTitel, String pStartDatum, String pStartZeit, String pEndDatum, String pEndZeit, Boolean pIntervall){
        titel = pTitel;
        startDatum = pStartDatum;
        endDatum = pEndDatum;
        startZeit = pStartZeit;
        endZeit = pEndZeit;
        intervall = pIntervall;
    }

    public String getTermin(){
        String termin = titel + " | " + startDatum + " | " + startZeit + " | " + endDatum + " | " + endZeit + " | " + intervall;
        return termin;
    }

}
