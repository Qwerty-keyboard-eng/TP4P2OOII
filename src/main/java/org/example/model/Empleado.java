package org.example.model;

import java.time.LocalDate;

public class Empleado {
    private String apellido;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String email;

    public Empleado(String apellido, String nombre, LocalDate fechaNacimiento, String email) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empleado empleado = (Empleado) o;
        return apellido.equals(empleado.apellido) && nombre.equals(empleado.nombre) && fechaNacimiento.equals(empleado.fechaNacimiento) && email.equals(empleado.email);
    }

    @Override
    public int hashCode() {
        int result = apellido.hashCode();
        result = 31 * result + nombre.hashCode();
        result = 31 * result + fechaNacimiento.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}