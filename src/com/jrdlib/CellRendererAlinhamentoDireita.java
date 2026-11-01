package com.jrdlib;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * CellRenderer para alinhamento de uma coluna a direita
 * @author Daniel
 */
public class CellRendererAlinhamentoDireita extends DefaultTableCellRenderer {
    @Override
    public void setHorizontalAlignment(int alignment) {
        super.setHorizontalAlignment(SwingConstants.RIGHT);
    }
}
