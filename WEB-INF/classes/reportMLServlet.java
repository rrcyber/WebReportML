/********************************************************************************************/
/*  Proyecto:						Web Report-ML    										*/
/*  Autor:							Ricardo Moises Rosero Bustamante						*/
/*	Descripcion:					Clase reportMLServlet									*/
/********************************************************************************************/
package webreport.reportml;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.*;
import java.util.*;

public class reportMLServlet extends HttpServlet {

	// funciones

    /************************************************************/
    /*						MODIFICADOAS     					*/
    /************************************************************/

	/*************************************************************/
    /* Funcion:						doGet   				     */
    /* Proposito:					atender las peticiones GET   */
    /*************************************************************/
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType( "text/html" );

        // out: sera utilizada para escribir en nuestro servlet
        PrintWriter out = response.getWriter();

        // ruta: obtiene el directorio en el cual se encuentran
        // nuestros reportes
        String ruta = this.getInitParameter( "directorio-reportes" );

        // archivo: obtiene el nombre del archivo o de nuestro reporte escrito en
        // la barra de direcciones del browser
        String archivo = request.getServletPath();

        // copyArchivo: realiza una copia del nombre del archivo a partir de
        // la posicion 1, puesto que en archivo se guarda el nombre con un " / "
        // en la posicion 0
        String copyArchivo = archivo.substring( 1, archivo.length() );

        // cargamos nuestro reporte
        WebReport wr = new WebReport( ruta + copyArchivo );

		if(wr.obtenerSalida().equals("html")){
		    String s = wr.exportarHTML();
		    out.println(s);
        }
		else if(wr.obtenerSalida().equals("docbook")){
			wr.exportarDocBook();
			out.println("<html>");
			out.println("<head><title>");
			out.println("Web Report-ML PDF</title></head>");
			out.println("<body>");
			out.println("<center><h1>El reporte se ha generado con exito en DocBook!!</h1></center>");
			out.println("</body>");
			out.println("</html>");
		}
		else if(wr.obtenerSalida().equals("pdf")){
			wr.exportarPDF();
			out.println("<html>");
			out.println("<head><title>");
			out.println("Web Report-ML PDF</title></head>");
			out.println("<body>");
			out.println("<center><h1>El reporte se ha generado con exito en PDF!!</h1></center>");
			out.println("</body>");
			out.println("</html>");
		}

    }

    /*************************************************************/
    /* Funcion:						doPost   				     */
    /* Proposito:					atender las peticiones POST  */
    /*************************************************************/
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	doGet(request, response);
    }
}