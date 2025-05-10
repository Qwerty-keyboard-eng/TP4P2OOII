package org.example.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class LectorDeDatos{

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public List<String> leerLineas(File archivo) throws IOException {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        }
        return lineas;
    }

    public List<Empleado> cargarEmpleados(File archivo) throws IOException, IllegalArgumentException {
        List<Empleado> empleados = new ArrayList<>();
        List<String> lineas = leerLineas(archivo);

        for (String linea : lineas) {
            String[] campos = linea.split(",");
            if (campos.length == 4) {
                String apellido = campos[0].trim();
                String nombre = campos[1].trim();
                String fechaNacimientoStr = campos[2].trim();
                String email = campos[3].trim();

                try {
                    LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr, DATE_FORMATTER);
                    empleados.add(new Empleado(apellido, nombre, fechaNacimiento, email));
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Formato de fecha de nacimiento incorrecto en línea: " + linea, e);
                }
            } else if (!linea.trim().isEmpty()) {
                throw new IllegalArgumentException("Formato de línea incorrecto: " + linea);
            }
        }
        return empleados;
    }

    // Clase interna para representar un Empleado

}