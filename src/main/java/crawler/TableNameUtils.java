package crawler;

public class TableNameUtils {
    public static String getTableNamePostfix() {
        return tableNamePostfix;
    }

    public static void setTableNamePostfix(String postfix) {
        TableNameUtils.tableNamePostfix = postfix;
    }

    static String tableNamePostfix = "" ;
}
