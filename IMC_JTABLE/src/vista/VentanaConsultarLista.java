package main.vista;

import main.controlador.Coordinador;
import main.modelo.dto.PersonaDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VentanaConsultarLista extends JDialog implements ActionListener {

    private Coordinador miCoordinador;
    private JLabel lblTitulo, lblInfo;
    private JButton btnActualizar, btnCerrar, btnExportar;
    private JTable tablaPersonas;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
    private JPanel panelPrincipal, panelBotones, panelInfo;

    public VentanaConsultarLista(VentanaPrincipal ventanaPrincipal, boolean modal) {
        super(ventanaPrincipal, modal);
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        setTitle("Lista de Personas Registradas");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        lblTitulo = new JLabel("Lista de Personas Registradas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);

        panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblInfo = new JLabel("Total de personas: 0");
        lblInfo.setFont(new Font("Arial", Font.BOLD, 14));
        panelInfo.add(lblInfo);

        String[] columnas = {"Nombre", "Edad", "Peso (kg)", "Talla (m)", "IMC", "Estado", "Recomendación"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPersonas = new JTable(modeloTabla);
        tablaPersonas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPersonas.setRowHeight(25);
        tablaPersonas.setFont(new Font("Arial", Font.PLAIN, 12));

        JTableHeader header = tablaPersonas.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setForeground(Color.BLACK);

        tablaPersonas.getColumnModel().getColumn(0).setPreferredWidth(150); // Nombre
        tablaPersonas.getColumnModel().getColumn(1).setPreferredWidth(60);  // Edad
        tablaPersonas.getColumnModel().getColumn(2).setPreferredWidth(80);  // Peso
        tablaPersonas.getColumnModel().getColumn(3).setPreferredWidth(80);  // Talla
        tablaPersonas.getColumnModel().getColumn(4).setPreferredWidth(80);  // IMC
        tablaPersonas.getColumnModel().getColumn(5).setPreferredWidth(150); // Estado
        tablaPersonas.getColumnModel().getColumn(6).setPreferredWidth(250); // Recomendación

        scrollTabla = new JScrollPane(tablaPersonas);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Datos de Personas"));

        btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.setForeground(Color.BLACK);
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 12));

        btnExportar = new JButton("Exportar Datos");
        btnExportar.setForeground(Color.BLACK);
        btnExportar.setFont(new Font("Arial", Font.BOLD, 12));

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setForeground(Color.BLACK);
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));

        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.add(btnActualizar);
        panelBotones.add(btnExportar);
        panelBotones.add(btnCerrar);
    }

    private void setupLayout() {
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        panelSuperior.add(panelInfo, BorderLayout.SOUTH);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void setupListeners() {
        btnActualizar.addActionListener(this);
        btnExportar.addActionListener(this);
        btnCerrar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnActualizar) {
            cargarDatos();
        } else if (e.getSource() == btnExportar) {
            exportarDatos();
        } else if (e.getSource() == btnCerrar) {
            dispose();
        }
    }

    private void cargarDatos() {
        try {
            modeloTabla.setRowCount(0);

            List<PersonaDTO> listaPersonas = miCoordinador.consultarTodasLasPersonas();

            if (listaPersonas != null && !listaPersonas.isEmpty()) {
                for (PersonaDTO persona : listaPersonas) {
                    Object[] fila = {
                            persona.getNombre(),
                            persona.getEdad(),
                            String.format("%.2f", persona.getPeso()),
                            String.format("%.2f", persona.getTalla()),
                            String.format("%.2f", persona.getImc()),
                            persona.getEstado(),
                            persona.getMensaje()
                    };
                    modeloTabla.addRow(fila);
                }
                lblInfo.setText("Total de personas: " + listaPersonas.size());
            } else {
                lblInfo.setText("Total de personas: 0");
                JOptionPane.showMessageDialog(this,
                        "No hay personas registradas en el sistema.",
                        "Sin datos",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            tablaPersonas.revalidate();
            tablaPersonas.repaint();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los datos: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportarDatos() {
        try {
            List<PersonaDTO> listaPersonas = miCoordinador.consultarTodasLasPersonas();

            if (listaPersonas == null || listaPersonas.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No hay datos para exportar.",
                        "Sin datos",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder datosExportados = new StringBuilder();
            datosExportados.append("REPORTE DE PERSONAS REGISTRADAS\n");
            datosExportados.append("===============================================\n");
            datosExportados.append("Fecha de generación: ").append(new java.util.Date()).append("\n");
            datosExportados.append("Total de registros: ").append(listaPersonas.size()).append("\n\n");

            int contador = 1;
            for (PersonaDTO persona : listaPersonas) {
                datosExportados.append("PERSONA #").append(contador).append("\n");
                datosExportados.append("-----------------------------------------------\n");
                datosExportados.append("Nombre: ").append(persona.getNombre()).append("\n");
                datosExportados.append("Edad: ").append(persona.getEdad()).append(" años\n");
                datosExportados.append(String.format("Peso: %.2f kg\n", persona.getPeso()));
                datosExportados.append(String.format("Talla: %.2f m\n", persona.getTalla()));
                datosExportados.append(String.format("IMC: %.2f\n", persona.getImc()));
                datosExportados.append("Estado: ").append(persona.getEstado()).append("\n");
                datosExportados.append("Recomendación: ").append(persona.getMensaje()).append("\n\n");
                contador++;
            }

            JTextArea areaTexto = new JTextArea(datosExportados.toString());
            areaTexto.setEditable(false);
            areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));

            JScrollPane scrollPane = new JScrollPane(areaTexto);
            scrollPane.setPreferredSize(new Dimension(600, 400));

            JOptionPane.showMessageDialog(this,
                    scrollPane,
                    "Datos Exportados",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al exportar los datos: " + ex.getMessage(),
                    "Error de Exportación",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setCoordinador(Coordinador miCoordinador) {
        this.miCoordinador = miCoordinador;
    }
}