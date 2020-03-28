/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import javax.swing.ImageIcon;
import Clases.*;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class frmBalancearStock extends javax.swing.JInternalFrame {

    Mimodelo modelo = new Mimodelo();
    Controlador control = new Controlador();
    String ID = "", stock = "", cdbarra = "";
    int Cantidad = 0;

    /**
     * * Creates new form frmBalancearStock   *******
     */
    public void Grabar() {
        if (txcantidad.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar la cantidad de productos existentes");
            txcantidad.grabFocus();
        } else {
            Cantidad = Integer.parseInt(txcantidad.getText()) - Integer.parseInt(stock);
            if (Cantidad != 0) {
                if (Cantidad > 0) {  //Faltan ingresar x lo tanto hay que crear 
                    String costo = "", iddoccmp = "";
                    control.Sql = "select * from doc_compra where nume='111111111111'";
                    iddoccmp = control.DevolverRegistroDto(control.Sql, 1);
                    control.Sql = "select * from catalogoproducto where codctlg='" + ID + "'";
                    costo = control.DevolverRegistroDto(control.Sql, 3);
                    for (int i = 1; i <= Cantidad; i++) {
                        control.Sql = "insert into producto(fecingralm,Catalogoproducto_codctlg,codbrr,idDoc_Compra,costo,estdo,precVenta,idSede) values('"
                                + control.Fecha() + "','" + ID + "','" + cdbarra + "','" + iddoccmp + "','" + costo + "','Disponible','" + costo + "','1')";
                        control.CrearRegistro(control.Sql);
                    }
                } else {
                    Cantidad = Cantidad * -1;
                    for (int i = 1; i <= Cantidad; i++) { //Hay que Quitar, editamos
                        control.Sql = "select * from producto where catalogoproducto_codctlg='" + ID + "' and estdo"
                                + "='Disponible' order by 1 desc limit 1";
                        control.Sql = "update producto set estdo=' Nuevo inventario' "
                                + "where idproducto='" + control.DevolverRegistroDto(control.Sql, 1) + "' ";
                        control.EditarRegistro(control.Sql);
                    }
                }
                lblProducto.setText("");
                txcantidad.setText("");
                txtBuscar.setText("");
                MostraProductos();
            }
        }
    }

    public frmBalancearStock() {
        initComponents();
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tProductos.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"ID", "Código", "Producto", "Marca", "Stock"});
        lblProducto.setText("");
        control.setMaxWidthColumnTable(tProductos, 300, 2);
        control.hideTableColumn(tProductos, 0);
        MostraProductos();
    }

    public void MostraProductos() {
        control.Sql = "select cp.codctlg ID,cp.codbarra,concat(md.nommod,' ',cp.nomctlg) producto,mrc.nommrc Marca,0 Stock\n"
                + "from catalogoproducto cp inner join modelocatalogo mc on mc.codctlg=cp.codctlg inner join modelo md\n"
                + "on md.idmodelos=mc.idmodelos inner join marca mrc on mrc.idmarca=cp.idmarca\n"
                + "where cp.codctlg not in (select catalogoproducto_codctlg from producto) and (cp.nomctlg like'%" + txtBuscar.getText() + "%' or md.nommod\n"
                + "like'%" + txtBuscar.getText() + "%' or cp.codbarra like'%" + txtBuscar.getText() + "%' or mrc.nommrc like'%" + txtBuscar.getText() + "%')"
                + "\n" + "union all select cp.codctlg ID,cp.codbarra,concat(md.nommod,' ',cp.nomctlg) producto,mrc.nommrc Marca,\n"
                + "(select count(catalogoproducto_codctlg) from producto where catalogoproducto_codctlg=cp.codctlg\n"
                + "and estdo='Disponible' and idSede='1') Stock "
                + "from catalogoproducto cp inner join modelocatalogo mc on mc.codctlg=cp.codctlg inner join modelo md\n"
                + "on md.idmodelos=mc.idmodelos inner join marca mrc on mrc.idmarca=cp.idmarca\n"
                + "where cp.codctlg  in (select catalogoproducto_codctlg from producto) and (cp.nomctlg like'%" + txtBuscar.getText() + "%' or md.nommod\n"
                + "like'%" + txtBuscar.getText() + "%' or cp.codbarra like'%" + txtBuscar.getText() + "%' or mrc.nommrc like'%" + txtBuscar.getText() + "%') "
                + "order by 4 asc;";
        control.LlenarJTabla(modelo, control.Sql, 5);
    }

    public void Imprimir() {
        if (tProductos.getRowCount() > 0) {
            control.impresor.Imprimircon1Parametros("Reporte de Stock de Productos", "reporStockProductos", "buscar", txtBuscar.getText());
        } else {
            JOptionPane.showMessageDialog(null, "No hay datos para imprimir");
            txtBuscar.setText("");
            txtBuscar.grabFocus();
        }
    }

    public void Seleccionar() {
        control.fila = tProductos.getSelectedRow();
        if (control.fila > -1) {
            ID = tProductos.getValueAt(control.fila, 0).toString();
            lblProducto.setText(tProductos.getValueAt(control.fila, 2).toString() + " " + tProductos.getValueAt(
                    control.fila, 3).toString());
            cdbarra = tProductos.getValueAt(control.fila, 1).toString();
            stock = tProductos.getValueAt(control.fila, 4).toString();
            txcantidad.grabFocus();
        }
    }

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
        tProductos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        lblProducto = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txcantidad = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        bImprimir = new javax.swing.JButton();
        bSalir = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        bGrabar = new javax.swing.JButton();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        tProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tProductos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 940, 360));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setText("Buscar");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 19, 390, -1));

        lblProducto.setText("jLabel2");
        jPanel1.add(lblProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, 330, -1));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setText("Cantidad");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 20, -1, -1));

        txcantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txcantidadKeyTyped(evt);
            }
        });
        jPanel1.add(txcantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 16, 60, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 955, 410));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bImprimir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bImprimir.setForeground(new java.awt.Color(0, 0, 102));
        bImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Print.png"))); // NOI18N
        bImprimir.setMnemonic('i');
        bImprimir.setText("Imprimir");
        bImprimir.setMargin(new java.awt.Insets(2, 1, 2, 14));
        bImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bImprimirActionPerformed(evt);
            }
        });
        jPanel2.add(bImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 115, 45));

        bSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bSalir.setForeground(new java.awt.Color(0, 0, 102));
        bSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir.setMnemonic('s');
        bSalir.setText("Salir");
        bSalir.setMargin(new java.awt.Insets(2, 1, 2, 14));
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });
        jPanel2.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(834, 10, 115, 45));

        bCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCancelar.setForeground(new java.awt.Color(0, 0, 102));
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setMnemonic('c');
        bCancelar.setText("Cancelar");
        bCancelar.setMargin(new java.awt.Insets(2, 1, 2, 14));
        jPanel2.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 10, 115, 45));

        bGrabar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bGrabar.setForeground(new java.awt.Color(0, 0, 102));
        bGrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/save.png"))); // NOI18N
        bGrabar.setMnemonic('g');
        bGrabar.setText("Grabar");
        bGrabar.setMargin(new java.awt.Insets(2, 1, 2, 14));
        bGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGrabarActionPerformed(evt);
            }
        });
        jPanel2.add(bGrabar, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 115, 45));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 955, 65));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        MostraProductos();
        if (tProductos.getRowCount() == 0) {
            lblProducto.setText("");
        }
        txcantidad.setText(null);
    }//GEN-LAST:event_txtBuscarKeyReleased
    private void txcantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txcantidadKeyTyped
        control.Solonumeros(evt);
    }//GEN-LAST:event_txcantidadKeyTyped
    private void tProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProductosMouseClicked
        Seleccionar();
    }//GEN-LAST:event_tProductosMouseClicked
    private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
        dispose();
    }//GEN-LAST:event_bSalirActionPerformed
    private void bImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bImprimirActionPerformed
        Imprimir();
    }//GEN-LAST:event_bImprimirActionPerformed
    private void bGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGrabarActionPerformed
        Grabar();
    }//GEN-LAST:event_bGrabarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bGrabar;
    private javax.swing.JButton bImprimir;
    private javax.swing.JButton bSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JTable tProductos;
    private javax.swing.JTextField txcantidad;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
