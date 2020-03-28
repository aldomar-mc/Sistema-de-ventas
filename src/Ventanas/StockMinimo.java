/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

/**
 *
 * @author Miguel Silva
 */
import Clases.*;import java.util.*;import javax.swing.*;
public class StockMinimo extends javax.swing.JInternalFrame {
    private Mimodelo modelo = new Mimodelo();private Controlador control = new Controlador();
    
    public StockMinimo() {
        initComponents();setTitle("Lista de Productos con stock mínimo");
        modelo.setColumnIdentifiers(  new String[]{"ID", "Código", "Producto","StockMin","Stock"});        
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tProductos.setModel(modelo);control.setMaxWidthColumnTable(tProductos, 350, 2);
        control.hideTableColumn(tProductos,0);cargarProductos();
        FormatoTabla ft= new FormatoTabla(2);tProductos.setDefaultRenderer(Object.class, ft);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        bSalir = new javax.swing.JButton();
        bImprimir = new javax.swing.JButton();
        bActualizar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tProductos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txBuscar = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bSalir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        bSalir.setForeground(new java.awt.Color(0, 51, 102));
        bSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir.setMnemonic('s');
        bSalir.setText("Salir");
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });
        jPanel1.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, 170, 40));

        bImprimir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        bImprimir.setForeground(new java.awt.Color(0, 51, 102));
        bImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Print.png"))); // NOI18N
        bImprimir.setMnemonic('i');
        bImprimir.setText("Imprimir");
        bImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bImprimirActionPerformed(evt);
            }
        });
        jPanel1.add(bImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 170, 40));

        bActualizar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        bActualizar.setForeground(new java.awt.Color(0, 51, 102));
        bActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/emotion_happy.png"))); // NOI18N
        bActualizar.setMnemonic('a');
        bActualizar.setText("Actualizar");
        bActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(bActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 170, 40));

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
        jScrollPane1.setViewportView(tProductos);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 153));
        jLabel1.setText("Buscar");

        txBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txBuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txBuscar))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
        dispose();
    }//GEN-LAST:event_bSalirActionPerformed
    private void bImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bImprimirActionPerformed
      if(tProductos.getRowCount()>0)  
      control.impresor.Imprimircon1Parametros("Productos con Stock Mínimo","productosStockMinimo",
      "filtro", txBuscar.getText());
      else
        JOptionPane.showMessageDialog(this,"No hay Productos");
    }//GEN-LAST:event_bImprimirActionPerformed

    private void bActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bActualizarActionPerformed
        cargarProductos();
    }//GEN-LAST:event_bActualizarActionPerformed

   private void txBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txBuscarKeyReleased
        cargarProductos();
   }//GEN-LAST:event_txBuscarKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bActualizar;
    private javax.swing.JButton bImprimir;
    private javax.swing.JButton bSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tProductos;
    private javax.swing.JTextField txBuscar;
    // End of variables declaration//GEN-END:variables

    private void cargarProductos() {
       /*control.Sql="select cp.codctlg,cp.codbarra,concat(md.nommod,' ',cp.nomctlg) Producto,cp.stockmin,count("
       + "p.idproducto) Stock from catalogoproducto cp inner join modelocatalogo mc on cp.codctlg=mc.codctlg "
       + "inner join modelo md on md.idmodelos=mc.idmodelos inner join producto p on p.catalogoproducto_codctlg"
       + "=cp.codctlg where p.estdo='Disponible' and (cp.nomctlg like'%"+txBuscar.getText()+"%' or md.nommod "
       + "like'%"+txBuscar.getText()+"%' or cp.codbarra like'%"+txBuscar.getText()+"%') group by cp.codctlg,"
       + "cp.nomctlg having count(p.idproducto)<=cp.stockmin and cp.stockmin>0  order by 1;"; */
       
       control.Sql="select cp.codctlg,cp.codbarra,concat(md.nommod,' ',cp.nomctlg) Producto,cp.stockmin,count(p.idproducto) Stock " +
       "from catalogoproducto cp inner join modelocatalogo mc on cp.codctlg=mc.codctlg " +
       "inner join modelo md on md.idmodelos=mc.idmodelos inner join producto p on p.catalogoproducto_codctlg=cp.codctlg " +
       "where p.estdo='Disponible' and (cp.nomctlg like'%%' or md.nommod like'%"+txBuscar.getText()+"%' or cp.codbarra like'%"+
        txBuscar.getText()+"%') group by cp.codctlg,cp.nomctlg having count(p.idproducto)<=cp.stockmin and cp.stockmin>0 " +
       "union all " +
       "select cp.codctlg ID,cp.codbarra,concat(md.nommod,' ',cp.nomctlg) producto,cp.stockmin,0 Stock " +
       "from catalogoproducto cp inner join modelocatalogo mc on mc.codctlg=cp.codctlg inner join modelo "
       + "md on md.idmodelos=mc.idmodelos where cp.codctlg not in (select catalogoproducto_codctlg "
       + "from producto) and (cp.nomctlg like'%"+txBuscar.getText()+"%' or md.nommod like'%"
       +txBuscar.getText()+"%' or cp.codbarra like'%"+txBuscar.getText()+"%') order by 3;";
       
       //System.out.println(control.Sql);
       control.LlenarJTabla(modelo,control.Sql, 5);
       
    }
}
