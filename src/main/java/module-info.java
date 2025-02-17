module org.example.markethelper {
    requires transitive java.desktop;
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires de.mkammerer.argon2.nolibs;
    requires com.sun.jna;

    opens org.example.markethelper to javafx.fxml;
    exports org.example.markethelper.View;
    exports org.example.markethelper.Controller;
    exports org.example.markethelper.Model;

}