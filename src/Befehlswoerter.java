import java.util.HashMap;

/**
 * Diese Klasse hält eine Aufzählung aller Befehlswörter, die der Spieler
 * eingeben kann. Mit ihrer Hilfe werden eingetippte Befehle erkannt. <br>
 * (Wolfgang Renz:) Sie werden jeweils einem reservierten
 * Befehlswort zugeordnet. <br>
 * So könnten also verschiedene sprachliche Befehle dem Befehlswor.QUIT
 * zugeordnet werden, z.B. "ade", "ciao", "quit".<br>
 * Ebenfalls kann der Spieler auf diese Weise Befehle in unterschiedlichen
 * Sprachen geben, z.B. "go", "walk", "gehe", "nach".<br>
 * Auf diese Weise gewinnt man durch Nutzung von enum Befehlswort Flexibilität.
 *
 * @author  Michael Kölling und David J. Barnes, Wolfgang Renz
 * @version 31.07.2011, April 2014
 */

class Befehlswoerter
{
    // eine Abbildung von Befehlswörtern auf Elemente des
    // Aufzählungstyps Befehlswort
    private HashMap<String, Befehlswort> gueltigeBefehle;

    /**
     * Konstruktor - initialisiere die Befehlswörter.
     */
    public Befehlswoerter()
    {
        gueltigeBefehle = new HashMap<String, Befehlswort>();
        gueltigeBefehle.put("go", Befehlswort.GO);
        gueltigeBefehle.put("help", Befehlswort.HELP);
        gueltigeBefehle.put("quit", Befehlswort.QUIT);
        gueltigeBefehle.put("use", Befehlswort.USE);
        gueltigeBefehle.put("pickup", Befehlswort.PICKUP);
        gueltigeBefehle.put("drop", Befehlswort.DROP);
        gueltigeBefehle.put("search", Befehlswort.SEARCH);
        gueltigeBefehle.put("inspect", Befehlswort.INSPECT);
        gueltigeBefehle.put("bag", Befehlswort.BAG);
    }

    /**
     * Finde das Befehlswort, das mit einem Befehls-String verknüpft ist.
     * @param befehlswort das nachzuschlagende Wort (als String)
     * @return Das zugehörige Befehlswort zum dem Wort oder UNKNOWN,
     *         wenn das Wort kein gültiges Befehlswort ist.
     */
    public Befehlswort gibBefehlswort(String wort)
    {
        Befehlswort befehlswort = gueltigeBefehle.get(wort);
        if(befehlswort != null) {
            return befehlswort;
        }
        else {
            return Befehlswort.UNKNOWN;
        }
    }

    /**
     * Prüfe, ob eine gegebene Zeichenkette ein gültiger
     * Befehl ist.
     * @return 'true', wenn die gegebene Zeichenkette ein gültiger
     * Befehl ist, 'false' sonst.
     */
    public boolean istBefehl(String eingabe)
    {
        return gueltigeBefehle.containsKey(eingabe);
    }

    /**
     * Gib alle gültigen Befehlswörter auf die Konsole aus.
     */
    public void alleAusgeben()
    {
        for(String befehl : gueltigeBefehle.keySet()) {
            System.out.print(befehl + "  ");
        }
        System.out.println();
    }
}
