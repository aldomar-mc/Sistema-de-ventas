/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Ventanas;

/**
 *
 * @author User Ing. Silva
 */
import Clases.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class FrmRepDefectuosos extends javax.swing.JInternalFrame {
    
    /*****************************LOS ATRIBUTOS**********************/
    Controlador control=new Controlador();Mimodelo modelo1=new Mimodelo();
    Mimodelo modelo2=new Mimodelo();String idret="";    
    /*****************************LOS ATRIBUTOS**********************/
    
    public FrmRepDefectuosos() {
      initComponents();setTitle("Visualizar los productos Defectuosos");
      this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
      tRetirosDefectuosos.setModel(modelo1);modelo1.setColumnIdentifiers(new String[] {"ID","Fecha",
      "Hora","Usuario","Observación"});control.hideTableColumn(tRetirosDefectuosos, 0);
       control.setWidthTableColumn(tRetirosDefectuosos,300, 4);tProductosDefectuosos.setModel(modelo2);
       modelo2.setColumnIdentifiers(new String[] {"ID","Código","Producto","Marca","Unidad","Cantidad"});        
       control.hideTableColumn(tProductosDefectuosos, 0);
       control.setWidthTableColumn(tProductosDefectuosos,300, 2);MostrarRetiros();       
    }
    public void MostrarRetiros(){
       control.Sql="select r.idRetiroDefectuosos ID,date_format(r.fecre,'%d/%m/%Y') Fecha,r.hra Hora,u.nomusr "
      + "Usuario,r.obser Observacion from RetiroDefectuosos r inner join usuario u on "
      + "r.idusuario=u.idusuario where u.nomusr like'%"+txBuscar.getText()+"%'";
       control.LlenarJTabla(modelo1,control.Sql,5); // System.out.println(control.Sql);
    }
    public void Seleccionar1(){
     control.fila=tRetirosDefectuosos.getSelectedRow();
     if(control.fila>-1){
      idret=tRetirosDefectuosos.getValueAt(control.fila,0).toString();
      control.Sql="SELECT  (SELECT p.`idProducto` FROM producto p WHERE p.`Catalogoproducto_codctlg` = c.`codctlg` AND p.`estdo`='Defectuoso' limit 1)" +
      ", (SELECT p.`codbrr` FROM producto p WHERE p.`Catalogoproducto_codctlg` = c.`codctlg` AND p.`estdo`='Defectuoso' limit 1)," +
      " concat(mo.`nommod`,' ',c.`nomctlg`)AS `nomctlg`, m.`nommrc`, u.`nomuni`," +
      " (SELECT count(p.`Catalogoproducto_codctlg`) FROM producto p WHERE p.`Catalogoproducto_codctlg` = c.`codctlg`" +
      " AND p.`estdo`='Defectuoso' limit 1)  FROM catalogoproducto c, marca m, modelo mo, unidad u,usuario us,prodDefectuosos "
      + "pd,RetiroDefectuosos rd WHERE (c.`idMarca` = m.`idMarca` AND c.`idModelos` = "
      + "mo.`idModelos` AND c.`idUnidad` = u.`idUnidad` and us.idusuario=rd.idusuario and "
      + "rd.idRetiroDefectuosos=pd.idRetiroDefectuosos and pd.idproducto= (SELECT p.`idproducto` FROM "
      + "producto p WHERE p.`Catalogoproducto_codctlg` = c.`codctlg` AND p.`estdo`='Defectuoso' limit 1)" +
       " and (SELECT p.`idSede` FROM producto p WHERE p.`Catalogoproducto_codctlg` = c.`codctlg` "
      + "AND p.`estdo`='Defectuoso' limit 1)='1') and(concat(`mo`.`nommod`,' ',`c`.`nomctlg`) like '%%' "
      + "or (SELECT p.`codbrr` FROM producto p WHERE p.`Catalogoproducto_codctlg` = c.`codctlg` "
      + "AND p.`estdo`='Defectuoso' limit 1) like '%%' or `m`.`nommrc` like '%%' or nomctlg like'%%') "
      + "AND c.`codctlg` in (SELECT p.`Catalogoproducto_codctlg` FROM producto p WHERE "
      + "p.`estdo`='Defectuoso' ) and rd.idRetiroDefectuosos="+idret+";";
      control.LlenarJTabla(modelo2, control.Sql, 6);
     }
     else{
       control.LimTabla(modelo2);
     }         
    }
    public void SeleccionarTodos(){     
     if(chkTodos.isSelected()){      
      txBuscar.setText(null);tRetirosDefectuosos.clearSelection();
      control.Sql="SELECT  (SELECT p.`idProducto` FROM producto p WHERE "
      + "p.`Catalogoproducto_codctlg`=c.`codctlg` AND p.`estdo`='Defectuoso' limit 1), (SELECT p.`codbrr` "
      + "FROM producto p WHERE p.`Catalogoproducto_codctlg` = c.`codctlg` AND p.`estdo`"
      + "='Defectuoso' limit 1),concat(mo.`nommod`,' ',c.`nomctlg`)AS `nomctlg`, m.`nommrc`, u.`nomuni`," +
      " (SELECT count(p.`Catalogoproducto_codctlg`) FROM producto p WHERE "
      + "p.`Catalogoproducto_codctlg`= c.`codctlg` AND p.`estdo`='Defectuoso' limit 1)  FROM catalogoproducto "
      + "c, marca m, modelo mo, unidad u,usuario us,prodDefectuosos pd,RetiroDefectuosos rd WHERE "
      + "(c.`idMarca` = m.`idMarca` AND c.`idModelos` = "
      + "mo.`idModelos` AND c.`idUnidad` = u.`idUnidad` and us.idusuario=rd.idusuario and "
      + "rd.idRetiroDefectuosos=pd.idRetiroDefectuosos and pd.idproducto= (SELECT p.`idproducto` FROM "
      + "producto p WHERE p.`Catalogoproducto_codctlg` = c.`codctlg` AND p.`estdo`='Defectuoso' limit 1)" +
       " and (SELECT p.`idSede` FROM producto p WHERE p.`Catalogoproducto_codctlg` = c.`codctlg` "
      + "AND p.`estdo`='Defectuoso' limit 1)='1') and(concat(`mo`.`nommod`,' ',`c`.`nomctlg`) like '%%' "
      + "or (SELECT p.`codbrr` FROM producto p WHERE p.`Catalogoproducto_codctlg` = c.`codctlg` "
      + "AND p.`estdo`='Defectuoso' limit 1) like '%%' or `m`.`nommrc` like '%%' or nomctlg like'%%') "
      + "AND c.`codctlg` in (SELECT p.`Catalogoproducto_codctlg` FROM producto p WHERE "
      + "p.`estdo`='Defectuoso' );"; 
      control.LlenarJTabla(modelo2, control.Sql, 6);      
     }
     else{
       control.LimTabla(modelo2);
     }
         
    }
    public void Cancelar(){
     txBuscar.setText(null);chkTodos.setSelected(false);control.LimTabla(modelo2);idret="";
     MostrarRetiros();             
    }
    public void Imprimir(){
      if(tProductosDefectuosos.getRowCount()>0){
       if(chkTodos.isSelected()){
        /*control.impresor.Imprimircon1Parametros("Reporte General de defectuosos",
        "repoProductoDefectuososgnral","buscar",InfoGeneral.idSede,"");    
        */
        control.impresor.Imprimircon2Parametros("Reporte General de defectuosos","repoProductoDefectuososgnral",
        "idsede", "buscar",InfoGeneral.idSede,"");
       }  
       else{
        control.impresor.Imprimircon2Parametros("Reporte de defectuosos","repCaberaDefectuosos",
        "idsede", "buscar",InfoGeneral.idSede,"");
       }
      }  
      else{
          JOptionPane.showMessageDialog(null, "No hay ningun producto defectuoso");
      }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tRetirosDefectuosos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txBuscar = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        bSalir = new javax.swing.JButton();
        bImprimir = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tProductosDefectuosos = new javax.swing.JTable();
        chkTodos = new javax.swing.JCheckBox();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de retiros de productos defectuosos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(0, 0, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tRetirosDefectuosos.setModel(new javax.swing.table.DefaultTableModel(
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
        tRetirosDefectuosos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tRetirosDefectuososMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tRetirosDefectuosos);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 910, 150));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Buscar");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));
        jPanel2.add(txBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 28, 460, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, 210));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir.setMnemonic('s');
        bSalir.setText("Salir");
        bSalir.setMargin(new java.awt.Insets(2, 1, 2, 1));
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });
        jPanel4.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 15, 110, 40));

        bImprimir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Print.png"))); // NOI18N
        bImprimir.setMnemonic('i');
        bImprimir.setText("Imprimir");
        bImprimir.setMargin(new java.awt.Insets(2, 1, 2, 1));
        bImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bImprimirActionPerformed(evt);
            }
        });
        jPanel4.add(bImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 15, 110, 40));

        bCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setMnemonic('c');
        bCancelar.setText("Cancelar");
        bCancelar.setMargin(new java.awt.Insets(2, 1, 2, 1));
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });
        jPanel4.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 15, 110, 40));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 475, 930, 70));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Productos Defectuosos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tProductosDefectuosos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tProductosDefectuosos);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 900, 210));

        chkTodos.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        chkTodos.setForeground(new java.awt.Color(0, 51, 102));
        chkTodos.setMnemonic('t');
        chkTodos.setText("Todos");
        chkTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTodosActionPerformed(evt);
            }
        });
        jPanel1.add(chkTodos, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 15, -1, 15));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 920, 260));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTodosActionPerformed
     SeleccionarTodos();
    }//GEN-LAST:event_chkTodosActionPerformed
    private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
     dispose();
    }//GEN-LAST:event_bSalirActionPerformed

    private void tRetirosDefectuososMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tRetirosDefectuososMouseClicked
      Seleccionar1();
    }//GEN-LAST:event_tRetirosDefectuososMouseClicked
    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
     Cancelar();
    }//GEN-LAST:event_bCancelarActionPerformed
    private void bImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bImprimirActionPerformed
      Imprimir();
    }//GEN-LAST:event_bImprimirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bImprimir;
    private javax.swing.JButton bSalir;
    private javax.swing.JCheckBox chkTodos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tProductosDefectuosos;
    private javax.swing.JTable tRetirosDefectuosos;
    private javax.swing.JTextField txBuscar;
    // End of variables declaration//GEN-END:variables
}
