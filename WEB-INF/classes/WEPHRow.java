/********************************************************************************************/
/*  Proyecto:						Web Report-ML    										*/
/*  Autor:							Ricardo Moises Rosero Bustamante						*/
/*	Descripcion:					Clase WEPHRow       									*/
/********************************************************************************************/
package webreport.reportml;
import org.jdom.*;
import java.util.*;
import java.lang.String.*;

class WEPHRow{
	// atributos
    private ArrayList campos;

    // funciones

    /************************************************************/
    /*						CONSTRUCTORAS     					*/
    /************************************************************/

	/*************************************************************/
    /* Funcion:						WEPHRow 				     */
    /* Proposito:					crear la lista de campos para*/
    /*								una fila					 */
    /*************************************************************/
	public WEPHRow(Element e){
        List hijos = e.getChildren();
        int i;
        WEPHField camp;
        campos = new ArrayList();
        for( i = 0; i <= (hijos.size() - 1); i ++ ){
            Element r = (Element) hijos.get(i);
            if( r.getName().equals("campo")){
                camp = new WEPHField(r);
                campos.add(camp);
            }
        }
    }

    /************************************************************/
    /*						ANALIZADORAS     					*/
    /************************************************************/

	/*************************************************************/
    /* Funcion:						obtenerCampo			     */
    /* Proposito:					obtener un campo de una posi */
    /*								csion dada					 */
    /*************************************************************/
	public WEPHField obtenerCampo(int pos){
        return (WEPHField) campos.get(pos);
    }

    /*************************************************************/
    /* Funcion:						obtenerTamaño			     */
    /* Proposito:					obtener el tamaño de la lista*/
    /*								de campos					 */
    /*************************************************************/
	public int obtenerTamaño(){
        return campos.size();
    }

    /*************************************************************/
    /* Funcion:						obtenerTexto			     */
    /* Proposito:					obtener el texto de un campo */
    /*								dada la posicion de este     */
    /*************************************************************/
	public String obtenerTexto(int pos){
        WEPHField campo;
        campo = (WEPHField) campos.get(pos);
        return ""+campo;
    }
}