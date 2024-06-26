package controlador;

import vista.VentanaPrincipal;
import vista.VentanaTabla;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ControladorTabla implements MouseListener {

    VentanaTabla vistaTabla;
    VentanaPrincipal vistaPrincipal;


    public ControladorTabla(VentanaTabla vistaTabla, VentanaPrincipal vistaPrincipal) {
        //Agregar la vista en el constructor
        this.vistaTabla = vistaTabla;
        this.vistaPrincipal = vistaPrincipal;
        //Iniciar Variables que pueda tener (listas, etc)

        //Asignar los listeners al contolador DEBEN SER TODOS LOS QUE TENGAN LISTENER
        this.vistaTabla.getBtnVoler().addMouseListener(this);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() ==  vistaTabla.getBtnVoler()) {
            

            vistaTabla.setVisible(false);

            vistaPrincipal.setSize(vistaTabla.getSize());
            vistaPrincipal.setLocation(vistaTabla.getLocation());

            vistaPrincipal.setVisible(true);

            vistaTabla = null;

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
}
