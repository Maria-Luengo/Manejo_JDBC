package datos;

//DAO Data Access Object -> Métodos que dan información de las entidades
import dominio.Persona;
import static datos.Conexion.*;
import java.sql.*;
import java.util.*;

//Heremos 1 por cada entidad
public class PersonaDao {

    /*NO ES COÚN QUE POOR CADA SENTENCIA SQL ABRA Y CIERRE LA CONEXIÓN. 
    Lpo norm es hacerlo en un conjunto de transaciones, abrir, hacer 
    sentencias y luego cerrar*/
    private static final String SQL_SELECT = "SELECT * FROM persona";

    //el método inset recibe un objeto de persona
    private static final String SQL_INSERT = "INSERT INTO persona (idpersona, "
            + "nombre, apellidpo, email, telefono)\n"
            + "VALUES (?, ?, ?, ?, ?)"; //un ? por cada campo
    
    private static final String SQL_UPDATE = "UPDATE persona SET " //recibe obj Persona con identificador
            + "nombre = ?, "
            + "apellidpo = ?, "
            + "email = ?, "
            + "telefono= ? "
            + "WHERE idpersona= ?";
    
    private static final String SQL_DELETE = "DELETE FROM persona " +
                                        "WHERE idpersona = ?;"; //un ? por cada campo


    //connection, prepare stagement y result
    public List<Persona> seleccionar() throws SQLException { 
        //devuelve OBJETOS para trabajar con ellos en la capa negocio
        //1 - Creamos nuestros objetos a null
        //tenemos 3 variables con las que vamos a trabajar
        Connection conn = null; //prarámetors para conectarme a la BBDD
        PreparedStatement stmt = null;
        ResultSet rs = null;
        //cada línea la convertiré en un nuevo objeto
        //lo resultados va a resultSet
        List<Persona> personas = new ArrayList<>();
        try {
            //1- establezco conexion, este método está en conexión
            conn = getConnection(); //hace return del DriveMangerConnection
            //2- preparo para ejecutar
            stmt = conn.prepareStatement(SQL_SELECT);
            //3- ejecuto
            rs = stmt.executeQuery();

            while (rs.next()) {
                //consuñlto línea a línea del select. el tipo de dato coincide con el tipo de mi tabla
                int idPersona = rs.getInt("idpersona");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellidpo");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");

                //Ahora CREAMOS LOS OBJETOS y lo añado al List
                personas.add(new Persona(idPersona, nombre, apellido, email, telefono));
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally { //el finally SIEMPRE se va a ejecutar
            close(rs);
            close(stmt); //viene de la clase conexión
            close(conn);
        }
        return personas; //mi arrayList
    }

    //METODO PARA INSET
    public int insertar(Persona persona) throws  SQLException {
        //preparatinon y stragement, no hay resul xq no ejecuto nada
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);

            //mando los atributos de la prsona
            stmt.setInt(1, persona.getIdPersona()); //1 -> simboliza el p`rimer interrogante
            stmt.setString(2, persona.getNombre());
            stmt.setString(3, persona.getApellido());
            stmt.setString(4, persona.getEmail());
            stmt.setString(5, persona.getTelefono());

            registros = stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            //para los close
            close(stmt);
            close(conn);
        }
        return registros;
    }

    //METODO PARA UPDATE
    public int actualizar(Persona persona) throws  SQLException {
        //preparatinon y stragement, no hay resul xq no ejecuto nada
        Connection conn = null;
        PreparedStatement stmt = null;
        int registrosAc = 0;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);

            //mando los atributos de la prsona
            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setString(3, persona.getEmail());
            stmt.setString(4, persona.getTelefono());
            stmt.setInt(5, persona.getIdPersona());

            registrosAc = stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            //para los close
            close(stmt);
            close(conn);
        }
        return registrosAc;
    }
    
    
    //HACER EL METODO DELETE
    public int borrar(Persona persona) throws  SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            //mando los atributos de la prsona
            stmt.setInt(1, persona.getIdPersona());

            registros = stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            //para los close
            close(stmt);
            close(conn);
        }
        return registros;
    }
}
