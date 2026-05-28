package com.biblioteca;

import com.biblioteca.service.ServicioBiblioteca;
import com.biblioteca.ui.Menu;

public class Main {

    public static void main(String[] args) {
        ServicioBiblioteca servicio = new ServicioBiblioteca();
        new Menu(servicio).iniciar();
    }

}
