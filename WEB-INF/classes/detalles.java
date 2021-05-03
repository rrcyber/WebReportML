/********************************************************************************************/
/*  Proyecto:						Web Report-ML    										*/
/*  Autor:							Ricardo Moises Rosero Bustamante						*/
/*	Descripcion:					Clase detalles      									*/
/********************************************************************************************/
package webreport.reportml;
import org.jdom.*;
import java.util.*;

class detalles{
	// atributos
	private  ArrayList lista;

    // funciones

	/*************************************************************/
    /* Funcion:						detalles				     */
    /* Proposito:					crear una lista llama detalle*/
    /*								cuyos elementos de esta son  */
    /*								las filas					 */
    /*************************************************************/
	public detalles(Element e){
		List hijos = e.getChildren();
		int i;
		WEPHRow fila;
		lista = new ArrayList();
		for( i=0; i <= (hijos.size() - 1); i++ ){
			Element r = (Element) hijos.get(i);
			if( r.getName().equals("fila")){
				fila = new WEPHRow(r);
				lista.add(fila);
			}

		}
	}

	/************************************************************/
    /*						ANALIZADORAS     					*/
    /************************************************************/

	/*************************************************************/
    /* Funcion:						obtenerFila				     */
    /* Proposito:					obtiene una fila dada la posi*/
    /*								cion						 */
    /*************************************************************/
	public WEPHRow obtenerFila(int pos){
		return (WEPHRow)lista.get(pos);
	}

	/*************************************************************/
    /* Funcion:						obtenerTamaño    		     */
    /* Proposito:					obtiene el tamaño de la lista*/
    /*								detalles					 */
    /*************************************************************/
	public int obtenerTamaño(){
		return lista.size();
	}
}