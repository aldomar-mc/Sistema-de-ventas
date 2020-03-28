
package Ventanas;
/**** @author Ing. Miguel Angel Silva Zapata.   **********/
import javax.swing.*;import Clases.*;
public class igv extends javax.swing.JInternalFrame {
    /*************************Atributos**********************/ 
    Mimodelo modelo=new Mimodelo();
    Controlador control = new Controlador();
    String codTipo="";
    /*************************Atributos**********************/ 
    public igv() {
     initComponents();setTitle("Valor de IGV");
     this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
     tTipocomprobante.setModel(modelo);modelo.setColumnIdentifiers(new String[] {"Código","Valor"});
     tTipocomprobante.getColumnModel().getColumn(1).setPreferredWidth(250);
     MostrarCliente();
     txTipocomprobante.setEnabled(false);
     //txtCantiDig.setEnabled(false);
     
    }    
     public void MostrarCliente(){
     BuscarCliente();
    }    
    public void BuscarCliente(){
     control.Sql="SELECT idigv,ver FROM igv "; 
     
     control.LlenarJTabla(modelo,control.Sql,2); 
     
 //    JOptionPane.showMessageDialog(null,control.Sql);
    }
    public void Cancelar(){
        txTipocomprobante.setEnabled(false);
        txTipocomprobante.setText("");
        bModificar.setEnabled(true);
       // bCrear.setText("Crear");
        bModificar.setText("Editar");
    }


    public void EditarTipoComprobante(){
        if(bModificar.getText().compareTo("Editar")==0){
            if(tTipocomprobante.getSelectedRowCount()>0){
                txTipocomprobante.setEnabled(true);
              //  txtCantiDig.setEnabled(true);
                codTipo=modelo.getValueAt(tTipocomprobante.getSelectedRow(), 0).toString();
                txTipocomprobante.setText(modelo.getValueAt(tTipocomprobante.getSelectedRow(), 1).toString());
               // txtCantiDig.setText(modelo.getValueAt(tTipocomprobante.getSelectedRow(), 2).toString());
                bModificar.setText("Grabar");
                
             //   bEliminar.setEnabled(false);
             //   bCrear.setEnabled(false);
            }else{
                JOptionPane.showMessageDialog(null, "Selecione el Igv");
            }
        }
        else{
            if(txTipocomprobante.getText().trim().length()>0){
                int ig=Integer.parseInt(txTipocomprobante.getText().toString());
                double val=Double.parseDouble("0."+ig);
                System.out.println(ig/100);
                control.Sql="update igv set valor='"+val+"', ver='"+ig+"'  where idigv='"+codTipo+"';";
                System.out.println(control.Sql);
                control.EditarRegistro(control.Sql );
                control.LimTabla(modelo);
                MostrarCliente();
                txTipocomprobante.setText("");
                txTipocomprobante.setEnabled(false);
                
                bModificar.setText("Editar");   
            }else{
                JOptionPane.showMessageDialog(null, "Ingrese Igv");
            }
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tTipocomprobante = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        bSalir = new javax.swing.JButton();
        bModificar = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txTipocomprobante = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tTipocomprobante.setForeground(new java.awt.Color(0, 51, 102));
        tTipocomprobante.setModel(new javax.swing.table.DefaultTableModel(
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
        tTipocomprobante.setName("tTipocomprobante"); // NOI18N
        tTipocomprobante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tTipocomprobanteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tTipocomprobante);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 370, 110));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        bSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bSalir.setForeground(new java.awt.Color(0, 51, 102));
        bSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir.setText("Salir");
        bSalir.setName("bSalir"); // NOI18N
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });

        bModificar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bModificar.setForeground(new java.awt.Color(0, 51, 102));
        bModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Edit.png"))); // NOI18N
        bModificar.setText("Editar");
        bModificar.setName("bModificar"); // NOI18N
        bModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bModificarActionPerformed(evt);
            }
        });

        bCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCancelar.setForeground(new java.awt.Color(0, 51, 102));
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setText("Cancelar");
        bCancelar.setName("bCancelar"); // NOI18N
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("%");
        jLabel3.setName("jLabel3"); // NOI18N

        txTipocomprobante.setName("txTipocomprobante"); // NOI18N

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Valor de IGV");
        jLabel4.setName("jLabel4"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(bModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txTipocomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txTipocomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bModificar)
                    .addComponent(bCancelar)
                    .addComponent(bSalir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, 90));

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
 dispose();
}//GEN-LAST:event_bSalirActionPerformed

private void bModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModificarActionPerformed
    EditarTipoComprobante();
}//GEN-LAST:event_bModificarActionPerformed

private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
    Cancelar();
}//GEN-LAST:event_bCancelarActionPerformed

    private void tTipocomprobanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tTipocomprobanteMouseClicked
       if(evt.getClickCount()==2){
           EditarTipoComprobante();
       }
    }//GEN-LAST:event_tTipocomprobanteMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bModificar;
    private javax.swing.JButton bSalir;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tTipocomprobante;
    private javax.swing.JTextField txTipocomprobante;
    // End of variables declaration//GEN-END:variables
}
