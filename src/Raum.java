import java.util.HashMap;
import org.json.simple.JSONObject;
import java.util.Set;



public class Raum {
    private String name;
    private String beschreibung;
    private HashMap<String, Raum> ausgaenge; // die Ausgänge dieses Raums
    private Inventar inventar; // Gegenstände im Raum


    /**
     * Erzeuge einen Raum mit einer Beschreibung. Ein Raum
     * hat anfangs keine Ausgänge.
     * @param jsonObject ein JSON-Objekt, das den Namen und die Beschreibung des Raumes enthält.
     */
    public Raum(JSONObject jsonObject){
        ausgaenge = new HashMap<String, Raum>();
        this.name = (String) jsonObject.get("name");
        this.beschreibung = (String) jsonObject.get("beschreibung");
        this.inventar = new Inventar();

    }


    /**
     * Definiere einen Ausgang für diesen Raum.
     * @param richtung die Richtung, in der der Ausgang liegen soll
     * @param nachbar der Raum, der über diesen Ausgang erreicht wird
     */
    public void setzeAusgang(String richtung, Raum nachbar)
    {
        ausgaenge.put(richtung, nachbar);
    }

    /**
     * Definiere einen Ausgang für diesen Raum basierend auf einem JSON-Objekt.
     * @param jsonObject ein JSON-Objekt, das die Richtung und den Nachbarraum enthält.
     */
    public void setzeAusgang(JSONObject jsonObject){
        String richtung = (String) jsonObject.get("richtung");
        Raum nachbar = (Raum) jsonObject.get("name");
        ausgaenge.put(richtung, nachbar);
    }

    /**
     * @return die kurze Beschreibung dieses Raums (die dem Konstruktor
     * übergeben wurde).
     */
    public String gibKurzbeschreibung()
    {
        return beschreibung;
    }

    /**
     * Liefere eine lange Beschreibung dieses Raums, in der Form:
     *     Sie sind in der Küche.
     *     Ausgänge: nord west
     * @return eine lange Beschreibung dieses Raumes.
     */
    public String gibLangeBeschreibung()
    {
        return "Sie sind " + beschreibung + ".\n" + gibAusgaengeAlsString() + ".\n" + "Gegenstände in der Umgebung: "+ inventar.gibGegenstaendeAlsString();
    }

    /**
     * Liefere eine Beschreibung der Ausgänge dieses Raumes,
     * beispielsweise
     * "Ausgänge: north west".
     * @return eine Beschreibung der Ausgänge dieses Raumes.
     */
    private String gibAusgaengeAlsString()
    {
        String ergebnis = "Ausgänge:";
        Set<String> keys = ausgaenge.keySet();
        for(String ausgang : keys)
            ergebnis += " " + ausgang;
        return ergebnis;
    }

    /**
     * Liefere den Raum, den wir erreichen, wenn wir aus diesem Raum
     * in die angegebene Richtung gehen. Liefere 'null', wenn in
     * dieser Richtung kein Ausgang ist.
     * @param richtung die Richtung, in die gegangen werden soll.
     * @return den Raum in der angegebenen Richtung.
     */
    public Raum gibAusgang(String richtung)
    {
        return ausgaenge.get(richtung);
    }

    /**
     * Liefere das Inventar des Raumes.
     * @return das Inventar des Raumes.
     */
    public Inventar gibRaumInventar(){
        return inventar;
    }

    /**
     * Liefere den Namen des Raumes.
     * @return den Namen des Raumes.
     */
    public String gibRaumNamen(){
        return name;
    }

    /**
     * Liefere eine Beschreibung der Ausgänge dieses Raumes zusammen mit den Namen der Nachbarräume.
     * @return eine Beschreibung der Ausgänge dieses Raumes zusammen mit den Namen der Nachbarräume.
     */
    public String gibAusgaengeMitNamenAlsString(){
        String str = "";
        for(String richtung: ausgaenge.keySet()){
            str += richtung + " " + ausgaenge.get(richtung).gibRaumNamen() + "\n";
        }

        return str;
    }
}
