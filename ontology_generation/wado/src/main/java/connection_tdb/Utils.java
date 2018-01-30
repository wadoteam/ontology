package connection_tdb;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utils {

    public static String format(String rawClassName) {
        return StringUtils.join(
                StringUtils.splitByCharacterTypeCamelCase(rawClassName.replaceAll("(Thin[kg])", "")),
                ' '
        ).toLowerCase();
    }

    public static List<String> getValuesArrayFromMap(Map<String, String> map) {
        List<String> values = new ArrayList<String>();
        for (Map.Entry<String, String> m : map.entrySet()) {
            values.add(m.getValue());
        }
        return values;
    }

    public static List<String> getKeysArrayFromMap(Map<String, String> map) {
        List<String> values = new ArrayList<String>();
        for (Map.Entry<String, String> m : map.entrySet()) {
            values.add(m.getKey());
        }
        return values;
    }
}
