package Ventanas;

import java.util.Date;
import Clases.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class GenerarGuiaDeRemision extends javax.swing.JInternalFrame {

    Controlador control = new Controlador();
    Mimodelo modelo = new Mimodelo();
    Mimodelo modelo1 = new Mimodelo();
    IMPRIMIR imprime = new IMPRIMIR();
    int contadopr = 0, controladorModo = 0, controlguia = 0;
    String[] dat = new String[5];
    String codguiarem = "", cliente = "",tipoclien="";

    /**
     * Creates new form Rpt_Ventas
     */
    public GenerarGuiaDeRemision() {
        initComponents();
        setTitle("Generar Guia de Remision");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tbVentas.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"Item", "Cliente", "Numero", "Fec. Venta", "Vendedor"});
        tbVentas.getColumnModel().getColumn(0).setPreferredWidth(80);
        tbVentas.getColumnModel().getColumn(1).setPreferredWidth(300);
        tbVentas.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbVentas.getColumnModel().getColumn(3).setPreferredWidth(100);
        tbVentas.getColumnModel().getColumn(4).setPreferredWidth(300);
        tbVentasSeleccionadas.setModel(modelo1);
        modelo1.setColumnIdentifiers(new String[]{"Item", "Cliente", "Numero", "Fec. Venta", "Vendedor"});
        tbVentasSeleccionadas.getColumnModel().getColumn(0).setPreferredWidth(80);
        tbVentasSeleccionadas.getColumnModel().getColumn(1).setPreferredWidth(300);
        tbVentasSeleccionadas.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbVentasSeleccionadas.getColumnModel().getColumn(3).setPreferredWidth(100);
        tbVentasSeleccionadas.getColumnModel().getColumn(4).setPreferredWidth(300);
//        fechaFin.setDate(new Date());
  //      fechaIni.setDate(new Date());
    //    control.LlenarCombo(cbusuario, "SELECT nomusr FROM vendedores v;", 1);
        MostrarCliente();
//        LlenarModo();
    }

//    public void LlenarModo() {
//        cbModo.addItem("Contado");
//        cbModo.addItem("Credito");
//        cbModo.setSelectedIndex(-1);
//    }

    public void MostrarCliente() {
        BuscarCliente();
    }

    public void BuscarCliente() {
        control.Sql = "SELECT idventa, nomclie, nume,fecvta,nom FROM ver_ventas_para_guia  where nomclie like '"+txtBuscar.getText()+"%' or nume like '"+txtBuscar.getText()+"%' order by idventa asc;";
        control.LlenarJTabla(modelo, control.Sql, 5);

        //    JOptionPane.showMessageDialog(null,control.Sql);
    }

//    public void BuscarPorUsuario() {
//        if (cbusuario.getSelectedItem().toString().trim().length() > 0) {
//            if (cbModo.getSelectedIndex() > -1) {
//                control.Sql = "SELECT * FROM ver_ventas_para_guia where fecvta>='" + control.Formato_Amd(fechaIni.getDate()) + "' and fecvta<='" + fechaFin.getDateFormatString() + "' and nomusr='" + cbusuario.getSelectedItem() + "' and moda='" + cbModo.getSelectedItem().toString() + "' order by idventa asc;";
//                control.LlenarJTabla(modelo, control.Sql, 5);
//            } else {
//                control.Sql = "SELECT * FROM ver_ventas_para_guia where fecvta>='" + control.Formato_Amd(fechaIni.getDate()) + "' and fecvta<='" + fechaFin.getDateFormatString() + "' and nomusr='" + cbusuario.getSelectedItem() + "' order by idventa asc;";
//                control.LlenarJTabla(modelo, control.Sql, 5);
//            }
//        }
//
//    }

    public void ImprimirVentas() {
        if(tbVentasSeleccionadas.getRowCount()>0){
            control.Sql="select idcomprobantes from guia_remision where idguia_remision='"+codguiarem+"'";
            String gia=control.DevolverRegistroDto(control.Sql,1);
            control.Sql="update comprobantes set esta='Emitido' where idcomprobantes='"+gia+"';";
            control.EditarRegistro(control.Sql);
            control.Sql="SELECT tipo FROM cliente where nomclie='"+cliente+"';";
            String d=control.DevolverRegistroDto(control.Sql, 1);
            if(d.compareTo("Juridico")==0){
                imprime.ImprimirusConFechas("GuiaRemision.jasper", codguiarem);
            }else{
                imprime.ImprimirusConFechas("GuiaRemisionNatural.jasper", codguiarem);
            }
            cancelar();
        }
    }

//    public void BuscarModos() {
//        if (cbusuario.getSelectedIndex() >= 0) {
//            control.Sql = "SELECT * FROM ver_ventas_para_guia where fecvta>='" + control.Formato_Amd(fechaIni.getDate()) + "' and fecvta<='" + control.Formato_Amd(fechaFin.getDate()) + "' and nomusr='" + cbusuario.getSelectedItem().toString() + "' and moda='" + cbModo.getSelectedItem().toString() + "' order by idventa asc;";
//            control.LlenarJTabla(modelo, control.Sql, 5);
//        } else {
//            control.Sql = "SELECT * FROM ver_ventas_para_guia where fecvta>='" + control.Formato_Amd(fechaIni.getDate()) + "' and fecvta<='" + control.Formato_Amd(fechaFin.getDate()) + "' and moda='" + cbModo.getSelectedItem().toString() + "' order by idventa asc;";
//            control.LlenarJTabla(modelo, control.Sql, 5);
//        }
//        // System.out.print(control.Sql);
//    }

    public void limipartbparaguia(String cod) {

        control.Sql = "delete from detalleguia where idguia_remision='" + cod + "';";
        control.EliminarRegistro(control.Sql);
        control.Sql = "delete from guia_remision where idguia_remision='" + cod + "';";
        control.EliminarRegistro(control.Sql);

    }
 public void GenerarNuevoComrpobante(){
        
        control.Sql="call  GeneraComprobante('1','Guia de Remision');";
     control.DevolverRegistroDto(control.Sql, 1);//lbSerie.setText(dato);
       //System.out.print(control.Sql);
    }
    public void AgregarParaGuia() {
        if (tbVentas.getSelectedRowCount() == 1) {
            if (controlguia == 0) {
                GenerarNuevoComrpobante();
                control.Sql = "select InsertaGuia();";
                codguiarem = control.DevolverRegistroDto(control.Sql, 1);
                control.Sql = "insert into detalleguia (idguia_remision, idventa) values('" + codguiarem + "','" + tbVentas.getValueAt(tbVentas.getSelectedRow(), 0).toString() + "');";
                control.CrearRegistro(control.Sql);
                dat[0] = tbVentas.getValueAt(tbVentas.getSelectedRow(), 0).toString();
                dat[1] = tbVentas.getValueAt(tbVentas.getSelectedRow(), 1).toString();
                dat[2] = tbVentas.getValueAt(tbVentas.getSelectedRow(), 2).toString();
                dat[3] = tbVentas.getValueAt(tbVentas.getSelectedRow(), 3).toString();
                dat[4] = tbVentas.getValueAt(tbVentas.getSelectedRow(), 4).toString();
                cliente = tbVentas.getValueAt(tbVentas.getSelectedRow(), 1).toString();
                modelo1.addRow(dat);
//                System.out.print(control.Sql);
                controlguia++;
            } else {
                if (tbVentas.getValueAt(tbVentas.getSelectedRow(), 1).toString().compareTo(cliente) == 0) {
                    control.Sql="select count(*) from detalleguia where idguia_remision='"+codguiarem+"' and idventa='"+tbVentas.getValueAt(tbVentas.getSelectedRow(), 0).toString()+"';";
                    String ver= control.DevolverRegistroDto(control.Sql, 1);
                    //System.out.print(control.Sql);
                    if(ver.compareTo("0")==0){
                        control.Sql = "insert into detalleguia (idguia_remision, idventa) values('" + codguiarem + "','" + tbVentas.getValueAt(tbVentas.getSelectedRow(), 0).toString() + "');";
                    control.CrearRegistro(control.Sql);
                    dat[0] = tbVentas.getValueAt(tbVentas.getSelectedRow(), 0).toString();
                    dat[1] = tbVentas.getValueAt(tbVentas.getSelectedRow(), 1).toString();
                    dat[2] = tbVentas.getValueAt(tbVentas.getSelectedRow(), 2).toString();
                    dat[3] = tbVentas.getValueAt(tbVentas.getSelectedRow(), 3).toString();
                    dat[4] = tbVentas.getValueAt(tbVentas.getSelectedRow(), 4).toString();
                    cliente = tbVentas.getValueAt(tbVentas.getSelectedRow(), 1).toString();
                    modelo1.addRow(dat);
                    }else{JOptionPane.showMessageDialog(null, "Ya ingreso esa Factura en la lista!!");}
                    
                    //System.out.print("anterior " + cliente);
                    
                } else {
                    //System.out.print("Nuevo ");
                    if (JOptionPane.showConfirmDialog(null, "Esta Cambiando de Cliente \n Deseea Continuar!!", "", JOptionPane.YES_NO_OPTION) == 0) {
                        limipartbparaguia(codguiarem);
                        control.LimTabla(modelo1);
                        control.Sql = "select InsertaGuia();";
                        codguiarem = control.DevolverRegistroDto(control.Sql, 1);
                        control.Sql = "insert into detalleguia (idguia_remision, idventa) values('" + codguiarem + "','" + tbVentas.getValueAt(tbVentas.getSelectedRow(), 0).toString() + "');";
                        control.CrearRegistro(control.Sql);
                        dat[0] = tbVentas.getValueAt(tbVentas.getSelectedRow(), 0).toString();
                        dat[1] = tbVentas.getValueAt(tbVentas.getSelectedRow(), 1).toString();
                        dat[2] = tbVentas.getValueAt(tbVentas.getSelectedRow(), 2).toString();
                        dat[3] = tbVentas.getValueAt(tbVentas.getSelectedRow(), 3).toString();
                        dat[4] = tbVentas.getValueAt(tbVentas.getSelectedRow(), 4).toString();
                        modelo1.addRow(dat);
                        controlguia++;
                        cliente = tbVentas.getValueAt(tbVentas.getSelectedRow(), 1).toString();
                    }
                }
            }
        }
    }

    public void cancelar(){
        txtBuscar.setText("");
txtBuscar.grabFocus();
cliente="";
controlguia=0;
control.LimTabla(modelo1);
MostrarCliente();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbVentas = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbVentasSeleccionadas = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Guia de Remision");
        jLabel1.setName("jLabel1"); // NOI18N

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
        });
        jScrollPane1.setViewportView(tbVentas);

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        jButton1.setText("Generar Guia");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        jButton2.setText("Cancelar");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("Ventas Generales");
        jLabel6.setName("jLabel6"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tbVentasSeleccionadas.setForeground(new java.awt.Color(0, 51, 102));
        tbVentasSeleccionadas.setModel(new javax.swing.table.DefaultTableModel(
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
        tbVentasSeleccionadas.setName("tbVentasSeleccionadas"); // NOI18N
        tbVentasSeleccionadas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbVentasSeleccionadasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbVentasSeleccionadas);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 102));
        jLabel7.setText("Ventas Seleccionadas para Generar Guia");
        jLabel7.setName("jLabel7"); // NOI18N

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 51, 102));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton3.setText("Salir");
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setText("Buscar Ventas");
        jLabel8.setName("jLabel8"); // NOI18N

        txtBuscar.setName("txtBuscar"); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 752, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 752, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(325, 325, 325)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        
        cancelar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ImprimirVentas();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tbVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbVentasMouseClicked
        if (evt.getClickCount() == 2) {
            //imprime.ImprimirusConFechas("Detalle_venta.jasper", modelo.getValueAt(tbVentas.getSelectedRow(), 0).toString());
            AgregarParaGuia();

//           }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tbVentasMouseClicked

    private void tbVentasSeleccionadasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbVentasSeleccionadasMouseClicked
        if (evt.getClickCount() == 2) {
            if (tbVentasSeleccionadas.getSelectedRowCount() == 1) {
                control.Sql = "delete from detalleguia where idventa='" + tbVentasSeleccionadas.getValueAt(tbVentasSeleccionadas.getSelectedRow(), 0).toString() + "' and idguia_remision='"+codguiarem+"';";
                control.EliminarRegistro(control.Sql);
                modelo1.removeRow(tbVentasSeleccionadas.getSelectedRow());
               // System.out.println(control.Sql);
                if (tbVentasSeleccionadas.getRowCount() == 0) {
                    control.Sql = "delete from guia_remision where idguia_remision='" + codguiarem + "';";
                    control.EliminarRegistro(control.Sql);
                    //System.out.println(control.Sql);
                    codguiarem="";
                    cliente="";
                    controlguia=0;
                    MostrarCliente();
                }
            }
        }
    }//GEN-LAST:event_tbVentasSeleccionadasMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        BuscarCliente();        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbVentas;
    private javax.swing.JTable tbVentasSeleccionadas;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
