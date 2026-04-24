/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import model.Ficha;
import java.io.File;
import java.io.FileOutputStream;
/**
 *
 * @author JD Madrigal Arias
 */
public class PdfUtil {
    
    public static void generarPdf(Ficha ficha, String nombreSucursal){
    try{
        //------carpeta donde se guardan los Pdf------
        String carpeta= "data/";
        new File(carpeta).mkdirs();//asegura que exista
        
        String rutaArchivo= carpeta+"ficha_"+ ficha.getNumero()+ ".pdf";
        Document documento=new Document();// crea el documento
        PdfWriter.getInstance(documento,new FileOutputStream(rutaArchivo));// se conecta con el archivo
        
        documento.open();// abre el documento parqa escribir
        
        //lo que va a contener el Pdf
        documento.add(new Paragraph("=====FICHA===== "));
         documento.add(new Paragraph("Numero: " + String.format("%03d" ,ficha.getNumero())));
           documento.add(new Paragraph("Cedula: " + ficha.getCedulaCliente()));
             documento.add(new Paragraph("Trámite: " + ficha.getIdTramite()));
               documento.add(new Paragraph("Sucursal: " + nombreSucursal));
                 documento.add(new Paragraph("Fecha: " + ficha.getFechaHoraGeneracion().toString()));
                 
                 if(ficha.isPreferencial()){
                 documento.add(new Paragraph("*****Preferencia*****"));//solo si cumple 
                 }
                 documento.close();//guarda y  lo cierra
                 
    }catch (Exception e){
        e.printStackTrace();//error en consola
    }
    }
}
