package main.vista;

import main.controlador.Coordinador;
import main.modelo.dto.PersonaDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaConsultaIndividual extends JDialog implements ActionListener {

    private Coordinador miCoordinador;
    private JLabel lblTitulo, lblNombre, lblEdad,lblDocumento;
    private JTextField txtNombre, txtEdad,txtDocumento;
    private JButton btnBuscar, btnCerrar, btnActualizar,btnEliminar;
    private JTextArea areaResultado;
    private PersonaDTO personaActual;

    public VentanaConsultaIndividual(VentanaPrincipal ventanaPrincipal, boolean modal) {
        super(ventanaPrincipal, modal);
        initComponents();
        setupLayout();
        setupListeners();
        habilitarBotones(false);
    }

    private void initComponents() {
        setTitle("Consulta Individual");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        lblTitulo = new JLabel("Consulta Individual");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField(20);
        lblEdad = new JLabel("Edad (años):");
        txtEdad = new JTextField(10);

        lblDocumento = new JLabel("Documento:");
        txtDocumento = new JTextField(20);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 12));

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setForeground(Color.BLACK);
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 12));

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setForeground(Color.BLACK);

        areaResultado = new JTextArea(12, 40);
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaResultado.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new GridBagLayout());
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panelSuperior.add(lblTitulo, gbc);

        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.0;


        gbc.gridx = 0; gbc.gridy = 1;
        panelSuperior.add(lblNombre, gbc);
        gbc.gridx = 1;gbc.weightx = 0.7;
        panelSuperior.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2;gbc.weightx = 0.0;
        panelSuperior.add(lblEdad, gbc);
        gbc.gridx = 1;gbc.weightx = 1.0;
        panelSuperior.add(txtEdad, gbc);

        gbc.gridx = 0; gbc.gridy = 3;gbc.weightx = 0.0;
        panelSuperior.add(lblDocumento, gbc);
        gbc.gridx = 1;gbc.weightx = 1.0;
        panelSuperior.add(txtDocumento, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnBuscar);
        panelBotones.add(btnCerrar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panelSuperior.add(panelBotones, gbc);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(areaResultado), BorderLayout.CENTER);
    }

    private void setupListeners() {
        btnBuscar.addActionListener(this);
        btnCerrar.addActionListener(this);
        btnActualizar.addActionListener(this);
        btnEliminar.addActionListener(this);
        txtNombre.addActionListener(this);
        txtEdad.addActionListener(this);
        txtDocumento.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBuscar || e.getSource() == txtDocumento) {
            buscarPersona();
        } else if (e.getSource() == btnActualizar) {
            actualizarPersona();
        } else if (e.getSource() == btnEliminar) {
            eliminarPersona();
        } else if (e.getSource() == btnCerrar) {
            dispose();
        }
    }

    private void buscarPersona() {
        String documento = txtDocumento.getText().trim();

        if (documento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un documento.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtDocumento.requestFocus();
            return;
        }

        PersonaDTO persona = miCoordinador.consultarPersona(documento);

        if (persona != null) {
            personaActual = persona;
            txtNombre.setText(persona.getNombre());
            txtEdad.setText(String.valueOf(persona.getEdad()));
            StringBuilder resultado = new StringBuilder();
            resultado.append("PERSONA ENCONTRADA\n");
            resultado.append("==============================\n\n");
            resultado.append("Nombre: ").append(persona.getNombre()).append("\n");
            resultado.append("Edad: ").append(persona.getEdad()).append(" años\n\n");

            if (persona.getPeso() > 0 && persona.getTalla() > 0) {
                resultado.append(String.format("Peso: %.1f kg\n", persona.getPeso()));
                resultado.append(String.format("Talla: %.2f m\n", persona.getTalla()));
                resultado.append(String.format("IMC: %.2f\n\n", persona.getImc()));
                resultado.append("Estado: ").append(persona.getEstado()).append("\n\n");
                resultado.append("Recomendación:\n").append(persona.getMensaje());
            } else {
                resultado.append("IMC: No calculado\n\n");
                resultado.append("Para calcular el IMC, use la opción\n'Calcular IMC' del menú principal.");
            }

            areaResultado.setText(resultado.toString());
            habilitarBotones(true);
        } else {
            personaActual = null;
            limpiarCampos();
            areaResultado.setText("PERSONA NO ENCONTRADA\n" +
                    "==============================\n\n" +
                    "No existe una persona con el documento:\n\"" + documento + "\"\n\n" +
                    "Verifique la escritura o registre\nla persona primero.");

            JOptionPane.showMessageDialog(this,
                    "Persona no encontrada: \"" + documento + "\"",
                    "Búsqueda",
                    JOptionPane.INFORMATION_MESSAGE);
            habilitarBotones(false);
        }

        areaResultado.setCaretPosition(0);
    }
    private void actualizarPersona() {
        if (personaActual == null) {
            JOptionPane.showMessageDialog(this, "Primero debe buscar una persona.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombre = txtNombre.getText().trim();
        String edadText = txtEdad.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocus();
            return;
        }

        if (edadText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La edad no puede estar vacía.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtEdad.requestFocus();
            return;
        }

        int edad;
        try {
            edad = Integer.parseInt(edadText);
            if (edad <= 0 || edad > 120) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese una edad válida (1-120).", "Validación", JOptionPane.WARNING_MESSAGE);
            txtEdad.requestFocus();
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de actualizar los datos de:\n" +
                        "Documento: " + personaActual.getDocumento() + "\n" +
                        "Nombre actual: " + personaActual.getNombre() + "\n" +
                        "Edad actual: " + personaActual.getEdad() + "\n\n" +
                        "Nuevo nombre: " + nombre + "\n" +
                        "Nueva edad: " + edad,
                "Confirmar Actualización",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            PersonaDTO personaActualizada = new PersonaDTO();
            personaActualizada.setDocumento(personaActual.getDocumento());
            personaActualizada.setNombre(nombre);
            personaActualizada.setEdad(edad);

            String resultado = miCoordinador.actualizarPersona(personaActualizada);

            JOptionPane.showMessageDialog(this, resultado, "Actualización",
                    resultado.contains("exitosamente") ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);

            if (resultado.contains("exitosamente")) {
                personaActual.setNombre(nombre);
                personaActual.setEdad(edad);
                buscarPersona();
            }
        }
    }
    private void eliminarPersona() {
        if (personaActual == null) {
            JOptionPane.showMessageDialog(this, "Primero debe buscar una persona.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar permanentemente a:\n\n" +
                        "Documento: " + personaActual.getDocumento() + "\n" +
                        "Nombre: " + personaActual.getNombre() + "\n" +
                        "Edad: " + personaActual.getEdad() + " años\n\n" +
                        "Esta acción no se puede deshacer.",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = miCoordinador.eliminarPersona(personaActual.getDocumento());

            if (eliminado) {
                JOptionPane.showMessageDialog(this,
                        "Persona eliminada exitosamente.",
                        "Eliminación",
                        JOptionPane.INFORMATION_MESSAGE);

                limpiarTodo();
                areaResultado.setText("PERSONA ELIMINADA\n" +
                        "==============================\n\n" +
                        "La persona ha sido eliminada exitosamente\n" +
                        "de la base de datos.");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al eliminar la persona.\nVerifique la conexión a la base de datos.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void habilitarBotones(boolean habilitar) {
        btnActualizar.setEnabled(habilitar);
        btnEliminar.setEnabled(habilitar);
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtEdad.setText("");
    }

    private void limpiarTodo() {
        txtDocumento.setText("");
        limpiarCampos();
        personaActual = null;
        habilitarBotones(false);
    }

    public void setCoordinador(Coordinador miCoordinador) {
        this.miCoordinador = miCoordinador;
    }
}