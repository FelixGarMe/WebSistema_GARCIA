/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelos.Alumnos;
import modelos.Conexion;

/**
 *
 * @author Felix Gar_Me
 */
public class MatriculaDAOImpl implements IMatriculaDAO{

    @Override
    public List<Alumnos> buscarAlumnos(Alumnos alumno) {
        Connection co =null;
        Statement stm= null;
        ResultSet rs=null;
        String sql="SELECT * FROM alumnos WHERE nombre LIKE'%" + alumno.getNombre() + "%'";
        List<Alumnos> listaAlumnos= new ArrayList<Alumnos>();

        try {			
                Conexion con = new Conexion();
                co=con.Conectar();
                stm=co.createStatement();
                rs=stm.executeQuery(sql);
                while (rs.next()) {
                        Alumnos nalumno=new Alumnos();
                        nalumno.setCodigo(rs.getInt(1));
                        nalumno.setNombre(rs.getString(2));
                        nalumno.setDireccion(rs.getString(3));
                        nalumno.setEmail(rs.getString(4));
                        nalumno.setTelefono(rs.getString(5));
                        nalumno.setCelular(rs.getString(6));
                        nalumno.setSexo(rs.getString(7));
                        nalumno.setFec_nac(rs.getDate(8));
                        nalumno.setEstado(rs.getString(9));
                        listaAlumnos.add(nalumno);
                }
                stm.close();
                rs.close();
                co.close();
        } catch (SQLException e) {
                System.out.println("Error:Clase MatriculaDaoImpl,"
                        + "método buscarAlumnos");
        }
        return listaAlumnos;
    }
}
