package org.example;


import controlador.ControladorPrincipal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vista.VentanaPrincipal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Principal {

    public static void main(String[] args) {

            VentanaPrincipal vistaPrincipal = new VentanaPrincipal();
            ControladorPrincipal controlador = new ControladorPrincipal(vistaPrincipal);

    }


}