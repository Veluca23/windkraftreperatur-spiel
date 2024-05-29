import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 *  Dies ist die Hauptklasse der Anwendung "Die Welt von Zuul".
 *  "Die Welt von Zuul" ist ein sehr einfaches, textbasiertes
 *  Adventure-Game. Ein Spieler kann sich in einer Umgebung bewegen,
 *  mehr nicht. Das Spiel sollte auf jeden Fall ausgebaut werden,
 *  damit es interessanter wird!
 *
 *  Zum Spielen muss eine Instanz dieser Klasse erzeugt werden und
 *  an ihr die Methode "spielen" aufgerufen werden.
 *
 *  Diese Instanz dieser Klasse erzeugt und initialisiert alle
 *  anderen Objekte der Anwendung: Sie legt alle Räume und einen
 *  Parser an und startet das Spiel. Sie wertet auch die Befehle
 *  aus, die der Parser liefert und sorgt für ihre Ausführung.
 *
 * @author  Michael Kölling und David J. Barnes
 * @version 31.07.2011
 */

class Spiel
{
    private Parser parser;
    private Raum aktuellerRaum;
    private Inventar inventar;
    private Umgebung umgebung;
    private int versuche;
    private boolean gewonnen = false;

    /**
     * Erzeuge ein Spiel und initialisiere die interne Raumkarte.
     */
    public Spiel()
    {
        versuche = 3;
        parser = new Parser();
        inventar = new Inventar();
        try{
            umgebung = new Umgebung();
            aktuellerRaum = umgebung.gibStartRaum();
        }catch(Exception e){
            System.err.print("JSON Datei fehlerhaft/nicht vorhanden");
        }

    }

    /**
     * Die Hauptmethode zum Spielen. Läuft bis zum Ende des Spiels
     * in einer Schleife.
     */
    public void spielen()
    {
        willkommenstextAusgeben();

        // Die Hauptschleife. Hier lesen wir wiederholt Befehle ein
        // und führen sie aus, bis das Spiel beendet wird.

        boolean beendet = false;
        while (!beendet) {
            Befehl befehl = parser.liefereBefehl();
            beendet = verarbeiteBefehl(befehl);
            if(gewonnen){
                System.out.println("Sie haben die Anlage repariert! Gut gemacht!");
                beendet = true;
            }
        }
        System.out.println("Danke für dieses Spiel. Auf Wiedersehen.");
    }

    /**
     * Einen Begrüßungstext für den Spieler ausgeben.
     */
    private void willkommenstextAusgeben()
    {
        System.out.println();
        System.out.println("Willkommen, frischgebackener Elektroingenieur!");
        System.out.println("Dein Ziel ist es, den Generator der Windkraftanlage zu reparieren.");
        System.out.println("Auf deinem Weg wirst du hilfreiche Gegenstände finden, die dir bei deinen Reparaturen nützlich sein werden.");
        System.out.println("Tippen sie 'help', wenn Sie Hilfe brauchen.");
        System.out.println();
        System.out.println(aktuellerRaum.gibLangeBeschreibung());

    }

    /**
     * Verarbeite einen gegebenen Befehl (führe ihn aus).
     * @param befehl Der zu verarbeitende Befehl.
     * @return 'true', wenn der Befehl das Spiel beendet, 'false' sonst.
     */
    private boolean verarbeiteBefehl(Befehl befehl)
    {
        boolean moechteBeenden = false;

        Befehlswort befehlswort = befehl.gibBefehlswort();

        switch(befehlswort) {
            case UNKNOWN:
                System.out.println("Ich weiss nicht, was Sie meinen...");
                break;

            case HELP:
                hilfstextAusgeben();
                break;

            case GO:
                wechsleRaum(befehl);
                break;

            case QUIT:
                moechteBeenden = beenden(befehl);
                break;

            case PICKUP:
                aufhebenGegenstand(befehl);
                break;

            case DROP:
                ablegenGegenstand(befehl);
                break;

            case SEARCH:
                //System.out.println(aktuellerRaum.gibLangeBeschreibung());
                System.out.println(aktuellerRaum.gibAusgaengeMitNamenAlsString());
                break;

            case INSPECT:
                betrachteGegenstand(befehl);
                break;

            case BAG:
                System.out.println(inventar.gibInventarAlsString());
                break;

            case USE:
                benutzeGegenstand(befehl);
                break;


        }
        // ansonsten: Befehl nicht erkannt.
        return moechteBeenden;
    }

    // Implementierung der Benutzerbefehle:

    /**
     * Gib Hilfsinformationen aus.
     * Hier geben wir eine etwas alberne und unklare Beschreibung
     * aus, sowie eine Liste der Befehlswörter.
     */
    private void hilfstextAusgeben()
    {
        System.out.println("Ihnen stehen folgende Befehle zur Verfügung:");
        parser.zeigeBefehle();
    }

    /**
     * Versuche, in eine Richtung zu gehen. Wenn es einen Ausgang gibt,
     * wechsele in den neuen Raum, ansonsten gib eine Fehlermeldung
     * aus.
     */
    private void wechsleRaum(Befehl befehl)
    {
        if(!befehl.hatZweitesWort()) {
            // Gibt es kein zweites Wort, wissen wir nicht, wohin...
            System.out.println("Wohin möchten Sie gehen?");
            return;
        }

        String richtung = befehl.gibZweitesWort();

        // Wir versuchen, den Raum zu verlassen.
        Raum naechsterRaum = aktuellerRaum.gibAusgang(richtung);

        if (naechsterRaum == null) {
            System.out.println("Dort ist keine Tür!");
        }
        else {
            aktuellerRaum = naechsterRaum;
            System.out.println(aktuellerRaum.gibLangeBeschreibung());
        }
    }

    /**
     * Versuche einen Gegenstand aufzuheben, ansonsten gib eine Fehlermeldung aus.
     * @param befehl der Befehl der angewendet wird
     */
    private void aufhebenGegenstand(Befehl befehl){
        if(!befehl.hatZweitesWort()) {
            // Gibt es kein zweites Wort, wissen wir nicht, wohin...
            System.out.println("Welchen Gegenstand möchten Sie aufheben?");
            return;
        }

        Gegenstand gegenstand = aktuellerRaum.gibRaumInventar().gibGegenstandAusInventar(befehl.gibZweitesWort());
        if(gegenstand != null){
            if(gegenstand.istTragbar() && gegenstand.istVorrausetzungErfuellt(inventar)){
                inventar.gegenstandHinzufuegen(gegenstand);
                aktuellerRaum.gibRaumInventar().gegenstandEntfernen(gegenstand);
                System.out.println("["+ gegenstand.gibGegenstandsNamen()+ "]" +" wurde erfolgreich aufgehoben.");
            } else{
                System.out.println("["+ gegenstand.gibGegenstandsNamen()+ "]" +" kann nicht aufgehoben werden.");
                System.out.println(gegenstand.gibBenoetigteGegenstaendeAlsString());
            }
        }else{
            System.out.println("[" + befehl.gibZweitesWort() + "]" + " ist nicht im Raum.");
        }

    }


    /**
     * Versuche einen Gegenstand zu benutzen, ansonsten gib eine Fehlermeldung aus.
     * @param befehl der Befehl der angewendet wird
     */
    private void benutzeGegenstand(Befehl befehl){
        if(!befehl.hatZweitesWort()) {
            // Gibt es kein zweites Wort, wissen wir nicht, wohin...
            System.out.println("Welchen Gegenstand möchten Sie benutzen?");
            return;
        }
        Gegenstand gegenstand = aktuellerRaum.gibRaumInventar().gibGegenstandAusInventar(befehl.gibZweitesWort());
        if(gegenstand != null){
            if(gegenstand.istBenutzbar() && gegenstand.istVorrausetzungErfuellt(inventar)){
                if(gegenstand.gibGegenstandsNamen().equals("Generator")){
                    gewonnen = true;
                }
            } else{
                System.out.println(gegenstand.gibGegenstandsNamen() + " kann nicht benutzt werden");
                System.out.println(gegenstand.gibBenoetigteGegenstaendeAlsString());
            }
        }else{
            System.out.println(gegenstand.gibGegenstandsNamen() + " kann nicht gefunden werden");

        }

    }

    /**
     * Versuche einen Gegenstand abzulegen, ansonsten gib eine Fehlermeldung aus.
     * @param befehl der Befehl der angewendet wird
     */
    private void ablegenGegenstand(Befehl befehl){
        if(!befehl.hatZweitesWort()) {
            // Gibt es kein zweites Wort, wissen wir nicht, wohin...
            System.out.println("Welchen Gegenstand möchten Sie ablegen?");
            return;
        }

        Gegenstand gegenstand = inventar.gibGegenstandAusInventar(befehl.gibZweitesWort());
        if(gegenstand != null){
            inventar.gegenstandEntfernen(gegenstand);
            aktuellerRaum.gibRaumInventar().gegenstandHinzufuegen(gegenstand);
            System.out.println("[" + gegenstand.gibGegenstandsNamen() + "]" + " wurde abgelegt.");
        } else{
            System.out.println("[" + befehl.gibZweitesWort() + "]" + " ist nicht im Inventar.");
        }
    }

    /**
     * Versuche einen Gegenstand zu betrachten, ansonsten gib eine Fehlermeldung aus.
     * @param befehl der Befehl der angewendet wird
     */
    private void betrachteGegenstand(Befehl befehl){
        if(!befehl.hatZweitesWort()) {
            // Gibt es kein zweites Wort, wissen wir nicht, wohin...
            System.out.println("Welchen Gegenstand möchten Sie betrachten?");
            return;
        }
        Gegenstand gegenstand = inventar.gibGegenstandAusInventar(befehl.gibZweitesWort());
        if(gegenstand != null){
            System.out.println("["+ gegenstand.gibGegenstandsNamen()+ "]:" + gegenstand.gibGegenstandsbeschreibung());
        }else{
            System.out.println("[" + befehl.gibZweitesWort() + "]" + " ist nicht im Inventar.");
        }

    }

    /**
     * "quit" wurde eingegeben. Überprüfe den Rest des Befehls,
     * ob das Spiel wirklich beendet werden soll.
     * @return 'true', wenn der Befehl das Spiel beendet, 'false' sonst.
     */
    private boolean beenden(Befehl befehl)
    {
        if(befehl.hatZweitesWort()) {
            System.out.println("Was soll beendet werden?");
            return false;
        }
        else {
            return true;  // Das Spiel soll beendet werden.
        }
    }

    /**
     * Ueberprueft ob das Spiel verloren wurde.
     * @return true ob das Spiel verloren wurde
     */
    private boolean gameover(){
        if(versuche == 0){
            System.out.println("Sie haben leider verloren.");
            return true;
        }else{
            return false;
        }
    }
}

