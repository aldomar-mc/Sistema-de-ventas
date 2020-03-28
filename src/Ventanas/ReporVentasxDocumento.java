/** To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.  */
package Ventanas;
/************** @author User ***************************/
import Clases.*;import com.toedter.calendar.JDateChooser;import java.beans.*;import java.lang.reflect.*;
import java.sql.*;import java.util.Date;import java.awt.event.*;import java.io.*;import java.util.*;
import javax.swing.*;import javax.swing.table.*;import jxl.*;import jxl.format.Alignment;import jxl.write.*;
public class ReporVentasxDocumento extends javax.swing.JInternalFrame {
   /**********************************ATRIBUTOS******************************/
   Controlador control=new Controlador();DefaultTableModel md1= new DefaultTableModel();
   DefaultTableModel md2= new DefaultTableModel();InfoGeneral info=new InfoGeneral();
   /********************************ATRIBUTOS********************************/
   
    public ReporVentasxDocumento() {
      initComponents();control.Sql="select * from tipocomprobante";this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
      String[] dts=new String[2];control.LlenarCombo(cboTipo,control.Sql,2);          
      md1.setColumnIdentifiers(new String[] {"Tipo","Serie","Numero","Fecha","Identificación",
      "Cliente","B_Importe","Igv","Monto"});jTable1.setModel(md2);
      md2.setColumnIdentifiers(new String[] {"Tipo","Serie","Numero","Fecha","Identificación",
      "Cliente","B_Importe","Igv","Monto"});PonerFechaActual();jTable1.setVisible(false);
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
          LlenarModelo();  
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
          LlenarModelo();  
        }
       }
      }
     });
     //****************************CONTROLAR EL CAMBIO DE FECHA*****************            
     cboTipo.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {       
       LlenarModelo();
      }
     });     
     //PonerFechaActual();
    }
    public void PonerFechaActual(){
     f_Desde.setDate( new Date());F_Hasta.setDate( new Date());   
    }
    public void Cancelar(){
      PonerFechaActual();cboTipo.setSelectedIndex(-1);
    }
    public void Imprimir(){
      String cd="";       
      if(cboTipo.getSelectedIndex()!=-1){       
       control.Sql="select idTipoComprobante from TipoComprobante where tipcompr='"+
       cboTipo.getSelectedItem().toString()+"';";cd=control.DevolverRegistroDto(control.Sql,1);
      }      
      control.impresor.Imprimircon5Parametros("Reporte de ventas", "CabeceraVentas",
      "bus","fdes","fhas","fdevis","fhavis",cd, control.Formato_Amd(f_Desde.getDate()),
      control.Formato_Amd(F_Hasta.getDate()),control.Formato_DMA(f_Desde.getDate()),
      control.Formato_DMA(F_Hasta.getDate()));
    }    
    public void LlenarModelo(){     
     /*****************************Capturamos Los Parametros***********************************/   
     String fcDesde="",fcHasta="",tdoc="",idtip="",sql1="";fcDesde=control.Formato_Amd(f_Desde.getDate());
     fcHasta=control.Formato_Amd(F_Hasta.getDate());
     /*****************************Capturamos Los Parametros***********************************/            
     if(cboTipo.getSelectedIndex()>-1){
      tdoc=cboTipo.getSelectedItem().toString();            
      control.Sql="select * from tipocomprobante where tipcompr='"+tdoc
       +"' and idSede='"+info.idSede+"'";tdoc=control.DevolverRegistroDto(control.Sql,1);              
     }
     else
        tdoc="";          
     try{
      control.Sql="select idTipocomprobante,tipcompr from tipocomprobante where idTipocomprobante in "
      + "(select distinct tipo from LaVista1 where (tipo like'" + tdoc+"%') and (Fecha   between '"+fcDesde
      +"' and  '"+ fcHasta+"') order by comprobante desc);";    
      
      
      
     //System.out.println("===>"+control.Sql);
      
      control.LimTablaDefault(md1);control.Base.rt=control.DevolverRegistro(control.Sql); 
      control.LimTablaDefault(md2);     
      while(control.Base.rt.next()){          
       idtip=control.Base.rt.getString(1);control.Data[0]=idtip;control.Data[1]=control.Base.rt.getString(2);       
       control.Data[2]="";control.Data[3]="";control.Data[4]="";control.Data[5]="";control.Data[6]="";
       control.Data[7]=""; md1.addRow(control.Data);         
      }
      if(md1.getRowCount()>0){
       control.LimTablaDefault(md2);
       for(int t=0;t<md1.getRowCount();t++){
        //********************Llenar La Cabecera del Reporte***********************************           
        tdoc=md1.getValueAt(t, 0).toString();   
        control.Data[0]=md1.getValueAt(t, 0).toString();control.Data[1]=md1.getValueAt(t, 1).toString();
        control.Data[2]=md1.getValueAt(t, 2).toString();control.Data[3]=md1.getValueAt(t, 3).toString();
        control.Data[4]=md1.getValueAt(t, 4).toString();control.Data[5]=md1.getValueAt(t, 5).toString();
        control.Data[6]=md1.getValueAt(t, 6).toString();control.Data[7]=md1.getValueAt(t, 7).toString();
        control.Data[8]="";md2.addRow(control.Data);
        if(control.Data[3].length()==0){
         control.Data[0]="Tipo";control.Data[1]="Serie";control.Data[2]="Numero";
         control.Data[3]="Fecha";control.Data[4]="Identificacion";control.Data[5]="Cliente";
         control.Data[6]="B_Imponible";control.Data[7]="IGV";control.Data[8]="Monto";
        }          
        else    
          control.Data[8]=md1.getValueAt(t, 8).toString();        
        md2.addRow(control.Data);
        //********************Llenar La Cabecera del Reporte***********************************
        
        //**************************Llenar el Detalle del Reporte********************************
      /*  sql1="select * from RpteVentasFinal where  (Fecha between '"+fcDesde+"' and '"+fcHasta
        +"') and (tipo='"+tdoc+"') order by comprobante desc;";
        */
        sql1="select v.idVenta,tc.tipcompr,tc.ser,cb.nume Comprobante,v.fecvta Fecha,cl.nomclie Cliente,\n" +
        "v.moda Modalidad, concat(dusr.ape,' ',dusr.nom) Usuario,v.montfactu MontoReal,\n" +
        "@Base:=round((v.montfactu / 1.18),2) Baseimponible,round((v.montfactu-@Base),2) Igv,\n" +
        "(v.montfactu-v.descuento) MontDescuento,cb.esta Estado,tc.idtipocomprobante Tipo,concat(idf.desident,' ',idf.numident)\n" +
        "ElNumero,cb.Esta\n" +
        "from venta v inner join compventa cv on cv.idventa=v.idventa inner join cliente cl on cl.idcliente=v.idcliente\n" +
        "inner join comprobantes cb on cb.idcomprobantes=cv.idcomprobantes inner join tipocomprobante tc\n" +
        "on tc.idtipocomprobante=cb.idtipocomprobante inner join usuario us on us.idusuario=v.Usuario_idusuario inner join\n" +
        "datosusuarios dusr on dusr.Usuario_idusuario=us.idusuario inner join identificacion idf on idf.idcliente=cl.idcliente\n" +
        "where cb.Esta='Emitido' and (v.fecvta between '"+fcDesde +"' and  '"+ fcHasta+"')  and (tc.idtipocomprobante LIKE '%" + tdoc+"%')\n" +
        "order by v.idVenta desc;";
        
        control.Base.rt=control.DevolverRegistro(sql1);
        double bi=0.00,ig=0.00,mt=0.00;     
        while(control.Base.rt.next()){          
          control.Data[0]=control.Base.rt.getString(2);control.Data[1]=control.Base.rt.getString(3);
          control.Data[2]=control.Base.rt.getString(4);control.Data[3]=control.Base.rt.getString(5);
          control.Data[4]=control.Base.rt.getString(15);control.Data[5]=control.Base.rt.getString(6);
          control.Data[6]=control.Base.rt.getString(10);control.Data[7]=control.Base.rt.getString(11);                  
          if(control.Base.rt.getString(6).length()==0)
           control.Data[8]="";  
          else
           control.Data[8]=control.Base.rt.getString(12);          
          bi=bi+Double.parseDouble(control.Data[6]);ig=ig+Double.parseDouble(control.Data[7]);
          mt=mt+Double.parseDouble(control.Data[8]);md2.addRow(control.Data);  
        }
        //*************************************LLENANDO LOS MONTO**********************************                
        control.Data[0]="";control.Data[1]="";control.Data[2]="";control.Data[3]="";control.Data[4]="";control.Data[5]=""; 
        control.Sql="select round("+bi+",2)";control.Data[6]=control.DevolverRegistroDto(control.Sql, 1);
        control.Sql="select round("+ig+",2)";control.Data[7]=control.DevolverRegistroDto(control.Sql, 1);
        control.Sql="select round("+mt+",2)";control.Data[8]=control.DevolverRegistroDto(control.Sql, 1);        
        md2.addRow(control.Data);  
        //*************************************LLENANDO LOS MONTO**********************************
       }                 
      }       
     }catch(Exception e){}     
    }        
    public void Exportar_a_Excel(){      
     try{         
         //**************CREAMOS UN ARCHIVO EXCEL*********************************     
      JFileChooser chooser = new JFileChooser();ArrayList<String> list=new ArrayList<String>();          
      int showSaveDialog = chooser.showSaveDialog(this);String tpo="";      
      if (showSaveDialog == JFileChooser.CANCEL_OPTION) 
       return;      
      File selectedFile = chooser.getSelectedFile();
      if (!selectedFile.getName().endsWith(".xls")) 
       selectedFile = new File(selectedFile.toString() + ".xls");      
      if (selectedFile.exists()) {
       int showConfirmDialog = JOptionPane.showConfirmDialog(chooser, "El archivo " 
       + selectedFile.toString()+ "\nya existe ¿Desea Reemplazarlo?", "Exportar",
       JOptionPane.YES_NO_OPTION);
       
       if (showConfirmDialog == JOptionPane.NO_OPTION || showConfirmDialog == 
        JOptionPane.CLOSED_OPTION) 
        return;       
      }
      //**************CREAMOS UN ARCHIVO EXCEL*********************************   
            
      //******************************ESTABLECER EL LIBRO************************ 
      WritableWorkbook workbook = Workbook.createWorkbook(selectedFile);
      WritableSheet sheet = workbook.createSheet("VentasxDocumento", 0);
      WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
      WritableFont font0 = new WritableFont(WritableFont.ARIAL, 15, WritableFont.BOLD);
        WritableCellFormat cellFormat0 = new WritableCellFormat(font0);     
      WritableCellFormat cellFormat = new WritableCellFormat(font);     
      //******************************ESTABLECER EL LIBRO*************************
      
      //*******************CONSTRUIR LA CABECERA DEL REPORTE*******************      
      CellView cellView = new CellView();cellView.setAutosize(true);
      WritableFont font1 = new WritableFont(WritableFont.TIMES, 11);
      
      cellFormat.setAlignment(Alignment.CENTRE);cellFormat0.setAlignment(Alignment.CENTRE);
      cellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK); 
        
      WritableCellFormat cellFormat1 = new WritableCellFormat(font1);                 
      cellFormat1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);       
       sheet.mergeCells(1, 1, 9, 1);      
       cellFormat0.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK); 
      sheet.addCell(new Label(1, 1, "Reporte de Ventas", cellFormat0));control.fila=3;     
      
      sheet.addCell(new Label(1, control.fila, "Desde", cellFormat));sheet.setColumnView(1, cellView);
      sheet.addCell(new Label(2, control.fila, control.Formato_DMA(f_Desde.getDate()), cellFormat1));                  
      sheet.setColumnView(2, cellView);sheet.addCell(new Label(8, control.fila, "Hasta", cellFormat));            
      sheet.setColumnView(8, cellView);
      sheet.addCell(new Label(9, control.fila, control.Formato_DMA(F_Hasta.getDate()), cellFormat1));                  
      sheet.setColumnView(9, cellView);control.fila=5;int f=0;                  
      while(f<md2.getRowCount()){
          control.bandera=false;cellView.setAutosize(true);
          if(!md2.getValueAt(f, 3).toString().equalsIgnoreCase("")) {               
            if(md2.getValueAt(f, 4).toString().equalsIgnoreCase("Identificacion")){
             sheet.addCell(new Label(1, control.fila,md2.getValueAt(f, 0).toString(), cellFormat));           
             sheet.setColumnView(1, cellView);                
            }  
            else{                                          
              sheet.addCell(new Label(1, control.fila,md2.getValueAt(f, 0).toString(), cellFormat1));           
              sheet.setColumnView(1, cellView);                                               
            }
          }
          else{
            if(md2.getValueAt(f, 7).toString().length()==0){
              sheet.mergeCells(1, control.fila, 9, control.fila);         
            }              
            sheet.addCell(new Label(1, control.fila,md2.getValueAt(f, 1).toString(), cellFormat));           
            sheet.setColumnView(1, cellView);                  
          }
           if(md2.getValueAt(f, 4).toString().equalsIgnoreCase("Identificacion")){
             sheet.addCell(new Label(2, control.fila,md2.getValueAt(f, 1).toString(), cellFormat));   
             sheet.setColumnView(2, cellView);        
             sheet.addCell(new Label(3, control.fila,md2.getValueAt(f, 2).toString(), cellFormat));   
             sheet.setColumnView(3, cellView);
             sheet.addCell(new Label(4, control.fila,md2.getValueAt(f, 3).toString(), cellFormat));   
             sheet.setColumnView(4, cellView);        
             sheet.addCell(new Label(5, control.fila,md2.getValueAt(f, 4).toString(), cellFormat));   
             sheet.setColumnView(5, cellView);             
             sheet.addCell(new Label(6, control.fila,md2.getValueAt(f, 5).toString(), cellFormat));                
             sheet.setColumnView(6,45);                  
             sheet.addCell(new Label(7, control.fila,md2.getValueAt(f, 6).toString(), cellFormat));   
             sheet.setColumnView(7, cellView);
             sheet.addCell(new Label(8, control.fila,md2.getValueAt(f, 7).toString(), cellFormat));   
             sheet.setColumnView(8, cellView);     
             sheet.addCell(new Label(9, control.fila,md2.getValueAt(f, 8).toString(), cellFormat));   
             sheet.setColumnView(9, cellView);                    
           }
           else{             
             sheet.addCell(new Label(2, control.fila,md2.getValueAt(f, 1).toString(),
             cellFormat1));sheet.setColumnView(2, cellView);        
             sheet.addCell(new Label(3, control.fila,md2.getValueAt(f, 2).toString(),
             cellFormat1));  sheet.setColumnView(3, cellView);
             sheet.addCell(new Label(4, control.fila,md2.getValueAt(f, 3).toString(), cellFormat1));   
             sheet.setColumnView(4, cellView);        
             sheet.addCell(new Label(5, control.fila,md2.getValueAt(f, 4).toString(), cellFormat1));   
             sheet.setColumnView(5, cellView);
             sheet.addCell(new Label(6, control.fila,md2.getValueAt(f, 5).toString(), cellFormat1));                
             sheet.setColumnView(6,45);                  
             sheet.addCell(new Label(7, control.fila,md2.getValueAt(f, 6).toString(), cellFormat1));   
             sheet.setColumnView(7, cellView);
             sheet.addCell(new Label(8, control.fila,md2.getValueAt(f, 7).toString(), cellFormat1));   
             sheet.setColumnView(8, cellView);     
             sheet.addCell(new Label(9, control.fila,md2.getValueAt(f, 8).toString(), cellFormat1));   
             sheet.setColumnView(9, cellView);  
           }
          f++;control.fila++;             
          //}
      }
      //}      
      /**********************CERRANDO EL LIBRO****************************/
      workbook.write();workbook.close();    
      JOptionPane.showMessageDialog(null, "Termino la migración");
      /**********************CERRANDO EL LIBRO****************************/        
      }
     catch(Exception e){}       
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        f_Desde = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        F_Hasta = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        cboTipo = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        bCancelar = new javax.swing.JButton();
        bExcel = new javax.swing.JButton();
        bImprimir = new javax.swing.JButton();
        bSalir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Criterios de Selección", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setText("Tipo");
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        f_Desde.setDateFormatString("dd-MM-yyyy");
        jPanel5.add(f_Desde, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 30, 130, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setText("Hasta");
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, -1, -1));

        F_Hasta.setDateFormatString("dd-MM-yyyy");
        jPanel5.add(F_Hasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 30, 130, -1));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setText("Desde");
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, -1, -1));

        jPanel5.add(cboTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 170, -1));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 770, 70));

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
        jPanel2.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 110, 45));

        bExcel.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        bExcel.setMnemonic('x');
        bExcel.setText("Excel");
        bExcel.setMargin(new java.awt.Insets(2, 1, 2, 14));
        bExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExcelActionPerformed(evt);
            }
        });
        jPanel2.add(bExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 110, 45));

        bImprimir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Print.png"))); // NOI18N
        bImprimir.setMnemonic('i');
        bImprimir.setText("Imprimir");
        bImprimir.setEnabled(false);
        bImprimir.setMargin(new java.awt.Insets(2, 1, 2, 14));
        bImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bImprimirActionPerformed(evt);
            }
        });
        jPanel2.add(bImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 110, 45));

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
        jPanel2.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 110, 45));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 768, 60));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 10, 20));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
     Cancelar();
    }//GEN-LAST:event_bCancelarActionPerformed
    private void bExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExcelActionPerformed
      LlenarModelo();Exportar_a_Excel();
    }//GEN-LAST:event_bExcelActionPerformed
    private void bImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bImprimirActionPerformed
     //Imprimir();
     
    }//GEN-LAST:event_bImprimirActionPerformed
    private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
           dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_bSalirActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser F_Hasta;
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bExcel;
    private javax.swing.JButton bImprimir;
    private javax.swing.JButton bSalir;
    private javax.swing.JComboBox cboTipo;
    private com.toedter.calendar.JDateChooser f_Desde;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
