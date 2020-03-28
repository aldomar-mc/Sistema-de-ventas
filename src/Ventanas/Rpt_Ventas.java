package Ventanas;
//**********************************PAQUETES*************************************
import java.util.Date;import Clases.*;import com.toedter.calendar.JDateChooser;
import java.beans.PropertyChangeEvent;import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;import java.text.DateFormat;
import java.text.DecimalFormat;import java.text.Format;
import java.util.prefs.Preferences;import javax.swing.ImageIcon;
import javax.swing.*;
//**********************************PAQUETES************************************
public class Rpt_Ventas extends javax.swing.JInternalFrame {
    //*******************ATRIBUTOS**********************************************
    Controlador control = new Controlador();Mimodelo modelo = new Mimodelo();
    IMPRIMIR imprime = new IMPRIMIR();InfoGeneral info= new InfoGeneral();
    private DecimalFormat forma = new DecimalFormat("0.00");int contadopr = 0, controladorModo = 0;
   //*******************ATRIBUTOS***********************************************
       
    //**********************************METODOS*********************************
    public Rpt_Ventas() {
     initComponents();setTitle("Reporte de Ventas");txtBuscar.grabFocus();
     this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
     tbVentas.setModel(modelo);
     modelo.setColumnIdentifiers(new String[]{"Item","Comprobante","Fec. Venta",
     "Cliente","Modalidad", "Vendedor","Total Venta","Descuento","Importe",
     "Estado"});tbVentas.getColumnModel().getColumn(0).setPreferredWidth(0);
     tbVentas.getColumnModel().getColumn(1).setPreferredWidth(120);
     tbVentas.getColumnModel().getColumn(2).setPreferredWidth(70);
     tbVentas.getColumnModel().getColumn(3).setPreferredWidth(350);
     tbVentas.getColumnModel().getColumn(4).setPreferredWidth(40);
     tbVentas.getColumnModel().getColumn(5).setPreferredWidth(50);
     tbVentas.getColumnModel().getColumn(6).setPreferredWidth(60);
     tbVentas.getColumnModel().getColumn(7).setPreferredWidth(40);
     tbVentas.getColumnModel().getColumn(8).setPreferredWidth(40);
     tbVentas.getColumnModel().getColumn(9).setPreferredWidth(40);
     jLabel4.setVisible(false);cbusuario.setVisible(false);cbModo.setVisible(false);
     jLabel5.setVisible(false);jButton3.setVisible(false);fechaFin.setDate(new Date());
     fechaIni.setDate(new Date());
     control.LlenarCombo(cbusuario, "SELECT nombre FROM "
     + "vendedores v;", 1);
     MostrarCliente();LlenarModo();MostrarVentas();llenattoal();   
     info.setIdSede(control.DevolverRegistroDto("SELECT idsede FROM sede s where nomse='"
     +control.sede+"';", 1));        
     
     fechaIni.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {       
       if("date".equals(evt.getPropertyName())){
        JDateChooser isse=(JDateChooser)evt.getSource();boolean isdatasele=false;
        try {
         Field dateselfi=JDateChooser.class.getDeclaredField("dateSelect");
         dateselfi.setAccessible(true);isdatasele=dateselfi.getBoolean(isse);
        }
        catch (Exception ignoreOrNot) {        
         //MostrarCliente();llenattoal();                        
         MostrarVentas();llenattoal();   
        }
       }
      }
     });
     fechaFin.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
       if("date".equals(evt.getPropertyName())){
        JDateChooser isse=(JDateChooser)evt.getSource();boolean isdatasele=false;
        try {
         Field dateselfi=JDateChooser.class.getDeclaredField("dateSelect");
         dateselfi.setAccessible(true);isdatasele=dateselfi.getBoolean(isse);
        }
        catch (Exception ignoreOrNot) {          
         //MostrarCliente();llenattoal();                        
         MostrarVentas();llenattoal();   
        }
       }
      }
     });
     txtBuscar.grabFocus();txtBuscar.requestFocus();       
    }
    public void llenattoal(){
     /*control.fila=0;double sum=0.0;
     while (control.fila<tbVentas.getRowCount()) {            
      sum=sum+Double.parseDouble(modelo.getValueAt(control.fila, 8).toString());      
      control.fila++;
     }
     */
     double sum=0.0;
     control.Sql="select if(isnull(sum(Monto))=1,0,sum(Monto)) from LaVista1 where (Comprobante like'%"+txtBuscar.getText()
     +"%' or cliente like'%"+txtBuscar.getText()+"%') and (Fecha between '"+
     control.Formato_Amd(fechaIni.getDate())+"' and '"+control.Formato_Amd(fechaFin.getDate())
     +"') and  Estado<>'Anulado'";
     //System.out.println(control.Sql);
     
     sum=Double.parseDouble(control.DevolverRegistroDto(control.Sql,1));
     lbTotalenVEntas.setText("S/. "+forma.format(sum));
    }
    public void LlenarModo() {
     cbModo.addItem("Contado");cbModo.addItem("Credito");cbModo.setSelectedIndex(-1);
    }
    public void MostrarCliente() {
     BuscarCliente();
    }
    public void MostrarVentas(){
     control.Sql="select * from LaVista1 where (Comprobante like'%"+txtBuscar.getText()
     +"%' or cliente like'%"+txtBuscar.getText()+"%') and (Fecha between '"+
     control.Formato_Amd(fechaIni.getDate())+"' and '"+control.Formato_Amd(fechaFin.getDate())
     +"') order by comprobante desc";
     //System.out.println(control.Sql);
      control.LlenarJTabla(modelo,control.Sql, 10);control.forma_table_ver(modelo, tbVentas);     
    }
    public void BuscarCliente() {
     /*control.Sql="select  v.idVenta,concat(ti.tipcompr,'-',cpr.nume),v.fecvta,c.nomclie,"
     + "v.moda,d.nom,IF(cpr.esta='Anulado',0.00,sum(v.montreal)),IF(cpr.esta='Anulado',"
     + "0.00,v.descuento),IF(cpr.esta='Anulado',0.00,(sum(vp.prc)-v.descuento)),cpr.esta"
     + " from  venta v,cliente c,usuario u,datosusuarios d,venta_producto vp,compventa co,"
     + "comprobantes cpr,tipocomprobante ti where v.idCliente=c.idCliente and v.Usuario_idusuario"
     + "=u.idusuario and u.idusuario=d.Usuario_idusuario and v.idVenta=vp.Venta_idVenta "
     + "and v.idVenta=co.idVenta and co.idComprobantes=cpr.idComprobantes and "
     + "cpr.idTipoComprobante=ti.idTipoComprobante and ti.idsede='"+info.getIdSede()+
     "' and v.fecvta between '"+control.Formato_Amd(fechaIni.getDate())+"' and '"+
     control.Formato_Amd(fechaFin.getDate())+"' group by v.idVenta order by idventa asc;";*/
        
     control.Sql="select v.idVenta,concat(ti.tipcompr,'-',cpr.nume) Comprobante,"
     + "v.fecvta Fecha,c.nomclie Cliente,v.moda Modalidad,d.nom Usuario,IF(cpr.esta='Anulado',"
     + "0.00,v.montreal),IF(cpr.esta='Anulado',0.00,v.descuento),IF(cpr.esta='Anulado',0.00,"
     + "(v.montreal-v.descuento)),cpr.esta from venta v,cliente c,usuario u,datosusuarios d,"
     + "venta_producto vp,compventa co,comprobantes cpr,tipocomprobante ti where v.idCliente="
     + "c.idCliente and v.Usuario_idusuario=u.idusuario and u.idusuario=d.Usuario_idusuario "
     + "and v.idVenta=vp.Venta_idVenta and v.idVenta=co.idVenta and co.idComprobantes="
     + "cpr.idComprobantes and cpr.idTipoComprobante=ti.idTipoComprobante and ti.idsede='"
     +info.getIdSede()+"' and v.fecvta between '"+control.Formato_Amd(fechaIni.getDate())
     +"' and '"+control.Formato_Amd(fechaFin.getDate())+"' group by v.idVenta order by idventa asc;";         
     control.LlenarJTabla(modelo,control.Sql, 10);control.forma_table_ver(modelo, tbVentas);     
    }
    public void BuscarPorUsuario() {
     if (cbusuario.getSelectedItem().toString().trim().length() > 0) {
      if (cbModo.getSelectedIndex() > -1) {
       control.Sql = "SELECT idventa,nomclie, fecvta, tipcompr, nume, moda, nom, "
       + "total FROM ventas_imprimir_todo where fecvta>='" + control.Formato_Amd(fechaIni.getDate()) 
       + "' and fecvta<='" + control.Formato_Amd(fechaFin.getDate()) + "' and nomusr='" 
       +cbusuario.getSelectedItem()+ "' and moda='" +cbModo.getSelectedItem().toString()
       + "' order by idventa asc;";control.LlenarJTabla(modelo, control.Sql, 8);
      }
      else {
       control.Sql = "SELECT idventa,nomclie, fecvta, tipcompr, nume, moda, nom, "
       + "total FROM ventas_imprimir_todo where fecvta>='" + control.Formato_Amd(fechaIni.getDate()) 
       + "' and fecvta<='" + control.Formato_Amd(fechaFin.getDate()) + "' and nomusr='" 
       + cbusuario.getSelectedItem() + "' order by idventa asc;";
       control.LlenarJTabla(modelo, control.Sql, 8);
      }
     }
    }
    public void ImprimirVentas() {
     if(tbVentas.getRowCount()>0){
      imprime.ImprimirusLasVentas("Ventas_General.jasper",control.Formato_Amd(
      fechaIni.getDate()), control.Formato_Amd(fechaFin.getDate()),txtBuscar.getText());   
     }   
     else
      JOptionPane.showMessageDialog(null,"No ha ventas");
     /*if (cbusuario.getSelectedIndex() == -1) {
      if (cbModo.getSelectedIndex() == -1) {
       JOptionPane.showMessageDialog(null,"Ventas 1");   
       imprime.ImprimirusConFechasVarias("Ventas_General.jasper", 
       control.Formato_Amd(fechaIni.getDate()), control.Formato_Amd(fechaFin.getDate()));       
      }
      else {
       JOptionPane.showMessageDialog(null,"Ventas 2");      
       imprime.ImprimirusConFechasVariasUsua("Ventas_General_Todo.jasper", 
       control.Formato_Amd(fechaIni.getDate()), control.Formato_Amd(fechaFin.getDate()), 
       cbModo.getSelectedItem().toString());
      }
     }
     else {
      if (cbModo.getSelectedIndex() == -1) {   
       JOptionPane.showMessageDialog(null,"Ventas 3");      
       imprime.ImprimirusConFechasVariasUsua("Ventas_General_Usuario.jasper", 
       control.Formato_Amd(fechaIni.getDate()), control.Formato_Amd(fechaFin.getDate())
       , cbusuario.getSelectedItem().toString());
      }
      else {
       JOptionPane.showMessageDialog(null,"Ventas 4");      
       imprime.ImprimirusConFechasVariasUsuaModo("Ventas_General_Modo.jasper", 
       control.Formato_Amd(fechaIni.getDate()), control.Formato_Amd(fechaFin.getDate())
       , cbusuario.getSelectedItem().toString(), cbModo.getSelectedItem().toString());
      }
     }*/
    }
    public void BuscarModos() {
     if (cbusuario.getSelectedIndex() >= 0) {
      control.Sql = "SELECT idventa,nomclie, fecvta, tipcompr, nume, moda, nom, total "
      + "FROM ventas_imprimir_todo where fecvta>='" + control.Formato_Amd(fechaIni.getDate()) 
      + "' and fecvta<='" + control.Formato_Amd(fechaFin.getDate()) + "' and nomusr='" + 
      cbusuario.getSelectedItem().toString() + "' and moda='" + cbModo.getSelectedItem().toString()
      + "' order by idventa asc;";control.LlenarJTabla(modelo, control.Sql, 8);
     }
     else {
      control.Sql = "SELECT idventa,nomclie, fecvta, tipcompr, nume, moda, nom, total "
      + "FROM ventas_imprimir_todo where fecvta>='" + control.Formato_Amd(fechaIni.getDate()) 
      + "' and fecvta<='" + control.Formato_Amd(fechaFin.getDate()) + "' and moda='" +
      cbModo.getSelectedItem().toString() + "' order by idventa asc;";
      control.LlenarJTabla(modelo, control.Sql,8);
     } 
    }
    public void llen(){
     control.Sql="SELECT idventa,nomclie, fecvta, tipcompr, nume, moda, nom, total "
     + "FROM ventas_imprimir_todo where nomclie like '"+txtBuscar.getText()+"%' "
     + "or nume like '"+txtBuscar.getText()+"%';";
     control.LlenarJTabla(modelo, control.Sql, 8);
    }
  //**********************************METODOS**********************************
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        fechaIni = new com.toedter.calendar.JDateChooser();
        fechaFin = new com.toedter.calendar.JDateChooser();
        cbusuario = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        cbModo = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbTotalenVEntas = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbVentas = new javax.swing.JTable();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Reporte de Ventas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 255))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Desde");
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, -1, -1));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Hasta");
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, -1, -1));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Usuario");
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, -1, -1));

        fechaIni.setDateFormatString("dd-MM-yyyy");
        fechaIni.setName("fechaIni"); // NOI18N
        jPanel1.add(fechaIni, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 157, -1));

        fechaFin.setDateFormatString("dd-MM-yyyy");
        fechaFin.setName("fechaFin"); // NOI18N
        jPanel1.add(fechaFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 30, 157, -1));

        cbusuario.setName("cbusuario"); // NOI18N
        cbusuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbusuarioMouseClicked(evt);
            }
        });
        cbusuario.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbusuarioItemStateChanged(evt);
            }
        });
        jPanel1.add(cbusuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 157, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Estado");
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 50, -1, -1));

        cbModo.setName("cbModo"); // NOI18N
        cbModo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbModoMouseClicked(evt);
            }
        });
        cbModo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbModoItemStateChanged(evt);
            }
        });
        jPanel1.add(cbModo, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, 157, -1));

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 51, 102));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Search1.png"))); // NOI18N
        jButton3.setText("Buscar");
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, -1, -1));

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton2.setMnemonic('s');
        jButton2.setText("Salir");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 15, 120, 40));

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Print.png"))); // NOI18N
        jButton1.setMnemonic('i');
        jButton1.setText("Imprimir");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 15, 120, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, 70));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Listado de Ventas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBuscar.setName("txtBuscar"); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel2.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 27, 480, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("Buscar");
        jLabel6.setName("jLabel6"); // NOI18N
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 50, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setText("Total en Ventas");
        jLabel8.setName("jLabel8"); // NOI18N
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 30, 100, -1));

        lbTotalenVEntas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbTotalenVEntas.setForeground(new java.awt.Color(0, 51, 102));
        lbTotalenVEntas.setText(" ");
        lbTotalenVEntas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbTotalenVEntas.setName("lbTotalenVEntas"); // NOI18N
        jPanel2.add(lbTotalenVEntas, new org.netbeans.lib.awtextra.AbsoluteConstraints(918, 27, 130, -1));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tbVentas.setForeground(new java.awt.Color(0, 51, 102));
        tbVentas.setModel(new javax.swing.table.DefaultTableModel(
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
        tbVentas.setName("tbVentas"); // NOI18N
        tbVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbVentasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbVentasMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tbVentas);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 1040, 480));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 71, 1060, 540));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //**********************************EVETOS***********************************
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed
    private void cbusuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbusuarioMouseClicked
     contadopr++;
    }//GEN-LAST:event_cbusuarioMouseClicked
    private void cbusuarioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbusuarioItemStateChanged
     if (contadopr > 0) 
      BuscarPorUsuario();
    }//GEN-LAST:event_cbusuarioItemStateChanged
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     ImprimirVentas();
    }//GEN-LAST:event_jButton1ActionPerformed
    private void cbModoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbModoMouseClicked
     controladorModo++;
    }//GEN-LAST:event_cbModoMouseClicked
    private void cbModoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbModoItemStateChanged
     if (controladorModo > 0) 
      BuscarModos();     
    }//GEN-LAST:event_cbModoItemStateChanged
    private void tbVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbVentasMouseClicked
     if(evt.getClickCount()==2)
      control.impresor.Imprimircon1Parametros("Detalles de la Venta","Detalle_venta","codv", 
      modelo.getValueAt(tbVentas.getSelectedRow(), 0).toString());
    }//GEN-LAST:event_tbVentasMouseClicked
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
     MostrarCliente();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed
    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
     //llen();      MostrarCliente();
     MostrarVentas();llenattoal();
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void tbVentasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbVentasMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tbVentasMouseEntered
    //**********************************EVETOS***********************************
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbModo;
    private javax.swing.JComboBox cbusuario;
    private com.toedter.calendar.JDateChooser fechaFin;
    private com.toedter.calendar.JDateChooser fechaIni;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbTotalenVEntas;
    private javax.swing.JTable tbVentas;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
