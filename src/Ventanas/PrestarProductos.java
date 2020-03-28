
package Ventanas;
/**** @author Silva  **********/
import javax.swing.*;import Clases.*;
import com.toedter.calendar.*;
import java.util.Date;
public class PrestarProductos extends javax.swing.JInternalFrame {
    Controlador control =new Controlador();Mimodelo modelo=new Mimodelo();
    Mimodelo modelo1=new Mimodelo();Mimodelo modelo2=new Mimodelo();
    InfoGeneral info= new InfoGeneral();IMPRIMIR imprime= new IMPRIMIR();
    Mimodelo modelotemp=new Mimodelo();String idpres="",codpr="",fe="";
    String codigoPrestamos="";
    
    public void SeleccionarPrestador(){
     control.fila=tPrestador.getSelectedRow();
     if(control.fila>=0){
      idpres=tPrestador.getValueAt(control.fila,0).toString();   
      jLabel5.setText(tPrestador.getValueAt(control.fila,1).toString());   
     }
    }
    public boolean VerificaEstado(String cod){
        boolean a=false;
        if(control.Verificarconsulta("select * from producto where idproducto='"+cod+"' and estdo='Disponible';")){
            a=true;
        }
        return a;
    }
    public void Actulizar_Anterior_Producto(){
        control.Sql="select idusuario from usuario where nomusr='"+info.getUsuario()+"'";
        String us=control.DevolverRegistroDto(control.Sql, 1);
        try {
            control.Base.st=control.Base.conec.createStatement();
            control.Base.rt=control.Base.st.executeQuery("select * from por_vender where idusuario='"+us+"';");
            while(control.Base.rt.next()){
                   control.Sql="update producto set estdo='Disponible' where idproducto='"+control.Base.rt.getString(1).toString()+"';";
                   control.EditarRegistro(control.Sql);       
            }
            control.Sql="delete from por_vender where idusuario='"+us+"';";
            control.EliminarRegistro(control.Sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void SeleccionayCargaProducto(){
        
     control.fila=tProductos.getSelectedRow();
     if(control.fila>=0){
         control.Sql="select idproducto from producto where codbrr='"+tProductos.getValueAt(control.fila,0).toString()+"'";
        String  idpio=control.DevolverRegistroDto(control.Sql, 1);
         if(VerificaEstado(idpio)==false){
             JOptionPane.showMessageDialog(null, "El producto no se puede Prestar!!");
             txtBucarProducto.setText("");
             MostrarProducto();
         }else{
      control.Data[0]=tProductos.getValueAt(control.fila,0).toString();    
      control.Data[1]=tProductos.getValueAt(control.fila,1).toString();    
      control.Data[2]=tProductos.getValueAt(control.fila,2).toString();    
      modelotemp.addRow(control.Data);modelo2.addRow(control.Data);
      control.Sql="update producto set estdo='Por Prestar' where codbrr='"+control.Data[0]+"';";//
      control.EditarRegistro(control.Sql);
//      System.out.print(control.Sql);
      control.Sql="select idusuario from usuario where nomusr='"+info.getUsuario()+"'";
        String us=control.DevolverRegistroDto(control.Sql, 1);
      control.Sql="insert into por_vender values('"+idpio+"','"+us+"');";
            control.CrearRegistro(control.Sql);
      MostrarProducto();
         }
     }
    }
    public PrestarProductos() {
     initComponents();setTitle("Los Prestadores");
     this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
     tPrestador.setModel(modelo);modelo.setColumnIdentifiers(new String[] 
     {"Código","Prestador","Responsable","Dirección"});jLabel5.setText(" ");
     tPrestador.getColumnModel().getColumn(1).setPreferredWidth(250);
     tPrestador.getColumnModel().getColumn(2).setPreferredWidth(250);
     tPrestador.getColumnModel().getColumn(3).setPreferredWidth(250);     
     MostrarPrestador();tProductos.setModel(modelo1);     
     modelo1.setColumnIdentifiers(new String[] {"Código","Producto","Serie","Costo"});     
     tProductos.getColumnModel().getColumn(1).setPreferredWidth(250);
     MostrarProducto();tProductosPrestar.setModel(modelo2);
     modelo2.setColumnIdentifiers(new String[] {"Código","Producto","Serie"});     
     modelotemp.setColumnIdentifiers(new String[] {"Código","Producto","Serie"});     
     tProductosPrestar.getColumnModel().getColumn(1).setPreferredWidth(250);
     bPrestar.setMnemonic('p');bCancelar.setMnemonic('c');bSalir.setMnemonic('s');     
     fecha.setDate(new Date());    
     control.forma_table_ver(modelo, tPrestador);
     FormatoTabla ft= new FormatoTabla(0);
     tProductos.setDefaultRenderer(Object.class, ft);
     tProductosPrestar.setDefaultRenderer(Object.class, ft);
    }
    public void QuitarProducto(){
     control.fila=tProductosPrestar.getSelectedRow();
     if(control.fila>=0){
      if(JOptionPane.showConfirmDialog(rootPane,"Seguro quieres quitar","Quitar",0)==0){
       //control.Sql="call ActualizarEstadoProducto('"+modelo2.getValueAt(control.fila,0).toString()+"','Disponible')";
       control.Sql="update producto set estdo='Disponible' where codbrr='"+modelo2.getValueAt(control.fila,0).toString()+"';";
       control.EditarRegistro(control.Sql); 
       control.Sql="select idproducto from producto where codbrr='"+modelo2.getValueAt(control.fila,0).toString()+"';";
      String idor = control.DevolverRegistroDto(control.Sql, 1);
       control.Sql="delete from por_vender where idproducto='"+idor+"'";
       control.EliminarRegistro(control.Sql);
       
       modelo2.removeRow(control.fila);
       //modelotemp.removeRow(control.fila);        
       txtBucarProducto.setText("ab");txtBucarProducto.setText(null);MostrarProducto();
      }    
     }
    }
    public void PrestarProductos(){
     if(tProductosPrestar.getRowCount()>0){
      control.fila=0;
      while(control.fila<tProductosPrestar.getRowCount()){
          
       control.fila++;   
      }
      control.LimTabla(modelo2);control.LimTabla(modelotemp);
     }    
    }
    public void BuscaProdutoPrestar(){
     int ctd=0,leng=0;leng=txtBuscarProdxPrestar.getText().length();
     control.LimTabla(modelo2);
     if(leng>0){
      while(ctd<modelotemp.getRowCount()){
       if((txtBuscarProdxPrestar.getText().equalsIgnoreCase(modelotemp.getValueAt(ctd,0).toString().substring(0, leng))) ||
       (txtBuscarProdxPrestar.getText().equalsIgnoreCase(modelotemp.getValueAt(ctd,1).toString().substring(0, leng))) || 
       (txtBuscarProdxPrestar.getText().equalsIgnoreCase(modelotemp.getValueAt(ctd,2).toString().substring(0, leng)))){
        control.Data[0]=modelotemp.getValueAt(ctd,0).toString();    
        control.Data[1]=modelotemp.getValueAt(ctd,1).toString();
        control.Data[2]=modelotemp.getValueAt(ctd,2).toString();
        modelo2.addRow(control.Data);
       }   
       ctd++;   
      }    
     }
     else{         
      while(ctd<modelotemp.getRowCount()){       
        control.Data[0]=modelotemp.getValueAt(ctd,0).toString();    
        control.Data[1]=modelotemp.getValueAt(ctd,1).toString();
        control.Data[2]=modelotemp.getValueAt(ctd,2).toString();
        modelo2.addRow(control.Data);ctd++;   
      }
     }     
    } 
    public void CargarConCodigoBarras(){
     if(txtCodigo.getText().length()>0){      
      try{
       control.Sql="select * from vta_productoprestar where Codigo='"+txtCodigo.getText()+"'";      
       control.Base.rt=control.DevolverRegistro(control.Sql);
       if(control.Base.rt.next()){
        control.Data[0]=control.Base.rt.getString(1);
        control.Data[1]=control.Base.rt.getString(2);
        control.Data[2]=control.Base.rt.getString(3);
        modelotemp.addRow(control.Data);modelo2.addRow(control.Data);
        
        control.Sql="update producto set estdo='Por Prestar' where codbrr='"+control.Data[0]+"';";//
      control.EditarRegistro(control.Sql);
      
      control.Sql="select idproducto from producto where codbrr='"+control.Data[0]+"';";
        String codpro= control.DevolverRegistroDto(control.Sql, 1);
        
        control.Sql="select idusuario from usuario where nomusr='"+info.getUsuario()+"'";
        String us=control.DevolverRegistroDto(control.Sql, 1);
      control.Sql="insert into por_vender values('"+codpro+"','"+us+"');";
            control.CrearRegistro(control.Sql);
        MostrarProducto();
       }   
       else{
        JOptionPane.showMessageDialog(rootPane,"No existe producto con el codigo "+txtCodigo.getText());   
       }
      }
      catch(Exception e){}      
      txtCodigo.setText(null);
     }   
    }
    public void MostrarProducto(){
     BuscarProducto();   
    }
    public void BuscarPrestador(){
     control.Sql="select * from vta_prestador where nomprest like'"+
     txtBuscarPrestador.getText()+"%' or respon like'"+txtBuscarPrestador.getText()+"%'";   
     control.LlenarJTabla(modelo,control.Sql,5);
    }
    public void MostrarPrestador(){
     BuscarPrestador();   
    }
    public void BuscarProducto(){    
      control.Sql="select * from vta_productoprestar where codigo like'"+
      txtBucarProducto.getText()+"%' or producto like'"+txtBucarProducto.getText()+
      "%' or serie like'"+txtBucarProducto.getText()+"%'";           
      control.LlenarJTabla(modelo1,control.Sql,4); 
    
    }
    public void RegistrarPrestamodeProductos(){
     if(tProductosPrestar.getRowCount()>0){
      //LlenarLosProductosaPrestar();
         
         
      idpres=control.ObtenerDato("Prestador","nomprest",jLabel5.getText(),1);
      fe=control.Formato_Amd(fecha.getDate());
      
      control.Sql="call insertarprestamosuno(0,'"+fe+"','"+idpres+"')";      
      codigoPrestamos=control.DevolverRegistroDto(control.Sql, 1);//control.CrearRegistro(control.Sql);
      
      LlenarLosProductosaPrestar();
      Cancelar();
      
      imprime.ImprimirusConFechas("Prestamo.jasper",codigoPrestamos);
     }        
    }
    public void LlenarLosProductosaPrestar(){
  
     for(control.fila=0;control.fila<tProductosPrestar.getRowCount();control.fila++){
         control.Sql="select idproducto from producto where codbrr='"+tProductosPrestar.getValueAt(control.fila, 0).toString()+"';";
         String codprod=control.DevolverRegistroDto(control.Sql, 1);
      control.Sql="insert into prestamoproducto (idprestamo, idproducto)values('"+codigoPrestamos+"','"+codprod+"');";    
      control.CrearRegistro(control.Sql);
      control.Sql="update producto set estdo='Prestado' where idproducto='"+codprod+"';";
      control.EditarRegistro(control.Sql);
     }
     control.Sql="select idusuario from usuario where nomusr='"+info.getUsuario()+"'";
        String us=control.DevolverRegistroDto(control.Sql, 1);
     control.Sql="delete from por_vender where idusuario='"+us+"';";
            control.EliminarRegistro(control.Sql);
    }
    public void Cancelar(){
        //ActualizaEsta();
     jLabel5.setText(" ");tPrestador.clearSelection();tProductos.clearSelection();
     control.LimTabla(modelotemp);control.LimTabla(modelo2);txtBuscarPrestador.grabFocus();
    }
        public void ActualizaEsta(){
        String codi="",idpio="";
    //    control.fila=tProdaVender.getRowCount();
        control.fila=0;
       if(tProductosPrestar.getRowCount()>0){
           control.Sql="select idusuario from usuario where nomusr='"+info.getUsuario()+"'";
        String us=control.DevolverRegistroDto(control.Sql, 1);
       while(control.fila<tProductosPrestar.getRowCount()){
           codi=modelo2.getValueAt(control.fila, 0).toString();
           control.Sql="select idproducto from producto where codbrr='"+codi+"'";
            idpio=control.DevolverRegistroDto(control.Sql, 1);
           control.Sql="update producto set estdo='Disponible' where idproducto='"+idpio+"' and estdo='Por Prestar';";
           control.EditarRegistro(control.Sql);
//           System.out.print(control.Sql);
           control.fila++;
        }
       control.Sql="delete from por_vender where idusuario='"+us+"';";
            control.EliminarRegistro(control.Sql);
    }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        bSalir = new javax.swing.JButton();
        bPrestar = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtBuscarPrestador = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tPrestador = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tProductos = new javax.swing.JTable();
        txtBucarProducto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tProductosPrestar = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtBuscarProdxPrestar = new javax.swing.JTextField();
        fecha = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();

        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(" "));
        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bSalir.setForeground(new java.awt.Color(0, 51, 102));
        bSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir.setMnemonic('s');
        bSalir.setText("Salir");
        bSalir.setName("bSalir"); // NOI18N
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });
        jPanel5.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 25, 100, 40));

        bPrestar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bPrestar.setForeground(new java.awt.Color(0, 51, 102));
        bPrestar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        bPrestar.setMnemonic('p');
        bPrestar.setText("Prestar");
        bPrestar.setName("bPrestar"); // NOI18N
        bPrestar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPrestarActionPerformed(evt);
            }
        });
        jPanel5.add(bPrestar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 25, 100, 40));

        bCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCancelar.setForeground(new java.awt.Color(0, 51, 102));
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setMnemonic('c');
        bCancelar.setText("Cancelar");
        bCancelar.setName("bCancelar"); // NOI18N
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });
        jPanel5.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 25, 110, 40));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 380, 340, 90));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos a prestar", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(0, 51, 102));
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setText("Buscar");
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 20));

        txtBuscarPrestador.setName("txtBuscarPrestador"); // NOI18N
        txtBuscarPrestador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarPrestadorKeyReleased(evt);
            }
        });
        jPanel2.add(txtBuscarPrestador, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 25, 200, -1));

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tPrestador.setForeground(new java.awt.Color(0, 51, 102));
        tPrestador.setModel(new javax.swing.table.DefaultTableModel(
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
        tPrestador.setName("tPrestador"); // NOI18N
        tPrestador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tPrestadorMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tPrestador);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 610, 170));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Prestador");
        jLabel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, 310, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, 230));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos a prestar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(0, 51, 102));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tProductos.setForeground(new java.awt.Color(0, 51, 102));
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
        tProductos.setName("tProductos"); // NOI18N
        tProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tProductos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 610, 170));

        txtBucarProducto.setName("txtBucarProducto"); // NOI18N
        txtBucarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBucarProductoKeyReleased(evt);
            }
        });
        jPanel1.add(txtBucarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 25, 270, -1));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Buscar");
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 630, 230));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Codigo");
        jLabel1.setName("jLabel1"); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 28, -1, -1));

        txtCodigo.setName("txtCodigo"); // NOI18N
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        getContentPane().add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 24, 230, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos a prestar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(0, 51, 102));
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        tProductosPrestar.setForeground(new java.awt.Color(0, 51, 102));
        tProductosPrestar.setModel(new javax.swing.table.DefaultTableModel(
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
        tProductosPrestar.setName("tProductosPrestar"); // NOI18N
        tProductosPrestar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tProductosPrestarMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tProductosPrestar);

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 520, 250));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Buscar");
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        txtBuscarProdxPrestar.setName("txtBuscarProdxPrestar"); // NOI18N
        txtBuscarProdxPrestar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProdxPrestarKeyReleased(evt);
            }
        });
        jPanel3.add(txtBuscarProdxPrestar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 25, 240, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 60, 540, 320));

        fecha.setDateFormatString("dd-MM-yyyy");
        fecha.setName("fecha"); // NOI18N
        fecha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fechaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                fechaMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                fechaMousePressed(evt);
            }
        });
        fecha.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                fechaMouseDragged(evt);
            }
        });
        fecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fechaKeyTyped(evt);
            }
        });
        getContentPane().add(fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 410, 160, -1));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fecha devolucion", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(51, 0, 102))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 380, 200, 90));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel6.setName("jPanel6"); // NOI18N
        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 0, 540, 60));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
    
        ActualizaEsta();
        dispose();
    }//GEN-LAST:event_bSalirActionPerformed
    private void txtBuscarPrestadorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarPrestadorKeyReleased
     BuscarPrestador();
    }//GEN-LAST:event_txtBuscarPrestadorKeyReleased
    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
    
    }//GEN-LAST:event_formComponentShown
    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
     MostrarPrestador();
    }//GEN-LAST:event_formInternalFrameActivated
    private void txtBucarProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBucarProductoKeyReleased
     BuscarProducto();
    }//GEN-LAST:event_txtBucarProductoKeyReleased
private void tPrestadorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tPrestadorMouseClicked

    if(evt.getClickCount()==2){
        SeleccionarPrestador();
    }
}//GEN-LAST:event_tPrestadorMouseClicked
private void tProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProductosMouseClicked
    
    if(evt.getClickCount()==2){
        if(jLabel5.getText().trim().length()>0){
            SeleccionayCargaProducto();
        }else{JOptionPane.showMessageDialog(null, "Selecione un prestador!!");
        }
    }
    
    
}//GEN-LAST:event_tProductosMouseClicked
private void txtBuscarProdxPrestarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProdxPrestarKeyReleased
 BuscaProdutoPrestar();
}//GEN-LAST:event_txtBuscarProdxPrestarKeyReleased
private void tProductosPrestarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProductosPrestarMouseClicked

    if(evt.getClickCount()==2){
        QuitarProducto();
    }
}//GEN-LAST:event_tProductosPrestarMouseClicked
private void bPrestarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPrestarActionPerformed
 RegistrarPrestamodeProductos();  
}//GEN-LAST:event_bPrestarActionPerformed
private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
 CargarConCodigoBarras();
}//GEN-LAST:event_txtCodigoActionPerformed
private void fechaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fechaMouseClicked
 
}//GEN-LAST:event_fechaMouseClicked
private void fechaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fechaMouseEntered

}//GEN-LAST:event_fechaMouseEntered
private void fechaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fechaMousePressed

}//GEN-LAST:event_fechaMousePressed
private void fechaMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fechaMouseDragged
 JOptionPane.showMessageDialog(rootPane,"los Dias");
}//GEN-LAST:event_fechaMouseDragged
private void fechaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaKeyTyped
 
}//GEN-LAST:event_fechaKeyTyped
private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed

    ActualizaEsta();
    Cancelar(); 
 
}//GEN-LAST:event_bCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bPrestar;
    private javax.swing.JButton bSalir;
    private com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tPrestador;
    public static javax.swing.JTable tProductos;
    public static javax.swing.JTable tProductosPrestar;
    private javax.swing.JTextField txtBucarProducto;
    private javax.swing.JTextField txtBuscarPrestador;
    private javax.swing.JTextField txtBuscarProdxPrestar;
    private javax.swing.JTextField txtCodigo;
    // End of variables declaration//GEN-END:variables
}
