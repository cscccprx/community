//package community;
////
////import org.flywaydb.core.Flyway;
////
////public class App {
////    public static void main(String[] args) {
////        // Create the Flyway instance and point it to the database
//////        Flyway flyway = Flyway.configure().dataSource("jdbc:mysql://localhost:3306/community", "root", null).load();
////        Flyway flyway = Flyway.configure().dataSource(
////                "jdbc:mysql://123.57.19.185:3306/community?useMysqlMetadata=true" ,
////                "root", "123456").load();
////
////        // Start the migration
////        flyway.migrate();
////    }
////}