/********************************************************************************************/
/*  Proyecto:						Web Report-ML    										*/
/*  Autor:							Ricardo Moises Rosero Bustamante						*/
/*	Descripcion:					Clase PageFooter    									*/
/********************************************************************************************/
package webreport.reportml;
import org.jdom.*;
import java.util.*;

class PageFooter{
	//atributos
	private ArrayList fil;

	// funciones

	/************************************************************/
    /*						CONSTRUCTORAS     					*/
    /************************************************************/

	/************************************************************/
    /*  Funcion:					PageFooter					*/
    /*	Proposito:					crear un pie de pagina para */
    /*								el reporte					*/
    /************************************************************/
	public PageFooter(Element e){
		List hijos = e.getChildren();
		WEPHRow fila;
		int i;
		fil = new ArrayList();
		for( i = 0; i < ( hijos.size() ); i ++ ){
			Element r = (Element) hijos.get(i);
			if( r.getName().equals("fila") ){
				fila = new WEPHRow(r);
				fil.add(fila);
			}
		}
	}

	/************************************************************/
    /*						ANALIZADORAS     					*/
    /************************************************************/

	/************************************************************/
	/*	Funcion:					obtenerRow					*/
	/*	Proposito:					obtener un campo dad la pos	*/
	/*								icion						*/
	/************************************************************/
	public WEPHRow obtenerRow(int pos){
		return (WEPHRow) fil.get(pos);
	}

	/************************************************************/
	/*	Funcion:					obtenerTamaño				*/
	/*	Proposito:					retornar el tamaño del 		*/
	/*								ArrayList fil				*/
	/************************************************************/
	public int obtenerTamaño(){
		return fil.size();
	}
}