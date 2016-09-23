package game_player.utilities;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class UnitTypeResourceBundleProcessor {

    private Map<String, String> mySymbols;

    public UnitTypeResourceBundleProcessor () {
        this.mySymbols = new HashMap<>();
    }

    public String getSymbol (String text) {
        return mySymbols.get(text);
    }

    public void addPatterns (String syntax) {
        ResourceBundle resources = ResourceBundle.getBundle(syntax);
        Enumeration<String> iter = resources.getKeys();
        while (iter.hasMoreElements()) {
            String key = iter.nextElement();
            String value = resources.getString(key);
            mySymbols.put(key, value);
        }
    }
    
    public boolean testUnitTypePreference (String type,
                                                  String preference) {
        String[] unitTypes = this.getSymbol(preference).split(",");
        for (int i = 0; i < unitTypes.length; i++) {
            if (type.contains(unitTypes[i])) {
                return true;
            }
        }
        return false;
    }

}
