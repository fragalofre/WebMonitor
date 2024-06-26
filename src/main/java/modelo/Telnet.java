package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Telnet {

   // private static final String rutaBat = "C:\\Users\\ricoh.ses\\OneDrive - Ricoh Europe PLC\\Desktop\\Telnet\\Telnet_Perron.bat"; //RUTA PC ALEJANDRO

    public static void telnetScript(Impresora impresora){


        // Ruta al archivo .bat
        String rutaBat = "C:\\Users\\Ricoh.ses\\Desktop\\AIM P\\Telnet_Perron.bat";
        String parametro = impresora.getIp();

        try {
            // Crear un proceso con el comando
            ProcessBuilder builder = new ProcessBuilder(rutaBat, parametro);
            // builder.directory(new File("C:\\Users\\Francisco\\IdeaProjects\\EjecutarTerminal\\")); // Directorio donde se encuentra el .bat
            builder.directory(new File("C:\\Users\\Ricoh.ses\\Desktop\\AIM P")); // Directorio donde se encuentra el .bat
            builder.redirectErrorStream(true);

            // Iniciar el proceso
            Process proceso = builder.start();

            // Leer la salida del comando
            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }

            // Esperar a que el proceso termine
            int exitCode = proceso.waitFor();
            System.out.println("Código de salida: " + exitCode);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
