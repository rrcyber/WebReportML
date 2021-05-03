/********************************************************************************************/
/*  Proyecto:						Web Report-ML    										*/
/*  Autor:							Ricardo Moises Rosero Bustamante						*/
/*	Descripcion:					Clase margenes									        */
/********************************************************************************************/
package webreport.reportml;
import org.jdom.*;
import java.lang.*;

class margenes{
	// atributos
	private double top, bottom, left, right;

	// funciones

    /************************************************************/
    /*						CONSTRUCTORAS     					*/
    /************************************************************/

	/*************************************************************/
    /* Funcion:						margenes				     */
    /* Proposito:					establecer los margenes del  */
    /*								reporte						 */
    /*************************************************************/
	public margenes(Element e){
		top = obtenerAtributo(e,"margen-superior");
		bottom = obtenerAtributo(e,"margen-inferior");
		left = obtenerAtributo(e,"margen-izquierdo");
		right = obtenerAtributo(e,"margen-derecho");
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
	/*								na -1						*/
	/************************************************************/
	public double obtenerAtributo(Element x, String atrib){
        try{
        	double z = Double.parseDouble(x.getAttributeValue(atrib));
            return z;
        }
        catch(Exception ex){
            return -1;
        }
    }

    /*************************************************************/
    /* Funcion:						obtenerTopM				     */
    /* Proposito:					obtener el margen superior   */
    /*************************************************************/
	public double obtenerTopM(){
		return top;
	}

	/*************************************************************/
    /* Funcion:						obtenerBottomM			     */
    /* Proposito:					obtener el margen inferior   */
    /*************************************************************/
	public double obtenerBottomM(){
		return bottom;
	}

	/*************************************************************/
    /* Funcion:						obtenerLeftM			     */
    /* Proposito:					obtener el margen izquierdo  */
    /*************************************************************/
	public double obtenerLeftM(){
		return left;
	}

	/*************************************************************/
    /* Funcion:						obtenerRightM			     */
    /* Proposito:					obtener el margen derecho    */
    /*************************************************************/
	public double obtenerRightM(){
		return right;
	}

	/************************************************************/
    /*						MODIFICADOAS     					*/
    /************************************************************/

	/*************************************************************/
    /* Funcion:						agregarTopM				     */
    /* Proposito:					agrega el margen superior del*/
    /*								reporte                      */
    /*************************************************************/
	public void agregarTopM(double tm){
    	top = tm;
    }

    /*************************************************************/
    /* Funcion:						agregarBottomM			     */
    /* Proposito:					agregar el margen inferior   */
    /*								del reporte					 */
    /*************************************************************/
	public void agregarBottomM(double bm){
    	bottom = bm;
    }

    /*************************************************************/
    /* Funcion:						agregarLeftM			     */
    /* Proposito:					agregar el margen izquierdo  */
    /*								del reporte                  */
    /*************************************************************/
	public void agregarLeftM(double lm){
    	left = lm;
    }

    /*************************************************************/
    /* Funcion:						agregarRightM			     */
    /* Proposito:					agregar el margen dereacho   */
    /*								del reporte                  */
    /*************************************************************/
	public void agregarRightM(double rm){
    	right = rm;
    }
}