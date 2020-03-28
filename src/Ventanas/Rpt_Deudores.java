/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/** VerDeudores.java*
 * Created on 04-feb-2014, 15:07:14
 */
package Ventanas;
import Clases.*;import com.toedter.calendar.JDateChooser;import java.beans.PropertyChangeEvent;
import java.lang.reflect.Field;import java.util.Date;import javax.swing.ImageIcon;
import javax.swing.JOptionPane;import javax.swing.JPopupMenu;

/**
 *
 * @author Silva
 */
public class Rpt_Deudores extends javax.swing.JInternalFrame {
 //*****************************ATRIBUTOS***************************************
 Controlador control=new Controlador();Mimodelo modelo=new Mimodelo();
 IMPRIMIR imprime= new IMPRIMIR();String codDEu="", CodVent="",CodSerie="";
 double montoDeuda=0,pagoini=0;
 //*****************************ATRIBUTOS***************************************
 
 //*****************************METODOS*****************************************
    public void BuscarDeudores(){
     control.Sql="SELECT iddeuda,nomclie,fono, fecvta,fecdeud, montdeu, pgoinici,"
     + "tipcompr,nume FROM deudores where nomclie like'%"+txtBuscar.getText()
     +"%' and montdeu<>0 and fecdeud<=curdate() and fecdeud between '"+control.Formato_Amd(f_Desde.getDate())+"' and '"+
     control.Formato_Amd(F_Hasta.getDate())+"'";control.LlenarJTabla(modelo,control.Sql,9);
    }
    public void MostrarDeudores(){
     BuscarDeudores();   
    }    
    public void MostrarDeudores1(){
      JOptionPane.showMessageDialog(null, "Rango de fechas incorrecto");   
       control.Sql="SELECT iddeuda,nomclie,fono, fecvta,fecdeud, montdeu, pgoinici,"
     + "tipcompr,nume FROM deudores where nomclie like'%----67777777344443434%  fecdeud<=curdate();";
     control.LlenarJTabla(modelo,control.Sql,9);
    }    
    public void Imprimir(){
     if(tDeudores.getRowCount()>0){   
      control.impresor.Imprimircon5Parametros("Lista de Deudores","DeudoresRetraso","buscar","fdesde", "fhasta","fdesdevta","fhastavta",txtBuscar.getText()
      ,control.Formato_Amd(f_Desde.getDate()),control.Formato_Amd(F_Hasta.getDate()), control.Formato_DMA(f_Desde.getDate()),control.Formato_DMA(F_Hasta.getDate()));
     }
     else
       JOptionPane.showMessageDialog(null, "No hay datos para Imprimir");
    }
    public void PonerFechaActual(){
     f_Desde.setDate( new Date());F_Hasta.setDate( new Date());   
    }
    public Rpt_Deudores() { 
     initComponents();setTitle("Deudores con Retraso");this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
     tDeudores.setModel(modelo);modelo.setColumnIdentifiers(new String[] {"Orden","cliente","Telefono",
     "Fecha Venta","Fecha Pago","Deuda","Pago Inicial","Comprobante","Numero"});control.hideTableColumn(tDeudores, 0);
     control.setWidthTableColumn(tDeudores, 200, 1);control.setWidthTableColumn(tDeudores, 80, 2,3,4,5,6);PonerFechaActual();
     
     //****************************CONTROLAR EL CAMBIO DE FECHA***********************************
     f_Desde.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
       if("date".equals(evt.getPropertyName())){ JDateChooser isse=(JDateChooser)evt.getSource(); boolean isdatasele=false;
        try { Field dateselfi=JDateChooser.class.getDeclaredField("dateSelect"); dateselfi.setAccessible(true); isdatasele=dateselfi.getBoolean(isse);} 
        catch (Exception e) {
         control.Sql="select datediff('"+control.Formato_Amd(F_Hasta.getDate())+"','"+control.Formato_Amd(f_Desde.getDate())+"')";
         if(Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1))<0){ MostrarDeudores1();}  else   MostrarDeudores();
        }
       }
      }
     });
     
     F_Hasta.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
       if("date".equals(evt.getPropertyName())){  JDateChooser isse=(JDateChooser)evt.getSource();
        boolean isdatasele=false;
        try {
         Field dateselfi=JDateChooser.class.getDeclaredField("dateSelect");
         dateselfi.setAccessible(true);isdatasele=dateselfi.getBoolean(isse);
        } 
        catch (Exception e) {
         control.Sql="select datediff('"+control.Formato_Amd(F_Hasta.getDate())+"','"+control.Formato_Amd(f_Desde.getDate())+"')";
         if(Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1))<0){ 
          MostrarDeudores1();
         }   
         else
          MostrarDeudores();
        }
       }
      }
     });
     //****************************CONTROLAR EL CAMBIO DE FECHA***********************************
     MostrarDeudores();
     //MostrarDeudores();control.forma_table_ver(modelo, tDeudores);
    }
 //*****************************METODOS*****************************************
    
    @SuppressWarnings("unchecked")
//*******************************EVENTOS****************************************
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        bSalir = new javax.swing.JButton();
        bImprimir = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        f_Desde = new com.toedter.calendar.JDateChooser();
        F_Hasta = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tDeudores = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel2.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 10, 114, 45));

        bImprimir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bImprimir.setForeground(new java.awt.Color(0, 51, 102));
        bImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Print.png"))); // NOI18N
        bImprimir.setMnemonic('i');
        bImprimir.setText("Imprimir");
        bImprimir.setName("bImprimir"); // NOI18N
        bImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bImprimirActionPerformed(evt);
            }
        });
        jPanel2.add(bImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 114, 45));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setText("Desde");
        jLabel9.setName("jLabel9"); // NOI18N
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 19, -1, -1));

        f_Desde.setDateFormatString("dd-MM-yyyy");
        f_Desde.setName("f_Desde"); // NOI18N
        jPanel2.add(f_Desde, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 19, 130, -1));

        F_Hasta.setDateFormatString("dd-MM-yyyy");
        F_Hasta.setName("F_Hasta"); // NOI18N
        jPanel2.add(F_Hasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 19, 130, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setText("Hasta");
        jLabel7.setName("jLabel7"); // NOI18N
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 19, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, 60));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tDeudores.setForeground(new java.awt.Color(0, 51, 102));
        tDeudores.setModel(new javax.swing.table.DefaultTableModel(
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
        tDeudores.setName("tDeudores"); // NOI18N
        tDeudores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tDeudoresMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tDeudores);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 39, 910, 370));

        txtBuscar.setName("txtBuscar"); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel3.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(66, 13, 312, -1));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Buscar");
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 16, -1, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 930, 420));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
     MostrarDeudores();
    }//GEN-LAST:event_txtBuscarKeyReleased
    private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
     dispose();        
    }//GEN-LAST:event_bSalirActionPerformed
    private void tDeudoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tDeudoresMouseClicked
     if(evt.getClickCount()==2){
      imprime.ImprimirusConFechas("rpt_Pagos.jasper", 
      modelo.getValueAt(tDeudores.getSelectedRow(), 0).toString());
     }
    }//GEN-LAST:event_tDeudoresMouseClicked
    private void bImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bImprimirActionPerformed
     Imprimir();
    }//GEN-LAST:event_bImprimirActionPerformed
//*******************************EVENTOS****************************************
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser F_Hasta;
    private javax.swing.JButton bImprimir;
    private javax.swing.JButton bSalir;
    private com.toedter.calendar.JDateChooser f_Desde;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tDeudores;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
