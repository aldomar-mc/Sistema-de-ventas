
package Ventanas;
/**** @author Ing. Miguel Angel Silva Zapata.   **********/
import javax.swing.*;import Clases.*;import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Date;
import org.xml.sax.Attributes;
public class IngresoSinDocumento extends javax.swing.JInternalFrame {
    /*************************Atributos**********************/ 
    Mimodelo modelo=new Mimodelo();Mimodelo modelo1=new Mimodelo();
    Mimodelo modelo2=new Mimodelo();Controlador control=new Controlador();
    Codificador codig= new Codificador();
    InfoGeneral info=new InfoGeneral();
    String idProv,idCatProd,codbr,Garantia,fe,tpodoc,costo,coddoc;
    int per,ctp;
    String CodigoPrestador="";
    /*************************Atributos*************************/ 
    public void TomarFecha(){
     String an="",ms="",da="";   
     an=String.valueOf( fecha.getCalendar().get(1));
     ms=String.valueOf(fecha.getCalendar().get(2) + 1);          
     da=String.valueOf(fecha.getCalendar().get(5));
     if(ms.length()==1){
      ms="0"+ms;    
     }
     fe=control.Formato_Amd(fecha.getDate());           
    }
    
    public String numdoc(){
        String dat="";
        int a=0;
        control.Sql="select count(*) from doc_compra where tipo='Producto-Sin-Documento';";
        dat=control.DevolverRegistroDto(control.Sql, 1);
        if(dat.compareTo("0")==0){
            a=1;
        }else{
            boolean aa=true;
            control.Sql="select serie from doc_compra where tipo='Producto-Sin-Documento' order by iddoc_compra desc limit 1"; 
            a=Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
            a=a+1;
           
        }
        
        dat=""+a;
        return dat;
    }
    public void RegistrarIngreso(){
     if(tProductosingresar.getRowCount()>0){
      TomarFecha();   
      /**************************CREAR COMPRA**********************************/               
      idProv="0";//control.ObtenerDato("proveedor","nompro","PROVEEDOR GENERAL",1);         
      control.fila=0;
      String nudoc=numdoc();
      control.Sql="SELECT InsertaCompra('"+fe+"','"+idProv+"','"+nudoc+
       "','"+nudoc+"','Producto-Sin-Documento')";
      ///////////////////////////////////
      coddoc=control.DevolverRegistroDto(control.Sql,1);            
    // System.out.println(control.Sql);
      while(control.fila<tProductosingresar.getRowCount()){
       codbr=tProductosingresar.getValueAt(control.fila,0).toString();
       //idCatProd=tProductosingresar.getValueAt(control.fila,1).toString();
       costo=tProductosingresar.getValueAt(control.fila,2).toString();       
       Garantia=tProductosingresar.getValueAt(control.fila,3).toString();
       //per=Integer.parseInt(tProductosingresar.getValueAt(control.fila,4).toString());
       ctp=Integer.parseInt(tProductosingresar.getValueAt(control.fila,5).toString());
       idCatProd=control.ObtenerDato("catalogoproducto","nomctlg",tProductosingresar.getValueAt(control.fila,1).toString(),1);
   if(tProductosingresar.getValueAt(control.fila,4).toString().equals("Meses")){
       per=1;
   }else {per=2;}
       control.Sql="call IngresarProductos('"+Garantia+"','"+idCatProd
       +"','"+fe+"','"+codbr+"','"+costo+"','"+per+"','"+ctp+"','"+
       idProv+"','1',0,'"+coddoc+"');";   
       control.CrearRegistro(control.Sql);
       control.fila++;
       //JOptionPane.showMessageDialog(rootPane, control.Sql);
       //System.out.println(control.Sql);
       
      }
      Cancelar();
      /**************************FIN CREA GARANTIA POR COMPRA******************/   
     }
     else{
      JOptionPane.showMessageDialog(rootPane,"Faltan datos");   
     // txDocumento.requestFocus();
     }
    }
    public void LimpiarDatos1(){
     jLabel1.setText(" ");jLabel5.setText(" ");txCosto.setText(null);txGarantia.setText(null);   
     txSerie.setText(null);txcodigo.setText(null);tProductos.clearSelection();
     txBuscarproductos.setText(null);rb.setSelected(true);rb1.setSelected(true);
    // rb3.setSelected(true);jCheckBox1.setSelected(false);MostrarCatalogo();
    }
    public void LimpiarDatos2(){
     LimpiarDatos1();
     //jLabel5.setText(null);
   //  rbBoleta.setSelected(false);   
    // rbFactura.setSelected(false);rbRemision.setSelected(false);   
     //tProveedores.clearSelection();
     control.LimTabla(modelo2);//txDocumento.setText("");
     //rb3.setSelected(true);
    // control.PonerFechaenDateChooser(fecha,control.VolverFecha(control.Fecha()));
     txBuscarproveedores.grabFocus();
    }
    public void Cancelar(){
     LimpiarDatos1();jLabel5.setText(" ");//rbBoleta.setSelected(false);   
     //rbFactura.setSelected(false);rbRemision.setSelected(false);   
     tProveedores.clearSelection();control.LimTabla(modelo2);//txDocumento.setText("");
     //rb3.setSelected(true);
     
     //control.PonerFechaenDateChooser(fecha,control.VolverFecha(control.Fecha()));
     txBuscarproveedores.grabFocus();
    }
    public void MostrarProveedores(){
     BuscarProveedores();   
    }
    public void mostarf(){
//        int diaHoy = fecha.getCalendar.DAY_OF_MONTH);
//        int mes=fecha.get(Calendar.MONTH);
//        int año =fecha.get(Calendar.YEAR);
        

    }
    public void BuscarProveedores(){
        
                 control.Sql="select * from vta_losproveedores where codigo=0";// like'"+
     //txBuscarproveedores.getText()+"%' ";
///     txBuscarproveedores.getText()+"%'";
     control.LlenarJTabla(modelo,control.Sql,3);
        
      
       
    }
    public void VerProveedor(){
     LimpiarDatos2();
     jLabel1.setText(" ");
     control.fila=tProveedores.getSelectedRow();
     if(control.fila>=0){    
         CodigoPrestador=tProveedores.getValueAt(control.fila,0).toString();
         jLabel5.setText(tProveedores.getValueAt(control.fila,2).toString());  
      if(rbNo.isSelected()){
       GeneraCodigoProducto();    
      }
     }
    }
    public void BuscarCatalogo(){
     control.Sql="select * from vta_catalogo where Producto like'"+txBuscarproductos.getText()+
     "%' or Modelo like '"+txBuscarproductos.getText()+"%' or Marca like'"+txBuscarproductos.getText()+
     "%' order by Codigo";control.LlenarJTabla(modelo1,control.Sql,3);     
    }
    public void MostrarCatalogo(){
     BuscarCatalogo();   
    }
    public void VerCatalogo(){
     control.fila=tProveedores.getSelectedRow();
     if(control.fila>=0){
      control.fila=tProductos.getSelectedRow();
      if(control.fila>=0){
       jLabel1.setText(tProductos.getValueAt(control.fila,1).toString());                 
       txCosto.setEditable(true);txCosto.setText(null);txCosto.requestFocus();
      }    
     }
     else{
      tProductos.clearSelection();   
      JOptionPane.showMessageDialog(rootPane,"Seleccione primero al proveedor");   
     }
    }
    public void ObtenerGarantia(){
     int dto=0,cantd=0;   
     if(rbMeses.isSelected()){
      dto=1;   
     }
     if(rbAños.isSelected()){
      dto=2;   
     }
     cantd=Integer.parseInt(txGarantia.getText());
     JOptionPane.showMessageDialog(rootPane,control.ObtenerFechaGarantia(Integer.toString(dto),txGarantia.getText()));
    }   
    public void GeneraCodigoProducto(){
     control.bandera=false;
     control.Veces=0;
     boolean b=false;
     if(tProveedores.getSelectedRow()<0)
      rb.setSelected(true);        
     else{
      String fech="",ltr="",cod="";
      fech=control.Fecha().substring(2,4)+control.Fecha().substring(5,7)+control.Fecha().substring(8,10);
      ltr=control.ObtenerDato("proveedor","idproveedor","0",5);
      //ltr=codig.CapturaIniciales(jLabel5.getText().toString());
//      System.out.print(jLabel5.getText().toString());
      cod=ltr+"_"+fech;
      control.Veces++;cod=cod+"_"+control.GeneraNumero(control.Veces);      
      while(!control.bandera){
       control.Sql="select * from producto where codbrr='"+cod+"'";
       if(control.Verificandoconsulta(control.Sql)){
        control.Veces++;cod=ltr+"_"+fech+"_"+control.GeneraNumero(control.Veces);   
       }
       else
        control.bandera=true;          
      }      
      if(modelo2.getRowCount()>0){
       control.bandera=false;
       while(!control.bandera){
        control.bandera=control.BuscarDatoenJtable(modelo2,cod,0);
        if(control.bandera){
         control.Veces++;
         cod=ltr+"_"+fech;
         cod=cod+"_"+control.GeneraNumero(control.Veces);
         control.bandera=false;
        }
        else{
         control.bandera=true;   
        }
       }   
      }      
      txcodigo.setText(cod);txcodigo.setEditable(false);       
     }     
    }
    public IngresoSinDocumento() {
     initComponents();setTitle("Ingreso de Productos sin Documentos");this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
     bCrear.setMnemonic('c');     
     bSalir.setMnemonic('s');bAgregar.setMnemonic('g');bCancelar.setMnemonic('a');
     rbSi.setMnemonic('i');rbNo.setMnemonic('n');//rbFactura.setMnemonic('b');
     //rbRemision.setMnemonic('r');
     rbMeses.setMnemonic('m');rbAños.setMnemonic('ñ');
     //rbBoleta.setMnemonic('b');//rbFactura.setMnemonic('f');     
     tProveedores.setModel(modelo); modelo.setColumnIdentifiers(new String[]{"Código","Ruc","Proveedor General"});       
     tProveedores.getColumnModel().getColumn(0).setPreferredWidth(50);
     tProveedores.getColumnModel().getColumn(1).setPreferredWidth(300);
     tProveedores.getColumnModel().getColumn(2).setPreferredWidth(300);      
     tProductos.setModel(modelo1); modelo1.setColumnIdentifiers(new String[]{"Código","Producto","Precio"});            
     tProductos.getColumnModel().getColumn(0).setPreferredWidth(20);
     tProductos.getColumnModel().getColumn(1).setPreferredWidth(350);
     tProductos.getColumnModel().getColumn(2).setPreferredWidth(50);           
     tProductosingresar.setModel(modelo2); modelo2.setColumnIdentifiers(new 
     String[]{"Código","Producto","Costo","Serie","Periodo","Cantidad"});       
     txCosto.setEditable(false);
     tProductosingresar.getColumnModel().getColumn(1).setPreferredWidth(250);
     jLabel5.setText(" ");jLabel1.setText(" ");rb.setVisible(false);bAgregar.setVisible(false);
     //control.PonerFechaenDateChooser(fecha,control.VolverFecha(control.Fecha()));
     rb1.setVisible(false);
     //rb3.setVisible(false);
     MostrarProveedores();MostrarCatalogo();
     //fecha.getDateEditor().getUiComponent()).setText(fec);
     //fecha.setDate(new Date(fechhaaaaaaaasssss().toString()));
     fecha.setDate(new Date());
     jButton1.setVisible(false);
     
    }
//    public String fechhaaaaaaaasssss(){
//       String a= control.DevolverRegistroDto("select concat(day(curdate()),'-',month(curdate()),'-',year(curdate()));", 1);
//        return a;
//    }
    public void EscribirGarantia(KeyEvent e){
     control.bandera=false;
     if(rbMeses.isSelected()){
      control.bandera=true;   
     }
     if(rbAños.isSelected()){
      control.bandera=true;   
     }
     if(control.bandera){
      control.Solonumeros(e);control.Longitudtx(txGarantia,e,2); 
     }
     else
      e.consume();    
    }
    public void Agregar(){
//     if(control.BuscarDatoenJtable(modelo2,txSerie.getText(),3)){
//      JOptionPane.showMessageDialog(rootPane,"La serie del producto esta repetida");   
//      txSerie.requestFocus();return;
//     }
    int contr=0;
        if(txSerie.getText().trim().length()==0){
            
        }else{
            control.Sql="SELECT count(*) FROM vtaprodcutostotal where seri='"+txSerie.getText()+"';";
           
          // System.out.println(control.Sql);
           contr=Integer.parseInt(control.DevolverRegistroDto(control.Sql,1));
        }
           if(control.BuscarDatoenJtable(modelo2,txSerie.getText(),3)){
               JOptionPane.showMessageDialog(rootPane,"La serie del producto esta repetida en esta Compra");   
               txSerie.requestFocus();return;
           }else{
               if(contr==1){
                    control.Sql="SELECT concat('PRODUCTO:  ',nomctlg),concat('SERIE: ',seri),concat('CODIGO: ', codbrr)AS DD FROM vtaprodcutostotal where seri='"+txSerie.getText()+"';";
                    String conts=control.DevolverRegistroDto(control.Sql,1);
                    String conts1=control.DevolverRegistroDto(control.Sql,2);
                    String conts2=control.DevolverRegistroDto(control.Sql,3);
                   //d //f
                        //String msg = "<HTML><BODY BGCOLOR=#FFCCCC>this</BODY></HTML>";
                    //String msg = "<HTML><BODY BGCOLOR=white> La serie ya se encuentra ingresada : \n\n"+conts+"\n\n"+conts1+"\n\n"+conts2+"</BODY></HTML>";    
                    //JLabel message = new JLabel(msg);
                        
                        //JOptionPane.showMessageDialog( null, message, "Error!", JOptionPane.ERROR_MESSAGE);
                        
                    JOptionPane.showMessageDialog(null,"La serie ya se encuentra ingresada : \n"+conts+"\n"+conts1+"\n"+conts2, "Error!", JOptionPane.ERROR_MESSAGE);
                    txSerie.requestFocus();return;
                }
           }
     Garantia="";   
     if(txGarantia.getText().trim().length()>0){
      if(rbMeses.isSelected()){
       Garantia="Meses";   
      }   
      if(rbAños.isSelected()){
       Garantia="Años";   
      }
      control.Data[0]=txcodigo.getText();control.Data[1]=jLabel1.getText();
      control.Data[2]=txCosto.getText();control.Data[3]=txSerie.getText();      
      control.Data[4]=Garantia;control.Data[5]=txGarantia.getText();
      modelo2.addRow(control.Data);LimpiarDatos1();
      jLabel1.setText(" ");
      txCosto.setEditable(false);
      
     }   
     else
      JOptionPane.showMessageDialog(rootPane,"Faltan datos");       
    }
    public void CapturarCodigo(){
     if(txcodigo.getText().trim().length()>0){
      Agregar();
     }
    }
    public void SeleccionarSi(){
     txcodigo.setEditable(true);
     if(jCheckBox1.isSelected()){
      txcodigo.setText(txSerie.getText());   
     }
     else{
      txcodigo.setText(null);txcodigo.setEditable(true);
      txcodigo.requestFocus(); 
     }  
    }
    public void SeleccionarNo(){
     GeneraCodigoProducto();
     Agregar();     
    }
//    public boolean SeleccionarDocumento(){
//     control.bandera=false;
//     if(rbBoleta.isSelected())
//      control.bandera=true;   
//     if(rbFactura.isSelected())
//      control.bandera=true; 
//     if(rbRemision.isSelected())
//      control.bandera=true; 
//     return control.bandera;             
//    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txBuscarproductosingresar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tProductosingresar = new javax.swing.JTable();
        bAgregar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txBuscarproveedores = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tProveedores = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txBuscarproductos = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tProductos = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        bSalir = new javax.swing.JButton();
        bCrear = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txCosto = new javax.swing.JTextField();
        txGarantia = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        rbAños = new javax.swing.JRadioButton();
        rbMeses = new javax.swing.JRadioButton();
        txSerie = new javax.swing.JTextField();
        rb1 = new javax.swing.JRadioButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        txcodigo = new javax.swing.JTextField();
        rbNo = new javax.swing.JRadioButton();
        rbSi = new javax.swing.JRadioButton();
        rb = new javax.swing.JRadioButton();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seleccionar productos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Buscar");
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 20));

        txBuscarproductosingresar.setName("txBuscarproductosingresar"); // NOI18N
        jPanel1.add(txBuscarproductosingresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 240, -1));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tProductosingresar.setForeground(new java.awt.Color(0, 51, 102));
        tProductosingresar.setModel(new javax.swing.table.DefaultTableModel(
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
        tProductosingresar.setName("tProductosingresar"); // NOI18N
        tProductosingresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tProductosingresarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tProductosingresar);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 620, 290));

        bAgregar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bAgregar.setForeground(new java.awt.Color(0, 51, 102));
        bAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Add.png"))); // NOI18N
        bAgregar.setText("Agregar");
        bAgregar.setName("bAgregar"); // NOI18N
        bAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAgregarActionPerformed(evt);
            }
        });
        jPanel1.add(bAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 120, 30));

        jButton1.setText("jButton1");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 160, 650, 350));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seleccionar productos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setText("Buscar");
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 20));

        txBuscarproveedores.setName("txBuscarproveedores"); // NOI18N
        txBuscarproveedores.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txBuscarproveedoresKeyReleased(evt);
            }
        });
        jPanel2.add(txBuscarproveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 25, 200, -1));

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tProveedores.setForeground(new java.awt.Color(0, 51, 102));
        tProveedores.setModel(new javax.swing.table.DefaultTableModel(
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
        tProveedores.setName("tProveedores"); // NOI18N
        tProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tProveedoresMouseClicked(evt);
            }
        });
        tProveedores.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tProveedoresKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tProveedores);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 570, 220));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setText(" ");
        jLabel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 24, 280, 20));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 590, 280));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seleccionar productos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setText("Buscar");
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 20));

        txBuscarproductos.setName("txBuscarproductos"); // NOI18N
        txBuscarproductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txBuscarproductosKeyReleased(evt);
            }
        });
        jPanel4.add(txBuscarproductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 25, 200, -1));

        jScrollPane3.setName("jScrollPane3"); // NOI18N

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
        tProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tProductosKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tProductos);

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 570, 270));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 590, 330));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(" "));
        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel5.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 90, 50));

        bCrear.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCrear.setForeground(new java.awt.Color(0, 51, 102));
        bCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        bCrear.setText("Ingresar");
        bCrear.setName("bCrear"); // NOI18N
        bCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCrearActionPerformed(evt);
            }
        });
        jPanel5.add(bCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 50));

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
        jPanel5.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 110, 50));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 510, 340, 110));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seleccionar productos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel6.setName("jPanel6"); // NOI18N
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 102));
        jLabel9.setText("Fecha");
        jLabel9.setName("jLabel9"); // NOI18N
        jPanel6.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        fecha.setDateFormatString("dd-MM-yyyy"); // NOI18N
        fecha.setMinSelectableDate(new java.util.Date(-62135747926000L));
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
        jPanel6.add(fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 160, -1));

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 510, 310, 90));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seleccionar productos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel7.setName("jPanel7"); // NOI18N
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText(" ");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel1.setName("jLabel1"); // NOI18N
        jPanel7.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 115, 410, -1));

        txCosto.setName("txCosto"); // NOI18N
        txCosto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txCostoFocusGained(evt);
            }
        });
        txCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txCostoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txCostoKeyTyped(evt);
            }
        });
        jPanel7.add(txCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 32, 110, -1));

        txGarantia.setName("txGarantia"); // NOI18N
        txGarantia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txGarantiaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txGarantiaKeyTyped(evt);
            }
        });
        jPanel7.add(txGarantia, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 80, 110, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("Garantia");
        jLabel6.setName("jLabel6"); // NOI18N
        jPanel7.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, -1, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 102));
        jLabel7.setText("Costo");
        jLabel7.setName("jLabel7"); // NOI18N
        jPanel7.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 35, -1, -1));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setText("Serie");
        jLabel8.setName("jLabel8"); // NOI18N
        jPanel7.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        buttonGroup3.add(rbAños);
        rbAños.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        rbAños.setForeground(new java.awt.Color(0, 51, 102));
        rbAños.setText("Años");
        rbAños.setName("rbAños"); // NOI18N
        rbAños.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbAñosActionPerformed(evt);
            }
        });
        jPanel7.add(rbAños, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 50, -1, -1));

        buttonGroup3.add(rbMeses);
        rbMeses.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        rbMeses.setForeground(new java.awt.Color(0, 51, 102));
        rbMeses.setText("Meses");
        rbMeses.setName("rbMeses"); // NOI18N
        rbMeses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbMesesActionPerformed(evt);
            }
        });
        jPanel7.add(rbMeses, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, -1, -1));

        txSerie.setName("txSerie"); // NOI18N
        txSerie.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txSerieFocusGained(evt);
            }
        });
        jPanel7.add(txSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 75, 110, -1));

        buttonGroup3.add(rb1);
        rb1.setForeground(new java.awt.Color(0, 51, 102));
        rb1.setText("rB1");
        rb1.setName("rb1"); // NOI18N
        jPanel7.add(rb1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, -1, -1));

        jCheckBox1.setName("jCheckBox1"); // NOI18N
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jPanel7.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 78, -1, -1));

        getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 440, 145));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seleccionar productos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txcodigo.setName("txcodigo"); // NOI18N
        txcodigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txcodigoMouseEntered(evt);
            }
        });
        txcodigo.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txcodigoCaretUpdate(evt);
            }
        });
        txcodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txcodigoActionPerformed(evt);
            }
        });
        txcodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txcodigoFocusGained(evt);
            }
        });
        txcodigo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txcodigoPropertyChange(evt);
            }
        });
        txcodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txcodigoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txcodigoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txcodigoKeyTyped(evt);
            }
        });
        jPanel3.add(txcodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 140, -1));

        buttonGroup1.add(rbNo);
        rbNo.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        rbNo.setForeground(new java.awt.Color(0, 51, 102));
        rbNo.setText("No");
        rbNo.setName("rbNo"); // NOI18N
        rbNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNoActionPerformed(evt);
            }
        });
        jPanel3.add(rbNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, -1, -1));

        buttonGroup1.add(rbSi);
        rbSi.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        rbSi.setForeground(new java.awt.Color(0, 51, 102));
        rbSi.setText("Si");
        rbSi.setName("rbSi"); // NOI18N
        rbSi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbSiActionPerformed(evt);
            }
        });
        jPanel3.add(rbSi, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        buttonGroup1.add(rb);
        rb.setForeground(new java.awt.Color(0, 51, 102));
        rb.setText("p");
        rb.setName("rb"); // NOI18N
        jPanel3.add(rb, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 60, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 40, 160, 100));

        pack();
    }// </editor-fold>//GEN-END:initComponents
private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
 dispose();
}//GEN-LAST:event_bSalirActionPerformed
private void bCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCrearActionPerformed
 RegistrarIngreso();
}//GEN-LAST:event_bCrearActionPerformed
private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
 Cancelar();
}//GEN-LAST:event_bCancelarActionPerformed
private void txBuscarproveedoresKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txBuscarproveedoresKeyReleased
 BuscarProveedores();
 if(tProveedores.getSelectedRow()==-1){
  jLabel5.setText(null);   
 }
}//GEN-LAST:event_txBuscarproveedoresKeyReleased
private void tProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProveedoresMouseClicked
 VerProveedor(); 
}//GEN-LAST:event_tProveedoresMouseClicked
private void tProveedoresKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tProveedoresKeyReleased
 VerProveedor(); 
}//GEN-LAST:event_tProveedoresKeyReleased
private void txBuscarproductosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txBuscarproductosKeyReleased
 BuscarCatalogo(); 
 if(tProductos.getSelectedRow()==-1){
  rb.setSelected(true);jLabel1.setText(null);   
 }
}//GEN-LAST:event_txBuscarproductosKeyReleased
private void tProductosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tProductosKeyReleased
 VerCatalogo(); 
}//GEN-LAST:event_tProductosKeyReleased
private void tProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProductosMouseClicked
 VerCatalogo();
}//GEN-LAST:event_tProductosMouseClicked
private void rbSiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbSiActionPerformed
 SeleccionarSi();
}//GEN-LAST:event_rbSiActionPerformed
private void rbNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNoActionPerformed
 SeleccionarNo();
}//GEN-LAST:event_rbNoActionPerformed
private void txcodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txcodigoKeyReleased
 
}//GEN-LAST:event_txcodigoKeyReleased
private void txCostoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txCostoKeyTyped
 control.decimal(evt,txCosto.getText()); 
 if((evt.getKeyChar()==10)&&(txCosto.getText().trim().length()>0)){
  txSerie.requestFocus();    
 }
}//GEN-LAST:event_txCostoKeyTyped
private void bAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAgregarActionPerformed
 Agregar();
 
}//GEN-LAST:event_bAgregarActionPerformed
private void txcodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txcodigoKeyTyped
 
}//GEN-LAST:event_txcodigoKeyTyped
private void txcodigoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txcodigoMouseEntered
  
}//GEN-LAST:event_txcodigoMouseEntered
private void txcodigoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txcodigoPropertyChange
 
}//GEN-LAST:event_txcodigoPropertyChange
private void txcodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txcodigoKeyPressed
 
}//GEN-LAST:event_txcodigoKeyPressed
private void txcodigoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txcodigoCaretUpdate
 
}//GEN-LAST:event_txcodigoCaretUpdate
private void txcodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txcodigoActionPerformed
 CapturarCodigo(); 
}//GEN-LAST:event_txcodigoActionPerformed
private void txcodigoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txcodigoFocusGained
 if(!rbSi.isSelected()){
  txGarantia.requestFocus();
 }
}//GEN-LAST:event_txcodigoFocusGained
private void rbMesesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbMesesActionPerformed
 txGarantia.requestFocus(); 
}//GEN-LAST:event_rbMesesActionPerformed
private void rbAñosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbAñosActionPerformed
 txGarantia.requestFocus();
}//GEN-LAST:event_rbAñosActionPerformed

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
private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
 if(txSerie.getText().trim().length()==0){
  jCheckBox1.setSelected(false); txSerie.requestFocus();  
 } 
 else{
  txGarantia.requestFocus();    
 }
}//GEN-LAST:event_jCheckBox1ActionPerformed
private void txGarantiaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txGarantiaKeyTyped
 EscribirGarantia(evt);          
}//GEN-LAST:event_txGarantiaKeyTyped
private void txGarantiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txGarantiaKeyReleased
 
}//GEN-LAST:event_txGarantiaKeyReleased
private void txCostoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txCostoFocusGained

    
    
}//GEN-LAST:event_txCostoFocusGained

private void txSerieFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txSerieFocusGained
 if(txCosto.getText().trim().length()==0){
  txCosto.grabFocus();   
 }
}//GEN-LAST:event_txSerieFocusGained

    private void tProductosingresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProductosingresarMouseClicked
      if(evt.getClickCount()>=2 ){
          modelo2.removeRow(tProductosingresar.getSelectedRow());
          //modelo.getValueAt(tProductosingresar.getSelectedRow(), ctp)
      }
    }//GEN-LAST:event_tProductosingresarMouseClicked

    private void txCostoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txCostoKeyReleased
      
    }//GEN-LAST:event_txCostoKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       //String msg = "<HTML><BODY BGCOLOR=#FFCCCC>this</BODY></HTML>";
String conts="Uno;";
String conts1="Uno;";
String conts2="Uno;";
//
        String msg = "<HTML><BODY BGCOLOR=white> La serie ya se encuentra ingresada : \n\n"+conts+"\n\n"+conts1+"\n\n"+conts2+"</BODY></HTML>";    
//                    
JLabel message = new JLabel(msg);
//JOptionPane.showMessageDialog( null, message, "Error!", JOptionPane.ERROR_MESSAGE);
JOptionPane.showMessageDialog(this,"<html><body><p style='width: 500px;'>Dosssss</body></html>","Error",JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAgregar;
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bCrear;
    private javax.swing.JButton bSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JRadioButton rb;
    private javax.swing.JRadioButton rb1;
    private javax.swing.JRadioButton rbAños;
    private javax.swing.JRadioButton rbMeses;
    private javax.swing.JRadioButton rbNo;
    private javax.swing.JRadioButton rbSi;
    private javax.swing.JTable tProductos;
    private javax.swing.JTable tProductosingresar;
    private javax.swing.JTable tProveedores;
    private javax.swing.JTextField txBuscarproductos;
    private javax.swing.JTextField txBuscarproductosingresar;
    private javax.swing.JTextField txBuscarproveedores;
    private javax.swing.JTextField txCosto;
    private javax.swing.JTextField txGarantia;
    private javax.swing.JTextField txSerie;
    private javax.swing.JTextField txcodigo;
    // End of variables declaration//GEN-END:variables
}

