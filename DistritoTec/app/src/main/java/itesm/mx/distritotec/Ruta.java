package itesm.mx.distritotec;

/**
 * Created by ricardo on 28/03/15.
 */

/*This file is part of DistritoTec.

        DistritoTec is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        DistritoTec is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with DistritoTec.  If not, see <http://www.gnu.org/licenses/>.*/
public class Ruta {
    private String tipo;    //expreso o circuito
    private String nombre;  //nombre de ruta
    private String inicio;  //donde empieza
    private String horario;  //horario que maneja

    public Ruta(String tipo, String nombre, String inicio, String horario){
        this.tipo = tipo;
        this.nombre = nombre;
        this.inicio = inicio;
        this.horario = horario;

    }
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setInicio(String inicio){
        this.inicio = inicio;
    }

    public void setHorario(String horario){
        this.horario = horario;
    }

    public String getTipo(){return this.tipo;}
    public String getNombre(){return this.nombre;}
    public String getInicio(){return this.inicio;}

    public String getHorario(){return this.horario;}

}
