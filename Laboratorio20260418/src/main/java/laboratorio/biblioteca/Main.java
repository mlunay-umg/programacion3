package laboratorio.biblioteca;

import laboratorio.biblioteca.elemento.Libro;
import laboratorio.biblioteca.elemento.Usuario;

import java.util.List;

public class Main {
    static void main() {
        Biblioteca biblioteca = new Biblioteca();

        biblioteca.registrarLibro(new Libro("1", "Java Basico", true));
        biblioteca.registrarLibro(new Libro("2", "Estructuras de Datos", true));
        biblioteca.registrarLibro(new Libro("3", "Estructuras de Datos Avanzadas", true));


        biblioteca.registrarUsuario(new Usuario("U1","Juan"));
        biblioteca.registrarUsuario(new Usuario("U2","Ana"));

        biblioteca.prestarLibros("1","U1");
        biblioteca.prestarLibros("1","U2"); //entra a cola

        biblioteca.devolverLibro("1");

        biblioteca.mostrarHistorial();

        //busqueda por titulo
        String busqueda = "Datos";
        List<Libro> resultado =  biblioteca.buscarPorTitulo(busqueda);
        System.out.println("Buscara libros con titulo ("+busqueda+")");
        for(Libro libro: resultado) {
            System.out.println("Encontro : "+libro);
        }

    }
}
