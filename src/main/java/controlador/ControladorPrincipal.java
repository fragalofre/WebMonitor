package controlador;

import modelo.ConfiguracionWeb;
import modelo.DocumentControlado;
import modelo.Impresora;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import vista.VentanaTabla;
import vista.VentanaPrincipal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControladorPrincipal implements MouseListener, KeyListener {

    //Definir las variables de la clase (incluidas las vistas)
    public VentanaPrincipal vistaPrincipal;
    private WebDriver driver;
    private ChromeOptions options = new ChromeOptions();
    private static final Logger logger = LogManager.getLogger(ControladorPrincipal.class);
    public String numeroSerie = "";
    public String modelo = "";
    public Map valoresTabla = new HashMap();
    private static Pattern pattern = Pattern.compile("^RREU.{14}$");


    public ControladorPrincipal(VentanaPrincipal vistaPrincipal) {
        //Agregar la vista en el constructor
        this.vistaPrincipal = vistaPrincipal;
        //Iniciar Variables que pueda tener (listas, etc)
        this.vistaPrincipal.getBtnDetalles().setVisible(false);
        this.vistaPrincipal.getBtnAceptar().setVisible(false);
        //Limitar el contenido de los documentos
        this.vistaPrincipal.getTxtRemote().setDocument(new DocumentControlado(18, false));
        this.vistaPrincipal.getTxtParteIpVariable().setDocument(new DocumentControlado(3, true));


        //Asignar los listeners al contolador DEBEN SER TODOS LOS QUE TENGAN LISTENER
        this.vistaPrincipal.getBtnIniciar().addMouseListener(this);
        this.vistaPrincipal.getBtnCancelar().addMouseListener(this);
        this.vistaPrincipal.getBtnReset().addMouseListener(this);
        this.vistaPrincipal.getBtnAceptar().addMouseListener(this);
        this.vistaPrincipal.getBtnDetalles().addMouseListener(this);
        this.vistaPrincipal.getTxtRemote().addKeyListener(this);


    }


    @Override
    public void mouseClicked(MouseEvent e) {



        //En caso de que el evento venga del botón iniciar de la Ventana Principal
        if (e.getSource() == vistaPrincipal.getBtnIniciar() && vistaPrincipal.getBtnIniciar().isEnabled()) {


            //En caso de no haber IP no pasa nada
                    if (vistaPrincipal.getTxtParteIpVariable().getText().isEmpty()) {

                        //Si hay IP se ejecuta
                    } else {


                        //Tras pulsar el botón cambian los botones y se deshabilita el texto
                        vistaPrincipal.getBtnReset().setEnabled(false);
                        vistaPrincipal.getBtnIniciar().setText("Running");
                        vistaPrincipal.getBtnIniciar().setEnabled(false);
                        vistaPrincipal.getTxtParteIpVariable().setEnabled(false);
                        vistaPrincipal.getTxtRemote().setEnabled(false);
                        vistaPrincipal.getCmbParteIpFija().setEnabled(false);

                        //Cambiar el color a amarillo (ejecutandose) mientras se ejecuta
                        cambiarColorEjecutando();
                        actualizarTextoEstado("Working");
                        procesoBackground();
                        //Volver a dejar a 0 el numero de seire y modelo de máquina
//                    numeroSerie = "";
//                    modelo = "";


            }
        }

        //En caso de apretar el botón cancelar de la Ventana Principal
        if (e.getSource() == vistaPrincipal.getBtnCancelar() && vistaPrincipal.getBtnCancelar().isEnabled()) {
            //TODO definir el comportamiento del botón cancel
            //  driver.close();   //VOLVER A PONER
          //  mostrarBotonesFallo();  /////////////////////BORRAR PRUEBA /////////////////////////7

        }


        //En caso de clicar en el botón reset de la ventana principal
            if (e.getSource() == vistaPrincipal.getBtnReset() && vistaPrincipal.getBtnReset().isEnabled()) {
            //TODO comportamiento del botón Reset
            }


        //En caso de clicar en el botón Acept de la ventana principal
        if (e.getSource() == vistaPrincipal.getBtnAceptar() && vistaPrincipal.getBtnAceptar().isEnabled()) {

            vistaPrincipal.getBtnAceptar().setVisible(false);
            vistaPrincipal.getBtnDetalles().setVisible(false);
            habilitarControles();

        }

            //En caso de clicar en el botón detalles de la ventana principal
            if (e.getSource() == vistaPrincipal.getBtnDetalles() && vistaPrincipal.getBtnDetalles().isEnabled()) {

                mostrarTabla();

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

//        if (e.getSource() == vistaPrincipal.getTxtRemote()) {
//
//            validarRemote();
//
//        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        //Controlar lel estado del texto del remote tras soldar la tecla
        if (e.getSource() == vistaPrincipal.getTxtRemote()) {

            //Si está en blanco es correcto
            if (vistaPrincipal.getTxtRemote().getText().equals("")) {

                this.vistaPrincipal.getTxtRemote().setBackground(Color.WHITE);

                //En caso de que se introduzca algo controlar lo introducido
            } else {
                validarRemote();
            }

        }

    }


    public void cambiarColorEjecutando(){

        vistaPrincipal.getTxtEstado().setBackground(Color.YELLOW);

    }

    public void actualizarTextoEstado(String texto) {

        vistaPrincipal.getTxtEstado().setText(texto);

    }

    public void cambiarColorError(){

        vistaPrincipal.getTxtEstado().setBackground(Color.RED);

    }

    public void actualizarTextoError(){

        //Error según estado concreto
        vistaPrincipal.getTxtEstado().setText("Error during " + vistaPrincipal.getTxtEstado().getText().toString().toLowerCase());

    }

    public void estadoTrasCompletarExito(Impresora impresora){


        vistaPrincipal.getBtnIniciar().setText("Launch");
        habilitarControles();
        vistaPrincipal.getTxtEstado().setText(impresora.getIp().toString() + " - Finished Successfully");
        vistaPrincipal.getTxtEstado().setBackground(Color.GREEN);

    }

    public void estadoTrasFallo(){

        //Cambiar esl estado de botones a fallo
        mostrarBotonesFallo();

        //Cambiar a calor rojo de error y actualizar mensaje
        cambiarColorError();
        actualizarTextoError();

        //Habiliar el botón para volver al lanzar
        vistaPrincipal.getBtnIniciar().setText("Launch");

    }


    public String definirTender(){

        //Comprobar los checkboxes y mirar que se ha seleccionado
        if (vistaPrincipal.getStantardTemplateRadioButton().isSelected()){
            return "standard";
        } else if ( vistaPrincipal.getTender1RadioButton().isSelected()){
            return "mapfre";
        } else if (vistaPrincipal.getTender2RadioButton().isSelected()){
            return "tender2";
        }


        return null;

    }


    public void mostrarTabla(){

        //Hace desaparecer la ventana actual
        this.vistaPrincipal.setVisible(false);

        //Crear la ventana con la tabla y pasar los parámetros
        VentanaTabla vistaTabla = new VentanaTabla(ControladorPrincipal.this);
        ControladorTabla controladorTabla = new ControladorTabla(vistaTabla, vistaPrincipal);

    }


    public void mostrarBotonesFallo(){

        //Deshabilitar los botones principales
       vistaPrincipal.getBtnIniciar().setEnabled(false);
       vistaPrincipal.getBtnCancelar().setEnabled(false);
       vistaPrincipal.getBtnReset().setEnabled(false);

       //Mostrar los botones de fallo
        vistaPrincipal.getBtnDetalles().setVisible(true);
        vistaPrincipal.getBtnAceptar().setVisible(true);

    }

    public void habilitarControles(){
        vistaPrincipal.getCmbParteIpFija().setEnabled(true);
        vistaPrincipal.getTxtParteIpVariable().setEnabled(true);
        vistaPrincipal.getTxtParteIpVariable().setText("");
        vistaPrincipal.getTxtRemote().setEnabled(true);
        vistaPrincipal.getTxtRemote().setText("");
        vistaPrincipal.getTxtRemote().setBackground(Color.WHITE);
        vistaPrincipal.getBtnIniciar().setEnabled(true);
        vistaPrincipal.getBtnCancelar().setEnabled(true);
        vistaPrincipal.getBtnReset().setEnabled(true);
    }

    //Lanzar el driver por background
    private void procesoBackground() {


        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

            @Override
            protected Void doInBackground() throws Exception {


                //Obtener la ip a partir de la parte fija y variable
                String ip = vistaPrincipal.getCmbParteIpFija().getSelectedItem().toString() + vistaPrincipal.getTxtParteIpVariable().getText().toString();
                String tender = definirTender();
                String remote = vistaPrincipal.getTxtRemote().getText();


                Impresora impresora = new Impresora();

                impresora.setIp(ip);
                impresora.setTender(tender);
                impresora.setClaveRemote(remote);


                try {
                    //Iniciar el processo en Background
                    options.setHeadless(false); //Chrome invisible
                    driver = new ChromeDriver(options);
                    driver.manage().window().minimize(); //MINIMIZA LA VENTANA
                    ConfiguracionWeb.principal(impresora, driver, ControladorPrincipal.this);
                    //Volver a dejarlo como al inicio en caso de que vaya bien
                    estadoTrasCompletarExito(impresora);
                    logger.info(numeroSerie + " - " + modelo +  " - PROCESO TERMINADO CORRECTAMENTE");

                } catch (Exception a) {
                    //informar por el log
                    logger.error("ERROR DURING " + vistaPrincipal.getTxtEstado().getText().toUpperCase());
                    logger.error(a.getMessage());
                    estadoTrasFallo();
                }


                //Cerrar el navegador
               // driver.close();

                return null;
            };

        };

        worker.execute();

    }


    private void validarRemote() {
        String textoIntroducido = vistaPrincipal.getTxtRemote().getText();
        Matcher matcher = pattern.matcher(textoIntroducido);

        // Verificar si el texto coincide con el patrón
        if (matcher.matches()) {
            vistaPrincipal.getTxtRemote().setBackground(Color.GREEN);
        } else {
            vistaPrincipal.getTxtRemote().setBackground(Color.RED);
        }
    }




    public VentanaPrincipal getVistaPrincipal() {
        return vistaPrincipal;
    }

    public void setVistaPrincipal(VentanaPrincipal vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }



}
