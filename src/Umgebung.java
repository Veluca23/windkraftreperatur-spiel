import java.util.HashMap;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
/**
 * Die komplette Spielumgebung des Spiels.
 *
 * @author (Veton Rieckenberg)
 * @version (22.05.2024)
 */
public class Umgebung
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private static final String DefaultFILE = "umgebung.json";
    private Raum startRaum;
    /**
     * Konstruktor für Objekte der Klasse Umgebung.
     * Initialisiert die Umgebung mit der Standard-JSON-Datei.
     * @throws Exception falls ein Fehler beim Lesen der JSON-Datei auftritt.
     */
    public Umgebung() throws Exception
    {
        // Instanzvariable initialisieren
        this(DefaultFILE);
    }

    /**
     * Konstruktor für Objekte der Klasse Umgebung.
     * Initialisiert die Umgebung mit einer angegebenen JSON-Datei.
     * @param filename der Name der JSON-Datei, die die Umgebung beschreibt.
     * @throws Exception falls ein Fehler beim Lesen der JSON-Datei auftritt.
     */
    public Umgebung(String filename) throws Exception{
        assert (filename != null && filename.contains(".json"));
        JSONParser parser = new JSONParser();
        JSONArray umgebungJSON = (JSONArray) parser.parse(new java.io.FileReader(filename));

        // Hier werden die Räume gespeichert
        HashMap<String,Raum>raeume = new HashMap<String,Raum>();

        // Erstelle Räume
        for(int i = 0;i<umgebungJSON.size();i++){
            JSONObject jsonObject = (JSONObject)umgebungJSON.get(i); // aktueller JSON Object im index i
            Raum raum = new Raum(jsonObject);
            raeume.put(raum.gibRaumNamen(),raum);

            // Der erste Raum ist der Startraum
            if(i == 0){
                startRaum = raum;
            }
        }

        // Erstelle Ausgänge
        for( Object obj: umgebungJSON){
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray jsonAusgaengeArray = (JSONArray)jsonObject.get("ausgaenge");
            for(int i = 0; i < jsonAusgaengeArray.size(); i++){
                JSONObject aktuellerAusgangJSON = (JSONObject) jsonAusgaengeArray.get(i);
                Raum raum = new Raum (jsonObject);
                raum = raeume.get(raum.gibRaumNamen());
                raum.setzeAusgang((String)aktuellerAusgangJSON.get("richtung"), raeume.get((String)aktuellerAusgangJSON.get("name")));

            }
        }

        // Erstelle Gegenstände und füge sie den Räumen hinzu
        for(Object obj: umgebungJSON){
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray jsonGegenstandArray = (JSONArray)jsonObject.get("gegenstaende");
            if(jsonGegenstandArray != null){
                for(int i = 0; i < jsonGegenstandArray.size(); i++){
                    JSONObject aktuellerGegenstandJSON = (JSONObject) jsonGegenstandArray.get(i);
                    Gegenstand aktuellerGegenstand = new Gegenstand(aktuellerGegenstandJSON);
                    raeume.get((String) jsonObject.get("name")).gibRaumInventar().gegenstandHinzufuegen(aktuellerGegenstand);
                }
            }
        }
    }

    /**
     * Gibt den Startpunkt der Umgebung
     * @return startRaum der Startraum der Umgebung
     */
    public Raum gibStartRaum(){
        return startRaum;
    }


}
