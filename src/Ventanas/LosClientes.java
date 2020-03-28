package Ventanas;

/**
 * ** @author @ldomar.mc *********
 */
import javax.swing.*;
import Clases.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.DefaultTableModel;

public class LosClientes extends javax.swing.JInternalFrame {

    /**
     * ***********************Atributos*****************************************
     */
    Controlador control = new Controlador();
    JOptionPane mensj = new JOptionPane();
    InfoGeneral info = new InfoGeneral();
    String tipoCli = "", DescriCli = "", NumCli = "",
            IdCli = "", Cliente = "";
    Mimodelo modelo = new Mimodelo();

    /**
     * ***********************Atributos*****************************************
     */
    /**
     * ***************************METODOS*************************************
     */
    public LosClientes() {
        initComponents();
        setTitle("Los Clientes");
        setLocation(120, 80);
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tClientes.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"Código", "Cliente",
            "Dirección", "Telefono", "Tipo", "Tipo Identificación", "Número"});
        control.hideTableColumn(tClientes, 0);
        control.setWidthTableColumn(tClientes, 350, 1, 2);
        control.setWidthTableColumn(tClientes, 50, 3, 4, 5, 6);
        textoBloc(false);
        txtDni.setEnabled(false);
        txtRuc.setEnabled(false);
        MostrarCliente();
        control.forma_table_ver(modelo, tClientes);
        cbTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CambiarTipo();
            }
        });
    }

    public void textoBloc(boolean b) {
        txtCliente.setEnabled(b);
        txtDireccion.setEnabled(b);
        txtTelefono.setEnabled(b);
        cbTipo.setEnabled(b);
        bCrear.setText("Crear");
        bModificar.setText("Editar");
    }

    public void MostrarCliente() {
        BuscarCliente();
    }

    public void BuscarCliente() {
        control.Sql = "SELECT idcliente, nomclie,dir,fono,tipo, desident, numident FROM "
                + "vta_cliente v where nomclie like'%" + txtBuscarCliente.getText() + "%' or numident"
                + " like'%" + txtBuscarCliente.getText() + "%' order by idcliente desc ";
        control.LlenarJTabla(modelo, control.Sql, 7);
    }

    public boolean verificarDatos() {
        boolean a = false;
        if (txtCliente.getText().trim() != null) {
            int tip = cbTipo.getSelectedIndex();
            if (tip == 0 && txtDni.getText().trim() != null) {
                a = true;
            }
            if (tip == 1 && txtRuc.getText().trim() != null) {
                a = true;
            }
        }
        return a;
    }

    public void limpiar() {
        txtCliente.setText(null);
        txtDireccion.setText(null);
        txtDni.setText(null);
        txtRuc.setText(null);
        txtTelefono.setText(null);
        cbTipo.setSelectedIndex(-1);
    }

    public void ControlEventoIdentificacion(JComboBox cb) {
        if (cb.getSelectedIndex() == -1) {
            return;
        }
        if (cb.getSelectedItem().toString().equalsIgnoreCase("Natural")) {
            if (txtDni.getText().trim().length() < 8) {
                JOptionPane.showMessageDialog(null, "La identificacion debe tener 8 digitos");
                txtDni.grabFocus();
                return;
            }
        } else {
            if (txtRuc.getText().trim().length() < 11) {
                JOptionPane.showMessageDialog(null, "La identificacion debe tener 11 digitos");
                txtRuc.grabFocus();
                return;
            }
        }
    }

    public boolean ControlEventoIdentificacionbool(JComboBox cb) {
        control.bandera = true;
        if (cb.getSelectedIndex() == -1) {
            return control.bandera;
        }
        if (cb.getSelectedItem().toString().equalsIgnoreCase("Natural")) {
            if (txtDni.getText().trim().length() < 8) {
                JOptionPane.showMessageDialog(null, "La identificacion debe tener 8 digitos");
                txtDni.grabFocus();
                control.bandera = false;
                txtDni.grabFocus();//return control.bandera;
            }
        } else {
            if (txtRuc.getText().trim().length() < 11) {
                JOptionPane.showMessageDialog(null, "La identificacion debe tener 11 digitos");
                txtRuc.grabFocus();
                control.bandera = false;
                control.MarcarTexto(txtRuc);//return control.bandera;
            }
        }
        return control.bandera;
    }

    public void CrearCliente() {
        ControlEventoIdentificacion(cbTipo);
        if (bCrear.getText().compareTo("Crear") == 0) {
            textoBloc(true);
            txtCliente.grabFocus();
            bCrear.setText("Grabar");
            bCrear.setMnemonic('g');
            bEliminar.setEnabled(false);
            bModificar.setEnabled(false);
        } else {
            if (verificarDatos()) {
                control.Sql = "select * from cliente where nomclie='" + txtCliente.getText() + "'";
                if (control.Verificandoconsulta(control.Sql)) {
                    JOptionPane.showMessageDialog(rootPane, "El Cliente esta repetido");
                    txtCliente.grabFocus();
                } else {
                    if (DescriCli.compareTo("Ruc") == 0) {
                        if (txtRuc.getText().trim().length() != 11) {
                            JOptionPane.showMessageDialog(null, "El Ruc debe tener 11 digitos y este tiene "
                                    + txtRuc.getText().trim().length());
                            txtRuc.grabFocus();
                            return;
                        }
                        NumCli = txtRuc.getText();
                    } else {
                        if (txtDni.getText().trim().length() < 8) {
                            JOptionPane.showMessageDialog(null, "El dni debe tener 8 digitos y este tiene "
                                    + txtDni.getText().trim().length());
                            txtDni.grabFocus();
                            return;
                        }
                        NumCli = txtDni.getText();
                    }
                    info.setRucc(NumCli);
                    control.Sql = "call InsertarCliente('" + txtCliente.getText().toUpperCase() + "',"
                            + "'" + txtDireccion.getText() + "','" + txtTelefono.getText() + "',"
                            + "'" + tipoCli + "','" + DescriCli + "','" + NumCli + "',0,0)";
                    control.CrearRegistro(control.Sql);
                    bCrear.setText("Crear");
                    bCrear.setMnemonic('c');
                    bEliminar.setEnabled(true);
                    bModificar.setEnabled(true);
                    limpiar();
                    control.LimTabla(modelo);
                    MostrarCliente();
                    textoBloc(false);
                    IdCli = modelo.getValueAt(0, 0).toString();
                    Cliente = modelo.getValueAt(0, 1).toString();
                    info.setPass(IdCli);
                }

                switch (info.control) {
                    case 1: {
                        this.dispose();
                        MisVentas.lbCliente.setText(Cliente);
                        control.Sql = "select * from cliente cl inner join identificacion idf on idf.idcliente=cl.idcliente order by 1 desc limit 1;";
                        MisVentas.idCliente = control.DevolverRegistroDto(control.Sql, 1);
                        MisVentas.lbIdentificacion.setText(control.DevolverRegistroDto(control.Sql, 7) + ": " + control.DevolverRegistroDto(control.Sql, 8));
                        MisVentas.txCliente.setText("");
                        info.setControlCliente(1);
                        break;
                    }
                    case 2: {
                        this.dispose();
                        info.setControlCliente(1);
                        Cotizaciondeproductos.lbCliente.setText(Cliente);
                        Cotizaciondeproductos.txtBuscarCliente.setText("");
                        break;
                    }
                    case 3: {
                        this.dispose();
                        info.setControlCliente(1);
                        Facturarservicios.lbCliente.setText(Cliente);
                        Facturarservicios.txtBucarCliente.setText("");
                        break;
                    }
                }

                /*if(info.control==1){   
                 this.dispose();  Ventas.lbCliente.setText(Cliente);Ventas.txtBucarCliente.setText("");
                 info.setControlCliente(1);      
                 }
                 if(info.control==2){
                 this.dispose();
                 info.setControlCliente(1);Cotizaciondeproductos.lbCliente.setText(Cliente);
                 Cotizaciondeproductos.txtBuscarCliente.setText("");
                 }
                 if(info.control==3){
                 this.dispose();
                 info.setControlCliente(1);Facturarservicios.lbCliente.setText(Cliente);
                 Facturarservicios.txtBucarCliente.setText("");
                 }
                 */
       //else{
                //else{
                //}
                //}
            } else {
                mensj.showMessageDialog(null, "Datos Incompletos");
                txtCliente.grabFocus();
            }
        }
    }

    public void EditarCliente() {
        if (!ControlEventoIdentificacionbool(cbTipo)) {
            return;
        }
        if (bModificar.getText().compareTo("Editar") == 0) {
            if (tClientes.getSelectedRowCount() == 1) {
                bCrear.setEnabled(false);
                bEliminar.setEnabled(false);
                textoBloc(true);
                bModificar.setText("Grabar");
                bModificar.setMnemonic('g');
                MostarDatosClinete();
                txtCliente.grabFocus();
            } else {
                mensj.showMessageDialog(null, "Tiene que selecionar un Cliente para Editarlo", "", 2);
            }
        } else {
            ActualizarCliente();
            bModificar.setText("Editar");
            bModificar.setMnemonic('e');
            bCrear.setEnabled(true);
            bEliminar.setEnabled(true);
            tClientes.setEnabled(true);
            limpiar();
            textoBloc(false);
            txtDni.setEnabled(false);
            txtRuc.setEnabled(false);
            txtBuscarCliente.grabFocus();
        }
    }

    public void ActualizarCliente() {
        if (verificarDatos()) {
            control.Sql = "select * from cliente where nomclie='" + txtCliente.getText() + "' and idCliente<>" + IdCli;
            if (control.Verificandoconsulta(control.Sql)) {
                JOptionPane.showMessageDialog(rootPane, "El Cliente esta repetido");
                txtCliente.grabFocus();
            } else {
                if (DescriCli.compareTo("Ruc") == 0) {
                    NumCli = txtRuc.getText();
                } else {
                    NumCli = txtDni.getText();
                }
                control.Sql = "call InsertarCliente('" + control.PriLtrasMayuscula(txtCliente.getText().toUpperCase()) + "',"
                        + "'" + control.PriLtrasMayuscula(txtDireccion.getText()) + "','" + txtTelefono.getText() + "',"
                        + "'" + tipoCli + "','" + DescriCli + "','" + NumCli + "'," + IdCli + ",1)";
                control.CrearRegistro(control.Sql);
                bCrear.setText("Crear");
                bEliminar.setEnabled(true);
                bModificar.setEnabled(true);
            }
            limpiar();
            MostrarCliente();
            txtDni.setEnabled(false);
            txtRuc.setEnabled(false);
        } else {
            mensj.showMessageDialog(null, "Datos Incompletos");
        }
    }

    public void MostarDatosClinete() {
        IdCli = modelo.getValueAt(tClientes.getSelectedRow(), 0).toString();
        txtCliente.setText(modelo.getValueAt(tClientes.getSelectedRow(), 1).toString());
        txtDireccion.setText(modelo.getValueAt(tClientes.getSelectedRow(), 2).toString());
        txtTelefono.setText(modelo.getValueAt(tClientes.getSelectedRow(), 3).toString());
        cbTipo.setSelectedItem(modelo.getValueAt(tClientes.getSelectedRow(), 4).toString());
        if (modelo.getValueAt(tClientes.getSelectedRow(), 5).toString().compareTo("Ruc") == 0) {
            txtRuc.setEnabled(true);
            txtDni.setEnabled(false);
            txtRuc.setText(modelo.getValueAt(tClientes.getSelectedRow(), 6).toString());
        } else {
            txtDni.setText(modelo.getValueAt(tClientes.getSelectedRow(), 6).toString());
            txtDni.setEnabled(true);
            txtRuc.setEnabled(false);
        }
    }

    public void EliminarCliente() {
        if (tClientes.getSelectedRowCount() == 1) {
            IdCli = "" + modelo.getValueAt(tClientes.getSelectedRow(), 0);
            control.Sql = "SELECT * FROM cliente c, comporbanteservicio cb WHERE c.`idCliente`"
                    + "=cb.`idCliente` AND c.`idCliente`='" + IdCli + "'";
            if (!control.Verificandoconsulta(control.Sql)) {
                control.Sql = "SELECT * FROM cliente c, cotizacion co WHERE c.`idCliente`=co.idCliente"
                        + " AND c.`idCliente`='" + IdCli + "'";
                if (!control.Verificandoconsulta(control.Sql)) {
                    control.Sql = "SELECT * FROM cliente c, venta v WHERE c.`idCliente`=v.idCliente"
                            + " AND c.`idCliente`='" + IdCli + "'";
                    if (!control.Verificandoconsulta(control.Sql)) {
                        if (mensj.showConfirmDialog(null, "Desea eliminar este registro!!", "",
                                JOptionPane.YES_NO_OPTION) == 0) {
                            control.Sql = "call InsertarCliente('ef','sef','4563','dg','ga','sd',"
                                    + modelo.getValueAt(tClientes.getSelectedRow(), 0) + ",2)";
                            control.EliminarRegistro(control.Sql);
                            limpiar();
                            MostrarCliente();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No se puede Eliminar por que ya se hicieron"
                                + " registros con ese cliente");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede Eliminar por que ya se hicieron"
                            + " registros con ese cliente");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se puede Eliminar por que ya se hicieron registros con ese cliente");
            }
        } else {
            mensj.showMessageDialog(null, "Tiene Que Seleccionar un Clinete para Eliminar", "", 2);
        }
    }

    public void CambiarTipo() {
        if (cbTipo.getSelectedIndex() == 1) {
            txtDni.setEnabled(false);
            txtRuc.setEnabled(true);
            tipoCli = "Juridico";
            DescriCli = "Ruc";
            txtRuc.grabFocus();
        }
        if (cbTipo.getSelectedIndex() == 0) {
            txtDni.setEnabled(true);
            txtRuc.setEnabled(false);
            tipoCli = "Natural";
            DescriCli = "Dni";
            txtDni.grabFocus();
        }
    }

    /**
     * ***************************METODOS*************************************
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        bSalir = new javax.swing.JButton();
        bCrear = new javax.swing.JButton();
        bModificar = new javax.swing.JButton();
        bEliminar = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cbTipo = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        txtRuc = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        txtBuscarCliente = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tClientes = new javax.swing.JTable();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bSalir.setForeground(new java.awt.Color(0, 51, 102));
        bSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir.setMnemonic('s');
        bSalir.setText("Salir");
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });
        jPanel1.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 10, 110, 45));

        bCrear.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCrear.setForeground(new java.awt.Color(0, 51, 102));
        bCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        bCrear.setMnemonic('c');
        bCrear.setText("Crear");
        bCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCrearActionPerformed(evt);
            }
        });
        jPanel1.add(bCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 100, 45));

        bModificar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bModificar.setForeground(new java.awt.Color(0, 51, 102));
        bModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Edit.png"))); // NOI18N
        bModificar.setMnemonic('e');
        bModificar.setText("Editar");
        bModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bModificarActionPerformed(evt);
            }
        });
        jPanel1.add(bModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 10, 110, 45));

        bEliminar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bEliminar.setForeground(new java.awt.Color(0, 51, 102));
        bEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        bEliminar.setMnemonic('l');
        bEliminar.setText("Eliminar");
        bEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(bEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(465, 10, 110, 45));

        bCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCancelar.setForeground(new java.awt.Color(0, 51, 102));
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setMnemonic('a');
        bCancelar.setText("Cancelar");
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(675, 10, 110, 45));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 1069, 65));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 10), java.awt.Color.white), "Datos del cliente", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setText("Teléfono");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 85, -1, 15));

        txtDni.setNextFocusableComponent(txtDireccion);
        txtDni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDniKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniKeyTyped(evt);
            }
        });
        jPanel2.add(txtDni, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 50, 370, -1));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setText("Cliente");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, -1, 20));

        txtCliente.setNextFocusableComponent(cbTipo);
        txtCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtClienteKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtClienteKeyReleased(evt);
            }
        });
        jPanel2.add(txtCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 15, 370, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 153));
        jLabel5.setText("Dirección");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 85, -1, 20));

        txtDireccion.setNextFocusableComponent(txtTelefono);
        txtDireccion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDireccionFocusGained(evt);
            }
        });
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDireccionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDireccionKeyReleased(evt);
            }
        });
        jPanel2.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 85, 370, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 153));
        jLabel6.setText("Dni");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 50, 20, 15));

        cbTipo.setForeground(new java.awt.Color(0, 51, 102));
        cbTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Natural", "Juridico" }));
        cbTipo.setSelectedIndex(-1);
        cbTipo.setNextFocusableComponent(txtDireccion);
        cbTipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTipoItemStateChanged(evt);
            }
        });
        cbTipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbTipoKeyPressed(evt);
            }
        });
        jPanel2.add(cbTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 15, 370, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 153));
        jLabel7.setText("Tipo");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 15, 30, 15));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 153));
        jLabel8.setText("Ruc");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 30, 20));

        txtTelefono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTelefonoFocusGained(evt);
            }
        });
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });
        jPanel2.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 85, 370, -1));

        txtRuc.setNextFocusableComponent(txtDni);
        txtRuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRucKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRucKeyTyped(evt);
            }
        });
        jPanel2.add(txtRuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 370, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1070, 120));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Clientes", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 153))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBuscarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarClienteKeyReleased(evt);
            }
        });
        jPanel3.add(txtBuscarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 26, 850, -1));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 153));
        jLabel9.setText("Buscar");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 24, -1, 20));

        tClientes.setAutoCreateRowSorter(true);
        tClientes.setForeground(new java.awt.Color(0, 51, 102));
        tClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tClientes);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 1050, 310));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 185, 1070, 370));

        pack();
    }// </editor-fold>//GEN-END:initComponents
/**
     * ***************************EVENTOS******************************************
     */
private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
    dispose();
}//GEN-LAST:event_bSalirActionPerformed
private void bCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCrearActionPerformed
    CrearCliente();
}//GEN-LAST:event_bCrearActionPerformed
private void bModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModificarActionPerformed
    EditarCliente();
}//GEN-LAST:event_bModificarActionPerformed
private void bEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEliminarActionPerformed
    EliminarCliente();
}//GEN-LAST:event_bEliminarActionPerformed
private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
    textoBloc(false);
    limpiar();
    control.LimTabla(modelo);
    MostrarCliente();
    txtRuc.setEnabled(false);
    txtDni.setEnabled(false);
    bCrear.setEnabled(true);
    bEliminar.setEnabled(true);
    bModificar.setEnabled(true);
    tClientes.setEnabled(true);
    bCrear.setMnemonic('c');
    bModificar.setMnemonic('e');
}//GEN-LAST:event_bCancelarActionPerformed
    private void cbTipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTipoItemStateChanged

    }//GEN-LAST:event_cbTipoItemStateChanged
    private void txtBuscarClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarClienteKeyReleased
        MostrarCliente();
    }//GEN-LAST:event_txtBuscarClienteKeyReleased
    private void txtClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClienteKeyReleased
        //control.Mayusculas(txtCliente);        // TODO add your handling code here:
    }//GEN-LAST:event_txtClienteKeyReleased
    private void txtRucKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucKeyTyped
        control.Solonumeros(evt);
        control.Longitudtx(txtRuc, evt, 11);
    }//GEN-LAST:event_txtRucKeyTyped
    private void txtDniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniKeyTyped
        control.Solonumeros(evt);
        control.Longitudtx(txtDni, evt, 8);
    }//GEN-LAST:event_txtDniKeyTyped
    private void txtDireccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyReleased
        control.Mayusculas(txtDireccion);        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionKeyReleased
    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        control.Solonumeros(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoKeyTyped
    private void tClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tClientesMouseClicked
        if (evt.getClickCount() == 2) {
            IdCli = modelo.getValueAt(tClientes.getSelectedRow(), 0).toString();
            Cliente = modelo.getValueAt(tClientes.getSelectedRow(), 1).toString();
            info.setPass(IdCli);
            if (info.control == 1) {
                this.dispose();
                MisVentas.lbCliente.setText(Cliente);
                MisVentas.idCliente = modelo.getValueAt(tClientes.getSelectedRow(), 0).toString();
                MisVentas.dni_ruc = "";
                MisVentas.lbIdentificacion.setText(modelo.getValueAt(tClientes.getSelectedRow(), 5).toString() + ": " + modelo.getValueAt(
                        tClientes.getSelectedRow(), 6).toString());
                MisVentas.dni_ruc = modelo.getValueAt(tClientes.getSelectedRow(), 6).toString();
                MisVentas.txCliente.setText("");
                info.setControlCliente(1);
            }
            if (info.control == 2) {
                this.dispose();
                Cotizaciondeproductos.lbCliente.setText(Cliente);
                Cotizaciondeproductos.txtBuscarCliente.setText("");
                info.setControlCliente(1);
            }
        }
    }//GEN-LAST:event_tClientesMouseClicked
    private void txtClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClienteKeyPressed
        if (evt.getKeyChar() == 10) {
            cbTipo.grabFocus();
        }
    }//GEN-LAST:event_txtClienteKeyPressed
    private void txtRucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucKeyPressed
        if (evt.getKeyChar() == 10) {
            txtDireccion.grabFocus();
        }
    }//GEN-LAST:event_txtRucKeyPressed
    private void txtDniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniKeyPressed
        if (evt.getKeyChar() == 10) {
            txtDireccion.grabFocus();        // TODO add your handling code here:
        }
    }//GEN-LAST:event_txtDniKeyPressed
    private void txtTelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyPressed
        if ((evt.getKeyChar() == 10) && (txtTelefono.getText().trim().length() > 5)) {
            bCrear.doClick();   // TODO add your handling code here:
        }
    }//GEN-LAST:event_txtTelefonoKeyPressed
    private void txtDireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyPressed
        if (evt.getKeyChar() == 10) {
            txtTelefono.grabFocus();  // TODO add your handling code here:
        }
    }//GEN-LAST:event_txtDireccionKeyPressed
    private void cbTipoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbTipoKeyPressed
        /*if(evt.getKeyChar()==10){
         if(cbTipo.getSelectedIndex()==1){
         txtDni.setEnabled(false);txtRuc.setEnabled(true);
         tipoCli="Juridico";DescriCli="Ruc";txtRuc.grabFocus();
         }
         if(cbTipo.getSelectedIndex()==0){
         txtDni.setEnabled(true);txtRuc.setEnabled(false);
         tipoCli="Natural";DescriCli="Dni";txtDni.grabFocus();
         }
         }*/
    }//GEN-LAST:event_cbTipoKeyPressed
    private void txtTelefonoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTelefonoFocusGained
        ControlEventoIdentificacion(cbTipo);
    }//GEN-LAST:event_txtTelefonoFocusGained
    private void txtDireccionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDireccionFocusGained
        ControlEventoIdentificacion(cbTipo);
    }//GEN-LAST:event_txtDireccionFocusGained
    /**
     * ***************************EVENTOS******************************************
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    public static javax.swing.JButton bCrear;
    private javax.swing.JButton bEliminar;
    private javax.swing.JButton bModificar;
    private javax.swing.JButton bSalir;
    private javax.swing.JComboBox cbTipo;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tClientes;
    private javax.swing.JTextField txtBuscarCliente;
    public static javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtRuc;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
