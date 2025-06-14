package main.modelo;

import main.controlador.Coordinador;
import main.modelo.dto.PersonaDTO;

public class Procesos {

    public Procesos() {
    }

    public PersonaDTO calcularIMC(PersonaDTO persona) {
        double peso = persona.getPeso();
        double talla = persona.getTalla();
        double imcCalculado = 0;

        System.out.println("=== Iniciando cálculo de IMC ===");
        System.out.println("Peso recibido: " + peso);
        System.out.println("Talla recibida: " + talla);

        if (talla > 0) {
            imcCalculado = peso / (talla * talla);
            System.out.println("IMC calculado: " + imcCalculado);
        } else {
            System.out.println("Error: Talla es 0 o negativa");
        }

        persona.setImc(imcCalculado);

        String estado = "";
        String mensaje = "";

        if (imcCalculado < 18.5) {
            estado = "Anorexia";
            mensaje = "Visite a su médico para mejorar su alimentación.";
        } else if (imcCalculado >= 18.5 && imcCalculado < 20) {
            estado = "Delgadez";
            mensaje = "Puede comer otro poquito.";
        } else if (imcCalculado >= 20 && imcCalculado < 27) {
            estado = "Normalidad";
            mensaje = "¡Estado de peso saludable!";
        } else if (imcCalculado >= 27 && imcCalculado < 30) {
            estado = "Obesidad Grado I (Sobrepeso)";
            mensaje = "Pilas, debería considerar ir al gym y mejorar hábitos.";
        } else if (imcCalculado >= 30 && imcCalculado < 35) {
            estado = "Obesidad Grado II";
            mensaje = "Debe ir al gym y mejorar su alimentación.";
        } else if (imcCalculado >= 35 && imcCalculado < 40) {
            estado = "Obesidad Grado III";
            mensaje = "Debería ir al gym, mejorar su alimentación y consultar a un médico.";
        } else if (imcCalculado >= 40) {
            estado = "Obesidad Mórbida";
            mensaje = "Debe cuidar mucho su peso, mejorar su alimentación y consultar a un médico urgentemente.";
        } else {
            estado = "Datos insuficientes o incorrectos";
            mensaje = "Verifique el peso y la talla ingresados. Deben ser mayores a cero.";
        }

        persona.setEstado(estado);
        persona.setMensaje(mensaje);

        System.out.println("Estado asignado: " + estado);
        System.out.println("Mensaje asignado: " + mensaje);
        System.out.println("=== Fin del cálculo de IMC ===");

        return persona;
    }

    public void setCoordinador(Coordinador miCoordinador) {
    }
}

