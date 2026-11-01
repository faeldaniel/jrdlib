package com.jrdlib;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * CCellRenderer para alinhamento de uma coluna ao centro
 * @author Daniel
 */
public class CellRendererAlinhamentoCentro extends DefaultTableCellRenderer{

     @Override
    public void setHorizontalAlignment(int alignment) {
        super.setHorizontalAlignment(SwingConstants.CENTER);
    }

}
