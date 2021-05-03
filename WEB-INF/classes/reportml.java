/********************************************************************************************/
/*  Proyecto:						Web Report-ML    										*/
/*  Autor:							Ricardo Moises Rosero Bustamante						*/
/*	Descripcion:					Clase reportMLServlet									*/
/********************************************************************************************/
package webreport.reportml;
import java.io.*;
import java.util.*;

class reporml{

	// funciones
    public static void main(String[] args){
        // cargamos nuestro reporte
        WebReport wr = new WebReport( "d:/reportml/reporte2.rml" );

		if(wr.obtenerSalida().equals("html")){
		    String s = wr.exportarHTML();
		    System.out.println("Elreporte se ha generado con exito");
        }
		else if(wr.obtenerSalida().equals("docbook")){
			wr.exportarDocBook();
			System.out.println("Elreporte se ha generado con exito");
		}
		else if(wr.obtenerSalida().equals("pdf")){
			wr.exportarPDF();
			System.out.println("Elreporte se ha generado con exito");
		}
	}
}