/********************************************************************************************/
/*  Proyecto:						Web Report-ML    										*/
/*  Autor:							Ricardo Moises Rosero Bustamante						*/
/*	Descripcion:					Clase titulo											*/
/********************************************************************************************/
package webreport.reportml;
import org.jdom.*;

class titulo{
	// atributos
	private String Titletype, Titlefont, TitleFontStyle, TitletextColor, TAlignment;
    private String TitletextDecor, TitleWeight, TitlefillColor, titleText, borderColor;
    private String borderStyle;
    private int Titlesize;

    // funciones

    /************************************************************/
    /*						CONSTRUCTORAS     					*/
    /************************************************************/

	/*************************************************************/
    /* Funcion:						titulo  				     */
    /* Proposito:					crear un titulo para el 	 */
    /*								reporte						 */
    /*************************************************************/
	public titulo(Element e){
    	Titletype = obtenerAtributo(e,"tipo-titulo");
    	Titlefont = obtenerAtributo(e,"fuente-titulo");
    	TitleFontStyle = obtenerAtributo(e,"estilo-fuente-titulo");
    	TitletextColor = obtenerAtributo(e,"color-texto");
    	TAlignment = obtenerAtributo(e,"alineacion-titulo");
    	TitletextDecor = obtenerAtributo(e,"decoracion-texto-titulo");
    	TitleWeight = obtenerAtributo(e,"ancho-titulo");
    	TitlefillColor = obtenerAtributo(e,"saturacion-color-titulo");
    	titleText = obtenerTexto(e);
    	borderColor = obtenerAtributo(e,"color-borde");
    	borderStyle = obtenerAtributo(e,"estilo-borde");
    	Titlesize = obtenerTamaño(e,"tamaño-titulo");
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
    /* Funcion:						obtenerTitleType		     */
    /* Proposito:					retornar el tipo del titulo  */
    /*************************************************************/
	public String obtenerTitleType(){
		return Titletype;
	}

	/*************************************************************/
    /* Funcion:						obtenerTitleFont		     */
    /* Proposito:					retornar el tipo de fuente de*/
    /*								el titulo					 */
    /*************************************************************/
	public String obtenerTitleFont(){
		return Titlefont;
	}

	/*************************************************************/
    /* Funcion:						obtenerTitleStyle		     */
    /* Proposito:					retornar el estilo de fuente */
    /*								de el titulo				 */
    /*************************************************************/
	public String obtenerTitleStyle(){
		return TitleFontStyle;
	}

	/*************************************************************/
    /* Funcion:						obtenerTitleWeight		     */
    /* Proposito:					retornar el ancho del titulo */
    /*************************************************************/
	public String obtenerTitleWeight(){
		return TitleWeight;
	}

	/*************************************************************/
    /* Funcion:						obtenerTextDecoration	     */
    /* Proposito:					retornar la decoracion del   */
    /*								texto del titulo			 */
    /*************************************************************/
	public String obtenerTextDecoration(){
		return TitletextDecor;
	}

	/*************************************************************/
    /* Funcion:						obtenerTitleTColor		     */
    /* Proposito:					retornar el color del texto  */
    /*								del titulo					 */
    /*************************************************************/
	public String obtenerTitleTColor(){
		return TitletextColor;
	}

	/*************************************************************/
    /* Funcion:						obtenerTitleFColor		     */
    /* Proposito:					retornar el color de sombrea */
    /*								do del texto del titulo		 */
    /*************************************************************/
	public String obtenerTitleFColor(){
		return TitlefillColor;
	}

	/*************************************************************/
    /* Funcion:						obtenerTitleSize		     */
    /* Proposito:					retornar el tamaño de letra  */
    /*								texto del titulo			 */
    /*************************************************************/
	public int obtenerTitleSize(){
		return Titlesize;
	}

	/*************************************************************/
    /* Funcion:						obtenerTitleText		     */
    /* Proposito:					retornar el texto del titulo */
    /*************************************************************/
	public String obtenerTitleText(){
		return titleText;
	}

	/*************************************************************/
    /* Funcion:						obtenerBorderColor		     */
    /* Proposito:					retornar el color de bordeado*/
    /*								del titulo					 */
    /*************************************************************/
	public String obtenerBorderColor(){
		return borderColor;
	}

	/*************************************************************/
    /* Funcion:						obtenerBorderStyle		     */
    /* Proposito:					retornar el estilo de bordea */
    /*								do del titulo				 */
    /*************************************************************/
	public String obtenerBorderStyle(){
		return borderStyle;
	}

	/*************************************************************/
    /* Funcion:						obtenerTAlignment		     */
    /* Proposito:					retornar la alineacion del   */
    /*								titulo						 */
    /*************************************************************/
	public String obtenerTAlignment(){
		return TAlignment;
	}

	/*************************************************************/
    /* Funcion:						obtenerTamaño   		     */
    /* Proposito:					retornar tamaño del titulo   */
    /*************************************************************/
	public int obtenerTamaño(Element e, String atrib){
    	try{
    		int i = Integer.parseInt(e.getAttributeValue(atrib));
    		return i;
    	}
    	catch(Exception ex){
    		return -1;
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