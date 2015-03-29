package itesm.mx.distritotec;

/**
 * Created by ricardo on 28/03/15.
 */
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
