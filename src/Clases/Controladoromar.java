/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Miguel Silva Zapata
 */
import com.toedter.calendar.JDateChooser;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
//import jym.ferreteria.renders.CustomTableModel;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.view.JasperViewer;

public class Controladoromar {

    public static ConexionBD Base = new ConexionBD();
    public String Sql = "";
    public int fila;
    public boolean bandera = false;
    static public String Artificio = "";
    static public String usuario;

    public Codificador Codigo = new Codificador();
    public String Data[] = new String[500];
    public int Veces = 0;
    public JFileChooser explorador = new JFileChooser();
    private int BUFFER = 10485760;
    private StringBuffer temp = null;
    private FileWriter fichero = null;
    private PrintWriter pw = null;
    public static String ICON_PATH = "/jym/ferreteria/imagenes/icoChiqui.png";
    private final DecimalFormat decimalFormat = new DecimalFormat("###,##0.00", DecimalFormatSymbols.getInstance(new Locale("en", "US")));

    public boolean Verificarconsulta(String sql, String usbd, String pbd) {
        boolean bd = false;
        try {

            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(sql);
            if (Base.rt.next()) {
                bd = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bd;
    }

    public String GeneraNumero(int num) {
        String rs = "";
        if ((num > 0) && (num < 10)) {
            rs = "000" + num;
        }
        if ((num >= 10) && (num < 100)) {
            rs = "00" + num;
        }
        return rs;
    }

    public String VolverFecha(String fce) {
        String f = "";
        f = fce.substring(8, 10) + "/" + fce.substring(5, 7) + "/" + fce.substring(0, 4);
        return f;
    }

    public void PonerFechaenDateChooser(JDateChooser fecha, String fec) {
        ((JTextComponent) fecha.getDateEditor().getUiComponent()).setText(fec);
    }

    public boolean BuscarDatoenJtable(CustomTableModel md, String dto, int col) {
        bandera = false;
        fila = 0;
        if (dto.trim().length() == 0) {
        } else {
            if (md.getRowCount() > 0) {
                while (fila < md.getRowCount()) {
                    if (md.getValueAt(fila, col).toString().equalsIgnoreCase(dto)) {
                        bandera = true;
                        fila = md.getRowCount();
                    }
                    fila++;
                }
            }
        }
        return bandera;
    }

    public String getValueQuery(String sql) {
        String res = "Ocurrió un error";
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(sql);

            if (Base.rt.next()) {
                res = Base.rt.getString(1);
            }

        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
            res = "Ocurrió un error";
            e.printStackTrace();
        }
        return res;
    }

    public String ObtenerFechaGarantia(String tp, String vlr) {
        String res = "";
        try {
            Sql = "select DevolverFechadeGarantia('" + tp + "','" + vlr + "')";
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(Sql);
            if (Base.rt.next()) {
                res = Base.rt.getString(1);
            }
        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }
        return res;
    }

    public void Longitudtx(JTextField p, KeyEvent e, int lgt) {
        if (p.getText().length() == lgt) {
            e.consume();
        }
    }

    public void VolverDeCboaCbo(JComboBox cbo) {
        if (cbo.getSelectedIndex() == -1) {
            cbo.grabFocus();
        }
    }

    public String PriLtrasMayuscula(String nombre) {
        String mayuscula = nombre.charAt(0) + "";
        mayuscula = mayuscula.toUpperCase();
        nombre = nombre.replaceFirst(nombre.charAt(0) + "", mayuscula);
        for (int k = 1; k < nombre.length(); k++) {
            if (nombre.charAt(k) == ' ') {
                mayuscula = nombre.charAt(k + 1) + "";
                mayuscula = mayuscula.toUpperCase();
                nombre = nombre.replaceFirst(nombre.charAt(k + 1) + "", mayuscula);
                //nombre.replace(nombre.charAt(k+1),mayuscula.charAt(k+1));
            }
        }
        return nombre;
    }

    public void AccesoSistemaus(String usrA, String pwA,
            JFrame vtac, JFrame vtpos, int inten, String usrBD, String pwBD) {
        Sql = "select * from usuario where nomusr='" + usrA + "'"
                + " and psw='" + pwA + "'";
        if (Verificarconsulta(Sql, usrBD, pwBD)) {
            vtac.dispose();
            vtpos.setVisible(true);
        } else {
            Veces++;
            if (Veces == 1) {
                JOptionPane.showMessageDialog(null, "Llevas un intento");
            } else {
                if (Veces == inten) {
                    JOptionPane.showMessageDialog(null, "Cumplistes tus " + inten + " Intentos");
                    vtac.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Llevas " + Veces + " Intentos");
                }
            }
        }
    }
//
//    public void AccesoSistema(String usr, String pw,
//            JFrame vtac, JFrame vtpos, int inten, String tipous) {
//        Sql = "select * from usuario where nomusr='" + usr + "'"
//                + " and psw='" + pw + "' and idTipousuario='" + tipous + "';";
//        if (Verificarconsulta(Sql)) {
//            String idUsuario = DevolverRegistroDto(Sql, 1);
//            Accesos.getInstance().setIdUsuario(idUsuario);
//            Accesos.getInstance().setUsuario(DevolverRegistroDto("SELECT d.`nom` FROM datosusuario d WHERE d.`Usuario_idusuario`='" + idUsuario + "'", 1));
//            System.err.println(Accesos.getInstance().getUsuario());
//            vtac.dispose();
//            vtpos.setVisible(true);
//        } else {
//            Veces++;
//            if (Veces == 1) {
//                JOptionPane.showMessageDialog(null, "Llevas un intento");
//            } else {
//                if (Veces == inten) {
//                    JOptionPane.showMessageDialog(null, "Cumplistes tus " + inten + " Intentos");
//                    vtac.dispose();
//                } else {
//                    JOptionPane.showMessageDialog(null, "Llevas " + Veces + " Intentos");
//                }
//            }
//        }
//    }

    public boolean Malicioso(String dtvld) {
        boolean b = false;
        int p = 0;
        while (p < dtvld.length()) {
            if (dtvld.codePointAt(p) == 39) {
                b = 4 > 2;
                p = dtvld.length();
            }
            p++;
        }
        return b;
    }

    public void LlenarCombo(JComboBox cbo, String Consulta, int pos) {
        cbo.removeAllItems();
        try {
            Base.rt = DevolverRegistro(Consulta);
            while (Base.rt.next()) {
                cbo.addItem(Base.rt.getString(pos));
            }
            cbo.setSelectedIndex(-1);
        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void VolverdeTxatx(JTextField tx1) {
        if (tx1.getText().length() == 0) {
            tx1.requestFocus();
        }
    }

    public void EliminarBD(String nmbd) {
        try {
            Sql = "drop database " + nmbd;
            Base.st = Base.conec.createStatement();
            Base.st.executeUpdate(Sql);
        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public boolean verifnum(String cd) {
        boolean b = true;
        int p = 0;
        while (p < 2) {
            if ((cd.charAt(p) != '0') || (cd.charAt(p) != '1') || (cd.charAt(p) != '2') || (cd.charAt(p) != '3')
                    || (cd.charAt(p) != '4') || (cd.charAt(p) != '5') || (cd.charAt(p) != '6') || (cd.charAt(p) != '7')
                    || (cd.charAt(p) != '8') || (cd.charAt(p) != '9')) {
                b = false;
                p = 2;
            }
            p++;
        }
        return b;
    }

    public void LLamarPaginaWeb(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void ModificarEstrTabla(int nm, String tabla) {
        Sql = "ALTER TABLE " + tabla + " AUTO_INCREMENT=" + nm + "";
        try {
            Base.st = Base.conec.createStatement();
            Base.st.execute(Sql);
        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public int DevIdentificador(String tbl, String cmp) {
        int r = 0, can = 0;
        boolean b = false;
        can = Integer.parseInt(DevolverRegistroDto("select count(*) from " + tbl, 1));
        System.out.println(can);
        if (can == 0) {
            r++;
        } else {
            while (b == false) {
                for (int i = 1; i <= can; i++) {
                    System.out.println(Verificandoconsulta("select * from " + tbl + " where " + cmp + "=" + i));
                    if (!Verificandoconsulta("select * from " + tbl + " where " + cmp + "=" + i)) {
                        r = i;
                        b = true;

                        break;
                    }
                }
                if (!b) {
                    b = true;
                    r = ++can;

                }
            }
        }
        return r;
    }

    public String CompletarFecha(String fe) {
        String a = fe.substring(0, 4), d = "", m = "", rts = fe;
        if (fe.length() < 10) {
            if (verifnum(fe.substring(6, 7))) {
                if (!verifnum(fe.substring(6, 7))) {
                    m = "0" + fe.substring(9, 9);
                }
            } else {
                m = "0" + fe.substring(6, 6);
            }
        }
        return rts;
    }

    public boolean Verificandoconsulta(String sql) {
        boolean b = false;
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(sql);
            if (Base.rt.next()) {
                b = true;
            }
        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }
        return b;
    }

    public void VerPrograma(String pgr) throws IOException {
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(pgr);
        } catch (IOException ex) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean Verificarconsulta(String sql) {
        boolean bd = false;
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(sql);
            if (Base.rt.next()) {
                bd = true;
            }
        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }
        return bd;
    }

    public void CrearRegistro(String sq) {
        try {
            Base.st = Base.conec.createStatement();
            Base.st.executeUpdate(sq);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void CrearRegistroCNProcedureStore(String cdg, String facd) {
        try {
            Base.prest = Base.conec.prepareCall("{call CrearFacultad(?,?)}");
            Base.prest.setString(1, cdg);
            Base.prest.setString(2, facd);
            Base.prest.execute();
        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void EditarRegistro(String sql) {
        try {
            Base.st = Base.conec.createStatement();
            Base.st.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void EliminarRegistroDependiante(String sql, String sql1, String msj) {
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(sql);
            if (Base.rt.next() == false) {
                Base.st = Base.conec.createStatement();
                Base.st.executeUpdate(sql1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public String EliminarRegistro(String sql) {
        String cd = "Registro Eliminado";
        try {
            Base.st = Base.conec.createStatement();
            Base.st.executeUpdate(sql);
            Base.rt.close();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
            cd = "Error";
        }
        return cd;
    }

    public String DevolverRegistroDto(String sq, int pos) {
        String rs = "";
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(sq);
            if (Base.rt.next()) {
                rs = Base.rt.getString(pos);
            }
        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }
        return rs;
    }

    public ResultSet DevolverRegistro(String sq) {
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(sq);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }
        return Base.rt;
    }

    public String Encriptar(String cdn1, int cnd) {
        String encr = "";
        int cr;
        cdn1 = Invertir(cdn1);
        for (int j = 0; j < cdn1.length(); j++) {
            cr = cdn1.codePointAt(j);
            if (Par(cdn1.length())) {
                cr = cr + cnd;
            } else {
                cr = cr - cnd;
            }
            encr = encr + (char) cr;
        }
        return encr;
    }

    public void MostrarEnCombo(String vl, JComboBox cbo) {
        int p = 0, ct = 0;
        while (ct < cbo.getItemCount()) {
            if (cbo.getModel().getElementAt(ct).toString().toUpperCase().compareTo(vl.toUpperCase()) == 0) {
                p = ct;
                ct = cbo.getItemCount();
            }
            ct++;
        }
        cbo.setSelectedIndex(p);
    }

    public static String Invertir(String cdn) {
        String rst = "";
        for (int t = cdn.length() - 1; t >= 0; t--) {
            rst = rst + cdn.charAt(t);
        }
        return rst;
    }

    public static boolean Par(int dto) {
        if (dto == 0) {
            return false;
        } else {
            if (dto % 2 == 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    public String Decencriptar(String cdn1, int cnd) {
        String encr = "";
        int cr;
        cdn1 = Invertir(cdn1);
        for (int j = 0; j < cdn1.length(); j++) {
            cr = cdn1.codePointAt(j);
            if (Par(cdn1.length())) {
                cr = cr - cnd;
            } else {
                cr = cr + cnd;
            }
            encr = encr + (char) cr;
        }
        return encr;
    }

    public void LlenarJTabla(CustomTableModel mdl, String sq, int cdt) {
        LimTabla(mdl);
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(sq);
            while (Base.rt.next()) {
                for (Veces = 1; Veces <= cdt; Veces++) {
                    Data[Veces - 1] = Base.rt.getString(Veces);
                }
                mdl.addRow(Data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }

    public void LlenarJTablaSE(CustomTableModel mdl, String sq, int cdt) {
        LimTabla(mdl);
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(sq);
            while (Base.rt.next()) {
                for (Veces = 1; Veces <= cdt; Veces++) {
                    //if(Veces==2){
                    //Data[Veces-1]=Decencriptar(Base.rt.getString(Veces),3);
                    //}
                    //else{
                    Data[Veces - 1] = Base.rt.getString(Veces);
                    //}
                }
                mdl.addRow(Data);
            }
        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public String CoverFecha(String fce) {
        String f = "";
        f = fce.substring(6, 10) + "/" + fce.substring(3, 5) + "/" + fce.substring(0, 2);
        return f;
    }

    public boolean Comprobarpermiso(String usu, String mnu) {
        String tipo = "";
        String mn = "";
        boolean b = false;
        mn = ObtenerDato("menu", "descr", mnu, 1);
        tipo = ObtenerDato("usuario", "nomusr", usu, 2);
        try {
            Sql = "select * from permiso where Menu_codmnu='" + mn
                    + "' and TipoUsuario_codtpusr='" + tipo + "'";
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(Sql);
            if (Base.rt.next()) {
                b = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }
        return b;
    }

    public boolean Existecam(String cdg, String cmpcod, String tb, String cmp, String vlr) {
        boolean b = false;
        Sql = "select * from " + tb + " where " + cmp + "='" + vlr + "' and " + cmpcod + "<>'" + cdg + "'";
        // JOptionPane.showMessageDialog(null,Sql);
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(Sql);
            if (Base.rt.next()) {
                b = true;
            }
        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }
        return b;
    }

    public boolean Existe(String tb, String cmp, String vlr) {
        boolean b = false;
        Sql = "select * from " + tb + " where " + cmp + "='" + vlr + "'";
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(Sql);
            if (Base.rt.next()) {
                b = true;
            }
        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }
        return b;
    }

    public String Fecha() {
        String fec = "";
        Base.Conectar();
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery("select curdate();");
            if (Base.rt.next()) {
                fec = Base.rt.getString(1);
            }
        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }
        return fec;
    }

    public String Hora() {
        String hra = "";
        Base.Conectar();
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery("select curtime()");
            if (Base.rt.next()) {
                hra = Base.rt.getString(1);
            }
        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }
        return hra;
    }

    public void LimTabla(CustomTableModel mdl) {
        while (mdl.getRowCount() > 0) {
            mdl.removeRow(0);
        }
    }

    public String ObtenerDato(String Tabla, String Cmp, String vl, int ps) {
        String rt = "", Sql = ""; //Base.Conectar();
        Sql = "Select * from " + Tabla + " where " + Cmp + "='" + vl + "'";
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(Sql);
            if (Base.rt.next()) {
                rt = Base.rt.getString(ps);
            }
        } catch (Exception ex) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rt;
    }

    public void SoloLetras(KeyEvent e) {
        if ((e.getKeyChar() >= 48) && (e.getKeyChar() <= 57)) {
            e.consume();
        }
    }

    public void Solonumeros(KeyEvent e) {
        if ((e.getKeyChar() < 48) || (e.getKeyChar() > 57)) {
            e.consume();
        }
    }

    public void AnularTecl(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_BACK_SPACE) || (e.getKeyChar() == KeyEvent.VK_DELETE)) {
            e.consume();
        }
    }

    public void decimal(KeyEvent e, String text) {
        int count = 0;
        if (text.length() == 0 && e.getKeyChar() == '.') {
            e.consume();
        } else {
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == '.') {
                    count++;
                    break;
                }
            }
            if (((e.getKeyChar() < 48) || (e.getKeyChar() > 57))
                    && (e.getKeyChar() != 8 && e.getKeyCode() != 37 && e.getKeyCode() != 39
                    && e.getKeyCode() != 127 && e.getKeyChar() != '.')) {
                e.consume();
            } else {
                if ((count > 0 && e.getKeyChar() == '.')) {
                    e.consume();
                }
            }
        }
    }

    public String RecuperaSerie(String tipo) {
        String ser = "";
        int seriean = 0;
        try {
            Base.Conectar();
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery("select nume from comprobantesvta where tipcompr='" + tipo + "' and esta= 'Activo' order by serie asc limit 1");
            while (Base.rt.next()) {
//               seriean=Base.rt.getInt(1);
                ser = Base.rt.getString(1);

            }
        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        return ser;
    }

    public String RecuperaNumeroSerie(String tip) {
        String ser = "";
        int seriean = 0;
        try {
            Base.Conectar();
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery("select count(*) from comprobantesvta c where tipcompr = '" + tip + "' order by c.serie desc limit 1");
            //SELECT count(*), serie FROM comprobantesvta c where c.tipcompr = 'factura'  order by c.serie desc limit 1;
            while (Base.rt.next()) {
                // seriean=Base.rt.getInt(1);
                seriean = Base.rt.getInt(1);

            }
        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

        try {
            Base.Conectar();
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery("select * from comprobantesvta c where tipcompr = '" + tip + "' order by c.serie desc limit 1");
            //SELECT count(*), serie FROM comprobantesvta c where c.tipcompr = 'factura'  order by c.serie desc limit 1;
            while (Base.rt.next()) {
                // seriean=Base.rt.getInt(1);
                ser = Base.rt.getString(4);

            }
        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

        if (seriean == 0) {
            ser = "1";
        } else {
            seriean = Integer.parseInt(ser);
            seriean++;
            ser = "" + seriean;
        }

        return ser;
    }

    public String CrearRegistroDev(String sq) {
        String cad = "";
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(sq);
            if (Base.rt.next()) {
                cad = Base.rt.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
            cad = e.toString();
        }
        return cad;
    }

    public String EditarRegistroDev(String sql) {

        String cad = "";
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(sql);

            if (Base.rt.next()) {
                cad = Base.rt.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
            cad = e.toString();
        }
        return cad;
    }

    public void MarcarTexto(JTextField tf) {
        tf.setSelectionStart(0);
        tf.setSelectionEnd(tf.getText().length());
        tf.grabFocus();
    }

    public String Formato_Amd(java.util.Date v1) {
        java.util.Date fecha1 = v1;
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String fecha = df.format(fecha1);

        return fecha;
    }

    public String Formato_Amd1(java.util.Date v1, String separator) {
        java.util.Date fecha1 = v1;
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd" + separator + "MM" + separator + "yyyy");
        String fecha = df.format(fecha1);

        return fecha;
    }

    public double toDouble(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public double toDouble(String value) {
        return toDouble(value, 0);
    }

    public void CrearUnaTablaTemporal(String nmtbl, String sqlcrear) {
        Sql = "DROP TABLE IF EXISTS " + nmtbl + ";";
        CrearRegistro(Sql);
        Sql = sqlcrear;
        CrearRegistro(Sql);
    }

    public void Mayusculas(JTextField tx) {
        int pos = tx.getCaretPosition();
        tx.setText(tx.getText().toUpperCase());
        tx.setCaretPosition(pos);
    }

    public void Minusculas(JTextField tx) {
        int pos = tx.getCaretPosition();
        tx.setText(tx.getText().toLowerCase());
        tx.setCaretPosition(pos);
    }

    public void hideTableColumn(JTable table, int... indexs) {
        for (int j : indexs) {

            table.getColumnModel().getColumn(j).setMinWidth(0);
            table.getColumnModel().getColumn(j).setPreferredWidth(0);
            table.getColumnModel().getColumn(j).setWidth(0);
            table.getColumnModel().getColumn(j).setMaxWidth(-1);
        }
    }

    public void tableWidthColumn(JTable table, int width, int... indexs) {
        for (int j : indexs) {
//            table.getColumnModel().getColumn(j).setMinWidth(width);
//            //table.getColumnModel().getColumn(j).setPreferredWidth(width);
            table.getColumnModel().getColumn(j).setWidth(width);
            table.getColumnModel().getColumn(j).setPreferredWidth(width);
        }
    }

    public void tableMaxWidthColumn(JTable table, int width, int... indexs) {
        for (int j : indexs) {
            table.getColumnModel().getColumn(j).setMaxWidth(width);

            table.getColumnModel().getColumn(j).setWidth(width);
            table.getColumnModel().getColumn(j).setPreferredWidth(width);
        }
    }

    public double redondearDecimal(double numero, int decimales) {
        return Math.round(numero * Math.pow(10, decimales)) / Math.pow(10, decimales);
    }

    public String format0(int cantidadDigitos, int numero) {
        Formatter fmt = new Formatter();
        fmt.format("%0" + cantidadDigitos + "d", numero);
        return fmt.toString();
    }

    public String[] cargarRegistro(String sql, int numeroColumnas) {
        String data[] = null;

        try {

            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(sql);
            if (Base.rt.next()) {
                data = new String[numeroColumnas];
                for (int i = 0; i < numeroColumnas; i++) {
                    data[i] = Base.rt.getString(i + 1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;

    }

    public void fillComboBox(String sql, JComboBox comboBox, Map map) {
        fillComboBox(sql, comboBox, map, new String[0]);
    }

    public void fillComboBox(String sql, JComboBox comboBox, Map map, String... items) {
        int index = 0;
        try {
            comboBox.removeAllItems();
            map.clear();
            for (String st : items) {
                map.put(index, "null");
                comboBox.addItem(st);
                index++;
            }

            Base.Conectar();
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(sql);
            while (Base.rt.next()) {
                map.put(index, Base.rt.getString(1));
//                System.out.println(index +" ==> "+Base.rt.getString(1));
                comboBox.addItem(Base.rt.getString(2));
                index++;

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object[][] resultSetToArray(ResultSet rs) {
        Object obj[][] = null;

        try {

            rs.last();

            ResultSetMetaData rsmd = rs.getMetaData();

            int numCols = rsmd.getColumnCount();

            int numFils = rs.getRow();

            obj = new Object[numFils][numCols];
            int j = 0;

            rs.beforeFirst();

            while (rs.next()) {
                for (int i = 0; i < numCols; i++) {

                    obj[j][i] = rs.getObject(i + 1);
                }
                j++;

            }

        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }

        return obj;
    }

    public List resultSetToListArray(ResultSet rs) {
        List list = new ArrayList();
        Object obj[] = null;
        try {
            rs.last();

            ResultSetMetaData rsmd = rs.getMetaData();

            int numCols = rsmd.getColumnCount();

            int numFils = rs.getRow();

            obj = new Object[numCols];

            rs.beforeFirst();

            while (rs.next()) {
                obj = new Object[numCols];
                for (int i = 0; i < numCols; i++) {
                    obj[i] = rs.getObject(i + 1);
                }

                list.add(obj);

            }

        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public List resultSetToListMap(ResultSet rs) {
        List list = new ArrayList();
        Map map;
        try {
            rs.last();

            ResultSetMetaData rsmd = rs.getMetaData();

            int numCols = rsmd.getColumnCount();

            int numFils = rs.getRow();

            rs.beforeFirst();

            while (rs.next()) {
                map = new HashMap();
                for (int i = 0; i < numCols; i++) {
                    map.put(rsmd.getColumnName(i + 1), rs.getString(i + 1));
                }

                list.add(map);

            }

        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public List getListData(String sql) {
        List list = new ArrayList();

        Base.rt = DevolverRegistro(sql);
        try {
            while (Base.rt.next()) {
                list.add(Base.rt.getString(1));

            }
        } catch (SQLException ex) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    private static final String REPORTS_PATH = System.getProperty("user.dir") + "/src/Reportes/";

    public void showReportDialog(String title, String reportName, Map parameters) {
        try {
            Base.Conectar();
            if (parameters == null) {
                parameters = new HashMap();
            }
            parameters.put("SUBREPORT_DIR", REPORTS_PATH);
            parameters.put(JRParameter.REPORT_LOCALE, new Locale("en", "US"));
            parameters.put("logo", getClass().getResource("/jym/ferreteria/imagenes/logoReporte.png").toString());
            //System.out.print(getClass().getResource("/jym/ferreteria/imagenes/logoReporte.png").toString());
            JasperPrint prt = JasperFillManager.fillReport(REPORTS_PATH + reportName + ".jasper", parameters, Base.conec);
            int pagesCount = prt.getPages().size();
            if (pagesCount > 0) {
                JasperViewer JsperView = new JasperViewer(prt, false);
                JsperView.getComponents();
                JsperView.setTitle(title);
                JsperView.setExtendedState(6);
                JsperView.toFront();
                JsperView.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "El documento no tiene páginas");
            }
        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void showReportDialog(String title, String reportName, Map parameters, JRDataSource dataSource) {

        try {

            if (parameters == null) {
                parameters = new HashMap();
            }
            parameters.put("SUBREPORT_DIR", REPORTS_PATH);
            parameters.put(JRParameter.REPORT_LOCALE, new Locale("en", "US"));
            parameters.put("logo", getClass().getResource("/jym/ferreteria/imagenes/logo.png").toString());
            JasperPrint prt = JasperFillManager.fillReport(REPORTS_PATH + reportName + ".jasper", parameters, dataSource);
            int pagesCount = prt.getPages().size();
            if (pagesCount > 0) {
                JasperViewer JsperView = new JasperViewer(prt, false);
                JsperView.setTitle(title);
                JsperView.setExtendedState(6);
                JsperView.toFront();
                JsperView.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "El documento no tiene páginas");
            }
        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public boolean ejecutar(String sql) {
        boolean b = false;
        try {
            Base.st = Base.conec.createStatement();
            Base.st.executeUpdate(sql);
            b = true;
        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        return b;
    }

    public String ejecutarMsg(String sql) {
        String res = "Ocurrió un error";
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(sql);

            if (Base.rt.next()) {
                res = Base.rt.getString(1);
            }

        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
            res = "Ocurrió un error";
            e.printStackTrace();
        }
        return res;
    }

    public int executeAndGetId(String sql) {
        int id = -1;

        try {
            Base.st = Base.conec.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            Base.st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            Base.rt = Base.st.getGeneratedKeys();
            if (Base.rt.next()) {
                id = Base.rt.getInt(1);
            }

        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
            id = -1;
            e.printStackTrace();
        }
        return id;
    }

    public String[] generarComprobante(String tipo) {
        String resultado[] = null;
        String numero = null;
        String id = null;
        String[] split = tipo.split("-");
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(String.format("SELECT `idComprobante`,CONCAT(`serie`,' - ',`numero`) AS numero,`estado` FROM `comprobante` WHERE `idTipoComprobante`=%s AND `serie`='%s' ORDER BY CONCAT(`serie`,' - ',`numero`) DESC LIMIT 1;", split[0], split[1]));
            if (Base.rt.next()) {
                id = Base.rt.getString(1);
                numero = Base.rt.getString(2);

                if (numero != null) {
                    if (!Base.rt.getString(3).toLowerCase().equals("por emitir")) {
                        String val[] = numero.split(" - ");
                        String sql = String.format("INSERT INTO comprobante VALUES(NULL,'%s',LPAD(%d, 7, '0'),7,%s,'Por Emitir');", val[0], Integer.parseInt(val[1]) + 1, split[0]);
                        ejecutar(sql);
                        Base.st = Base.conec.createStatement();
                        Base.rt = Base.st.executeQuery(String.format("SELECT `idComprobante`,CONCAT(`serie`,' - ',`numero`) AS numero,`estado` FROM `comprobante` WHERE `idTipoComprobante`=%s AND `serie`='%s' ORDER BY CONCAT(`serie`,' - ',`numero`) DESC LIMIT 1;", split[0], split[1]));
                        if (Base.rt.next()) {
                            id = Base.rt.getString(1);
                            numero = Base.rt.getString(2);
                        }
                    }
                }
            }

        } catch (Exception e) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, e);
            numero = null;
            id = null;
            e.printStackTrace();
        }
        if (numero != null) {
            resultado = new String[2];
            resultado[0] = id;
            resultado[1] = numero;
        }
        return resultado;
    }

    public void imprimirComprobanteVenta(String idVenta, String formato, Map map) {

//        String impresora = Configuracion.getInstance().getImpresoraFacturaBoleta();
        String impresora = DevolverRegistroDto("SELECT c.`valor` FROM config c WHERE c.`idconfig`='impresoraFacturaBoleta';", 1);

        if (formato.contains("ticket")) {
//            impresora = Configuracion.getInstance().getImpresoraTicket();
            impresora = DevolverRegistroDto("SELECT c.`valor` FROM config c WHERE c.`idconfig`='impresoraTicket';", 1);
        }
        if (map == null) {
            map = new HashMap();
        }
        map.put("idVenta", "" + idVenta);

        if (impresora == null) {
            impresora = "";
        }
        map.put("SUBREPORT_DIR", REPORTS_PATH);

        Base.Conectar();

        JasperPrint prt;
        boolean showPrintDialog = impresora.length() == 0 || impresora.compareTo("Mostrar Ventana") == 0;
        if (showPrintDialog) {
            try {
                prt = JasperFillManager.fillReport(REPORTS_PATH + formato + ".jasper", map, Base.conec);

                JasperPrintManager.printReport(prt, showPrintDialog);

            } catch (JRException ex) {
                Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
            PrintService printService = null;
            if (printServices.length > 0) {
                for (PrintService printService1 : printServices) {
                    if (printService1.getName().compareToIgnoreCase(impresora) == 0) {
                        printService = printService1;
                        break;
                    }
                }

                if (printService != null) {

                    try {
                        prt = JasperFillManager.fillReport(REPORTS_PATH + formato + ".jasper", map, Base.conec);
                        JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
                        jrprintServiceExporter.setParameter(JRExporterParameter.JASPER_PRINT, prt);
                        jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService);
                        jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                        int copias = Configuracion.getInstance().getCantidadCopiasSinVistaPrevia();
                        for (int i = 0; i < copias; i++) {
                            jrprintServiceExporter.exportReport();
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Se ha Producido un Error en la impresora");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "No se ha encontrado la impresora");
                    showReportDialog("Comprobante de compra", formato, map);
                }
            }
        }
    }

    public void imprimirComprobanteVentaFBO(String idVenta, String formato, Map map) {

        String impresora = Configuracion.getInstance().getImpresoraFacturaBoleta();
        if (formato.contains("orden de pedido")) {
            impresora = Configuracion.getInstance().getImpresoraTicket();
        }
        if (map == null) {
            map = new HashMap();
        }
        map.put("idVenta", "" + idVenta);

        if (impresora == null) {
            impresora = "";
        }
        map.put("SUBREPORT_DIR", REPORTS_PATH);

        Base.Conectar();

        JasperPrint prt;
        boolean showPrintDialog = impresora.length() == 0 || impresora.compareTo("Mostrar Ventana") == 0;
        if (showPrintDialog) {
            try {
                prt = JasperFillManager.fillReport(REPORTS_PATH + formato + ".jasper", map, Base.conec);

                JasperPrintManager.printReport(prt, showPrintDialog);

            } catch (JRException ex) {
                Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
            PrintService printService = null;
            if (printServices.length > 0) {
                for (PrintService printService1 : printServices) {
                    if (printService1.getName().compareToIgnoreCase(impresora) == 0) {
                        printService = printService1;
                        break;
                    }
                }

                if (printService != null) {

                    try {
                        prt = JasperFillManager.fillReport(REPORTS_PATH + formato + ".jasper", map, Base.conec);
                        JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
                        jrprintServiceExporter.setParameter(JRExporterParameter.JASPER_PRINT, prt);
                        jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService);
                        jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                        int copias = Configuracion.getInstance().getCantidadCopiasSinVistaPrevia();
                        for (int i = 0; i < copias; i++) {
                            jrprintServiceExporter.exportReport();
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "No se ha encontrado la impresora");
                    showReportDialog("Comprobante de compra", formato, map);
                }
            }
        }
    }

    public Map obtenerPermisos(String codUsuario) {

        Map<Integer, Boolean> map = new HashMap();

        try {
            Base.Conectar();
            Base.st = Base.conec.createStatement();

            ResultSet rs = Base.st.executeQuery(String.format("SELECT p.`idPermisos`,IF(ISNULL(pu.`idPermisos`),FALSE,TRUE) AS valor FROM permisos p LEFT JOIN permisosusuario pu ON p.`idPermisos`=pu.`idPermisos` "
                    + "AND pu.`idUsuario`=%s;", codUsuario));
            while (rs.next()) {
                map.put(rs.getInt(1), rs.getBoolean(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    public String nextId(String tableName) {
        String id = "1";
        try {
            Base.Conectar();
            Base.st = Base.conec.createStatement();

            Base.rt = Base.st.executeQuery(String.format("SELECT AUTO_INCREMENT FROM information_schema.TABLES where TABLE_SCHEMA='%s' and TABLE_NAME='%s';", ConexionBD.db, tableName));
            while (Base.rt.next()) {
                id = Base.rt.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controladoromar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public boolean checkPermiso(boolean b) {
        if (!b) {
            JOptionPane.showMessageDialog(null, "Usted no tiene permiso para realizar esta acción", "Acceso denegado", JOptionPane.WARNING_MESSAGE);
        }
        return b;
    }

    public void passFocus(KeyEvent evt, JComponent component) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            component.requestFocus();
        }

    }

    public void passFocusTable(KeyEvent evt, JTable table) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (table.getRowCount() > 0) {
                table.getSelectionModel().setSelectionInterval(0, 0);
                table.requestFocus();
            }
        }
    }

    public boolean isEnterKey(KeyEvent evt) {
        return evt.getKeyCode() == KeyEvent.VK_ENTER;
    }

    public String decimalFormat(Object object) {
        return decimalFormat.format(object);
    }

    public boolean isDouble(String valor) {
        try {
            Double.parseDouble(valor.replace(",", ""));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
