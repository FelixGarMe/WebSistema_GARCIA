/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores;

import dao.*;
import modelos.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "controladorPrincipal", urlPatterns = {"/controladorPrincipal"})
public class controladorPrincipal extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("usuario") == null) {
               response.sendRedirect("login_error.jsp");
              return;
           }
            cBaseDatos objDatos = new cBaseDatos();
            String accion = request.getParameter("accion");
            if (accion == null) {
                accion = "bienvenida";
            }
            if (accion.equals("bienvenida")) {
                request.getRequestDispatcher("/contenido.html").forward(request, response);
            } else if (accion.equals("listadoAreas")) {
                Vector arrAreas = (Vector) objDatos.getAreas();
                request.setAttribute("arrAreas", arrAreas);
                request.getRequestDispatcher("/mantenimientos/listadoAreas.jsp").forward(
                        request, response);
            } else if (accion.equals("NuevoEliminarArea")) {
                if (request.getParameter("boton").compareTo("Nuevo Registro") == 0) {
                    Vector registro = new Vector();
                    registro.add("");
                    registro.add("");
                    registro.add("");
                    registro.add("");
                    request.setAttribute("registro", registro);
                    request.setAttribute("operacion", "INSERT");
                    request.setAttribute("titulo", "Nueva Area");
                    request.getRequestDispatcher("/mantenimientos/editarAreas.jsp").forward(
                            request, response);
                }else {
                        String[] datos = request.getParameterValues("xcod");
                        objDatos.eliminarAreas(datos);
                        request.getRequestDispatcher("/controladorPrincipal?accion=listadoAreas").forward(
                        request,response );
                    }

            } else if (accion.compareTo("GRABAR_AREA") == 0) {
                if (request.getParameter("boton").compareTo("GRABAR") == 0) {
                    String operacion = request.getParameter("operacion");
                    if (operacion.equals("INSERT")) {
                        String[] datos = new String[3];
                        datos[0] = request.getParameter("xnom");
                        datos[1] = request.getParameter("xabr");
                        datos[2] = request.getParameter("xest");
                        objDatos.grabarNuevaArea(datos);
                    }else {
                        String[] datos = new String[4];
                        datos[0] = request.getParameter("xcod");
                        datos[1] = request.getParameter("xnom");
                        datos[2] = request.getParameter("xabr");
                        datos[3] = request.getParameter("xest");
                        objDatos.modificarArea(datos);
                    }

                    
                }
                request.getRequestDispatcher("/controladorPrincipal?accion=listadoAreas").forward(
                        request, response);
            }else if ( accion.compareTo( "modificarArea" ) == 0 ) {
                String xcod = request.getParameter( "xcod" );
                Vector registro = objDatos.buscarArea(xcod);

                request.setAttribute( "registro", registro );
                request.setAttribute( "operacion","UPDATE");
                request.setAttribute( "titulo","Modificar Area");
                request.getRequestDispatcher( "/mantenimientos/editarAreas.jsp" ).forward( 
             request,response );
            }else if (accion.equals("listadoAlumnos")){
                List<Alumnos> arrAlumnos = new ArrayList<Alumnos>();
                IAlumnosDAO dao = new AlumnoDAOImpl();
                arrAlumnos = dao.obtener();
                request.setAttribute("arrAlumnos", arrAlumnos);
                request.getRequestDispatcher("/mantenimientos/listadoAlumnos.jsp").forward(request, response);
            }else if (accion.equals("NuevoEliminarAlumno")){
                if(request.getParameter("boton").compareTo("Nuevo Registro") == 0 ){
                    Alumnos alumno = new Alumnos();
                    request.setAttribute("alumno", alumno);
                    request.setAttribute("operacion", "INSERT");
                    request.setAttribute("titulo", "nuevo Alumno");
                    request.getRequestDispatcher("/mantenimientos/editarAlumnos.jsp").forward(request, response);
                }
                    
            }else if (accion.compareTo("GRABAR_ALUMNO")==0){
                if(request.getParameter("boton").compareTo("GRABAR") == 0){
                    String operacion = request.getParameter("operacion");
                    String strDate = request.getParameter("xfec");
                    Date xfec = Date.valueOf(strDate);
                    Alumnos alumno = new Alumnos();
                    alumno.setCodigo(Integer.parseInt(request.getParameter("xcod")));
                    alumno.setNombre(request.getParameter("xnom"));
                    alumno.setDireccion(request.getParameter("xdir"));
                    alumno.setEmail(request.getParameter("xema"));
                    alumno.setTelefono(request.getParameter("xtel"));
                    alumno.setCelular(request.getParameter("xcel"));
                    alumno.setSexo(request.getParameter("xsex"));
                    alumno.setFec_nac(xfec);
                    alumno.setEstado(request.getParameter("xest"));
                    if(operacion.equals("INSERT")){
                        IAlumnosDAO dao = new AlumnoDAOImpl();
                        dao.registrar(alumno);
                    } else{
                        IAlumnosDAO dao = new AlumnoDAOImpl();
                        dao.actualizar(alumno);
                    }
                }
                request.getRequestDispatcher("/controladorPrincipal?accion=listadoAlumnos").forward(
                        request, response);
            }else if (accion.compareTo("modificarAlumno") == 0){
                String xcod = request.getParameter("xcod").trim();
                IAlumnosDAO dao = new AlumnoDAOImpl();
                Alumnos alumno = dao.buscar(Integer.parseInt(xcod));
                request.setAttribute("alumno", alumno);
                request.setAttribute("operacion", "UPDATE");
                request.setAttribute("titulo", "Modificar Alumno");
                request.getRequestDispatcher("/mantenimientos/editarAlumnos.jsp").forward(request, response);               
            }else if (accion.compareTo("matriculaMostrarAlumnos")== 0) {
                if (request.getParameter("modo").compareTo("Lista")==0) {
                    List<Alumnos> arrAlumnos = new ArrayList<Alumnos>();
                    Alumnos alumno = new Alumnos();
                    alumno.setNombre(" ");
                    IMatriculaDAO dao = new MatriculaDAOImpl();
                    arrAlumnos = dao.buscarAlumnos(alumno);
                    request.setAttribute("arrAlumnos", arrAlumnos);
                    request.getRequestDispatcher("/operaciones/matriculaMostrarAlumnos.jsp").forward(request, response);
                
                } else if (request.getParameter("boton").equals("Buscar")) {
                    List<Alumnos> arrAlumnos = new ArrayList<Alumnos>();
                    Alumnos alumno = new Alumnos();
                    alumno.setNombre(request.getParameter("xalu"));
                    IMatriculaDAO dao = new MatriculaDAOImpl();
                    arrAlumnos = dao.buscarAlumnos(alumno);
                    request.setAttribute("arrAlumnos", arrAlumnos);
                    request.setAttribute("nombre", alumno.getNombre());
                    request.getRequestDispatcher("/operaciones/matriculaMostrarAlumnos.jsp").forward(request, response);
                }
            }
            
            
            
            
            
            
            else {
                out.println("Accion: (" + accion + ") no reconocida...");
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            out.close();
        }

    }



    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
