package main.vista;

import main.controlador.Coordinador;
import main.modelo.dto.PersonaDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaRegistro extends JDialog implements ActionListener {

    private Coordinador miCoordinador;
    private JLabel lblTitulo, lblNombre, lblEdad,lblDocumento;
    private JTextField txtNombre, txtEdad,txtDocumento;
    private JButton btnRegistrar, btnCancelar;
    PersonaDTO persona = new PersonaDTO();

    public VentanaRegistro(VentanaPrincipal ventanaPrincipal, boolean modal) {
        super(ventanaPrincipal, modal);
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        setTitle("Registrar Persona");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        lblTitulo = new JLabel("Registro de Nueva Persona");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        lblNombre = new JLabel("Nombre completo:");
        txtNombre = new JTextField(20);

        lblEdad = new JLabel("Edad (años):");
        txtEdad = new JTextField(10);

        lblDocumento = new JLabel("Documento:");
        txtDocumento = new JTextField(20);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setForeground(Color.BLACK);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 12));

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setForeground(Color.BLACK);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(lblTitulo, gbc);

        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.0;


        gbc.gridx = 0; gbc.gridy = 1;
        panelPrincipal.add(lblNombre, gbc);
        gbc.gridx = 1;gbc.weightx = 0.7;
        panelPrincipal.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2;gbc.weightx = 0.0;
        panelPrincipal.add(lblEdad, gbc);
        gbc.gridx = 1;gbc.weightx = 1.0;
        panelPrincipal.add(txtEdad, gbc);

        gbc.gridx = 0; gbc.gridy = 3;gbc.weightx = 0.0;
        panelPrincipal.add(lblDocumento, gbc);
        gbc.gridx = 1;gbc.weightx = 1.0;
        panelPrincipal.add(txtDocumento, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCancelar);

        add(panelPrincipal, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        btnRegistrar.addActionListener(this);
        btnCancelar.addActionListener(this);
        txtNombre.addActionListener(this);
        txtEdad.addActionListener(this);
        txtDocumento.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegistrar || e.getSource() == txtEdad || e.getSource() == txtDocumento ) {
            procesarRegistro();
        } else if (e.getSource() == txtNombre) {
            txtEdad.requestFocus();
        } else if (e.getSource() == btnCancelar) {
            dispose();
        }
    }

    private void procesarRegistro() {
        String documento = txtDocumento.getText().trim();
        String nombre = txtNombre.getText().trim();
        String edadStr = txtEdad.getText().trim();

        if (documento.isEmpty() || nombre.isEmpty() || edadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Complete todos los campos.",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int edad = Integer.parseInt(edadStr);

            if (edad <= 0 || edad > 120) {
                JOptionPane.showMessageDialog(this,
                        "Ingrese una edad válida (1-120 años).",
                        "Edad inválida",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (miCoordinador.existePersona(documento)) {
                JOptionPane.showMessageDialog(this,
                        "Ya existe una persona con ese documento.",
                        "Documento duplicado",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            PersonaDTO persona = new PersonaDTO();
            persona.setDocumento(documento);
            persona.setNombre(nombre);
            persona.setEdad(edad);
            persona.setPeso(0.0);
            persona.setTalla(0.0);
            persona.setImc(0.0);
            persona.setEstado("Sin calcular");
            persona.setMensaje("Debe calcular el IMC");

            miCoordinador.registrarPersona(persona);

            JOptionPane.showMessageDialog(this,
                    "¡Persona registrada exitosamente!\n\n" +
                            "Ahora puede calcular su IMC en la opción 'Calcular IMC'.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE);

            limpiarCampos();
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "La edad debe ser un número entero.",
                    "Error de formato",
                    JOptionPane.ERROR_MESSAGE);
            txtEdad.requestFocus();
        }
    }

    private void limpiarCampos() {
        txtDocumento.setText("");
        txtNombre.setText("");
        txtEdad.setText("");
        txtNombre.requestFocus();
    }

    public void setCoordinador(Coordinador miCoordinador) {
        this.miCoordinador = miCoordinador;
    }
}