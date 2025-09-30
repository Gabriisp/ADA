package com.ada;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Escribir el texto
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter writer = new BufferedWriter(new FileWriter("Ejercicio1.txt", true));

            System.out.println("Escribe un nuevo texto:");
            String texto = reader.readLine();

            writer.write(texto);
            writer.newLine(); 
            writer.close(); 
            System.out.println("Texto guardado en Ejercicio1.txt");

            // Lee el fichero
            BufferedReader fileReader = new BufferedReader(new FileReader("Ejercicio1.txt"));
            String linea;
            int totalPalabras = 0;

            System.out.println("\nContenido del fichero Ejercicio1.txt:");
            while ((linea = fileReader.readLine()) != null) {
                System.out.println(linea);
                // Contar palabras en la línea
                String[] palabras = linea.trim().split("\\s+");
                totalPalabras += palabras.length;
            }

            fileReader.close();

            System.out.println("\nNúmero total de palabras en el fichero: " + totalPalabras);

        } catch (IOException e) {
            System.out.println("Error de texto: " + e.getMessage());
        }
    }
}
