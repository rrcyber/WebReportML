/********************************************************************************************/
/*  Proyecto:						Web Report-ML    										*/
/*  Autor:							Ricardo Moises Rosero Bustamante						*/
/*	Descripcion:					Clase PageHeader    									*/
/********************************************************************************************/
package webreport.reportml;
import org.jdom.*;
import java.util.*;

class PageHeader{
	// atributos
	private ArrayList filas;

	// funciones

	/************************************************************/
    /*						CONSTRUCTORAS     					*/
    /************************************************************/

	/*************************************************************/
    /* Funcion:						PageHeader				     */
    /* Proposito:					crear una lista llamada cabe */
    /*								cera cuyos elementos son las */
    /*								filas						 */
    /*************************************************************/
	public PageHeader(Element e){
		List hijos = e.getChildren();
		WEPHRow filita;
		int i;
		filas = new ArrayList();
		for( i = 0; i <= ( hijos.size() - 1 ); i ++ ){
			Element r = (Element) hijos.get(i);
			if( r.getName().equals("fila") ){
				filita = new WEPHRow(r);
				filas.add(filita);
			}
		}
	}

	/************************************************************/
    /*						ANALIZADORAS     					*/
    /************************************************************/

	/*************************************************************/
    /* Funcion:						obtenerRow				     */
    /* Proposito:					obtiene un fila dada la posi */
    /*								cion						 */
    /*************************************************************/
	public WEPHRow obtenerRow(int pos){
		return (WEPHRow) filas.get(pos);
	}

	/*************************************************************/
    /* Funcion:						obtenerTamaño   		     */
    /* Proposito:					obtiene el tamaño de la lista*/
    /*************************************************************/
	public int obtenerTamaño(){
		return filas.size();
	}
}