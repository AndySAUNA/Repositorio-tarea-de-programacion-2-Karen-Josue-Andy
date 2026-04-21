package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AudioUtil {

    // Detectar sistema operativo
    private static final String OS = System.getProperty("os.name").toLowerCase();
    private static final boolean IS_WINDOWS = OS.contains("win");
    private static final boolean IS_MAC = OS.contains("mac");
    private static final boolean IS_LINUX = OS.contains("nux") || OS.contains("nix");

    public static void reproducirLlamado(int numeroFicha, String nombreEstacion) {
        String mensaje = "Ficha " + numeroFicha + ", favor pasar a la estación " + nombreEstacion;
        reproducirMensaje(mensaje);
    }

    public static void reproducirMensaje(String texto) {
        System.out.println(" Intentando: " + texto);
        System.out.println(" SO detectado: " + OS);

        if (IS_WINDOWS) {
            hablarWindows(texto);
        } else if (IS_MAC) {
            hablarMac(texto);
        } else if (IS_LINUX) {
            hablarLinux(texto);
        } else {
            System.out.println(" Sistema operativo no soportado: " + OS);
            System.out.println("[SIMULACIÓN AUDIO] " + texto);
        }
    }

    // ========== WINDOWS ==========
    private static void hablarWindows(String texto) {
        try {
            String[] cmd = {
                "powershell.exe",
                "-Command",
                "Add-Type -AssemblyName System.Speech; " +
                "$speak = New-Object System.Speech.Synthesis.SpeechSynthesizer; " +
                "$speak.Speak('" + texto.replace("'", "''") + "');"
            };

            Process process = Runtime.getRuntime().exec(cmd);
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println(" Audio reproducido en Windows: " + texto);
            } else {
                System.out.println(" Error en Windows. Código: " + exitCode);
                leerErrores(process);
            }

        } catch (Exception e) {
            System.err.println(" Excepción en Windows: " + e.getMessage());
        }
    }

    // ========== MAC ==========
    private static void hablarMac(String texto) {
        try {
           
            String[] cmd = { "say", "-v", "Monica", texto };

            Process process = Runtime.getRuntime().exec(cmd);
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println(" Audio reproducido en Mac: " + texto);
            } else {
                System.out.println(" Error en Mac. Código: " + exitCode);
                leerErrores(process);
            }

        } catch (Exception e) {
            System.err.println(" Excepción en Mac: " + e.getMessage());
            System.out.println("[SIMULACIÓN AUDIO] " + texto);
        }
    }

    // ========== LINUX ==========
    private static void hablarLinux(String texto) {
        try {
          
            String[] cmd = { "espeak", "-v", "es", "-s", "140", texto };

            Process process = Runtime.getRuntime().exec(cmd);
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println(" Audio reproducido en Linux: " + texto);
            } else {
                System.out.println(" Error en Linux. Código: " + exitCode);
                leerErrores(process);
            }

        } catch (Exception e) {
            System.err.println(" Excepción en Linux: " + e.getMessage());
            System.out.println("[SIMULACIÓN AUDIO] " + texto);
        }
    }

    private static void leerErrores(Process process) {
        try {
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            while ((line = errorReader.readLine()) != null) {
                System.err.println("Error: " + line);
            }
        } catch (Exception e) {

        }
    }
    public static void shutdown() {
        System.out.println("AudioUtil cerrado");
    }
}