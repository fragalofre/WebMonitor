package vista;

import controlador.ControladorPrincipal;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class VentanaTabla extends JFrame {
    private JTable tabla;
    private JPanel panelPrincipal;
    private JScrollPane panelTabla;
    private JPanel panelInformacion;
    private JLabel txtError;
    private JPanel panelBotones;
    private JButton btnVoler;
    private JLabel txtInformacion;
    DefaultTableModel dtm = new DefaultTableModel();
    private ControladorPrincipal controladorPrincipal;


    public VentanaTabla(ControladorPrincipal controladorPrincipal) {

        this.controladorPrincipal = controladorPrincipal;

        //Llamar al método para introducir los datos dentro de una tabla
        crearTabla();

        //En caso de que los valores de la tabla sean diferentes a null crea la tabla
        if (controladorPrincipal.valoresTabla.isEmpty()) {

        }  else {



            //Construir un renderer para centrar las columnas
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);

            //Centrar las columnas usando el renderer
            tabla.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            tabla.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        }

        //TODO crear una función que según el estado donde ha habido el fallo muestre diferente texto
        txtError.setText(controladorPrincipal.getVistaPrincipal().getTxtEstado().getText());

        //Asignar el contenido al JFrame y hacerlo visible
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(panelPrincipal);
        setSize(controladorPrincipal.vistaPrincipal.getSize());
        setLocation(controladorPrincipal.vistaPrincipal.getLocation());
        setVisible(true);

    }

    private void crearTabla(){

        //Valores de Ejemplo para pruebas BORRAR///////////////////////////////////////////////////////////////////////
//
//        controladorPrincipal.valoresTabla.put("USA", "1");
//        controladorPrincipal.valoresTabla.put("Japan", "3");
//        controladorPrincipal.valoresTabla.put("China", "2");
//        controladorPrincipal.valoresTabla.put("India", "5");
//        controladorPrincipal.valoresTabla.put("Germany", "4");
////
//
//          Valores de Ejemplo para pruebas BORRAR///////////////////////////////////////////////////////////////////////

        //Ontroducir los titulos de la tabla
        String[] titulo  = new String[] {"Operación", "Resultado"};
        dtm.setColumnIdentifiers(titulo);



        if (controladorPrincipal.valoresTabla.isEmpty()) {

        } else {

            //Llenar los valores de las columnas
            List<String> claves = new ArrayList<String>(controladorPrincipal.valoresTabla.keySet());
            List<String> valores = new ArrayList<String>(controladorPrincipal.valoresTabla.values());


            for (int i = 0; i < claves.size(); i++) {

                dtm.addRow(new Object[]{
                        claves.get(i), valores.get(i)
                });


            }
        }



        tabla.setModel(dtm);
        tabla.setEnabled(false);

    }


    public JTable getTabla() {
        return tabla;
    }

    public void setTabla(JTable tabla) {
        this.tabla = tabla;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JScrollPane getPanelTabla() {
        return panelTabla;
    }

    public void setPanelTabla(JScrollPane panelTabla) {
        this.panelTabla = panelTabla;
    }

    public JPanel getPanelInformacion() {
        return panelInformacion;
    }

    public void setPanelInformacion(JPanel panelInformacion) {
        this.panelInformacion = panelInformacion;
    }

    public JLabel getTxtError() {
        return txtError;
    }

    public void setTxtError(JLabel txtError) {
        this.txtError = txtError;
    }

    public JPanel getPanelBotones() {
        return panelBotones;
    }

    public void setPanelBotones(JPanel panelBotones) {
        this.panelBotones = panelBotones;
    }


    public JButton getBtnVoler() {
        return btnVoler;
    }

    public void setBtnVoler(JButton btnVoler) {
        this.btnVoler = btnVoler;
    }

    public DefaultTableModel getDtm() {
        return dtm;
    }

    public void setDtm(DefaultTableModel dtm) {
        this.dtm = dtm;
    }
}

