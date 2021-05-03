/********************************************************************************************/
/*  Proyecto:						Web Report-ML    										*/
/*  Autor:							Ricardo Moises Rosero Bustamante						*/
/*	Descripcion:					Clase conexion      									*/
/********************************************************************************************/
package webreport.reportml;
import org.jdom.*;

class conexion{
	// atributos
	private String driv, ur, us, pas;

    /************************************************************/
    /*						CONSTRUCTORAS     					*/
    /************************************************************/

	/************************************************************/
    /*  Funcion:					conexion					*/
    /*	Proposito:					crear una conexion con la 	*/
    /*								Base de Datos				*/
    /************************************************************/
	public conexion(Element e){
		driv = obtenerAtributo(e,"conector");
		ur = obtenerAtributo(e,"url");
		us = obtenerAtributo(e,"usuario");
		pas = obtenerAtributo(e,"clave");
	}

	/************************************************************/
    /*						ANALIZADORAS     					*/
    /************************************************************/

	/************************************************************/
	/*  Funcion:					obtenerDriver				*/
	/*	Proposito:					retornar el nombre del      */
	/*								driver						*/
	/************************************************************/
	public String obtenerDriver(){
		return driv;
	}

	/************************************************************/
	/*  Funcion:					obtenerUrl	    			*/
	/*	Proposito:					retornar el url del driver  */
	/************************************************************/
	public String obtenerUrl(){
		return ur;
	}

	/************************************************************/
	/*  Funcion:					obtenerUser					*/
	/*	Proposito:					retornar el nombre del usua-*/
	/*								rio de la conexion			*/
	/************************************************************/
	public String obtenerUser(){
		return us;
	}

	/************************************************************/
	/*  Funcion:					obtenerPassword				*/
	/*	Proposito:					retornar la contraseña del  */
	/*								usuario de la conexion		*/
	/************************************************************/
	public String obtenerPassword(){
		return pas;
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
}