package com.biblioteca.ui;

import com.biblioteca.model.*;
import com.biblioteca.service.ServicioBiblioteca;
import com.biblioteca.structures.Cola;
import com.biblioteca.structures.ListaEnlazada;
import com.biblioteca.structures.MapaHash;
import com.biblioteca.structures.NodoComun;
import java.util.Scanner;

public class Menu {
    private final ServicioBiblioteca servicio;
    private final ConsoleIO consola;

    public Menu(ServicioBiblioteca servicio) {
        this.servicio = servicio;
        this.consola = new ConsoleIO(new Scanner(System.in));
    }

    public void iniciar() {
        ConsoleIO.println("""
                ╔════════════════════════════════════╗
                ║      BIBLIOTECA UNIVERSITARIA      ║
                ╚════════════════════════════════════╝
                """);
        boolean continuar = true;
        while (continuar) {
            mostrarMenuPrincipal();
            int op = consola.leerOpcion("  Opcion: ", 0, 5);
            switch (op) {
                case 1 -> {
                    menuLibros();
                }
                case 2 -> {
                    menuUsuarios();
                }
                case 3 -> {
                    menuPrestamos();
                }
                case 4 -> {
                    menuConsultas();
                }
                case 5 -> {
                    menuHistorial();
                }
                case 0 -> {
                    ConsoleIO.println("  Hasta luego.");
                    continuar = false;
                }
                default -> {
                    ConsoleIO.println("  [!] Opcion invalida.");
                }
            }
        }
    }

    private void mostrarMenuPrincipal() {
        ConsoleIO.println("""

                ┌──────────────────────────────────────────┐
                │           MENU PRINCIPAL                 │
                ├──────────────────────────────────────────┤
                │  1. Libros                               │
                │  2. Usuarios                             │
                │  3. Prestamos                            │
                │  4. Consultas                            │
                │  5. Historial                            │
                │  0. Salir                                │
                └──────────────────────────────────────────┘
                """);
    }

    private void menuLibros() {
        while (true) {
            ConsoleIO.println("""

                    --- LIBROS ---
                    1. Registrar libro
                    2. Listar libros (A-Z)
                    3. Buscar por codigo
                    4. Buscar por ISBN
                    5. Buscar por titulo
                    6. Eliminar libro
                    7. Marcar disponibilidad
                    0. Volver""");
            int op = consola.leerOpcion("  Opcion: ", 0, 7);
            switch (op) {
                case 1 -> {
                    registrarLibro();
                }
                case 2 -> {
                    listarLibros();
                }
                case 3 -> {
                    buscarLibroPorCodigo();
                }
                case 4 -> {
                    buscarLibroPorIsbn();
                }
                case 5 -> {
                    buscarLibroPorTitulo();
                }
                case 6 -> {
                    eliminarLibro();
                }
                case 7 -> {
                    marcarDisponibilidad();
                }
                case 0 -> {
                    return;
                }
            }
        }
    }

    private void registrarLibro() {
        ConsoleIO.println("\n  -- Registrar libro --");
        String isbn = consola.leerCadenaNoVacia("  ISBN: ");
        String titulo = consola.leerCadenaNoVacia("  Titulo: ");
        String autor = consola.leerCadena("  Autor: ");
        String cat = consola.leerCadenaNoVacia("  Categoria: ");
        try {
            Libro libro = servicio.registrarLibro(isbn, titulo, autor, new Categoria(cat));
            ConsoleIO.println("  >> Libro registrado con codigo: " + libro.getCodigo());
        } catch (Exception e) {
            ConsoleIO.println("  [!] Error: " + e.getMessage());
        }
    }

    private void listarLibros() {
        ConsoleIO.println("\n  -- Libros (orden alfabetico) --");
        ListaEnlazada<Libro> lista = servicio.listarLibrosAlfabetico();
        if (lista.estaVacia()) {
            ConsoleIO.println("  (ninguno)");
            return;
        }
        NodoComun<Libro> n = lista.getCabeza();
        while (n != null) {
            ConsoleIO.println("  " + n.getValor());
            n = n.getSiguiente();
        }
    }

    private void buscarLibroPorCodigo() {
        long cod = consola.leerLong("  Codigo: ");
        Libro libro = servicio.buscarPorCodigo(cod);
        ConsoleIO.println(libro != null ? "  " + libro : "  [!] No encontrado.");
    }

    private void buscarLibroPorIsbn() {
        String isbn = consola.leerCadenaNoVacia("  ISBN: ");
        Libro libro = servicio.buscarPorIsbn(isbn);
        ConsoleIO.println(libro != null ? "  " + libro : "  [!] No encontrado.");
    }

    private void buscarLibroPorTitulo() {
        String titulo = consola.leerCadenaNoVacia("  Titulo: ");
        Libro libro = servicio.buscarPorTitulo(titulo);
        ConsoleIO.println(libro != null ? "  " + libro : "  [!] No encontrado.");
    }

    private void eliminarLibro() {
        long cod = consola.leerLong("  Codigo del libro a eliminar: ");
        try {
            servicio.eliminarLibro(cod);
            ConsoleIO.println("  >> Libro eliminado.");
        } catch (Exception e) {
            ConsoleIO.println("  [!] " + e.getMessage());
        }
    }

    private void marcarDisponibilidad() {
        long cod = consola.leerLong("  Codigo: ");
        int d = consola.leerOpcion("  1=Disponible  2=No disponible: ", 1, 2);
        try {
            servicio.marcarDisponibilidad(cod, d == 1);
            ConsoleIO.println("  >> Disponibilidad actualizada.");
        } catch (Exception e) {
            ConsoleIO.println("  [!] " + e.getMessage());
        }
    }

    private void menuUsuarios() {
        while (true) {
            ConsoleIO.println("""

                    --- USUARIOS ---
                    1. Registrar usuario
                    2. Listar usuarios
                    3. Buscar por carnet
                    0. Volver""");
            int op = consola.leerOpcion("  Opcion: ", 0, 3);
            switch (op) {
                case 1 -> {
                    registrarUsuario();
                }
                case 2 -> {
                    listarUsuarios();
                }
                case 3 -> {
                    buscarUsuario();
                }
                case 0 -> {
                    return;
                }
            }
        }
    }

    private void registrarUsuario() {
        ConsoleIO.println("\n  -- Registrar usuario --");
        String carnet = consola.leerCadenaNoVacia("  Carnet: ");
        String nombre = consola.leerCadenaNoVacia("  Nombre: ");
        try {
            servicio.registrarUsuario(carnet, nombre);
            ConsoleIO.println("  >> Usuario registrado.");
        } catch (Exception e) {
            ConsoleIO.println("  [!] " + e.getMessage());
        }
    }

    private void listarUsuarios() {
        ConsoleIO.println("\n  -- Usuarios --");
        ListaEnlazada<Usuario> lista = servicio.listarUsuarios();
        if (lista.estaVacia()) {
            ConsoleIO.println("  (ninguno)");
            return;
        }
        NodoComun<Usuario> n = lista.getCabeza();
        while (n != null) {
            ConsoleIO.println("  " + n.getValor());
            n = n.getSiguiente();
        }
    }

    private void buscarUsuario() {
        String carnet = consola.leerCadenaNoVacia("  Carnet: ");
        Usuario u = servicio.buscarUsuario(carnet);
        ConsoleIO.println(u != null ? "  " + u : "  [!] No encontrado.");
    }

    private void menuPrestamos() {
        while (true) {
            ConsoleIO.println("""

                    --- PRESTAMOS ---
                    1. Prestar libro
                    2. Devolver libro
                    3. Listar prestamos activos
                    4. Ver cola de espera
                    0. Volver""");
            int op = consola.leerOpcion("  Opcion: ", 0, 4);
            switch (op) {
                case 1 -> {
                    prestarLibro();
                }
                case 2 -> {
                    devolverLibro();
                }
                case 3 -> {
                    listarPrestamosActivos();
                }
                case 4 -> {
                    verColaEspera();
                }
                case 0 -> {
                    return;
                }
            }
        }
    }

    private void prestarLibro() {
        String carnet = consola.leerCadenaNoVacia("  Carnet usuario: ");
        long cod = consola.leerLong("  Codigo libro: ");
        try {
            Prestamo p = servicio.prestarLibro(carnet, cod);
            if (p == null) {
                ConsoleIO.println("  >> Libro no disponible. Usuario agregado a la cola de espera.");
            } else {
                ConsoleIO.println("  >> Prestamo creado: " + p);
            }
        } catch (Exception e) {
            ConsoleIO.println("  [!] " + e.getMessage());
        }
    }

    private void devolverLibro() {
        long id = consola.leerLong("  ID del prestamo: ");
        try {
            Prestamo p = servicio.devolverLibro(id);
            ConsoleIO.println("  >> Devuelto: " + p);
        } catch (Exception e) {
            ConsoleIO.println("  [!] " + e.getMessage());
        }
    }

    private void listarPrestamosActivos() {
        ConsoleIO.println("\n  -- Prestamos activos --");
        ListaEnlazada<Prestamo> lista = servicio.listarPrestamosActivos();
        if (lista.estaVacia()) {
            ConsoleIO.println("  (ninguno)");
            return;
        }
        NodoComun<Prestamo> n = lista.getCabeza();
        while (n != null) {
            ConsoleIO.println("  " + n.getValor());
            n = n.getSiguiente();
        }
    }

    private void verColaEspera() {
        long cod = consola.leerLong("  Codigo del libro: ");
        Cola<String> cola = servicio.colaEspera(cod);
        if (cola.estaVacia()) {
            ConsoleIO.println("  Cola vacia.");
            return;
        }
        ConsoleIO.println("  Cola de espera:");
        NodoComun<String> n = cola.getFrente();
        int pos = 1;
        while (n != null) {
            ConsoleIO.println("  " + pos + ". " + n.getValor());
            pos++;
            n = n.getSiguiente();
        }
    }

    private void menuConsultas() {
        while (true) {
            ConsoleIO.println("""

                    --- CONSULTAS ---
                    1. Libros relacionados
                    2. Recorrido en-orden por titulo (A-Z)
                    3. Recorrido en-orden por codigo
                    4. Relaciones del grafo por categoria
                    5. Adyacencias del grafo
                    0. Volver""");
            int op = consola.leerOpcion("  Opcion: ", 0, 5);
            switch (op) {
                case 1 -> {
                    librosRelacionados();
                }
                case 2 -> {
                    enOrdenBST();
                }
                case 3 -> {
                    enOrdenAVL();
                }
                case 4 -> {
                    relacionesPorCategoria();
                }
                case 5 -> {
                    listarAdyacencias();
                }
                case 0 -> {
                    return;
                }
            }
        }
    }

    private void librosRelacionados() {
        long cod = consola.leerLong("  Codigo del libro semilla: ");
        ListaEnlazada<Long> lista = servicio.recomendarRelacionados(cod);
        if (lista.estaVacia()) {
            ConsoleIO.println("  Sin libros relacionados.");
            return;
        }
        ConsoleIO.println("  Libros relacionados:");
        NodoComun<Long> n = lista.getCabeza();
        while (n != null) {
            Libro libro = servicio.buscarPorCodigo(n.getValor());
            ConsoleIO.println("  - " + (libro != null ? libro : "codigo " + n.getValor()));
            n = n.getSiguiente();
        }
    }

    private void enOrdenBST() {
        ConsoleIO.println("\n  -- En orden alfabetico) --");
        ListaEnlazada<Libro> lista = servicio.listarLibrosAlfabetico();
        if (lista.estaVacia()) {
            ConsoleIO.println("  (ninguno)");
            return;
        }
        NodoComun<Libro> n = lista.getCabeza();
        while (n != null) {
            ConsoleIO.println("  " + n.getValor());
            n = n.getSiguiente();
        }
    }

    private void enOrdenAVL() {
        ConsoleIO.println("\n  -- En orden por codigo --");
        ListaEnlazada<Libro> lista = servicio.listarLibrosPorCodigo();
        if (lista.estaVacia()) {
            ConsoleIO.println("  (ninguno)");
            return;
        }
        NodoComun<Libro> n = lista.getCabeza();
        while (n != null) {
            ConsoleIO.println("  " + n.getValor());
            n = n.getSiguiente();
        }
    }

    private void relacionesPorCategoria() {
        MapaHash<String, ListaEnlazada<Libro>> mapa = servicio.listarRelacionesPorCategoria();
        if (mapa.getSize() == 0) {
            ConsoleIO.println("  No hay libros registrados.");
            return;
        }
        ConsoleIO.println("\n  -- Relaciones del grafo por categoria --");
        ListaEnlazada<String> categorias = mapa.claves();
        NodoComun<String> c = categorias.getCabeza();
        while (c != null) {
            ListaEnlazada<Libro> libros = mapa.obtener(c.getValor());
            ConsoleIO.println("  [" + c.getValor() + "] (" + libros.getSize() + " libro(s) conectados)");
            NodoComun<Libro> l = libros.getCabeza();
            while (l != null) {
                ConsoleIO.println("    - " + l.getValor());
                l = l.getSiguiente();
            }
            c = c.getSiguiente();
        }
    }

    private void listarAdyacencias() {
        MapaHash<Long, ListaEnlazada<Long>> adyacencias = servicio.listarAdyacencias();
        if (adyacencias.getSize() == 0) {
            ConsoleIO.println("  No hay libros en el grafo.");
            return;
        }
        ConsoleIO.println("\n  -- Adyacencias del grafo --");
        ListaEnlazada<Long> nodos = adyacencias.claves();
        NodoComun<Long> n = nodos.getCabeza();
        while (n != null) {
            Libro libro = servicio.buscarPorCodigo(n.getValor());
            String titulo = libro != null ? libro.getTitulo() : "codigo " + n.getValor();
            ListaEnlazada<Long> vecinos = adyacencias.obtener(n.getValor());
            if (vecinos.estaVacia()) {
                ConsoleIO.println("  [" + n.getValor() + "] " + titulo + " → (sin conexiones)");
            } else {
                StringBuilder sb = new StringBuilder("  [" + n.getValor() + "] " + titulo + " → ");
                NodoComun<Long> v = vecinos.getCabeza();
                while (v != null) {
                    Libro vecino = servicio.buscarPorCodigo(v.getValor());
                    sb.append("[").append(v.getValor()).append("] ");
                    sb.append(vecino != null ? vecino.getTitulo() : "?");
                    if (v.getSiguiente() != null) {
                        sb.append(", ");
                    }
                    v = v.getSiguiente();
                }
                ConsoleIO.println(sb.toString());
            }
            n = n.getSiguiente();
        }
    }

    private void menuHistorial() {
        ConsoleIO.println("\n  -- Historial (ultimas N acciones) --");
        int n = consola.leerEntero("  ¿Cuantas acciones?: ");
        if (n <= 0) {
            n = 10;
        }
        ListaEnlazada<AccionHistorial> lista = servicio.historialReciente(n);
        if (lista.estaVacia()) {
            ConsoleIO.println("  (vacio)");
            return;
        }
        NodoComun<AccionHistorial> nodo = lista.getCabeza();
        while (nodo != null) {
            ConsoleIO.println("  " + nodo.getValor());
            nodo = nodo.getSiguiente();
        }
    }
}
