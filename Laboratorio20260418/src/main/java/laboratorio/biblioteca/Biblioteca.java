package laboratorio.biblioteca;
import laboratorio.biblioteca.elemento.Libro;
import laboratorio.biblioteca.elemento.Usuario;

import java.util.*;

public class Biblioteca {
    public HashMap<String, Libro> libros = new HashMap<>();
    public HashMap<String, Usuario> usuarios = new HashMap<>();

    public Queue<Usuario> colaEspera = new LinkedList<>();
    public Stack<String> historial = new Stack<>();

    public NodoBST raiz;

    public NodoBST insertar(NodoBST nodo, Libro libro) {
        if (nodo == null) return new NodoBST(libro);

        if (libro.titulo.compareTo(nodo.libro.titulo) < 0) {
            nodo.izquierda = insertar(nodo.izquierda, libro);
        } else {
            nodo.derecha = insertar(nodo.derecha,libro);
        }

        return nodo;
    }

    //registrar Libro
    public void registrarLibro(Libro libro) {
        libros.put(libro.isbn, libro);
        raiz = insertar(raiz,libro);
        historial.push("Libro Registrado: "+ libro.titulo);
    }

    //registrar usuario
    public void registrarUsuario(Usuario usuario) {
        usuarios.put(usuario.id, usuario);
        historial.push("Usuario Regstrado: "+ usuario.nombre);
    }

    //Solicitar prestamo
    public void prestarLibros(String isbn, String usuarioId) {
        Libro libro = libros.get(isbn);
        Usuario usuario = usuarios.get(usuarioId);

        if (libro == null || usuario == null) return;

        if (libro.disponible) {
            libro.disponible = false;
            historial.push("Libro "+libro.titulo+" prestado a: "+usuario.nombre);
        } else {
            colaEspera.add(usuario);
            historial.push("Usuario "+usuario.nombre+ "en espera e libro "+libro.titulo);
        }
    }

    public void devolverLibro(String isbn) {
        Libro libro = libros.get(isbn);

        if (!colaEspera.isEmpty()) {
            Usuario siguiente = colaEspera.poll();
            historial.push("Libro "+libro.titulo+" asignado a: "+ siguiente.nombre);
        } else {
            libro.disponible = true;
            historial.push("Libro: "+libro.titulo+" deuelto y disponible");
        }
    }

    public void mostrarHistorial() {
        for (String historico: historial) {
            System.out.println(historico);
        }
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        ArrayList<Libro> busqueda = new ArrayList<>();
        for (String isbn: libros.keySet()) {
            if (libros.get(isbn).titulo.toLowerCase().contains(titulo.toLowerCase())) {
                busqueda.add(libros.get(isbn));
            }
        }
        return busqueda;
    }


}
