package Ventanas;import Clases.Controlador;import Clases.InfoGeneral;
import Clases.Mimodelo;import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;import java.lang.reflect.Field;
import java.util.Date;import javax.swing.ImageIcon;import javax.swing.JOptionPane;

/**************************** author Ing. Miguel Silva Zapata **************************/
public class RepVentasxTipo extends javax.swing.JInternalFrame {
/******************************Los Atributos****************************************/
    Controlador control=new Controlador();Mimodelo modelo=new Mimodelo();String idSed="";
    /******************************Fin Los Atributos********************************/
    
    /******************************Los Metodos********************************/
    public RepVentasxTipo() {
     initComponents();
     this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
     
     modelo.setColumnIdentifiers(new String[]{"Fec_Venta","Cliente","Comprobante","Fec_Emisión","M_Facturado","Monto en Caja"});
     setTitle("Lista de Ventas Por Titulo");tVentasxTipo.setModel(modelo);control.setWidthTableColumn(tVentasxTipo, 300, 1);
     PonerFechaActual();idSed=InfoGeneral.getIdSede();
     cboTipo.addActionListener(new ActionListener(){
       @Override
       public void actionPerformed(ActionEvent e){
         MostrarVentas();      
       }
     });
     
     //****************************CONTROLAR EL CAMBIO DE FECHA***********************************
     f_Desde.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
       if("date".equals(evt.getPropertyName())){ JDateChooser isse=(JDateChooser)evt.getSource(); boolean isdatasele=false;
        try { Field dateselfi=JDateChooser.class.getDeclaredField("dateSelect"); dateselfi.setAccessible(true); isdatasele=dateselfi.getBoolean(isse);
        } 
        catch (Exception e) {
         control.Sql="select datediff('"+control.Formato_Amd(F_Hasta.getDate())+"','"+control.Formato_Amd(f_Desde.getDate())+"')";
         if(Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1))<0){ MostrarVentas1();}  else   MostrarVentas();
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
          MostrarVentas1();
         }   
         else
          MostrarVentas();
        }
       }
      }
     });
     //****************************CONTROLAR EL CAMBIO DE FECHA***********************************
         MostrarVentas();
    }
    public void Cancelar(){
     txtBuscar.setText(null); PonerFechaActual();
    }
    public void PonerFechaActual(){
     f_Desde.setDate( new Date());F_Hasta.setDate( new Date());   
    }
    public void Imprimir(){
      if(tVentasxTipo.getRowCount()>0){
       control.impresor.Imprimircon6Parametros("Ventas por Tipo","reporTiposVenta", "buscar","finicio","ffinal","vtafinicio", "vtaffinal", "tipo",
       txtBuscar.getText(),control.Formato_Amd(f_Desde.getDate()),control.Formato_Amd(F_Hasta.getDate()),control.Formato_DMA(
       f_Desde.getDate()),control.Formato_DMA(F_Hasta.getDate()), cboTipo.getSelectedItem().toString());
      }  
      else
        JOptionPane.showMessageDialog(null, "No hay datos para imprimir");
    }
    public void MostrarVentas(){        
     double mt=0.0;
     control.Sql="select date_format(v.fecvta,'%d/%m/%Y') Fecha,cl.nomclie,concat(tc.tipcompr,' ',tc.ser,' ',cb.nume) Comprobante,"
     + "date_format(cv.fecemi,'%d/%m/%Y'),v.montfactu,v.montreal from venta v inner join cliente cl on cl.idcliente=v.idcliente inner join compventa "
     + "cv on cv.idventa=v.idventa inner join comprobantes cb on cb.idcomprobantes=cv.idcomprobantes inner join tipocomprobante tc on "
     + "tc.idtipocomprobante=cb.idtipocomprobante where (cl.nomclie like'%"+txtBuscar.getText()+"%' or tc.tipcompr like'%"+txtBuscar.getText()
     +"%') and (v.tipo='"+cboTipo.getSelectedItem().toString()+"') and v.fecvta between '"+control.Formato_Amd(f_Desde.getDate())+"' and '"
     +control.Formato_Amd(F_Hasta.getDate())+"'";control.LlenarJTablaConCheck(modelo,control.Sql,6); 
     mt=Double.parseDouble(control.CalcularMontodeJtable(modelo,5));jLabel9.setText(control.decimalFormat(mt));
    }
    public void MostrarVentas1(){        
     double mt=0.0;JOptionPane.showMessageDialog(null, "Rango de fechas incorrecto");
     control.Sql="select date_format(v.fecvta,'%d/%m/%Y') Fecha,cl.nomclie,concat(tc.tipcompr,' ',tc.ser,' ',cb.nume) Comprobante,"
     + "date_format(cv.fecemi,'%d/%m/%Y'),v.montfactu,v.montreal from venta v inner join cliente cl on cl.idcliente=v.idcliente inner join compventa "
     + "cv on cv.idventa=v.idventa inner join comprobantes cb on cb.idcomprobantes=cv.idcomprobantes inner join tipocomprobante tc on "
     + "tc.idtipocomprobante=cb.idtipocomprobante where (cl.nomclie like'%"+txtBuscar.getText()+"%' or tc.tipcompr like' _________________') "
     + "and (v.tipo='"+cboTipo.getSelectedItem().toString()+"') and v.fecvta between '"+control.Formato_Amd(f_Desde.getDate())+"' and '"
     +control.Formato_Amd(F_Hasta.getDate())+"'";control.LlenarJTablaConCheck(modelo,control.Sql,6); 
     mt=Double.parseDouble(control.CalcularMontodeJtable(modelo,5));jLabel9.setText(control.decimalFormat(mt));
    }
   /******************************Los Metodos********************************/        
    /*** This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.********/
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tVentasxTipo = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        f_Desde = new com.toedter.calendar.JDateChooser();
        F_Hasta = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cboTipo = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        bCancelar = new javax.swing.JButton();
        bSalir = new javax.swing.JButton();
        bImprimir = new javax.swing.JButton();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Ventas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tVentasxTipo.setModel(new javax.swing.table.DefaultTableModel(
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
        tVentasxTipo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tVentasxTipoMouseClicked(evt);
            }
        });
        tVentasxTipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tVentasxTipoKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tVentasxTipo);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 1050, 340));

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
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 30, -1, -1));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setText("Desde");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 30, -1, -1));

        f_Desde.setDateFormatString("dd-MM-yyyy");
        jPanel1.add(f_Desde, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 25, 130, -1));

        F_Hasta.setDateFormatString("dd-MM-yyyy");
        jPanel1.add(F_Hasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 25, 120, -1));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setText("Monto Total");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 400, 80, -1));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setText("Liquido en Caja");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 400, -1, -1));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel11.setText("Tipo");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 30, -1, -1));

        cboTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N", "A", "F", "P" }));
        jPanel1.add(cboTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 25, 120, -1));

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
        jPanel2.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 5, 110, 45));

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
        jPanel2.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 5, 110, 45));

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
        jPanel2.add(bImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 5, 110, 45));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 1065, 60));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void tVentasxTipoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tVentasxTipoMouseClicked
        //        CargarDatosMovimiento();
    }//GEN-LAST:event_tVentasxTipoMouseClicked
    private void tVentasxTipoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tVentasxTipoKeyReleased
        //        CargarDatosMovimiento();
    }//GEN-LAST:event_tVentasxTipoKeyReleased
    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        //MostrarMovimientoCja();
    }//GEN-LAST:event_txtBuscarKeyReleased
    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
        Cancelar();
    }//GEN-LAST:event_bCancelarActionPerformed
    
    private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_bSalirActionPerformed
    private void bImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bImprimirActionPerformed
        Imprimir();
    }//GEN-LAST:event_bImprimirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser F_Hasta;
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bImprimir;
    private javax.swing.JButton bSalir;
    private javax.swing.JComboBox cboTipo;
    private com.toedter.calendar.JDateChooser f_Desde;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tVentasxTipo;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
