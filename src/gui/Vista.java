package gui;

import com.github.lgooddatepicker.components.DatePicker;
import gui.enums.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Vista extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private final static String TITULO_FRAME = "La Vinoteca";

    // vinos
    JPanel JPanelVinos;
    JTextField textNombreVino;
    JComboBox comboBoxTipoVino;
    JTextField textAnada;
    JComboBox comboBoxOrigenVino;
    JTextField textPrecioVino;
    JTextField textStockVino;
    JComboBox comboBoxBodega;
    DatePicker datePFechaIngreso;
    JButton buttonVinosBuscar;
    JButton buttonVinosAnadir;
    JButton buttonVinosModificar;
    JButton buttonVinosEliminar;
    JTable tablaVinos;
    JTextField textBuscarVinos;

    // bodegas
    JPanel JPanelBodegas;
    JTextField textNombreBodega;
    JComboBox comboBoxPaisBodega;
    JTextField textTelefonoBodega;
    JTextField textEmailBodega;
    JTextField textDireccionBodega;
    JButton buttonBodegasBuscar;
    JButton buttonBodegasAnadir;
    JButton buttonBodegasModificar;
    JButton buttonBodegasEliminar;
    JTable tablaBodegas;
    JTextField textBuscarBodegas;

    // empleados
    JPanel JPanelEmpleados;
    JTextField textNombreEmpleado;
    JTextField textApellidosEmpleado;
    JTextField textDNIEmpleado;
    JComboBox comboBoxCargoEmpleado;
    JTextField textTelefonoEmpleado;
    JTextField textSalarioEmpleado;
    DatePicker datePFechaContratacion;
    JButton buttonEmpleadosBuscar;
    JButton buttonEmpleadosAnadir;
    JButton buttonEmpleadosModificar;
    JButton buttonEmpleadosEliminar;
    JTable tablaEmpleados;
    JTextField textBuscarEmpleados;

    // ventas
    JPanel JPanelVentas;
    JComboBox comboBoxEmpleadoVenta;
    JComboBox comboBoxVinoVenta;
    JTextField textNombreCliente;
    JComboBox comboBoxEstadoVenta;
    JComboBox comboBoxMetodoPago;
    DatePicker datePFechaVenta;
    JTextField textTotalVenta;
    JButton buttonVentasBuscar;
    JButton buttonVentasAnadir;
    JButton buttonVentasModificar;
    JButton buttonVentasEliminar;
    JTable tablaVentas;
    JTextField textBuscarVentas;

    // etiqueta estado
    JLabel etiquetaEstado;

    // default table models
    DefaultTableModel dtmVinos;
    DefaultTableModel dtmBodegas;
    DefaultTableModel dtmEmpleados;
    DefaultTableModel dtmVentas;

    // menubar
    JMenuItem itemOpciones;
    JMenuItem itemDesconectar;
    JMenuItem itemSalir;

    // cuadro dialogo
    OptionDialog optionDialog;
    JDialog adminPasswordDialog;
    JButton btnValidate;
    JPasswordField adminPassword;

    public Vista() {
        super(TITULO_FRAME);
        initFrame();
    }

    public void initFrame() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();

        }

        try {
            java.net.URL iconURL = getClass().getResource("/img/icono.png");
            if (iconURL != null) {
                this.setIconImage(new ImageIcon(iconURL).getImage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setSize(new Dimension(this.getWidth() + 100, this.getHeight()));
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        optionDialog = new OptionDialog(this);
        setMenu();
        setAdminDialog();
        setEnumComboBox();
        setTableModels();
        aplicarEstilo();
    }

    private void setMenu() {
        JMenuBar mbBar = new JMenuBar();
        JMenu menu = new JMenu("Archivo");
        itemOpciones = new JMenuItem("Opciones");
        itemOpciones.setActionCommand("Opciones");
        itemDesconectar = new JMenuItem("Desconectar");
        itemDesconectar.setActionCommand("Desconectar");
        itemSalir = new JMenuItem("Salir");
        itemSalir.setActionCommand("Salir");
        menu.add(itemOpciones);
        menu.add(itemDesconectar);
        menu.add(itemSalir);
        mbBar.add(menu);
        mbBar.add(Box.createHorizontalGlue());
        this.setJMenuBar(mbBar);
    }

    private void setAdminDialog() {
        btnValidate = new JButton("Validar");
        btnValidate.setActionCommand("abrirOpciones");
        adminPassword = new JPasswordField();
        this.setSize(new Dimension(1100, 700));
        Object[] options = new Object[]{adminPassword, btnValidate};
        JOptionPane jop = new JOptionPane("Introduce la contraseña", JOptionPane.WARNING_MESSAGE,
                JOptionPane.YES_NO_OPTION, null, options);
        adminPasswordDialog = new JDialog(this, "Opciones", true);
        adminPasswordDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        adminPasswordDialog.setContentPane(jop);
        adminPasswordDialog.pack();
        adminPasswordDialog.setLocationRelativeTo(this);
    }

    private void setEnumComboBox() {
        for (TipoVino constant : TipoVino.values()) {
            comboBoxTipoVino.addItem(constant.getValor());
        }
        comboBoxTipoVino.setSelectedIndex(-1);

        for (OrigenVino constant : OrigenVino.values()) {
            comboBoxOrigenVino.addItem(constant.getValor());
        }
        comboBoxOrigenVino.setSelectedIndex(-1);

        for (OrigenVino constant : OrigenVino.values()) {
            comboBoxPaisBodega.addItem(constant.getValor());
        }
        comboBoxPaisBodega.setSelectedIndex(-1);

        for (CargoEmpleado constant : CargoEmpleado.values()) {
            comboBoxCargoEmpleado.addItem(constant.getValor());
        }
        comboBoxCargoEmpleado.setSelectedIndex(-1);

        for (EstadoVenta constant : EstadoVenta.values()) {
            comboBoxEstadoVenta.addItem(constant.getValor());
        }
        comboBoxEstadoVenta.setSelectedIndex(-1);

        for (MetodoPago constant : MetodoPago.values()) {
            comboBoxMetodoPago.addItem(constant.getValor());
        }
        comboBoxMetodoPago.setSelectedIndex(-1);
    }

    private void setTableModels() {
        this.dtmVinos = new DefaultTableModel();
        this.tablaVinos.setModel(dtmVinos);

        this.dtmBodegas = new DefaultTableModel();
        this.tablaBodegas.setModel(dtmBodegas);

        this.dtmEmpleados = new DefaultTableModel();
        this.tablaEmpleados.setModel(dtmEmpleados);

        this.dtmVentas = new DefaultTableModel();
        this.tablaVentas.setModel(dtmVentas);
    }

    private void aplicarEstilo() {
        Color fondoClaro = new Color(245, 235, 220);
        Color burdeos = new Color(120, 50, 50);

        panel1.setBackground(fondoClaro);
        tabbedPane1.setBackground(fondoClaro);
        tabbedPane1.setForeground(burdeos);

        JPanelVinos.setBackground(fondoClaro);
        JPanelBodegas.setBackground(fondoClaro);
        JPanelEmpleados.setBackground(fondoClaro);
        JPanelVentas.setBackground(fondoClaro);

        // Cargar imagen
        ImageIcon icono = null;
        try {
            java.net.URL imgURL = getClass().getResource("/img/vino.png");
            if (imgURL != null) {
                Image img = new ImageIcon(imgURL).getImage()
                        .getScaledInstance(210, 380, Image.SCALE_SMOOTH);
                icono = new ImageIcon(img);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (icono != null) {
            asignarImagenLabel(JPanelVinos, icono);
            asignarImagenLabel(JPanelBodegas, icono);
            asignarImagenLabel(JPanelEmpleados, icono);
            asignarImagenLabel(JPanelVentas, icono);
        }
    }

    private void asignarImagenLabel(JPanel panel, ImageIcon icono) {
        for (Component c : panel.getComponents()) {
            if (c instanceof JLabel && ((JLabel) c).getText().equals("")) {
                ((JLabel) c).setIcon(icono);
                break;
            }
        }
    }
}