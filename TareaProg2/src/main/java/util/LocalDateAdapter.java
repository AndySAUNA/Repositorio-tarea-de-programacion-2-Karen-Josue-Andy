/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDate;
/**
 *
 * @author JD Madrigal Arias
 */
public class LocalDateAdapter extends TypeAdapter<LocalDate> {
     @Override
    public void write(JsonWriter output, LocalDate value) throws IOException {
        if(value!=null){
            output.value(value.toString());
        }else{
           output.nullValue();
        }
    }

    @Override
    public LocalDate read(JsonReader reader) throws IOException {
        if(reader.peek()== com.google.gson.stream.JsonToken.NULL){
            reader.nextNull();
            return null;
        }
        return LocalDate.parse(reader.nextString());
    }
    
}
