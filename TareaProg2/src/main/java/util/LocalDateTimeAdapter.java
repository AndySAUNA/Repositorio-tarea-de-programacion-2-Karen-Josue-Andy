/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDateTime;
/**
 *
 * @author JD Madrigal Arias
 */
public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {

    @Override
    public void write(JsonWriter output, LocalDateTime value) throws IOException {
        if(value!=null){
            output.value(value.toString());
        }else{
           output.nullValue();
        }
    }

    @Override
    public LocalDateTime read(JsonReader reader) throws IOException {
        String fecha= reader.nextString();
        if(fecha!=null){
            LocalDateTime.parse(fecha);
        }
        return null;//que se "vacie" el texto y se ponga otro nuevo cada que ocurra.
    }
       
  
    
}
