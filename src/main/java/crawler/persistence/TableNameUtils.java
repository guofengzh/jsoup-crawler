package crawler.persistence;

public class TableNameUtils {
    public static String getTableName() {
        return tableName;
    }

    public static void setTableName(String tableName) {
        TableNameUtils.tableName = tableName;
    }

    static String tableName = "" ;
}
