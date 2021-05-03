/********************************************************************************************/
/*  Proyecto:						Web Report-ML    										*/
/*  Autor:							Ricardo Moises Rosero Bustamante						*/
/*	Descripcion:					Clase descripcion   									*/
/********************************************************************************************/
package webreport.reportml;
import org.jdom.*;

class descripcion{
	// atributos
	private String texto;

	// funciones

	/************************************************************/
    /*						CONSTRUCTORAS     					*/
    /************************************************************/

	/*************************************************************/
    /* Funcion:						descripcion				     */
    /* Proposito:					establecer una breve descrip */
    /*								cion para el reporte		 */
    /*************************************************************/
	public descripcion(Element e){
		texto = obtenerTexto(e);
	}

	/************************************************************/
    /*						MODIFICADOAS     					*/
    /************************************************************/

	/*************************************************************/
    /* Funcion:						agregarDescripcion   	     */
    /* Proposito:					agrega una descripcion       */
    /*************************************************************/
	public void agregarDescripcion(String d){
		texto = d;
	}

	/************************************************************/
    /*						ANALIZADORAS     					*/
    /************************************************************/

	/*************************************************************/
    /* Funcion:						obtenerDescripcion		     */
    /* Proposito:					obtiene la descripcion del   */
    /*								reporte						 */
    /*************************************************************/
	public String obtenerDescripcion(){
		return texto;
	}

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

    /************************************************************/
    /*	Funcion:					obtenerTexto				*/
    /*	Proposito:					capturar el texto interno 	*/
    /*								de un elemento				*/
    /*	Parametro:					el elemento					*/
    /*	Curso alternativo   		si el elemento tiene texto	*/
    /*	de eventos:					interno lo retorna sino		*/
    /*								retorna vacio				*/
    /************************************************************/
    public String obtenerTexto(Element e){
    	try{
    		return e.getText();
    	}
    	catch(Exception ex){
    		return " ";
    	}
    }
}