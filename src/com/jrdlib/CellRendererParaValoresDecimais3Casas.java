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
 * CellRenderer para ser usado em valores Decimais com 3 Casas decimais
 * OBS: Usar valores Double
 * @author Daniel
 */
public class CellRendererParaValoresDecimais3Casas extends DefaultTableCellRenderer {

    @Override
    public void setHorizontalAlignment(int alignment) {
        super.setHorizontalAlignment(SwingConstants.RIGHT);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // First format the cell value as required
        try {
            if (value != null) {
                value = new Tratamentos().ValorDoubleParaString(Double.parseDouble(value.toString()), 3, 3);
            }
        } catch (Exception ex) {
        }
        
        // And pass it on to parent class
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
