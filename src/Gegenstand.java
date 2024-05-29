import java.util.HashSet;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
/**
 * Diese Klasse repräsentiert einen Gegenstand mit verschiedenen Attributen
 * wie Name, Beschreibung, Verbrauchbarkeit, Tragbarkeit und Benutzbarkeit.
 * Zudem kann ein Gegenstand eine Menge von benötigten Gegenständen haben.
 *
 * @autor (Ihr Name)
 * @version (eine Versionsnummer oder ein Datum)
 */public class Gegenstand
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private String gegenstandsName;
    private String gegenstandsBeschreibung;
    private boolean verbrauchbar;
    private boolean istTragbar;
    private boolean istBenutzbar;
    private String benoetigtBeschreibung;
    private HashSet<String> benoetigteGegenstaende;
    /**
     * Konstruktor für Objekte der Klasse Gegenstand
     * Initialisiert ein Gegenstandsobjekt basierend auf den Werten im gegebenen JSONObject
     *
     * @param jsonObject Das JSONObject, das die Gegenstandsdaten enthält
     */
    public Gegenstand(JSONObject jsonObject){
        gegenstandsName = (String) jsonObject.get("name");
        gegenstandsBeschreibung = (String) jsonObject.get("beschreibung");
        verbrauchbar = (Boolean) jsonObject.get("verbrauchbar");
        istTragbar= (Boolean) jsonObject.get("tragbar");
        istBenutzbar= (Boolean) jsonObject.get("benutzbar");
        benoetigtBeschreibung = (String) jsonObject.get("benoetigtBeschreibung");
        benoetigteGegenstaende = new HashSet<String>();

        JSONArray jsonBenoetigteGegenstaendeArray = (JSONArray)jsonObject.get("benoetigt");
        if(jsonBenoetigteGegenstaendeArray != null){
            for(Object obj: jsonBenoetigteGegenstaendeArray){
                JSONObject jsonBenoetigterGegenstand = (JSONObject) obj;
                fuegeBenoetigteGegenstaendeHinzu((String) jsonBenoetigterGegenstand.get("name"));
            }
        }

    }

    /**
     * Gibt den Namen des Gegenstands zurück
     *
     * @return Der Name des Gegenstands
     */
    public String gibGegenstandsNamen(){
        return gegenstandsName;
    }

    /**
     * Prüft, ob der Gegenstand verbrauchbar ist
     *
     * @return true, wenn der Gegenstand verbrauchbar ist, sonst false
     */
    public Boolean istVerbrauchbar(){
        return verbrauchbar;
    }

    /**
     * Prüft, ob der Gegenstand tragbar ist
     *
     * @return true, wenn der Gegenstand tragbar ist, sonst false
     */
    public boolean istTragbar(){
        return istTragbar;
    }

    /**
     * Gibt die Beschreibung des Gegenstands zurück
     *
     * @return Die Beschreibung des Gegenstands
     */
    public String gibGegenstandsbeschreibung(){
        return gegenstandsBeschreibung;
    }

    /**
     * Gibt die Menge der benötigten Gegenstände zurück
     *
     * @return Eine HashSet von Strings, die die Namen der benötigten Gegenstände enthält
     */
    public HashSet<String> gibBenoetigteGegenstaende(){
        return benoetigteGegenstaende;
    }

    /**
     * Fügt einen benötigten Gegenstand zur Menge der benötigten Gegenstände hinzu
     *
     * @param gegenstand Der Name des hinzuzufügenden Gegenstands
     */
    private void fuegeBenoetigteGegenstaendeHinzu(String gegenstand){
        if(gegenstand != null){
            benoetigteGegenstaende.add(gegenstand);
        }
    }

    /**
     * Prüft, ob alle benötigten Gegenstände im Inventar vorhanden sind
     *
     * @param inventar Das Inventar, das überprüft werden soll
     * @return true, wenn alle benötigten Gegenstände im Inventar vorhanden sind, sonst false
     */
    public boolean istVorrausetzungErfuellt(Inventar inventar){
        if(benoetigteGegenstaende.isEmpty()){
            return true;
        }else{
            int anzahlGesuchterGegenstaende = benoetigteGegenstaende.size();
            int gefundeneGleiche = 0;
            for(Gegenstand ggst: inventar.gibInventar()){
                for(String s: benoetigteGegenstaende){
                    if(ggst.gibGegenstandsNamen().equals(s)){
                        gefundeneGleiche++;
                    }
                }
            }
            if(gefundeneGleiche == anzahlGesuchterGegenstaende){
                return true;
            }
        }
        return false;
    }

    /**
     * Gibt die benötigten Gegenstände als String zurück
     *
     * @return Ein String, der die benötigten Gegenstände auflistet
     */
    public String gibBenoetigteGegenstaendeAlsString(){
        String str = "Folgende Gegenstaende werden benoetigt:";
        for(String gegenstand: benoetigteGegenstaende){
            str+= " ["+ gegenstand + "]";
        }
        return str;
    }

    /**
     * Prüft, ob der Gegenstand benutzbar ist
     *
     * @return true, wenn der Gegenstand benutzbar ist, sonst false
     */
    public boolean istBenutzbar(){
        return istBenutzbar;
    }

}
