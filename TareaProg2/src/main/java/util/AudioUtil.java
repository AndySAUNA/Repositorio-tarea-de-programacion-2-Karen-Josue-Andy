package util;

import java.io.*;
import java.util.concurrent.*;

public class AudioUtil {

    private static Process powerShellProcess;
    private static BufferedWriter commandWriter;
    private static boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
    private static boolean inicializado = false;

    static {
        if (isWindows) {
            try {
                ProcessBuilder builder = new ProcessBuilder("powershell.exe");
                powerShellProcess = builder.start();
                commandWriter = new BufferedWriter(new OutputStreamWriter(powerShellProcess.getOutputStream()));
                readStreamInBackground(powerShellProcess.getInputStream());
                readStreamInBackground(powerShellProcess.getErrorStream());
                sendCommandToPowerShell("Add-Type -AssemblyName System.Speech;");
                inicializado = true;
                System.out.println("AudioUtil: PowerShell y System.Speech listos.");
            } catch (IOException e) {
                System.err.println("Error al iniciar PowerShell: " + e.getMessage());
                powerShellProcess = null;
            }
        } else {
            System.out.println("AudioUtil: Sistema operativo no soportado (requiere Windows).");
        }
    }

    private static void sendCommandToPowerShell(String command) throws IOException {
        if (commandWriter != null && powerShellProcess != null && powerShellProcess.isAlive()) {
            commandWriter.write(command);
            commandWriter.newLine();
            commandWriter.flush();
        }
    }

   
    public static void reproducirLlamado(int numeroFicha, String nombreEstacion) {
        String mensaje = "Ficha " + numeroFicha + ", favor pasar a la estación " + nombreEstacion;
        reproducirMensaje(mensaje);
    }

    public static void reproducirMensaje(String texto) {
        if (!isWindows) {
            System.out.println("[SIMULACIÓN AUDIO] " + texto);
            return;
        }

        if (!inicializado || powerShellProcess == null || !powerShellProcess.isAlive()) {
            System.err.println("Audio no disponible.");
            return;
        }

        try {
            String escapedText = texto.replace("'", "''").replace("\"", "\\\"");
            String command = String.format("$speak = New-Object System.Speech.Synthesis.SpeechSynthesizer; $speak.Speak('%s');", escapedText);
            sendCommandToPowerShell(command);
        } catch (Exception e) {
            System.err.println("Error al enviar comando de voz: " + e.getMessage());
        }
    }

    private static void readStreamInBackground(InputStream inputStream) {
        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    
                }
            } catch (IOException e) {
                
            }
        }).start();
    }

    public static void shutdown() {
        if (powerShellProcess != null) {
            powerShellProcess.destroy();
        }
    }
}