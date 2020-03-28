/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ResibirProductoPrestado.java
 *
 * Created on 12-feb-2014, 13:07:10
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
public class ResibirProductoPrestado extends javax.swing.JInternalFrame {
Controlador control= new Controlador();
Mimodelo modelo = new Mimodelo();
Mimodelo modelo1 = new Mimodelo();
Mimodelo modelo2 = new Mimodelo();
Mimodelo modelo3 = new Mimodelo();
InfoGeneral info= new InfoGeneral();
String codigoprestamo="",d="",idprest="";
String us;
String []dd= new String[5];

    /** Creates new form ResibirProductoPrestado */
    public ResibirProductoPrestado() {
        initComponents();setTitle("Listar Productos para Devolver");
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
     tbProductosAlamcen.setModel(modelo2);
     modelo2.setColumnIdentifiers(new String[] {"Item","Producto","Codigo","Serie","Precio"});
     tbListaParaDEvolver.setModel(modelo3);
     modelo3.setColumnIdentifiers(new String[] {"Item","Producto","Codigo","Serie","Precio"});
     MostrarCliente();
    }
    
     public void llenarCBo(String dats){
         control.LimTabla(modelo2);
    control.Sql="select nomlna from linea_producto where nomctlg='"+dats+"';";
    d=control.DevolverRegistroDto(control.Sql, 1);
    control.Sql="SELECT idproducto,nomctlg, codbrr, seri, precVenta FROM ver_para_devolver v where estdo='Disponible' and nomlna='"+d+"';";
    control.LlenarJTabla(modelo2, control.Sql, 5);
    //cbProducto.setSelectedItem(dats);
    }
    
public void MostrarCliente(){
        
     BuscarCliente();   
    }    
    public void BuscarCliente(){
     control.Sql="SELECT idprestamo, nomprest,fecini, fecdev, sit FROM prestados  where sit='Activo' and tipopres=1 and nomprest like'"+
     txtBuscarPrestamso1.getText()+"%';";
     control.LlenarJTabla(modelo,control.Sql,5); 
     
 //    JOptionPane.showMessageDialog(null,control.Sql);
    }
     public void SeleccionarPrestamo(){
//        tbPrestados.getSelectedRow();
     lbPrestador.setText(modelo.getValueAt(tbPrestados.getSelectedRow(), 1).toString());
     codigoprestamo=modelo.getValueAt(tbPrestados.getSelectedRow(), 0).toString();
     control.Sql="SELECT pp.idprestamoproducto, pp.nomctlg, pp.codbrr, pp.seri, pp.precventa FROM productos_prestados pp,producto p where pp.idproducto=p.idproducto and  pp.idprestamo='"+codigoprestamo+"';";
     control.LlenarJTabla(modelo1,control.Sql,5);   
    //System.out.print(control.Sql);
    }
public void BuscarProduto(){
    control.Sql="SELECT idproducto,nomctlg, codbrr, seri, precVenta FROM ver_para_devolver"
            + " where estdo='Disponible' and nomlna='"+d+"' and (nomctlg like '"+txtBuscarProducto.getText()+"%' or codbrr like '"+txtBuscarProducto.getText()+"%' or seri like '"+txtBuscarProducto.getText()+"%');";
    control.LlenarJTabla(modelo2, control.Sql, 5);    
}
public void AgregarListaDEvolver(){
    control.Sql="select idusuario from usuario where nomusr='"+info.getUsuario()+"'";
        us=control.DevolverRegistroDto(control.Sql, 1);
        //*aqiu na mas se va a hacer el cambio*//
    control.Sql="select estdo from producto where codbrr='"+tbProductosAlamcen.getValueAt(tbProductosAlamcen.getSelectedRow(), 2).toString()+"';";
    String sss= control.DevolverRegistroDto(control.Sql, 1);
    if(sss.compareTo("Disponible")==0){
        control.Sql="insert into por_vender values('"+tbProductosAlamcen.getValueAt(tbProductosAlamcen.getSelectedRow(), 0).toString()+"','"+us+"');";
        control.CrearRegistro(control.Sql);
            control.Sql="update producto set estdo='Por Devolver' where codbrr='"+tbProductosAlamcen.getValueAt(tbProductosAlamcen.getSelectedRow(), 2).toString()+"';";
        control.EditarRegistro(control.Sql);
     dd[0]=tbProductosAlamcen.getValueAt(tbProductosAlamcen.getSelectedRow(), 0).toString();
     dd[1]=tbProductosAlamcen.getValueAt(tbProductosAlamcen.getSelectedRow(), 1).toString();
    dd[2]=tbProductosAlamcen.getValueAt(tbProductosAlamcen.getSelectedRow(), 2).toString();
     dd[3]=tbProductosAlamcen.getValueAt(tbProductosAlamcen.getSelectedRow(), 3).toString();
     dd[4]=tbProductosAlamcen.getValueAt(tbProductosAlamcen.getSelectedRow(), 4).toString();
      modelo3.addRow(dd);
       BuscarProduto();
    }else{
        JOptionPane.showMessageDialog(null,"Este Producto ya esta en uso en otro proceso de venta!!");
    }

}
public void EliminarLista(){
    if(tbListaParaDEvolver.getSelectedRowCount()==1){
        control.Sql="update producto set estdo='Disponible' where codbrr='"+tbListaParaDEvolver.getValueAt(tbListaParaDEvolver.getSelectedRow(), 2).toString()+"';";
        control.EditarRegistro(control.Sql);
        control.Sql="delete from por_vender where idproducto='"+tbListaParaDEvolver.getValueAt(tbListaParaDEvolver.getSelectedRow(), 0).toString()+"' and idusuario='"+us+"';";
        control.EliminarRegistro(control.Sql);
        modelo3.removeRow(tbListaParaDEvolver.getSelectedRow());
        control.LimTabla(modelo2);BuscarProduto();
    }else{
        JOptionPane.showMessageDialog(null, "Seleccione un producto de lista de Producto por Devolver");
    }
}
public void Actualizaest(){
    if(tbListaParaDEvolver.getRowCount()>0){
        if(JOptionPane.showConfirmDialog(null, "Tiene Elimentos en la lista de entrega\nDesea Cancelar la Lista!! ","", JOptionPane.YES_NO_OPTION)==0){
     while(control.fila<tbListaParaDEvolver.getRowCount()){
         control.Sql="update producto set estdo='Disponible' where codbrr='"+tbListaParaDEvolver.getValueAt(control.fila, 2).toString()+"';";
        control.EditarRegistro(control.Sql);
        control.Sql="delete from por_vender where idproducto='"+tbListaParaDEvolver.getValueAt(control.fila, 0).toString()+"' and idusuario='"+us+"';";
        control.EliminarRegistro(control.Sql);
        control.fila++;
     }   
    }
    }
}
public void cancelar(){
    BuscarCliente();
    control.LimTabla(modelo1);
    control.LimTabla(modelo2);
    control.LimTabla(modelo3);
    txtBuscarProducto.setText("");
    lbPrestador.setText(" ");
    txtBuscarPrestamso1.setText("");
    txtBuscarProducto1.setText("");
    txtBuscarPrestamso1.grabFocus();
}

public void EntregarProdcutos(){
    control.Sql="update externo set sit='Cancelado' where idprestamo='"+codigoprestamo+"';";
        control.EditarRegistro(control.Sql);
        while(control.fila<tbListaParaDEvolver.getRowCount()){
            
         control.Sql="update producto set estdo='Devuelto' where codbrr='"+tbListaParaDEvolver.getValueAt(control.fila, 2).toString()+"';";
        control.EditarRegistro(control.Sql);
        control.Sql="delete from por_vender where idproducto='"+tbListaParaDEvolver.getValueAt(control.fila, 0).toString()+"' and idusuario='"+us+"';";
        control.EliminarRegistro(control.Sql);
        control.fila++;
     } 
        cancelar();
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lbPrestador = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbProductosPrestados = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbPrestados = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtBuscarPrestamso1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbProductosAlamcen = new javax.swing.JTable();
        txtBuscarProducto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbListaParaDEvolver = new javax.swing.JTable();
        txtBuscarProducto1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Agregar prodcuto Prestado");
        jLabel1.setName("jLabel1"); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, -1, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de  Prestamos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        lbPrestador.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbPrestador.setForeground(new java.awt.Color(0, 51, 102));
        lbPrestador.setText(" ");
        lbPrestador.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbPrestador.setName("lbPrestador"); // NOI18N

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Prestador");
        jLabel3.setName("jLabel3"); // NOI18N

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(lbPrestador, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 16, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel3))
                    .addComponent(lbPrestador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 620, 250));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de  Prestamos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

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

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Buscar");
        jLabel2.setName("jLabel2"); // NOI18N

        txtBuscarPrestamso1.setName("txtBuscarPrestamso1"); // NOI18N
        txtBuscarPrestamso1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarPrestamso1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(10, 10, 10)
                        .addComponent(txtBuscarPrestamso1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 128, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel2))
                    .addComponent(txtBuscarPrestamso1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 620, 190));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de  Prestamos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        tbProductosAlamcen.setForeground(new java.awt.Color(0, 51, 102));
        tbProductosAlamcen.setModel(new javax.swing.table.DefaultTableModel(
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
        tbProductosAlamcen.setName("tbProductosAlamcen"); // NOI18N
        tbProductosAlamcen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProductosAlamcenMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbProductosAlamcen);

        txtBuscarProducto.setName("txtBuscarProducto"); // NOI18N
        txtBuscarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProductoKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Buscar");
        jLabel4.setName("jLabel4"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel4)
                        .addGap(10, 10, 10)
                        .addComponent(txtBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel4))
                    .addComponent(txtBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 30, 530, 190));

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 51, 102));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        jButton3.setText("Registrar Devolucion");
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 500, -1, -1));

        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 51, 102));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        jButton4.setText("Cancelar");
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 500, 120, -1));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de  Prestamos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        tbListaParaDEvolver.setForeground(new java.awt.Color(0, 51, 102));
        tbListaParaDEvolver.setModel(new javax.swing.table.DefaultTableModel(
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
        tbListaParaDEvolver.setName("tbListaParaDEvolver"); // NOI18N
        tbListaParaDEvolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListaParaDEvolverMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbListaParaDEvolver);

        txtBuscarProducto1.setName("txtBuscarProducto1"); // NOI18N
        txtBuscarProducto1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProducto1KeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Buscar");
        jLabel5.setName("jLabel5"); // NOI18N

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        jButton2.setText("Eliminar de Lista");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel5)
                        .addGap(10, 10, 10)
                        .addComponent(txtBuscarProducto1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel5))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtBuscarProducto1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 250, 530, 240));

        jButton5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 51, 102));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton5.setText("Salir");
        jButton5.setName("jButton5"); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 500, 170, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarPrestamso1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarPrestamso1KeyReleased
MostrarCliente();        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarPrestamso1KeyReleased

    private void tbPrestadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPrestadosMouseClicked
        if(evt.getClickCount()==2){
            SeleccionarPrestamo();
        }
    }//GEN-LAST:event_tbPrestadosMouseClicked

    private void txtBuscarProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProductoKeyReleased
        BuscarProduto();
    }//GEN-LAST:event_txtBuscarProductoKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    Actualizaest();
    cancelar();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tbProductosPrestadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductosPrestadosMouseClicked
        if(evt.getClickCount()==2){
            if(tbProductosPrestados.getSelectedRowCount()==1){
                idprest=tbProductosPrestados.getValueAt(tbProductosPrestados.getSelectedRow(), 0).toString();
                llenarCBo(tbProductosPrestados.getValueAt(tbProductosPrestados.getSelectedRow(), 1).toString());
                
            }
        }
    }//GEN-LAST:event_tbProductosPrestadosMouseClicked

    private void txtBuscarProducto1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProducto1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarProducto1KeyReleased

    private void tbProductosAlamcenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductosAlamcenMouseClicked
       if(evt.getClickCount()==2){
            AgregarListaDEvolver();
       }
       
    }//GEN-LAST:event_tbProductosAlamcenMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
            EliminarLista();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tbListaParaDEvolverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListaParaDEvolverMouseClicked
        if(evt.getClickCount()==2){
            EliminarLista();
        }
    }//GEN-LAST:event_tbListaParaDEvolverMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Actualizaest();
        dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        EntregarProdcutos();
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lbPrestador;
    private javax.swing.JTable tbListaParaDEvolver;
    private javax.swing.JTable tbPrestados;
    private javax.swing.JTable tbProductosAlamcen;
    private javax.swing.JTable tbProductosPrestados;
    private javax.swing.JTextField txtBuscarPrestamso1;
    private javax.swing.JTextField txtBuscarProducto;
    private javax.swing.JTextField txtBuscarProducto1;
    // End of variables declaration//GEN-END:variables
}
