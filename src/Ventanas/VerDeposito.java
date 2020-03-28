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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

/**
 *
 * @author Silva
 */
public class VerDeposito extends javax.swing.JInternalFrame {
Controlador control=new Controlador();
Mimodelo modelo=new Mimodelo();
IMPRIMIR imprime= new IMPRIMIR();
    /** Creates new form VerDeudores */
    public VerDeposito() {
        initComponents();this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        setTitle("Ver Depositos");
        tDepositos.setModel(modelo);
        modelo.setColumnIdentifiers(new String[] {"idDepósito","Número de Depósito","Fecha de Depósito","Nombre del Proveedor","Número de Cuenta","Nombre de Banco","Fecha de Compra"});
        tDepositos.getColumnModel().getColumn(0).setPreferredWidth(50);
        tDepositos.getColumnModel().getColumn(1).setPreferredWidth(200);
        tDepositos.getColumnModel().getColumn(2).setPreferredWidth(150);
        tDepositos.getColumnModel().getColumn(3).setPreferredWidth(250);
        tDepositos.getColumnModel().getColumn(4).setPreferredWidth(180);
        tDepositos.getColumnModel().getColumn(5).setPreferredWidth(250);
        tDepositos.getColumnModel().getColumn(6).setPreferredWidth(200);
        MostrarDepositos();
    }
    public void MostrarDepositos(){
        BuscarDepositos();
    }
    public void BuscarDepositos(){
        control.Sql="SELECT d.`idDeposito`, d.`numdpo`, d.`fechdep`, p.`nompro`, cb.`numcta`, b.`nombco`, c.`fec`\n" +
"FROM deposito d, compra c, cuentaban cb, banco b, proveedor p\n" +
"WHERE (p.nompro LIKE'%"+txtBuscar.getText()+"%' OR cb.numcta LIKE'%"+txtBuscar.getText()+"%') AND d.`idCompra`=c.`idCompra` AND d.`idCuentaban`=cb.`idCuentaban` AND b.`idBanco`=cb.`idBanco` AND p.`idProveedor`=c.`idProveedor` GROUP BY p.nompro, d.idDeposito";
        control.LlenarJTabla(modelo, control.Sql, 7);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tDepositos = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setClosable(true);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setName("jPanel3"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tDepositos.setForeground(new java.awt.Color(0, 51, 102));
        tDepositos.setModel(new javax.swing.table.DefaultTableModel(
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
        tDepositos.setName("tDepositos"); // NOI18N
        tDepositos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tDepositosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tDepositos);

        txtBuscar.setName("txtBuscar"); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Buscar");
        jLabel3.setName("jLabel3"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 797, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 371, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tDepositosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tDepositosMouseClicked

    }//GEN-LAST:event_tDepositosMouseClicked

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        MostrarDepositos();
    }//GEN-LAST:event_txtBuscarKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tDepositos;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
