package com.jrdlib;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * CellRenderer para ser usado em valores Monterios
 * OBS: Usar valores Double
 * @author Daniel
 */
public class CellRendererParaValoresMonetario extends DefaultTableCellRenderer {

    @Override
    public void setHorizontalAlignment(int alignment) {
        super.setHorizontalAlignment(SwingConstants.RIGHT);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // First format the cell value as required
        try {
            if (value != null) {
                value = new Tratamentos().ValorDoubleParaStringMonetario(Double.parseDouble(value.toString()));
            }
        } catch (Exception ex) {
        }
        
        // And pass it on to parent class
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
