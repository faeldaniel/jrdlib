package com.jrdlib;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;

/**
 *
 * @author Daniel
 */
public class SituacoesPadroes {

    public int Existe(){
        int situacao = 0;
        return situacao;
    }

    public Color CorExiste(){
        return Color.red;
    }

    public int NaoExiste(){
        int situacao = 1;
        return situacao;
    }

    public Color CorNaoExiste(){
        return new java.awt.Color(0, 102, 0);
    }

     public int EmBranco(){
        int situacao = 2;
        return situacao;
    }

     public Color CorEmBranco(){
        return Color.WHITE;
    }

      public int Incorreto(){
        int situacao = 3;
        return situacao;
    }

       public Color CorIncorreto(){
        return new java.awt.Color(204, 102, 0);
    }

}
