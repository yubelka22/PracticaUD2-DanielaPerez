package gui;

import util.Util;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class Controlador implements ActionListener, ListSelectionListener, WindowListener {

    private Modelo modelo;
    private Vista vista;
    boolean refrescar;

    public Controlador(Modelo modelo, Vista vista) {
        this.modelo = modelo;
        this.vista = vista;
        modelo.conectar();
        setOptions();
        addActionListeners(this);
        addWindowListeners(this);
        refrescarTodo();
        iniciar();
    }

    private void refrescarTodo() {
        refrescarVinos();
        refrescarBodegas();
        refrescarEmpleados();
        refrescarVentas();
        refrescar = false;
    }

    private void addActionListeners(ActionListener listener) {
        //vinos
        vista.buttonVinosAnadir.addActionListener(listener);
        vista.buttonVinosAnadir.setActionCommand("anadirVino");
        vista.buttonVinosModificar.addActionListener(listener);
        vista.buttonVinosModificar.setActionCommand("modificarVino");
        vista.buttonVinosEliminar.addActionListener(listener);
        vista.buttonVinosEliminar.setActionCommand("eliminarVino");
        vista.buttonVinosBuscar.addActionListener(listener);
        vista.buttonVinosBuscar.setActionCommand("buscarVino");
        //bodegas
        vista.buttonBodegasAnadir.addActionListener(listener);
        vista.buttonBodegasAnadir.setActionCommand("anadirBodega");
        vista.buttonBodegasModificar.addActionListener(listener);
        vista.buttonBodegasModificar.setActionCommand("modificarBodega");
        vista.buttonBodegasEliminar.addActionListener(listener);
        vista.buttonBodegasEliminar.setActionCommand("eliminarBodega");
        vista.buttonBodegasBuscar.addActionListener(listener);
        vista.buttonBodegasBuscar.setActionCommand("buscarBodega");
        //empleados
        vista.buttonEmpleadosAnadir.addActionListener(listener);
        vista.buttonEmpleadosAnadir.setActionCommand("anadirEmpleado");
        vista.buttonEmpleadosModificar.addActionListener(listener);
        vista.buttonEmpleadosModificar.setActionCommand("modificarEmpleado");
        vista.buttonEmpleadosEliminar.addActionListener(listener);
        vista.buttonEmpleadosEliminar.setActionCommand("eliminarEmpleado");
        vista.buttonEmpleadosBuscar.addActionListener(listener);
        vista.buttonEmpleadosBuscar.setActionCommand("buscarEmpleado");
        //ventas
        vista.buttonVentasAnadir.addActionListener(listener);
        vista.buttonVentasAnadir.setActionCommand("anadirVenta");
        vista.buttonVentasModificar.addActionListener(listener);
        vista.buttonVentasModificar.setActionCommand("modificarVenta");
        vista.buttonVentasEliminar.addActionListener(listener);
        vista.buttonVentasEliminar.setActionCommand("eliminarVenta");
        vista.buttonVentasBuscar.addActionListener(listener);
        vista.buttonVentasBuscar.setActionCommand("buscarVenta");
        //menu
        vista.itemOpciones.addActionListener(listener);
        vista.itemSalir.addActionListener(listener);
        vista.itemDesconectar.addActionListener(listener);
        vista.btnValidate.addActionListener(listener);
        //option dialog
        vista.optionDialog.guardarButton.addActionListener(listener);
        vista.optionDialog.guardarButton.setActionCommand("guardarOpciones");
    }

    private void addWindowListeners(WindowListener listener) {
        vista.addWindowListener(listener);
    }

    void iniciar() {
        //seleccion tabla vinos
        vista.tablaVinos.setCellSelectionEnabled(true);
        ListSelectionModel sm1 = vista.tablaVinos.getSelectionModel();
        sm1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sm1.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !sm1.isSelectionEmpty()) {
                int row = vista.tablaVinos.getSelectedRow();
                vista.textNombreVino.setText(String.valueOf(vista.tablaVinos.getValueAt(row, 1)));
                vista.comboBoxTipoVino.setSelectedItem(String.valueOf(vista.tablaVinos.getValueAt(row, 2)));
                vista.textAnada.setText(String.valueOf(vista.tablaVinos.getValueAt(row, 3)));
                vista.comboBoxOrigenVino.setSelectedItem(String.valueOf(vista.tablaVinos.getValueAt(row, 4)));
                vista.textPrecioVino.setText(String.valueOf(vista.tablaVinos.getValueAt(row, 5)));
                vista.textStockVino.setText(String.valueOf(vista.tablaVinos.getValueAt(row, 6)));
                vista.comboBoxBodega.setSelectedItem(String.valueOf(vista.tablaVinos.getValueAt(row, 7)));
                vista.datePFechaIngreso.setDate((Date.valueOf(String.valueOf(vista.tablaVinos.getValueAt(row, 8)))).toLocalDate());
            }
        });

        //seleccion tabla bodegas
        vista.tablaBodegas.setCellSelectionEnabled(true);
        ListSelectionModel sm2 = vista.tablaBodegas.getSelectionModel();
        sm2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sm2.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !sm2.isSelectionEmpty()) {
                int row = vista.tablaBodegas.getSelectedRow();
                vista.textNombreBodega.setText(String.valueOf(vista.tablaBodegas.getValueAt(row, 1)));
                vista.comboBoxPaisBodega.setSelectedItem(String.valueOf(vista.tablaBodegas.getValueAt(row, 2)));
                vista.textTelefonoBodega.setText(String.valueOf(vista.tablaBodegas.getValueAt(row, 3)));
                vista.textEmailBodega.setText(String.valueOf(vista.tablaBodegas.getValueAt(row, 4)));
                vista.textDireccionBodega.setText(String.valueOf(vista.tablaBodegas.getValueAt(row, 5)));
            }
        });

        //seleccion tabla empleados
        vista.tablaEmpleados.setCellSelectionEnabled(true);
        ListSelectionModel sm3 = vista.tablaEmpleados.getSelectionModel();
        sm3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sm3.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !sm3.isSelectionEmpty()) {
                int row = vista.tablaEmpleados.getSelectedRow();
                vista.textNombreEmpleado.setText(String.valueOf(vista.tablaEmpleados.getValueAt(row, 1)));
                vista.textApellidosEmpleado.setText(String.valueOf(vista.tablaEmpleados.getValueAt(row, 2)));
                vista.textDNIEmpleado.setText(String.valueOf(vista.tablaEmpleados.getValueAt(row, 3)));
                vista.comboBoxCargoEmpleado.setSelectedItem(String.valueOf(vista.tablaEmpleados.getValueAt(row, 4)));
                vista.textTelefonoEmpleado.setText(String.valueOf(vista.tablaEmpleados.getValueAt(row, 5)));
                vista.textSalarioEmpleado.setText(String.valueOf(vista.tablaEmpleados.getValueAt(row, 6)));
                vista.datePFechaContratacion.setDate((Date.valueOf(String.valueOf(vista.tablaEmpleados.getValueAt(row, 7)))).toLocalDate());
            }
        });

        //seleccion tabla ventas
        vista.tablaVentas.setCellSelectionEnabled(true);
        ListSelectionModel sm4 = vista.tablaVentas.getSelectionModel();
        sm4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sm4.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !sm4.isSelectionEmpty()) {
                int row = vista.tablaVentas.getSelectedRow();
                vista.comboBoxEmpleadoVenta.setSelectedItem(String.valueOf(vista.tablaVentas.getValueAt(row, 1)));
                vista.comboBoxVinoVenta.setSelectedItem(String.valueOf(vista.tablaVentas.getValueAt(row, 2)));
                vista.textNombreCliente.setText(String.valueOf(vista.tablaVentas.getValueAt(row, 3)));
                vista.comboBoxEstadoVenta.setSelectedItem(String.valueOf(vista.tablaVentas.getValueAt(row, 4)));
                vista.comboBoxMetodoPago.setSelectedItem(String.valueOf(vista.tablaVentas.getValueAt(row, 5)));
                vista.datePFechaVenta.setDate((Date.valueOf(String.valueOf(vista.tablaVentas.getValueAt(row, 6)))).toLocalDate());
                vista.textTotalVenta.setText(String.valueOf(vista.tablaVentas.getValueAt(row, 7)));
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Opciones":
                vista.adminPasswordDialog.setVisible(true);
                break;
            case "Desconectar":
                modelo.desconectar();
                break;
            case "Salir":
                System.exit(0);
                break;
            case "abrirOpciones":
                if (String.valueOf(vista.adminPassword.getPassword()).equals(modelo.getAdminPassword())) {
                    vista.adminPassword.setText("");
                    vista.adminPasswordDialog.dispose();
                    vista.optionDialog.setVisible(true);
                } else {
                    Util.showErrorAlert("La contraseña introducida no es correcta.");
                }
                break;
            case "guardarOpciones":
                modelo.setPropValues(
                        vista.optionDialog.textIP.getText(),
                        vista.optionDialog.textUsuario.getText(),
                        String.valueOf(vista.optionDialog.pfContrasena.getPassword()),
                        String.valueOf(vista.optionDialog.pfContrasenaAdmin.getPassword()));
                vista.optionDialog.dispose();
                vista.dispose();
                new Controlador(new Modelo(), new Vista());
                break;
            //vinos
            case "anadirVino":
                try {
                    if (comprobarVinoVacio()) {
                        Util.showErrorAlert("Rellena todos los campos");
                    } else if (modelo.vinoNombreYaExiste(vista.textNombreVino.getText())) {
                        Util.showErrorAlert("Ese vino ya existe.");
                    } else {
                        int idbodega = Integer.parseInt(String.valueOf(vista.comboBoxBodega.getSelectedItem()).split(" ")[0]);
                        modelo.insertarVino(
                                vista.textNombreVino.getText(),
                                String.valueOf(vista.comboBoxTipoVino.getSelectedItem()),
                                Integer.parseInt(vista.textAnada.getText()),
                                String.valueOf(vista.comboBoxOrigenVino.getSelectedItem()),
                                Float.parseFloat(vista.textPrecioVino.getText()),
                                Integer.parseInt(vista.textStockVino.getText()),
                                idbodega,
                                vista.datePFechaIngreso.getDate());
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                }
                borrarCamposVinos();
                refrescarVinos();
                break;
            case "modificarVino":
                try {
                    if (comprobarVinoVacio()) {
                        Util.showErrorAlert("Rellena todos los campos");
                    } else {
                        int idbodega = Integer.parseInt(String.valueOf(vista.comboBoxBodega.getSelectedItem()).split(" ")[0]);
                        modelo.modificarVino(
                                vista.textNombreVino.getText(),
                                String.valueOf(vista.comboBoxTipoVino.getSelectedItem()),
                                Integer.parseInt(vista.textAnada.getText()),
                                String.valueOf(vista.comboBoxOrigenVino.getSelectedItem()),
                                Float.parseFloat(vista.textPrecioVino.getText()),
                                Integer.parseInt(vista.textStockVino.getText()),
                                idbodega,
                                vista.datePFechaIngreso.getDate(),
                                (Integer) vista.tablaVinos.getValueAt(vista.tablaVinos.getSelectedRow(), 0));
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                }
                borrarCamposVinos();
                refrescarVinos();
                break;
            case "eliminarVino":
                modelo.eliminarVino((Integer) vista.tablaVinos.getValueAt(vista.tablaVinos.getSelectedRow(), 0));
                borrarCamposVinos();
                refrescarVinos();
                break;
            case "buscarVino":
                try {
                    cargarFilasVinos(modelo.buscarVino(vista.textBuscarVinos.getText()));
                } catch (SQLException ex) { ex.printStackTrace(); }
                break;
            //bodegas
            case "anadirBodega":
                try {
                    if (comprobarBodegaVacia()) {
                        Util.showErrorAlert("Rellena todos los campos");
                    } else if (modelo.bodegaNombreYaExiste(vista.textNombreBodega.getText())) {
                        Util.showErrorAlert("Esa bodega ya existe.");
                    } else {
                        modelo.insertarBodega(
                                vista.textNombreBodega.getText(),
                                String.valueOf(vista.comboBoxPaisBodega.getSelectedItem()),
                                vista.textTelefonoBodega.getText(),
                                vista.textEmailBodega.getText(),
                                vista.textDireccionBodega.getText());
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                }
                borrarCamposBodegas();
                refrescarBodegas();
                break;
            case "modificarBodega":
                try {
                    if (comprobarBodegaVacia()) {
                        Util.showErrorAlert("Rellena todos los campos");
                    } else {
                        modelo.modificarBodega(
                                vista.textNombreBodega.getText(),
                                String.valueOf(vista.comboBoxPaisBodega.getSelectedItem()),
                                vista.textTelefonoBodega.getText(),
                                vista.textEmailBodega.getText(),
                                vista.textDireccionBodega.getText(),
                                (Integer) vista.tablaBodegas.getValueAt(vista.tablaBodegas.getSelectedRow(), 0));
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                }
                borrarCamposBodegas();
                refrescarBodegas();
                break;
            case "eliminarBodega":
                modelo.eliminarBodega((Integer) vista.tablaBodegas.getValueAt(vista.tablaBodegas.getSelectedRow(), 0));
                borrarCamposBodegas();
                refrescarBodegas();
                break;
            case "buscarBodega":
                try {
                    cargarFilasBodegas(modelo.buscarBodega(vista.textBuscarBodegas.getText()));
                } catch (SQLException ex) { ex.printStackTrace(); }
                break;
            //empleados
            case "anadirEmpleado":
                try {
                    if (comprobarEmpleadoVacio()) {
                        Util.showErrorAlert("Rellena todos los campos");
                    } else if (modelo.empleadoYaExiste(vista.textNombreEmpleado.getText(), vista.textApellidosEmpleado.getText())) {
                        Util.showErrorAlert("Ese empleado ya existe.");
                    } else {
                        modelo.insertarEmpleado(
                                vista.textNombreEmpleado.getText(),
                                vista.textApellidosEmpleado.getText(),
                                vista.textDNIEmpleado.getText(),
                                String.valueOf(vista.comboBoxCargoEmpleado.getSelectedItem()),
                                vista.textTelefonoEmpleado.getText(),
                                Float.parseFloat(vista.textSalarioEmpleado.getText()),
                                vista.datePFechaContratacion.getDate());
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                }
                borrarCamposEmpleados();
                refrescarEmpleados();
                break;
            case "modificarEmpleado":
                try {
                    if (comprobarEmpleadoVacio()) {
                        Util.showErrorAlert("Rellena todos los campos");
                    } else {
                        modelo.modificarEmpleado(
                                vista.textNombreEmpleado.getText(),
                                vista.textApellidosEmpleado.getText(),
                                vista.textDNIEmpleado.getText(),
                                String.valueOf(vista.comboBoxCargoEmpleado.getSelectedItem()),
                                vista.textTelefonoEmpleado.getText(),
                                Float.parseFloat(vista.textSalarioEmpleado.getText()),
                                vista.datePFechaContratacion.getDate(),
                                (Integer) vista.tablaEmpleados.getValueAt(vista.tablaEmpleados.getSelectedRow(), 0));
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                }
                borrarCamposEmpleados();
                refrescarEmpleados();
                break;
            case "eliminarEmpleado":
                modelo.eliminarEmpleado((Integer) vista.tablaEmpleados.getValueAt(vista.tablaEmpleados.getSelectedRow(), 0));
                borrarCamposEmpleados();
                refrescarEmpleados();
                break;
            case "buscarEmpleado":
                try {
                    cargarFilasEmpleados(modelo.buscarEmpleado(vista.textBuscarEmpleados.getText()));
                } catch (SQLException ex) { ex.printStackTrace(); }
                break;
            //ventas
            case "anadirVenta":
                try {
                    if (comprobarVentaVacia()) {
                        Util.showErrorAlert("Rellena todos los campos");
                    } else {
                        int idempleado = Integer.parseInt(String.valueOf(vista.comboBoxEmpleadoVenta.getSelectedItem()).split(" ")[0]);
                        int idvino = Integer.parseInt(String.valueOf(vista.comboBoxVinoVenta.getSelectedItem()).split(" ")[0]);
                        modelo.insertarVenta(
                                idempleado, idvino,
                                vista.textNombreCliente.getText(),
                                String.valueOf(vista.comboBoxEstadoVenta.getSelectedItem()),
                                String.valueOf(vista.comboBoxMetodoPago.getSelectedItem()),
                                vista.datePFechaVenta.getDate(),
                                Float.parseFloat(vista.textTotalVenta.getText()));
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                }
                borrarCamposVentas();
                refrescarVentas();
                break;
            case "modificarVenta":
                try {
                    if (comprobarVentaVacia()) {
                        Util.showErrorAlert("Rellena todos los campos");
                    } else {
                        int idempleado = Integer.parseInt(String.valueOf(vista.comboBoxEmpleadoVenta.getSelectedItem()).split(" ")[0]);
                        int idvino = Integer.parseInt(String.valueOf(vista.comboBoxVinoVenta.getSelectedItem()).split(" ")[0]);
                        modelo.modificarVenta(
                                idempleado, idvino,
                                vista.textNombreCliente.getText(),
                                String.valueOf(vista.comboBoxEstadoVenta.getSelectedItem()),
                                String.valueOf(vista.comboBoxMetodoPago.getSelectedItem()),
                                vista.datePFechaVenta.getDate(),
                                Float.parseFloat(vista.textTotalVenta.getText()),
                                (Integer) vista.tablaVentas.getValueAt(vista.tablaVentas.getSelectedRow(), 0));
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                }
                borrarCamposVentas();
                refrescarVentas();
                break;
            case "eliminarVenta":
                modelo.eliminarVenta((Integer) vista.tablaVentas.getValueAt(vista.tablaVentas.getSelectedRow(), 0));
                borrarCamposVentas();
                refrescarVentas();
                break;
            case "buscarVenta":
                try {
                    cargarFilasVentas(modelo.buscarVenta(vista.textBuscarVentas.getText()));
                } catch (SQLException ex) { ex.printStackTrace(); }
                break;
        }
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        int resp = Util.mensajeConfirmacion("¿Desea cerrar la ventana?", "Salir");
        if (resp == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }

    private void refrescarVinos() {
        try {
            vista.tablaVinos.setModel(construirTableModel(modelo.consultarVinos(), vista.dtmVinos));
            vista.comboBoxVinoVenta.removeAllItems();
            for (int i = 0; i < vista.dtmVinos.getRowCount(); i++) {
                vista.comboBoxVinoVenta.addItem(vista.dtmVinos.getValueAt(i, 0) + " - " + vista.dtmVinos.getValueAt(i, 1));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void refrescarBodegas() {
        try {
            vista.tablaBodegas.setModel(construirTableModel(modelo.consultarBodegas(), vista.dtmBodegas));
            vista.comboBoxBodega.removeAllItems();
            for (int i = 0; i < vista.dtmBodegas.getRowCount(); i++) {
                vista.comboBoxBodega.addItem(vista.dtmBodegas.getValueAt(i, 0) + " - " + vista.dtmBodegas.getValueAt(i, 1));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void refrescarEmpleados() {
        try {
            vista.tablaEmpleados.setModel(construirTableModel(modelo.consultarEmpleados(), vista.dtmEmpleados));
            vista.comboBoxEmpleadoVenta.removeAllItems();
            for (int i = 0; i < vista.dtmEmpleados.getRowCount(); i++) {
                vista.comboBoxEmpleadoVenta.addItem(vista.dtmEmpleados.getValueAt(i, 0) + " - " +
                        vista.dtmEmpleados.getValueAt(i, 2) + ", " + vista.dtmEmpleados.getValueAt(i, 1));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void refrescarVentas() {
        try {
            vista.tablaVentas.setModel(construirTableModel(modelo.consultarVentas(), vista.dtmVentas));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private DefaultTableModel construirTableModel(ResultSet rs, DefaultTableModel dtm) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }
        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        dtm.setDataVector(data, columnNames);
        return dtm;
    }

    private void cargarFilasVinos(ResultSet rs) throws SQLException {
        vista.dtmVinos.setRowCount(0);
        while (rs.next()) {
            Object[] fila = new Object[9];
            for (int i = 0; i < 9; i++) fila[i] = rs.getObject(i + 1);
            vista.dtmVinos.addRow(fila);
        }
    }

    private void cargarFilasBodegas(ResultSet rs) throws SQLException {
        vista.dtmBodegas.setRowCount(0);
        while (rs.next()) {
            Object[] fila = new Object[6];
            for (int i = 0; i < 6; i++) fila[i] = rs.getObject(i + 1);
            vista.dtmBodegas.addRow(fila);
        }
    }

    private void cargarFilasEmpleados(ResultSet rs) throws SQLException {
        vista.dtmEmpleados.setRowCount(0);
        while (rs.next()) {
            Object[] fila = new Object[8];
            for (int i = 0; i < 8; i++) fila[i] = rs.getObject(i + 1);
            vista.dtmEmpleados.addRow(fila);
        }
    }

    private void cargarFilasVentas(ResultSet rs) throws SQLException {
        vista.dtmVentas.setRowCount(0);
        while (rs.next()) {
            Object[] fila = new Object[8];
            for (int i = 0; i < 8; i++) fila[i] = rs.getObject(i + 1);
            vista.dtmVentas.addRow(fila);
        }
    }

    private void borrarCamposVinos() {
        vista.textNombreVino.setText("");
        vista.comboBoxTipoVino.setSelectedIndex(-1);
        vista.textAnada.setText("");
        vista.comboBoxOrigenVino.setSelectedIndex(-1);
        vista.textPrecioVino.setText("");
        vista.textStockVino.setText("");
        vista.comboBoxBodega.setSelectedIndex(-1);
        vista.datePFechaIngreso.setText("");
        vista.textBuscarVinos.setText("");
    }

    private void borrarCamposBodegas() {
        vista.textNombreBodega.setText("");
        vista.comboBoxPaisBodega.setSelectedIndex(-1);
        vista.textTelefonoBodega.setText("");
        vista.textEmailBodega.setText("");
        vista.textDireccionBodega.setText("");
        vista.textBuscarBodegas.setText("");
    }

    private void borrarCamposEmpleados() {
        vista.textNombreEmpleado.setText("");
        vista.textApellidosEmpleado.setText("");
        vista.textDNIEmpleado.setText("");
        vista.comboBoxCargoEmpleado.setSelectedIndex(-1);
        vista.textTelefonoEmpleado.setText("");
        vista.textSalarioEmpleado.setText("");
        vista.datePFechaContratacion.setText("");
        vista.textBuscarEmpleados.setText("");
    }

    private void borrarCamposVentas() {
        vista.comboBoxEmpleadoVenta.setSelectedIndex(-1);
        vista.comboBoxVinoVenta.setSelectedIndex(-1);
        vista.textNombreCliente.setText("");
        vista.comboBoxEstadoVenta.setSelectedIndex(-1);
        vista.comboBoxMetodoPago.setSelectedIndex(-1);
        vista.datePFechaVenta.setText("");
        vista.textTotalVenta.setText("");
        vista.textBuscarVentas.setText("");
    }

    private boolean comprobarVinoVacio() {
        return vista.textNombreVino.getText().isEmpty() ||
                vista.comboBoxTipoVino.getSelectedIndex() == -1 ||
                vista.textAnada.getText().isEmpty() ||
                vista.comboBoxOrigenVino.getSelectedIndex() == -1 ||
                vista.textPrecioVino.getText().isEmpty() ||
                vista.textStockVino.getText().isEmpty() ||
                vista.comboBoxBodega.getSelectedIndex() == -1 ||
                vista.datePFechaIngreso.getText().isEmpty();
    }

    private boolean comprobarBodegaVacia() {
        return vista.textNombreBodega.getText().isEmpty() ||
                vista.comboBoxPaisBodega.getSelectedIndex() == -1 ||
                vista.textTelefonoBodega.getText().isEmpty() ||
                vista.textEmailBodega.getText().isEmpty() ||
                vista.textDireccionBodega.getText().isEmpty();
    }

    private boolean comprobarEmpleadoVacio() {
        return vista.textNombreEmpleado.getText().isEmpty() ||
                vista.textApellidosEmpleado.getText().isEmpty() ||
                vista.textDNIEmpleado.getText().isEmpty() ||
                vista.comboBoxCargoEmpleado.getSelectedIndex() == -1 ||
                vista.textTelefonoEmpleado.getText().isEmpty() ||
                vista.textSalarioEmpleado.getText().isEmpty() ||
                vista.datePFechaContratacion.getText().isEmpty();
    }

    private boolean comprobarVentaVacia() {
        return vista.comboBoxEmpleadoVenta.getSelectedIndex() == -1 ||
                vista.comboBoxVinoVenta.getSelectedIndex() == -1 ||
                vista.textNombreCliente.getText().isEmpty() ||
                vista.comboBoxEstadoVenta.getSelectedIndex() == -1 ||
                vista.comboBoxMetodoPago.getSelectedIndex() == -1 ||
                vista.datePFechaVenta.getText().isEmpty() ||
                vista.textTotalVenta.getText().isEmpty();
    }

    private void setOptions() {
        vista.optionDialog.textIP.setText(modelo.getIp());
        vista.optionDialog.textUsuario.setText(modelo.getUser());
        vista.optionDialog.pfContrasena.setText(modelo.getPassword());
        vista.optionDialog.pfContrasenaAdmin.setText(modelo.getAdminPassword());
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {}
    @Override
    public void windowOpened(WindowEvent e) {}
    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}
}
