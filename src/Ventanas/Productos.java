/* * Productos.java *
 * Created on 27-ene-2014, 8:56:00 */
package Ventanas;

import Clases.Controlador;
import Clases.FormatoTabla;
import Clases.IMPRIMIR;
import Clases.Mimodelo;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Productos extends javax.swing.JInternalFrame {
    //***************************VENTA DE PRODUCTOS***************************

    Mimodelo modelo = new Mimodelo();
    Mimodelo modelo1 = new Mimodelo();
    Controlador control = new Controlador();
    IMPRIMIR imprime = new IMPRIMIR();
    String dd[] = new String[8];
    String idSede = "";
    private String text = "";
    private double valor = 0;
    private DecimalFormat forma = new DecimalFormat("00.00");
    //***************************VENTA DE PRODUCTOS***************************

    /**
     * * Creates new form Productos***
     */
    public Productos() {
        initComponents();
        setTitle("Los Productos");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tbProducto.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"id", "Codigo Barra",
             "Tipo Producto", "Nombre", "Unidad", "Marca", "Stock", "Precio", "Total"});
        tbProducto.getColumnModel().getColumn(0).setMaxWidth(0);
        tbProducto.getColumnModel().getColumn(0).setMinWidth(0);
        tbProducto.getColumnModel().getColumn(0).setPreferredWidth(0);
        tbProducto.getColumnModel().getColumn(1).setPreferredWidth(75);
        tbProducto.getColumnModel().getColumn(2).setPreferredWidth(150);
        tbProducto.getColumnModel().getColumn(3).setPreferredWidth(450);
        tbProducto.getColumnModel().getColumn(4).setPreferredWidth(75);
        tbProducto.getColumnModel().getColumn(5).setPreferredWidth(140);
        tbListaImprimir.setModel(modelo1);
        modelo1.setColumnIdentifiers(new String[]{"id",
            "Codigo Barra", "Tipo Producto", "Nombre", "Unidad", "Marca", "Stock", "Costo"});
        tbListaImprimir.getColumnModel().getColumn(0).setMaxWidth(0);
        tbListaImprimir.getColumnModel().getColumn(0).setMinWidth(0);
        tbListaImprimir.getColumnModel().getColumn(0).setPreferredWidth(0);
        tbListaImprimir.getColumnModel().getColumn(1).setPreferredWidth(60);
        tbListaImprimir.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbListaImprimir.getColumnModel().getColumn(3).setPreferredWidth(250);
        tbListaImprimir.getColumnModel().getColumn(4).setPreferredWidth(50);
        MostrarProductos();
        llenartotal(Controlador.sede);
        eliminartablar();
        FormatoTabla ft = new FormatoTabla(4);
        tbProducto.setDefaultRenderer(Object.class, ft);
        TableRowSorter<TableModel> elorden = new TableRowSorter<TableModel>(modelo);
        tbProducto.setRowSorter(elorden);
        control.forma_table_ver(modelo1, tbListaImprimir);
        jCheckBox1.setVisible(false);
    }

    public void eliminartablar() {
        control.Sql = "delete from imprimirvarios;";
        control.EliminarRegistro(control.Sql);
    }

    public void MostrarProductos() {
        BuscarProductos();
        llenartotal(idSede);
    }

    public void Calcularmontoensoles() {
        double MNT = 0.00;
        for (control.fila = 0; control.fila < modelo1.getRowCount(); control.fila++) {
            MNT = MNT + (Integer.parseInt(tbListaImprimir.getValueAt(control.fila, 6).toString()) * Double.parseDouble(tbListaImprimir.getValueAt(control.fila, 7).toString()));
        }
        jLabel5.setText(Double.toString(MNT));
    }

    public void BuscarProductos() {
        /*control.Sql=" select  p.Catalogoproducto_codctlg,c.codbarra, l.nommod, c.nomctlg,"
    + "un.nomuni, m.nommrc, count(p.Catalogoproducto_codctlg), c.prexmenor,count"
    + "(p.Catalogoproducto_codctlg)*c.prexmenor from producto p , catalogoproducto "
    + "c , modelo l , marca m , sede s , unidad un where p.Catalogoproducto_codctlg "
    + "= c.codctlg and p.idSede = s.idSede and c.idmodelos = l.idmodelos and c.idMarca "
    + "= m.idMarca and c.idunidad=un.idunidad and p.estdo = 'Disponible' "+
    " and (c.nomctlg like '%"+txtBuscar.getText()+"%' or l.nommod like '%" + txtBuscar.getText() 
    + "%'" + " or m.nommrc like '%" + txtBuscar.getText() + "%' or c.codbarra like'%" 
    + txtBuscar.getText() + "%') and s.nomse='"+Controlador.sede+"' " +
    " and not p.idProducto in (select venta_producto.Producto_idProducto AS Producto_idproducto "
    + "from venta_producto) group by s.nomse ,c.codctlg;";    */
        control.Sql = "select cp.codctlg,cp.codbarra,md.nommod,cp.nomctlg,ud.nomuni Unidad,mrc.nommrc Marca,"
                + "count(pd.catalogoproducto_codctlg) Stock,cp.prexmenor,count(pd.catalogoproducto_codctlg)*cp.prexmenor"
                + " from catalogoproducto cp inner join producto pd on pd.catalogoproducto_codctlg=cp.codctlg\n"
                + "inner join marca mrc on mrc.idmarca=cp.idmarca inner join unidad ud on ud.idunidad=cp.idunidad inner join "
                + "modelocatalogo mc on mc.codctlg=cp.codctlg inner join modelo md on md.idmodelos=mc.idmodelos inner join sede s "
                + "on s.idsede=pd.idsede where pd.estdo='Disponible' and (cp.codbarra like '%" + txtBuscar.getText() + "%' or md.nommod like '%"
                + txtBuscar.getText() + "%' or cp.nomctlg " + "like '%" + txtBuscar.getText() + "%' or mrc.nommrc like '%" + txtBuscar.getText()
                + "%')" + "group by cp.codctlg order by 1 desc;";

        control.LlenarJTabla(modelo, control.Sql, 9);
        control.Sql = "SELECT count(*) FROM producto_venta p where (nomctlg like'%"
                + txtBuscar.getText() + "%' or nommrc like '%" + txtBuscar.getText() + "%') and( Sede='"
                + Controlador.sede + "');";
        llenartotal(idSede);
    }

    public void llenartotal(String sed) {
        double precio = 0, sumatotal = 0;
        int cant = 0;
        control.fila = 0;
        while (control.fila < tbProducto.getRowCount()) {
            precio = Double.parseDouble(tbProducto.getValueAt(control.fila, 7).toString());
            cant = Integer.parseInt(tbProducto.getValueAt(control.fila, 6).toString());
            sumatotal = sumatotal + precio * cant;
            control.fila++;
        }
        text = "" + forma.format(sumatotal);
        try {
            Number numero = forma.parse(text);
            valor = numero.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        lbtotla.setText("S/. " + valor);
        if (tbProducto.getRowCount() == 0) {
            lbtotla.setText("S/. 0.00");
        }
    }

    public void PasarProductos() {
        if (tbProducto.getSelectedRowCount() == 1) {
            dd[0] = tbProducto.getValueAt(tbProducto.getSelectedRow(), 0).toString();
            if (veri(dd[0])) {
                dd[1] = tbProducto.getValueAt(tbProducto.getSelectedRow(), 1).toString();
                dd[2] = tbProducto.getValueAt(tbProducto.getSelectedRow(), 2).toString();
                dd[3] = tbProducto.getValueAt(tbProducto.getSelectedRow(), 3).toString();
                dd[4] = tbProducto.getValueAt(tbProducto.getSelectedRow(), 4).toString();
                dd[5] = tbProducto.getValueAt(tbProducto.getSelectedRow(), 5).toString();
                dd[6] = tbProducto.getValueAt(tbProducto.getSelectedRow(), 6).toString();
                dd[7] = tbProducto.getValueAt(tbProducto.getSelectedRow(), 7).toString();
                modelo1.addRow(dd);
                control.Sql = "insert into imprimirvarios ( codctlg)values('" + dd[0] + "');";
                control.CrearRegistro(control.Sql);
            } else {
                JOptionPane.showMessageDialog(null, "Ya Ingreso ese producto!!");
            }
        }
    }

    public boolean veri(String dd) {
        boolean ae = true;
        int c = 0;
        while (c < tbListaImprimir.getRowCount()) {
            if (tbListaImprimir.getValueAt(c, 0).toString().compareTo(dd) == 0) {
                ae = false;
            }
            c++;
        }
        return ae;
    }

    public void CalcularMontos() {
        control.Sql = "SELECT count(p.`Catalogoproducto_codctlg`) as cntd,p.`Catalogoproducto_codctlg` "
                + "as codctlg,concat(mo.`nommod`,' ',c.`nomctlg`)as producto, m.`nommrc` FROM producto p, "
                + "catalogoproducto c, marca m, modelo mo WHERE p.`estdo`='Disponible' AND "
                + "p.`Catalogoproducto_codctlg` = c.`codctlg` AND c.`idModelos` = mo.`idModelos`"
                + "AND c.`idMarca` = m.`idMarca`  GROUP BY p.`Catalogoproducto_codctlg`   order by 1 desc;";
        try {

        } catch (Exception e) {
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbProducto = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbListaImprimir = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lbtotla = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tbProducto.setForeground(new java.awt.Color(0, 51, 102));
        tbProducto.setModel(new javax.swing.table.DefaultTableModel(
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
        tbProducto.setName("tbProducto"); // NOI18N
        tbProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProductoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbProducto);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 81, 1200, 240));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBuscar.setName("txtBuscar"); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 20, 260, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Buscar:");
        jLabel1.setName("jLabel1"); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 23, -1, -1));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, 360, 60));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton1.setMnemonic('s');
        jButton1.setText("Salir");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 115, 40));

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Print.png"))); // NOI18N
        jButton2.setMnemonic('i');
        jButton2.setText("Imprimir");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 110, 40));

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 51, 102));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        jButton3.setMnemonic('a');
        jButton3.setText("Agregar Todos");
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, 40));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/save.png"))); // NOI18N
        jButton5.setText("Parar Inv Inicial");
        jButton5.setName("jButton5"); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 140, 40));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(373, 13, 560, 60));

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tbListaImprimir.setForeground(new java.awt.Color(0, 51, 102));
        tbListaImprimir.setModel(new javax.swing.table.DefaultTableModel(
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
        tbListaImprimir.setName("tbListaImprimir"); // NOI18N
        tbListaImprimir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListaImprimirMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbListaImprimir);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 1210, 160));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Lista de Impresion");
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, -1));

        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 51, 102));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        jButton4.setMnemonic('l');
        jButton4.setText("Limpiar La Lista");
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 340, -1, -1));

        jCheckBox1.setBackground(new java.awt.Color(51, 153, 255));
        jCheckBox1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jCheckBox1.setText("En Almacen");
        jCheckBox1.setName("jCheckBox1"); // NOI18N
        jPanel2.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 30, -1, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("00.00");
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 350, 60, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("Monto S/.");
        jLabel6.setName("jLabel6"); // NOI18N
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 350, 70, -1));

        jButton6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(0, 51, 102));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/list_box.png"))); // NOI18N
        jButton6.setMnemonic('l');
        jButton6.setText("Imprimir kardex producto");
        jButton6.setName("jButton6"); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 340, 200, -1));

        jButton7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(0, 51, 102));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/list_box.png"))); // NOI18N
        jButton7.setMnemonic('l');
        jButton7.setText("Imprimir kardex todo");
        jButton7.setName("jButton7"); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 340, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 1220, 550));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Ver Total en Stock");
        jLabel3.setName("jLabel3"); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, -1, -1));

        lbtotla.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbtotla.setForeground(new java.awt.Color(0, 51, 102));
        lbtotla.setText("        ");
        lbtotla.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbtotla.setName("lbtotla"); // NOI18N
        getContentPane().add(lbtotla, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 10, 130, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed
    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        MostrarProductos();
    }//GEN-LAST:event_txtBuscarKeyReleased
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
     imprime.Impresiones1("El Stock de Productos","Stocc",Controlador.sede); 
     //   imprime.Impresionprueba("Stocc", "Stock de Productos", "sede", Controlador.sede, "bsc", txtBuscar.getText());
    }//GEN-LAST:event_jButton2ActionPerformed
    private void tbProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductoMouseClicked
        if (evt.getClickCount() == 2) {
            PasarProductos();
        }
        Calcularmontoensoles();
    }//GEN-LAST:event_tbProductoMouseClicked
    private void tbListaImprimirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListaImprimirMouseClicked
        if (evt.getClickCount() == 2) {
            if (tbListaImprimir.getSelectedRowCount() == 1) {
                control.Sql = "delete from imprimirvarios where codctlg='" + tbListaImprimir.getValueAt(tbListaImprimir.getSelectedRow(), 0).toString() + "';";
                control.EliminarRegistro(control.Sql);
                modelo1.removeRow(tbListaImprimir.getSelectedRow());
            }
        }
        Calcularmontoensoles();
    }//GEN-LAST:event_tbListaImprimirMouseClicked
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (tbProducto.getRowCount() > 0) {
            if (JOptionPane.showConfirmDialog(null, "Desea Agregar todo el los Datos de la lista ",
                    "", JOptionPane.YES_NO_OPTION) == 0) {
                int asds = 0;
                while (asds < tbProducto.getRowCount()) {
                    dd[0] = tbProducto.getValueAt(asds, 0).toString();
                    if (veri(dd[0])) {
                        dd[1] = tbProducto.getValueAt(asds, 1).toString();
                        dd[2] = tbProducto.getValueAt(asds, 2).toString();
                        dd[3] = tbProducto.getValueAt(asds, 3).toString();
                        dd[4] = tbProducto.getValueAt(asds, 4).toString();
                        dd[5] = tbProducto.getValueAt(asds, 5).toString();
                        dd[6] = tbProducto.getValueAt(asds, 6).toString();
                        dd[7] = tbProducto.getValueAt(asds, 7).toString();
                        modelo1.addRow(dd);
                        control.Sql = "insert into imprimirvarios(codctlg) "
                                + "values('" + dd[0] + "');";
                        control.CrearRegistro(control.Sql);
                    }
                    asds++;
                }
            }
        }
        Calcularmontoensoles();
    }//GEN-LAST:event_jButton3ActionPerformed
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Deseq Quitar todos los Elementos de la la Lista?", "Mensaje del Sistema", JOptionPane.YES_NO_OPTION) == 0) {
            eliminartablar();
            control.LimTabla(modelo1);
        }
        Calcularmontoensoles();
    }//GEN-LAST:event_jButton4ActionPerformed
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String est = "";
        control.Sql = "SELECT estado FROM estado_sinicial";
        est = control.DevolverRegistroDto(control.Sql, 1).toString();
        if (est.equals("1")) {
            if (JOptionPane.showConfirmDialog(null, "¿Desea Detener el Ingreso al Inventario Inicial?",
                    "Mensaje del Sistema", JOptionPane.YES_NO_OPTION) == 0) {
                control.Sql = "UPDATE estado_sinicial SET estado=0";
                control.EditarRegistro(control.Sql);
                JOptionPane.showMessageDialog(null, "Ingreso al Inventario Inicial Detenido Correctamente");
            }
        } else {
            if (JOptionPane.showConfirmDialog(null, "El inventario inicial ya esta detenido "
                    + "¿Desea Iniciarlo Nuevamente?", "Mensaje del Sistema", JOptionPane.YES_NO_OPTION) == 0) {
                control.Sql = "UPDATE estado_sinicial SET estado=1";
                control.EditarRegistro(control.Sql);
                JOptionPane.showMessageDialog(null, "Ingreso al Inventario Inventario Inicial Activo");
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (tbProducto.getSelectedRowCount() == 1) {
            imprime.Impresiones1("Reporte Kardez", "kardex_uno", tbProducto.getValueAt(
                    tbProducto.getSelectedRow(), 0).toString());
        } else {
            JOptionPane.showMessageDialog(null, "Selecione un producto", "System Message", 2);
        }
    }//GEN-LAST:event_jButton6ActionPerformed
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        imprime.Impresiones1("Reporte Kardez", "kardex", Controlador.sede);  // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbtotla;
    private javax.swing.JTable tbListaImprimir;
    private javax.swing.JTable tbProducto;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
