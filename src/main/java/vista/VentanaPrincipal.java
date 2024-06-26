package vista;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private JPanel panelPrincipal;
    private JComboBox cmbParteIpFija;
    private JTextField txtParteIpVariable;
    private JRadioButton stantardTemplateRadioButton;
    private JRadioButton tender1RadioButton;
    private JRadioButton tender2RadioButton;
    private JTextField txtRemote;
    private JButton btnIniciar;
    private JButton btnReset;
    private JButton btnCancelar;
    private JTextField txtEstado;
    private JPanel panelp;
    private JPanel panelTender;
    private JPanel panelRemote;
    private JPanel panelBotones;
    private JPanel panelEstado;
    private JButton btnAceptar;
    private JButton btnDetalles;
    private static JFrame ventana;
    private WebDriver driver;
    private ChromeOptions options = new ChromeOptions();


    public static JFrame getVentana() {
        return ventana;
    }

    public static void setVentana(JFrame ventana) {
        VentanaPrincipal.ventana = ventana;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JComboBox getCmbParteIpFija() {
        return cmbParteIpFija;
    }

    public void setCmbParteIpFija(JComboBox cmbParteIpFija) {
        this.cmbParteIpFija = cmbParteIpFija;
    }

    public JTextField getTxtParteIpVariable() {
        return txtParteIpVariable;
    }

    public void setTxtParteIpVariable(JTextField txtParteIpVariable) {
        this.txtParteIpVariable = txtParteIpVariable;
    }

    public JRadioButton getStantardTemplateRadioButton() {
        return stantardTemplateRadioButton;
    }

    public void setStantardTemplateRadioButton(JRadioButton stantardTemplateRadioButton) {
        this.stantardTemplateRadioButton = stantardTemplateRadioButton;
    }

    public JRadioButton getTender1RadioButton() {
        return tender1RadioButton;
    }

    public void setTender1RadioButton(JRadioButton tender1RadioButton) {
        this.tender1RadioButton = tender1RadioButton;
    }

    public JRadioButton getTender2RadioButton() {
        return tender2RadioButton;
    }

    public void setTender2RadioButton(JRadioButton tender2RadioButton) {
        this.tender2RadioButton = tender2RadioButton;
    }

    public JTextField getTxtRemote() {
        return txtRemote;
    }

    public void setTxtRemote(JTextField txtRemote) {
        this.txtRemote = txtRemote;
    }

    public JButton getBtnIniciar() {
        return btnIniciar;
    }

    public void setBtnIniciar(JButton btnIniciar) {
        this.btnIniciar = btnIniciar;
    }

    public JButton getBtnReset() {
        return btnReset;
    }

    public void setBtnReset(JButton btnReset) {
        this.btnReset = btnReset;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public JTextField getTxtEstado() {
        return txtEstado;
    }

    public void setTxtEstado(JTextField txtEstado) {
        this.txtEstado = txtEstado;
    }

    public JPanel getPanelp() {
        return panelp;
    }

    public void setPanelp(JPanel panelp) {
        this.panelp = panelp;
    }

    public JPanel getPanelTender() {
        return panelTender;
    }

    public void setPanelTender(JPanel panelTender) {
        this.panelTender = panelTender;
    }

    public JPanel getPanelRemote() {
        return panelRemote;
    }

    public void setPanelRemote(JPanel panelRemote) {
        this.panelRemote = panelRemote;
    }

    public JPanel getPanelBotones() {
        return panelBotones;
    }

    public void setPanelBotones(JPanel panelBotones) {
        this.panelBotones = panelBotones;
    }

    public JPanel getPanelEstado() {
        return panelEstado;
    }

    public void setPanelEstado(JPanel panelEstado) {
        this.panelEstado = panelEstado;
    }

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public void setBtnAceptar(JButton btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    public JButton getBtnDetalles() {
        return btnDetalles;
    }

    public void setBtnDetalles(JButton btnDetalles) {
        this.btnDetalles = btnDetalles;
    }

    public VentanaPrincipal() throws HeadlessException {


        setContentPane(panelPrincipal);
        setTitle("Web Monitor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setVisible(true);
       // setLocation(300, 800); //Pasar como parametro


    }



}