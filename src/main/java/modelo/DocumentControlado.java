package modelo;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class DocumentControlado extends PlainDocument {
    //Longitud máxima introducida
    private int limit;
    //Debe contener solo numeros
    private boolean soloNumeros;


    public DocumentControlado(int limit, boolean soloNumeros) {
        this.limit = limit;
        this.soloNumeros = soloNumeros;
    }

    public DocumentControlado(Content c, int limit, boolean soloNumeros) {
        super(c);
        this.limit = limit;
        this.soloNumeros = soloNumeros;
    }

    @Override
    public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) throws BadLocationException {
        if (str == null) {
            return;
        }


        if (soloNumeros){

            // Verificar si el texto contiene solo números
            if (!str.matches("\\d+")) {
                return;
            }


        }


        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}