package laboratorio.biblioteca.elemento;

public class Libro {

    public String isbn;
    public String titulo;
    public boolean disponible;

    public Libro(String isbn, String titulo, boolean disponible) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.disponible = disponible;
    }

    public String toString() {
        return "ISBN: "+this.isbn+" , TITULO: "+this.titulo+" , DISPONIBLE: "+this.disponible;
    }


}
