/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

/**
 *
 * @author User
 */
import java.util.Date;
import Clases.*;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class RepComprobantesAnulados extends javax.swing.JInternalFrame {

    /************************************LOS ATRIBUTOS************************************************/
    Controlador control = new Controlador();Mimodelo modelo = new Mimodelo();
    String Comprobante="";
    /************************************LOS ATRIBUTOS************************************************/
    
    /************************************LOS METODOS*************************************************/
    public RepComprobantesAnulados() {
        initComponents();setTitle("Los Comprobantes Anulados");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        modelo.setColumnIdentifiers(new String[]{"IdAnulado", "Num_Anulado", "F_Anulo", "H_Anulo", "Nuevo_Comprobante",
        "Cliente", "Motivo"});control.LlenarCombo(cboTipo,"select * from tipocomprobante where tipcompr not in('Venta Libre','Recibo',"
        + "'Nota de Venta') order by 2 desc;",2);T_ComprobantesAnulados.setModel(modelo);
        control.setWidthTableColumn(T_ComprobantesAnulados,15,2,3);
        control.setWidthTableColumn(T_ComprobantesAnulados,250,5);
        control.setWidthTableColumn(T_ComprobantesAnulados,120,6);
        control.hideTableColumn(T_ComprobantesAnulados, 0);
        control.setCenterColumns(T_ComprobantesAnulados,2,3);
        f_Desde.setDate(new Date());
        F_Hasta.setDate(new Date());
        MostrarComprobantesAnulados();
        cboTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Comprobante = cboTipo.getSelectedItem().toString();                
                MostrarComprobantesAnulados();
            }
        });
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
                    } catch (Exception ignoreOrNot) {
                        MostrarComprobantesAnulados();
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
                    } catch (Exception ignoreOrNot) {
                        MostrarComprobantesAnulados();
                    }
                }
            }
        });
    }
    public void MostrarComprobantesAnulados() {
       String fde = "", fhst = "";fde = control.Formato_Amd(f_Desde.getDate());fhst = control.Formato_Amd(F_Hasta.getDate()); 
       control.Sql = "select cb.idcomprobantes ID_Anulado, concat(tc.tipcompr,':  ',tc.ser,'-',cb.nume) Numero_Anulado,"
       + "date_format(a.Fecha,'%d/%m/%Y') F_Anulo,a.Hora H_Anulo,(select concat(tcb.tipcompr,"
       + "':  ',tcb.ser,'-',cbt.nume) from compventa cvt inner join comprobantes cbt on cbt.idcomprobantes=cvt.idcomprobantes "
       + "inner join tipocomprobante tcb on tcb.idtipocomprobante=cbt.idtipocomprobante where cvt.idventa=v.idventa) "
       + "Nuevo_Comprobante,cl.nomclie Cliente,a.Motivo from venta v inner join cliente cl on cl.idcliente=v.idcliente inner join "
       + "anulados a on a.idventa=v.idventa inner join comprobantes cb on cb.idcomprobantes=a.idcomprobantes inner join "
       + "tipocomprobante tc on tc.idtipocomprobante=cb.idtipocomprobante where a.Fecha between '" + fde + "' and '" + fhst 
       + "' and tc.tipcompr like'"+Comprobante+"%' and (cb.nume like '%"+txtBuscar.getText()+"%')";
       control.LlenarJTabla(modelo, control.Sql, 7);
    }
    public void Imprimir(){
     if(T_ComprobantesAnulados.getRowCount()>0){
      control.impresor.Imprimircon6Parametros("Reporte de Comprobantes Anulados","repCompAnulados","fdesde","fhasta","fdesdeV",
      "fhastaV", "comprobante","filtro",control.Formato_Amd(f_Desde.getDate()),control.Formato_Amd(F_Hasta.getDate()),
      control.Formato_DMA(f_Desde.getDate()),control.Formato_DMA(F_Hasta.getDate()),Comprobante,txtBuscar.getText());
     }  
     else{
      JOptionPane.showMessageDialog(null, "No hay comprobantes anulado para imprimir");
     }
    }
    public void Cancelar(){
       T_ComprobantesAnulados.clearSelection();
       cboTipo.setSelectedIndex(-1);
       txtBuscar.setText("");Comprobante="";
    }
    
    /************************************LOS METODOS*************************************************/
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        T_ComprobantesAnulados = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        bSalir = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        bImprimir = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        f_Desde = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        F_Hasta = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        cboTipo = new javax.swing.JComboBox();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Comprobantes Anulados", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(51, 0, 102))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        T_ComprobantesAnulados.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(T_ComprobantesAnulados);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 1020, 320));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("Buscar");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 30, -1, -1));

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 25, 960, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 55, 1040, 380));

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
        jPanel3.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 120, 50));

        bCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCancelar.setForeground(new java.awt.Color(0, 0, 102));
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setMnemonic('c');
        bCancelar.setText("Cancelar");
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });
        jPanel3.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 10, 120, 50));

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
        jPanel3.add(bImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 120, 50));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 436, 450, 70));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Criterios de Selección", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setText("Tipo");
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        f_Desde.setDateFormatString("dd-MM-yyyy");
        jPanel5.add(f_Desde, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, 130, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setText("Hasta");
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 22, -1, -1));

        F_Hasta.setDateFormatString("dd-MM-yyyy");
        jPanel5.add(F_Hasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 20, 130, -1));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setText("Desde");
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 22, -1, -1));

        jPanel5.add(cboTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 200, -1));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 1040, 50));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
        dispose();
    }//GEN-LAST:event_bSalirActionPerformed
    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
      MostrarComprobantesAnulados();
    }//GEN-LAST:event_txtBuscarKeyReleased
    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
     Cancelar();
    }//GEN-LAST:event_bCancelarActionPerformed
    private void bImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bImprimirActionPerformed
     Imprimir();
    }//GEN-LAST:event_bImprimirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser F_Hasta;
    private javax.swing.JTable T_ComprobantesAnulados;
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bImprimir;
    private javax.swing.JButton bSalir;
    private javax.swing.JComboBox cboTipo;
    private com.toedter.calendar.JDateChooser f_Desde;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
