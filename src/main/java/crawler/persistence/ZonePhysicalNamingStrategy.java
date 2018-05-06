package crawler.persistence;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZonePhysicalNamingStrategy implements PhysicalNamingStrategy {
    final static Logger logger = LoggerFactory.getLogger(ZonePhysicalNamingStrategy.class);

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        System.out.println("****:" + name.getCanonicalName());
        String tableNamePostfix = TableNameUtils.getTableNamePostfix() ;
        String tableName = null ;
        switch (name.getCanonicalName()) {
            case "product":
                tableName = "crawler_data_" + tableNamePostfix ;
                break;
            case "dailyproduct":
                tableName = "crawler_daily_data_" + tableNamePostfix ;
                break;
        }
        System.out.println("****:" + tableName);

        Identifier identifier = Identifier.toIdentifier(tableName) ;
        return identifier;
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }
}