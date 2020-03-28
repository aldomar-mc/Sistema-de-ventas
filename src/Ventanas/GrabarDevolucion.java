
package Ventanas;

import Clases.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
public class GrabarDevolucion extends javax.swing.JInternalFrame {

Controlador control=new Controlador();
Mimodelo modelo= new Mimodelo();
Mimodelo modelo1= new Mimodelo();
Codificador codi= new Codificador();
String codigoprestamo="",CodProductoPres="",TipoGarantia="",codigobarras="";
int tipoper=0;

    public GrabarDevolucion() {
        initComponents();setTitle("Grabar devolucion de Producto");
     this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
     tbPrestados.setModel(modelo);
     modelo.setColumnIdentifiers(new String[] {"Código","Prestador","Fec. Prestamo","Fec. Devolucion","Situacion"});
     tbProductosPrestados.setModel(modelo1);
     modelo1.setColumnIdentifiers(new String[] {"Código","Producto","Codigo","serie","Precio"});
     tbProductosPrestados.getColumnModel().getColumn(0).setPreferredWidth(50);
     tbProductosPrestados.getColumnModel().getColumn(1).setPreferredWidth(250);
     tbProductosPrestados.getColumnModel().getColumn(2).setPreferredWidth(100);
     tbProductosPrestados.getColumnModel().getColumn(3).setPreferredWidth(100);
     tbProductosPrestados.getColumnModel().getColumn(4).setPreferredWidth(50);
     txtCodigo.setEnabled(false);
     txtSerie.setEnabled(false);
     txtPrecio.setEnabled(false );
     txtCantidad .setEnabled(false);
     rbSiCodBrr.setEnabled(false);
    rbNoCodBrr.setEnabled(false);
    rbAños.setEnabled(false);
    rbMeses.setEnabled(false);
    cbProducto.setEnabled(false);
     MostrarCliente();
     rbAux.setVisible(false);
     rbAuxCodigo.setVisible(false);
    }
public void Cancelar(){
    cbProducto.removeAllItems();
    cbProducto.setEnabled(false);
    MostrarCliente();
    control.LimTabla(modelo1);
    lbPrestador.setText(" ");
//    lbProducto.setText(" ");
    txtCodigo.setText("");
    txtCantidad.setText("");
    txtPrecio.setText("");
    txtSerie.setText("");
    rbAños.setEnabled(false);
    rbMeses.setEnabled(false);
    txtCodigo.setEnabled(false);
    txtPrecio.setEnabled(false);
    txtSerie.setEnabled(false);
    txtCantidad.setEnabled(false);
    rbSiCodBrr.setEnabled(false);
    rbNoCodBrr.setEnabled(false);
    //rbAuxCodigo.setEnabled(false);
    txtBuscarPrestamso.setText("");
    txtBuscarPrestamso.grabFocus();
    rbAux.setSelected(true);
    rbAuxCodigo.setSelected(true);
}
    public void MostrarCliente(){
        
     BuscarCliente();   
    }    
    public void BuscarCliente(){
     control.Sql="SELECT idprestamo, nomprest,fecini, fecdev, sit FROM prestados  where sit='Activo' and tipopres=0 and nomprest like'"+
     txtBuscarPrestamso.getText()+"%';";
     control.LlenarJTabla(modelo,control.Sql,5); 
     
 //    JOptionPane.showMessageDialog(null,control.Sql);
    }
    public void SeleccionarPrestamo(){
//        tbPrestados.getSelectedRow();
     lbPrestador.setText(modelo.getValueAt(tbPrestados.getSelectedRow(), 1).toString());
     codigoprestamo=modelo.getValueAt(tbPrestados.getSelectedRow(), 0).toString();
     control.Sql="SELECT pp.idprestamoproducto, pp.nomctlg, pp.codbrr, pp.seri, pp.precventa FROM productos_prestados pp,producto p where pp.idproducto=p.idproducto and  pp.idprestamo='"+codigoprestamo+"' and p.estdo='Prestado';";
     control.LlenarJTabla(modelo1,control.Sql,5);   
    //System.out.print(control.Sql);
    }
    public void llenarCBo(String dats){
    control.Sql="select nomlna from linea_producto where nomctlg='"+dats+"';";
    String d=control.DevolverRegistroDto(control.Sql, 1);
    control.Sql="select nomctlg from linea_producto where nomlna='"+d+"';";
    control.LlenarCombo(cbProducto, control.Sql, 1);
    cbProducto.setSelectedItem(dats);
    }
    public void SeleccionarProducto(){
        CodProductoPres=modelo1.getValueAt(tbProductosPrestados.getSelectedRow(), 0).toString();
        
        //lbProducto.setText();
        llenarCBo(modelo1.getValueAt(tbProductosPrestados.getSelectedRow(), 1).toString());
        codigobarras=modelo1.getValueAt(tbProductosPrestados.getSelectedRow(), 2).toString();
        txtCantidad.setText("");
        txtCodigo.setText("");
        txtPrecio.setText("");
        txtSerie.setText("");
        rbAux.setSelected(true);
        rbAuxCodigo.setSelected(true);
        txtCodigo.setEnabled(true);
        txtSerie.setEnabled(true);
        txtPrecio.setEnabled(true);
        txtCantidad.setEnabled(true);
        rbAños.setEnabled(true);
        rbMeses.setEnabled(true);
        rbNoCodBrr.setEnabled(true);
        rbSiCodBrr.setEnabled(true);
        rbAuxCodigo.setEnabled(true);
        cbProducto.setEnabled(true);
        //txtCodigo.grabFocus();
        rbMeses.grabFocus();
    }
    
    public void GrabarDEvolucionPrestamo(){
        if(veridicar()){
            control.Sql="call agregardcevueltos('"+cbProducto.getSelectedItem().toString()+"','"+txtCodigo.getText()+"','"+txtPrecio.getText()+"','"+tipoper+"','"+txtCantidad.getText()+"','"+codigobarras+"','"+txtSerie.getText()+"');";
            control.CrearRegistro(control.Sql);
            //System.out.print(control.Sql);
            control.Sql="SELECT pp.idprestamoproducto, pp.nomctlg, pp.codbrr, pp.seri, pp.precventa FROM productos_prestados pp,producto p where pp.idproducto=p.idproducto and  pp.idprestamo='"+codigoprestamo+"' and p.estdo='Prestado';";
            control.LlenarJTabla(modelo1,control.Sql,5); 
            limpiar();
            if(tbProductosPrestados.getRowCount()==0){
                control.Sql="update externo set sit='Cancelada' where idprestamo='"+codigoprestamo+"';";
                //System.out.print(control.Sql);
                control.EditarRegistro(control.Sql);
                MostrarCliente();
                JOptionPane.showMessageDialog(null, "Se ha Cancelado el Prestamo en su totalidad!!");
            }
        }
    }
    public void limpiar(){
        txtCantidad.setText("");
        txtCodigo.setText("");
        txtPrecio.setText("");
        txtSerie.setText("");
       // lbProducto.setText(" ");
        rbAux.setSelected(true);
        rbAuxCodigo.setSelected(true);
        rbMeses.grabFocus();
        cbProducto.removeAllItems();
        cbProducto.setEnabled(false);
        txtCantidad.setEnabled(false);
        rbAños.setEnabled(false);
        rbMeses.setEnabled(false);
        rbNoCodBrr.setEnabled(false);
        rbSiCodBrr.setEnabled(false);
        txtCodigo.setEnabled(false);
        txtPrecio.setEnabled(false);
        txtSerie.setEnabled(false);
    }
    public boolean veridicar(){
        boolean a=false;
        if(txtCodigo.getText().trim().length()>0 && txtPrecio.getText().trim().length()>0 && tipoper>0 && txtCantidad.getText().trim().length()>0){
            a=true;
        }
        return a;
    }
    public String CrearCodigo(){
        int coun=1;
        String cod ="",codpro="",codmar="",ini="",fech="",codn="";
            //ini=codi.CapturaIniciales(cbProducto.getSelectedItem().toString());
            control.Sql="select ltrs from proveedor where idproveedor=0;";
            ini=control.DevolverRegistroDto(control.Sql, 1);
            fech=control.Fecha().substring(2,4)+control.Fecha().substring(5,7)+control.Fecha().substring(8,10);
            cod=ini+"_"+fech+"_"+"0001";
            codpro=ini+"_"+fech+"_";
            control.Sql="select * from producto where codbrr='"+cod+"';";
            //System.out.print(control.Sql);
            while(control.Verificarconsulta(control.Sql)){
                coun++;
                codmar=""+coun;
                for(int a=0; a<4;a++){
                    codmar="0"+codmar;
                }
                codn=codpro+codmar;
            control.Sql="select * from producto where codbrr='"+codn+"';";    
            //System.out.print(codn);
            cod=codn;
            }
        return cod;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbProductosPrestados = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        lbPrestador = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtSerie = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        rbMeses = new javax.swing.JRadioButton();
        rbAños = new javax.swing.JRadioButton();
        txtCantidad = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        rbAux = new javax.swing.JRadioButton();
        jLabel12 = new javax.swing.JLabel();
        rbSiCodBrr = new javax.swing.JRadioButton();
        rbNoCodBrr = new javax.swing.JRadioButton();
        rbAuxCodigo = new javax.swing.JRadioButton();
        cbProducto = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        txtBuscarPrestamso = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbPrestados = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tbProductosPrestados.setForeground(new java.awt.Color(0, 51, 102));
        tbProductosPrestados.setModel(new javax.swing.table.DefaultTableModel(
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
        tbProductosPrestados.setName("tbProductosPrestados"); // NOI18N
        tbProductosPrestados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProductosPrestadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbProductosPrestados);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 255, 860, 110));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Prestador");
        jLabel3.setName("jLabel3"); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 203, -1, -1));

        lbPrestador.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbPrestador.setForeground(new java.awt.Color(0, 51, 102));
        lbPrestador.setText(" ");
        lbPrestador.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbPrestador.setName("lbPrestador"); // NOI18N
        getContentPane().add(lbPrestador, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 199, 457, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("Producto");
        jLabel6.setName("jLabel6"); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, -1, -1));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setText("Garantia");
        jLabel8.setName("jLabel8"); // NOI18N
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, -1, -1));

        txtCodigo.setName("txtCodigo"); // NOI18N
        getContentPane().add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 490, 161, -1));

        txtSerie.setName("txtSerie"); // NOI18N
        getContentPane().add(txtSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 490, 161, -1));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 102));
        jLabel9.setText("Serie");
        jLabel9.setName("jLabel9"); // NOI18N
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 490, -1, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        jButton1.setMnemonic('g');
        jButton1.setText("Grabar");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 13, 110, 40));

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
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(443, 13, 110, 40));

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 51, 102));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        jButton3.setMnemonic('c');
        jButton3.setText("Cancelar");
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(299, 13, 110, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 515, 688, 60));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 102));
        jLabel10.setText("Precio");
        jLabel10.setName("jLabel10"); // NOI18N
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 490, -1, -1));

        txtPrecio.setName("txtPrecio"); // NOI18N
        getContentPane().add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 490, 107, -1));

        buttonGroup1.add(rbMeses);
        rbMeses.setForeground(new java.awt.Color(0, 51, 102));
        rbMeses.setText("Meses");
        rbMeses.setName("rbMeses"); // NOI18N
        rbMeses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbMesesActionPerformed(evt);
            }
        });
        getContentPane().add(rbMeses, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 430, -1, -1));

        buttonGroup1.add(rbAños);
        rbAños.setForeground(new java.awt.Color(0, 51, 102));
        rbAños.setText("Años");
        rbAños.setName("rbAños"); // NOI18N
        rbAños.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbAñosActionPerformed(evt);
            }
        });
        getContentPane().add(rbAños, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 430, -1, -1));

        txtCantidad.setName("txtCantidad"); // NOI18N
        getContentPane().add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 430, 213, -1));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 102));
        jLabel11.setText("Cantidad");
        jLabel11.setName("jLabel11"); // NOI18N
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 430, -1, -1));

        buttonGroup1.add(rbAux);
        rbAux.setForeground(new java.awt.Color(0, 51, 102));
        rbAux.setText("rb");
        rbAux.setName("rbAux"); // NOI18N
        getContentPane().add(rbAux, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 430, -1, -1));

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 51, 102));
        jLabel12.setText("Codigo");
        jLabel12.setName("jLabel12"); // NOI18N
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, -1, -1));

        buttonGroup2.add(rbSiCodBrr);
        rbSiCodBrr.setForeground(new java.awt.Color(0, 51, 102));
        rbSiCodBrr.setText("Si");
        rbSiCodBrr.setName("rbSiCodBrr"); // NOI18N
        rbSiCodBrr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbSiCodBrrActionPerformed(evt);
            }
        });
        getContentPane().add(rbSiCodBrr, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 460, -1, -1));

        buttonGroup2.add(rbNoCodBrr);
        rbNoCodBrr.setForeground(new java.awt.Color(0, 51, 102));
        rbNoCodBrr.setText("No");
        rbNoCodBrr.setName("rbNoCodBrr"); // NOI18N
        rbNoCodBrr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNoCodBrrActionPerformed(evt);
            }
        });
        getContentPane().add(rbNoCodBrr, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 460, -1, -1));

        buttonGroup2.add(rbAuxCodigo);
        rbAuxCodigo.setForeground(new java.awt.Color(0, 51, 102));
        rbAuxCodigo.setText("rb");
        rbAuxCodigo.setName("rbAuxCodigo"); // NOI18N
        getContentPane().add(rbAuxCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 460, -1, -1));

        cbProducto.setForeground(new java.awt.Color(0, 51, 102));
        cbProducto.setName("cbProducto"); // NOI18N
        getContentPane().add(cbProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, 324, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Los Prestamos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBuscarPrestamso.setName("txtBuscarPrestamso"); // NOI18N
        txtBuscarPrestamso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarPrestamsoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarPrestamsoKeyReleased(evt);
            }
        });
        jPanel2.add(txtBuscarPrestamso, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 380, -1));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Buscar Prestado");
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tbPrestados.setForeground(new java.awt.Color(0, 51, 102));
        tbPrestados.setModel(new javax.swing.table.DefaultTableModel(
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
        tbPrestados.setName("tbPrestados"); // NOI18N
        tbPrestados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPrestadosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbPrestados);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 860, 140));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 230));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lsiata de Productos Prestados", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 868, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 127, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 880, 150));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtBuscarPrestamsoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarPrestamsoKeyReleased
        Cancelar(); 
        MostrarCliente();
    }//GEN-LAST:event_txtBuscarPrestamsoKeyReleased

    private void tbPrestadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPrestadosMouseClicked
       if(evt.getClickCount()==2){
           SeleccionarPrestamo();
       }
        
    }//GEN-LAST:event_tbPrestadosMouseClicked

    private void tbProductosPrestadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductosPrestadosMouseClicked
       if(evt.getClickCount()==2){
           SeleccionarProducto();
       } 
    }//GEN-LAST:event_tbProductosPrestadosMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Cancelar();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtBuscarPrestamsoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarPrestamsoKeyPressed
        if(evt.getKeyChar()==10){
            if(tbPrestados.getRowCount()==1){
                tbPrestados.selectAll();
                SeleccionarPrestamo();
            }
        }
    }//GEN-LAST:event_txtBuscarPrestamsoKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        GrabarDEvolucionPrestamo();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void rbMesesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbMesesActionPerformed
        tipoper=1;
        txtCantidad.grabFocus();
    }//GEN-LAST:event_rbMesesActionPerformed

    private void rbAñosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbAñosActionPerformed
        tipoper=2;
        txtCantidad.grabFocus();
    }//GEN-LAST:event_rbAñosActionPerformed

    private void rbSiCodBrrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbSiCodBrrActionPerformed
        txtCodigo.grabFocus();
    }//GEN-LAST:event_rbSiCodBrrActionPerformed

    private void rbNoCodBrrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNoCodBrrActionPerformed
        txtCodigo.setText(CrearCodigo());
        txtSerie.grabFocus();
    }//GEN-LAST:event_rbNoCodBrrActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox cbProducto;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbPrestador;
    private javax.swing.JRadioButton rbAux;
    private javax.swing.JRadioButton rbAuxCodigo;
    private javax.swing.JRadioButton rbAños;
    private javax.swing.JRadioButton rbMeses;
    private javax.swing.JRadioButton rbNoCodBrr;
    private javax.swing.JRadioButton rbSiCodBrr;
    private javax.swing.JTable tbPrestados;
    private javax.swing.JTable tbProductosPrestados;
    private javax.swing.JTextField txtBuscarPrestamso;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtSerie;
    // End of variables declaration//GEN-END:variables
}
