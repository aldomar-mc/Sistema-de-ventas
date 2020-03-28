package Ventanas;

import Clases.*;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Usuarios extends javax.swing.JInternalFrame {

    Controlador control = new Controlador();
    Mimodelo modelo = new Mimodelo();
    String dato = "", pass = "", Codigo = "";

    /**
     * Creates new form Vendedores
     */
    public Usuarios() {
        initComponents();
        bloquear(false);
        setTitle("Los Usuarios");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tVendedores.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"Código", "Dni", "Nombre", "Dirección", "Telefono", "Login", "Tipo Usuario","nom","ape"});
        control.LlenarCombo(cbTipoUsu, "SELECT * FROM tipousuario t;", 2);
        MostrarCliente();
        control.forma_table_ver(modelo, tVendedores);
    }

    public void bloquear(boolean a) {
        tNombre.setEnabled(a);
        tApellido.setEnabled(a);
        txDireccion.setEnabled(a);
        txDni.setEnabled(a);
        txTelefono.setEditable(a);
        txUsuario.setEnabled(a);
        pwClave.setEnabled(a);
        cbTipoUsu.setEnabled(a);

    }

    public void limpiar() {
        cbTipoUsu.setSelectedIndex(-1);
        txDireccion.setText("");
        txDni.setText("");
        txTelefono.setText("");
        txUsuario.setText("");
        pwClave.setText("");
        tNombre.setText("");
        tApellido.setText("");
    }

    public void Cancelar() {
        limpiar();
        bloquear(false);
        bCrear.setText("Crear");
        bModificar.setText("Editar");
        bCrear.setEnabled(true);
        bModificar.setEnabled(true);
        bEliminar.setEnabled(true);
    }

    public void MostrarCliente() {
        BuscarCliente();
    }

    public void BuscarCliente() {
        control.Sql = "SELECT idusuario,dni,nombre,dire,tel, nomusr,nomtpus, nom, ape "
                + "FROM vendedores  where nom like'"
                + txtbucarVendedor.getText() + "%' or dni like'" + txtbucarVendedor.getText() + "%' ";
        control.LlenarJTabla(modelo, control.Sql, 9);
        control.hideTableColumn(tVendedores, 7,8);
    }

    public void AgregarVendedor() {
        if (bCrear.getText().compareTo("Crear") == 0) {
            bloquear(true);
            tNombre.grabFocus();
            bEliminar.setEnabled(false);
            bModificar.setEnabled(false);
            bCrear.setText("Grabar");
            bCrear.setMnemonic('g');
        } else {
            if (verfica()) {
                if (control.Verificarconsulta("select * from usuario where nomusr='" + txUsuario.getText() + "';") == false) {
                    control.Sql = "select InsertaVendedor('" + txUsuario.getText() + "','" + pwClave.getText()
                            + "','" + cbTipoUsu.getSelectedItem().toString() + "','" + control.ObtenerDato("sede", "nomse",
                                    Controlador.sede, 1) + "');";
                    dato = control.DevolverRegistroDto(control.Sql, 1);

                    control.Sql = "insert into datosusuarios(nom,ape,dire,tel, dni, Usuario_idusuario)values"
                            + "('" + control.PriLtrasMayuscula(tNombre.getText()) + "','"+tApellido.getText()+ "','" + txDireccion.getText() + "','"
                            + txTelefono.getText() + "','" + txDni.getText() + "','" + dato + "');";
                    control.CrearRegistro(control.Sql);
                    bloquear(false);
                    limpiar();
                    bEliminar.setEnabled(true);
                    bCrear.setMnemonic('c');
                    bModificar.setEnabled(true);
                    bCrear.setText("Crear");
                    control.LimTabla(modelo);
                    MostrarCliente();
                } else {
                    JOptionPane.showMessageDialog(null, "El usuario ya Existe!!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Informacion Incompleta!!");
            }
        }
    }

    public boolean verfica() {
        boolean a = false;
        if (tNombre.getText().trim().length() > 0 && txDireccion.getText().trim().length() > 0 && txDni.getText().trim().length() > 0 && txTelefono.getText().trim().length() > 0 && txUsuario.getText().trim().length() > 0 && cbTipoUsu.getSelectedIndex() != -1) {
            a = true;
        }
        return a;
    }

    public void EliminarVendedor() {
        if (tVendedores.getSelectedRowCount() == 1) {
            if (JOptionPane.showConfirmDialog(null, "Desea eliminar este Usuario? Se borrarán todos los datos anexos!!", "", JOptionPane.YES_NO_OPTION) == 0) {
                control.Sql = "SELECT * FROM venta WHERE Usuario_idusuario='" + modelo.getValueAt(tVendedores.getSelectedRow(), 0) + "'";
                if (!control.Verificandoconsulta(control.Sql)) {
                    control.Sql = "SELECT * FROM cotizacion WHERE idusuario='" + modelo.getValueAt(tVendedores.getSelectedRow(), 0) + "'";
                    if (!control.Verificandoconsulta(control.Sql)) {
                        control.Sql = "DELETE FROM permisosusuario WHERE idusuario='" + modelo.getValueAt(tVendedores.getSelectedRow(), 0) + "'";
                        control.EliminarRegistro(control.Sql);
                        control.Sql = "DELETE FROM datosusuarios WHERE Usuario_idusuario='" + modelo.getValueAt(tVendedores.getSelectedRow(), 0) + "'";
                        control.EliminarRegistro(control.Sql);
                        control.Sql = "delete from usuario where idusuario='" + modelo.getValueAt(tVendedores.getSelectedRow(), 0) + "';";
                        control.EliminarRegistro(control.Sql);
                        limpiar();
                        MostrarCliente();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se puede eliminar el Usuario pues ya realizó transacciones");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede eliminar el Usuario pues ya realizó transacciones");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tiene Que Seleccionar un Clinete para Eliminar", "", 2);
        }
    }

    public void EditarVendedor() {
        if (bModificar.getText().compareTo("Editar") == 0) {
            if (tVendedores.getSelectedRowCount() == 1) {
                bloquear(true);
                tNombre.grabFocus();
                bCrear.setEnabled(false);
                bEliminar.setEnabled(false);
                Codigo = modelo.getValueAt(tVendedores.getSelectedRow(), 0).toString();
                tNombre.setText(modelo.getValueAt(tVendedores.getSelectedRow(), 7).toString());
                tApellido.setText(modelo.getValueAt(tVendedores.getSelectedRow(), 8).toString());
                txDireccion.setText(modelo.getValueAt(tVendedores.getSelectedRow(), 3).toString());
                txDni.setText(modelo.getValueAt(tVendedores.getSelectedRow(), 1).toString());
                txTelefono.setText(modelo.getValueAt(tVendedores.getSelectedRow(), 4).toString());
                txUsuario.setText(modelo.getValueAt(tVendedores.getSelectedRow(), 5).toString());
                cbTipoUsu.setSelectedItem(modelo.getValueAt(tVendedores.getSelectedRow(), 6).toString());
                pass = control.DevolverRegistroDto("select psw from usuario where idusuario='" + Codigo + "';", 1);
                pwClave.setText(pass);
                bModificar.setText("Grabar");
            } else {
                JOptionPane.showMessageDialog(null, "Selecione un Vendedor!!");
            }
        } else {
            if (verfica()) {
                control.Sql = "call UpdateVendedor('" + Codigo + "','" + txUsuario.getText() + "','" + pwClave.getText()
                        + "','" + control.PriLtrasMayuscula(tNombre.getText()) + "','" + control.PriLtrasMayuscula(tApellido.getText()) + "','" + txDireccion.getText() + "','"
                        + txTelefono.getText() + "','" + txDni.getText() + "');";
                control.EditarRegistro(control.Sql);
                control.Sql = "update usuario set Tipousuario_idTipousuario='" + control.DevolverRegistroDto("SELECT idTipousuario FROM tipousuario where nomtpus='"
                        + cbTipoUsu.getSelectedItem().toString() + "';", 1) + "' where idusuario='" + Codigo + "';";
                control.EditarRegistro(control.Sql);
                bloquear(false);
                limpiar();
                bCrear.setEnabled(true);
                bEliminar.setEnabled(true);
                bModificar.setText("Editar");
                control.LimTabla(modelo);
                MostrarCliente();
            } else {
                JOptionPane.showMessageDialog(null, "Datos incompletos!!!");
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        pwClave = new javax.swing.JPasswordField();
        txUsuario = new javax.swing.JTextField();
        txDni = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txDireccion = new javax.swing.JTextField();
        txTelefono = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbTipoUsu = new javax.swing.JComboBox();
        tNombre = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tApellido = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txtbucarVendedor = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tVendedores = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        bSalir = new javax.swing.JButton();
        bCrear = new javax.swing.JButton();
        bModificar = new javax.swing.JButton();
        bEliminar = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del usuario", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 102));
        jLabel10.setText("Dni");
        jLabel10.setName("jLabel10"); // NOI18N

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Usuario");
        jLabel6.setName("jLabel6"); // NOI18N

        pwClave.setName("pwClave"); // NOI18N

        txUsuario.setName("txUsuario"); // NOI18N

        txDni.setName("txDni"); // NOI18N
        txDni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txDniKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 102));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Clave");
        jLabel11.setName("jLabel11"); // NOI18N

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Dirección");
        jLabel5.setName("jLabel5"); // NOI18N

        txDireccion.setName("txDireccion"); // NOI18N
        txDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txDireccionKeyReleased(evt);
            }
        });

        txTelefono.setName("txTelefono"); // NOI18N
        txTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txTelefonoKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setText("Teléfono");
        jLabel8.setName("jLabel8"); // NOI18N

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Tipo de Usuario");
        jLabel3.setName("jLabel3"); // NOI18N

        cbTipoUsu.setName("cbTipoUsu"); // NOI18N

        tNombre.setName("tNombre"); // NOI18N
        tNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tNombreKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tNombreKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 102));
        jLabel7.setText("Nombre");
        jLabel7.setName("jLabel7"); // NOI18N

        tApellido.setName("tApellido"); // NOI18N
        tApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tApellidoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tApellidoKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 51, 102));
        jLabel12.setText("Apellidos");
        jLabel12.setName("jLabel12"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel12))
                .addGap(47, 47, 47)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(tNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10))
                            .addComponent(tApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(txDni, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(356, 356, 356)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(363, 363, 363)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pwClave, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbTipoUsu, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbTipoUsu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pwClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 150));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de usuarios", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtbucarVendedor.setName("txtbucarVendedor"); // NOI18N
        txtbucarVendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucarVendedorKeyReleased(evt);
            }
        });
        jPanel4.add(txtbucarVendedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 350, -1));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 102));
        jLabel9.setText("Buscar");
        jLabel9.setName("jLabel9"); // NOI18N
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 20));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tVendedores.setForeground(new java.awt.Color(0, 51, 102));
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
        tVendedores.setName("tVendedores"); // NOI18N
        jScrollPane1.setViewportView(tVendedores);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 750, 190));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 780, 250));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel1.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(495, 10, 110, 40));

        bCrear.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCrear.setForeground(new java.awt.Color(0, 51, 102));
        bCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        bCrear.setMnemonic('c');
        bCrear.setText("Crear");
        bCrear.setName("bCrear"); // NOI18N
        bCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCrearActionPerformed(evt);
            }
        });
        jPanel1.add(bCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 10, 110, 40));

        bModificar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bModificar.setForeground(new java.awt.Color(0, 51, 102));
        bModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Edit.png"))); // NOI18N
        bModificar.setMnemonic('e');
        bModificar.setText("Editar");
        bModificar.setName("bModificar"); // NOI18N
        bModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bModificarActionPerformed(evt);
            }
        });
        jPanel1.add(bModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 10, 110, 40));

        bEliminar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bEliminar.setForeground(new java.awt.Color(0, 51, 102));
        bEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        bEliminar.setMnemonic('l');
        bEliminar.setText("Eliminar");
        bEliminar.setName("bEliminar"); // NOI18N
        bEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(bEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 10, 110, 40));

        bCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCancelar.setForeground(new java.awt.Color(0, 51, 102));
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setMnemonic('a');
        bCancelar.setText("Cancelar");
        bCancelar.setName("bCancelar"); // NOI18N
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 10, 110, 40));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 620, 60));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 480));

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
    dispose();
}//GEN-LAST:event_bSalirActionPerformed
private void bCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCrearActionPerformed
    AgregarVendedor();
}//GEN-LAST:event_bCrearActionPerformed
private void bModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModificarActionPerformed
    EditarVendedor();
}//GEN-LAST:event_bModificarActionPerformed
private void bEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEliminarActionPerformed
    EliminarVendedor();
}//GEN-LAST:event_bEliminarActionPerformed
private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
    Cancelar();
}//GEN-LAST:event_bCancelarActionPerformed
    private void txtbucarVendedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarVendedorKeyReleased
        MostrarCliente();
    }//GEN-LAST:event_txtbucarVendedorKeyReleased
    private void tNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tNombreKeyReleased
        control.Mayusculas(tNombre);
    }//GEN-LAST:event_tNombreKeyReleased
    private void txDireccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txDireccionKeyReleased
        control.Mayusculas(txDireccion);
    }//GEN-LAST:event_txDireccionKeyReleased
    private void tNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tNombreKeyTyped
        control.SoloLetras(evt);
    }//GEN-LAST:event_tNombreKeyTyped
    private void txTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txTelefonoKeyTyped
        control.Solonumeros(evt);
        control.Longitudtx(txTelefono, evt, 13);
    }//GEN-LAST:event_txTelefonoKeyTyped
    private void txDniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txDniKeyTyped
        control.Solonumeros(evt);
        control.Longitudtx(txDni, evt, 8);
    }//GEN-LAST:event_txDniKeyTyped

    private void tApellidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tApellidoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tApellidoKeyReleased

    private void tApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tApellidoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_tApellidoKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bCrear;
    private javax.swing.JButton bEliminar;
    private javax.swing.JButton bModificar;
    private javax.swing.JButton bSalir;
    private javax.swing.JComboBox cbTipoUsu;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPasswordField pwClave;
    private javax.swing.JTextField tApellido;
    private javax.swing.JTextField tNombre;
    private javax.swing.JTable tVendedores;
    private javax.swing.JTextField txDireccion;
    private javax.swing.JTextField txDni;
    private javax.swing.JTextField txTelefono;
    private javax.swing.JTextField txUsuario;
    private javax.swing.JTextField txtbucarVendedor;
    // End of variables declaration//GEN-END:variables
}
