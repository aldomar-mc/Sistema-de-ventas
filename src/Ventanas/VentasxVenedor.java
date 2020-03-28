package Ventanas;

import Clases.*;import com.toedter.calendar.JDateChooser;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Field;
import java.util.Date;
import javax.swing.JOptionPane;

import java.io.*;import javax.swing.*;import jxl.*;import jxl.format.*;import jxl.write.Label;
import jxl.write.WritableCellFormat;import jxl.write.WritableFont;
import jxl.write.WritableSheet;import jxl.write.WritableWorkbook;


public class VentasxVenedor extends javax.swing.JInternalFrame {
    //******************************Atributos***********************************
    Controlador control=new Controlador(); Mimodelo modelo=new Mimodelo();
    Mimodelo modelo1=new Mimodelo();String idVendedor,Vendedor;int may=-1;
    private JFileChooser chooser = new JFileChooser();
    //******************************Atributos***********************************
    
    //******************************Metodos*************************************
    public VentasxVenedor() {
     initComponents();setTitle("Ventas por vendedor");this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
     tVendedores.setModel(modelo);tVentasxVendedor.setModel(modelo1);
     modelo.setColumnIdentifiers(new String[] {"ID","Vendedor"});     
     
     modelo1.setColumnIdentifiers(new String[] {"ID_Vta","Fecha","Modalidad"
     ,"Comprobante","Cliente","Monto","Porc","ImpxVendedor"});
     PonerFechaActual();MostrarVendedores();VentasdelVendedor();
     tVendedores.getColumnModel().getColumn(1).setPreferredWidth(300);OcultarColumnas();
     tVentasxVendedor.getColumnModel().getColumn(3).setPreferredWidth(165);
     tVentasxVendedor.getColumnModel().getColumn(4).setPreferredWidth(300);
     
     //****************************CONTROLAR EL CAMBIO DE FECHA***********************
     f_Desde.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
       if("date".equals(evt.getPropertyName())){
        JDateChooser isse=(JDateChooser)evt.getSource();
        boolean isdatasele=false;
        try {
         Field dateselfi=JDateChooser.class.getDeclaredField("dateSelect");
         dateselfi.setAccessible(true);isdatasele=dateselfi.getBoolean(isse);
        } 
        catch (Exception e) {
         VentasdelVendedor();
        }
       }
      }
     });
     F_Hasta.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
       if("date".equals(evt.getPropertyName())){
        JDateChooser isse=(JDateChooser)evt.getSource();
        boolean isdatasele=false;
        try {
         Field dateselfi=JDateChooser.class.getDeclaredField("dateSelect");
         dateselfi.setAccessible(true);
         isdatasele=dateselfi.getBoolean(isse);
        } 
        catch (Exception e) {
         VentasdelVendedor();
        }
       }
      }
     });
     //****************************CONTROLAR EL CAMBIO DE FECHA*****************    
     Total.setText("");M_Pagar.setText("");elvendedor.setText("");
    }
    public void OcultarColumnas(){     
     control.hideTableColumn(tVendedores, 0);control.hideTableColumn(tVentasxVendedor, 0,6);
    }
    public void PonerFechaActual(){
     f_Desde.setDate( new Date());F_Hasta.setDate( new Date());   
    }
    public void MostrarVendedores(){
      elvendedor.setText("");  
     control.Sql="select idusuario,nombre from vendedores where nom like'%"+
     txtBuscar.getText()+"%' and idSede='"+InfoGeneral.getIdSede()+"'";     
     control.LlenarJTabla(modelo,control.Sql,2);        
    }
    public void SeleccionaVendedor(){
     if(tVendedores.getSelectedRow()>=0){
      idVendedor=tVendedores.getValueAt(tVendedores.getSelectedRow(),0).toString();
      Vendedor=tVendedores.getValueAt(tVendedores.getSelectedRow(),1).toString();
      elvendedor.setText(Vendedor); VentasdelVendedor();
     }   
     else{
      idVendedor="";Vendedor="";   
     }         
    }
    public void Cancelar(){
     tVendedores.clearSelection();txtBuscar.setText("");PonerFechaActual();
     VentasdelVendedor();idVendedor="";Vendedor="";elvendedor.setText("");
     control.LimTabla(modelo1);Total.setText("");M_Pagar.setText("");txtBuscar.grabFocus();
    }
    public void VentasdelVendedor(){
     if(tVendedores.getSelectedRow()>-1){
      control.Sql="select v.idventa ID,v.fecvta Fec_Venta,v.moda Modalidad,concat(tc.tipcompr,'= ',tc.ser,'-',cb.nume) Comprobante,\n" +
      "cl.nomclie Cliente,@Monto:=(v.montreal-v.descuento) Monto,\n" +
      "@Porce:=(select round(max(cast(valor as decimal(5,2)))/100,2) as Porcentaje from config\n" +
      "where idconfig='Porcentajevendedor'),round((@Monto*@Porce),2) ImporteVendedor\n" +
      "from venta v inner join vendedores vdr on v.Usuario_idusuario=vdr.idusuario inner join datosusuarios dtus\n" +
      "on dtus.usuario_idusuario= vdr.idusuario inner join cliente cl on cl.idCliente=v.idCliente\n" +
      "inner join compventa cv on cv.idventa=v.idventa inner join comprobantes cb on cb.idcomprobantes=cv.idcomprobantes\n" +
      "inner join tipocomprobante tc on tc.idtipocomprobante=cb.idtipocomprobante\n" +
      "where vdr.idusuario='"+tVendedores.getValueAt(tVendedores.getSelectedRow(),0).toString()+
       "' and v.fecvta between '"+control.Formato_Amd(f_Desde.getDate())+"' and '"+
       control.Formato_Amd(F_Hasta.getDate())+"'";
      control.LimTabla(modelo1);control.LlenarJTabla(modelo1,control.Sql,8);Total.setText(control.
      CalcularMontodeJtable(modelo1, 5));M_Pagar.setText(control.CalcularMontodeJtable(modelo1, 7));
     }        
    }
    public void Imprimir(){
     if(tVentasxVendedor.getRowCount()>0){      
      control.impresor.Imprimircon5Parametros("Reporte de Ventas por Vendedor","RptVentasxVendedor",
      "f_des", "f_has", "inicio", "fin","idvendr",control.Formato_DMA(f_Desde.getDate()),
      control.Formato_DMA(F_Hasta.getDate()), control.Formato_Amd(f_Desde.getDate()),
      control.Formato_Amd(F_Hasta.getDate()), 
      tVendedores.getValueAt(tVendedores.getSelectedRow(),0).toString());
     }
     else{
      if(elvendedor.getText().trim().length()==0)   
       JOptionPane.showMessageDialog(null, "Selecciona al vendedor");      
      else    
       JOptionPane.showMessageDialog(null, "El vendedor"+elvendedor.getText()+
       " no tiene ventas");   
     }      
    }
    public void ExportaraExcel(){
     int fila=0,longitud=0,col=1;   
     try{
      //**************************Muestra el dialogo para seleccionar el archivo   
      int showSaveDialog = chooser.showSaveDialog(this);
      if (showSaveDialog == JFileChooser.CANCEL_OPTION) {
       return;
      }         
      //**************************Crea el archivo excel*************************   
      File selectedFile = chooser.getSelectedFile();
      if (!selectedFile.getName().endsWith(".xls")) {
       selectedFile = new File(selectedFile.toString() + ".xls");
      }
      
      //****************************Verifica si existe el archivo creado********
      if (selectedFile.exists()) {       
       int showConfirmDialog = JOptionPane.showConfirmDialog(chooser,
       "El archivo "+ selectedFile.toString()+ "\nya existe ¿Desea "
       + "Reemplazarlo?", "Exportar",JOptionPane.YES_NO_OPTION);                
      }
      //****************************Verifica si existe el archivo creado********
      
      //****************Creamos los objetos NECESARIOS libro hoja y fuente*******
      WritableWorkbook workbook = Workbook.createWorkbook(selectedFile);
      WritableSheet sheet = workbook.createSheet("Ventas_Vendedor", 0);
      WritableFont font = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);      
      WritableFont font1 = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);      
      WritableCellFormat cellFormat = new WritableCellFormat(font);
      cellFormat.setAlignment(Alignment.LEFT);                  
      //****************Creamos los objetos NECESARIOS libro hoja y fuente*******
      
      //*********************ESTABLECEMOS EL FORMATO Y EL TITULO****************           
      fila=5;
      cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK); 
      sheet.addCell(new Label(1, fila, "Vendedor:   "+elvendedor.getText(), cellFormat));           
      sheet.mergeCells(1, fila, 6, fila);cellFormat = new WritableCellFormat(font);      
      //*********************ESTABLECEMOS EL FORMATO Y EL TITULO****************     
      
      //*********************PONEMOS LA CABECERA DE LA TABLA********************
      cellFormat.setAlignment(Alignment.CENTRE);fila=6;
      cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
      sheet.addCell(new Label(1, fila, "Fecha", cellFormat));
      sheet.addCell(new Label(2, fila, "Modalidad", cellFormat));
      sheet.addCell(new Label(3, fila, "Comprobante", cellFormat));
      sheet.addCell(new Label(4, fila, "Cliente", cellFormat));     
      sheet.addCell(new Label(5, fila, "Monto", cellFormat));     
      sheet.addCell(new Label(6, fila, "ImpxVendedor", cellFormat));     
      //*********************PONEMOS LA CABECERA DE LA TABLA********************                  
      
      //*********************LLENAR LA TABLA************************************
      WritableFont cellFont = new WritableFont(WritableFont.TIMES, 10);
      cellFont.setColour(Colour.BLUE);      
      cellFormat = new WritableCellFormat(cellFont);
      cellFormat.setAlignment(Alignment.CENTRE);
      cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);                
      fila=7;
      for(control.fila=0;control.fila<tVentasxVendedor.getRowCount();control.fila++){
       for(col=1;col<=6;col++){                 
        longitud=tVentasxVendedor.getValueAt(control.fila,col).toString().length()+5;
        if(col==4){
         if(longitud>may)
          may=longitud;               
         sheet.setColumnView(col,may+6);                  
        } 
        else    
         sheet.setColumnView(col,longitud);               
        if(col==1)         
         sheet.addCell(new Label(col,fila,control.VolverFecha(
         tVentasxVendedor.getValueAt(control.fila,col).toString()),cellFormat));               
        else               
         sheet.addCell(new Label(col,fila,tVentasxVendedor.getValueAt(control.fila
         ,col).toString(),cellFormat));                                   
        
        if((col==5) || (col==6)) {
         longitud=tVentasxVendedor.getValueAt(control.fila,col).toString().length()+5;
         sheet.setColumnView(col,15);        
        }
        if(col==6)         {
         sheet.addCell(new Label(col,fila,tVentasxVendedor.getValueAt(control.fila
         ,col+1).toString(),cellFormat));                                  }
       }
       fila++;
      }
      //*********************LLENAR LA TABLA************************************
      
      //**************************PONER LAS FECHAS DE LAS VENTAS****************
      WritableCellFormat cellFechas = new WritableCellFormat(font);      
      sheet.addCell(new Label(1, 2, "Desde", cellFechas));      
      sheet.addCell(new Label(1, 3, "Hasta", cellFechas));           
      WritableCellFormat cellFechasvl = new WritableCellFormat(font1);
      longitud=control.Formato_DMA(f_Desde.getDate()).length()+2;
      
      sheet.setColumnView(2,longitud);               
      sheet.addCell(new Label(2, 2, control.Formato_DMA(f_Desde.getDate()),
      cellFechasvl));                 
      sheet.addCell(new Label(2, 3, control.Formato_DMA(F_Hasta.getDate()),
      cellFechasvl));      
      //**************************PONER LAS FECHAS DE LAS VENTAS****************
      
      //*********************ESCRIBIMOS Y CERRAMOS EL LIBRO********************
      JOptionPane.showMessageDialog(rootPane,"Finalizo la exportación de "+
       selectedFile.toString());workbook.write();workbook.close();
      //*********************ESCRIBIMOS Y CERRAMOS EL LIBRO********************   
     }   
     catch(Exception e){}
    }
    //******************************Metodos*************************************
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tVendedores = new javax.swing.JTable();
        elvendedor = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tVentasxVendedor = new javax.swing.JTable();
        txtBuscar1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        f_Desde = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        F_Hasta = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        bCancelar = new javax.swing.JButton();
        bSalir = new javax.swing.JButton();
        bImprimir = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        M_Pagar = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        Total = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setText("Buscar");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 40, -1, -1));

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 30, 180, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Vendedores", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tVendedores.setModel(new javax.swing.table.DefaultTableModel(
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
        tVendedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tVendedoresMouseClicked(evt);
            }
        });
        tVendedores.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tVendedoresKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tVendedores);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 500, 110));

        elvendedor.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        elvendedor.setText("MontoTotal");
        jPanel3.add(elvendedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 35, 230, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 520, 170));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de ventas del vendedor", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tVentasxVendedor.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tVentasxVendedor);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 760, 190));

        txtBuscar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscar1KeyReleased(evt);
            }
        });
        jPanel4.add(txtBuscar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 26, 290, -1));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setText("Buscar");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 780, 250));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Las Fechas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setText("Desde");
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, -1));

        f_Desde.setDateFormatString("dd-MM-yyyy");
        jPanel5.add(f_Desde, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 130, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setText("Hasta");
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        F_Hasta.setDateFormatString("dd-MM-yyyy");
        jPanel5.add(F_Hasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 130, -1));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 5, 250, 170));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setMnemonic('a');
        bCancelar.setText("Cancelar");
        bCancelar.setMargin(new java.awt.Insets(2, 1, 2, 14));
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 70, 110, 45));

        bSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir.setMnemonic('s');
        bSalir.setText("Salir");
        bSalir.setMargin(new java.awt.Insets(2, 1, 2, 14));
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });
        jPanel2.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 130, 110, 45));

        bImprimir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Print.png"))); // NOI18N
        bImprimir.setMnemonic('i');
        bImprimir.setText("Imprimir");
        bImprimir.setMargin(new java.awt.Insets(2, 1, 2, 14));
        bImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bImprimirActionPerformed(evt);
            }
        });
        jPanel2.add(bImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 10, 110, 45));

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setMnemonic('x');
        jButton1.setText("Exportar");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 110, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 190, 160, 235));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Montos del Vendedor", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setText("Total a pagar");
        jPanel6.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, -1));

        M_Pagar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        M_Pagar.setText("Monto");
        jPanel6.add(M_Pagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 70, -1));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel11.setText("MontoTotal");
        jPanel6.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        Total.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        Total.setText("Monto");
        jPanel6.add(Total, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 70, -1));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 5, 170, 170));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    //******************************Eventos********************************************
    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
     MostrarVendedores();
    }//GEN-LAST:event_txtBuscarKeyReleased
    private void tVendedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tVendedoresMouseClicked
     SeleccionaVendedor();
    }//GEN-LAST:event_tVendedoresMouseClicked
    private void tVendedoresKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tVendedoresKeyReleased
     SeleccionaVendedor();
    }//GEN-LAST:event_tVendedoresKeyReleased
    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
      Cancelar();
    }//GEN-LAST:event_bCancelarActionPerformed
    private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
     dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_bSalirActionPerformed
    private void bImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bImprimirActionPerformed
      Imprimir();
    }//GEN-LAST:event_bImprimirActionPerformed

    private void txtBuscar1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscar1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscar1KeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     ExportaraExcel();
    }//GEN-LAST:event_jButton1ActionPerformed
    //******************************Eventos*********************************************

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser F_Hasta;
    private javax.swing.JLabel M_Pagar;
    private javax.swing.JLabel Total;
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bImprimir;
    private javax.swing.JButton bSalir;
    private javax.swing.JLabel elvendedor;
    private com.toedter.calendar.JDateChooser f_Desde;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tVendedores;
    private javax.swing.JTable tVentasxVendedor;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtBuscar1;
    // End of variables declaration//GEN-END:variables
}
