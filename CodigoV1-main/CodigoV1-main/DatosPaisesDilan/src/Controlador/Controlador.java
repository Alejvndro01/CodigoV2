package Controlador;

import Modelo.Ciudad;
import Modelo.Conexion;
import Modelo.Idioma;
import Modelo.Pais;
import Vista.Ventana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Controlador implements ActionListener {

    Ventana vista = new Ventana();
    DefaultTableModel modelo = new DefaultTableModel();
    Pais p = new Pais();
    Ciudad c = new Ciudad();
    Idioma i = new Idioma();
    PaisOperaciones paisOp = new PaisOperaciones();
    CiudadOperaciones CiuOp = new CiudadOperaciones();
    IdiomaOperaciones IdioOP = new IdiomaOperaciones();
    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    Conexion conectar = new Conexion();
    

    public Controlador(Ventana v) {
        this.vista = v;
        //Btnes Pais
        this.vista.btnConsultar.addActionListener(this);
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.ComboContinente.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            filtrarPaisesPorContinente();
        }
    });
        //Btnes Ciudad
        this.vista.btnAgregarC.addActionListener(this);
        this.vista.btnConsultarC.addActionListener(this);
        this.vista.btnModificarC.addActionListener(this);
        this.vista.btnEliminarC.addActionListener(this);
        this.vista.btnGuardarC.addActionListener(this);
        this.vista.btnLimpiarC.addActionListener(this);
        //Btnes idioma
        this.vista.btnConsultarIdio.addActionListener(this);
        this.vista.btnAgregarIdio.addActionListener(this);
        this.vista.btnModificarIdio.addActionListener(this);
        this.vista.btnEliminarIdio.addActionListener(this);
        this.vista.btnGuardarIdio.addActionListener(this);
        this.vista.btnLimpiarIdio.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

//Ventana Pais
        if (e.getSource() == vista.btnAgregar) {
            agregar();
            vista.mostrarDatos();        
        }
        if (e.getSource() == vista.btnConsultar) {
            Consultar();
            
        }
        if (e.getSource() == vista.btnModificar) {
            Modificar();
            vista.mostrarDatos();
        }      
        if (e.getSource() == vista.btnGuardar) {
            Guardar();
            vista.mostrarDatos();
        }       
        if (e.getSource() == vista.btnEliminar) {
            Eliminar();
            vista.mostrarDatos();            
        }      
        if (e.getSource() == vista.btnLimpiar) {
            limpiar();
            vista.mostrarDatos(); 
        }
        
//Ventana Ciudad
        if (e.getSource() == vista.btnAgregarC) {
            agregarCiudad();
            vista.mostrarDatosC();
            limpiarC();
        }
        if (e.getSource() == vista.btnConsultarC) {
            ConsultarCiudad();
        }
        if (e.getSource() == vista.btnModificarC) {
            ModificarCiudad();
            vista.mostrarDatosC();
            limpiarC();
        }      
        if (e.getSource() == vista.btnGuardarC) {          
        }
        if (e.getSource() == vista.btnEliminarC) {
            EliminarCiudad();
            vista.mostrarDatosC();
            limpiarC();                     
        }        
        if (e.getSource() == vista.btnLimpiarC) {
            vista.mostrarDatosC();
            limpiarC();
        }        

//Ventana Idioma
        if (e.getSource() == vista.btnAgregarIdio) {
            agregarIdioma();
            vista.mostrarDatosIdio();
            limpiarIdio();
        } 
        if (e.getSource() == vista.btnConsultarIdio) {
            ConsultarIdioma();
            centrarCeldas(vista.jTableIdioma);
        }
        if (e.getSource() == vista.btnModificarIdio) {
            ModificarIdioma();
            vista.mostrarDatosIdio();
            limpiarIdio();
        }
        if (e.getSource() == vista.btnGuardarIdio) {
        }
        if (e.getSource() == vista.btnEliminarIdio) {
            EliminarIdioma();
            vista.mostrarDatosIdio();
            limpiarIdio();
        }
        if (e.getSource() == vista.btnLimpiarIdio) {
            limpiarIdio();
        }
    }

    public void limpiar() {
        vista.txtCodigoPais.setText("");
        vista.txtNombrePais.setText("");
        vista.txtPoblacionPais.setText("");
        vista.txtGob.setText("");
        vista.ComboContinente.setSelectedItem("Seleccione un Continente:");
        vista.txtCodigoPais.requestFocus();
}
    public void limpiarC() {
        vista.txtIDC.setText("");
        vista.txtNombreC.setText("");
        vista.txtPoblacionC.setText("");
        //vista.txtCodigoPC.setText("");
        vista.txtNombreC.requestFocus();
    }
    public void limpiarIdio() {
        vista.txtIDIdio.setText("");
        vista.txtNombreIdio.setText("");
        vista.txtOficialdio.setText("");
        vista.txtCodigoPIdio.setText("");
        vista.txtNombreIdio.requestFocus();
    }
    public void limpiarcodigo()
    {
        vista.txtCodigoPais.setText("");
    }
    public void centrarCeldas(JTable tabla) {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < vista.jTablePais.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }
    public void centrarCeldasC(JTable tabla) {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < vista.JTableCiudad.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }
    public void centrarCeldasIdio(JTable tabla) {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < vista.jTableIdioma.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }
    
   //Codigo de la venntana Pais
    public void agregar() {
    try {
        String codigo = vista.txtCodigoPais.getText();
        String nombre = vista.txtNombrePais.getText();
        String continente = String.valueOf(vista.ComboContinente.getSelectedItem());
        String poblacionStr = vista.txtPoblacionPais.getText(); 
        String tipoGob = vista.txtGob.getText();
        String region = vista.txtRegion.getText();
        String superficieStr = vista.txtSuperficie.getText();
        String añoIndependenciaStr = vista.txtAñoIndependencia.getText();
        String expectativaVidaStr = vista.txtExpectativaVida.getText();
        String pnbStr = vista.txtPnb.getText();
        String jefeEstado = vista.txtJefeEstado.getText();
        String ciudadCapitalStr = vista.txtCapital.getText();
        String idiomaPais = vista.txtIdioma.getText();
        
        int poblacion;
        float superficie;
        int añoIndependencia;
        float expectativaVida;
        float pnb;
        int ciudadCapital;
        int tipgob = 0;
            if ("Seleccione un Continente:".equals(continente)){
                JOptionPane.showMessageDialog(null, "Seleccione un continente", "Adverntencia", JOptionPane.ERROR_MESSAGE);
            }
            if (codigo.isEmpty() || nombre.isEmpty() || poblacionStr.isEmpty() || region.isEmpty() || superficieStr.isEmpty() 
                                 || añoIndependenciaStr.isEmpty() || expectativaVidaStr.isEmpty() || pnbStr.isEmpty() || jefeEstado.isEmpty() 
                                 || ciudadCapitalStr.isEmpty() || idiomaPais.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "No dejes campos vacíos.");
                return;
            }
            try{
                tipgob = Integer.parseInt(tipoGob);
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(vista, "Ingrese un gobierno valido 0-1.");
            }
            try {  
                poblacion = Integer.parseInt(poblacionStr); 
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Ingrese un numero valido.");
                return;
            }
            try {  
                superficie = Integer.parseInt(superficieStr); 
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Ingrese una superficie valida.");
                return;
            }
            try {  
                añoIndependencia = Integer.parseInt(añoIndependenciaStr); 
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Ingrese un año valido.");
                return;
            }
            try {  
                expectativaVida = Integer.parseInt(expectativaVidaStr); 
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Ingrese una expectativa de vida valido.");
                return;
            }
            try {  
                pnb = Integer.parseInt(pnbStr); 
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Ingrese un Producto Nacional valido.");
                return;
            }
            try {  
                ciudadCapital = Integer.parseInt(ciudadCapitalStr); 
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Ingrese una capital valida.");
                return;
            }
        p.setCodigoPais(codigo);
        p.setNombrePais(nombre);
        p.setContinentePais(continente);
        p.setPoblacionPais(poblacion);
        p.setTipoGobierno(tipgob);
        p.setRegion(region);
        p.setSuperficie(superficie);
        p.setAnioIndependencia(añoIndependencia);
        p.setExpectativaVida(expectativaVida);
        p.setPnb(pnb);
        p.setJefeEstado(jefeEstado);
        p.setCiudadCapital(poblacion);
        p.setIdiomaPais(idiomaPais);
 
        int r = paisOp.agregar(p);
        
        if (r == 1) {
            JOptionPane.showMessageDialog(vista, "País agregado con éxito.");
            limpiar();
            vista.mostrarDatos();
        } else {
            JOptionPane.showMessageDialog(vista, "Error al agregar el país.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(vista, "Ocurrió un error inesperado: " + e.getMessage());  
    }
}
    
    private void filtrarPaisesPorContinente() {
        String continenteSeleccionado = String.valueOf(vista.ComboContinente.getSelectedItem());

            if ("Seleccione un Continente:".equals(continenteSeleccionado)) {
                mostrarTodosLosPaises(); 
            } else {
                List<Pais> paises = paisOp.ConsultarPorContinente(continenteSeleccionado);
                actualizarTablaPaises(paises);
            }
}

    private void mostrarTodosLosPaises() {
        List<Pais> paises = paisOp.ConsultarPorContinente(""); 
        actualizarTablaPaises(paises);
    }

    private void actualizarTablaPaises(List<Pais> paises) {
        DefaultTableModel model = (DefaultTableModel) vista.jTablePais.getModel();
        model.setRowCount(0); 
            for (Pais pais : paises) {
                Object[] row = new Object[13];
                row[0] = pais.getCodigoPais();
                row[1] = pais.getNombrePais();
                row[2] = pais.getContinentePais();
                row[3] = pais.getPoblacionPais();
                row[4] = pais.getTipoGobierno();
                row[5] = pais.getRegion();
                row[6] = pais.getSuperficie();
                row[7] = pais.getAnioIndependencia();
                row[8] = pais.getExpectativaVida();
                row[9] = pais.getPnb();
                row[10] = pais.getJefeEstado();
                row[11] = pais.getCiudadCapital();
                row[12] = pais.getIdiomaPais();

                model.addRow(row);
            }
    }

    public void Consultar() {
    String nombre = vista.txtNombrePais.getText();
    if (nombre.isEmpty()) {
        JOptionPane.showMessageDialog(vista, "Por favor ingrese el nombre del país.", "ERROR", JOptionPane.ERROR_MESSAGE);
        return;
    }
    List<Pais> paises = paisOp.Consultar(nombre);
    DefaultTableModel model = (DefaultTableModel) vista.jTablePais.getModel();
    model.setRowCount(0); // Limpia la tabla antes de agregar nuevos resultados

    if (paises.isEmpty()) {
        JOptionPane.showMessageDialog(vista, "No se encontraron países con ese nombre.", "INFO", JOptionPane.INFORMATION_MESSAGE);
    } else {
        for (Pais pais : paises) {
            Object[] row = new Object[13]; 
            row[0] = pais.getCodigoPais();
            row[1] = pais.getNombrePais();
            row[2] = pais.getContinentePais();
            row[3] = pais.getPoblacionPais();
            row[4] = pais.getTipoGobierno();
            row[5] = pais.getRegion();
            row[6] = pais.getSuperficie();
            row[7] = pais.getAnioIndependencia();
            row[8] = pais.getExpectativaVida();
            row[9] = pais.getPnb();
            row[10] = pais.getJefeEstado();
            row[11] = pais.getCiudadCapital();
            row[12] = pais.getIdiomaPais();

            model.addRow(row); 
        }
    }
}

    public void Modificar() {
    int selectedRow = vista.jTablePais.getSelectedRow();
    if (selectedRow != -1) { 
        String codigo = vista.jTablePais.getValueAt(selectedRow, 0).toString();
        List<Pais> paises = paisOp.Consultar(codigo);
        if (!paises.isEmpty()) {
            Pais pais = paises.get(0);
            
            vista.txtCodigoPais.setText(pais.getCodigoPais());
            vista.txtNombrePais.setText(pais.getNombrePais());
            vista.txtPoblacionPais.setText(String.valueOf(pais.getPoblacionPais()));
            vista.txtGob.setText(String.valueOf(pais.getTipoGobierno()));
            vista.ComboContinente.setSelectedItem(pais.getContinentePais());
        }
    } else {
        JOptionPane.showMessageDialog(vista, "Por favor selecciona un país de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}
    
    public void Guardar(){
        String codigo = vista.txtCodigoPais.getText();
        String nombre = vista.txtNombrePais.getText();
        String continente = String.valueOf(vista.ComboContinente.getSelectedItem());
        String poblacionStr = vista.txtPoblacionPais.getText();
        String tipoGob = vista.txtGob.getText();


        if (codigo.isEmpty() || nombre.isEmpty() || continente.isEmpty() || poblacionStr.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Todos los campos deben ser llenados.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int poblacion;
        int tipGob = 0;
        try {

            tipGob = Integer.parseInt(tipoGob);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Ingrese un gobierno válido (0 o 1).", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {

            poblacion = Integer.parseInt(poblacionStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "La población debe ser un número válido.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }


        Pais p = new Pais();
        p.setCodigoPais(codigo);
        p.setNombrePais(nombre);
        p.setContinentePais(continente);
        p.setPoblacionPais(poblacion);
        p.setTipoGobierno(tipGob);

        int r = paisOp.modificar(p);
        if (r == 1) {
            JOptionPane.showMessageDialog(vista, "País modificado con éxito.");
            limpiar(); 
            vista.mostrarDatos(); 
        } else {
            JOptionPane.showMessageDialog(vista, "Error al modificar el país.");
        } 
    }   
    
    public void Eliminar() {
    try {
        String codigo = vista.txtCodigoPais.getText(); 

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese un código de país.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int idiomasEliminados = IdioOP.eliminarPorCodigoPais(codigo);
        if (idiomasEliminados > 0) {
            JOptionPane.showMessageDialog(vista, "Se eliminaron " + idiomasEliminados + " idiomas asociados.");
        }
     
        int ciudadesEliminadas = CiuOp.eliminarPorCodigoPais(codigo);
        if (ciudadesEliminadas > 0) {
            JOptionPane.showMessageDialog(vista, "Se eliminaron " + ciudadesEliminadas + " ciudades asociadas.");
        }

        int r = paisOp.eliminar(codigo);
        if (r == 1) {
            JOptionPane.showMessageDialog(vista, "País eliminado con éxito.");
            limpiar(); 
            vista.mostrarDatos();
        } else {
            JOptionPane.showMessageDialog(vista, "Error al eliminar el país.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(vista, "Error: " + e.getMessage());
    }
}
    
    //Codigo de la venntana Ciudad
    public void agregarCiudad() {
    try {
        String nombreC = vista.txtNombreC.getText();
        String codigoPC = vista.txtCodigoPC.getText();
        String poblacionC = vista.txtPoblacionC.getText();
        String distrito = "Nombre del distrito";
        int poblacion;
        
        if (codigoPC.isEmpty() || nombreC.isEmpty() ) {
            JOptionPane.showMessageDialog(vista, "No dejes campos vacíos.");
            return;
        }
        try {  
            poblacion = Integer.parseInt(poblacionC); 
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Ingrese un numero valido.");
            return;
        }

        c.setNombreCiudad(nombreC);
        c.setPoblacionCiudad(poblacion);
        c.setCodigoPais(codigoPC);
 
        Ciudad ciudad = new Ciudad(poblacion, nombreC, poblacion, codigoPC, distrito);
        int r = CiuOp.agregarCiudad(ciudad);
        if (r == 1) {
            JOptionPane.showMessageDialog(vista, "Ciudad agregada con éxito.");
            limpiarC();
        } else {
            JOptionPane.showMessageDialog(vista, "Error al agregar la ciudad.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(vista, "Ocurrió un error inesperado: " + e.getMessage());
        
    }
}
    
    public void ModificarCiudad() {
    try {
        String idC = vista.txtIDC.getText();
        String nombreC = vista.txtNombreC.getText();
        String codigoPC = vista.txtCodigoPC.getText();
        String poblacionC = vista.txtPoblacionC.getText();
        int poblacion;

        if (idC.isEmpty() || codigoPC.isEmpty() || nombreC.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No dejes campos vacíos.");
            return;
        }
        try {
            poblacion = Integer.parseInt(poblacionC);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Ingrese un número válido.");
            return;
        }

        c.setIdCiudad(Integer.parseInt(idC)); 
        c.setNombreCiudad(nombreC);
        c.setPoblacionCiudad(poblacion);
        c.setCodigoPais(codigoPC);

        int r = CiuOp.modificarCiudad(c);
        if (r == 1) {
            JOptionPane.showMessageDialog(vista, "Ciudad modificada con éxito.");
            limpiarC();
        } else {
            JOptionPane.showMessageDialog(vista, "Error al modificar la ciudad.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(vista, "Ocurrió un error inesperado: " + e.getMessage());
    }
}
    public void EliminarCiudad(){
        try{
            String Nombre = vista.txtNombreC.getText();
            Pais p = new Pais();
            
            if(Nombre.isEmpty()){
                JOptionPane.showMessageDialog(vista, "Ingrese un nombre","ERROR", JOptionPane.ERROR_MESSAGE);  
            }
            else
            {
        
        int r = CiuOp.eliminarCiudad(Nombre);
        if (r == 1) {
            JOptionPane.showMessageDialog(vista, "Ciudad eliminada con éxito.");
            limpiarC();
        } else {
            JOptionPane.showMessageDialog(vista, "Error al eliminar la ciudad.");
        }
            }

        }catch(Exception e){
                JOptionPane.showMessageDialog(vista, "Error: " +e );
        }
    }
    
    public void ConsultarCiudad() {
        
         String nombre = vista.txtNombreC.getText();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor ingrese el nombre de la ciudad.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            List<Ciudad> ciudades = CiuOp.ConsultarCiudad(nombre);
            DefaultTableModel modelC = (DefaultTableModel) vista.JTableCiudad.getModel();
            modelC.setRowCount(0);
                    
             if (ciudades.isEmpty()) {         
            } else {
                for (Ciudad ciudad : ciudades){
                    Object[] row = new Object[5];
                    row[0] = ciudad.getIdCiudad();
                    row[1] = ciudad.getNombreCiudad();
                    row[2] = ciudad.getPoblacionCiudad();
                    row[3] = ciudad.getCodigoPais();
                    row[4] = ciudad.getDistrito();
                    
                    modelC.addRow(row);
                }
            }
    }
    
    //Codigo de la venntana Idioma
    public void agregarIdioma() {
    try { 
        String nombreidio = vista.txtNombreIdio.getText();
        String oficial = vista.txtOficialdio.getText();
        String codigoidio = vista.txtCodigoPIdio.getText();
        int oficialidio = 0;
        
        if (codigoidio.isEmpty() || nombreidio.isEmpty() ) {
            JOptionPane.showMessageDialog(vista, "No dejes campos vacíos.");
            return;
        }

        try {  
            oficialidio = Integer.parseInt(oficial); 
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Ingrese un numero valido.");
            return;
        }

        i.setNombreIdioma(nombreidio);
        i.setOficial(oficialidio);
        i.setCodigoPais(codigoidio);
        
        int r = IdioOP.agregarIdioma(i);
        if (r == 1) {
            JOptionPane.showMessageDialog(vista, "Idioma agregado con éxito.");
            
        } else {
            JOptionPane.showMessageDialog(vista, "Error al agregar el idioma.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(vista, "Ocurrió un error inesperado: " + e.getMessage());
        
    }
}
    
    public void ModificarIdioma() {
    try {
        String idIdio = vista.txtIDIdio.getText();
        String nombreIdio = vista.txtNombreIdio.getText();
        String oficial = vista.txtOficialdio.getText();
        String codigoIdio = vista.txtCodigoPIdio.getText();
        int oficialIdio = 0;

        if (idIdio.isEmpty() || codigoIdio.isEmpty() || nombreIdio.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No dejes campos vacíos.");
            return;
        }
        try {
            oficialIdio = Integer.parseInt(oficial);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Ingrese un número válido.");
            return;
        }

        i.setIdIdioma(Integer.parseInt(idIdio)); 
        i.setNombreIdioma(nombreIdio);
        i.setOficial(oficialIdio);
        i.setCodigoPais(codigoIdio);

        int r = IdioOP.modificarIdioma(i);
        if (r == 1) {
            JOptionPane.showMessageDialog(vista, "Idioma modificado con éxito.");
            limpiarIdio(); 
        } else {
            JOptionPane.showMessageDialog(vista, "Error al modificar el idioma.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(vista, "Ocurrió un error inesperado: " + e.getMessage());
    }
}
    
    public void EliminarIdioma(){
        try{
            String Nombre = vista.txtNombreIdio.getText();

            if(Nombre.isEmpty()){
                JOptionPane.showMessageDialog(vista, "Ingrese un nombre","ERROR", JOptionPane.ERROR_MESSAGE);  
            }
            else
            {
        
        int r = IdioOP.eliminarIdioma(Nombre);
        if (r == 1) {
            JOptionPane.showMessageDialog(vista, "Idioma eliminado con éxito.");
            
        } else {
            JOptionPane.showMessageDialog(vista, "Error al eliminar el idioma.");
        }
            }

        }catch(Exception e){
                JOptionPane.showMessageDialog(vista, "Error: " +e );
        }
    }
    
    public void ConsultarIdioma() {
            String nombre = vista.txtNombreIdio.getText();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor ingrese el nombre del idioma.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            List<Idioma> idiomas = IdioOP.ConsultarIdioma(nombre);
            DefaultTableModel modelC = (DefaultTableModel) vista.jTableIdioma.getModel();
            modelC.setRowCount(0);
                    
             if (idiomas.isEmpty()) {
                
            } else {
                for (Idioma idioma : idiomas){
                    Object[] row = new Object[4];
                    row[0] = idioma.getIdIdioma();
                    row[1] = idioma.getNombreIdioma();
                    row[2] = idioma.getOficial();
                    row[3] = idioma.getCodigoPais();
                    
                    modelC.addRow(row);
                }
            }
    }
}   
    
        
        

    