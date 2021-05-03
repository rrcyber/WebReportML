/********************************************************************************************/
/*  Proyecto:						Web Report-ML    										*/
/*  Autor:							Ricardo Moises Rosero Bustamante						*/
/*	Descripcion:					Clase WEPHField											*/
/********************************************************************************************/
package webreport.reportml;
import org.jdom.*;
import java.util.*;

class WEPHField{
    // atributos
    private String tipo, formato, estilo, weight, decoration, fuente, tam, color_texto;
    private String color_llenado, color_borde, estilo_borde, alineacion, nombre_bd, contenido, valor_tipo, funcion;

    // funciones

    /************************************************************/
    /*						CONSTRUCTORAS     					*/
    /************************************************************/

    /************************************************************/
    /*  Funcion:					WEPHField					*/
    /*	Proposito:					crear un campo de una fila  */
    /************************************************************/
	public WEPHField(Element e){
        String dato = obtenerAtributo( e, "tipo" );
        if(dato == " "){
            tipo = "texto";
        }
        else{
            tipo = dato;
        }
        formato = obtenerAtributo(e,"formato");
        estilo = obtenerAtributo(e,"estilo");
        weight = obtenerAtributo(e,"ancho");
        decoration = obtenerAtributo(e,"decoracion");
        valor_tipo = obtenerAtributo(e,"valor");
        funcion = obtenerAtributo(e,"funcion");
        estilo_borde = obtenerAtributo(e,"borde");
        if(estilo_borde==" "){
        	estilo_borde = "single";
        }
        alineacion = obtenerAtributo(e,"alieacion");
        if(alineacion == " "){
            alineacion = "left";
        }
        fuente = obtenerAtributo(e,"fuente");
        if(fuente == " "){
            fuente = "Times New Roman";
        }
        tam = obtenerAtributo(e,"tamaño");
        if(tam== " "){
            tam = "12";
        }
        color_texto = obtenerAtributo(e,"color");
        if(color_texto==" " ){
            color_texto = "black";
        }
        color_llenado = obtenerAtributo(e,"color-relleno");
        color_borde = obtenerAtributo(e,"color-borde");
        if(color_borde==" "){
            color_borde = "black";
        }
        if((tipo.equals("bd"))||(tipo.equals("total"))){
            nombre_bd = obtenerAtributo(e,"nombre");
        }
        if((tipo.equals("texto"))||(tipo.equals("formula"))||(tipo.equals("total"))){
            contenido = obtenerContenido1(e);
        }
    }

    /************************************************************/
    /*						ANALIZADORAS     					*/
    /************************************************************/

	/************************************************************/
    /*  Funcion:					obtenerColor_Llenado		*/
    /*	Proposito:					obtener el color de relleno */
    /*								de un campo					*/
    /************************************************************/
	public String obtenerColor_Llenado(){
    	return color_llenado;
    }

    /************************************************************/
    /*  Funcion:					obtenerContenido			*/
    /*	Proposito:					obtener el contenido de un  */
    /*								campo						*/
    /************************************************************/
	public String obtenerContenido(){
    	return contenido;
    }

    /************************************************************/
    /*  Funcion:					obtenerColor_Borde			*/
    /*	Proposito:					obtener el color del borde  */
    /*								de un campo					*/
    /************************************************************/
	public String obtenerColor_Borde(){
    	return color_borde;
    }

    /************************************************************/
    /*  Funcion:					obtenerEstilo_Borde			*/
    /*	Proposito:					obtener el estilo del borde */
    /*								de un campo					*/
    /************************************************************/
	public String obtenerEstilo_Borde(){
    	return estilo_borde;
    }

    /************************************************************/
    /*  Funcion:					obtenerAlineacion			*/
    /*	Proposito:					obtener la alineación de un */
    /*								campo						*/
    /************************************************************/
	public String obtenerAlineacion(){
    	return alineacion;
    }

    /************************************************************/
    /*  Funcion:					obtenerNombreBD				*/
    /*	Proposito:					obtener el nombre de un cam */
    /*								po de la base de datos		*/
    /************************************************************/
	public String obtenerNombreBD(){
    	return nombre_bd;
    }

    /************************************************************/
    /*  Funcion:					obtenerContenido1			*/
    /*	Proposito:					obtener el contenido de un  */
    /*								elemento llamado campo      */
    /************************************************************/
	public String obtenerContenido1(Element e){
    	try{
    		return e.getText();
    	}
    	catch(Exception ex){
    		return " ";
    	}
    }

    /************************************************************/
    /*  Funcion:					obtenerValor_tipo			*/
    /*	Proposito:					obtener el valor del tipo de*/
    /*								un campo					*/
    /************************************************************/
	public String obtenerValor_tipo(){
    	return valor_tipo;
    }

    /************************************************************/
    /*  Funcion:					obtenerFuncion				*/
    /************************************************************/
	public String obtenerFuncion(){
    	return funcion;
    }

    /************************************************************/
    /*  Funcion:					obtenerTipo					*/
    /************************************************************/
	public String obtenerTipo(){
    	return tipo;
    }

    /************************************************************/
    /*  Funcion:					obtenerFormato				*/
    /************************************************************/
	public String obtenerFormato(){
    	return formato;
    }

    /************************************************************/
    /*  Funcion:					obtenerEstilo				*/
    /************************************************************/
	public String obtenerEstilo(){
    	return estilo;
    }

    /************************************************************/
    /*  Funcion:					obtenerWeight				*/
    /************************************************************/
	public String obtenerWeight(){
    	return weight;
    }

    /************************************************************/
    /*  Funcion:					obtenerDecoration			*/
    /************************************************************/
	public String obtenerDecoration(){
    	return decoration;
    }

    /************************************************************/
    /*  Funcion:					obtenerFuente				*/
    /*	Proposito:					retornar el tipo de fuente  */
    /*								de un campo					*/
    /************************************************************/
	public String obtenerFuente(){
    	return fuente;
    }

    /************************************************************/
    /*  Funcion:					obtenerTam					*/
    /*	Proposito:					retornar el tamaño de letra	*/
    /*								del texto de un campo		*/
    /************************************************************/
	public String obtenerTam(){
    	return tam;
    }

    /************************************************************/
    /*  Funcion:					obtenerColor_Texto			*/
    /*	Proposito:					obtener el color del texto  */
    /*								de un campo					*/
    /************************************************************/
	public String obtenerColor_Texto(){
    	return color_texto;
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
            String val = x.getAttributeValue(atrib);
            return ( ( val == null ) ? " " : val );
        }
        catch(Exception ex){
            return " ";
        }
    }
}