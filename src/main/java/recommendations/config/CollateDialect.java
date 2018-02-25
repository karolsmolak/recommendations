package recommendations.config;

import org.hibernate.dialect.MySQL5Dialect;

public class CollateDialect extends MySQL5Dialect {
    @Override
    public String getTableTypeString() {
        return " COLLATE utf8_bin";
    }
}
