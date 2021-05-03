/********************************************************************************************/
/*  Proyecto:						Web Report-ML    										*/
/*  Autor:							Ricardo Moises Rosero Bustamante						*/
/*	Descripcion:					Clase consulta											*/
/********************************************************************************************/
package webreport.reportml;
import org.jdom.*;

class consulta{
	private String query;

	/************************************************************/
    /*						CONSTRUCTORAS     					*/
    /************************************************************/

	public consulta(Element e){
		query = obtenerTexto(e);
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

    /*************************************************************/
    /* Funcion:						obtenerConsulta 		     */
    /* Proposito:					retornar una consulta        */
    /*************************************************************/
	public String obtenerConsulta(){
		return query;
	}
}