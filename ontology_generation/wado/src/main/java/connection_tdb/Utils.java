package connection_tdb;

import org.apache.commons.lang3.StringUtils;

public class Utils {

    public static String format(String rawClassName) {
        return StringUtils.join(
                StringUtils.splitByCharacterTypeCamelCase(rawClassName.replaceAll("Think", "")),
                ' '
        );
    }
}
