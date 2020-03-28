package Ventanas;

/**
 * ********************* Ing. Miguel Angel Silva Zapata.   ******************************
 */

//********************************PAQUETE***************************************
import javax.swing.*;
import Clases.*;
import java.awt.event.*;
import java.io.File;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import org.xml.sax.Attributes;
//********************************PAQUETE***************************************

public class Ingresodeproductos extends javax.swing.JInternalFrame {

    /**
     * ***********************Atributos**************************************
     */
    Mimodelo modelo = new Mimodelo();
    Mimodelo modelo1 = new Mimodelo();
    Controlador control = new Controlador();
    String idProv, idCatProd, codbr, Garantia, fe,
            fec, tpodoc, costo, coddoc, inve;
    InfoGeneral info = new InfoGeneral();
    public static int per,
            ctp, controlado = 0;
    DecimalFormat forma = new DecimalFormat("00.00");
    String dat[] = new String[8];
    int contador = 0, cant, cuantosclick = 1;
    double total = 0.00;
    int numerodeproductoingresados = 0;
    private JFileChooser chooser = new JFileChooser();
    String Efecha, Eserie, Enume, Etipo, EcodProv, Eprovee, Ecompra, EdocCompra, Serie = "",
            Produc = "", codcatlg = "", fechaactual = "";
    /**
     * ***********************Atributos**************************************
     */

    /**
     * ***********************Los Metodos**************************************
     */
    private CustomTableModel model = new CustomTableModel() {
        private DecimalFormat format = new DecimalFormat("0.00");

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 4 || column == 5 || column == 7;
        }

        @Override
        public void setValueAt(Object aValue, int row, int column) {
            if (column == 4) {
                try {
                    double costouni = Double.parseDouble(aValue.toString());
                    int cantidadun = Integer.parseInt(super.getValueAt(row, 5).toString());
                    //super.setValueAt(format.format(costouni).replace(",", "."), row, column);
                    super.setValueAt(Double.toString(costouni).replace(",", "."), row, column);
                    //super.setValueAt(format.format(costouni*cantidadun).replace(",", "."), row, 6);
                    super.setValueAt(Double.toString(costouni * cantidadun).replace(",", "."), row, 6);
                } catch (Exception e) {
                    super.setValueAt(format.format(aValue).replace(",", "."), row, column);
                }
            } else {
                if (column == 5) {
                    double costo = Double.parseDouble(super.getValueAt(row, 4).toString());
                    int cantiAnterior = Integer.parseInt(super.getValueAt(row, 5).toString());
                    try {
                        int cantidnueva = Integer.parseInt(aValue.toString());
                        double precio = Double.parseDouble(super.getValueAt(row, 4).toString());
                        super.setValueAt((int) (cantidnueva), row, column);
                        //super.setValueAt(format.format(cantidnueva*precio).replace(",", "."), row, 6);                    
                        super.setValueAt(Double.toString(cantidnueva * precio).replace(",", "."), row, 6);
                    } catch (Exception e) {
                        super.setValueAt((int) (cantiAnterior), row, column);
                    }
                } else {
                }
            }
            sacartotalcompra();
        }
    };

    public Ingresodeproductos() {
        initComponents();
        setTitle("Ingreso de productos adquiridos");
        control.InventarioInicial = true;
        jLabel13.setText(null);
        jLabel13.setVisible(false);
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tProveedores.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"Código", "Ruc", "Proveedor"});
        tProveedores.getColumnModel().getColumn(0).setPreferredWidth(20);
        tProveedores.getColumnModel().getColumn(1).setPreferredWidth(50);
        tProveedores.getColumnModel().getColumn(2).setPreferredWidth(350);
        tProductos.setModel(modelo1);
        modelo1.setColumnIdentifiers(new String[]{"Código", "Codigo Barra", "Producto", "Precio Venta", "Costo"});
        tProductos.getColumnModel().getColumn(0).setPreferredWidth(0);
        tProductos.getColumnModel().getColumn(1).setPreferredWidth(100);
        tProductos.getColumnModel().getColumn(2).setPreferredWidth(420);
        tProductosingresar.setModel(model);
        model.setColumnIdentifiers(new String[]{"id", "N°", "Código", "Producto", "Costo", "Cantidad", "Total", "Caducidad"});
        lbTotal.setVisible(false);
        txCosto.setEditable(false);
        actcosto.setSelected(true);
        control.hideTableColumn(tProductosingresar, 0);
        control.setWidthTableColumn(tProductosingresar, 20, 1);
        control.setWidthTableColumn(tProductosingresar, 90, 2);
        control.setWidthTableColumn(tProductosingresar, 70, 7);
        control.setWidthTableColumn(tProductosingresar, 60, 4, 5, 6);
        control.setWidthTableColumn(tProductosingresar, 300, 3);
        lbProveedor.setText(" ");
        jLabel1.setText(null);
        rb.setVisible(false);
        bAgregar.setVisible(false);
        rb1.setVisible(false);
        rb3.setVisible(false);
        MostrarProveedores();
        MostrarCatalogo();
        fCompra.setDate(new Date());
        cboSedes.setVisible(false);
        jLabel10.setText("Sede :" + Controlador.sede);
        jCheckBox1.setVisible(2 > 4);
        jLabel6.setVisible(false);
        control.forma_table_ver(modelo1, tProductos);
        control.forma_table_ver(modelo, tProveedores);
        FormatoTabla ft = new FormatoTabla(5);
        tProductosingresar.setDefaultRenderer(Object.class, ft);
        jProgressBar1.setVisible(false);
        txDocumento1.setVisible(false);
        lbTotalCompra.setText("0.00");
        jPanel8.setVisible(false);
        fechaven.setDate(new Date());
        fechaactual = control.Formato_Amd(fechaven.getDate());
        cbTipoCompra.setSelectedItem("Contado");
        lbFechaCancelacion.setVisible(false);
        fDePago.setVisible(false);
        fCompra.setDate(new Date());
        fDePago.setDate(new Date());

        cbTipoCompra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbTipoCompra.getSelectedItem().equals("Credito")) {
                    lbFechaCancelacion.setVisible(true);
                    fDePago.setVisible(true);
                } else {
                    lbFechaCancelacion.setVisible(false);
                    fDePago.setVisible(false);
                }
            }
        });

        cbTipoComprobante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txDocumento.grabFocus();
            }
        });
    }

    public void TomarFecha() {
        String an = "", ms = "", da = "";
        an = String.valueOf(fCompra.getCalendar().get(1));
        ms = String.valueOf(fCompra.getCalendar().get(2) + 1);
        da = String.valueOf(fCompra.getCalendar().get(5));
        if (ms.length() == 1) {
            ms = "0" + ms;
        }
        fe = (an + "/" + ms + "/" + da);
    }

    public void TomarFechaCaducidad() {
        String an = "", ms = "", da = "";
        an = String.valueOf(fechaven.getCalendar().get(1));
        ms = String.valueOf(fechaven.getCalendar().get(2) + 1);
        da = String.valueOf(fechaven.getCalendar().get(5));
        if (ms.length() == 1) {
            ms = "0" + ms;
        }
        fec = (an + "/" + ms + "/" + da);
    }

    public void SeguirAgregandoInfo() {
        control.ejecutar(String.format("UPDATE doc_compra d SET d.`nume`='%s', d.`tipo`="
                + "'%s', d.`monto`='%s', d.`incluyeigv`=%s, d.`cometario`='%s', d.`credcont`='%s' "
                + "WHERE d.`idCompra`=%s;", txDocumento.getText(), cbTipoComprobante.getSelectedItem().toString(),
                lbTotalCompra.getText(), jCheckBox3.isSelected(), txtComentario.getText(),
                cbTipoCompra.getSelectedItem().toString(), info.getCompraeditar()));
        control.ejecutar(String.format("UPDATE compra c SET c.`fec`='%s' WHERE c.`idCompra`=%s;",
                control.Formato_Amd(fCompra.getDate()), info.getCompraeditar()));
        control.fila = contador;
        idProv = control.ObtenerDato("proveedor", "nompro",
                lbProveedor.getText(), 1);
        int contadordeingreso = 0;
        while (control.fila < tProductosingresar.getRowCount()) {
            codbr = tProductosingresar.getValueAt(control.fila, 2).toString().replace("'", "\\'");
            costo = tProductosingresar.getValueAt(control.fila, 4).toString();
            Garantia = tProductosingresar.getValueAt(control.fila, 3).toString();
            int cantidadproducto = Integer.parseInt(tProductosingresar.getValueAt(control.fila,
                     5).toString());
            contadordeingreso = 0;
            while (contadordeingreso < cantidadproducto) {
                idCatProd = control.ObtenerDato("catalogoproducto", "codctlg",
                        tProductosingresar.getValueAt(control.fila, 3).toString(), 1);
                control.Sql = "call IngresarProductos('','" + tProductosingresar.getValueAt(control.fila,
                        0).toString() + "','" + control.Formato_Amd(fCompra.getDate()) + "','"
                        + tProductosingresar.getValueAt(control.fila, 7).toString() + "','" + codbr + "','"
                        + costo + "','1','1','" + idProv + "','1',0,'" + coddoc + "','" + Controlador.sede + "',"
                        + "'Diponible');";
                System.out.println(control.Sql);
                control.CrearRegistro(control.Sql);
                contadordeingreso++;
            }
            control.fila++;
        }
        tProveedores.setEnabled(true);
        rb.setSelected(true);
        rb1.setEnabled(true);
        rb3.setEnabled(true);
        rbAños.setEnabled(true);
        rbBoleta.setEnabled(true);
        rbFactura.setEnabled(true);
        rbMeses.setEnabled(true);
        rbNo.setEnabled(true);
        rbRemision.setEnabled(true);
        rbSi.setEnabled(true);
        rb1.setSelected(true);
        rb3.setSelected(true);
        Cancelar();
        info.setCompraeditar(-1);
        JOptionPane.showMessageDialog(null, "Se ha ingresado los productos Correcamente!!!!");
    }

    public void IngresarProductos() {
        if (info.getCompraeditar() > 0) {
            SeguirAgregandoInfo();
        } else {
            GrabarNuevaFormanew();
        }
    }

    public void RegistrarIngreso() {
        if (txDocumento.getText().trim().length() > 0) {
            TomarFecha();
            TomarFechaCaducidad();
            /**
             * ************************CREAR COMPRA*********************************
             */
            idProv = control.ObtenerDato("proveedor", "nompro", lbProveedor.getText(), 1);
            control.fila = 0;
            control.Sql = "SELECT InsertaCompra('" + fe + "','" + idProv + "','0000','"
                    + txDocumento.getText() + "','" + tpodoc + "','" + control.ObtenerDato("sede", "nomse",
                     Controlador.sede, 1) + "','" + lbTotalCompra.getText() + "','1','comenta','Credito');";
            coddoc = control.DevolverRegistroDto(control.Sql, 1);

            while (control.fila < tProductosingresar.getRowCount()) {
                codbr = tProductosingresar.getValueAt(control.fila, 0).toString().replace("'", "\\'");
                costo = tProductosingresar.getValueAt(control.fila, 2).toString();
                Garantia = tProductosingresar.getValueAt(control.fila, 3).toString();
                JOptionPane.showMessageDialog(null, Garantia);
                ctp = 1;
                idCatProd = control.ObtenerDato("catalogoproducto", "codctlg",
                        tProductosingresar.getValueAt(control.fila, 1).toString(), 1);
                if (tProductosingresar.getValueAt(control.fila, 4).toString().equals("Meses")) {
                    per = 1;
                } else {
                    per = 2;
                }
                control.Sql = "call IngresarProductos('" + Garantia + "','" + tProductosingresar.getValueAt(control.fila, 6).toString()
                        + "','" + fe + "','" + tProductosingresar.getValueAt(control.fila, 5).toString() + "','"
                        + codbr + "','" + costo + "','" + per + "','" + ctp + "','" + idProv + "','1',0,'" + coddoc + "','"
                        + Controlador.sede + "','" + tProductosingresar.getValueAt(control.fila, 7).toString() + "');";
//     System.out.println(control.Sql);
                control.CrearRegistro(control.Sql);
                control.Sql = "SELECT * FROM estado_sinicial";
                inve = control.DevolverRegistroDto(control.Sql, 2);
                if (inve.equals("1")) {//inventario inicial activo             
                    control.Sql = "select * from producto order by idProducto desc limit 1";
                    idCatProd = control.DevolverRegistroDto(control.Sql, 1);
                    control.Sql = "call IngresoInventarioInicial('" + control.ObtenerDato("sede",
                            "nomse", Controlador.sede, 1) + "','" + idCatProd + "','" + idProv + "')";
                    control.CrearRegistro(control.Sql);
                }
                control.fila++;
            }
            JOptionPane.showMessageDialog(null, "Se ha Ingresado los Productos Correctamente!!!");
            Cancelar();
            /**
             * ************************FIN CREA GARANTIA POR COMPRA*****************
             */
        } else {
            JOptionPane.showMessageDialog(rootPane, "Faltan datos");
            txDocumento.requestFocus();
        }
    }

    public void LimpiarDatos1() {
        jLabel1.setText(null);
        txCosto.setText(null);
        txGarantia.setText(null);
        txSerie.setText(null);
        txcodigo.setText(null);
        tProductos.clearSelection();
        control.MarcarTexto(txBuscarproductos);
        rb.setSelected(true);
        rb1.setSelected(true);
        jCheckBox1.setSelected(false);
        txCantidad.setText(null);
        jCheckBox2.setSelected(false);
        txCantidadDefectuosa.setText("0");
        MostrarCatalogo();
    }

    public void LimpiarDatos2() {
        LimpiarDatos1();
        rbBoleta.setSelected(false);
        rbFactura.setSelected(false);
        rbRemision.setSelected(false);
        control.LimTablaDefault(model);
        txDocumento.setText("");
        rb3.setSelected(true);
        txBuscarproveedores.grabFocus();
    }

    public void Cancelar() {
        LimpiarDatos1();
        lbProveedor.setText(" ");
        rbBoleta.setSelected(false);
        rbFactura.setSelected(false);
        rbRemision.setSelected(false);
        tProveedores.clearSelection();
        control.LimTablaDefault(model);
        txDocumento.setText("");
        rb3.setSelected(true);
        controlado = 0;
        txBuscarproveedores.grabFocus();
        cuantosclick = 0;
        txCosto.setEditable(false);
        txSerie.setEditable(false);
        txGarantia.setEditable(false);
        txCantidad.setEditable(false);
        txcodigo.setEditable(false);
        lbTotalCompra.setText("0.00");
        fDePago.setDate(new Date());
        fCompra.setDate(new Date());
        txtComentario.setText("");
        cbTipoComprobante.setSelectedItem("Compra Libre");
        cbTipoCompra.setSelectedItem("Contado");
        jCheckBox3.setSelected(false);
        txBuscarproveedores.setEnabled(true);
        bClienete.setEnabled(true);
        cbTipoCompra.setEnabled(true);
    }

    public void MostrarProveedores() {
        BuscarProveedores();
    }

    public void BuscarProveedores() {
        if (control.bandera1 == true) {
            control.Sql = "SELECT * FROM vta_losproveedores WHERE `Proveedor`="
                    + "'PROVEEDOR GENERAL'";
        } else {
            control.Sql = "select * from vta_losproveedores where ( ruc like'"
                    + txBuscarproveedores.getText() + "%' or proveedor like'"
                    + txBuscarproveedores.getText() + "%')";
        }
        control.LlenarJTabla(modelo, control.Sql, 3);
    }

    public void VerProveedor() {
        control.fila = tProveedores.getSelectedRow();
        if (control.fila >= 0) {
            LimpiarDatos2();
            jLabel13.setText(tProveedores.getValueAt(control.fila, 2).toString());
            lbProveedor.setText(tProveedores.getValueAt(control.fila, 2).toString());
            controlado = 1;
            if (rbNo.isSelected()) {
                GeneraCodigoProducto();
            }
        }
    }

    public void BuscarCatalogo() {
        control.Sql = " select `cp`.`codctlg` AS `Codigo`,codbarra,concat(`mo`.`nommod`,"
                + "' ',`m`.`nommrc`,' ',`cp`.`nomctlg`) AS `Producto`,`cp`.`prexmenor` AS `prexmenor`"
                + ",`cp`.`precsg` AS `Precio` from ((((`marca` `m` join `modelo` `mo`) join "
                + "`modelocatalogo` `mc`) join `catalogoproducto` `cp`) join `unidad` `u`) "
                + " where ((`cp`.`idMarca` = `m`.`idMarca`) and (`cp`.`idUnidad` = `u`.`idUnidad`)"
                + " and (`mo`.`idModelos` = `mc`.`idModelos`) and (`cp`.`codctlg` = `mc`.`codctlg`))"
                + " and (concat(`mo`.`nommod`,' ',`m`.`nommrc`,' ',`cp`.`nomctlg`) like '%"
                + txBuscarproductos.getText() + "%' or `mo`.`nommod` like '%" + txBuscarproductos.getText()
                + "%' or `m`.`nommrc` like '%" + txBuscarproductos.getText() + "%' or codbarra like '%"
                + txBuscarproductos.getText() + "%' );";
        control.LlenarJTabla(modelo1, control.Sql, 5);
    }

    public void MostrarCatalogo() {
        BuscarCatalogo();
    }

    public void VerCatalogo() {
        control.fila = tProveedores.getSelectedRow();
        if (controlado > 0 && lbProveedor.getText().trim().length() > 0) {
            control.fila = tProductos.getSelectedRow();
            if (control.fila >= 0) {
                codcatlg = tProductos.getValueAt(control.fila, 0).toString();
                jLabel1.setText(tProductos.getValueAt(control.fila, 1).toString());
                txCosto.setText(null);
                txCosto.setText(tProductos.getValueAt(control.fila, 4).toString());
                txCosto.setEditable(true);
                txSerie.setEditable(true);
                txGarantia.setEditable(true);
                txCantidad.setEditable(true);
                txcodigo.setEditable(true);
                txCosto.grabFocus();
            }
        } else {
//    System.out.println("Ingreso bien dos");
            tProductos.clearSelection();
            JOptionPane.showMessageDialog(rootPane, "Seleccione primero al Proveedor");
        }
    }

    public void ObtenerGarantia() {
        int dto = 0, cantd = 0;
        if (rbMeses.isSelected()) {
            dto = 1;
        }
        if (rbAños.isSelected()) {
            dto = 2;
        }
        cantd = Integer.parseInt(txGarantia.getText());
        JOptionPane.showMessageDialog(rootPane, control.ObtenerFechaGarantia(Integer.toString(dto), txGarantia.getText()));
    }

    public void sacartotalcompra() {
        if (tProductosingresar.getRowCount() > 0) {
            control.fila = 0;
            double costototal = 0;
            while (control.fila < tProductosingresar.getRowCount()) {
                costototal = costototal + (Double.parseDouble(tProductosingresar.getValueAt(control.fila,
                         6).toString()));
                control.fila++;
            }
            if (jCheckBox3.isSelected()) {
                costototal = costototal * 1.18;
            }
            //lbTotalCompra.setText(""+String.valueOf(forma.format(costototal).replace(",", ".")));
            lbTotalCompra.setText("" + String.valueOf(Double.toString(costototal).replace(",", ".")));
        } else {
            lbTotalCompra.setText("0.00");
        }
    }

    ;
    public void EscribirGarantia(KeyEvent e) {
        control.bandera = false;
        if (rbMeses.isSelected()) {
            control.bandera = true;
        }
        if (rbAños.isSelected()) {
            control.bandera = true;
        }
        if (control.bandera) {
            control.Solonumeros(e);
            control.Longitudtx(txGarantia, e, 2);
        } else {
            e.consume();
        }
    }

    public void AgregarVarios(String est) {
        int contr = 0;
        TomarFechaCaducidad();
        /**
         * **************************************Redefiniendo****************************
         */
        if (rbSi.isSelected()) {
            Garantia = "";
            if (txGarantia.getText().trim().length() > 0) {
                if (rbMeses.isSelected()) {
                    Garantia = "Meses";
                }
                if (rbAños.isSelected()) {
                    Garantia = "Años";
                }
            } else {
                txGarantia.setText("0");
            }
            for (int i = 1; i <= Integer.parseInt(txCantidad.getText()); i++) {
                control.Data[0] = txcodigo.getText();
                control.Data[1] = Produc;
                control.Data[2] = txCosto.getText();
                if (jCheckBox2.isSelected()) {
                    Serie = JOptionPane.showInputDialog(rootPane, "Ingrese la serie " + i);
                }
                control.Data[3] = Serie;
                control.Data[4] = Garantia;
                control.Data[5] = fec;
                control.Data[6] = codcatlg;
                if (i > (Integer.parseInt(txCantidad.getText()) - Integer.parseInt(
                        txCantidadDefectuosa.getText()))) {
                    control.Data[7] = "Defectuosa";
                } else {
                    control.Data[7] = "Disponible";
                }
                model.addRow(control.Data);
                total = Double.parseDouble(txCosto.getText())
                        * Double.parseDouble(txCantidad.getText());
                lbTotal.setText("S/. " + total);
            }
            LimpiarDatos1();
            jLabel1.setText("");
            txCosto.setEditable(false);
        }
        if (rbNo.isSelected()) {
            System.out.println("jhon jamanca");
            Garantia = "";
            if (txGarantia.getText().trim().length() > 0) {
                if (rbMeses.isSelected()) {
                    Garantia = "Meses";
                }
                if (rbAños.isSelected()) {
                    Garantia = "Años";
                }
            }
            control.Data[0] = txcodigo.getText();
            control.Data[1] = Produc;
            control.Data[2] = txCosto.getText();
            if (jCheckBox2.isSelected()) {
                Serie = JOptionPane.showInputDialog(rootPane, "Ingrese la serie " + cant);
            }
            control.Data[3] = Serie;
            control.Data[4] = Garantia;
            control.Data[5] = fec;
            control.Data[6] = codcatlg;
            control.Data[7] = est;
            total = Double.parseDouble(txCosto.getText()) * Double.parseDouble(txCantidad.getText());
            lbTotal.setText("S/. " + total);
            model.addRow(control.Data);
        }
        txCosto.setEditable(false);
        txSerie.setEditable(false);
        txGarantia.setEditable(false);
        txCantidad.setEditable(false);
        txcodigo.setEditable(false);
        /**
         * **************************************Fin Redefiniendo*************************
         */
    }

    public void Agregar() {
        int contr = 0;
        if (txSerie.getText().trim().length() == 0) {
        } else {
            control.Sql = "SELECT count(*) FROM vtaprodcutostotal where seri='" + txSerie.getText() + "';";
            contr = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
        }
        if (control.BuscarDatoenJtable(modelo, txSerie.getText(), 3)) {
            JOptionPane.showMessageDialog(rootPane, "La serie del producto esta repetida en esta Compra");
            txSerie.requestFocus();
            return;
        } else {
            if (contr == 1) {
                control.Sql = "SELECT concat('PRODUCTO:  ',nomctlg),concat('SERIE: ',seri),concat('CODIGO: ', codbrr)AS DD FROM vtaprodcutostotal where seri='" + txSerie.getText() + "';";
                String conts = control.DevolverRegistroDto(control.Sql, 1);
                String conts1 = control.DevolverRegistroDto(control.Sql, 2);
                String conts2 = control.DevolverRegistroDto(control.Sql, 3);
                JOptionPane.showMessageDialog(null, "La serie ya se encuentra ingresada : \n"
                        + conts + "\n" + conts1 + "\n" + conts2, "Error!", JOptionPane.ERROR_MESSAGE);
                txSerie.requestFocus();
                return;
            }
        }
        Garantia = "";
        if (txGarantia.getText().trim().length() > 0) {
            if (rbMeses.isSelected()) {
                Garantia = "Meses";
            }
            if (rbAños.isSelected()) {
                Garantia = "Años";
            }
            control.Data[0] = txcodigo.getText();
            control.Data[1] = Produc;
            control.Data[2] = txCosto.getText();
            control.Data[3] = Serie;
            control.Data[4] = Garantia;
            control.Data[5] = fec;
            control.Data[6] = codcatlg;
            control.Data[7] = "Disponible";
            model.addRow(control.Data);
            LimpiarDatos1();
            jLabel1.setText("");
            txCosto.setEditable(false);
            txCosto.setEditable(false);
            txSerie.setEditable(false);
            txGarantia.setEditable(false);
            txCantidad.setEditable(false);
            txcodigo.setEditable(false);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Faltan datos");
        }
    }

    public boolean verificarexitencia() {
        boolean ver = false;
        control.Sql = "select count(*) from producto where codbrr='" + txcodigo.getText() + "'";
        String cer = control.DevolverRegistroDto(control.Sql, 1);
        if (cer.equals("0")) {
            ver = true;
        }
        return ver;
    }

    public void CapturarCodigo() {
        if (txcodigo.getText().trim().length() > 0) {
            Serie = txSerie.getText();
            Produc = jLabel1.getText();
            if (txCantidad.getText().trim().length() > 0) {
                AgregarVarios("Disponible");
            } else {
                Agregar();
            }
        }
    }

    public void SeleccionarSi() {
        txcodigo.setEditable(true);
        if (jCheckBox1.isSelected()) {
            txcodigo.setText(txSerie.getText());
        } else {
            txcodigo.setText(null);
            txcodigo.setEditable(true);
            txcodigo.requestFocus();
        }
    }

    public void SeleccionarNo() {
        int ctd = 0;
        Produc = jLabel1.getText();
        Serie = txSerie.getText();
        if (txCantidad.getText().trim().length() == 0) {
            ctd = 1;
        } else {
            ctd = Integer.parseInt(txCantidad.getText().trim());
        }
        int cntodoado = 0;
        for (cant = 1; cant <= ctd; cant++) {
            if (cntodoado == 0) {
                GeneraCodigoProducto();
                cntodoado++;
            }
            if (cant > (Integer.parseInt(txCantidad.getText()) - Integer.parseInt(
                    txCantidadDefectuosa.getText()))) {
                AgregarVarios("Defectuosa");
            } else {
                AgregarVarios("Disponible");
            }
        }
        sacartotalcompra();
        LimpiarDatos1();
        jLabel1.setText("");
        txCosto.setEditable(false);
    }

    public void GeneraCodigoProducto() {
        control.bandera = false;
        control.Veces = 0;
        boolean b = false;
        if (lbProveedor.getText().trim().length() < 0) {
            rb.setSelected(true);
        } else {
            String fech = "", ltr = "", cod = "", val = "";
            fech = control.Fecha().substring(2, 4)
                    + control.Fecha().substring(5, 7) + control.Fecha().substring(8, 10);
            ltr = control.ObtenerDato("proveedor", "nompro", lbProveedor.getText(), 5);
            cod = ltr + "_" + fech;
            control.Veces++;
            cod = cod + "_00" + cuantosclick;//+control.GeneraNumero(control.Veces);            
            while (!control.bandera) {
                control.Sql = "select * from producto where codbrr='" + cod + "'";
                if (control.Verificandoconsulta(control.Sql)) {
                    control.Veces++;
                    cod = ltr + "_" + fech + "_00" + cuantosclick++;
                } else {
                    control.bandera = true;
                }
            }
            if (model.getRowCount() > 0) {
                control.bandera = false;
                while (!control.bandera) {
                    control.bandera = control.BuscarDatoenJtable(modelo, cod, 0);
                    if (control.bandera) {
                        control.Veces++;
                        cod = ltr + "_" + fech;
                        cod = cod + "_00" + cuantosclick;//+control.GeneraNumero(control.Veces);control.bandera=false;
                    } else {
                        control.bandera = true;
                    }
                }
            }
            txcodigo.setText(cod);
            txcodigo.setEditable(false);
        }
    }

    public boolean SeleccionarDocumento() {
        control.bandera = false;
        if (rbBoleta.isSelected()) {
            control.bandera = true;
        }
        if (rbFactura.isSelected()) {
            control.bandera = true;
        }
        if (rbRemision.isSelected()) {
            control.bandera = true;
        }
        return control.bandera;
    }

    public void CargarEditar() {
        controlado = 1;
        if (info.getCompraeditar() > 0) {
            control.Sql = String.format(" SELECT p.`nompro`, d.`tipo`, d.`nume`,concat(substring(c.`fec`,9,2),'-',substring(c.`fec`,6,2),'-',substring(c.`fec`,1,4)), d.`credcont`,concat(substring(d.`fechapago`,9,2),'-',substring(d.`fechapago`,6,2),'-',substring(d.`fechapago`,1,4)), d.`cometario`, "
                    + " d.`incluyeigv`, d.`monto`,d.`idDoc_Compra` FROM compra c, doc_compra d, proveedor p "
                    + " WHERE c.`idCompra` = d.`idCompra` AND c.`idProveedor` = p.`idProveedor` AND c.`idCompra`=%s;", info.getCompraeditar());
            lbProveedor.setText(control.DevolverRegistroDto(control.Sql, 1));
            cbTipoComprobante.setSelectedItem(control.DevolverRegistroDto(control.Sql, 2));
            txDocumento.setText(control.DevolverRegistroDto(control.Sql, 3));
            control.PonerFechaenDateChooser(fCompra, control.DevolverRegistroDto(control.Sql, 4));
            cbTipoCompra.setSelectedItem(control.DevolverRegistroDto(control.Sql, 5));
            control.PonerFechaenDateChooser(fDePago, control.DevolverRegistroDto(control.Sql, 6));
            txtComentario.setText(control.DevolverRegistroDto(control.Sql, 7));
            lbTotalCompra.setText(control.DevolverRegistroDto(control.Sql, 9));
            coddoc = control.DevolverRegistroDto(control.Sql, 10);
            if (control.DevolverRegistroDto(control.Sql, 8).equals("1")) {
                jCheckBox3.setSelected(true);
            }
            cbTipoCompra.setEnabled(false);//System.out.println(control.Sql);
            control.Sql = String.format("SELECT c.`codctlg`, p.`codbrr`, c.`nomctlg`, "
                    + "p.`costo`, count(p.`Catalogoproducto_codctlg`) as cnt,p.`costo`* "
                    + "count(p.`Catalogoproducto_codctlg`) as tot, f.`fecha_cad` FROM doc_compra"
                    + " d, producto p, catalogoproducto c, garantiaxcompra g, fecha_caducidad f "
                    + " WHERE d.`idDoc_Compra` = p.`idDoc_Compra` AND p.`Catalogoproducto_codctlg` = "
                    + "c.`codctlg` AND p.`idProducto`=f.`id_Producto` AND p.`idProducto` = "
                    + "g.`idProducto` AND d.`idCompra`=%s GROUP BY p.`Catalogoproducto_codctlg`;",
                     info.getCompraeditar());
            try {
                control.Base.st = control.Base.conec.createStatement();
                control.Base.rt = control.Base.st.executeQuery(control.Sql);
                while (control.Base.rt.next()) {
                    dat[0] = control.Base.rt.getString(1);
                    dat[1] = "" + (++numerodeproductoingresados);
                    dat[2] = control.Base.rt.getString(2);
                    dat[3] = control.Base.rt.getString(3);
                    dat[4] = control.Base.rt.getString(4);
                    dat[5] = control.Base.rt.getString(5);
                    dat[6] = control.Base.rt.getString(6);
                    dat[7] = control.Base.rt.getString(7);
                    contador++;
                    model.addRow(dat);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void ActualizarCostoProducto() {
        if (tProductosingresar.getRowCount() > 0) {
            control.fila = 0;
            while (control.fila < tProductosingresar.getRowCount()) {
                //control.Sql="update catalogoproducto "   
                control.fila++;
            }
        }
    }

    public void ReacomodarItems() {
        int n = 0;
        if (tProductosingresar.getRowCount() > 0) {
            for (int f = 0; f < tProductosingresar.getRowCount(); f++) {
                n++;
                tProductosingresar.setValueAt(n, f, 0);
            }
        }
    }

    private void CargarProducto() {
        String producto = tProductos.getValueAt(tProductos.getSelectedRow(), 2).toString();
        control.bandera = VerificarenTabla(producto, tProductosingresar, 3);
        if (!control.bandera) {
            dat[0] = tProductos.getValueAt(tProductos.getSelectedRow(), 0).toString();
            dat[1] = "" + (++numerodeproductoingresados);
            dat[2] = tProductos.getValueAt(tProductos.getSelectedRow(), 1).toString();
            dat[3] = tProductos.getValueAt(tProductos.getSelectedRow(), 2).toString();
            dat[4] = tProductos.getValueAt(tProductos.getSelectedRow(), 4).toString();
            dat[5] = "0";
            dat[6] = "0.00";
            dat[7] = fechaactual;
            model.addRow(dat);
            ReacomodarItems();
        } else {
            JOptionPane.showMessageDialog(null, "El producto \n" + producto + "\n Ya esta ingresado Solo falta ingresar la cantidad");
            tProductosingresar.grabFocus();
        }
        txBuscarproductos.setText("");
    }

    private String InsertayobtenerId(String tabla, String campo, String dato) {
        String codigomode = "";
        if (control.Existe(tabla, campo, dato)) {
            codigomode = control.DevolverRegistroDto("select * from " + tabla + " where " + campo
                    + "='" + dato + "';", 1);
        } else {
            if (tabla.equals("unidad")) {
                codigomode = String.valueOf(control.executeAndGetId(String.format("INSERT INTO "
                        + tabla + " VALUES(NULL,'%s','');", dato)));
            } else {
                codigomode = String.valueOf(control.executeAndGetId(String.format("INSERT INTO "
                        + tabla + " VALUES(NULL,'%s');", dato)));
            }
        }
        return codigomode;
    }

    private void capturauno() {
        lbProveedor.setText(tProveedores.getValueAt(0, 2).toString());
        controlado = 1;
        txBuscarproductos.grabFocus();
    }

    public void llenarcontar() {
        int contd = 0;
        while (contd < tProductosingresar.getRowCount()) {
            tProductosingresar.setValueAt((contd + 1), contd, 1);
            System.out.println("valor " + (contd + 1) + " fila " + contd);
            contd++;
        }
    }

    private void leerArchivoExcel(String archivoDestino) {
        String fec, prov, serinum, codcatlg, tipdoc, feinal, codbrr, cost, prev, ser, marc, model,
                unid, abr, descripcatlg, presug, prexmen, prexmay, fecin, fecfin, period, cantper;
        try {
            idProv = control.ObtenerDato("proveedor", "nompro", lbProveedor.getText(), 1);
            WorkbookSettings confi = new WorkbookSettings();
            confi.setEncoding("ISO-8859-1");
            Workbook archivoExcel = Workbook.getWorkbook(new File(archivoDestino), confi);
            jProgressBar1.setVisible(true);
            for (int sheetNo = 0; sheetNo < archivoExcel.getNumberOfSheets(); sheetNo++) {
                Sheet hoja = archivoExcel.getSheet(sheetNo);
                int numFilas = hoja.getRows();
                jProgressBar1.setMaximum(numFilas);
                jProgressBar1.setValue(0);
                int flag = 0;
                for (int fila = 5; fila < numFilas; fila++) {
                    jProgressBar1.setValue(fila + 1);
                    if (flag == 0) {
                        fec = hoja.getCell(2, fila).getContents().toString();
                        serinum = hoja.getCell(4, fila).getContents().toString() + "-" + hoja.getCell(5,
                                fila).getContents().toString();
                        tipdoc = hoja.getCell(2, fila).getContents().toString();
                        control.Sql = String.format("select InsertaCompra('%s','%s','%s','%s','%s','%s',"
                                + "'0.00','1','','Contado',curdate())", fec, "2", "0000", serinum, tipdoc, info.getIdSede());
                        coddoc = control.DevolverRegistroDto(control.Sql, 1);
                        flag++;
                    }
                    ////captura los idunidad
                    unid = control.DevolverRegistroDto("select count(idunidad) from unidad where nomuni='"
                            + hoja.getCell(15, fila).getContents().toString() + "' or abre='" + hoja.getCell(16,
                            fila).getContents().toString() + "';", 1);
                    if (unid.equals("0")) {
                        control.ejecutar(String.format("insert into unidad values(null,'%s','%s');",
                                hoja.getCell(15, fila).getContents().toString(), hoja.getCell(16,
                                fila).getContents().toString()));
                        unid = control.DevolverRegistroDto("select idunidad "
                                + "from unidad where nomuni='" + hoja.getCell(15, fila).getContents().toString()
                                + "' or abre='" + hoja.getCell(16, fila).getContents().toString() + "';", 1);
                    } else {
                        unid = control.DevolverRegistroDto("select idunidad from unidad where nomuni='"
                                + hoja.getCell(15, fila).getContents().toString() + "' or abre='" + hoja.getCell(16,
                                fila).getContents().toString() + "';", 1);
                    }
                    //captura los idmodelos
                    if (control.Existe("modelo", "nommod", hoja.getCell(14, fila).getContents().toString())) {
                        model = control.DevolverRegistroDto("select idmodelos from modelo where nommod='"
                                + hoja.getCell(14, fila).getContents().toString() + "';", 1);
                    } else {
                        control.ejecutar(String.format("insert into modelo values(null,'%s');",
                                hoja.getCell(14, fila).getContents().toString()));
                        model = control.DevolverRegistroDto("select idmodelos from modelo where nommod='"
                                + hoja.getCell(14, fila).getContents().toString() + "';", 1);
                    }
                    //captura los idmarca
                    if (control.Existe("marca", "nommrc", hoja.getCell(13, fila).getContents().toString())) {
                        marc = control.DevolverRegistroDto("select idmarca from marca where nommrc='"
                                + hoja.getCell(13, fila).getContents().toString() + "'", 1);
                    } else {
                        control.ejecutar(String.format("insert into marca values(null,'%s');",
                                hoja.getCell(13, fila).getContents().toString()));
                        marc = control.DevolverRegistroDto("select idmarca from marca where nommrc='"
                                + hoja.getCell(13, fila).getContents().toString() + "'", 1);
                    }
                    codcatlg = control.DevolverRegistroDto(String.format("select count(codctlg) from "
                            + "catalogoproducto where nomctlg='%s' and idmarca='%s' and idmodelos='%s' and"
                            + " idunidad='%s';", hoja.getCell(17, fila).getContents().toString(), marc, model, unid), 1);
                    if (codcatlg.equals("0")) {
                        control.ejecutar(String.format("insert into catalogoproducto values(null,'%s'"
                                + ",'%s','%s','%s','%s','%s','%s','%s',curdate());",
                                hoja.getCell(17, fila).getContents().toString(), hoja.getCell(18, fila).getContents().toString(),
                                marc, model, unid, hoja.getCell(21, fila).getContents().toString(),
                                hoja.getCell(19, fila).getContents().toString(), hoja.getCell(20, fila).getContents().toString()));
                        codcatlg = control.DevolverRegistroDto(String.format("select codctlg from "
                                + "catalogoproducto where nomctlg='%s' and idmarca='%s' and idmodelos='%s' "
                                + "and idunidad='%s';", hoja.getCell(17, fila).getContents().toString(), marc,
                                model, unid), 1);
                        control.Sql = "insert into modelocatalogo(codctlg,idModelos) values"
                                + "('" + codcatlg + "','" + model + "')";
                        control.CrearRegistro(control.Sql);
                    } else {
                        codcatlg = control.DevolverRegistroDto(String.format("select codctlg from "
                                + "catalogoproducto where nomctlg='%s' and idmarca='%s' and idmodelos='%s' and "
                                + "idunidad='%s';", hoja.getCell(17, fila).getContents().toString(), marc, model, unid), 1);
                    }
                    control.Sql = "call IngresarProductos("
                            + "'" + hoja.getCell(12, fila).getContents().toString() + "','" + codcatlg + "',"
                            + "'" + hoja.getCell(7, fila).getContents().toString() + "','" + hoja.getCell(26,
                            fila).getContents().toString() + "','" + hoja.getCell(9, fila).getContents().toString() + "',"
                            + "'" + hoja.getCell(10, fila).getContents().toString() + "','" + hoja.getCell(24,
                            fila).getContents().toString() + "',"
                            + "'" + hoja.getCell(25, fila).getContents().toString() + "','" + hoja.getCell(3,
                            fila).getContents().toString() + "','1',0,'" + coddoc + "'," + "'" + Controlador.sede + "');";
                    control.CrearRegistro(control.Sql);
                    control.Sql = "SELECT * FROM estado_sinicial";
                    inve = control.DevolverRegistroDto(control.Sql, 2);
                    if (inve.equals("1")) {//inventario inicial activo
                        control.Sql = "select * from producto order by idProducto desc limit 1";
                        idCatProd = control.DevolverRegistroDto(control.Sql, 1);
                        control.Sql = "call IngresoInventarioInicial('" + control.ObtenerDato("sede",
                                "nomse", Controlador.sede, 1) + "','" + idCatProd + "','" + idProv + "')";
                        control.CrearRegistro(control.Sql);
                    }
                }
            }
            //MostrarBancos();
            JOptionPane.showMessageDialog(null, "Se ha Ingresado Correctamente");
            txDocumento1.setVisible(false);
            jProgressBar1.setVisible(false);
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }

    private void LeerexcelInventario() {
        control.Sql = "select count(*) from doc_compra where tipo='Compra libre' and serie='0000' "
                + "and nume='001-00001' and idSede='" + info.idSede + "'";

        if (Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1)) > 0) {
            JOptionPane.showMessageDialog(null, "Ya se ingreso el inventario inicial para la sede " + Controlador.sede);
            return;
        }
        if (bLeerInventario.getText().equals("Inicial")) {
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Archivo Excel (.xls)", "xls"));
            int retVal = chooser.showOpenDialog(this);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                txDocumento1.setText(chooser.getSelectedFile().getAbsolutePath());
                jProgressBar1.setEnabled(true);
                txDocumento1.setVisible(true);
            }
            bLeerInventario.setText("Procesar");
        } else {
            if (txDocumento1.getText().length() > 0) {
                SwingWorker worker = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        //leerinventario(txDocumento1.getText());return true;
                        AgregandoInventario(txDocumento1.getText());
                        return true;
                    }

                    @Override
                    protected void done() {
                        txDocumento1.setText("");
                        bLeerInventario.setText("Inicial");
                    }
                };
                worker.execute();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor primero seleccione un archivo para procesar",
                        "Atención", JOptionPane.WARNING_MESSAGE);
                bLeerInventario.setText("Inicial");
            }
        }
    }

    private void GrabarNuevaFormanew() {
        if (lbProveedor.getText().trim().length() > 0) {
            /**
             * ************************CREAR COMPRA*********************************
             */
            idProv = control.ObtenerDato("proveedor", "nompro", lbProveedor.getText(), 1);  //Captura el identificador del proveedor         
            control.Sql = "SELECT InsertaCompra('" + control.Formato_Amd(fCompra.getDate())
                    + "','" + idProv + "','0000','" + txDocumento.getText() + "','" + cbTipoComprobante.getSelectedItem().toString()
                    + "','" + control.ObtenerDato("sede", "nomse", Controlador.sede, 1) + "','" + lbTotalCompra.getText()
                    + "'," + jCheckBox3.isSelected() + ",'" + txtComentario.getText() + "','"
                    + cbTipoCompra.getSelectedItem().toString() + "','" + control.Formato_Amd(fDePago.getDate()) + "');";

            coddoc = control.DevolverRegistroDto(control.Sql, 1);

            if (cbTipoCompra.getSelectedItem().toString().equals("Contado")) {
                if (control.DevolverRegistroDto(String.format("SELECT count(*) FROM pagos p "
                        + "WHERE p.`id`=%s AND p.`tipo`='Compra';", coddoc), 1).equals("0")) {

                    control.ejecutar(String.format("INSERT INTO pagos VALUES(NULL,'%s',curtime(),"
                            + "'%s','Compra','%s','%s','%s','%s','%s',0.00);", control.Formato_Amd(fCompra.getDate()),
                             coddoc, info.getUsuario(), (Double.parseDouble(lbTotalCompra.getText())),
                            lbProveedor.getText(), cbTipoComprobante.getSelectedItem().toString(), txDocumento.getText()));

                } else {
                    control.ejecutar(String.format("UPDATE pagos p SET  p.`fecha`='%s', p.`monto`='%s', "
                            + "p.`comprobante`='%s', p.`numerocomprobante`='%s' WHERE p.`id`=%s AND p.`tipo`='Compra';",
                            control.Formato_Amd(fCompra.getDate()), (Double.parseDouble(lbTotalCompra.getText())),
                             cbTipoComprobante.getSelectedItem().toString(), txDocumento.getText(), coddoc));
                }
            }

            if (tProductosingresar.getRowCount() > 0) {
                control.fila = 0;
                int contadordeingreso = 0;
                double cst = 0;
                while (control.fila < tProductosingresar.getRowCount()) {
                    codbr = tProductosingresar.getValueAt(control.fila, 2).toString().replace("'", "\\'");
                    costo = tProductosingresar.getValueAt(control.fila, 4).toString();
                    Garantia = tProductosingresar.getValueAt(control.fila, 3).toString();
                    int cantidadproducto = Integer.parseInt(tProductosingresar.getValueAt(control.fila,
                            5).toString());
                    contadordeingreso = 0;

                    /*
        if(actcosto.isSelected()){         
         control.Sql="select if(round(avg(costo),2)>0,round(avg(costo),2),0.00) from producto where codbrr='"
         +tProductosingresar.getValueAt(control.fila,2).toString()+" ' and estdo='Disponible';";               
         if(Double.parseDouble(control.DevolverRegistroDto(control.Sql,1))>0){
          control.Sql="update catalogoproducto set precsg='"+control.DevolverRegistroDto(control.Sql,1)
          +"' where codbarra='"+tProductosingresar.getValueAt(control.fila,2).toString()+"'";     
         }
         else{
          control.Sql="update catalogoproducto set precsg='"+tProductosingresar.getValueAt(
          control.fila,4).toString()+"' where codbarra='"+tProductosingresar.getValueAt(control.fila,
          2).toString()+"'";  
         }
         control.EditarRegistro(control.Sql);
        }      */
                    while (contadordeingreso < cantidadproducto) {
                        idCatProd = control.ObtenerDato("catalogoproducto", "codctlg",
                                tProductosingresar.getValueAt(control.fila, 3).toString(), 1);
                        control.Sql = "call IngresarProductos('','" + tProductosingresar.getValueAt(
                                control.fila, 0).toString() + "','" + control.Formato_Amd(fCompra.getDate()) + "','"
                                + tProductosingresar.getValueAt(control.fila, 7).toString() + "','" + codbr + "','"
                                + costo + "','1','1','" + idProv + "','1',0,'" + coddoc + "','" + Controlador.sede
                                + "','Disponible');";
                        control.CrearRegistro(control.Sql);
                        contadordeingreso++;
                    }

                    if (actcosto.isSelected()) {
                        //control.Sql="select if(round(avg(costo),2)>0,round(avg(costo),2),0.00) from producto where codbrr='"
                        control.Sql = "select if(round(costo,2)>0,round(costo,2),0.00) from producto where codbrr='"
                                + tProductosingresar.getValueAt(control.fila, 2).toString() + " ' and estdo='Disponible';";

                        if (Double.parseDouble(control.DevolverRegistroDto(control.Sql, 1)) > 0) {
                            JOptionPane.showMessageDialog(null, "Actualizando el costo Es: " + tProductosingresar.getValueAt(control.fila, 4).toString());

                            //control.Sql="update catalogoproducto set precsg='"+control.DevolverRegistroDto(control.Sql,1)
                            control.Sql = "update catalogoproducto set precsg='" + tProductosingresar.getValueAt(control.fila, 4).toString()
                                    + "' where codbarra='" + tProductosingresar.getValueAt(control.fila, 2).toString() + "'";
                        } else {
                            JOptionPane.showMessageDialog(null, "Vamos por el otro lado: " + tProductosingresar.getValueAt(control.fila, 4).toString());
                            control.Sql = "update catalogoproducto set precsg='" + tProductosingresar.getValueAt(control.fila, 4).toString()
                                    + "' where codbarra='" + tProductosingresar.getValueAt(control.fila, 2).toString() + "'";
                        }
                        control.EditarRegistro(control.Sql);
                    }

                    control.fila++;
                }
            }
            Cancelar();
        } else {
            JOptionPane.showMessageDialog(null, "Selecione un Proveedor para registrar la "
                    + "compra", "System Message", 2);
        }
    }

    private void GrabarNuevaForma() {
        if (lbProveedor.getText().trim().length() > 0) {
            /**
             * ************************CREAR COMPRA*********************************
             */
            idProv = control.ObtenerDato("proveedor", "nompro", lbProveedor.getText(), 1);
            //   control.fila=0;            
            control.Sql = "SELECT InsertaCompra('" + control.Formato_Amd(fCompra.getDate())
                    + "','" + idProv + "','0000','" + txDocumento.getText() + "','"
                    + cbTipoComprobante.getSelectedItem().toString() + "','"
                    + control.ObtenerDato("sede", "nomse", Controlador.sede, 1) + "','" + lbTotalCompra.getText()
                    + "'," + jCheckBox3.isSelected() + ",'" + txtComentario.getText() + "','"
                    + cbTipoCompra.getSelectedItem().toString() + "','" + control.Formato_Amd(fDePago.getDate())
                    + "');";
            coddoc = control.DevolverRegistroDto(control.Sql, 1);
            if (cbTipoCompra.getSelectedItem().toString().equals("Contado")) {
                if (control.DevolverRegistroDto(String.format("SELECT count(*) FROM pagos p "
                        + "WHERE p.`id`=%s AND p.`tipo`='Compra';", coddoc), 1).equals("0")) {
                    control.ejecutar(String.format("INSERT INTO pagos VALUES(NULL,'%s',curtime(),"
                            + "'%s','Compra','%s','%s','%s','%s','%s',0.00);",
                            control.Formato_Amd(fCompra.getDate()), coddoc, info.getUsuario(), (Double.parseDouble(
                            lbTotalCompra.getText())), lbProveedor.getText(), cbTipoComprobante.getSelectedItem().toString(),
                             txDocumento.getText()));
                } else {
                    control.ejecutar(String.format("UPDATE pagos p SET  p.`fecha`='%s', p.`monto`='%s', "
                            + "p.`comprobante`='%s', p.`numerocomprobante`='%s' WHERE p.`id`=%s AND p.`tipo`='Compra';",
                            control.Formato_Amd(fCompra.getDate()), (Double.parseDouble(lbTotalCompra.getText())),
                             cbTipoComprobante.getSelectedItem().toString(), txDocumento.getText(), coddoc));
                }
            }
            if (tProductosingresar.getRowCount() > 0) {
                control.fila = 0;
                int contadordeingreso = 0;
                while (control.fila < tProductosingresar.getRowCount()) {
                    codbr = tProductosingresar.getValueAt(control.fila, 2).toString().replace("'", "\\'");
                    costo = tProductosingresar.getValueAt(control.fila, 4).toString();
                    Garantia = tProductosingresar.getValueAt(control.fila, 3).toString();
                    int cantidadproducto = Integer.parseInt(tProductosingresar.getValueAt(control.fila,
                            5).toString());
                    contadordeingreso = 0;
                    while (contadordeingreso < cantidadproducto) {
                        idCatProd = control.ObtenerDato("catalogoproducto", "codctlg",
                                tProductosingresar.getValueAt(control.fila, 3).toString(), 1);
                        control.Sql = "call IngresarProductos('','" + tProductosingresar.getValueAt(
                                control.fila, 0).toString() + "','" + control.Formato_Amd(fCompra.getDate()) + "','"
                                + tProductosingresar.getValueAt(control.fila, 7).toString() + "','" + codbr + "','"
                                + costo + "','1','1','" + idProv + "','1',0,'" + coddoc + "','" + Controlador.sede + "','Disponible');";
                        control.CrearRegistro(control.Sql);
                        contadordeingreso++;
                    }
                    control.fila++;
                }
            }
            Cancelar();
        } else {
            JOptionPane.showMessageDialog(null, "Selecione un Proveedor para registrar la compra", "System Message", 2);
        }
    }

    public void AgregandoInventario(String txt) {
        String mdelos = "", unidad = "", marca = "", detalle = "", preciomayor = "", preciomenor = "",
                Codigobarr = "", preciocompra = "", stock = "", idmodelo = "", idmarca = "", idunidad = "", idcatalogo = "", unidadprese = "";
        try {
            /**
             * *****************capturamos el identificador del proveedore general************
             */
            idProv = control.ObtenerDato("proveedor", "nompro", "PROVEEDOR GENERAL", 1);
            /**
             * *****************capturamos el identificador del proveedore general************
             */

            /**
             * *********************Creamos el libro y la Hoja**************************
             */
            WorkbookSettings confi = new WorkbookSettings();
            confi.setEncoding("ISO-8859-1");
            Workbook archivoExcel = Workbook.getWorkbook(new File(txt), confi);
            jProgressBar1.setVisible(true);
            //int sheetNo = 1; 
            int sheetNo = 0;
            Sheet hoja = archivoExcel.getSheet(sheetNo);
            int numFilas = hoja.getRows();
            jProgressBar1.setMaximum(numFilas);
            jProgressBar1.setValue(0);
            jProgressBar1.setStringPainted(true);
            int flag = 0;
            double montotal = 0.00;
            /**
             * *********************Creamos el libro y la Hoja**************************
             */

            /**
             * *********************Creamos La primera compra y capturamos el id
             * del comprobante********
             */
            control.Sql = "select InsertaCompra(curdate(),'1','00000','000001','Factura','" + info.getIdSede()
                    + "','20000','1','Es el inventario Inicial','Contado',curdate())";
            coddoc = control.DevolverRegistroDto(control.Sql, 1);
            /**
             * *********************Creamos La primera compra y capturamos el id
             * del comprobante********
             */

            /**
             * *******************Recorremos las filas del Excel***************************
             */
            for (int fila = 2; fila < numFilas; fila++) {
                jProgressBar1.setValue(fila + 1);

                /**
                 * ****************************Capturamos los datos de la hoja*************************
                 */
                mdelos = hoja.getCell(1, fila).getContents().toString();
                if (mdelos.trim().length() == 0) {
                    return;
                }
                unidad = hoja.getCell(2, fila).getContents().toString();
                marca = hoja.getCell(3, fila).getContents().toString();
                unidadprese = hoja.getCell(8, fila).getContents().toString();
                detalle = hoja.getCell(4, fila).getContents() + " " + hoja.getCell(5, fila).getContents() + " "
                        + hoja.getCell(6, fila).getContents() + " " + hoja.getCell(7, fila).getContents() + " " + hoja.getCell(8, fila).getContents();
                preciomenor = hoja.getCell(9, fila).getContents().toString().replace(",", ".");
                preciomayor = hoja.getCell(10, fila).getContents().toString().replace(",", ".");
                Codigobarr = hoja.getCell(11, fila).getContents().toString();
                preciocompra = hoja.getCell(12, fila).getContents().toString().replace(",", ".") == "" ? "0.00" : hoja.getCell(12, fila).getContents().toString().replace(",", ".");
                stock = hoja.getCell(13, fila).getContents().toString().replace(",", ".") == "" ? "0" : hoja.getCell(13, fila).getContents().toString().replace(",", ".");
                /**
                 * ****************************Capturamos los datos de la hoja*************************
                 */

                /**
                 * ************************Creamos el modelos, unidad y marca*************************
                 */
                idmodelo = InsertayobtenerId("modelo", "nommod", mdelos.replace("'", "\\'").trim());
                idmarca = InsertayobtenerId("marca", "nommrc", marca.replace("'", "\\'").trim());
                /*if(!control.Verificarconsulta("select * from unidad where nomuni='"+unidad.trim()+"' and unimd='"+
         unidadprese.trim()+"'")){*/
                if (!control.Verificarconsulta("select * from unidad where nomuni='" + unidad.trim() + "'")) {
                    /*control.Sql="insert into unidad(nomuni,abre,unimd) values('"+unidad.replace("'","\\'").trim()+
         "','','"+unidadprese.trim()+"')";control.CrearRegistro(control.Sql);*/
                    control.Sql = "insert into unidad(nomuni,abre) values('" + unidad.replace("'", "\\'").trim()
                            + "','')";
                    control.CrearRegistro(control.Sql);
                    control.Sql = "select * from unidad order by 1 desc limit 1";
                    idunidad = control.DevolverRegistroDto(control.Sql, 1);
                } else {
                    control.Sql = "select * from unidad where nomuni='" + unidad.trim() + "'";
                    idunidad = control.DevolverRegistroDto(control.Sql, 1);
                }
                /**
                 * ************************Creamos el modelos, unidad y marca*************************
                 */

                /**
                 * *******************Creamos el catalogo de producto y
                 * devolvemos su ID*****************
                 */
                control.Sql = "insert into catalogoproducto(nomctlg,precsg,idMarca,idModelos,idUnidad,Stockmin,"
                        + "prexmenor,prexmayor,fechacad,codbarra) values('" + detalle.trim() + "'," + preciocompra + ",'" + idmarca
                        + "','" + idmodelo + "','" + idunidad + "','0'," + preciomenor + "," + preciomayor + ",curdate(),'" + Codigobarr + "'); ";
                control.CrearRegistro(control.Sql);
                control.Sql = "select * from catalogoproducto order by 1 desc limit 1";
                idcatalogo = control.DevolverRegistroDto(control.Sql, 1);
                /**
                 * *******************Creamos el catalogo de producto y
                 * devolvemos su ID*****************
                 */

                /**
                 * *******************Creamos el model catalogo de producto *****************
                 */
                control.Sql = "insert into modelocatalogo(codctlg,idModelos) values('" + idcatalogo
                        + "','" + idmodelo + "')";
                control.CrearRegistro(control.Sql);
                /**
                 * *******************Creamos el model catalogo de producto *****************
                 */

                /**
                 * *******************Creamos a los productos existentes *****************
                 */
                if (Integer.parseInt(stock) > 0) {
                    control.fila = 0;
                    while (control.fila < Integer.parseInt(stock)) {
                        codbr = Codigobarr.replace("'", "\\'");
                        costo = preciocompra;
                        Garantia = "1";
                        ctp = 1;
                        idCatProd = idcatalogo;
                        per = 1;
                        control.Sql = "call IngresarProductos('','" + idCatProd
                                + "'," + "curdate()," + "adddate(curdate(),interval 1 month),'" + codbr + "','" + costo + "','"
                                + per + "','" + ctp + "','" + idProv + "'," + "'1',0,'" + coddoc + "','" + Controlador.sede + "','Disponible');";
                        control.CrearRegistro(control.Sql);
                        control.fila++;
                    }
                }
                /**
                 * *******************Creamos a los productos existentes *****************
                 */
                System.out.println((fila - 1) + ">>" + Codigobarr + ">>>>" + stock);
            }
            /**
             * *******************Recorremos las filas del Excel***************************
             */
            control.Sql = "update doc_compra set monto=" + montotal + " where idDoc_Compra='" + coddoc + "'";
            control.EditarRegistro(control.Sql);
            JOptionPane.showMessageDialog(null, "Se ha Ingresado Correctamente");
            txDocumento1.setVisible(false);
            jProgressBar1.setVisible(false);
            BuscarCatalogo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void leerinventario(String text) {
        String mdelos = "", unidad = "", marca = "", detalle = "", preciomayor = "", preciomenor = "",
                Codigobarr = "", preciocompra = "", stock = "", idmodelo = "", idmarca = "", idunidad = "",
                idcatalogo = "", prsuger = "";
        try {
            idProv = control.ObtenerDato("proveedor", "nompro", "PROVEEDOR GENERAL", 1);
            WorkbookSettings confi = new WorkbookSettings();
            confi.setEncoding("ISO-8859-1");
            Workbook archivoExcel = Workbook.getWorkbook(new File(text), confi);
            jProgressBar1.setVisible(true);
            double montotal = 0.00;
            //for (int sheetNo = 0; sheetNo < archivoExcel.getNumberOfSheets(); sheetNo++) {
            //int sheetNo = 0; 
            int sheetNo = 1;
            Sheet hoja = archivoExcel.getSheet(sheetNo);
            int numFilas = hoja.getRows();
            jProgressBar1.setMaximum(numFilas);
            jProgressBar1.setValue(0);
            jProgressBar1.setStringPainted(true);
            int flag = 0;

            /**
             * **********************CREAMOS LA COMPRA INICIAL***********************
             */
            control.Sql = "select InsertaCompra(curdate(),'1','00000','000001','Factura','" + info.getIdSede()
                    + "','20000','1','Es el inventario Inicial','Contado',curdate())";
            coddoc = control.DevolverRegistroDto(control.Sql, 1);
            //System.out.println(">>>>>>"+control.Sql);
            //JOptionPane.showMessageDialog(this,control.Sql);
            /**
             * **********************CREAMOS LA COMPRA INICIAL***********************
             */

            for (int fila = 3; fila < numFilas; fila++) {
                jProgressBar1.setValue(fila + 1);
//        if(flag==0){

                /*control.Sql=String.format("select InsertaCompra(%s,'%s','%s','%s','%s','%s'"
         + ",'0.00,'1','','Contado',curdate())","curdate()","2","0000","001-00001",
         "Compra Libre",info.getIdSede());
         coddoc=control.DevolverRegistroDto(control.Sql,1);
                 */
                //      flag++;
                //      }
                //**********CAPTURAR LOS DATOS A PARTIR DEL DOCUMENTO DE EXCEL
                mdelos = hoja.getCell(1, fila).getContents().toString();
                unidad = hoja.getCell(2, fila).getContents().toString();
                marca = hoja.getCell(3, fila).getContents().toString();
                detalle = hoja.getCell(4, fila).getContents() + " " + hoja.getCell(5, fila).getContents() + " "
                        + hoja.getCell(6, fila).getContents() + " " + hoja.getCell(7, fila).getContents() + " " + hoja.getCell(8, fila).getContents();
                preciomenor = hoja.getCell(9, fila).getContents().toString().replace(",", ".");
                preciomayor = hoja.getCell(10, fila).getContents().toString().replace(",", ".");
                Codigobarr = hoja.getCell(11, fila).getContents().toString();
                preciocompra = hoja.getCell(12, fila).getContents().toString().replace(",", ".") == "" ? "0.00" : hoja.getCell(12, fila).getContents().toString().replace(",", ".");
                //prsuger=hoja.getCell(13, fila).getContents().toString().replace(",", ".")==""?"0.00":hoja.getCell(13, fila).getContents().toString().replace(",", ".");

                //stock=hoja.getCell(13, fila).getContents().toString().replace(",", ".")== "" ? "0" : hoja.getCell(13, fila).getContents().toString().replace(",", ".");
                //stock=hoja.getCell(13, fila).getContents().toString()== "" ? "0" : hoja.getCell(13, fila).getContents().toString(); 
                //montotal=montotal+(Double.parseDouble(preciocompra)*Double.parseDouble(stock));
                //montotal=montotal+(Double.parseDouble(preciocompra)*Integer.parseInt(stock));
                //**********CAPTURAR LOS DATOS A PARTIR DEL DOCUMENTO DE EXCEL
                //***********CREA LOS MODELOS, UNIDADES Y MARCAS
                idmodelo = InsertayobtenerId("modelo", "nommod", mdelos.replace("'", "\\'"));
                idunidad = InsertayobtenerId("unidad", "nomuni", unidad.replace("'", "\\'"));
                idmarca = InsertayobtenerId("marca", "nommrc", marca.replace("'", "\\'"));

                //************CREA LOS CATALOGOS
                control.Sql = String.format("insert into catalogoproducto values(null,'%s','%s','%s','%s',"
                        + "'%s','%s','%s','%s',%s,'%s'); ", detalle.replace("'", "\\'"), prsuger,
                        idmarca, idmodelo, idunidad, 0, preciomenor, preciomayor, "curdate()", Codigobarr);
                //************CREA LOS MODELOCATALOGO

                idcatalogo = String.valueOf(control.executeAndGetId(control.Sql));
                control.Sql = "insert into modelocatalogo(codctlg,idModelos) values('" + idcatalogo
                        + "','" + idmodelo + "')";
                control.CrearRegistro(control.Sql);
                //Llenar los productos Según el Stock indicado
                if (Integer.parseInt(stock) > 0) {
                    control.fila = 0;
                    while (control.fila < Integer.parseInt(stock)) {
                        codbr = Codigobarr.replace("'", "\\'");
                        costo = preciocompra;
                        Garantia = "1";
                        ctp = 1;
                        idCatProd = idcatalogo;
                        per = 1;
                        control.Sql = "call IngresarProductos('','" + idCatProd
                                + "'," + "curdate()," + "adddate(curdate(),interval 1 month),'" + codbr + "','" + costo + "','"
                                + per + "','" + ctp + "','" + idProv + "'," + "'1',0,'" + coddoc + "','" + Controlador.sede + "','Disponible');";
                        control.CrearRegistro(control.Sql);
                        control.fila++;
                    }
                }
                //*********************************************************
            }
            control.Sql = "update doc_compra set monto=" + montotal + " where idDoc_Compra='" + coddoc + "'";
            control.EditarRegistro(control.Sql);
            //}
            JOptionPane.showMessageDialog(null, "Se ha Ingresado Correctamente");
            txDocumento1.setVisible(false);
            jProgressBar1.setVisible(false);
            BuscarCatalogo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean VerificarenTabla(String dato, JTable tb, int col) {
        boolean a = false;
        if (tb.getRowCount() > 0) {
            for (int i = 0; i < tb.getRowCount(); i++) {
                if (tb.getValueAt(i, col).toString().equals(dato)) {
                    a = true;
                    break;
                }
            }
        }
        return a;
    }

    public void CargarProductosInventarioini() {
        if (jButton2.getText().equals("cargar productos")) {
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Archivo Excel (.xls)", "xls"));
            int retVal = chooser.showOpenDialog(this);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                txDocumento1.setText(chooser.getSelectedFile().getAbsolutePath());
                leerinventario(txDocumento1.getText());
                jProgressBar1.setEnabled(true);
                txDocumento1.setVisible(true);
            }
            jButton2.setText("Procesar");
        } else {
            if (txDocumento1.getText().length() > 0) {
                SwingWorker worker = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        leerArchivoExcel(txDocumento1.getText());
                        return true;
                    }

                    @Override
                    protected void done() {
                        txDocumento1.setText("");
                        jButton2.setText("cargar productos");
                    }
                };
                worker.execute();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor primero seleccione un "
                        + "archivo para procesar", "Atención", JOptionPane.WARNING_MESSAGE);
                jButton2.setText("cargar productos");
            }
        }
    }

    public void EliminarProdporIngresar(KeyEvent e) {
        if (e.getKeyChar() == 127) {
            if (tProductosingresar.getSelectedRowCount() == 1) {
                if (tProductosingresar.getSelectedRow() >= contador) {
                    if (JOptionPane.showConfirmDialog(null, "Desea Eliminar este producto de la lista?",
                            "System Message", JOptionPane.YES_NO_OPTION) == 0) {
                        model.removeRow(tProductosingresar.getSelectedRow());
                        numerodeproductoingresados--;
                        llenarcontar();
                        sacartotalcompra();
                        ReacomodarItems();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No puede eliminar un ingreso ya guardado!!!", "System Message", 3);
                }
            }
        }
    }

    public void CalcularconIGV() {
        if (jCheckBox3.isSelected()) {
            double total = Double.parseDouble(lbTotalCompra.getText());
            lbTotalCompra.setText(forma.format((total * 1.18)).replace(",", "."));
        } else {
            sacartotalcompra();
        }
    }

    /**
     * ***********************Los Metodos**************************************
     */

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        txBuscarproductosingresar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tProductosingresar = new javax.swing.JTable();
        bAgregar = new javax.swing.JButton();
        bAgregar1 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        actcosto = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txBuscarproveedores = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tProveedores = new javax.swing.JTable();
        lbProveedor = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        bClienete = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txDocumento = new javax.swing.JTextField();
        fCompra = new com.toedter.calendar.JDateChooser();
        cbTipoComprobante = new javax.swing.JComboBox();
        cbTipoCompra = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtComentario = new javax.swing.JTextArea();
        lbFechaCancelacion = new javax.swing.JLabel();
        fDePago = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txBuscarproductos = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tProductos = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        bSalir = new javax.swing.JButton();
        bCrear = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        bLeerInventario = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        rbRemision = new javax.swing.JRadioButton();
        rbFactura = new javax.swing.JRadioButton();
        rb3 = new javax.swing.JRadioButton();
        rbBoleta = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cboSedes = new javax.swing.JComboBox();
        lbTotal = new javax.swing.JLabel();
        txDocumento1 = new javax.swing.JTextField();
        jProgressBar1 = new javax.swing.JProgressBar();
        jButton2 = new javax.swing.JButton();
        rbCompraLibre = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txCosto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txSerie = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jPanel8 = new javax.swing.JPanel();
        rbAños = new javax.swing.JRadioButton();
        rbMeses = new javax.swing.JRadioButton();
        rb1 = new javax.swing.JRadioButton();
        txGarantia = new javax.swing.JTextField();
        fechaven = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txcodigo = new javax.swing.JTextField();
        rbNo = new javax.swing.JRadioButton();
        rbSi = new javax.swing.JRadioButton();
        rb = new javax.swing.JRadioButton();
        jLabel12 = new javax.swing.JLabel();
        txCantidad = new javax.swing.JTextField();
        txCantidadDefectuosa = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lbTotalCompra = new javax.swing.JLabel();
        jCheckBox3 = new javax.swing.JCheckBox();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txBuscarproductosingresar.setName("txBuscarproductosingresar"); // NOI18N
        jPanel1.add(txBuscarproductosingresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 25, 240, -1));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tProductosingresar.setForeground(new java.awt.Color(0, 51, 102));
        tProductosingresar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tProductosingresar.setName("tProductosingresar"); // NOI18N
        tProductosingresar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tProductosingresarKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tProductosingresar);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 740, 350));

        bAgregar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bAgregar.setForeground(new java.awt.Color(0, 51, 102));
        bAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Add.png"))); // NOI18N
        bAgregar.setMnemonic('r');
        bAgregar.setText("Agregar");
        bAgregar.setName("bAgregar"); // NOI18N
        bAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAgregarActionPerformed(evt);
            }
        });
        jPanel1.add(bAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 19, 120, 30));

        bAgregar1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bAgregar1.setForeground(new java.awt.Color(0, 51, 102));
        bAgregar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        bAgregar1.setMnemonic('l');
        bAgregar1.setText("Eliminar");
        bAgregar1.setName("bAgregar1"); // NOI18N
        bAgregar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAgregar1ActionPerformed(evt);
            }
        });
        jPanel1.add(bAgregar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 19, 120, 30));

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 51, 153));
        jLabel14.setText("Buscar");
        jLabel14.setName("jLabel14"); // NOI18N
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 20));

        actcosto.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        actcosto.setText("Act.Costo");
        actcosto.setName("actcosto"); // NOI18N
        jPanel1.add(actcosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 19, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 50, 760, 410));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del producto", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setText("Buscar");
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 20));

        txBuscarproveedores.setName("txBuscarproveedores"); // NOI18N
        txBuscarproveedores.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txBuscarproveedoresKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txBuscarproveedoresKeyReleased(evt);
            }
        });
        jPanel2.add(txBuscarproveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 25, 310, -1));

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tProveedores.setForeground(new java.awt.Color(0, 51, 102));
        tProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tProveedores.setName("tProveedores"); // NOI18N
        tProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tProveedoresMouseClicked(evt);
            }
        });
        tProveedores.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tProveedoresKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tProveedores);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 570, 0));

        lbProveedor.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbProveedor.setForeground(new java.awt.Color(0, 51, 102));
        lbProveedor.setText("Proveedor");
        lbProveedor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbProveedor.setName("lbProveedor"); // NOI18N
        jPanel2.add(lbProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 430, 30));

        jLabel13.setText("jLabel13");
        jLabel13.setName("jLabel13"); // NOI18N
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, 170, -1));

        bClienete.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        bClienete.setForeground(new java.awt.Color(0, 51, 102));
        bClienete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Clien list.png"))); // NOI18N
        bClienete.setMnemonic('l');
        bClienete.setText("Proveedor");
        bClienete.setName("bClienete"); // NOI18N
        bClienete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bClieneteActionPerformed(evt);
            }
        });
        jPanel2.add(bClienete, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, 120, -1));

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 51, 102));
        jLabel18.setText("Datos complemantarios");
        jLabel18.setName("jLabel18"); // NOI18N
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 51, 102));
        jLabel19.setText("Tipo Comprobante");
        jLabel19.setName("jLabel19"); // NOI18N
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 51, 102));
        jLabel20.setText("N° Comprobante");
        jLabel20.setName("jLabel20"); // NOI18N
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, -1, -1));

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 51, 102));
        jLabel21.setText("Fecha Compra");
        jLabel21.setName("jLabel21"); // NOI18N
        jPanel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        txDocumento.setName("txDocumento"); // NOI18N
        jPanel2.add(txDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 100, 150, 20));

        fCompra.setDateFormatString("dd-MM-yyyy"); // NOI18N
        fCompra.setMinSelectableDate(new java.util.Date(-62135747926000L));
        fCompra.setName("fCompra"); // NOI18N
        fCompra.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                fCompraMouseDragged(evt);
            }
        });
        jPanel2.add(fCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 120, -1));

        cbTipoComprobante.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compra Libre", "Factura", "Boleta", "Guia de Remisión", "Ticket", "Ticket Factura" }));
        cbTipoComprobante.setName("cbTipoComprobante"); // NOI18N
        jPanel2.add(cbTipoComprobante, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 120, -1));

        cbTipoCompra.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Contado", "Credito" }));
        cbTipoCompra.setName("cbTipoCompra"); // NOI18N
        jPanel2.add(cbTipoCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 130, 150, -1));

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 51, 102));
        jLabel22.setText("Tipo Compra");
        jLabel22.setName("jLabel22"); // NOI18N
        jPanel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 130, -1, -1));

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        txtComentario.setColumns(20);
        txtComentario.setLineWrap(true);
        txtComentario.setRows(5);
        txtComentario.setName("txtComentario"); // NOI18N
        jScrollPane4.setViewportView(txtComentario);

        jPanel2.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 370, 50));

        lbFechaCancelacion.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbFechaCancelacion.setForeground(new java.awt.Color(0, 51, 102));
        lbFechaCancelacion.setText("Fecha de Pago");
        lbFechaCancelacion.setName("lbFechaCancelacion"); // NOI18N
        jPanel2.add(lbFechaCancelacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, 10));

        fDePago.setDateFormatString("dd-MM-yyyy"); // NOI18N
        fDePago.setMinSelectableDate(new java.util.Date(-62135747926000L));
        fDePago.setName("fDePago"); // NOI18N
        jPanel2.add(fDePago, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 120, 20));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 250));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del producto", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setText("Buscar");
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 20));

        txBuscarproductos.setName("txBuscarproductos"); // NOI18N
        txBuscarproductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txBuscarproductosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txBuscarproductosKeyReleased(evt);
            }
        });
        jPanel4.add(txBuscarproductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 25, 440, -1));

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        tProductos.setForeground(new java.awt.Color(0, 51, 102));
        tProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tProductos.setName("tProductos"); // NOI18N
        tProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tProductosMouseClicked(evt);
            }
        });
        tProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tProductosKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tProductos);

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 520, 340));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 540, 370));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(" "));
        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bSalir.setForeground(new java.awt.Color(0, 51, 102));
        bSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir.setMnemonic('s');
        bSalir.setText("Salir");
        bSalir.setName("bSalir"); // NOI18N
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });
        jPanel5.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, 110, 30));

        bCrear.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCrear.setForeground(new java.awt.Color(0, 51, 102));
        bCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        bCrear.setMnemonic('g');
        bCrear.setText("Ingresar");
        bCrear.setName("bCrear"); // NOI18N
        bCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCrearActionPerformed(evt);
            }
        });
        jPanel5.add(bCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 110, 30));

        bCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCancelar.setForeground(new java.awt.Color(0, 51, 102));
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setMnemonic('c');
        bCancelar.setText("Cancelar");
        bCancelar.setName("bCancelar"); // NOI18N
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });
        jPanel5.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 110, 30));

        bLeerInventario.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bLeerInventario.setForeground(new java.awt.Color(0, 51, 102));
        bLeerInventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Add to basket.png"))); // NOI18N
        bLeerInventario.setMnemonic('g');
        bLeerInventario.setText("Inicial");
        bLeerInventario.setName("bLeerInventario"); // NOI18N
        bLeerInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLeerInventarioActionPerformed(evt);
            }
        });
        jPanel5.add(bLeerInventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 110, 30));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 560, 670, 50));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Facturación", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel6.setName("jPanel6"); // NOI18N
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttonGroup2.add(rbRemision);
        rbRemision.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        rbRemision.setForeground(new java.awt.Color(0, 51, 102));
        rbRemision.setMnemonic('m');
        rbRemision.setText("G.Remision");
        rbRemision.setName("rbRemision"); // NOI18N
        rbRemision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbRemisionActionPerformed(evt);
            }
        });
        jPanel6.add(rbRemision, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 140, -1, -1));

        buttonGroup2.add(rbFactura);
        rbFactura.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        rbFactura.setForeground(new java.awt.Color(0, 51, 102));
        rbFactura.setMnemonic('f');
        rbFactura.setText("Factura");
        rbFactura.setName("rbFactura"); // NOI18N
        rbFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbFacturaActionPerformed(evt);
            }
        });
        jPanel6.add(rbFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, -1, -1));

        buttonGroup2.add(rb3);
        rb3.setForeground(new java.awt.Color(0, 51, 102));
        rb3.setText("r");
        rb3.setName("rb3"); // NOI18N
        jPanel6.add(rb3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, -1, -1));

        buttonGroup2.add(rbBoleta);
        rbBoleta.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        rbBoleta.setForeground(new java.awt.Color(0, 51, 102));
        rbBoleta.setMnemonic('b');
        rbBoleta.setText("Boleta");
        rbBoleta.setName("rbBoleta"); // NOI18N
        rbBoleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbBoletaActionPerformed(evt);
            }
        });
        jPanel6.add(rbBoleta, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, -1, -1));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 102));
        jLabel9.setText("Fecha");
        jLabel9.setName("jLabel9"); // NOI18N
        jPanel6.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, -1, -1));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 102));
        jLabel10.setText("Sede");
        jLabel10.setName("jLabel10"); // NOI18N
        jPanel6.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 170, 20));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 102));
        jLabel11.setText("Número");
        jLabel11.setName("jLabel11"); // NOI18N
        jPanel6.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, -1, -1));

        cboSedes.setName("cboSedes"); // NOI18N
        jPanel6.add(cboSedes, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 20, -1));

        lbTotal.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbTotal.setForeground(new java.awt.Color(0, 51, 153));
        lbTotal.setText("Buscar");
        lbTotal.setName("lbTotal"); // NOI18N
        jPanel6.add(lbTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 0, 20));

        txDocumento1.setName("txDocumento1"); // NOI18N
        jPanel6.add(txDocumento1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 280, 20));

        jProgressBar1.setName("jProgressBar1"); // NOI18N
        jPanel6.add(jProgressBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 280, -1));

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/report_go.png"))); // NOI18N
        jButton2.setText("cargar productos");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        buttonGroup2.add(rbCompraLibre);
        rbCompraLibre.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        rbCompraLibre.setForeground(new java.awt.Color(0, 51, 102));
        rbCompraLibre.setMnemonic('l');
        rbCompraLibre.setText("Compra Libre");
        rbCompraLibre.setName("rbCompraLibre"); // NOI18N
        rbCompraLibre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCompraLibreActionPerformed(evt);
            }
        });
        jPanel6.add(rbCompraLibre, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, -1, -1));

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 480, 680, 70));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del producto", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel7.setName("jPanel7"); // NOI18N
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Costo");
        jLabel1.setName("jLabel1"); // NOI18N
        jPanel7.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 390, -1));

        txCosto.setEditable(false);
        txCosto.setName("txCosto"); // NOI18N
        txCosto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txCostoFocusGained(evt);
            }
        });
        txCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txCostoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txCostoKeyTyped(evt);
            }
        });
        jPanel7.add(txCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 210, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("Garantia");
        jLabel6.setName("jLabel6"); // NOI18N
        jPanel7.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, -1, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 102));
        jLabel7.setText("Costo");
        jLabel7.setName("jLabel7"); // NOI18N
        jPanel7.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 20, -1, -1));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setText("Serie");
        jLabel8.setName("jLabel8"); // NOI18N
        jPanel7.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 60, -1, -1));

        txSerie.setEditable(false);
        txSerie.setName("txSerie"); // NOI18N
        txSerie.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txSerieFocusGained(evt);
            }
        });
        txSerie.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txSerieKeyReleased(evt);
            }
        });
        jPanel7.add(txSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 210, -1));

        jCheckBox1.setName("jCheckBox1"); // NOI18N
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jPanel7.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, -1, -1));

        jCheckBox2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jCheckBox2.setMnemonic('d');
        jCheckBox2.setText("Diferente");
        jCheckBox2.setName("jCheckBox2"); // NOI18N
        jCheckBox2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jCheckBox2KeyReleased(evt);
            }
        });
        jPanel7.add(jCheckBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, -1, -1));

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Caducidad", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(51, 0, 153))); // NOI18N
        jPanel8.setName("jPanel8"); // NOI18N
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttonGroup3.add(rbAños);
        rbAños.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        rbAños.setForeground(new java.awt.Color(0, 51, 102));
        rbAños.setMnemonic('o');
        rbAños.setText("Años");
        rbAños.setToolTipText("");
        rbAños.setName("rbAños"); // NOI18N
        rbAños.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbAñosActionPerformed(evt);
            }
        });
        jPanel8.add(rbAños, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 40, 20));

        buttonGroup3.add(rbMeses);
        rbMeses.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        rbMeses.setForeground(new java.awt.Color(0, 51, 102));
        rbMeses.setMnemonic('m');
        rbMeses.setSelected(true);
        rbMeses.setText("Meses");
        rbMeses.setName("rbMeses"); // NOI18N
        rbMeses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbMesesActionPerformed(evt);
            }
        });
        rbMeses.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                rbMesesKeyReleased(evt);
            }
        });
        jPanel8.add(rbMeses, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 40, 20));

        buttonGroup3.add(rb1);
        rb1.setForeground(new java.awt.Color(0, 51, 102));
        rb1.setText("rB1");
        rb1.setName("rb1"); // NOI18N
        jPanel8.add(rb1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 30, 20));

        txGarantia.setEditable(false);
        txGarantia.setText("0");
        txGarantia.setName("txGarantia"); // NOI18N
        txGarantia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txGarantiaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txGarantiaKeyTyped(evt);
            }
        });
        jPanel8.add(txGarantia, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 20, 20));

        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 130, 90));

        fechaven.setDateFormatString("dd-MM-yyyy");
        fechaven.setName("fechaven"); // NOI18N
        fechaven.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fechavenKeyReleased(evt);
            }
        });
        jPanel7.add(fechaven, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 210, -1));

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 51, 102));
        jLabel15.setText("Caducidad");
        jLabel15.setName("jLabel15"); // NOI18N
        jPanel7.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, 410, 0));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Codificación", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txcodigo.setEditable(false);
        txcodigo.setName("txcodigo"); // NOI18N
        txcodigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txcodigoMouseEntered(evt);
            }
        });
        txcodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txcodigoActionPerformed(evt);
            }
        });
        txcodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txcodigoFocusGained(evt);
            }
        });
        txcodigo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txcodigoPropertyChange(evt);
            }
        });
        jPanel3.add(txcodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 130, -1));

        buttonGroup1.add(rbNo);
        rbNo.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        rbNo.setForeground(new java.awt.Color(0, 51, 102));
        rbNo.setMnemonic('n');
        rbNo.setText("No");
        rbNo.setName("rbNo"); // NOI18N
        rbNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNoActionPerformed(evt);
            }
        });
        jPanel3.add(rbNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, -1, -1));

        buttonGroup1.add(rbSi);
        rbSi.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        rbSi.setForeground(new java.awt.Color(0, 51, 102));
        rbSi.setMnemonic('i');
        rbSi.setText("Si");
        rbSi.setToolTipText("");
        rbSi.setName("rbSi"); // NOI18N
        rbSi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbSiActionPerformed(evt);
            }
        });
        rbSi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                rbSiKeyReleased(evt);
            }
        });
        jPanel3.add(rbSi, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        buttonGroup1.add(rb);
        rb.setForeground(new java.awt.Color(0, 51, 102));
        rb.setText("p");
        rb.setName("rb"); // NOI18N
        jPanel3.add(rb, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 40, -1));

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel12.setText("Cantidad defectuoa");
        jLabel12.setName("jLabel12"); // NOI18N
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 100, 120, -1));

        txCantidad.setEditable(false);
        txCantidad.setName("txCantidad"); // NOI18N
        txCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txCantidadKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txCantidadKeyTyped(evt);
            }
        });
        jPanel3.add(txCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 70, -1));

        txCantidadDefectuosa.setText("0");
        txCantidadDefectuosa.setName("txCantidadDefectuosa"); // NOI18N
        txCantidadDefectuosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txCantidadDefectuosaActionPerformed(evt);
            }
        });
        txCantidadDefectuosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txCantidadDefectuosaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txCantidadDefectuosaKeyTyped(evt);
            }
        });
        jPanel3.add(txCantidadDefectuosa, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 70, 20));

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel16.setText("Cantidad");
        jLabel16.setName("jLabel16"); // NOI18N
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, -1, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 0, 260, 0));

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 51, 102));
        jLabel17.setText("S/. ");
        jLabel17.setName("jLabel17"); // NOI18N
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 10, 40, 40));

        lbTotalCompra.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lbTotalCompra.setForeground(new java.awt.Color(0, 51, 102));
        lbTotalCompra.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbTotalCompra.setText(" ");
        lbTotalCompra.setName("lbTotalCompra"); // NOI18N
        getContentPane().add(lbTotalCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 10, 210, 40));

        jCheckBox3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jCheckBox3.setForeground(new java.awt.Color(0, 51, 102));
        jCheckBox3.setText("No Incluye IGV");
        jCheckBox3.setName("jCheckBox3"); // NOI18N
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });
        getContentPane().add(jCheckBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents
/**
     * ***********************Los Eventos*****************************************
     */
private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
    dispose();
}//GEN-LAST:event_bSalirActionPerformed
    private void bCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCrearActionPerformed
        IngresarProductos();
}//GEN-LAST:event_bCrearActionPerformed
    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
        Cancelar();
}//GEN-LAST:event_bCancelarActionPerformed
    private void txBuscarproveedoresKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txBuscarproveedoresKeyReleased
        BuscarProveedores();
        if (tProveedores.getSelectedRow() == -1) {
            lbProveedor.setText(null);
        }
}//GEN-LAST:event_txBuscarproveedoresKeyReleased
    private void tProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProveedoresMouseClicked
        VerProveedor();
}//GEN-LAST:event_tProveedoresMouseClicked
    private void tProveedoresKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tProveedoresKeyReleased
        VerProveedor();
}//GEN-LAST:event_tProveedoresKeyReleased
    private void txBuscarproductosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txBuscarproductosKeyReleased
        BuscarCatalogo();
        if (tProductos.getSelectedRow() == -1) {
            rb.setSelected(true);
            jLabel1.setText(null);
        }
}//GEN-LAST:event_txBuscarproductosKeyReleased
    private void tProductosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tProductosKeyReleased
        // VerCatalogo();
}//GEN-LAST:event_tProductosKeyReleased
    private void tProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProductosMouseClicked
        if (evt.getClickCount() == 2) {
            CargarProducto();
        }
}//GEN-LAST:event_tProductosMouseClicked
    private void rbSiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbSiActionPerformed
        if (txCantidad.getText().trim().length() > 0) {
            SeleccionarSi();
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese la Cantidad de Productos", "System Message", 3);
            rb.setSelected(true);
            txCantidad.grabFocus();
        }
}//GEN-LAST:event_rbSiActionPerformed
    private void rbNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNoActionPerformed
        if (txCantidad.getText().trim().length() > 0) {
            cuantosclick++;
            SeleccionarNo();
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese la Cantidad de Productos", "System Message", 3);
            rb.setSelected(true);
            txCantidad.grabFocus();
        }
}//GEN-LAST:event_rbNoActionPerformed
    private void txCostoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txCostoKeyTyped
        control.decimal(evt, txCosto.getText());
}//GEN-LAST:event_txCostoKeyTyped
    private void bAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAgregarActionPerformed
        Agregar();
}//GEN-LAST:event_bAgregarActionPerformed
    private void txcodigoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txcodigoMouseEntered
    }//GEN-LAST:event_txcodigoMouseEntered
    private void txcodigoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txcodigoPropertyChange
    }//GEN-LAST:event_txcodigoPropertyChange
    private void txcodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txcodigoActionPerformed
        CapturarCodigo();
}//GEN-LAST:event_txcodigoActionPerformed
    private void txcodigoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txcodigoFocusGained
        if (!rbSi.isSelected()) {
            txGarantia.requestFocus();
        }
}//GEN-LAST:event_txcodigoFocusGained
    private void rbMesesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbMesesActionPerformed
        txGarantia.requestFocus();
}//GEN-LAST:event_rbMesesActionPerformed
    private void rbAñosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbAñosActionPerformed
        txGarantia.requestFocus();
}//GEN-LAST:event_rbAñosActionPerformed
    private void fCompraMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fCompraMouseDragged
        JOptionPane.showMessageDialog(rootPane, "los Dias");
}//GEN-LAST:event_fCompraMouseDragged
    private void rbBoletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbBoletaActionPerformed
        tpodoc = "Boleta";
        txDocumento.requestFocus();
}//GEN-LAST:event_rbBoletaActionPerformed
    private void rbFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbFacturaActionPerformed
        tpodoc = "Factura";
        txDocumento.requestFocus();
}//GEN-LAST:event_rbFacturaActionPerformed
    private void rbRemisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbRemisionActionPerformed
        tpodoc = "G. Remision";
        txDocumento.requestFocus();
}//GEN-LAST:event_rbRemisionActionPerformed
    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if (txSerie.getText().trim().length() == 0) {
            jCheckBox1.setSelected(false);
            txSerie.requestFocus();
        } else {
            txGarantia.requestFocus();
        }
}//GEN-LAST:event_jCheckBox1ActionPerformed
    private void txGarantiaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txGarantiaKeyTyped
        EscribirGarantia(evt);
}//GEN-LAST:event_txGarantiaKeyTyped
    private void txGarantiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txGarantiaKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jCheckBox2.requestFocus();
        }
}//GEN-LAST:event_txGarantiaKeyReleased
    private void txCostoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txCostoFocusGained
}//GEN-LAST:event_txCostoFocusGained
    private void txSerieFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txSerieFocusGained
        if (txCosto.getText().trim().length() == 0) {
            txCosto.grabFocus();
        }
}//GEN-LAST:event_txSerieFocusGained
    private void txCostoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txCostoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txSerie.requestFocus();
        }
    }//GEN-LAST:event_txCostoKeyReleased
    private void bAgregar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAgregar1ActionPerformed
        if (tProductosingresar.getSelectedRowCount() == 1) {
            control.fila = tProductosingresar.getSelectedRow();
            if (info.getCompraeditar() > 0) {
                if (control.fila + 1 > contador) {
                    model.removeRow(tProductosingresar.getSelectedRow());
                } else {
                    JOptionPane.showMessageDialog(null, "No Puede eliminar un Producto "
                            + "Ingresado a Almacen");
                }
            } else {
                model.removeRow(tProductosingresar.getSelectedRow());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione un Producto para Eliminar!!!");
        }
    }//GEN-LAST:event_bAgregar1ActionPerformed
    private void txCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txCantidadKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txCantidadKeyTyped
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        CargarProductosInventarioini();
    }//GEN-LAST:event_jButton2ActionPerformed
    private void txSerieKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txSerieKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            fechaven.requestFocus();
        }
    }//GEN-LAST:event_txSerieKeyReleased
    private void fechavenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechavenKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jCheckBox2.requestFocus();
        }
    }//GEN-LAST:event_fechavenKeyReleased
    private void jCheckBox2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox2KeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txCantidad.requestFocus();
        }
    }//GEN-LAST:event_jCheckBox2KeyReleased
    private void txCantidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txCantidadKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            rbSi.requestFocus();
        }
    }//GEN-LAST:event_txCantidadKeyReleased
    private void rbMesesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbMesesKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txGarantia.requestFocus();
        }
    }//GEN-LAST:event_rbMesesKeyReleased
    private void rbSiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbSiKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txcodigo.requestFocus();
        }
    }//GEN-LAST:event_rbSiKeyReleased
    private void rbCompraLibreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCompraLibreActionPerformed
        tpodoc = "Compra Libre";
        txDocumento.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_rbCompraLibreActionPerformed
    private void txCantidadDefectuosaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txCantidadDefectuosaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txCantidadDefectuosaKeyReleased
    private void txCantidadDefectuosaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txCantidadDefectuosaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txCantidadDefectuosaKeyTyped
    private void txCantidadDefectuosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txCantidadDefectuosaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txCantidadDefectuosaActionPerformed
    private void bLeerInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLeerInventarioActionPerformed
        LeerexcelInventario();
    }//GEN-LAST:event_bLeerInventarioActionPerformed
    private void bClieneteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bClieneteActionPerformed
        Proveedores cli = new Proveedores();
        Menu.jDesktopPane1.add(cli);
        Proveedores.txProveedor.grabFocus();
        info.setControl(1);
        cli.toFront();
        cli.setVisible(true);
    }//GEN-LAST:event_bClieneteActionPerformed
    private void txBuscarproveedoresKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txBuscarproveedoresKeyPressed
        if (evt.getKeyChar() == 10) {
            if (tProveedores.getRowCount() == 1) {
                capturauno();
            } else if (tProveedores.getRowCount() == 0) {
                bClienete.doClick();
            }
        }
    }//GEN-LAST:event_txBuscarproveedoresKeyPressed
    private void txBuscarproductosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txBuscarproductosKeyPressed
        if (evt.getKeyChar() == 10) {
            if (tProductos.getRowCount() == 1) {
                tProductos.selectAll();
                CargarProducto();
            }
        }
    }//GEN-LAST:event_txBuscarproductosKeyPressed
    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        CalcularconIGV();
    }//GEN-LAST:event_jCheckBox3ActionPerformed
    private void tProductosingresarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tProductosingresarKeyPressed
        EliminarProdporIngresar(evt);
    }//GEN-LAST:event_tProductosingresarKeyPressed

    /**
     * ***********************Los Eventos**************************************
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox actcosto;
    private javax.swing.JButton bAgregar;
    private javax.swing.JButton bAgregar1;
    private javax.swing.JButton bCancelar;
    public static javax.swing.JButton bClienete;
    private javax.swing.JButton bCrear;
    private javax.swing.JButton bLeerInventario;
    private javax.swing.JButton bSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox cbTipoCompra;
    private javax.swing.JComboBox cbTipoComprobante;
    private javax.swing.JComboBox cboSedes;
    public static com.toedter.calendar.JDateChooser fCompra;
    public static com.toedter.calendar.JDateChooser fDePago;
    private com.toedter.calendar.JDateChooser fechaven;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lbFechaCancelacion;
    public static javax.swing.JLabel lbProveedor;
    private javax.swing.JLabel lbTotal;
    private javax.swing.JLabel lbTotalCompra;
    private javax.swing.JRadioButton rb;
    private javax.swing.JRadioButton rb1;
    public static javax.swing.JRadioButton rb3;
    private javax.swing.JRadioButton rbAños;
    public static javax.swing.JRadioButton rbBoleta;
    public static javax.swing.JRadioButton rbCompraLibre;
    public static javax.swing.JRadioButton rbFactura;
    private javax.swing.JRadioButton rbMeses;
    private javax.swing.JRadioButton rbNo;
    public static javax.swing.JRadioButton rbRemision;
    private javax.swing.JRadioButton rbSi;
    private javax.swing.JTable tProductos;
    public static javax.swing.JTable tProductosingresar;
    private javax.swing.JTable tProveedores;
    private javax.swing.JTextField txBuscarproductos;
    private javax.swing.JTextField txBuscarproductosingresar;
    public static javax.swing.JTextField txBuscarproveedores;
    private javax.swing.JTextField txCantidad;
    private javax.swing.JTextField txCantidadDefectuosa;
    private javax.swing.JTextField txCosto;
    public static javax.swing.JTextField txDocumento;
    public static javax.swing.JTextField txDocumento1;
    private javax.swing.JTextField txGarantia;
    private javax.swing.JTextField txSerie;
    private javax.swing.JTextField txcodigo;
    private javax.swing.JTextArea txtComentario;
    // End of variables declaration//GEN-END:variables
}
