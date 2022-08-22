/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DAO.IReserva;
import DAO.ReservaDAO;
import DB.ConexionDB;
import VO.Reserva;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class ListaReserva extends javax.swing.JFrame {

    IReserva reservaDao = new ReservaDAO();

    private DefaultTableModel dtm = new DefaultTableModel();

    public ListaReserva() {
        initComponents();
        
        this.setLocationRelativeTo(null);
        
        tblReserva.setModel(dtm);
        txtId.setVisible(false);
        
        tblReserva.addMouseListener( new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt){
                JTable table = (JTable) Mouse_evt.getSource();
                Point point = Mouse_evt.getPoint();
                int row = table.rowAtPoint(point);
                if(Mouse_evt.getClickCount()==1){
                    txtId.setText(tblReserva.getValueAt(tblReserva.getSelectedRow(), 0).toString());
                    txtCancha.setText(tblReserva.getValueAt(tblReserva.getSelectedRow(), 3).toString());
                    txtFechaInsc.setText(tblReserva.getValueAt(tblReserva.getSelectedRow(), 4).toString());
                    txtFechaInicio.setText(tblReserva.getValueAt(tblReserva.getSelectedRow(), 5).toString());
                    txtFechaSalida.setText(tblReserva.getValueAt(tblReserva.getSelectedRow(), 6).toString());
                }
            }
            
        });
        
        dtm.addColumn("ID");
        dtm.addColumn("DNI");
        dtm.addColumn("Cliente");
        dtm.addColumn("Cancha");
        dtm.addColumn("Fechas Inscripcion");
        dtm.addColumn("Fecha Inicio");
        dtm.addColumn("Fecha Salida");
        
        
        tblReserva.getColumnModel().getColumn(0).setMaxWidth(0);
        tblReserva.getColumnModel().getColumn(0).setMaxWidth(0);
        
        tblReserva.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        tblReserva.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        
        listar();
        
        /*Botones*/
        ImageIcon iconoAtras = new ImageIcon(getClass().getResource("/imagenes/back.png"));
        ImageIcon iconoBuscar = new ImageIcon(getClass().getResource("/imagenes/search.png"));
        ImageIcon iconoEditar = new ImageIcon(getClass().getResource("/imagenes/edit.png"));
        ImageIcon iconoGuardar = new ImageIcon(getClass().getResource("/imagenes/register.png"));
        ImageIcon iconoCancelar = new ImageIcon(getClass().getResource("/imagenes/cancel.png"));

        int ancho = 15; // ancho en pixeles que tendra el icono escalado
        int alto = -1; // alto (para que conserve la proporcion pasamos -1)

        // Obtiene un icono en escala con las dimensiones especificadas
        ImageIcon iconoEscala = new ImageIcon(iconoAtras.getImage().getScaledInstance(ancho, alto, java.awt.Image.SCALE_DEFAULT));
        ImageIcon iconoEscala2 = new ImageIcon(iconoBuscar.getImage().getScaledInstance(ancho, alto, java.awt.Image.SCALE_DEFAULT));
        ImageIcon iconoEscEditar = new ImageIcon(iconoEditar.getImage().getScaledInstance(ancho, alto, java.awt.Image.SCALE_DEFAULT));
        ImageIcon iconoEscGuardar = new ImageIcon(iconoGuardar.getImage().getScaledInstance(ancho, alto, java.awt.Image.SCALE_DEFAULT));
        ImageIcon iconoEscCancel = new ImageIcon(iconoCancelar.getImage().getScaledInstance(ancho, alto, java.awt.Image.SCALE_DEFAULT));

        btnAtras.setIcon(iconoEscala);
        btnBuscar.setIcon(iconoEscala2);
        btnEditar.setIcon(iconoEscEditar);
        btnGuardar.setIcon(iconoEscGuardar);
        btnCancelar.setIcon(iconoEscCancel);
    }
    
    public void listar(){
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData rsmd;
        Connection conn;
        int columnas;
        
        try {
            conn = ConexionDB.MySQL8();
            ps = conn.prepareStatement("select idreserva,dni,concat(nombre,' ',apellidos),cancha, fechaInscripcion ,fechaInicio, fechaFin from reserva inner join cliente on reserva.cliente = cliente.idCliente");

            rs = ps.executeQuery();
            rsmd = (ResultSetMetaData) rs.getMetaData();
            columnas = rsmd.getColumnCount();
            
            while(rs.next()){
                Object[] fila = new Object[columnas];
                for(int indice =0; indice<columnas; indice++){
                    fila[indice] = rs.getObject(indice + 1);
                }
                dtm.addRow(fila);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public DefaultTableModel buscarFecha(String buscar){
        
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData rsmd;
        Connection conn;
        int columnas;
        
        
        try {
            conn = ConexionDB.MySQL8();
            ps = conn.prepareStatement("select idreserva,dni,concat(nombre,' ',apellidos),cancha,fechaInscripcion ,fechaInicio, fechaFin from reserva inner join cliente on reserva.cliente = cliente.idCliente where fechaInicio like '%"+buscar+"%' order by fechaInicio");
            
            //ps.setString(1, buscar);

            rs = ps.executeQuery();
            rsmd = (ResultSetMetaData) rs.getMetaData();
            columnas = rsmd.getColumnCount();
            
            while(rs.next()){
                Object[] fila = new Object[columnas];
                for(int indice =0; indice<columnas; indice++){
                    fila[indice] = rs.getObject(indice + 1);
                }
                dtm.addRow(fila);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dtm;
        
    }
    
    private DefaultTableModel buscarDNI(String buscar){
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData rsmd;
        Connection conn;
        int columnas;
        
        
        try {
            conn = ConexionDB.MySQL8();
            ps = conn.prepareStatement("select idreserva,dni,concat(nombre,' ',apellidos),cancha,fechaInscripcion ,fechaInicio, fechaFin from reserva inner join cliente on reserva.cliente = cliente.idCliente where dni like '%"+buscar+"%' order by fechaInicio");
            
            //ps.setString(1, buscar);

            rs = ps.executeQuery();
            rsmd = (ResultSetMetaData) rs.getMetaData();
            columnas = rsmd.getColumnCount();
            
            while(rs.next()){
                Object[] fila = new Object[columnas];
                for(int indice =0; indice<columnas; indice++){
                    fila[indice] = rs.getObject(indice + 1);
                }
                dtm.addRow(fila);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dtm;
        
    }
    
    private void limpiar() {
        for (int i = 0; i < tblReserva.getRowCount(); i++) {
            dtm.removeRow(i);
            i -= 1;
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnAtras = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReserva = new javax.swing.JTable();
        btnBuscar = new javax.swing.JButton();
        txtDni = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtFechaInsc = new javax.swing.JTextField();
        txtFechaInicio = new javax.swing.JTextField();
        txtFechaSalida = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCancha = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        txtId = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 204));
        setForeground(new java.awt.Color(204, 255, 204));

        jPanel1.setBackground(new java.awt.Color(232, 249, 253));

        btnAtras.setBackground(new java.awt.Color(89, 206, 143));
        btnAtras.setText("Atras");
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 30, 0));
        jLabel2.setText("LISTA DE RESERVAS");

        jDateChooser1.setDateFormatString("yyyy-MM-dd");
        jDateChooser1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDateChooser1KeyReleased(evt);
            }
        });

        jLabel1.setText("Fecha:");

        tblReserva.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblReserva);

        btnBuscar.setBackground(new java.awt.Color(89, 206, 143));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        txtDni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDniActionPerformed(evt);
            }
        });
        txtDni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDniKeyReleased(evt);
            }
        });

        jLabel3.setText("DNI:");

        btnEditar.setBackground(new java.awt.Color(89, 206, 143));
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        jLabel4.setText("Fecha Inscripcion:");

        jLabel5.setText("Fecha Inicio:");

        jLabel6.setText("Fecha Salida:");

        txtFechaInsc.setEditable(false);
        txtFechaInsc.setBackground(new java.awt.Color(236, 204, 178));

        txtFechaInicio.setEditable(false);
        txtFechaInicio.setBackground(new java.awt.Color(236, 204, 178));
        txtFechaInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaInicioActionPerformed(evt);
            }
        });

        txtFechaSalida.setEditable(false);
        txtFechaSalida.setBackground(new java.awt.Color(236, 204, 178));

        jLabel7.setText("Cancha:");

        txtCancha.setEditable(false);
        txtCancha.setBackground(new java.awt.Color(236, 204, 178));

        btnGuardar.setBackground(new java.awt.Color(89, 206, 143));
        btnGuardar.setText("Guardar Reserva");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(255, 30, 0));
        btnCancelar.setText("Cancelar Reserva");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(146, 146, 146)
                                .addComponent(jLabel2)
                                .addGap(58, 58, 58)
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7))
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtFechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                                    .addComponent(txtCancha))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBuscar))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addComponent(jLabel4))
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtFechaInsc, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFechaSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnEditar)
                                .addGap(48, 48, 48))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnCancelar)
                                .addGap(35, 35, 35))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnGuardar)
                                .addContainerGap())))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAtras))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnBuscar)
                        .addComponent(btnEditar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFechaInsc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(txtCancha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardar)
                            .addComponent(txtFechaSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jDateChooser1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateChooser1KeyReleased
        // TODO add your handling code here:
        buscarFecha(jDateChooser1.toString());
    }//GEN-LAST:event_jDateChooser1KeyReleased

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        limpiar();
        String fechaInicio = ((JTextField)jDateChooser1.getDateEditor().getUiComponent()).getText();
        buscarFecha(fechaInicio);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        // TODO add your handling code here:
        ReservaJFrame abrir = new ReservaJFrame();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnAtrasActionPerformed

    private void txtDniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDniActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDniActionPerformed

    private void txtDniKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniKeyReleased
        // TODO add your handling code here:
        limpiar();
        String dni = txtDni.getText().toString();
        buscarDNI(dni);
    }//GEN-LAST:event_txtDniKeyReleased

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
       
        //DateTimeFormatter fechaActual = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").format(Calendar.getInstance().getTime());
        //LocalDateTime inscripcion = LocalDateTime.parse(txtFechaInsc.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        LocalDateTime inscripcion = LocalDateTime.parse(timeStamp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        //LocalDateTime inscripcion = LocalDateTime.parse(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S").format(LocalDateTime.now()));
        LocalDateTime inicio = LocalDateTime.parse(txtFechaInicio.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        
        long diff = ChronoUnit.DAYS.between(inscripcion, inicio);
        
        if(diff<2){
            JOptionPane.showMessageDialog(null, "El plazo de cambio debe ser mayor a 48 horas","Error", JOptionPane.ERROR_MESSAGE);
        }else{
            //Cambiar a modo Editable
            txtCancha.setEditable(true); 
            txtFechaInsc.setEditable(true);       
            txtFechaInicio.setEditable(true);
            txtFechaSalida.setEditable(true);
            
            //Cambiar color de fondo
            txtCancha.setBackground(Color.WHITE);
            txtFechaInsc.setBackground(Color.WHITE);
            txtFechaInicio.setBackground(Color.WHITE);
            txtFechaSalida.setBackground(Color.WHITE);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void txtFechaInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaInicioActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        try {
            String cancha = txtCancha.getText();
            String fechaInsc = txtFechaInsc.getText();
            String fechaInicio = txtFechaInicio.getText();
            String fechaSalida = txtFechaSalida.getText();
            Integer id = Integer.parseInt(txtId.getText());
                   
            
            Reserva reserva = new Reserva();
            
            reserva.setIdReserva(id);
            reserva.setCancha(cancha);
            reserva.setFechaInscripcion(fechaInsc);
            reserva.setFechaInicio(fechaInicio);
            reserva.setFechaFin(fechaSalida);
            
            reservaDao.editarReserva(reserva);
            
            JOptionPane.showMessageDialog(null, "Reserva Actualizada");
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        int id = Integer.parseInt(txtId.getText());
        
        reservaDao.delete(id);
        JOptionPane.showMessageDialog(null, "Reserva anulada");
        
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ListaReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListaReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListaReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListaReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListaReserva().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardar;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblReserva;
    private javax.swing.JTextField txtCancha;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtFechaInicio;
    private javax.swing.JTextField txtFechaInsc;
    private javax.swing.JTextField txtFechaSalida;
    private javax.swing.JTextField txtId;
    // End of variables declaration//GEN-END:variables
}
