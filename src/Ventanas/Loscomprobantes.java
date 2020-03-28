package Ventanas;
/**** @author Ing. Miguel Angel Silva Zapata.   **********/
import javax.swing.*;import Clases.*;
public class Loscomprobantes extends javax.swing.JInternalFrame {
    /*************************Atributos**********************/ 
    Mimodelo modelo=new Mimodelo();
    Controlador control = new Controlador();
    /*************************Atributos**********************/ 
    public Loscomprobantes() {
     initComponents();setTitle("Los comprobantes");
     this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
     tComprobante.setModel(modelo);modelo.setColumnIdentifiers(new String[] {"Código","Serie","Número","Tipo","Estado"});     
     control.LlenarCombo(cbTipoCompro, "SELECT * FROM tipocomprobante t", 2);
     MostrarCliente();
     Bloquear(false);
    }
     public void MostrarCliente(){
     BuscarCliente();   
    }    
    public void BuscarCliente(){
     control.Sql="SELECT idcomprobantes, serie,nume,tipcompr,esta FROM comprobantesvta  where serie like'"+
     txBuscar.getText()+"%'"; 
     
     control.LlenarJTabla(modelo,control.Sql,5); 
     
 //    JOptionPane.showMessageDialog(null,control.Sql);
    }
    public void EmitirComprobante(){
        if(bCrear.getText().compareTo("Crear")==0){
            bCrear.setText("Grabar");
            Bloquear(true);
//            bModificar.setEnabled(false);
  //          bEliminar.setEnabled(false);
            cbTipoCompro.grabFocus();
        }else{
            if(cbTipoCompro.getSelectedIndex()>-1 && txtCantidad.getText().trim().length()>0 && txtSerie.getText().trim().length()>0){
                String cn="";
                int iniSer=Integer.parseInt(txtSerie.getText());
                int idti=Integer.parseInt(control.DevolverRegistroDto("select idTipoComprobante from tipocomprobante where tipcompr='"+cbTipoCompro.getSelectedItem().toString()+"'", 1));
            for(int a=0;a<Integer.parseInt(txtCantidad.getText());a++){
                  cn=""+iniSer;      
                for(int as=cn.length();as<10;as++){
                    cn="0"+cn;
                }
                control.Sql="insert into comprobantes (serie,nume,esta,idTipoComprobante)values('"+cn+"','001','Activo','"+idti+"');";
                iniSer++;
                control.CrearRegistro(control.Sql);
            }
            control.LimTabla(modelo);
            MostrarCliente();
                bCrear.setText("Crear");
               // bModificar.setEnabled(true);
                //bEliminar.setEnabled(true);
                Cancelar();
            }
            else{
                JOptionPane.showMessageDialog(null,"Faltan datos!!");
            }
        }
    }
    public void Bloquear(boolean  b){
        txtCantidad.setEnabled(b);
        txtSerie.setEditable(b);
        cbTipoCompro.setEnabled(b);
    }
    public void Limpiar(){
        txtCantidad.setText("");
        txtSerie.setText("");
        cbTipoCompro.setSelectedIndex(-1);
    }
    public void Cancelar(){
        Limpiar();
        Bloquear(false);
        MostrarCliente();
       // bEliminar.setEnabled(true);
        //bModificar.setEnabled(true);
        bCrear.setText("Crear");
        //bModificar.setText("Editar");
    }
    public void EliminarComprobante(){
     if(tComprobante.getSelectedRowCount()==1){
         if(JOptionPane.showConfirmDialog(null, "Desea eliminar este registro!!","",JOptionPane.YES_NO_OPTION)==0){
             control.Sql="delete from comprobantesvta where idcomprobantes='"+modelo.getValueAt(tComprobante.getSelectedRow(), 0)+"';";
                control.EliminarRegistro(control.Sql );
                Limpiar();
                MostrarCliente();
         }
     }else{
         JOptionPane.showMessageDialog(null, "Tiene Que Seleccionar un Clinete para Eliminar","",2);
     }
 }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txBuscar = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tComprobante = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        bSalir = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        bCrear = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cbTipoCompro = new javax.swing.JComboBox();
        txtSerie = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setText("Buscar");
        jLabel2.setName("jLabel2"); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 118, -1, 20));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setText("Cantidad");
        jLabel3.setName("jLabel3"); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, -1, 20));

        txBuscar.setName("txBuscar"); // NOI18N
        txBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txBuscarKeyReleased(evt);
            }
        });
        getContentPane().add(txBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 115, 240, -1));

        txtCantidad.setName("txtCantidad"); // NOI18N
        getContentPane().add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 80, -1));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tComprobante.setForeground(new java.awt.Color(0, 51, 102));
        tComprobante.setModel(new javax.swing.table.DefaultTableModel(
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
        tComprobante.setName("tComprobante"); // NOI18N
        jScrollPane1.setViewportView(tComprobante);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 430, 210));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel1.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 90, 30));

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
        jPanel1.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 110, 30));

        bCrear.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCrear.setForeground(new java.awt.Color(0, 51, 102));
        bCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        bCrear.setText("Crear");
        bCrear.setName("bCrear"); // NOI18N
        bCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCrearActionPerformed(evt);
            }
        });
        jPanel1.add(bCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 50));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setText("Tipo de Documento");
        jLabel4.setName("jLabel4"); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        cbTipoCompro.setForeground(new java.awt.Color(0, 51, 102));
        cbTipoCompro.setName("cbTipoCompro"); // NOI18N
        getContentPane().add(cbTipoCompro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 140, -1));

        txtSerie.setName("txtSerie"); // NOI18N
        getContentPane().add(txtSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 80, 80, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 153));
        jLabel5.setText("Serie");
        jLabel5.setName("jLabel5"); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, -1, 20));

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
 dispose();
}//GEN-LAST:event_bSalirActionPerformed

private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
    Cancelar();
}//GEN-LAST:event_bCancelarActionPerformed

    private void txBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txBuscarKeyReleased
MostrarCliente();        // TODO add your handling code here:
    }//GEN-LAST:event_txBuscarKeyReleased

    private void bCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCrearActionPerformed
EmitirComprobante();
    }//GEN-LAST:event_bCrearActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bCrear;
    private javax.swing.JButton bSalir;
    private javax.swing.JComboBox cbTipoCompro;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tComprobante;
    private javax.swing.JTextField txBuscar;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtSerie;
    // End of variables declaration//GEN-END:variables
}
