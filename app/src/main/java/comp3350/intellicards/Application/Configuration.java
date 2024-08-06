package comp3350.intellicards.Application;

public class Configuration {

    /**
     * Recognized Values
     * - hsqldb
     * - testHsqldb
     * If value is not recognized, datasource will default to hsqldb
     */
    private static final String datasource = "hsqldb";
    private static String dbName;
    private static String dbPathName;

    public static String getDatasource() {
        return datasource;
    }

    public static String getDbName() {
        return dbName;
    }

    public static void setDbName(String dbName) {
        Configuration.dbName = dbName;
    }

    public static void setDBPathName(final String name) {
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        dbPathName = name;
    }

    public static String getDBPathName() {
        return dbPathName;
    }
}
