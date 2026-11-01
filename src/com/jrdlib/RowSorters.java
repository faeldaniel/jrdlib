package com.jrdlib;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.JTable;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Daniel
 */
public class RowSorters{
    
    /**
     * Permite que as colunas sejam ordenadas usando os valores padrões
     * fornecidas a jTable
     * @param table
     * @return 
     */
    public TableRowSorter rowSorterPadrao(JTable table){
        return new TableRowSorter(table.getModel());
    }
    
}
