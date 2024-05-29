import java.util.List;
import java.util.HashSet;
import java.util.HashMap;
/**
 * Diese Klasse modelliert ein Inventar, welches Gegenstände enthält.
 *
 * @author (Ihr Name)
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Inventar
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private HashSet<Gegenstand> inventar;
    /**
     * Konstruktor für Objekte der Klasse Inventar.
     * Initialisiert das Inventar als leere Menge.
     */
    public Inventar()
    {
        // Instanzvariable initialisieren
        inventar = new HashSet<Gegenstand>();

    }

    /**
     * Hebt den Gegenstand auf und tut dies in das Inventar
     * @param gegenstand der aufgehobene Gegenstand
     */
    public void gegenstandHinzufuegen(Gegenstand gegenstand){
        inventar.add(gegenstand);
    }

    /**
     * Legt den Gegenstand ab und entfernt dieses aus dem Inventar
     * @param gegenstand der zu ablegende Gegenstand
     */
    public void gegenstandEntfernen(Gegenstand gegenstand){
        inventar.remove(gegenstand);
    }


    /**
     * Gibt das Inventar zurück.
     * @return inventar das komplette Inventar
     */
    public HashSet<Gegenstand> gibInventar(){
        return inventar;
    }


    /**
     * Gibt eine textuelle Darstellung des Inventars zurück.
     * @return eine Zeichenkette, die alle Gegenstände im Inventar beschreibt.
     */
    public String gibInventarAlsString(){
        String gegenstaende = "Im Inventar:";
        for(Gegenstand gegenstand:inventar){
            gegenstaende +=" [" + (gegenstand.gibGegenstandsNamen()) + "]";
        }
        return gegenstaende;
    }

    /**
     * Gibt einen Gegenstand aus dem Inventar zurück, basierend auf seinem Namen.
     * @param gegenstand der Name des gesuchten Gegenstandes.
     * @return der Gegenstand mit dem gegebenen Namen oder null, falls nicht vorhanden.
     */
    public Gegenstand gibGegenstandAusInventar(String gegenstand){
        for(Gegenstand ggst: inventar){
            if(ggst.gibGegenstandsNamen().equals(gegenstand)){
                return ggst;
            }
        }
        return null;
    }

    /**
     * Liefert eine Beschreibung der Gegenstände im Inventar.
     * @return eine Zeichenkette, die die Gegenstände im Inventar beschreibt.
     */
    public String gibGegenstaendeAlsString()
    {
        String ergebnis = "";
        //Set<String> keys = ausgaenge.keySet();
        for(Gegenstand ggst : inventar)
            ergebnis += " " + "[" + ggst.gibGegenstandsNamen() + "]";
        return ergebnis;
    }


}
