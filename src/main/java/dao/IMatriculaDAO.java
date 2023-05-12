/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import modelos.Alumnos;

/**
 *
 * @author Felix Gar_Me
 */
public interface IMatriculaDAO {
    public List<Alumnos> buscarAlumnos(Alumno alumno); 
}
