package Clases;

import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.PropertyResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConexionBD1 {

    public PreparedStatement prest = null;
    public CallableStatement cllst = null;
    public static Connection conec = null;
    public Connection conec1 = null;
    public Statement st = null, st1 = null;
    public ResultSet rt = null, rt1 = null;
    public static String user, password, host, db, usu, contraseña, Destinobc, rtmysql;

    private static ConexionBD1 instance = null;

    static {
        String properties = "Configurar.properties";
        PropertyResourceBundle file;
        try {
            file = new PropertyResourceBundle(new FileInputStream(properties));
            user = file.getString("user");
            password = file.getString("password");
            host = file.getString("host");
            db = file.getString("database");

            InfoGeneral.rutades = file.getString("Destinobc");
            InfoGeneral.rtamysql = file.getString("rutmysql");
            InfoGeneral.nombd = db;
            InfoGeneral.pswbd = password;
            InfoGeneral.usbd = user;
            InfoGeneral.host = host;

        } catch (Exception e) {
        }
    }

    public static ConexionBD1 getInstance() {
        if (instance == null) {
            instance = new ConexionBD1();
            Conectar();
        }
        try {
            if (conec.isClosed()) {
                Conectar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return instance;
    }

    public static void Conectar() {
        try {
            String url = "jdbc:mysql://" + host + "/" + db;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conec = DriverManager.getConnection(url, user, password);
            //InfoGeneral.rutades=Destinobc;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Ha ocurrido un error al realizar la conexión al servidor de base de datos.\n"
                    + "Verifique los parámetros de conexión.\n\nDetalle de error:\n\n" + e
                    + "", "Error de conexión", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

//***************FIN METODOS**************
}
