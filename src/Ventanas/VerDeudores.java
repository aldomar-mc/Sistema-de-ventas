/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * VerDeudores.java
 *
 * Created on 04-feb-2014, 15:07:14
 */
package Ventanas;
import Clases.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

/**** @author Silva ****/
public class VerDeudores extends javax.swing.JInternalFrame {
 //*********************************ATRIBUTOS***********************************
 Controlador control=new Controlador();Mimodelo modelo=new Mimodelo();
 IMPRIMIR imprime= new IMPRIMIR();String codDEu="", CodVent="",CodSerie="",
 dato="";double montoDeuda=0,pagoini=0;
 //*********************************ATRIBUTOS***********************************
 
    //*********************************METODOS**********************************
    public VerDeudores() {
     initComponents();setTitle("Los Deudores");
     this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
     tDeudores.setModel(modelo);
     modelo.setColumnIdentifiers(new String[] {"Orden","cliente","Telefono","Fecha Venta",
     "Fecha Pago","Deuda","Pago Inicial","Comprobante","Numero"});          
     control.setWidthTableColumn(tDeudores,50,0);control.setWidthTableColumn(tDeudores,200,1);           
     control.setWidthTableColumn(tDeudores,80,2,3,4,5,6);
     control.LlenarCombo(cbxTipoPag, "select * from tipopago" ,2);
     control.LlenarCombo(cbxBanco, "select * from banco", 2);
     control.LlenarCombo(cbxTarjeta,"select * from Tarjeta",2);
     MostrarDeudores();bloquear(false);control.forma_table_ver(modelo, tDeudores);
     cbxTipoPag.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {      
       cbxBanco.setSelectedIndex(-1);cbxTarjeta.setSelectedIndex(-1);
       cbxCuenta.setSelectedIndex(-1);txtNumCheque.setText(null);
       txtNumTarjeta.setText(null);
      }
     });
    }
    public void MostrarDeudores(){
     BuscarDeudores();   
    }    
    public void BuscarDeudores(){
     control.Sql="SELECT iddeuda,nomclie,fono, fecvta,fecdeud, montdeu, pgoinici,"
     + "tipcompr,nume  FROM deudores  where   nomclie like'"+
     txtBuscar.getText()+"%' and montdeu<>0;";control.LlenarJTabla(modelo,control.Sql,9);
    }
    public void bloquear(boolean  a){
     txtCliente.setEnabled(a);txtDeuda.setEnabled(a);cbxTipoPag.setEnabled(a);
    }
    public void limpiar(){
     txtCliente.setText("");txtDeuda.setText("");
    }
    public void CrearDetalle(){
     if(cbxTipoPag.getSelectedIndex()>-1){
      if(cbxTipoPag.getSelectedItem().toString().equals("Efectivo")){
       dato="Pago en efectivo";   
      } 
      if(cbxTipoPag.getSelectedItem().toString().equals("Cheque")){
       dato="Pago con Cheque N° "+txtNumCheque.getText()+" del "+
       cbxBanco.getSelectedItem().toString();   
      }
      if(cbxTipoPag.getSelectedItem().toString().equals("Tarjeta")){
       dato="Pago con Tarjeta "+cbxTarjeta.getSelectedItem().toString()+" N° "+
       txtNumTarjeta.getText();   
      }
     }   
    }
    public void CancelarDeuda(){
     if(bPagar.getText().compareTo("Pagar")==0){  
      if(tDeudores.getSelectedRowCount()==1){
       bloquear(true);codDEu=modelo.getValueAt(tDeudores.getSelectedRow(), 0).toString();
       txtCliente.setText(modelo.getValueAt(tDeudores.getSelectedRow(), 1).toString());
       txtDeuda.setText(modelo.getValueAt(tDeudores.getSelectedRow(), 5).toString());
       pagoini=Double.parseDouble(modelo.getValueAt(tDeudores.getSelectedRow(), 6).toString());
       montoDeuda=Double.parseDouble(modelo.getValueAt(tDeudores.getSelectedRow(), 5).toString());
       bPagar.setText("Grabar");bPagar.setMnemonic('g');
       txtDeuda.grabFocus();cbxTipoPag.setSelectedItem("Efectivo");
      }
      else
       JOptionPane.showMessageDialog(null,"Tiene que Selecionar una Deuda!!");      
     }
     else{
      if(txtCliente.getText().trim().length()>0 && txtDeuda.getText().trim().length()>0 ){//  
       double de=Double.parseDouble(txtDeuda.getText());CrearDetalle();   
       if(de<=montoDeuda){
        if(de<montoDeuda){                  
         control.Sql="insert into pagoxdeuda (fecpgxdeu, montpag, idDeuda,detalle)"
           + " values(curdate(),'"+txtDeuda.getText()+"','"+codDEu+"','"+dato+"')";         
         control.CrearRegistro(control.Sql);control.Sql=" update deuda set pgoinici='"
         +(pagoini+de)+"',montdeu='"+((montoDeuda+pagoini)-(pagoini+de))+"' where "
         + "idDeuda='"+codDEu+"';";control.EditarRegistro(control.Sql);cancelar();
         }
         else{          
          control.Sql="insert into pagoxdeuda (fecpgxdeu, montpag, idDeuda,detalle)"
           + " values(curdate(),'"+txtDeuda.getText()+"','"+codDEu+"','"+dato+"')";
          control.CrearRegistro(control.Sql);  
          control.Sql=" update deuda set pgoinici='"+(pagoini+de)+"',montdeu='"+
          ((montoDeuda+pagoini)-(pagoini+de))+"' where idDeuda='"+codDEu+"';";
          control.EditarRegistro(control.Sql);
          JOptionPane.showMessageDialog(null, "Se ha Cancenlado la Deuda completamente");
          cancelar();
         }
        }
        else
         JOptionPane.showMessageDialog(null, "tiene que ingresar un monto menor a la deuda!!");
//       }
      }
     }
    } 
    public void cancelar(){
     control.LimTabla(modelo);MostrarDeudores();limpiar();bloquear(false);
     bPagar.setText("Pagar");bPagar.setMnemonic('p');cbxTipoPag.setSelectedIndex(-1);
     cbxTarjeta.setSelectedIndex(-1);cbxTipoPag.setSelectedIndex(0);
    }
    //*********************************METODOS**********************************
   
    //*********************************EVENTOS***********************************
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDeuda = new javax.swing.JTextField();
        lblNumCheque = new javax.swing.JLabel();
        cbxTipoPag = new javax.swing.JComboBox();
        lblTarjeta = new javax.swing.JLabel();
        cbxTarjeta = new javax.swing.JComboBox();
        cbxBanco = new javax.swing.JComboBox();
        lblBanco = new javax.swing.JLabel();
        cbxCuenta = new javax.swing.JComboBox();
        lblCuenta = new javax.swing.JLabel();
        lblNumTarjeta = new javax.swing.JLabel();
        txtNumTarjeta = new javax.swing.JTextField();
        txtNumCheque = new javax.swing.JTextField();
        lblMonto = new javax.swing.JLabel();
        txtCliente = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        bPagar = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        bSalir = new javax.swing.JButton();
        bSalir1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tDeudores = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Cliente:");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Tipo de Pago:");
        jLabel5.setName("jLabel5"); // NOI18N

        txtDeuda.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDeuda.setText("0.00");
        txtDeuda.setName("txtDeuda"); // NOI18N
        txtDeuda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDeudaKeyTyped(evt);
            }
        });

        lblNumCheque.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblNumCheque.setForeground(new java.awt.Color(0, 51, 102));
        lblNumCheque.setText("N° Cheque:");
        lblNumCheque.setName("lblNumCheque"); // NOI18N

        cbxTipoPag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxTipoPag.setName("cbxTipoPag"); // NOI18N
        cbxTipoPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTipoPagActionPerformed(evt);
            }
        });

        lblTarjeta.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblTarjeta.setForeground(new java.awt.Color(0, 51, 102));
        lblTarjeta.setText("Tarjeta:");
        lblTarjeta.setName("lblTarjeta"); // NOI18N

        cbxTarjeta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxTarjeta.setName("cbxTarjeta"); // NOI18N

        cbxBanco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxBanco.setName("cbxBanco"); // NOI18N

        lblBanco.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblBanco.setForeground(new java.awt.Color(0, 51, 102));
        lblBanco.setText("Banco:");
        lblBanco.setName("lblBanco"); // NOI18N

        cbxCuenta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxCuenta.setName("cbxCuenta"); // NOI18N

        lblCuenta.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblCuenta.setForeground(new java.awt.Color(0, 51, 102));
        lblCuenta.setText("Cuenta:");
        lblCuenta.setName("lblCuenta"); // NOI18N

        lblNumTarjeta.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblNumTarjeta.setForeground(new java.awt.Color(0, 51, 102));
        lblNumTarjeta.setText("N° Tarjeta:");
        lblNumTarjeta.setName("lblNumTarjeta"); // NOI18N

        txtNumTarjeta.setName("txtNumTarjeta");
        txtNumTarjeta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumTarjetaKeyTyped(evt);
            }
        });

        txtNumCheque.setName("txtNumCheque"); // NOI18N
        txtNumCheque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumChequeKeyTyped(evt);
            }
        });

        lblMonto.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lblMonto.setForeground(new java.awt.Color(0, 51, 102));
        lblMonto.setText("Monto:");
        lblMonto.setName("lblMonto"); // NOI18N

        txtCliente.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        txtCliente.setForeground(new java.awt.Color(0, 51, 204));
        txtCliente.setName("txtCliente"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(293, 293, 293)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(266, 266, 266)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(lblMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtDeuda, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblNumTarjeta)
                                    .addComponent(lblNumCheque)
                                    .addComponent(lblCuenta)
                                    .addComponent(lblBanco)
                                    .addComponent(lblTarjeta)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbxTipoPag, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbxBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbxCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtNumTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtNumCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(341, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbxTipoPag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTarjeta)
                    .addComponent(cbxTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBanco)
                    .addComponent(cbxBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCuenta)
                    .addComponent(cbxCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumCheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNumCheque))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNumTarjeta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDeuda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMonto))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 990, 150));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bPagar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bPagar.setForeground(new java.awt.Color(0, 51, 102));
        bPagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        bPagar.setMnemonic('p');
        bPagar.setText("Pagar");
        bPagar.setMargin(new java.awt.Insets(2, 1, 2, 14));
        bPagar.setName("bPagar"); // NOI18N
        bPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPagarActionPerformed(evt);
            }
        });
        jPanel2.add(bPagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(207, 15, 110, 40));

        bCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCancelar.setForeground(new java.awt.Color(0, 51, 102));
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setMnemonic('a');
        bCancelar.setText("Cancelar ");
        bCancelar.setMargin(new java.awt.Insets(2, 1, 2, 14));
        bCancelar.setName("bCancelar"); // NOI18N
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(497, 15, 110, 40));

        bSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bSalir.setForeground(new java.awt.Color(0, 51, 102));
        bSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir.setMnemonic('s');
        bSalir.setText("Salir");
        bSalir.setMargin(new java.awt.Insets(2, 1, 2, 14));
        bSalir.setName("bSalir"); // NOI18N
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });
        jPanel2.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(634, 15, 110, 40));

        bSalir1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bSalir1.setForeground(new java.awt.Color(0, 51, 102));
        bSalir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Print.png"))); // NOI18N
        bSalir1.setMnemonic('i');
        bSalir1.setText("Imprimir");
        bSalir1.setMargin(new java.awt.Insets(2, 1, 2, 14));
        bSalir1.setName("bSalir1"); // NOI18N
        bSalir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalir1ActionPerformed(evt);
            }
        });
        jPanel2.add(bSalir1, new org.netbeans.lib.awtextra.AbsoluteConstraints(355, 15, 110, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 990, 70));

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

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 960, 310));

        txtBuscar.setName("txtBuscar"); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel3.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 312, -1));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Buscar");
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 990, 360));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
       MostrarDeudores();
    }//GEN-LAST:event_txtBuscarKeyReleased
    private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
        dispose();
    }//GEN-LAST:event_bSalirActionPerformed
    private void bPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPagarActionPerformed
     CancelarDeuda();
    }//GEN-LAST:event_bPagarActionPerformed
    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
      // cancelar();
      CrearDetalle();
      JOptionPane.showMessageDialog(rootPane, dato);
    }//GEN-LAST:event_bCancelarActionPerformed
    private void tDeudoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tDeudoresMouseClicked
     if(evt.getClickCount()==2){
      imprime.ImprimirusConFechas("rpt_Pagos.jasper", modelo.getValueAt(tDeudores.getSelectedRow(), 0).toString());
     }        
    }//GEN-LAST:event_tDeudoresMouseClicked
    private void bSalir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalir1ActionPerformed
     imprime.Impresiones("Lista de Deudores", "Deudores");
    }//GEN-LAST:event_bSalir1ActionPerformed
    private void cbxTipoPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTipoPagActionPerformed
     if (cbxTipoPag.getSelectedIndex()==0) {
      cbxTarjeta.setVisible(false);cbxBanco.setVisible(false);
      cbxCuenta.setVisible(false);lblTarjeta.setVisible(false);
      txtNumCheque.setVisible(false);lblNumCheque.setVisible(false);
      lblNumTarjeta.setVisible(false);lblBanco.setVisible(false);
      lblCuenta.setVisible(false);txtNumTarjeta.setVisible(false);           
     }
     else if(cbxTipoPag.getSelectedIndex()==1){
      cbxTarjeta.setVisible(true);cbxBanco.setVisible(false);
      cbxCuenta.setVisible(false);lblTarjeta.setVisible(true);
      lblNumCheque.setVisible(false);lblNumTarjeta.setVisible(true);
      lblBanco.setVisible(false);lblCuenta.setVisible(false);
      txtNumTarjeta.setVisible(true);txtNumCheque.setVisible(false);
     }
     else if(cbxTipoPag.getSelectedIndex()==2){
      cbxTarjeta.setVisible(false);cbxBanco.setVisible(true);
      cbxCuenta.setVisible(false);lblTarjeta.setVisible(false);
      lblNumCheque.setVisible(true);lblNumTarjeta.setVisible(false);
      lblBanco.setVisible(true);lblCuenta.setVisible(false);          
      txtNumTarjeta.setVisible(false);txtNumCheque.setVisible(true);
     }
    }//GEN-LAST:event_cbxTipoPagActionPerformed
    private void txtNumChequeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumChequeKeyTyped
     control.Solonumeros(evt);
    }//GEN-LAST:event_txtNumChequeKeyTyped
    private void txtNumTarjetaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumTarjetaKeyTyped
     control.Solonumeros(evt);
    }//GEN-LAST:event_txtNumTarjetaKeyTyped
    private void txtDeudaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDeudaKeyTyped
     control.decimal(evt,txtDeuda.getText());
    }//GEN-LAST:event_txtDeudaKeyTyped
   //*********************************EVENTOS***********************************
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bPagar;
    private javax.swing.JButton bSalir;
    private javax.swing.JButton bSalir1;
    private javax.swing.JComboBox cbxBanco;
    private javax.swing.JComboBox cbxCuenta;
    private javax.swing.JComboBox cbxTarjeta;
    private javax.swing.JComboBox cbxTipoPag;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBanco;
    private javax.swing.JLabel lblCuenta;
    private javax.swing.JLabel lblMonto;
    private javax.swing.JLabel lblNumCheque;
    private javax.swing.JLabel lblNumTarjeta;
    private javax.swing.JLabel lblTarjeta;
    private javax.swing.JTable tDeudores;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JLabel txtCliente;
    private javax.swing.JTextField txtDeuda;
    private javax.swing.JTextField txtNumCheque;
    private javax.swing.JTextField txtNumTarjeta;
    // End of variables declaration//GEN-END:variables
}
