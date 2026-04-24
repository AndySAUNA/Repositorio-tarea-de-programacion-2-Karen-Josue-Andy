package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class JsonUtil {
    private static final Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
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
        } catch (Exception e) {
            System.err.println("Error al cargar: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //adaptador con el proposito de hacer que la utilidad pueda mannejar LocalDate, codigo robado de una busqueda de google (Andy)
    public static class LocalDateAdapter extends TypeAdapter<LocalDate> {
        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            
            out.value(value.toString()); // Serializes to "yyyy-MM-dd"
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
            return LocalDate.parse(in.nextString());
        }
    }
}