package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import util.LocalDateTimeAdapter;

public class JsonUtil {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create();

    public static <T> void guardarLista(String archivo, List<T> lista) {
        try (Writer writer = new FileWriter(archivo)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }

    public static <T> List<T> cargarLista(String archivo, Class<T> clase) {
        File file = new File(archivo);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(archivo)) {
            Type type = TypeToken.getParameterized(List.class, clase).getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            System.err.println("Error al cargar: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}