/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

/**
 * ******************** @author J&M SYSTEM SOFT  *******************
 */
import Clases.*;
import com.toedter.calendar.JDateChooser;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Field;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Movimientodecaja extends javax.swing.JInternalFrame {

    //****************************LOS ATRIBUTOS*********************************
    Controlador control = new Controlador();
    Mimodelo modelo = new Mimodelo();
    String sed = "", tipo = "", usuario = "", Estado = "", idmovcja = "";
    double montoTotal = 0.0;
    IMPRIMIR prin = new IMPRIMIR();
    //***************************FIN LOS ATRIBUTOS******************************

    //****************************LOS METODOS***********************************
    public Movimientodecaja() {
        initComponents();
        setTitle("Movimientos de caja");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        modelo.setColumnIdentifiers(new String[]{"ID", "Concepto", "Fecha", "Hora", "Monto",
            "Comprobante", "N° Comprobante", "Ingreso/Egreso"});
        tMovimientosdeCaja.setModel(modelo);
        tMovimientosdeCaja.getColumnModel().getColumn(1).setPreferredWidth(350);
        tMovimientosdeCaja.getColumnModel().getColumn(0).setMaxWidth(0);
        tMovimientosdeCaja.getColumnModel().getColumn(0).setMinWidth(0);
        tMovimientosdeCaja.getColumnModel().getColumn(0).setPreferredWidth(0);
        PonerFechaActual();
        MarcarEstadoCaja();
        ActivarSerie();
        //****************************CAPTURAR USUARIO***********************************
        control.Sql = "select * from usuario where nomusr='" + InfoGeneral.usuario + "'";
        usuario = control.DevolverRegistroDto(control.Sql, 1);
        //****************************CAPTURAR USUARIO***********************************

        //****************************CONTROLAR EL CAMBIO DE FECHA***********************************
        f_Desde.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    JDateChooser isse = (JDateChooser) evt.getSource();
                    boolean isdatasele = false;
                    try {
                        Field dateselfi = JDateChooser.class.getDeclaredField("dateSelect");
                        dateselfi.setAccessible(true);
                        isdatasele = dateselfi.getBoolean(isse);
                    } catch (Exception e) {
                        MostrarMovimientoCja();
                    }
                }
            }
        });
        F_Hasta.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    JDateChooser isse = (JDateChooser) evt.getSource();
                    boolean isdatasele = false;
                    try {
                        Field dateselfi = JDateChooser.class.getDeclaredField("dateSelect");
                        dateselfi.setAccessible(true);
                        isdatasele = dateselfi.getBoolean(isse);
                    } catch (Exception e) {
                        MostrarMovimientoCja();
                    }
                }
            }
        });
        //****************************CONTROLAR EL CAMBIO DE FECHA***********************************     
        jLabel9.setText(null);
        MostrarMovimientoCja();
    }

    public void PonerFechaActual() {
        f_Desde.setDate(new Date());
        F_Hasta.setDate(new Date());
    }

    public void MarcarEstadoCaja() {
        if (Estado_Caja()) {
            rbAbrir.setSelected(true);
        } else {
            rbCerrar.setSelected(true);
        }
    }

    public void ActivarSerie() {
        if (cboComprobante.getSelectedItem().equals("Libre")) {
            txtSerieNumero.setEditable(false);
            txtMonto.grabFocus();
        } else {
            txtSerieNumero.setEditable(true);
            txtSerieNumero.grabFocus();
        }
    }

    public boolean Estado_Caja() {
        boolean bdr = true;
        int ct = -1;
        //********DETERMINAR LA CANTIDAD DE CIERRES Y APERTURA***************************************
        control.Sql = "select count(*) from cierreapertura where idSede='" + InfoGeneral.getIdSede() + "'";
        ct = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
        //********DETERMINAR LA CANTIDAD DE CIERRES Y APERTURA***************************************
        if (ct == 0) {
            bdr = false;
        } else {
            //********DETERMINAR LA CANTIDAD DE CIERRES Y APERTURA*****************************
            control.Sql = "select estado from cierreapertura where idSede='" + InfoGeneral.getIdSede() + "'"
                    + " order by idCierreApertura desc limit 1";
            tipo = control.DevolverRegistroDto(control.Sql, 1);
            //********DETERMINAR LA SITUACION DE LA CAJA*******************************
            if (tipo.equals("Abierta")) {
                bdr = true;
            }
            if (tipo.equals("Cerrada")) {
                bdr = false;
            }
        }
        return bdr;
    }

    public void GrabarCierreAperturaCaja() {
        control.bandera = false;
        if (rbAbrir.isSelected()) {
            Estado = "Abierta";
            sed = "Abrir la Caja";
        }
        if (rbCerrar.isSelected()) {
            Estado = "Cerrada";
            sed = "Cerrar la Caja";
        }
        if (JOptionPane.showConfirmDialog(null, "Estas seguro que Quieres " + sed, "Confirmar", 0) == 0) {
            if (Estado_Caja()) {
                if (rbAbrir.isSelected()) {
                    control.bandera = false;
                    sed = "La Caja ya esta Abierta no se puede volver a Abrir";
                } else {
                    control.bandera = true;
                }
            } else {
                if (rbCerrar.isSelected()) {
                    control.bandera = false;
                    sed = "La Caja ya esta Cerrada No se puede volver a Cerrar";
                } else {
                    control.bandera = true;
                }
            }
            if (control.bandera) {
                CalcularMontoCajaParaGrabar();
                control.Sql = "insert into cierreapertura(fec,hra,monto,estado,idusuario,idSede)"
                        + " values('" + control.Fecha() + "','" + control.Hora() + "','" + montoTotal
                        + "','" + Estado + "','" + usuario + "','" + InfoGeneral.getIdSede() + "')";
                control.CrearRegistro(control.Sql);
            } else {
                JOptionPane.showMessageDialog(null, sed);
            }
        } else {
            return;
        }
        MarcarEstadoCaja();
    }

    public void Cancelar() {
        txtConcepto.setText(null);
        txtMonto.setText(null);
        txtSerieNumero.setText(null);
        txtBuscar.setText(null);
        cboComprobante.setSelectedIndex(0);
        cboTipo.setSelectedIndex(0);
        MarcarEstadoCaja();
        txtConcepto.grabFocus();
    }

    public void GrabarMovimientoCaja() {
        if (txtMonto.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Faltan Datos");
            txtMonto.grabFocus();
        } else {
            control.Sql = "insert into movimientocaja(concepto,fch,hra,monto,tipo,comprobante,"
                    + "numcomprobante,idUsuario,idSede)values('" + txtConcepto.getText() + "','"
                    + control.Fecha() + "','" + control.Hora() + "','" + txtMonto.getText() + "','"
                    + cboTipo.getSelectedItem().toString() + "','" + cboComprobante.getSelectedItem().toString()
                    + "','" + txtSerieNumero.getText() + "','" + usuario + "','" + InfoGeneral.getIdSede() + "')";
            control.CrearRegistro(control.Sql);
            Cancelar();
            MostrarMovimientoCja();
        }
    }

    public void MostrarMovimientoCja() {
        control.Sql = "select idMovimientoCaja,concepto,fch,hra,monto,comprobante,numcomprobante,tipo "
                + "from movimientocaja where (idSede='" + InfoGeneral.getIdSede() + "' and fch between '"
                + control.Formato_Amd(f_Desde.getDate()) + "' and '" + control.Formato_Amd(F_Hasta.getDate())
                + "') and (concepto like'%" + txtBuscar.getText() + "%' or comprobante like'%"
                + txtBuscar.getName() + "%' or numcomprobante like'%" + txtBuscar.getText() + "%' or "
                + "tipo like'%" + txtBuscar.getText() + "%')";
        control.LlenarJTabla(modelo, control.Sql, 8);
        jLabel9.setText(control.CalcularMontodeJtable(modelo, 4));
        CalcularMontodeCaja();
    }

    public void CargarDatosMovimiento() {
        control.fila = tMovimientosdeCaja.getSelectedRow();
        if (control.fila > -1) {
            idmovcja = tMovimientosdeCaja.getValueAt(control.fila, 0).toString();
            txtConcepto.setText(tMovimientosdeCaja.getValueAt(control.fila, 1).toString());
            txtMonto.setText(tMovimientosdeCaja.getValueAt(control.fila, 4).toString());
            txtSerieNumero.setText(tMovimientosdeCaja.getValueAt(control.fila, 6).toString());
            control.MostrarEnCombo(tMovimientosdeCaja.getValueAt(control.fila, 5).toString(), cboComprobante);
            control.MostrarEnCombo(tMovimientosdeCaja.getValueAt(control.fila, 7).toString(), cboTipo);
            tMovimientosdeCaja.grabFocus();
        }
    }

    public void CalcularMontodeCaja() {
        double mt = 0.0;
        for (control.fila = 0; control.fila < modelo.getRowCount(); control.fila++) {
            if (modelo.getValueAt(control.fila, 7).toString().equalsIgnoreCase("Ingreso")) {
                mt = mt + Double.parseDouble(modelo.getValueAt(control.fila, 4).toString());
            } else {
                mt = mt - Double.parseDouble(modelo.getValueAt(control.fila, 4).toString());
            }
        }
        jLabel9.setText(control.decimalFormat(mt));
    }

    public void CalcularMontoCajaParaGrabar() {
        control.Sql = "select CalcularMontoCaja()";
        montoTotal = Double.parseDouble(control.DevolverRegistroDto(control.Sql, 1));
    }

    public void EliminarMovimientoCja() {
        if (tMovimientosdeCaja.getSelectedRow() >= 0) {
            control.fila = tMovimientosdeCaja.getSelectedRow();
            if (JOptionPane.showConfirmDialog(null, "Estas Seguro de Eliminar", "Confirmar", 0) == 0) {
                control.Sql = "delete from movimientocaja ";
            }
        }
    }

    private void GrabarEdicioncaja() {
        if (txtMonto.getText().trim().length() == 0 || txtConcepto.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Faltan Datos");
            txtMonto.grabFocus();
        } else {
            control.ejecutar(String.format("UPDATE movimientocaja m SET m.`concepto`='%s', m.`monto`='%s', m.`tipo`='%s', m.`comprobante`='%s', m.`numcomprobante`='%s' WHERE m.`idMovimientoCaja`='%s';",
                    txtConcepto.getText(), txtMonto.getText(), cboTipo.getSelectedItem().toString(), cboComprobante.getSelectedItem().toString(), txtSerieNumero.getText(), idmovcja));
            Cancelar();
            MostrarMovimientoCja();
        }
    }

    private void EliminarMovimientoCaja() {
        if (JOptionPane.showConfirmDialog(null, "Desea eliminar el movimiento de caja?", "System Message", JOptionPane.YES_NO_OPTION) == 0) {
            control.ejecutar(String.format("DELETE FROM movimientocaja WHERE `idMovimientoCaja`=%s;",
                    tMovimientosdeCaja.getValueAt(tMovimientosdeCaja.getSelectedRow(), 0).toString()));
            Cancelar();
            MostrarMovimientoCja();
        }
    }

    private void ImprimirMovimientos() {
        Map map = new HashMap();
        map.put("fecIn", control.Formato_Amd(f_Desde.getDate()));
        map.put("fecFi", control.Formato_Amd(F_Hasta.getDate()));
        map.put("codv", jLabel9.getText());
        prin.imprimir("movimientocaja", "Movimientos de caja", map);
    }
//***************************FIN LOS METODOS************************************

    //***************************LOS EVENTOS**************************************** 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tMovimientosdeCaja = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        f_Desde = new com.toedter.calendar.JDateChooser();
        F_Hasta = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cboComprobante = new javax.swing.JComboBox();
        txtConcepto = new javax.swing.JTextField();
        txtSerieNumero = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cboTipo = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        bSalir = new javax.swing.JButton();
        bGrabar = new javax.swing.JButton();
        bEliminar = new javax.swing.JButton();
        bImprimir = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        bModificar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        rbCerrar = new javax.swing.JRadioButton();
        rbAbrir = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Movimientos de Caja", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel1MouseMoved(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tMovimientosdeCaja.setModel(new javax.swing.table.DefaultTableModel(
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
        tMovimientosdeCaja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tMovimientosdeCajaMouseClicked(evt);
            }
        });
        tMovimientosdeCaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tMovimientosdeCajaKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tMovimientosdeCaja);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 1050, 310));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setText("Buscar");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 30, -1, -1));

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 25, 280, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setText("Hasta");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 30, -1, -1));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setText("Desde");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 30, -1, -1));

        f_Desde.setDateFormatString("dd-MM-yyyy");
        jPanel1.add(f_Desde, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 25, 130, -1));

        F_Hasta.setDateFormatString("dd-MM-yyyy");
        jPanel1.add(F_Hasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 25, 120, -1));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setText("Monto Total");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 370, 80, -1));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setText("Liquido en Caja");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 370, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 1070, 400));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Moviento de Caja", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setText("Comprobante");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 115, -1, -1));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setText("Concepto");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setText("Monto");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        cboComprobante.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Libre", "Boleta", "Boucher", "Factura", "Recibo", "Ticket", " " }));
        cboComprobante.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboComprobanteItemStateChanged(evt);
            }
        });
        cboComprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cboComprobanteKeyPressed(evt);
            }
        });
        jPanel2.add(cboComprobante, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 220, -1));

        txtConcepto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtConceptoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtConceptoKeyReleased(evt);
            }
        });
        jPanel2.add(txtConcepto, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 940, -1));
        jPanel2.add(txtSerieNumero, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 110, 140, -1));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setText("Serie_Numero");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 110, -1, -1));

        cboTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ingreso", "Egreso" }));
        cboTipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cboTipoKeyPressed(evt);
            }
        });
        jPanel2.add(cboTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 70, 140, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setText("Tipo");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 70, -1, -1));

        txtMonto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMontoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoKeyTyped(evt);
            }
        });
        jPanel2.add(txtMonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 220, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1070, 150));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bSalir.setForeground(new java.awt.Color(0, 0, 102));
        bSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir.setMnemonic('s');
        bSalir.setText("Salir");
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });
        jPanel3.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 305, 110, 40));

        bGrabar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bGrabar.setForeground(new java.awt.Color(0, 0, 102));
        bGrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/save.png"))); // NOI18N
        bGrabar.setMnemonic('g');
        bGrabar.setText("Grabar");
        bGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGrabarActionPerformed(evt);
            }
        });
        jPanel3.add(bGrabar, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 15, 110, 40));

        bEliminar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bEliminar.setForeground(new java.awt.Color(0, 0, 102));
        bEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        bEliminar.setMnemonic('m');
        bEliminar.setText("Eliminar");
        bEliminar.setMargin(new java.awt.Insets(2, 1, 2, 14));
        bEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEliminarActionPerformed(evt);
            }
        });
        jPanel3.add(bEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 130, 110, 40));

        bImprimir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bImprimir.setForeground(new java.awt.Color(0, 0, 102));
        bImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Print.png"))); // NOI18N
        bImprimir.setMnemonic('i');
        bImprimir.setText("Imprimir");
        bImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bImprimirActionPerformed(evt);
            }
        });
        jPanel3.add(bImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 245, 110, 40));

        bCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCancelar.setForeground(new java.awt.Color(0, 0, 102));
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setMnemonic('a');
        bCancelar.setText("Cancelar");
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });
        jPanel3.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 185, 110, 40));

        bModificar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bModificar.setForeground(new java.awt.Color(0, 0, 102));
        bModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Edit.png"))); // NOI18N
        bModificar.setMnemonic('m');
        bModificar.setText("Modificar");
        bModificar.setMargin(new java.awt.Insets(2, 1, 2, 14));
        bModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bModificarActionPerformed(evt);
            }
        });
        jPanel3.add(bModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 75, 110, 40));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 170, 140, 360));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttonGroup1.add(rbCerrar);
        rbCerrar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        rbCerrar.setForeground(new java.awt.Color(0, 0, 102));
        rbCerrar.setMnemonic('c');
        rbCerrar.setText("Cerrada");
        jPanel4.add(rbCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 55, -1, -1));

        buttonGroup1.add(rbAbrir);
        rbAbrir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        rbAbrir.setForeground(new java.awt.Color(0, 0, 102));
        rbAbrir.setMnemonic('b');
        rbAbrir.setText("Abierta");
        jPanel4.add(rbAbrir, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 15, -1, -1));

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 102));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        jButton1.setMnemonic('e');
        jButton1.setText("Ejecutar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 90, 110, 40));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 0, 140, 150));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
        dispose();   // TODO add your handling code here:
    }//GEN-LAST:event_bSalirActionPerformed
    private void bImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bImprimirActionPerformed
        ImprimirMovimientos();
    }//GEN-LAST:event_bImprimirActionPerformed
    private void cboComprobanteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboComprobanteItemStateChanged
        ActivarSerie();   // TODO add your handling code here:
    }//GEN-LAST:event_cboComprobanteItemStateChanged
    private void txtConceptoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConceptoKeyReleased
        control.Mayusculas(txtConcepto);
    }//GEN-LAST:event_txtConceptoKeyReleased
    private void bEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEliminarActionPerformed
        if (tMovimientosdeCaja.getSelectedRowCount() == 1) {
            EliminarMovimientoCaja();
        } else {
            JOptionPane.showMessageDialog(null, "Selecione un registro para eliminar!!", "System Message", 2);
        }
    }//GEN-LAST:event_bEliminarActionPerformed
    private void txtMontoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoKeyTyped
        control.decimal(evt, txtMonto.getText());
    }//GEN-LAST:event_txtMontoKeyTyped
    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
        Cancelar();
    }//GEN-LAST:event_bCancelarActionPerformed
    private void bGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGrabarActionPerformed
        GrabarMovimientoCaja();
    }//GEN-LAST:event_bGrabarActionPerformed
    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        MostrarMovimientoCja();
    }//GEN-LAST:event_txtBuscarKeyReleased
    private void tMovimientosdeCajaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tMovimientosdeCajaMouseClicked
        if (evt.getClickCount() == 2) {
            CargarDatosMovimiento();
        }
    }//GEN-LAST:event_tMovimientosdeCajaMouseClicked
    private void tMovimientosdeCajaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tMovimientosdeCajaKeyReleased
        CargarDatosMovimiento();
    }//GEN-LAST:event_tMovimientosdeCajaKeyReleased
    private void bModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModificarActionPerformed
        GrabarEdicioncaja();
    }//GEN-LAST:event_bModificarActionPerformed
    private void jPanel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseMoved
        CalcularMontodeCaja();
    }//GEN-LAST:event_jPanel1MouseMoved
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed
    private void txtConceptoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConceptoKeyPressed
        if (evt.getKeyChar() == 10) {
            txtMonto.grabFocus();
        }
    }//GEN-LAST:event_txtConceptoKeyPressed
    private void txtMontoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoKeyPressed
        if (evt.getKeyChar() == 10) {
            cboTipo.grabFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoKeyPressed
    private void cboTipoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboTipoKeyPressed
        if (evt.getKeyChar() == 10) {
            cboComprobante.grabFocus();
        }
    }//GEN-LAST:event_cboTipoKeyPressed
    private void cboComprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboComprobanteKeyPressed
        if (evt.getKeyChar() == 10) {
            txtSerieNumero.grabFocus();
        }
    }//GEN-LAST:event_cboComprobanteKeyPressed
    //***************************FIN LOS EVENTOS**************************************** 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser F_Hasta;
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bEliminar;
    private javax.swing.JButton bGrabar;
    private javax.swing.JButton bImprimir;
    private javax.swing.JButton bModificar;
    private javax.swing.JButton bSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboComprobante;
    private javax.swing.JComboBox cboTipo;
    private com.toedter.calendar.JDateChooser f_Desde;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbAbrir;
    private javax.swing.JRadioButton rbCerrar;
    private javax.swing.JTable tMovimientosdeCaja;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtConcepto;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtSerieNumero;
    // End of variables declaration//GEN-END:variables

}
