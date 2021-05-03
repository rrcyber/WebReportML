/********************************************************************************************/
/*  Proyecto:						Web Report-ML    										*/
/*  Autor:							Ricardo Moises Rosero Bustamante						*/
/*	Descripcion:					Clase outPut        									*/
/********************************************************************************************/
package webreport.reportml;
import org.jdom.*;

class outPut{
	// atributos
	private String archivo, formato;

	// funciones

	/************************************************************/
    /*						CONSTRUCTORAS     					*/
    /************************************************************/

	/************************************************************/
    /*  Funcion:					outPut						*/
    /*	Proposito:					crear el formato de salida	*/
    /*								de un reporte				*/
    /************************************************************/
	public outPut(Element e){
		archivo = obtenerAtributo(e,"archivo");
		formato = obtenerAtributo(e,"formato");
	}

	/************************************************************/
    /*						ANALIZADORAS     					*/
    /************************************************************/

	/************************************************************/
	/*	Funcion:					obtenerAtributo				*/
	/*	Proposito:					retornar el valor de un     */
	/*								atributo de un elemento		*/
	/*	Parametros:					el elemento y el atributo	*/
	/*	Curso alternativo		    si el elemento tiene atribu	*/
	/*	de eventos:					to se retorna el valor de di*/
	/*								cho atributo, sino se retor	*/
	/*								na vacio					*/
	/************************************************************/
	public String obtenerAtributo(Element x, String atrib){
        try{
            return x.getAttributeValue(atrib);
        }
        catch(Exception ex){
            return " ";
        }
    }

	/*************************************************************/
    /* Funcion:						obtenerFormato               */
    /* Proposito:					obtener el nombre del formato*/
    /*************************************************************/
	public String obtenerFormato(){
		return formato;
	}

	/*************************************************************/
    /* Funcion:						obtenerArchivo               */
    /* Proposito:					obtener el nombre del archivo*/
    /*************************************************************/
	public String obtenerArchivo(){
		return archivo;
	}

	/************************************************************/
    /*						MODIFICADORAS     					*/
    /************************************************************/

	/*************************************************************/
    /* Funcion:						agregarFormato			     */
    /* Proposito:					agregar un formato           */
    /*************************************************************/
	public void agregarFormato(String tFormato){
		formato = tFormato;
	}

	/*************************************************************/
    /* Funcion:						agregarArchivo			     */
    /* Proposito:					agregar un archivo           */
    /*************************************************************/
	public void agregarArchivo(String nombre){
		archivo = nombre;
	}
}