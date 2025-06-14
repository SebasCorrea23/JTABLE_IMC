package main.modelo.dto;

public class PersonaDTO {
    public String documento;
    public String nombre;
    public int edad;
    public double peso;
    public double talla;
    public double imc;
    public String estado;
    public String mensaje;

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public PersonaDTO(String documento, String nombre, int edad, double peso, double talla, double imc, String estado, String mensaje) {
        this.documento = documento;
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.talla = talla;
        this.imc = imc;
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public PersonaDTO() {

    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getTalla() {
        return talla;
    }

    public void setTalla(double talla) {
        this.talla = talla;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    @Override
    public String toString() {
        return "PersonaDTO [documento=" + imc + ", nombre=" + nombre + ", edad=" + edad + "]";
    }



}
