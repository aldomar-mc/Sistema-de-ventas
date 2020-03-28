package Clases;

/**
 * *** @author Miguel Silva Zapata ********
 */
import com.toedter.calendar.JDateChooser;
import java.awt.Desktop;
import java.awt.event.KeyEvent;

import java.io.File;

import java.io.*;
import java.io.InputStreamReader;
import java.util.Calendar;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.view.JasperViewer;

public class Controlador {

    /**
     * *****************************ATRIBUTOS******************************
     */
    public static ConexionBD1 Base = ConexionBD1.getInstance();
    //public ConexAlternativa Base1=new ConexAlternativa();
    public Visualizar Vsz = new Visualizar();
    public String Sql = "";
    public int fila;
    public boolean bandera = false;
    static public boolean bandera1 = false;
    public boolean InventarioInicial = false;
    static public String Artificio = "";
    static public String sede;
    public IMPRIMIR impresor = new IMPRIMIR();
    public Numero_a_Letra numlt = new Numero_a_Letra();
    //public Codificador Codigo = new Codificador();
    public String Data[] = new String[20];
    public int Veces = 0;
    public JFileChooser explorador = new JFileChooser();
    private int BUFFER = 10485760;
    private StringBuffer temp = null;
    private FileWriter fichero = null;
    private PrintWriter pw = null;
    private final DecimalFormat decimalFormat = new DecimalFormat("00.00", DecimalFormatSymbols.getInstance(new Locale("en", "US")));
    public static String ICON_PATH = "/Imagenes/icon.png";

    public String QuitarCaracter(String Cadena) {
        String res = "";
        boolean b = false;
        Cadena = Cadena.replace("'", ";");
        return Cadena;
    }

    public void forma_table_ver(Mimodelo md, JTable tb) {
        FormatoTabla ft = new FormatoTabla(1);
        tb.setDefaultRenderer(Object.class, ft);
        hideTableColumn(tb, 0);
        /*tb.getColumnModel().getColumn(0).setMinWidth(0);
         tb.getColumnModel().getColumn(0).setMaxWidth(0);
         tb.getColumnModel().getColumn(0).setPreferredWidth(0);*/
    }

    public String DevDatoJtable(Mimodelo md, int fl, int cl) {
        return md.getValueAt(fl, cl).toString();
    }

    public void forma_table_ver1(DefaultTableModel md, JTable tb) {
        FormatoTabla ft = new FormatoTabla(1);
        tb.setDefaultRenderer(Object.class, ft);
//        tb.getColumnModel().getColumn(0).setMinWidth(0);
//        tb.getColumnModel().getColumn(0).setMaxWidth(0);
//        tb.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public void LimTablaDefault(DefaultTableModel mdl) {
        while (mdl.getRowCount() > 0) {
            mdl.removeRow(0);
        }
    }

    public double[] CalcularIgv(double monto) {
        double res[] = new double[3];
        res[0] = Redondear((monto / 1.18), 2); //Calculo del Subtotal
        res[1] = Redondear((monto - res[0]), 2); //Calculo del Igv   
        return res;
    }

    public double RedondearNumero(double numero, int digitos) {
        int cifras = (int) Math.pow(10, digitos);
        return Math.rint(numero * cifras) / cifras;
    }

    public double Redondear(double num, int cant) {
        double r = 0;
        try {
            Sql = "select round(" + num + "," + cant + ")";
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(Sql);
            Base.rt.next();
            r = Base.rt.getDouble(1);
        } catch (Exception e) {
        }
        return r;
    }

    public int[] VerificarenJtabla(String dato, JTable tabla, int pos) {
        int cont = 0;
        int t[] = new int[2];
        while (cont < tabla.getRowCount()) {
            if (dato.equals(tabla.getValueAt(cont, pos).toString())) {
                t[0] = 1;
                t[1] = cont;
                break;
            }
            cont++;
        }
        return t;
    }

    public String CalcularImporte(String ctd, String pre) {
        String res = "";
        res = Double.toString(RedondearNumero(Integer.parseInt(ctd) * Double.parseDouble(pre), 2));
        return res;
    }

    public void Enter(KeyEvent e, JTextField tx1, JTextField tx2) {
        if ((e.getKeyChar() == 10) && (tx1.getText().trim().length() > 0)) {
            tx2.grabFocus();
        }
    }

    public String CalcularMontodeJtableDefault(DefaultTableModel model, int columna) {
        double mto = 0.0;
        for (int i = 0; i < model.getRowCount(); i++) {
            mto = mto + Double.parseDouble(model.getValueAt(i, columna).toString().replace(",", "."));
        }
        return decimalFormat(mto);
    }

    public int BuscarDatoenJtablePos(Mimodelo md, String dto, int col) {
        int pos = -1;
        fila = 0;
        if (md.getRowCount() > 0) {
            while (fila < md.getRowCount()) {
                if (md.getValueAt(fila, col).toString().equalsIgnoreCase(dto)) {
                    pos = fila;
                    fila = md.getRowCount();
                }
                fila++;
            }
        }
        return pos;
    }

    public String CalcularMontodeJtable(Mimodelo model, int columna) {
        double mto = 0.0;
        for (int i = 0; i < model.getRowCount(); i++) {
            mto = mto + Double.parseDouble(model.getValueAt(i, columna).toString().replace(",", "."));
        }
        return decimalFormat(mto);
    }

    public boolean Verificarconsulta(String sql, String usbd, String pbd) {
        boolean bd = false;
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(sql);
            if (Base.rt.next()) {
                bd = true;
            }
        } catch (Exception e) {
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

    public boolean BuscarDatoenJtable(Mimodelo md, String dto, int col) {
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

    public String GeneraNumeroDocumento(String tabla, String campo) {
        int can = 0;
        String cod = "";
        boolean bdr = false;
        try {
            Sql = "select count(*) from " + tabla;
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(Sql);
            if (Base.rt.next()) {
                can = Base.rt.getInt(1);
            }
            can++;
            cod = "" + can;
            while (bdr == false) {
                Sql = "select * from " + tabla + " where " + campo + "='" + cod + "'";
                Base.st = Base.conec.createStatement();
                Base.rt = Base.st.executeQuery(Sql);
                bdr = Base.rt.next();
                if (bdr) {
                    can++;
                    cod = GeneraNumero(can);
                } else {
                    bdr = true;
                }
            }
        } catch (Exception e) {
        }
        return cod;
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

    public void AccesoSistema(String usr, String pw,
            JFrame vtac, JFrame vtpos, int inten, String tipous) {
        Sql = "select * from usuario where nomusr='" + usr + "'"
                + " and psw='" + pw + "' and tipousuario_idtipousuario='" + tipous + "';";
        if (Verificarconsulta(Sql)) {
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
            e.printStackTrace();
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
        }
    }

    public String ObtmtoIGV(String cst, int cln) {
        double to = 0.0, st = 0.0;
        String rst = "", SQ = "";
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(cst);
            while (Base.rt.next()) {
                to = to + Base.rt.getDouble(1);
            }
            st = (to / 1.18);
            SQ = "select round(" + Double.toString(to) + ",2) Total,round(" + Double.toString(st) + ",2) ST,"
                    + "round(" + Double.toString((to - st)) + ",2) Igv";
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(SQ);
            if (Base.rt.next()) {
                rst = Base.rt.getString(cln);
            }
        } catch (Exception e) {
        }
        return rst;
    }

    public boolean CrearBackup(String host, String port, String user, String password, String db, String file_backup) {
        boolean ok = false;
        try {
            //sentencia para crear el BackUp
            Process run = Runtime.getRuntime().exec(
                    "C:\\xampp\\mysql\\bin\\mysqldump --host=" + host + " --port=" + port
                    + " --user=" + user + " --password=" + password
                    + " --compact --complete-insert --extended-insert --skip-quote-names"
                    + " --skip-comments --skip-triggers " + db);
            //se guarda en memoria el backup
            InputStream in = run.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            temp = new StringBuffer();
            int count;
            char[] cbuf = new char[BUFFER];
            while ((count = br.read(cbuf, 0, BUFFER)) != -1) {
                temp.append(cbuf, 0, count);
            }
            br.close();
            in.close();
            /* se crea y escribe el archivo SQL */
            fichero = new FileWriter(file_backup);
            pw = new PrintWriter(fichero);
            pw.println(temp.toString());
            ok = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return ok;
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
        }
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
        }
        return b;
    }

    public void VerPrograma(String pgr) throws IOException {
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(pgr);
        } catch (IOException ex) {
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
            e.printStackTrace();
        }
        return bd;
    }

    public void CrearRegistro(String sq) {
        try {
            Base.st = Base.conec.createStatement();
            Base.st.executeUpdate(sq);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CrearRegistro1(String sq) {
        try {
//       Base1.st=Base1.conec.createStatement();Base1.st.executeUpdate(sq);
        } catch (Exception e) {
        }
    }

    public void CrearRegistroCNProcedureStore(String cdg, String facd) {
        try {
            Base.prest = Base.conec.prepareCall("{call CrearFacultad(?,?)}");
            Base.prest.setString(1, cdg);
            Base.prest.setString(2, facd);
            Base.prest.execute();
        } catch (Exception e) {
        }
    }

    public boolean ValidarBlancos(String Cadena) {
        bandera = true;
        int cta = 0;
        for (int psc = 0; psc < Cadena.trim().length(); psc++) {
            if (Cadena.charAt(psc) == ' ') {
                if (Cadena.charAt(psc + 1) == ' ') {
                    bandera = false;
                    break;
                }
            }
        }
        return bandera;
    }

    public void EditarRegistro(String sql) {
        try {
            Base.st = Base.conec.createStatement();
            Base.st.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet DevolverRegistro(String sq) {
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(sq);
        } catch (Exception e) {
            e.printStackTrace();
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

    public void LlenarJTabla(Mimodelo mdl, String sq, int cdt) {
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
            e.printStackTrace();
        }
    }

    public void LlenarJTablaBOOLE(Mimodelo mdl, String sq) {
        LimTabla(mdl);
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(sq);
            while (Base.rt.next()) {
                Data[0] = Base.rt.getString(1);
                Data[1] = Base.rt.getString(2);
                Data[2] = Base.rt.getString(3);
                Data[3] = Base.rt.getString(4);

                Data[4] = Base.rt.getString(5);
                Data[5] = Base.rt.getString(6);
                Data[6] = Base.rt.getString(7);
                Data[7] = Base.rt.getString(4);
                mdl.addRow(new Object[]{Data[0], Data[1], Data[2], Data[3], Data[4], Data[5], Data[6], Boolean.TRUE});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LlenarJTablaConCheck(Mimodelo mdl, String sq, int cdt) {
        LimTabla(mdl);
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(sq);
            while (Base.rt.next()) {
                for (Veces = 1; Veces <= cdt; Veces++) {
                    Data[Veces - 1] = Base.rt.getString(Veces);
                }
                /*model.addRow(new Object[]{datosProducto[0],datosProducto[1],datosProducto[2],
                 datosProducto[3],datosProducto[4],datosProducto[5],datosProducto[6],
                 datosProducto[7],datosProducto[8],Boolean.TRUE});*/
                mdl.addRow(Data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LlenarJTablaConCheckBox(CustomTableModel mdl, String sq, int cdt) {
        while (mdl.getRowCount() > 0) {
            mdl.removeRow(0);
        }
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(sq);
            while (Base.rt.next()) {
                for (Veces = 1; Veces <= cdt; Veces++) {
                    Data[Veces - 1] = Base.rt.getString(Veces);

                }
                mdl.addRow(new Object[]{Data[0], Data[1], Data[2], Boolean.FALSE});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LlenarJTablaSE(Mimodelo mdl, String sq, int cdt) {
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
        }
        return b;
    }

    public boolean Existecam(String cdg, String cmpcod, String tb, String cmp, String vlr) {
        boolean b = false;
        Sql = "select * from " + tb + " where " + cmp + "='" + vlr + "' and " + cmpcod + "<>'" + cdg + "'";
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(Sql);
            if (Base.rt.next()) {
                b = true;
            }
        } catch (Exception e) {
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
        }
        return b;
    }

    public String verificarcrearregistro(String tb, String... d) {
        String id = "";
        return id;
    }

    public void CrearBackupBD(String us, String ps, String bd) {
        Calendar c = Calendar.getInstance();
        String fecha = String.valueOf(c.get(Calendar.DATE));
        fecha = fecha + String.valueOf(c.get(Calendar.MONTH));
        fecha = fecha + String.valueOf(c.get(Calendar.YEAR));
        fecha = fecha + "_" + String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        fecha = fecha + String.valueOf(c.get(Calendar.MINUTE));
        fecha = fecha + String.valueOf(c.get(Calendar.SECOND));

        Runtime rutime = Runtime.getRuntime();
        File backup = new File("D:/Respaldos/backup");
        backup.mkdirs();
        File backupFile = new File("D:/Respaldos/backup/backup" + "_" + fecha + ".sql");
        //File backupFile=new File("D:/Respaldos/backup/Elbackup.sql");
        try {
            InputStreamReader irs;
            BufferedReader br;
            FileWriter filewrite = new FileWriter(backupFile);
            String patch = "C:\\xampp\\mysql\\bin\\";
            Process chil = rutime.exec(patch + "mysqldump" + " --opt " + " --user=" + us + " --password=" + ps + " --databases "
                    + bd + " -R");
            irs = new InputStreamReader(chil.getInputStream());
            br = new BufferedReader(irs);
            String line;
            while ((line = br.readLine()) != null) {
                filewrite.write(line + "\n");
            }
            JOptionPane.showMessageDialog(null, "Copia de Seguridad Exitosa");
        } catch (Exception e) {
            System.out.println("Errores");
        }
    }

    public void CrearBackupBD_Vrs1() {
        Calendar c = Calendar.getInstance();
        String fecha = String.valueOf(c.get(Calendar.DATE));
        fecha = fecha + String.valueOf(c.get(Calendar.MONTH));
        fecha = fecha + String.valueOf(c.get(Calendar.YEAR));
        fecha = fecha + "_" + String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        fecha = fecha + String.valueOf(c.get(Calendar.MINUTE));
        fecha = fecha + String.valueOf(c.get(Calendar.SECOND));
        Runtime rutime = Runtime.getRuntime();
        File backup = new File(InfoGeneral.rutades);
        backup.mkdirs();
        File backupFile = new File(InfoGeneral.rutades + "/backup" + "_" + fecha + ".sql");
        try {
            InputStreamReader irs;
            BufferedReader br;
            FileWriter filewrite = new FileWriter(backupFile);
            String patch = InfoGeneral.rtamysql;
            Process chil = rutime.exec(patch + "mysqldump" + " --opt " + " --user=" + InfoGeneral.usbd + " --password="
                    + InfoGeneral.pswbd + " --databases " + InfoGeneral.nombd + " -R");
            irs = new InputStreamReader(chil.getInputStream());
            br = new BufferedReader(irs);
            String line;
            while ((line = br.readLine()) != null) {
                filewrite.write(line + "\n");
            }
            JOptionPane.showMessageDialog(null, "Se genero un backup de la Base de datos");
            filewrite.flush();
            filewrite.close();
        } catch (Exception e) {
            System.out.println("Errores");
        }
    }

    public String Fecha() {
        String fec = "";
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery("select curdate();");
            if (Base.rt.next()) {
                fec = Base.rt.getString(1);
            }
        } catch (Exception e) {
        }
        return fec;
    }

    public String Hora() {
        String hra = "";
        try {
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery("select curtime()");
            if (Base.rt.next()) {
                hra = Base.rt.getString(1);
            }
        } catch (Exception e) {
        }
        return hra;
    }

    public void LimTabla(Mimodelo mdl) {
        while (mdl.getRowCount() > 0) {
            mdl.removeRow(0);
        }
    }

    public void LimTablaeditable(CustomTableModel mdl) {
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
        } catch (Exception E) {
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

    public void Restaurar() {
        int valor;
        File carpeta = new File("D:/");
        explorador.setCurrentDirectory(carpeta);
        valor = explorador.showOpenDialog(null);
        if (valor == JFileChooser.APPROVE_OPTION) {
            try {
                String ubicacion = String.valueOf(explorador.getSelectedFile());

                Process proceso = Runtime.getRuntime().exec("cmd /c mysql --user=root --password=silva BDActas < " + ubicacion);

                JOptionPane.showMessageDialog(null, "La Base de Datos ha sido actualizada", "Verificar", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error no se actualizó la Base de Datos por el siguiente motivo: " + e.getMessage(), "Verificar", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "La actualización del Backup ha sido cancelada");
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
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery("select nume from comprobantesvta where tipcompr='" + tipo + "' and esta= 'Activo' order by serie asc limit 1");
            while (Base.rt.next()) {
                ser = Base.rt.getString(1);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return ser;
    }

    public String RecuperaNumeroSerie(String tip) {
        String ser = "";
        int seriean = 0;
        try {
            //Base.Conectar();
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery("select count(*) from comprobantesvta c where tipcompr = '" + tip + "' order by c.serie desc limit 1");
            //SELECT count(*), serie FROM comprobantesvta c where c.tipcompr = 'factura'  order by c.serie desc limit 1;
            while (Base.rt.next()) {
                // seriean=Base.rt.getInt(1);
                seriean = Base.rt.getInt(1);

            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        try {
            //Base.Conectar();
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery("select * from comprobantesvta c where tipcompr = '" + tip + "' order by c.serie desc limit 1");
            //SELECT count(*), serie FROM comprobantesvta c where c.tipcompr = 'factura'  order by c.serie desc limit 1;
            while (Base.rt.next()) {
                // seriean=Base.rt.getInt(1);
                ser = Base.rt.getString(4);

            }
        } catch (Exception e) {

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
        }
        return cad;
    }

    public void MarcarTexto(JTextField tf) {
        tf.setSelectionStart(0);
        tf.setSelectionEnd(tf.getText().length());
        tf.grabFocus();
    }

    public String Formato_Amd(java.util.Date v1) {
        Date fecha1 = v1;
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String fecha = df.format(fecha1);
        return fecha;
    }
    /*public String Formato_Amd(java.util.Date v1) {
     java.util.Date fecha1 = v1;
     java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
     String fecha = df.format(fecha1);
     return fecha;
     }*/

    public String Formato_DMA(java.util.Date v1) {
        java.util.Date fecha1 = v1;
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd-MM-yyyy");
        String fecha = df.format(fecha1);

        return fecha;
    }

    public void CrearUnaTablaTemporal(String nmtbl, String sqlcrear) {
        Sql = "DROP TABLE IF EXISTS " + nmtbl + ";";
        CrearRegistro(Sql);
        Sql = sqlcrear;
        CrearRegistro(Sql);
    }

    public void Mayusculas(JTextField tx) {
        tx.setText(tx.getText().toUpperCase());
    }

    public void Minusculas(JTextField tx) {
        tx.setText(tx.getText().toLowerCase());
    }

    public void passFocus(KeyEvent evt, JComponent component) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            component.requestFocus();
        }
    }

    public boolean isEnterKey(KeyEvent evt) {
        return evt.getKeyCode() == KeyEvent.VK_ENTER;
    }
    private static final String REPORTS_PATH = System.getProperty("user.dir") + "/src/Reportes/";

    public void showReportDialog(String title, String reportName, Map parameters) {
        try {
            ConexionBD1.Conectar();
            Base.Conectar();
            if (parameters == null) {
                parameters = new HashMap();
            }
            parameters.put("SUBREPORT_DIR", REPORTS_PATH);
            parameters.put("codv", System.getProperty("user.dir") + "/src/Reportes/");
            parameters.put(JRParameter.REPORT_LOCALE, new Locale("en", "US"));

            JasperPrint prt = JasperFillManager.fillReport(REPORTS_PATH + reportName + ".jasper", parameters, ConexionBD1.conec);
            int pagesCount = prt.getPages().size();
            if (pagesCount > 0) {
                JasperViewer JsperView = new JasperViewer(prt, false);
                JsperView.getComponents();
                JsperView.setTitle(title);
                JsperView.setExtendedState(6);

                JsperView.setVisible(true);
                JsperView.toFront();
            } else {
                JOptionPane.showMessageDialog(null, "No hay resultados para mostrar", "Report", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void imprimirComprobanteVenta(String idVenta, String formato, Map map) {
        String impresora = DevolverRegistroDto("SELECT c.`valor` FROM config c WHERE "
                + "c.`idconfig`='impresoraFacturaBoleta';", 1);
        if (formato.contains("ticket") || formato.contains("ticket_boleta")
                || formato.contains("ticket_factura")) {
            //         impresora = Configuracion.getInstance().getImpresoraTicket();
            impresora = DevolverRegistroDto("SELECT c.`valor` FROM config c WHERE c.`idconfig"
                    + "`='impresoraTicket';", 1);
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
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
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
                        //int copias = Configuracion.getInstance().getCantidadCopiasSinVistaPrevia();
                        int copias = Integer.parseInt(DevolverRegistroDto("SELECT c.`valor` FROM " + "config c WHERE c.`idconfig`='cantidadCopiasSinVistaPrevia';", 1));
                        for (int i = 0; i < copias; i++) {
                            jrprintServiceExporter.exportReport();
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha encontrado la impresora");
                    showReportDialog("Comprobante de compra", formato, map);
                }
            }
        }
//        System.out.println(REPORTS_PATH + formato + ".jasper");
//        String impresora = "";
////        String impresora = Configuracion.getInstance().getImpresoraTicket();
//
//        if (impresora == null) {
//            impresora = "";
//        }
//        map.put("SUBREPORT_DIR", REPORTS_PATH);
//
//        Base.Conectar();
//
//        JasperPrint prt;
//        boolean showPrintDialog = impresora.length() == 0 || impresora.compareTo("Mostrar Ventana") == 0;
//        if (showPrintDialog) {
//            try {
//                prt = JasperFillManager.fillReport(REPORTS_PATH + formato + ".jasper", map, Base.conec);
//
//                JasperPrintManager.printReport(prt, showPrintDialog);
//
//            } catch (JRException ex) {
//                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } else {
//            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
//            PrintService printService = null;
//            if (printServices.length > 0) {
//                for (PrintService printService1 : printServices) {
//                    if (printService1.getName().compareToIgnoreCase(impresora) == 0) {
//                        printService = printService1;
//                        break;
//                    }
//                }
//
//                if (printService != null) {
//
//                    try {
//                        prt = JasperFillManager.fillReport(REPORTS_PATH + formato + ".jasper", map, Base.conec);
//                        JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
//                        jrprintServiceExporter.setParameter(JRExporterParameter.JASPER_PRINT, prt);
//                        jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService);
//                        jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
//
//                        jrprintServiceExporter.exportReport();
//
//                    } catch (Exception ex) {
//                        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
//                        JOptionPane.showMessageDialog(null, "La impresora predeterminada no está disponible en estos momentos", "Imprimir", JOptionPane.ERROR_MESSAGE);
//                        showReportDialog("Comprobante", formato, map);
//                    }
//
//                } else {
//                    JOptionPane.showMessageDialog(null, "No se ha encontrado la impresora predeterminada", "Imprimir", JOptionPane.ERROR_MESSAGE);
//                    showReportDialog("Comprobante", formato, map);
//                }
//            }
//        }
    }

    /*public void imprimirComprobanteVenta(String idVenta, String formato, Map map) {
     //String impresora = Configuracion.getInstance().getImpresoraFacturaBoleta();
     String impresora = DevolverRegistroDto("SELECT c.`valor` FROM config c WHERE " + "c.`idconfig`='impresoraFacturaBoleta';", 1);
     if (formato.contains("ticket") || formato.contains("ticket_boleta")|| formato.contains("ticket_factura")) {
     //         impresora = Configuracion.getInstance().getImpresoraTicket();
     impresora = DevolverRegistroDto("SELECT c.`valor` FROM config c WHERE c.`idconfig"+ "`='impresoraTicket';", 1);
     }
     if (map == null) {
     map = new HashMap();
     }
     map.put("idVenta", "" + idVenta);
     if (impresora == null) {
     impresora = "";
     }
     map.put("SUBREPORT_DIR", REPORTS_PATH);Base.Conectar();JasperPrint prt;
     boolean showPrintDialog = impresora.length() == 0 || impresora.compareTo("Mostrar Ventana") == 0;
     if (showPrintDialog) {
     try {
     prt = JasperFillManager.fillReport(REPORTS_PATH + formato + ".jasper", map, Base.conec);
     JasperPrintManager.printReport(prt, showPrintDialog);
     } 
     catch (JRException ex) {
     Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
     }
     } 
     else {
     PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);PrintService printService = null;
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
     jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE,
     printService);
     jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,
     Boolean.FALSE);
     //int copias = Configuracion.getInstance().getCantidadCopiasSinVistaPrevia();
     int copias = Integer.parseInt(DevolverRegistroDto("SELECT c.`valor` FROM " + "config c WHERE c.`idconfig`='cantidadCopiasSinVistaPrevia';", 1));
     for (int i = 0; i < copias; i++) {
     jrprintServiceExporter.exportReport();
     }
     } catch (Exception ex) {
     Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
     }
     } else {
     JOptionPane.showMessageDialog(null, "No se ha encontrado la impresora");
     showReportDialog("Comprobante de compra", formato, map);
     }
     }
     }
     //        System.out.println(REPORTS_PATH + formato + ".jasper");
     //        String impresora = "";
     ////        String impresora = Configuracion.getInstance().getImpresoraTicket();
     //
     //        if (impresora == null) {
     //            impresora = "";
     //        }
     //        map.put("SUBREPORT_DIR", REPORTS_PATH);
     //
     //        Base.Conectar();
     //
     //        JasperPrint prt;
     //        boolean showPrintDialog = impresora.length() == 0 || impresora.compareTo("Mostrar Ventana") == 0;
     //        if (showPrintDialog) {
     //            try {
     //                prt = JasperFillManager.fillReport(REPORTS_PATH + formato + ".jasper", map, Base.conec);
     //
     //                JasperPrintManager.printReport(prt, showPrintDialog);
     //
     //            } catch (JRException ex) {
     //                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
     //            }
     //        } else {
     //            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
     //            PrintService printService = null;
     //            if (printServices.length > 0) {
     //                for (PrintService printService1 : printServices) {
     //                    if (printService1.getName().compareToIgnoreCase(impresora) == 0) {
     //                        printService = printService1;
     //                        break;
     //                    }
     //                }
     //
     //                if (printService != null) {
     //
     //                    try {
     //                        prt = JasperFillManager.fillReport(REPORTS_PATH + formato + ".jasper", map, Base.conec);
     //                        JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
     //                        jrprintServiceExporter.setParameter(JRExporterParameter.JASPER_PRINT, prt);
     //                        jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService);
     //                        jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
     //
     //                        jrprintServiceExporter.exportReport();
     //
     //                    } catch (Exception ex) {
     //                        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
     //                        JOptionPane.showMessageDialog(null, "La impresora predeterminada no está disponible en estos momentos", "Imprimir", JOptionPane.ERROR_MESSAGE);
     //                        showReportDialog("Comprobante", formato, map);
     //                    }
     //
     //                } else {
     //                    JOptionPane.showMessageDialog(null, "No se ha encontrado la impresora predeterminada", "Imprimir", JOptionPane.ERROR_MESSAGE);
     //                    showReportDialog("Comprobante", formato, map);
     //                }
     //            }
     //        }
     }
     */
    public void Imprimir(JComboBox cbo) {
        if (cbo.getSelectedIndex() == -1) {
            return;
        }
        JFileChooser explorador = new JFileChooser();
        explorador.setDialogTitle("Ventana para Abrir Documentos");
        //Muestro un dialogo sin pasarle parent con el boton de abrir
        int seleccion = explorador.showDialog(null, "Abrir!");
        //analizamos la respuesta
        switch (seleccion) {
            case JFileChooser.APPROVE_OPTION: {
                //Podemos crear un File con lo seleccionado
                File archivo = explorador.getSelectedFile();
                //y guardar una ruta
                String ruta = archivo.getPath();
                ImprimirDocumentos(ruta, cbo);
                //JOptionPane.showMessageDialog(null, "El Directorio es "+ruta);
                break;
            }
            case JFileChooser.CANCEL_OPTION: {
                JOptionPane.showMessageDialog(null, "Se eligio Cancelar el Dialogo");
                break;
            }
            case JFileChooser.ERROR_OPTION:
                JOptionPane.showMessageDialog(null, "Se producjo un error");
                break;
        }
    }

    public void ImprimirDocumentos(String ruta, JComboBox cbo) {
        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
        java.io.File fichero = new java.io.File(ruta);
        if (desktop.isSupported(java.awt.Desktop.Action.PRINT)) {
            try {
                try {
                    //String nombreImpresora = "HP Color LaserJet CP1215"; //dato a modificar segun la impresora
                    String nombreImpresora = cbo.getSelectedItem().toString(); //dato a modificar segun la impresora
                    Process pr = Runtime.getRuntime().exec("Rundll32 printui.dll,PrintUIEntry /y /n \"" + nombreImpresora + "\"");
                } catch (Exception ex) {
                    System.out.println("Ha ocurrido un error al ejecutar el comando. Error: " + ex);
                }
                desktop.print(fichero);
            } catch (Exception e) {
                System.out.print("El sistema no permite imprimir usando la clase Desktop");
                e.printStackTrace();
            }
        } else {
            System.out.print("El sistema no permite imprimir usando la clase Desktop");
        }
    }

    static public void ImprimirWord(String ruta, String Impresora) {
        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
        java.io.File fichero = new java.io.File(ruta);
        if (desktop.isSupported(java.awt.Desktop.Action.PRINT)) {
            try {
                desktop.print(fichero);
            } catch (Exception e) {
                System.out.print("El sistema no permite imprimir usando la clase Desktop");
                e.printStackTrace();
            }
        } else {
            System.out.print("El sistema no permite imprimir usando la clase Desktop");
        }
    }

    //public void printAvailable() {

    public void LlenarImpresoras(JComboBox cbo) {
        // busca los servicios de impresion...
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        // -- ver los atributos de las impresoras...
        for (PrintService printService : services) {
            cbo.addItem(printService.getName());
            /*System.out.println(" ---- IMPRESORA: " + printService.getName());            
            
             JOptionPane.showMessageDialog(null, " Impresora: " + printService.getName());
        
             PrintServiceAttributeSet printServiceAttributeSet = printService.getAttributes();
             System.out.println("--- atributos");
             // todos los atributos de la impresora
             Attribute[] a = printServiceAttributeSet.toArray();
             for (Attribute unAtribute : a) {
             System.out.println("atributo: " + unAtribute.getName());
             }
            
             System.out.println("--- viendo valores especificos de los atributos ");
             // valor especifico de un determinado atributo de la impresora
             System.out.println("PrinterLocation: " + printServiceAttributeSet.get(PrinterLocation.class));
             System.out.println("PrinterInfo: " + printServiceAttributeSet.get(PrinterInfo.class));
             System.out.println("PrinterState: " + printServiceAttributeSet.get(PrinterState.class));
             System.out.println("Destination: " + printServiceAttributeSet.get(Destination.class));
             System.out.println("PrinterMakeAndModel: " + printServiceAttributeSet.get(PrinterMakeAndModel.class));
             System.out.println("PrinterIsAcceptingJobs: " + printServiceAttributeSet.get(PrinterIsAcceptingJobs.class));*/
        }
    }

    public String dateFormat(java.util.Date date) {
        java.util.Date fecha1 = date;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = df.format(fecha1);
        return fecha;
    }

    public String decimalFormat(Object object) {
        return this.decimalFormat.format(object);
    }

    public String[] getArrayData(String sql) {
        String[] data = null;
        try {
            Base.Conectar();
            this.Base.st = Base.conec.createStatement();
            this.Base.rt = this.Base.st.executeQuery(sql);
            int numeroColumnas = this.Base.rt.getMetaData().getColumnCount();
            if (this.Base.rt.next()) {
                data = new String[numeroColumnas];
                for (int i = 0; i < numeroColumnas; i++) {
                    data[i] = this.Base.rt.getString(i + 1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
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

    public void hideTableColumn(JTable table, int... indexs) {
        for (int j : indexs) {
            table.getColumnModel().getColumn(j).setMinWidth(0);
            table.getColumnModel().getColumn(j).setPreferredWidth(0);
            table.getColumnModel().getColumn(j).setWidth(0);
            table.getColumnModel().getColumn(j).setMaxWidth(-1);
        }
    }

    public void setWidthTableColumn(JTable table, int width, int... indexs) {
        for (int j : indexs) {
            table.getColumnModel().getColumn(j).setWidth(width);
            table.getColumnModel().getColumn(j).setPreferredWidth(width);
        }
    }

    public void setCenterColumns(JTable table, int... indexs) {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for (int j : indexs) {
            table.getColumnModel().getColumn(j).setCellRenderer(tcr);
        }
    }

    public void setMaxWidthColumnTable(JTable table, int width, int... indexs) {
        for (int j : indexs) {
            table.getColumnModel().getColumn(j).setMaxWidth(width);
            table.getColumnModel().getColumn(j).setWidth(width);
            table.getColumnModel().getColumn(j).setPreferredWidth(width);
        }
    }

    public boolean ejecutar(String sql) {
        boolean b = false;
        try {
            Base.st = Base.conec.createStatement();
            Base.st.executeUpdate(sql);
            b = true;
        } catch (Exception e) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        return b;
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
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, e);
            res = "Ocurrió un error";
            e.printStackTrace();
        }
        return res;
    }

    public void fillComboBox(String sql, JComboBox comboBox, Map map) {
        fillComboBox(sql, comboBox, map, "");
    }

    public void fillComboBox1(String sql, JComboBox comboBox, Map map, int ps) {
        fillComboBox1(sql, comboBox, map, "", ps);
    }

    public void fillComboBox1(String sql, JComboBox comboBox, Map map, String primerItem, int p) {
        int index = 0;
        try {
            comboBox.removeAllItems();
            map.clear();
            if (primerItem.length() > 0) {
                map.put(index, "null");
                comboBox.addItem(primerItem);
                index++;
            }
            Base.Conectar();
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(sql);
            while (Base.rt.next()) {
                map.put(index, Base.rt.getString(p));
                comboBox.addItem(Base.rt.getString(2));
                index++;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fillComboBox(String sql, JComboBox comboBox, Map map, String primerItem) {
        int index = 0;
        try {
            comboBox.removeAllItems();
            map.clear();
            if (primerItem.length() > 0) {
                map.put(index, "null");
                comboBox.addItem(primerItem);
                index++;
            }
            Base.Conectar();
            Base.st = Base.conec.createStatement();
            Base.rt = Base.st.executeQuery(sql);
            while (Base.rt.next()) {
                map.put(index, Base.rt.getString(1));
                comboBox.addItem(Base.rt.getString(2));
                index++;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, e);
            id = -1;
            e.printStackTrace();
        }
        return id;
    }

    public static void main(String[] args) {
        char x = '1';
        //System.out.println(x == 1);
        // System.out.println(Integer.parseInt("" + x) == 1);
    }
}
/**
 * ************************IMPLEMENTACION DE METODOS************************
 */
