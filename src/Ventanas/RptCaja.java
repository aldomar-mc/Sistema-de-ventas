package Ventanas;

import Clases.Controlador;
import Clases.InfoGeneral;
import Clases.Mimodelo;
import com.toedter.calendar.JDateChooser;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Field;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * *
 * @author Ing. Miguel Angel Silva Zapata
 */
public class RptCaja extends javax.swing.JInternalFrame {
    //****************************LOS ATRIBUTOS*********************************

    Controlador control = new Controlador();
    Mimodelo modelo = new Mimodelo();
    String sed = "", tipo = "", usuario = "", Estado = "", idmovcja = "";
    double montoTotal = 0.0;
    String IgrEgre = "";
    //***************************FIN LOS ATRIBUTOS******************************

    //****************************LOS METODOS******************************
    public RptCaja() {
        initComponents();
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        modelo.setColumnIdentifiers(new String[]{"ID", "Concepto", "Fecha", "Comprobante", "Monto",
            "Descuento", "Ingreso", "N° Comprobante", "Ingreso/Egreso"});
        tMovimientosdeCaja.setModel(modelo);
        tMovimientosdeCaja.getColumnModel().getColumn(1).setPreferredWidth(350);
        tMovimientosdeCaja.getColumnModel().getColumn(0).setMaxWidth(0);
        tMovimientosdeCaja.getColumnModel().getColumn(0).setMinWidth(0);
        tMovimientosdeCaja.getColumnModel().getColumn(0).setPreferredWidth(0);
        PonerFechaActual();
        rbGeneral.setSelected(true);
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

    public void ObtenterTipo() {
        if (rbGeneral.isSelected()) {
            IgrEgre = "";
        }
        if (rbIngresos.isSelected()) {
            IgrEgre = "Ingreso";
        }
        if (rbEgresos.isSelected()) {
            IgrEgre = "Egreso";
        }
    }

    public void MostrarMovimientoCja() {
        ObtenterTipo();
        /*if(IgrEgre.length()==0){
    control.Sql="select 1 as idMovimientoCaja,concepto,fch,comprobante,monto, descuento,"
    + "(importe-descuento),numcomprobante,tipo from vta_MovCaja1 where ( fch between '"                
    +control.Formato_Amd(f_Desde.getDate())+"' and '"+control.Formato_Amd(F_Hasta.getDate())
    +"') and (concepto like'%"+txtBuscar.getText()+"%' or comprobante like'%"+
    txtBuscar.getName()+"%' or "+"numcomprobante like'%"+txtBuscar.getText()
    +"%' or comprobante like'%"+txtBuscar.getText()+"%')";    
   }
   else{*/
 /*control.Sql="select 1 as idMovimientoCaja,concepto,fch,comprobante,monto, descuento,"
    + "(importe-descuento),numcomprobante,tipo from vta_MovCaja1 where ( fch between '"                
    +control.Formato_Amd(f_Desde.getDate())+"' and '"+control.Formato_Amd(F_Hasta.getDate())
    +"') and tipo='"+IgrEgre+"' and (concepto like'%"+txtBuscar.getText()+"%' or comprobante like'%"+
     txtBuscar.getName()+"%' or "+"numcomprobante like'%"+txtBuscar.getText()
     +"%' or comprobante like'%"+txtBuscar.getText()+"%')";     */
        control.Sql = "select 1 as idMovimientoCaja,concepto,fch,comprobante,monto, descuento,"
                + "(importe-descuento),numcomprobante,tipo from vta_MovCaja1 where ( fch between '"
                + control.Formato_Amd(f_Desde.getDate()) + "' and '" + control.Formato_Amd(F_Hasta.getDate())
                + "') and (concepto like'%" + txtBuscar.getText() + "%' or comprobante like'%"
                + txtBuscar.getName() + "%' or " + "numcomprobante like'%" + txtBuscar.getText()
                + "%' or comprobante like'%" + txtBuscar.getText() + "%' or tipo like'%" + IgrEgre + "%')";
        // }
        control.LlenarJTabla(modelo, control.Sql, 9);
        jLabel9.setText(control.CalcularMontodeJtable(modelo, 6));
        CalcularMontodeCaja();
    }

    public void CalcularMontodeCaja() {
        double mt = 0.0;
        for (control.fila = 0; control.fila < modelo.getRowCount(); control.fila++) {
            if (modelo.getValueAt(control.fila, 8).toString().equalsIgnoreCase("Ingreso")) {
                mt = mt + Double.parseDouble(modelo.getValueAt(control.fila, 6).toString());
            } else {
                mt = mt - Double.parseDouble(modelo.getValueAt(control.fila, 6).toString());
            }
        }
        if (IgrEgre.equals("Egreso")) {
            mt = -1 * mt;
        }
        jLabel9.setText(control.decimalFormat(mt));
    }

    public void Cancelar() {
        txtBuscar.setText(null);
        PonerFechaActual();
        tMovimientosdeCaja.clearSelection();
        MostrarMovimientoCja();
    }

    public void Imprimir() {
        if (tMovimientosdeCaja.getRowCount() > 0) {
            control.impresor.ImprGeneralCaja("RptMovimientoCaja", "Reporte de Caja", "dtoBuscar",
                    "FDesde", "FHasta", "MtonLiqu", "FIni", "Ffin", txtBuscar.getText(),
                    control.Formato_Amd(f_Desde.getDate()), control.Formato_Amd(F_Hasta.getDate()),
                    jLabel9.getText(), control.Formato_DMA(f_Desde.getDate()),
                    control.Formato_DMA(F_Hasta.getDate()));
        } else {
            JOptionPane.showMessageDialog(null, "No hay nada");
        }
        Cancelar();
    }
//*************************FIN LOS METODOS******************************

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
        rbEgresos = new javax.swing.JRadioButton();
        rbGeneral = new javax.swing.JRadioButton();
        rbIngresos = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        bCancelar = new javax.swing.JButton();
        bSalir = new javax.swing.JButton();
        bImprimir = new javax.swing.JButton();

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
        jScrollPane1.setViewportView(tMovimientosdeCaja);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 1050, 340));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setText("Buscar");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 30, -1, -1));

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 25, 300, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setText("Hasta");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(585, 30, -1, -1));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setText("Desde");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, -1, -1));

        f_Desde.setDateFormatString("dd-MM-yyyy");
        jPanel1.add(f_Desde, new org.netbeans.lib.awtextra.AbsoluteConstraints(445, 27, 110, -1));

        F_Hasta.setDateFormatString("dd-MM-yyyy");
        jPanel1.add(F_Hasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 27, 110, -1));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setText("Monto Total");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 400, 80, -1));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setText("Liquido en Caja");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 400, -1, -1));

        buttonGroup1.add(rbEgresos);
        rbEgresos.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        rbEgresos.setMnemonic('e');
        rbEgresos.setText("Egresos");
        rbEgresos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbEgresosActionPerformed(evt);
            }
        });
        jPanel1.add(rbEgresos, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 20, 69, -1));

        buttonGroup1.add(rbGeneral);
        rbGeneral.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        rbGeneral.setMnemonic('n');
        rbGeneral.setText("General");
        rbGeneral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbGeneralActionPerformed(evt);
            }
        });
        jPanel1.add(rbGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 20, 69, -1));

        buttonGroup1.add(rbIngresos);
        rbIngresos.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        rbIngresos.setMnemonic('g');
        rbIngresos.setText("Ingresos");
        rbIngresos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbIngresosActionPerformed(evt);
            }
        });
        jPanel1.add(rbIngresos, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 20, 73, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1070, 420));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setMnemonic('a');
        bCancelar.setText("Cancelar");
        bCancelar.setMargin(new java.awt.Insets(2, 1, 2, 14));
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 110, 45));

        bSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir.setMnemonic('s');
        bSalir.setText("Salir");
        bSalir.setMargin(new java.awt.Insets(2, 1, 2, 14));
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });
        jPanel2.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 110, 45));

        bImprimir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Print.png"))); // NOI18N
        bImprimir.setMnemonic('i');
        bImprimir.setText("Imprimir");
        bImprimir.setMargin(new java.awt.Insets(2, 1, 2, 14));
        bImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bImprimirActionPerformed(evt);
            }
        });
        jPanel2.add(bImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 110, 45));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 420, 390, 65));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        MostrarMovimientoCja();
    }//GEN-LAST:event_txtBuscarKeyReleased
    private void jPanel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseMoved
        CalcularMontodeCaja();
    }//GEN-LAST:event_jPanel1MouseMoved
    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
        Cancelar();
    }//GEN-LAST:event_bCancelarActionPerformed
    private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_bSalirActionPerformed
    private void bImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bImprimirActionPerformed
        Imprimir();
    }//GEN-LAST:event_bImprimirActionPerformed
    private void rbGeneralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbGeneralActionPerformed
        MostrarMovimientoCja();
    }//GEN-LAST:event_rbGeneralActionPerformed
    private void rbIngresosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbIngresosActionPerformed
        MostrarMovimientoCja();
    }//GEN-LAST:event_rbIngresosActionPerformed
    private void rbEgresosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbEgresosActionPerformed
        MostrarMovimientoCja();
    }//GEN-LAST:event_rbEgresosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser F_Hasta;
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bImprimir;
    private javax.swing.JButton bSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser f_Desde;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbEgresos;
    private javax.swing.JRadioButton rbGeneral;
    private javax.swing.JRadioButton rbIngresos;
    private javax.swing.JTable tMovimientosdeCaja;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
