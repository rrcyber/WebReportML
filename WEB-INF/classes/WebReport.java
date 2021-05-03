/********************************************************************************************/
/*  Proyecto:						Web Report-ML    										*/
/*  Autor:							Ricardo Moises Rosero Bustamante						*/
/*	Descripcion:					Clase WebReport											*/
/********************************************************************************************/
package webreport.reportml;
import com.lowagie.text.pdf.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.*;
import org.mozilla.javascript.*;
import java.awt.Color;
import java.io.*;
import java.sql.*;
import java.lang.*;
import java.lang.Object;
import java.util.*;
import java.util.Dictionary;
import java.util.Hashtable;
import java.text.NumberFormat;
import java.text.DecimalFormat;

class WebReport{
	// atrivutos
	private WEPHRow wr;
	private WEPHField wf;
	private titulo t;
	private outPut op;
	private margenes m;
	private detalles d;
	private descripcion des;
	private conexion con;
	private consulta q;
	private PageHeader ph;
	private PageFooter pf;
	private Connection conn = null;

    // funciones

    /************************************************************/
    /*						CONSTRUCTORAS     					*/
    /************************************************************/

	/*************************************************************/
    /* Funcion:						WebReport       		     */
    /* Proposito:					crear un reporte para la WEB */
    /*************************************************************/
	public WebReport(String archivo){
		try{
			SAXBuilder builder = new SAXBuilder(false);
			Document doc = builder.build(archivo);
			Element raiz = doc.getRootElement();
			List hijos = raiz.getChildren();
			Iterator i = hijos.iterator();
			while( i.hasNext() ){
				Element e = (Element) i.next();
				System.out.println(i+"\n");
				if( e.getName().equals("conexion") ){
					con = new conexion(e);
				}
				else if( e.getName().equals("margenes") ){
					m = new margenes(e);
				}
				else if( e.getName().equals("consulta") ){
					q = new consulta(e);
				}
				else if( e.getName().equals("campo") ){
					wf = new WEPHField(e);
				}
				else if( e.getName().equals("fila") ){
					wr = new WEPHRow(e);
				}
				else if( e.getName().equals("titulo") ){
					t = new titulo(e);
				}
				else if( e.getName().equals("salida") ){
					op = new outPut(e);
				}
				else if( e.getName().equals("detalles") ){
					d = new detalles(e);
				}
				else if( e.getName().equals("descripcion") ){
					des = new descripcion(e);
				}
				else if( e.getName().equals("cabecera") ){
					ph = new PageHeader(e);
				}
				else if( e.getName().equals("pie-de-pagina") ){
					pf = new PageFooter(e);
				}
			}
		}
		catch( Exception ex ){
			ex.printStackTrace();
		}
	}

    /************************************************************/
    /*						ANALIZADORAS     					*/
    /************************************************************/

	/*************************************************************/
    /* Funcion:						obtenerSalida   		     */
    /* Proposito:					retornar la salida del repor */
    /*								te. Ésta es html, pdf ó doc- */
    /*								book						 */
    /*************************************************************/
	public String obtenerSalida() {
	   if ( op != null ) {
	       return op.obtenerFormato();
	   }
	   return "null";
	}

	/*************************************************************/
    /* Funcion:						obtenerQuery    		     */
    /* Proposito:					retornar la consulta del re- */
    /*								porte					     */
    /*************************************************************/
	public String obtenerQuery(){
		if ( q != null ) {
			return q.obtenerConsulta();
		}
		return "null";
	}

	/*************************************************************/
    /* Funcion:						obtenerTitulo   		     */
    /* Proposito:					retornar el titulo del repor-*/
    /*								te							 */
    /*************************************************************/
	public String obtenerTitulo(){
		if ( t != null ) {
			return t.obtenerTitleText();
		}
		return "null";
	}

	/*************************************************************/
    /* Funcion:						conectarBD      		     */
    /* Proposito:					retorna un conexion a la BD  */
    /*************************************************************/
	public ResultSet conectarBD() {
		// Declaración de variables
		int i, td, fd, cont;
		String  getDriver, getUser, getUrl, getPass, getQuery;
		PreparedStatement ps;
		ResultSet rs = null;

		//obtenemos los datos necesarios para establecer la conexion
		getDriver = con.obtenerDriver();
		getUser = con.obtenerUser();
		getUrl = con.obtenerUrl();
		getPass = con.obtenerPassword();

		//obtenemos la consulta
		getQuery = q.obtenerConsulta();

		try{
			//cargamos el driver
			Class.forName( getDriver );
		}
		catch(Exception ex){
			System.out.println("error");
		}

		try{
			//armamos la conexion
			conn = DriverManager.getConnection( getUrl, getUser, getPass );

			//cargamos y ejecutamos el query
			ps = conn.prepareStatement( getQuery );
			rs = ps.executeQuery();

        }
        catch( Exception ex ){
        	ex.printStackTrace();
        }

        return rs;
	}

	/*************************************************************/
    /* Funcion:						reconectarConectarBD		 */
    /* Proposito:					retornar nuevamente una conex*/
    /*								ion a la BD					 */
    /*************************************************************/
	public ResultSet reconectarConectarBD() {
		// Declaración de variables
		int i, td, fd, cont;
		String  getDriver, getUser, getUrl, getPass, getQuery;
		PreparedStatement ps;
		ResultSet rs = null;

		//obtenemos los datos necesarios para establecer la conexion
		getDriver = con.obtenerDriver();
		getUser = con.obtenerUser();
		getUrl = con.obtenerUrl();
		getPass = con.obtenerPassword();

		//obtenemos la consulta
		getQuery = q.obtenerConsulta();

		try{
			//cargamos el driver
			Class.forName( getDriver );
		}
		catch(Exception ex){
			System.out.println("error");
		}

		try{
			//armamos la conexion
			conn = DriverManager.getConnection( getUrl, getUser, getPass );

			//cargamos y ejecutamos el query
			ps = conn.prepareStatement( getQuery );
			rs = ps.executeQuery();

        }
        catch( Exception ex ){
        	ex.printStackTrace();
        }

        return rs;
	}

    /*************************************************************/
    /* Funcion:						exportarHTML    		     */
    /* Proposito:					retornar el reporte en forma */
    /*								to HTML						 */
    /*************************************************************/
	public String exportarHTML() {
		String text, texto, borderColor, borderStyle, titu, fontF, TColor, FColor, FName, alignm;
		String valor, temp, estilo1, estilo2, estilo3, descripcion;
		int contHsi, contHno, contadorSi, contadorNo, divi, dividendo, ifoot, jfoot, sizeF, j, k, tp, fp;
		double promedio, suma, producto, marginL, marginR, marginT, marginB;
		double maxi, mini, maxBd, minBd, suma2, producto2, promedio2;
		int contDsi, contDno;
		WEPHField c, campo;
		WEPHRow fila;
		Object ci;

		text = null;

		// contDsi se utiliza para hallar el número de veces que aparece el estilo de borde en el details
  		contDsi = 0;

  		// contDno se utiliza para hallar el número de veces que no aparece el estilo de borde en el details
  		contDno = 0;

  		// contadorSi se utiliza para hallar el número de veces que aparece el estilo de borde en el page-footer
  		contadorSi = 0;

  		// contadorNo se utiliza para hallar el número de veces que aparece el estilo de borde en el page-footer
  		contadorNo = 0;

  		// contHsi se utiliza para hallar el número de veces que aparece el estilo de borde en el page-header
  		contHsi = 0;

  		// contHno se utiliza para hallar el número de veces que aparece el estilo de borde en el page-header
  		contHno = 0;

  		// Se obtiene el nombre del archivo
  		FName = op.obtenerArchivo()+".html";

  		// Para obtener el título
  		titu = t.obtenerTitleText();

  		// Para obtener la fuente del título
  		fontF = t.obtenerTitleFont();


  		// Para obtener el tamaño de la letra del título
  		sizeF = t.obtenerTitleSize();

  		// Para obtener el color de fondo
  		FColor = t.obtenerTitleFColor();

  		// Para obtener la alineación del texto
  		alignm = t.obtenerTAlignment();


  		// Para obtener el estilo de la letra
  		estilo1 = t.obtenerTitleStyle();
  		estilo2 = t.obtenerTitleWeight();
  		estilo3 = t.obtenerTextDecoration();

  		// Para obtener el margen Izquierdo
  		marginL = m.obtenerLeftM();

  		// Para obtener el margen izquierdo
  		marginR = m.obtenerRightM();

  		// Para obtener el margen superior
  		marginT = m.obtenerTopM();

  		// Para obtener el margen inferior
  		marginB = m.obtenerBottomM();

  		// Para obtener la descripción
  		descripcion = des.obtenerDescripcion();

  		// Para obtener el estilo del borde
  		borderStyle = t.obtenerBorderStyle();

  		// Para obtener el color del borde
  		borderColor = t.obtenerBorderColor();

  		File a;
		a = new File( FName );
		// Se crea un nuevo archivo externo con el nombre asignado en la variable "a"
  		try{
  			a.createNewFile();

  			FileWriter out = new FileWriter(a);
  			text = "<html><head><title>";
  			text = text + "Web Report HTML</title></head>";
  			text = text + "<body bgcolor=white" ;

  			// Si lo márgenes son negativos le asignamos un valor por default, en este caso 1.0
  			if ( marginL < 0 ){
  				marginL = 4.0;
  			}
  			if ( marginR < 0 ){
  				marginR = 3.0;
  			}
  			if ( marginT < 0 ){
  				marginT = 4.0;
  			}
  			if ( marginB < 0 ){
  				marginB = 3.0;
  			}

  			String ml, mr, mt, mb;
  			mt = Double.toString(marginT);
  			ml = Double.toString(marginL);
  			mr = Double.toString(marginR);
  			mb = Double.toString(marginB);

  			text = text + " style='margin-top:"+mt+"cm;"+" margin-left:"+ml+"cm;"+" margin-right:"+mr+"cm;"+" margin-bottom:"+mb+"cm'>";
  			text = text + "<!-- Descripcion: "+descripcion+"-->";

  			if (borderStyle != " "){
  				text = text + "<table border=3 align=center width=80% style='border-style:"+borderStyle+"; border-color:"+borderColor+"; background-color:"+FColor+"'>";
  				text = text + "<tr><td align='"+alignm+"'>";
  			}

  			// para la alineacion del titulo
  			text = text + "<p ";

  			text = text + " style='";
  			text = text + "font-style:"+estilo1+";";
  			text = text + "text-decoration:" + estilo3 + ";";
  			text = text + "font-weight:" + estilo2 + ";";

  			// Para la fuente del título
  			text = text + "font-family:" + fontF + ";";

  			// Para el tamaño del título
  			text = text + "font-size:" + Integer.toString(sizeF) + "pt;";

  			// Para el color del título
  			text = text + "color:" + t.obtenerTitleTColor();

  			// Cerramos la marca del párrafo y mostramos el titulo
  			text = text + "'>" + titu + "</p>";

  			// Cerramos los bordes de la tabla en caso de que el usuario haya requerido
  			// un estilo de borde
  			if ( borderStyle != " " ){
  				text = text + "</td></tr></table>";
  			}

  			text = text +"<br><br>";

  			text = text + "<table border='3' align='center' width='100%' style='border-color:black'>";
            text = text + "<tr>";

        	// *** Recorremos el page-header ***
        	for ( tp = 0 ; tp <= ph.obtenerTamaño() - 1; tp ++ ){
        		fila = ph.obtenerRow(tp);
        	//	text = text + "<td>";
                for ( fp = 0; fp <= fila.obtenerTamaño()-1; fp ++ ){
        			c = fila.obtenerCampo(fp);
        			// Si el tipo de campo es Text, se imprime el contenido con sus respectivas acciones
        			if( c.obtenerTipo().equals("texto") ){
        				// Para la fuente del page-header
        				text = text + "<th align='"+c.obtenerAlineacion()+"'><center><p style='font-family:"+c.obtenerFuente()+";";
        				// si el estilo de letra es cursiva
        				if ( c.obtenerEstilo().equals("italic") ){
        					text = text + "font-style:" + c.obtenerEstilo() + ";";
          				}
          				// si el estilo de letra es subrayada
          				if( c.obtenerDecoration().equals("underline") ){
          					text = text + "text-decoration:" + c.obtenerDecoration() + ";";
          				}
          				// si el estilo de letra es negrita
          				if( c.obtenerWeight().equals("bold") ){
          					text = text + "font-weight:" + c.obtenerWeight() + ";";
          				}
          				// Para el tamaño de la letra
          				text = text + "font-size:" + c.obtenerTam() + "pt;";
          				// Para el color de fondo de la celda
          				if(c.obtenerColor_Llenado() != " " ){
          					text = text + "background-color:" + c.obtenerColor_Llenado() + ";";
          				}
          				// Asignamos un color de texto
          				if(c.obtenerColor_Texto() != "black" ){
          					text = text + "color:" +c.obtenerColor_Texto()+ ";";
          				}
          				// Cerramos la marca del font y mostramos el contenido
          				text = text + "'>" + c.obtenerContenido() + "</p></center></th>";
          			}

          		}
          	}// *** Final del recorrido del page-header ***
          	text = text + "</tr>";
            ResultSet rs = conectarBD();
            ResultSetMetaData rsmd;
            try{
            	if( rs != null){
            		while( rs.next() ){
                        rsmd = rs.getMetaData();
                        Hashtable dict = new Hashtable();
                        try {
                            int cant = rsmd.getColumnCount();
                            for ( int i = 1; i <= cant; i++ ) {
                                String nom = rsmd.getColumnName(i);
                                String data = rs.getString(i);
                                dict.put( nom, data );
                            }
                        }
                        catch ( Exception ex ) {
                            ex.printStackTrace();
                            System.exit( 0 );
                        }

            			if( ( d != null ) ){
	            			// *** Recorremos el contenido de la base de datos a mostrar en el reporte ***
	    					// Realizamos la conexión a la base de datos correspondiente
	    					for( j = 0; j <= d.obtenerTamaño()-1; j ++ ){
	    						text = text + "<tr>";
	    						fila = d.obtenerFila(j);
	    						for( k = 0; k <= fila.obtenerTamaño()-1; k ++ ){
	    							c = fila.obtenerCampo(k);


	    							if( c.obtenerTipo().equals("texto") ){
	    								// Para el color del texto
	    								if(c.obtenerColor_Texto() != "black" ){
	    									text = text + "<td align='"+c.obtenerAlineacion()+"'><p style='color:" +c.obtenerColor_Texto() + ";";
	    								}
	    								else{
	    									text = text + "<td align='"+c.obtenerAlineacion()+"'><p style='";
	    								}

	    								// Para el estilo de letra negrita
	    								if(c.obtenerWeight().equals("bold")){
	    									text = text + "font-weight:" + c.obtenerWeight() + ";";
	    								}

	    								// Para el estilo de letra cursiva
	            						if(c.obtenerEstilo().equals("italic")){
	            							text = text + "font-style:" + c.obtenerEstilo() + ";";
	            						}

	            						// Para el estilo de letra underline
	            						if(c.obtenerDecoration().equals("underline")){
	            							text = text + "text-decoration:" + c.obtenerDecoration() + ";";
	            						}
		            					// Para el color de relleno de la celda
	            						if(c.obtenerColor_Llenado() != " "){
		           							text = text + "background-color:" + c.obtenerColor_Llenado() + ";";
	           							}
	           							// Para el tipo de fuente
	           							if(c.obtenerFuente() != "times new roman"){
	           								text = text + "font-familiy:" + c.obtenerFuente() + ";";
	           							}

	            						// Para el tamaño de letra
		           						if( c.obtenerTam() != " "){
		           							text = text + "font-size:" + c.obtenerTam() + "pt;";
	    	       						}

	           							text = text + "'>" + c.obtenerContenido() + "</p></td>";
	           						}
       							    else if( c.obtenerTipo().equals("bd") ){
       									// Para el color de texto
       									if(c.obtenerColor_Texto() != "black"){
       										text = text + "<td align='"+c.obtenerAlineacion()+"'><p style='color:"+c.obtenerColor_Texto() +";";
       									}
       									else{
      	  									text = text + "<td align='"+c.obtenerAlineacion()+"'><p style='";
          								}
	       								// Para el estilo de letra negrita
    									if(c.obtenerWeight().equals("bold")){
    										text = text + "font-weight:" + c.obtenerWeight() + ";";
    									}
	   									// Para el estilo de letra cursiva
	           							if(c.obtenerEstilo().equals("italic")){
    	       								text = text + "font-style:" + c.obtenerEstilo() + ";";
        	   							}

           								// Para el estilo de letra underline
           								if(c.obtenerDecoration().equals("underline")){
       	    								text = text + "text-decoration:" + c.obtenerDecoration() + ";";
            							}

    	       							// Para el color de relleno de la celda
        	   							if(c.obtenerColor_Llenado() != " "){
       	    								text = text + "background-color:" + c.obtenerColor_Llenado() + ";";
            							}

           								// Para el tipo de fuente
           								if(c.obtenerFuente() != "times new roman"){
       	    								text = text + "font-familiy:" + c.obtenerFuente() + ";";
            							}

           								// Para el tamaño de letra
           								if( c.obtenerTam() != " "){
           									text = text + "font-size:" + c.obtenerTam() + "pt;";
           								}

           								if(c.obtenerFormato().equals(" ")){
                                            text = text + "'>" + dict.get(c.obtenerNombreBD()) + "</p></td>";
           								}
           								else{
           									DecimalFormat myFormatter = new DecimalFormat(c.obtenerFormato());
                                            String output = myFormatter.format(Double.parseDouble((String)dict.get(c.obtenerNombreBD())));

           									text = text + "'>";
           									text = text + output;
        								    text = text + "</p></td>";
       	   								}
       	   							}
       	   							else if( c.obtenerTipo().equals("formula") ){
       	   								String formula = c.obtenerContenido();
										Context cx = Context.enter();
										cx.setLanguageVersion(Context.VERSION_1_2);
										Scriptable scope = cx.initStandardObjects( null );
										Object result = null;
										try {
											Scriptable query = Context.toObject(dict, scope);
											scope.put("query", scope, query);
											result = cx.evaluateString(scope, formula, c.obtenerContenido(), 1, null);
										}
										catch (JavaScriptException jse) {
											jse.printStackTrace();
											System.exit( 0 );
										}
       	   								cx.exit();

        								// Para el color de texto
       									if(c.obtenerColor_Texto() != "black"){
       										text = text + "<td align='"+c.obtenerAlineacion()+"'><p style='color:"+c.obtenerColor_Texto() +";";
       									}
       									else{
      	  									text = text + "<td align='"+c.obtenerAlineacion()+"'><p style='";
          								}
	       								// Para el estilo de letra negrita
    									if(c.obtenerWeight().equals("bold")){
    										text = text + "font-weight:" + c.obtenerWeight() + ";";
    									}
	   									// Para el estilo de letra cursiva
	           							if(c.obtenerEstilo().equals("italic")){
    	       								text = text + "font-style:" + c.obtenerEstilo() + ";";
        	   							}

           								// Para el estilo de letra underline
           								if(c.obtenerDecoration().equals("underline")){
       	    								text = text + "text-decoration:" + c.obtenerDecoration() + ";";
            							}

    	       							// Para el color de relleno de la celda
        	   							if(c.obtenerColor_Llenado() != " "){
       	    								text = text + "background-color:" + c.obtenerColor_Llenado() + ";";
            							}

           								// Para el tipo de fuente
           								if(c.obtenerFuente().equals(" ")){
       	    								text = text + "font-familiy:times new roman;";
            							}
           								else if(c.obtenerFuente() != "times new roman"){
       	    								text = text + "font-familiy:" + c.obtenerFuente() + ";";
            							}

           								// Para el tamaño de letra
           								if( c.obtenerTam() != " "){
           									text = text + "font-size:" + c.obtenerTam() + "pt;";
           								}

           								if(c.obtenerFormato().equals(" ")){

                                            text = text + "'>" + cx.toString( result ) + "</p></td>";
           								}
           								else{
           									DecimalFormat myFormatter = new DecimalFormat(c.obtenerFormato());
                                            String output = myFormatter.format(cx.toString( result ));

           									text = text + "'>";
           									text = text + output;
        								    text = text + "</p></td>";
       	   								}

       	   							}
       	   						}
       	   					    text = text + "</tr>";
       	   					}
       	   				}
       	   			}
       	   		}
       	   		cerrarBD();
    			text = text + "</table>";
       	   	}
       	   	catch( Exception ex ){
       	   		ex.printStackTrace();
       	   	}
       	   	text = text +"<br><br>";
  			text = text + "<table border='3' align='center' width='50%'>";


          	double[] cont = new double[20];
          	int c1 = 0;

          	for( int w = 0; w <= pf.obtenerTamaño() - 1; ++ w ){
          		fila = pf.obtenerRow(w);
          		for( int z = 0; z <= fila.obtenerTamaño() - 1; ++ z ){
          			c = fila.obtenerCampo(z);
          			if( c.obtenerTipo().equals("total") || c.obtenerTipo().equals("texto") ){
          				if( c.obtenerFuncion().equals("suma") ){
          					cont[c1++] = 0;
          				}
          				else if( c.obtenerFuncion().equals("contar") ){
          					cont[c1++] = 0;
          				}
          				else if( c.obtenerFuncion().equals("mult") ){
          					cont[c1++] = 1;
          				}
          				else if( c.obtenerFuncion().equals("mayor") ){
          					maxi = Double.MIN_VALUE;
          				}
          				else if( c.obtenerFuncion().equals("menor") ){
          					mini = Double.MAX_VALUE;
          				}
          				else if( c.obtenerFuncion().equals("promedio") ){
          					cont[c1++] = 0;
          				}
          			}
          		}
          	}

          	ResultSet rs1 = reconectarConectarBD();
          	int n = 0;
          	double s10 = 0;
          	try{
          		if( rs1 != null ){
          			while( rs1.next() ){
          				n ++;
          				int x = 0;
          				for( int w1 = 0; w1 <= pf.obtenerTamaño() - 1; ++ w1 ){
          					fila = pf.obtenerRow(w1);
          					for( int z1 = 0; z1 <= fila.obtenerTamaño() - 1; ++ z1 ){
          						c = fila.obtenerCampo(z1);
          						if( c.obtenerTipo().equals("total") || c.obtenerTipo().equals("texto") ){
          							if( c.obtenerFuncion().equals("suma") ){
          								cont[x] += rs1.getDouble(c.obtenerNombreBD());
          								x ++;
          							}
          							else if( c.obtenerFuncion().equals("contar") ){
          								cont[x] += rs1.getDouble(c.obtenerNombreBD());
          								x ++;
          							}
          							else if( c.obtenerFuncion().equals("mult") ){
          								cont[x] *= rs1.getDouble(c.obtenerNombreBD());
          								x ++;
          							}
          							else if( c.obtenerFuncion().equals("mayor") ){
          								double a2 = rs1.getDouble(c.obtenerNombreBD());
          								if( a2 > cont[x] ){
          									cont[x] = a2;
          								}
          								x ++;
          							}
          							else if( c.obtenerFuncion().equals("menor") ){
          								double b = rs1.getDouble(c.obtenerNombreBD());
          								if( b < cont[x] ){
          									cont[x] = b;
          								}
          								x ++;
          							}
          							else if( c.obtenerFuncion().equals("promedio") ){
          								cont[x] += rs1.getDouble(c.obtenerNombreBD()) / n;
          								x ++;
          							}
          						}
          					}
          				}
          			}
          		}
                int dz1= 0;
        	  	for( int w2 = 0; w2 <= pf.obtenerTamaño() - 1; ++ w2 ){
        	  		fila = pf.obtenerRow(w2);
        	  		for( int z2 = 0; z2 <= fila.obtenerTamaño() - 1; ++ z2 ){
        	  			c = fila.obtenerCampo(z2);
        	  			if( c.obtenerTipo().equals("texto") ){
        	  				// Para el color de texto
    	   					if(c.obtenerColor_Texto() != "black"){
    	   						text = text + "<th align='"+c.obtenerAlineacion()+"'><p style='color:"+c.obtenerColor_Texto() +";";
    	   					}
    	   					else{
    	  	  					text = text + "<th align='"+c.obtenerAlineacion()+"'><p style='";
    	      				}
		       				// Para el estilo de letra negrita
    						if(c.obtenerWeight().equals("bold")){
    							text = text + "font-weight:" + c.obtenerWeight() + ";";
	    					}
		   					// Para el estilo de letra cursiva
		           			if(c.obtenerEstilo().equals("italic")){
	    	       				text = text + "font-style:" + c.obtenerEstilo() + ";";
	        	   			}
							// Para el estilo de letra underline
	           				if(c.obtenerDecoration().equals("underline")){
	       	    				text = text + "text-decoration:" + c.obtenerDecoration() + ";";
	            			}
							// Para el color de relleno de la celda
	        	   			if(c.obtenerColor_Llenado() != " "){
	       	    				text = text + "background-color:" + c.obtenerColor_Llenado() + ";";
	            			}
           					// Para el tipo de fuente
           					if(c.obtenerFuente().equals(" ")){
       	    					text = text + "font-familiy:times new roman;";
            				}
           					else if(c.obtenerFuente() != "times new roman"){
       	    					text = text + "font-familiy:" + c.obtenerFuente() + ";";
            				}
	           				// Para el tamaño de letra
	           				if( c.obtenerTam() != " "){
	           					text = text + "font-size:" + c.obtenerTam() + "pt;";
	           				}

	           				text = text + "'>" + c.obtenerContenido() + "</p></th>";

    	      		    }
       		   			else if( c.obtenerTipo().equals("total") ){
   		       				if( c.obtenerFuncion().equals("suma") ){
	          					// Para el color de texto
    	   						if(c.obtenerColor_Texto() != "black"){
    	   							text = text + "<td align='"+c.obtenerAlineacion()+"'><p style='color:"+c.obtenerColor_Texto() +";";
    	   						}
    	   						else{
    	  	  						text = text + "<td align='"+c.obtenerAlineacion()+"'><p style='";
    	      					}
		       					// Para el estilo de letra negrita
    							if(c.obtenerWeight().equals("bold")){
    								text = text + "font-weight:" + c.obtenerWeight() + ";";
	    						}
		   						// Para el estilo de letra cursiva
		           				if(c.obtenerEstilo().equals("italic")){
	    	       					text = text + "font-style:" + c.obtenerEstilo() + ";";
	        	   				}
								// Para el estilo de letra underline
	           					if(c.obtenerDecoration().equals("underline")){
	       	    					text = text + "text-decoration:" + c.obtenerDecoration() + ";";
	            				}
								// Para el color de relleno de la celda
	        	   				if(c.obtenerColor_Llenado() != " "){
	       	    					text = text + "background-color:" + c.obtenerColor_Llenado() + ";";
	            				}

	           					// Para el tipo de fuente
	           					if(c.obtenerFuente().equals(" ")){
       	    						text = text + "font-familiy:times new roman;";
            					}
           						else if(c.obtenerFuente() != "times new roman"){
       	    						text = text + "font-familiy:" + c.obtenerFuente() + ";";
            					}

	           					// Para el tamaño de letra
	           					if( c.obtenerTam() != " "){
	           						text = text + "font-size:" + c.obtenerTam() + "pt;";
	           					}

	           					if(c.obtenerFormato().equals(" ")){
	                                text = text + "'>" + cont[dz1++] + "</p></td>";
	           					}
	           					else{
	           						DecimalFormat myFormatter1 = new DecimalFormat(c.obtenerFormato());
	                                String output1 = myFormatter1.format(cont[dz1++]);

        	   						text = text + "'>";
        	   						text = text + output1;
        							text = text + "</p></td>";
    	   	   				    }
    	      				}
    	      				else if( c.obtenerFuncion().equals("contar") ){
    	      					// Para el color de texto
    	   						if(c.obtenerColor_Texto() != "black"){
    	   							text = text + "<td align='"+c.obtenerAlineacion()+"'><p style='color:"+c.obtenerColor_Texto() +";";
    	   						}
    	   						else{
    	  	  						text = text + "<td align='"+c.obtenerAlineacion()+"'><p style='";
    	      					}
		       					// Para el estilo de letra negrita
    							if(c.obtenerWeight().equals("bold")){
    								text = text + "font-weight:" + c.obtenerWeight() + ";";
	    						}
		   						// Para el estilo de letra cursiva
		           				if(c.obtenerEstilo().equals("italic")){
	    	       					text = text + "font-style:" + c.obtenerEstilo() + ";";
	        	   				}
								// Para el estilo de letra underline
	           					if(c.obtenerDecoration().equals("underline")){
	       	    					text = text + "text-decoration:" + c.obtenerDecoration() + ";";
	            				}
								// Para el color de relleno de la celda
	        	   				if(c.obtenerColor_Llenado() != " "){
	       	    					text = text + "background-color:" + c.obtenerColor_Llenado() + ";";
	            				}

	           					// Para el tipo de fuente
	           					if(c.obtenerFuente().equals(" ")){
       	    						text = text + "font-familiy:times new roman;";
            					}
           						else if(c.obtenerFuente() != "times new roman"){
       	    						text = text + "font-familiy:" + c.obtenerFuente() + ";";
            					}

	           					// Para el tamaño de letra
	           					if( c.obtenerTam() != " "){
	           						text = text + "font-size:" + c.obtenerTam() + "pt;";
	           					}

	           					if(c.obtenerFormato().equals(" ")){
	                                text = text + "'>" + cont[dz1++] + "</p></td>";
	           					}
	           					else{
	           						DecimalFormat myFormatter1 = new DecimalFormat(c.obtenerFormato());
	                                String output1 = myFormatter1.format(cont[dz1++]);

        	   						text = text + "'>";
        	   						text = text + output1;
        							text = text + "</p></td>";
    	   	   				    }
    	      				}
    	      				else if( c.obtenerFuncion().equals("mult") ){
    	      					// Para el color de texto
    	   						if(c.obtenerColor_Texto() != "black"){
    	   							text = text + "<td align='"+c.obtenerAlineacion()+"'><p style='color:"+c.obtenerColor_Texto() +";";
    	   						}
    	   						else{
    	  	  						text = text + "<td align='"+c.obtenerAlineacion()+"'><p style='";
    	      					}
		       					// Para el estilo de letra negrita
	    						if(c.obtenerWeight().equals("bold")){
	    							text = text + "font-weight:" + c.obtenerWeight() + ";";
	    						}
		   						// Para el estilo de letra cursiva
		           				if(c.obtenerEstilo().equals("italic")){
	    	       					text = text + "font-style:" + c.obtenerEstilo() + ";";
	        	   				}
								// Para el estilo de letra underline
	           					if(c.obtenerDecoration().equals("underline")){
	       	    					text = text + "text-decoration:" + c.obtenerDecoration() + ";";
	            				}
								// Para el color de relleno de la celda
	        	   				if(c.obtenerColor_Llenado() != " "){
	       	    					text = text + "background-color:" + c.obtenerColor_Llenado() + ";";
	            				}

	           					// Para el tipo de fuente
	           					if(c.obtenerFuente().equals(" ")){
       	    						text = text + "font-familiy:times new roman;";
            					}
           						else if(c.obtenerFuente() != "times new roman"){
       	    						text = text + "font-familiy:" + c.obtenerFuente() + ";";
            					}

	           					// Para el tamaño de letra
	           					if( c.obtenerTam() != " "){
	           						text = text + "font-size:" + c.obtenerTam() + "pt;";
	           					}

	           					if(c.obtenerFormato().equals(" ")){
	                                text = text + "'>" + cont[dz1++] + "</p></td>";
	           					}
	           					else{
	           						DecimalFormat myFormatter1 = new DecimalFormat(c.obtenerFormato());
	                                String output1 = myFormatter1.format(cont[dz1++]);

           							text = text + "'>";
           							text = text + output1;
        							text = text + "</p></td>";
       	   					    }
          					}
          					else if( c.obtenerFuncion().equals("mayor") ){
          						// Para el color de texto
       							if(c.obtenerColor_Texto() != "black"){
       								text = text + "<td align='"+c.obtenerAlineacion()+"'><p style='color:"+c.obtenerColor_Texto() +";";
       							}
       							else{
      	  							text = text + "<td align='"+c.obtenerAlineacion()+"'><p style='";
          						}
	       						// Para el estilo de letra negrita
    							if(c.obtenerWeight().equals("bold")){
    								text = text + "font-weight:" + c.obtenerWeight() + ";";
    							}
	   							// Para el estilo de letra cursiva
	           					if(c.obtenerEstilo().equals("italic")){
    	       						text = text + "font-style:" + c.obtenerEstilo() + ";";
        	   					}
								// Para el estilo de letra underline
           						if(c.obtenerDecoration().equals("underline")){
       	    						text = text + "text-decoration:" + c.obtenerDecoration() + ";";
            					}
								// Para el color de relleno de la celda
        	   					if(c.obtenerColor_Llenado() != " "){
       	    						text = text + "background-color:" + c.obtenerColor_Llenado() + ";";
            					}

           						// Para el tipo de fuente
           						if(c.obtenerFuente().equals(" ")){
       	    						text = text + "font-familiy:times new roman;";
            					}
           						else if(c.obtenerFuente() != "times new roman"){
       	    						text = text + "font-familiy:" + c.obtenerFuente() + ";";
            					}

           						// Para el tamaño de letra
           						if( c.obtenerTam() != " "){
           							text = text + "font-size:" + c.obtenerTam() + "pt;";
           						}

           						if(c.obtenerFormato().equals(" ")){
                        	        text = text + "'>" + cont[dz1++] + "</p></td>";
           						}
           						else{
           							DecimalFormat myFormatter1 = new DecimalFormat(c.obtenerFormato());
                        	        String output1 = myFormatter1.format(cont[dz1++]);

           							text = text + "'>";
           							text = text + output1;
        							text = text + "</p></td>";
       	   					    }
          					}
          					else if( c.obtenerFuncion().equals("menor") ){
          						// Para el color de texto
       							if(c.obtenerColor_Texto() != "black"){
       								text = text + "<td align='"+c.obtenerAlineacion()+"'><p style='color:"+c.obtenerColor_Texto() +";";
       							}
       							else{
      	  							text = text + "<td align='"+c.obtenerAlineacion()+"'><p style='";
          						}
	       						// Para el estilo de letra negrita
    							if(c.obtenerWeight().equals("bold")){
    								text = text + "font-weight:" + c.obtenerWeight() + ";";
    							}
	   							// Para el estilo de letra cursiva
	           					if(c.obtenerEstilo().equals("italic")){
    	       						text = text + "font-style:" + c.obtenerEstilo() + ";";
        	   					}
								// Para el estilo de letra underline
           						if(c.obtenerDecoration().equals("underline")){
       	    						text = text + "text-decoration:" + c.obtenerDecoration() + ";";
            					}
								// Para el color de relleno de la celda
        	   					if(c.obtenerColor_Llenado() != " "){
       	    						text = text + "background-color:" + c.obtenerColor_Llenado() + ";";
            					}

           						// Para el tipo de fuente
           						if(c.obtenerFuente().equals(" ")){
       	    						text = text + "font-familiy:times new roman;";
            					}
           						else if(c.obtenerFuente() != "times new roman"){
       	    						text = text + "font-familiy:" + c.obtenerFuente() + ";";
            					}

           						// Para el tamaño de letra
           						if( c.obtenerTam() != " "){
           							text = text + "font-size:" + c.obtenerTam() + "pt;";
           						}

           						if(c.obtenerFormato().equals(" ")){
                	                text = text + "'>" + cont[dz1++] + "</p></td>";
           						}
        	   					else{
    	   							DecimalFormat myFormatter1 = new DecimalFormat(c.obtenerFormato());
                           		    String output1 = myFormatter1.format(cont[dz1++]);
	       							text = text + "'>";
           							text = text + output1;
        							text = text + "</p></td>";
       	   				 	  	}
          					}
          					else if( c.obtenerFuncion().equals("promedio") ){
          						// Para el color de texto
       							if(c.obtenerColor_Texto() != "black"){
       								text = text + "<td align='"+c.obtenerAlineacion()+"'><p style='color:"+c.obtenerColor_Texto() +";";
       							}
       							else{
      	  							text = text + "<td align='"+c.obtenerAlineacion()+"'><p style='";
          						}
	       						// Para el estilo de letra negrita
    							if(c.obtenerWeight().equals("bold")){
    								text = text + "font-weight:" + c.obtenerWeight() + ";";
    							}
	   							// Para el estilo de letra cursiva
	           					if(c.obtenerEstilo().equals("italic")){
    	       						text = text + "font-style:" + c.obtenerEstilo() + ";";
        	   					}
								// Para el estilo de letra underline
           						if(c.obtenerDecoration().equals("underline")){
       	    						text = text + "text-decoration:" + c.obtenerDecoration() + ";";
            					}
								// Para el color de relleno de la celda
        	   					if(c.obtenerColor_Llenado() != " "){
       	    						text = text + "background-color:" + c.obtenerColor_Llenado() + ";";
            					}

           						// Para el tipo de fuente
           						if(c.obtenerFuente().equals(" ")){
       	    						text = text + "font-familiy:times new roman;";
            					}
           						else if(c.obtenerFuente() != "times new roman"){
       	    						text = text + "font-familiy:" + c.obtenerFuente() + ";";
            					}

           						// Para el tamaño de letra
           						if( c.obtenerTam() != " "){
           							text = text + "font-size:" + c.obtenerTam() + "pt;";
           						}

           						if(c.obtenerFormato().equals(" ")){
                                	text = text + "'>" + cont[dz1++] + "</p></td>";
           						}
           						else{
           							DecimalFormat myFormatter1 = new DecimalFormat(c.obtenerFormato());
                                	String output1 = myFormatter1.format(cont[dz1++]);

           							text = text + "'>";
           							text = text + output1;
        							text = text + "</p></td>";
       	   				    	}
          					}
          				}
          			}
          			text = text + "</tr>";
          		}
          		cerrarBD();
          		text = text + "</table>";
          	}
          	catch( Exception ex ){
          		ex.printStackTrace();
          	}

  			text = text + "</body></html>";
  			out.write(text);
  			out.close();

  		}
  		catch(IOException ioe){
  			System.out.println("el archivo no se puede crear");
  		}
  		return text;
    }

    /************************************************************/
    /*						MODIFICADOAS     					*/
    /************************************************************/

	/*************************************************************/
    /* Funcion:						exportarDocBook 		     */
    /* Proposito:					exportar el reporte a DocBook*/
    /*************************************************************/
	public void exportarDocBook() {
		String text, texto, borderColor, borderStyle, titu, fontF, TColor, FColor, FName, alignm;
		String valor, temp, estilo1, estilo2, estilo3, descripcion;
		int contHsi, contHno, contadorSi, contadorNo, divi, dividendo, ifoot, jfoot, sizeF, j, k, tp, fp;
		double promedio, suma, producto, marginL, marginR, marginT, marginB;
		double maxi, mini, maxBd, minBd, suma2, producto2, promedio2;
		int contDsi, contDno;
		WEPHField c, campo;
		WEPHRow fila;
		Object ci;

		text = null;

		// contDsi se utiliza para hallar el número de veces que aparece el estilo de borde en el details
  		contDsi = 0;

  		// contDno se utiliza para hallar el número de veces que no aparece el estilo de borde en el details
  		contDno = 0;

  		// contadorSi se utiliza para hallar el número de veces que aparece el estilo de borde en el page-footer
  		contadorSi = 0;

  		// contadorNo se utiliza para hallar el número de veces que aparece el estilo de borde en el page-footer
  		contadorNo = 0;

  		// contHsi se utiliza para hallar el número de veces que aparece el estilo de borde en el page-header
  		contHsi = 0;

  		// contHno se utiliza para hallar el número de veces que aparece el estilo de borde en el page-header
  		contHno = 0;

  		// Se obtiene el nombre del archivo
  		FName = op.obtenerArchivo()+".sgml";

  		// Para obtener el título
  		titu = t.obtenerTitleText();

  		// Para obtener la fuente del título
  		fontF = t.obtenerTitleFont();

  		// Para obtener el color del título
  		TColor = t.obtenerTitleTColor();

  		// Para obtener el tamaño de la letra del título
  		sizeF = t.obtenerTitleSize();

  		// Para obtener el color de fondo
  		FColor = t.obtenerTitleFColor();

  		// Para obtener la alineación del texto
  		alignm = t.obtenerTAlignment();


  		// Para obtener el estilo de la letra
  		estilo1 = t.obtenerTitleStyle();
  		estilo2 = t.obtenerTitleWeight();
  		estilo3 = t.obtenerTextDecoration();

  		// Para obtener el margen Izquierdo
  		marginL = m.obtenerLeftM();

  		// Para obtener el margen izquierdo
  		marginR = m.obtenerRightM();

  		// Para obtener el margen superior
  		marginT = m.obtenerTopM();

  		// Para obtener el margen inferior
  		marginB = m.obtenerBottomM();

  		// Para obtener la descripción
  		descripcion = des.obtenerDescripcion();

  		// Para obtener el estilo del borde
  		borderStyle = t.obtenerBorderStyle();

  		// Para obtener el color del borde
  		borderColor = t.obtenerBorderColor();

  		File a;
		a = new File( FName );
		// Se crea un nuevo archivo externo con el nombre asignado en la variable "a"
  		try{
  			a.createNewFile();

  			FileWriter out = new FileWriter(a);
  			text = "<?xml version='1.0' encoding='iso-8859-1'?>";
  			text = text + "<!DOCTYPE article PUBLIC '-//OASIS//DTD DocBook XML V4.1.2//EN'";
  			text = text + " '../reportml/docbookx.dtd'>";

  			// Si lo márgenes son negativos le asignamos un valor por default, en este caso 1.0
  			if ( marginL < 0 ){
  				marginL = 4.0;
  			}
  			if ( marginR < 0 ){
  				marginR = 3.0;
  			}
  			if ( marginT < 0 ){
  				marginT = 4.0;
  			}
  			if ( marginB < 0 ){
  				marginB = 3.0;
  			}

  			// titulo
  			text = text + "<webreport>";
  			text = text + "<title>" + titu + "</title>";

  			text = text + "<table>";
            text = text + "<tgroup>";
            text = text + "<thead>";
            text = text + "<row>";


        	// *** Recorremos el page-header ***
        	for ( tp = 0 ; tp <= ph.obtenerTamaño() - 1; tp ++ ){
        		fila = ph.obtenerRow(tp);
        	    for ( fp = 0; fp <= fila.obtenerTamaño()-1; fp ++ ){
        			c = fila.obtenerCampo(fp);
        			// Si el tipo de campo es Text, se imprime el contenido con sus respectivas acciones
        			if( c.obtenerTipo().equals("texto") ){
        				text = text + "<entry>" + c.obtenerContenido() + "</entry>";
          			}
          		}
          	}// *** Final del recorrido del page-header ***

          	text = text + "</row>";
            text = text + "</thead>";

            text = text + "<tbody>";

            ResultSet rs = conectarBD();
            ResultSetMetaData rsmd;
            try{
            	if( rs != null){
            		while( rs.next() ){
            			rsmd = rs.getMetaData();
            			Hashtable dict = new Hashtable();
                        try {
                            int cant = rsmd.getColumnCount();
                            for ( int i = 1; i <= cant; i++ ) {
                                String nom = rsmd.getColumnName(i);
                                String data = rs.getString(i);
                                dict.put( nom, data );
                            }
                        }
                        catch ( Exception ex ) {
                            ex.printStackTrace();
                            System.exit( 0 );
                        }

            			if( ( d != null ) ){
	            			// *** Recorremos el contenido de la base de datos a mostrar en el reporte ***
	    					// Realizamos la conexión a la base de datos correspondiente
	    					for( j = 0; j <= d.obtenerTamaño()-1; j ++ ){
	    						text = text + "<row>";
            					fila = d.obtenerFila(j);
	    						for( k = 0; k <= fila.obtenerTamaño()-1; k ++ ){
	    							c = fila.obtenerCampo(k);
	    							if( c.obtenerTipo().equals("texto") ){
	    								text = text + "<entry>" + c.obtenerContenido() + "</entry>";
	           						}
       							    else if( c.obtenerTipo().equals("bd") ){
       									if(c.obtenerFormato().equals(" ")){
                                            text = text + "<entry>" + dict.get(c.obtenerNombreBD()) + "</entry>";
           								}
           								else{
                                            DecimalFormat myFormatter = new DecimalFormat(c.obtenerFormato());
                                            String output = myFormatter.format(Double.parseDouble((String)dict.get(c.obtenerNombreBD())));

           									text = text + "<entry>";
           									text = text + output;
        								    text = text + "</entry>";
       	   								}
       	   							}
       	   							else if( c.obtenerTipo().equals("formula") ){
       	   								String formula = c.obtenerContenido();
										Context cx = Context.enter();
										cx.setLanguageVersion(Context.VERSION_1_2);
										Scriptable scope = cx.initStandardObjects( null );
										Object result = null;
										try {
											Scriptable query = Context.toObject(dict, scope);
											scope.put("query", scope, query);
											result = cx.evaluateString(scope, formula, c.obtenerContenido(), 1, null);
										}
										catch (JavaScriptException jse) {
											jse.printStackTrace();
											System.exit( 0 );
										}
       	   								cx.exit();

           								if(c.obtenerFormato().equals(" ")){

                                            text = text + "<entry>" + cx.toString( result ) + "</entry>";
           								}
           								else{
           									DecimalFormat myFormatter = new DecimalFormat(c.obtenerFormato());
                                            String output = myFormatter.format(cx.toString( result ));

           									text = text + "<entry>";
           									text = text + output;
        								    text = text + "</entry>";
       	   								}
       	   							}
       	   						}
       	   						text = text + "</row>";
       	   					}
       	   				}
       	   			}
       	   		}
       	   		cerrarBD();
       	   		text = text + "</tbody>";
       	   		text = text + "</tgroup>";
    			text = text + "</table>";
       	   	}
       	   	catch( Exception ex ){
       	   		ex.printStackTrace();
       	   	}

       	   	// de aqui para abajo es otra tabla

       	   	text = text + "<table>";
       	   	text = text + "<tgroup>";
       	   	text = text + "<thead></thead>";
       	   	text = text + "<tbody>";

          	double[] cont = new double[20];
          	int c1 = 0;

          	for( int w = 0; w <= pf.obtenerTamaño() - 1; ++ w ){
          		fila = pf.obtenerRow(w);
          		for( int z = 0; z <= fila.obtenerTamaño() - 1; ++ z ){
          			c = fila.obtenerCampo(z);
          			if( c.obtenerTipo().equals("total") || c.obtenerTipo().equals("texto") ){
          				if( c.obtenerFuncion().equals("suma") ){
          					cont[c1++] = 0;
          				}
          				else if( c.obtenerFuncion().equals("contar") ){
          					cont[c1++] = 0;
          				}
          				else if( c.obtenerFuncion().equals("mult") ){
          					cont[c1++] = 1;
          				}
          				else if( c.obtenerFuncion().equals("mayor") ){
          					maxi = Double.MIN_VALUE;
          				}
          				else if( c.obtenerFuncion().equals("menor") ){
          					mini = Double.MAX_VALUE;
          				}
          				else if( c.obtenerFuncion().equals("promedio") ){
          					cont[c1++] = 0;
          				}
          			}
          		}
          	}

          	ResultSet rs1 = reconectarConectarBD();
          	int n = 0;
          	double s10 = 0;
          	try{
          		if( rs1 != null ){
          			while( rs1.next() ){
          				n ++;
          				int x = 0;
          				for( int w1 = 0; w1 <= pf.obtenerTamaño() - 1; ++ w1 ){
          					fila = pf.obtenerRow(w1);
          					for( int z1 = 0; z1 <= fila.obtenerTamaño() - 1; ++ z1 ){
          						c = fila.obtenerCampo(z1);
          						if( c.obtenerTipo().equals("total") || c.obtenerTipo().equals("texto") ){
          							if( c.obtenerFuncion().equals("suma") ){
          								cont[x] += rs1.getDouble(c.obtenerNombreBD());
          								x ++;
          							}
          							else if( c.obtenerFuncion().equals("contar") ){
          								cont[x] += rs1.getDouble(c.obtenerNombreBD());
          								x ++;
          							}
          							else if( c.obtenerFuncion().equals("mult") ){
          								cont[x] *= rs1.getDouble(c.obtenerNombreBD());
          								x ++;
          							}
          							else if( c.obtenerFuncion().equals("mayor") ){
          								double a2 = rs1.getDouble(c.obtenerNombreBD());
          								if( a2 > cont[x] ){
          									cont[x] = a2;
          								}
          								x ++;
          							}
          							else if( c.obtenerFuncion().equals("menor") ){
          								double b = rs1.getDouble(c.obtenerNombreBD());
          								if( b < cont[x] ){
          									cont[x] = b;
          								}
          								x ++;
          							}
          							else if( c.obtenerFuncion().equals("promedio") ){
          								cont[x] += rs1.getDouble(c.obtenerNombreBD()) / n;
          								x ++;
          							}
          						}
          					}
          				}
          			}
          		}
                int dz1= 0;
        	  	for( int w2 = 0; w2 <= pf.obtenerTamaño() - 1; ++ w2 ){
        	  		fila = pf.obtenerRow(w2);
        	  		for( int z2 = 0; z2 <= fila.obtenerTamaño() - 1; ++ z2 ){
        	  			text = text + "<row>";
          				c = fila.obtenerCampo(z2);
          				if( c.obtenerTipo().equals("texto") ){
          					text = text + "<entry>" + c.obtenerContenido() + "</entry>";
          				}
       		   			else if( c.obtenerTipo().equals("total") ){
   		       				if( c.obtenerFuncion().equals("suma") ){
	          					if(c.obtenerFormato().equals(" ")){
	                                text = text + "<entry>" + cont[dz1++] + "</entry>";
	           					}
	           					else{
	           						DecimalFormat myFormatter1 = new DecimalFormat(c.obtenerFormato());
	                                String output1 = myFormatter1.format(cont[dz1++]);

        	   						text = text + "<entry>";
        	   						text = text + output1;
        							text = text + "</entry>";
    	   	   				    }
    	      				}
    	      				else if( c.obtenerFuncion().equals("contar") ){
    	      					if(c.obtenerFormato().equals(" ")){
	                                text = text + "<entry>" + cont[dz1++] + "</entry>";
	           					}
	           					else{
	           						DecimalFormat myFormatter1 = new DecimalFormat(c.obtenerFormato());
	                                String output1 = myFormatter1.format(cont[dz1++]);

        	   						text = text + "<entry>";
        	   						text = text + output1;
        							text = text + "</entry>";
    	   	   				    }
    	      				}
    	      				else if( c.obtenerFuncion().equals("mult") ){
    	      					if(c.obtenerFormato().equals(" ")){
	                                text = text + "<entry>" + cont[dz1++] + "</entry>";
	           					}
	           					else{
	           						DecimalFormat myFormatter1 = new DecimalFormat(c.obtenerFormato());
	                                String output1 = myFormatter1.format(cont[dz1++]);

        	   						text = text + "<entry>";
        	   						text = text + output1;
        							text = text + "</entry>";
    	   	   				    }
          					}
          					else if( c.obtenerFuncion().equals("mayor") ){
          						if(c.obtenerFormato().equals(" ")){
	                                text = text + "<entry>" + cont[dz1++] + "</entry>";
	           					}
	           					else{
	           						DecimalFormat myFormatter1 = new DecimalFormat(c.obtenerFormato());
	                                String output1 = myFormatter1.format(cont[dz1++]);

        	   						text = text + "<entry>";
        	   						text = text + output1;
        							text = text + "</entry>";
    	   	   				    }
          					}
          					else if( c.obtenerFuncion().equals("menor") ){
          						if(c.obtenerFormato().equals(" ")){
	                                text = text + "<entry>" + cont[dz1++] + "</entry>";
	           					}
	           					else{
	           						DecimalFormat myFormatter1 = new DecimalFormat(c.obtenerFormato());
	                                String output1 = myFormatter1.format(cont[dz1++]);

        	   						text = text + "<entry>";
        	   						text = text + output1;
        							text = text + "</entry>";
    	   	   				    }
          					}
          					else if( c.obtenerFuncion().equals("promedio") ){
          						if(c.obtenerFormato().equals(" ")){
	                                text = text + "<entry>" + cont[dz1++] + "</entry>";
	           					}
	           					else{
	           						DecimalFormat myFormatter1 = new DecimalFormat(c.obtenerFormato());
	                                String output1 = myFormatter1.format(cont[dz1++]);

        	   						text = text + "<entry>";
        	   						text = text + output1;
        							text = text + "</entry>";
    	   	   				    }
          					}
          				}
          				text = text + "</row>";
          			}
          		}
          		cerrarBD();
          		text = text + "</tbody>";
          		text = text + "</tgroup>";
          		text = text + "</table>";
          	}
          	catch( Exception ex ){
          		ex.printStackTrace();
          	}

  			text = text + "</webreport>";
  			out.write(text);
  			out.close();

  		}
  		catch(IOException ioe){
  			System.out.println("el archivo no se puede crear");
  		}
  	}

  	/*************************************************************/
    /* Funcion:						exportarPDF     		     */
    /* Proposito:					exportar el reporte a PDF    */
    /*************************************************************/
	public void exportarPDF(){
  		String texto, borderColor, borderStyle, titu, fontF, TColor, FColor, FName, alignm;
		String valor, temp, estilo1, estilo2, estilo3, descripcion;
		int contHsi, contHno, contadorSi, contadorNo, divi, dividendo, ifoot, jfoot, sizeF, j, k, tp, fp;
		double promedio, suma, producto, marginL, marginR, marginT, marginB;
		double maxi, mini, maxBd, minBd, suma2, producto2, promedio2;
		int contDsi, contDno;
		WEPHField c, campo;
		WEPHRow fila;
		Object ci;

		FName = op.obtenerArchivo()+".pdf";

		// paso 1:
        com.lowagie.text.Document document = new com.lowagie.text.Document(com.lowagie.text.PageSize.LETTER, 50, 50, 50, 50);
        try {

            // contDsi se utiliza para hallar el número de veces que aparece el estilo de borde en el details
  			contDsi = 0;

  			// contDno se utiliza para hallar el número de veces que no aparece el estilo de borde en el details
  			contDno = 0;

  			// contadorSi se utiliza para hallar el número de veces que aparece el estilo de borde en el page-footer
  			contadorSi = 0;

  			// contadorNo se utiliza para hallar el número de veces que aparece el estilo de borde en el page-footer
  			contadorNo = 0;

  			// contHsi se utiliza para hallar el número de veces que aparece el estilo de borde en el page-header
  			contHsi = 0;

  			// contHno se utiliza para hallar el número de veces que aparece el estilo de borde en el page-header
  			contHno = 0;


  			// Para obtener el título
  			titu = t.obtenerTitleText();

  			// Para obtener la fuente del título
  			fontF = t.obtenerTitleFont();

  			// Para obtener el color del título
  			TColor = t.obtenerTitleTColor();

  			// Para obtener el tamaño de la letra del título
  			sizeF = t.obtenerTitleSize();

  			// Para obtener el color de fondo
  			FColor = t.obtenerTitleFColor();

  			// Para obtener la alineación del texto
  			alignm = t.obtenerTAlignment();


  			// Para obtener el estilo de la letra
  			estilo1 = t.obtenerTitleStyle();
  			estilo2 = t.obtenerTitleWeight();
  			estilo3 = t.obtenerTextDecoration();

  			// Para obtener el margen Izquierdo
  			marginL = m.obtenerLeftM();

  			// Para obtener el margen izquierdo
  			marginR = m.obtenerRightM();

  			// Para obtener el margen superior
  			marginT = m.obtenerTopM();

  			// Para obtener el margen inferior
  			marginB = m.obtenerBottomM();

  			// Para obtener la descripción
  			descripcion = des.obtenerDescripcion();

  			// Para obtener el estilo del borde
  			borderStyle = t.obtenerBorderStyle();

  			// Para obtener el color del borde
  			borderColor = t.obtenerBorderColor();

  			// Si lo márgenes son negativos le asignamos un valor por default, en este caso 1.0
  			if ( marginL < 0 ){
  				marginL = 4.0;
  			}
  			if ( marginR < 0 ){
  				marginR = 3.0;
  			}
  			if ( marginT < 0 ){
  				marginT = 4.0;
  			}
  			if ( marginB < 0 ){
  				marginB = 3.0;
  			}

  			// titulo
  			// paso 2:
            PdfWriter.getInstance(document, new FileOutputStream(FName));
            // paso 3:
            document.open();
            // paso 4:
            PdfPTable table = new PdfPTable(1);
            if( alignm.equals("center") ){
            	table.getDefaultCell().setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
            	table.addCell(titu);
            }
            document.add(table);
            PdfPTable table2 = null;
            // *** Recorremos el page-header ***
        	for ( tp = 0 ; tp <= ph.obtenerTamaño() - 1; tp ++ ){
        		fila = ph.obtenerRow(tp);
        		if ( table2 == null ){
                    table2 = new PdfPTable(fila.obtenerTamaño());
        		}

        		for ( fp = 0; fp <= fila.obtenerTamaño()-1; fp ++ ){
        			c = fila.obtenerCampo(fp);
        			// Si el tipo de campo es Text, se imprime el contenido con sus respectivas acciones
        			if( c.obtenerTipo().equals("texto") ){
        				table2.getDefaultCell().setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        				table2.addCell(c.obtenerContenido());
        			}
                    document.add(table2);
          		}
          	}// *** Final del recorrido del page-header ***

            ResultSet rs = conectarBD();
            ResultSetMetaData rsmd;
            try{
            	if( rs != null){
            		while( rs.next() ){
            			rsmd = rs.getMetaData();
            			Hashtable dict = new Hashtable();
                        try {
                            int cant = rsmd.getColumnCount();
                            for ( int i = 1; i <= cant; i++ ) {
                                String nom = rsmd.getColumnName(i);
                                String data = rs.getString(i);
                                dict.put( nom, data );
                            }
                        }
                        catch ( Exception ex ) {
                            ex.printStackTrace();
                            System.exit( 0 );
                        }

            			if( ( d != null ) ){
	            			// *** Recorremos el contenido de la base de datos a mostrar en el reporte ***
	    					// Realizamos la conexión a la base de datos correspondiente
	    					for( j = 0; j <= d.obtenerTamaño()-1; j ++ ){
	    						fila = d.obtenerFila(j);
	    						if ( table2 != null ){
                                    table2 = new PdfPTable(fila.obtenerTamaño());
	    						}
	    						for( k = 0; k <= fila.obtenerTamaño()-1; k ++ ){
	    							c = fila.obtenerCampo(k);
	    						    if( c.obtenerTipo().equals("texto") ){
	    								table2.getDefaultCell().setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        								table2.addCell(c.obtenerContenido());
        							}
        							else if( c.obtenerTipo().equals("bd") ){
       									if(c.obtenerFormato().equals(" ")){
       										table2.getDefaultCell().setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        									table2.addCell((String)dict.get(c.obtenerNombreBD()));
        								}
           								else{
           									DecimalFormat myFormatter = new DecimalFormat(c.obtenerFormato());
                                            String output = myFormatter.format(Double.parseDouble((String)dict.get(c.obtenerNombreBD())));
											table2.getDefaultCell().setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        									table2.addCell(output);
        								}
       	   							}
       	   							else if( c.obtenerTipo().equals("formula") ){
       	   								String formula = c.obtenerContenido();
										Context cx = Context.enter();
										cx.setLanguageVersion(Context.VERSION_1_2);
										Scriptable scope = cx.initStandardObjects( null );
										Object result = null;
										try {
											Scriptable query = Context.toObject(dict, scope);
											scope.put("query", scope, query);
											result = cx.evaluateString(scope, formula, c.obtenerContenido(), 1, null);
										}
										catch (JavaScriptException jse) {
											jse.printStackTrace();
											System.exit( 0 );
										}
       	   								cx.exit();


           								if(c.obtenerFormato().equals(" ")){
           									table2.getDefaultCell().setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        									table2.addCell(cx.toString( result ));
        								}
           								else{
           									DecimalFormat myFormatter = new DecimalFormat(c.obtenerFormato());
                                            String output = myFormatter.format(cx.toString( result ));
											table2.getDefaultCell().setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
											table2.addCell(output);
										}
       	   							}
       	   						}
       	   					}
       	   					document.add( table2 );
                        }
       	   			}
       	   		}
       	   		cerrarBD();
       	   	}
       	   	catch( Exception ex ){
       	   		ex.printStackTrace();
       	   	}

       	   	// de aqui para abajo es otra tabla

       	   	double[] cont = new double[20];
          	int c1 = 0;

          	for( int w = 0; w <= pf.obtenerTamaño() - 1; ++ w ){
          		fila = pf.obtenerRow(w);
          		for( int z = 0; z <= fila.obtenerTamaño() - 1; ++ z ){
          			c = fila.obtenerCampo(z);
          			if( c.obtenerTipo().equals("total") || c.obtenerTipo().equals("texto") ){
          				if( c.obtenerFuncion().equals("suma") ){
          					cont[c1++] = 0;
          				}
          				else if( c.obtenerFuncion().equals("contar") ){
          					cont[c1++] = 0;
          				}
          				else if( c.obtenerFuncion().equals("mult") ){
          					cont[c1++] = 1;
          				}
          				else if( c.obtenerFuncion().equals("mayor") ){
          					maxi = Double.MIN_VALUE;
          				}
          				else if( c.obtenerFuncion().equals("menor") ){
          					mini = Double.MAX_VALUE;
          				}
          				else if( c.obtenerFuncion().equals("promedio") ){
          					cont[c1++] = 0;
          				}
          			}
          		}
          	}

          	ResultSet rs1 = reconectarConectarBD();
          	int n = 0;
          	double s10 = 0;
          	try{
          		if( rs1 != null ){
          			while( rs1.next() ){
          				n ++;
          				int x = 0;
          				for( int w1 = 0; w1 <= pf.obtenerTamaño() - 1; ++ w1 ){
          					fila = pf.obtenerRow(w1);
          					for( int z1 = 0; z1 <= fila.obtenerTamaño() - 1; ++ z1 ){
          						c = fila.obtenerCampo(z1);
          						if( c.obtenerTipo().equals("total") || c.obtenerTipo().equals("texto") ){
          							if( c.obtenerFuncion().equals("suma") ){
          								cont[x] += rs1.getDouble(c.obtenerNombreBD());
          								x ++;
          							}
          							else if( c.obtenerFuncion().equals("contar") ){
          								cont[x] += rs1.getDouble(c.obtenerNombreBD());
          								x ++;
          							}
          							else if( c.obtenerFuncion().equals("mult") ){
          								cont[x] *= rs1.getDouble(c.obtenerNombreBD());
          								x ++;
          							}
          							else if( c.obtenerFuncion().equals("mayor") ){
          								double a2 = rs1.getDouble(c.obtenerNombreBD());
          								if( a2 > cont[x] ){
          									cont[x] = a2;
          								}
          								x ++;
          							}
          							else if( c.obtenerFuncion().equals("menor") ){
          								double b = rs1.getDouble(c.obtenerNombreBD());
          								if( b < cont[x] ){
          									cont[x] = b;
          								}
          								x ++;
          							}
          							else if( c.obtenerFuncion().equals("promedio") ){
          								cont[x] += rs1.getDouble(c.obtenerNombreBD()) / n;
          								x ++;
          							}
          						}
          					}
          				}
          			}
          		}
                int dz1= 0;
        	  	for( int w2 = 0; w2 <= pf.obtenerTamaño() - 1; ++ w2 ){
        	  		fila = pf.obtenerRow(w2);
                    if ( table2 != null ){
                        table2 = new PdfPTable(fila.obtenerTamaño());
                    }

        	  		for( int z2 = 0; z2 <= fila.obtenerTamaño() - 1; ++ z2 ){
        	  			c = fila.obtenerCampo(z2);
        	  			if( c.obtenerTipo().equals("texto") ){
        					table2.getDefaultCell().setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        					table2.addCell(c.obtenerContenido());
        				}
        				else if( c.obtenerTipo().equals("total") ){
   		       				if( c.obtenerFuncion().equals("suma") ){
	          					if(c.obtenerFormato().equals(" ")){
	                                table2.getDefaultCell().setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        							table2.addCell(Double.toString(cont[dz1++]));
        						}
	           					else{
	           						DecimalFormat myFormatter1 = new DecimalFormat(c.obtenerFormato());
	                                String output1 = myFormatter1.format(cont[dz1++]);
        	   						table2.getDefaultCell().setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        							table2.addCell(output1);
        						}
    	      				}
    	      				else if( c.obtenerFuncion().equals("contar") ){
    	      					if(c.obtenerFormato().equals(" ")){
	                                table2.getDefaultCell().setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        							table2.addCell(Double.toString(cont[dz1++]));
        						}
	           					else{
	           						DecimalFormat myFormatter1 = new DecimalFormat(c.obtenerFormato());
	                                String output1 = myFormatter1.format(cont[dz1++]);
									table2.getDefaultCell().setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        							table2.addCell(output1);
        						}
    	      				}
    	      				else if( c.obtenerFuncion().equals("mult") ){
    	      					if(c.obtenerFormato().equals(" ")){
	                                table2.getDefaultCell().setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        							table2.addCell(Double.toString(cont[dz1++]));
        						}
	           					else{
	           						DecimalFormat myFormatter1 = new DecimalFormat(c.obtenerFormato());
	                                String output1 = myFormatter1.format(cont[dz1++]);
									table2.getDefaultCell().setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        							table2.addCell(output1);
        						}
          					}
          					else if( c.obtenerFuncion().equals("mayor") ){
          						if(c.obtenerFormato().equals(" ")){
	                                table2.getDefaultCell().setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        							table2.addCell(Double.toString(cont[dz1++]));
        						}
	           					else{
	           						DecimalFormat myFormatter1 = new DecimalFormat(c.obtenerFormato());
	                                String output1 = myFormatter1.format(cont[dz1++]);
									table2.getDefaultCell().setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        							table2.addCell(output1);
        						}
          					}
          					else if( c.obtenerFuncion().equals("menor") ){
          						if(c.obtenerFormato().equals(" ")){
	                                table2.getDefaultCell().setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        							table2.addCell(Double.toString(cont[dz1++]));
        						}
	           					else{
	           						DecimalFormat myFormatter1 = new DecimalFormat(c.obtenerFormato());
	                                String output1 = myFormatter1.format(cont[dz1++]);
									table2.getDefaultCell().setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        							table2.addCell(output1);
        						}
          					}
          					else if( c.obtenerFuncion().equals("promedio") ){
          						if(c.obtenerFormato().equals(" ")){
	                                table2.getDefaultCell().setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        							table2.addCell(Double.toString(cont[dz1++]));
        						}
	           					else{
	           						DecimalFormat myFormatter1 = new DecimalFormat(c.obtenerFormato());
	                                String output1 = myFormatter1.format(cont[dz1++]);
									table2.getDefaultCell().setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
        							table2.addCell(output1);
        						}
          					}
          				}
          			}
                    document.add( table2 );
          		}
          		cerrarBD();
          	}
          	catch( Exception ex ){
          		ex.printStackTrace();
          	}
		}

        catch (Exception de) {
            de.printStackTrace();
        }
        // paso 5:
        document.close();
  	}

    /*************************************************************/
    /* Funcion:						cerrarBD        		     */
    /* Proposito:					cerrar una conexion a la BD  */
    /*************************************************************/
	public void cerrarBD(){
		try{
			conn.close();
		}
		catch( SQLException sqlEx ){
			System.out.println("Error al cerrar la base de datos");
		}
	}
}