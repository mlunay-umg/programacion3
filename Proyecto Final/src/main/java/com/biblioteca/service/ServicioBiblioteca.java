package com.biblioteca.service;

import com.biblioteca.model.*;
import com.biblioteca.structures.*;

public class ServicioBiblioteca {

    private final MapaHash<Long, Libro>        librosPorCodigo   = new MapaHash<>();
    private final MapaHash<String, Libro>      librosPorIsbn     = new MapaHash<>();
    private final ArbolAVL<Long, Libro>        indiceLibrosCodigo = new ArbolAVL<>();
    private final ArbolBST                     indiceLibrosTitulo = new ArbolBST();
    private final MapaHash<String, Usuario>    usuariosPorCarnet = new MapaHash<>();
    private final ListaEnlazada<Prestamo>      prestamos         = new ListaEnlazada<>();
    private final MapaHash<Long, Cola<String>> colasEspera       = new MapaHash<>();
    private final Pila<AccionHistorial>        historial         = new Pila<>(50);
    private final Grafo<Long>                  grafoLibros       = new Grafo<>();

    private long contadorLibros    = 0;
    private long contadorPrestamos = 0;

    public Libro registrarLibro(String isbn, String titulo, String autor, Categoria categoria) {
        String isbnLimpio = isbn.replaceAll("[^0-9]", "");
        if (librosPorIsbn.contiene(isbnLimpio)) {
            throw new IllegalArgumentException("Ya existe un libro con ISBN: " + isbn);
        }
        long codigo = ++contadorLibros;
        Libro libro = new Libro(codigo, isbn, titulo, autor, categoria);
        librosPorCodigo.poner(codigo, libro);
        librosPorIsbn.poner(isbnLimpio, libro);
        indiceLibrosCodigo.insertar(codigo, libro);
        indiceLibrosTitulo.insertar(titulo.toLowerCase(), libro);
        grafoLibros.agregarNodo(codigo);
        conectarPorCategoria(libro);
        historial.apilar(new AccionHistorial(TipoAccionHistorial.REGISTRAR_LIBRO,
                "Libro registrado: '" + titulo + "' (codigo " + codigo + ")"));
        return libro;
    }

    private void conectarPorCategoria(Libro nuevo) {
        ListaEnlazada<Libro> todosLibros = librosPorCodigo.valores();
        NodoComun<Libro> n = todosLibros.getCabeza();
        while (n != null) {
            Libro otro = n.getValor();
            if (otro.getCodigo() != nuevo.getCodigo()
                    && otro.getCategoria().equals(nuevo.getCategoria())) {
                grafoLibros.agregarArista(nuevo.getCodigo(), otro.getCodigo());
            }
            n = n.getSiguiente();
        }
    }

    public void eliminarLibro(long codigo) {
        Libro libro = librosPorCodigo.obtener(codigo);
        if (libro == null) {
            throw new IllegalArgumentException("Libro no encontrado: " + codigo);
        }
        NodoComun<Prestamo> n = prestamos.getCabeza();
        while (n != null) {
            Prestamo p = n.getValor();
            if (p.getCodigoLibro() == codigo && p.isActivo()) {
                throw new IllegalStateException("No se puede eliminar: el libro tiene prestamos activos");
            }
            n = n.getSiguiente();
        }
        Cola<String> cola = colasEspera.obtener(codigo);
        if (cola != null && !cola.estaVacia()) {
            throw new IllegalStateException("No se puede eliminar: el libro tiene cola de espera");
        }
        librosPorCodigo.eliminar(codigo);
        librosPorIsbn.eliminar(libro.getIsbn());
        indiceLibrosCodigo.eliminar(codigo);
        indiceLibrosTitulo.eliminar(libro.getTitulo().toLowerCase());
        colasEspera.eliminar(codigo);
        grafoLibros.eliminarNodo(codigo);
        historial.apilar(new AccionHistorial(TipoAccionHistorial.ELIMINAR_LIBRO,
                "Libro eliminado: '" + libro.getTitulo() + "' (codigo " + codigo + ")"));
    }

    public void marcarDisponibilidad(long codigo, boolean disponible) {
        Libro libro = librosPorCodigo.obtener(codigo);
        if (libro == null) {
            throw new IllegalArgumentException("Libro no encontrado: " + codigo);
        }
        libro.setDisponible(disponible);
    }

    public Libro buscarPorCodigo(long codigo) {
        return librosPorCodigo.obtener(codigo);
    }

    public Libro buscarPorIsbn(String isbn) {
        return librosPorIsbn.obtener(isbn.replaceAll("[^0-9]", ""));
    }

    public Libro buscarPorTitulo(String titulo) {
        return indiceLibrosTitulo.buscar(titulo.toLowerCase());
    }

    public ListaEnlazada<Libro> listarLibrosAlfabetico() {
        return indiceLibrosTitulo.enOrden();
    }

    public ListaEnlazada<Libro> listarLibrosPorCodigo() {
        return indiceLibrosCodigo.enOrden();
    }

    public Usuario registrarUsuario(String carnet, String nombre) {
        if (usuariosPorCarnet.contiene(carnet)) {
            throw new IllegalArgumentException("Ya existe un usuario con carnet: " + carnet);
        }
        Usuario usuario = new Usuario(carnet, nombre);
        usuariosPorCarnet.poner(carnet, usuario);
        historial.apilar(new AccionHistorial(TipoAccionHistorial.REGISTRAR_USUARIO,
                "Usuario registrado: " + nombre + " (carnet " + carnet + ")"));
        return usuario;
    }

    public Usuario buscarUsuario(String carnet) {
        return usuariosPorCarnet.obtener(carnet);
    }

    public ListaEnlazada<Usuario> listarUsuarios() {
        return usuariosPorCarnet.valores();
    }

    public Prestamo prestarLibro(String carnet, long codigoLibro) {
        Usuario usuario = usuariosPorCarnet.obtener(carnet);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado: " + carnet);
        }
        Libro libro = librosPorCodigo.obtener(codigoLibro);
        if (libro == null) {
            throw new IllegalArgumentException("Libro no encontrado: " + codigoLibro);
        }

        if (!libro.isDisponible()) {
            Cola<String> cola = colasEspera.obtener(codigoLibro);
            if (cola == null) {
                cola = new Cola<>();
                colasEspera.poner(codigoLibro, cola);
            }
            cola.encolar(carnet);
            historial.apilar(new AccionHistorial(TipoAccionHistorial.EN_COLA_ESPERA,
                    "Usuario " + carnet + " en cola para libro " + codigoLibro));
            return null;
        }

        long id = ++contadorPrestamos;
        Prestamo prestamo = new Prestamo(id, carnet, codigoLibro);
        prestamos.agregar(prestamo);
        libro.setDisponible(false);
        usuario.getPrestamosActivos().agregar(id);
        historial.apilar(new AccionHistorial(TipoAccionHistorial.PRESTAMO_CREADO,
                "Prestamo #" + id + ": libro " + codigoLibro + " a " + carnet));
        return prestamo;
    }

    public Prestamo devolverLibro(long idPrestamo) {
        Prestamo prestamo = null;
        NodoComun<Prestamo> n = prestamos.getCabeza();
        while (n != null) {
            if (n.getValor().getId() == idPrestamo && n.getValor().isActivo()) {
                prestamo = n.getValor();
                break;
            }
            n = n.getSiguiente();
        }
        if (prestamo == null) {
            throw new IllegalArgumentException("Prestamo no encontrado: " + idPrestamo);
        }

        prestamo.setFechaDevolucion(java.time.LocalDateTime.now());
        Libro libro = librosPorCodigo.obtener(prestamo.getCodigoLibro());
        Usuario usuario = usuariosPorCarnet.obtener(prestamo.getCarnetUsuario());
        if (usuario != null) {
            usuario.getPrestamosActivos().eliminar(idPrestamo);
        }

        historial.apilar(new AccionHistorial(TipoAccionHistorial.DEVOLUCION_CREADA,
                "Devolucion prestamo #" + idPrestamo + " (libro " + prestamo.getCodigoLibro() + ")"));

        Cola<String> cola = colasEspera.obtener(prestamo.getCodigoLibro());
        if (cola != null && !cola.estaVacia()) {
            String siguienteCarnet = cola.desencolar();
            libro.setDisponible(true);
            prestarLibro(siguienteCarnet, prestamo.getCodigoLibro());
        } else {
            if (libro != null) {
                libro.setDisponible(true);
            }
        }

        return prestamo;
    }

    public ListaEnlazada<Prestamo> listarPrestamosActivos() {
        ListaEnlazada<Prestamo> activos = new ListaEnlazada<>();
        NodoComun<Prestamo> n = prestamos.getCabeza();
        while (n != null) {
            if (n.getValor().isActivo()) {
                activos.agregar(n.getValor());
            }
            n = n.getSiguiente();
        }
        return activos;
    }

    public Cola<String> colaEspera(long codigoLibro) {
        Cola<String> cola = colasEspera.obtener(codigoLibro);
        return cola != null ? cola : new Cola<>();
    }

    public ListaEnlazada<AccionHistorial> historialReciente(int n) {
        ListaEnlazada<AccionHistorial> lista = new ListaEnlazada<>();
        NodoComun<AccionHistorial> nodo = historial.getCima();
        int count = 0;
        while (nodo != null && count < n) {
            lista.agregar(nodo.getValor());
            nodo = nodo.getSiguiente();
            count++;
        }
        return lista;
    }

    public ListaEnlazada<Long> recomendarRelacionados(long codigoLibro) {
        return grafoLibros.bfs(codigoLibro, 2);
    }

    public MapaHash<String, ListaEnlazada<Libro>> listarRelacionesPorCategoria() {
        MapaHash<String, ListaEnlazada<Libro>> porCategoria = new MapaHash<>();
        ListaEnlazada<Libro> todos = librosPorCodigo.valores();
        NodoComun<Libro> n = todos.getCabeza();
        while (n != null) {
            Libro libro = n.getValor();
            String cat = libro.getCategoria().getNombre();
            if (!porCategoria.contiene(cat)) {
                porCategoria.poner(cat, new ListaEnlazada<>());
            }
            porCategoria.obtener(cat).agregar(libro);
            n = n.getSiguiente();
        }
        return porCategoria;
    }

    public MapaHash<Long, ListaEnlazada<Long>> listarAdyacencias() {
        MapaHash<Long, ListaEnlazada<Long>> resultado = new MapaHash<>();
        NodoGrafo<Long>[] tabla = grafoLibros.getTabla();
        for (int i = 0; i < grafoLibros.getCapacidad(); i++) {
            NodoGrafo<Long> ng = tabla[i];
            while (ng != null) {
                resultado.poner(ng.getValor(), grafoLibros.vecinos(ng.getValor()));
                ng = ng.getSiguienteEnTabla();
            }
        }
        return resultado;
    }
}
