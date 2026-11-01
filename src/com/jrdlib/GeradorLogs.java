package com.jrdlib;

import java.io.FileOutputStream;
import java.io.IOException;

public class GeradorLogs {

    private Datas objDatas = new Datas();
    private FileOutputStream fos;
    private static String CaminhoArquivoLog = "LogSistema.txt";

    public void InserindoLog(String InserirLog) {
        try {
            String TEXTO = objDatas.DataOrHora("dd/MM/yyyy HH:mm:ss ") + "// " + InserirLog + System.getProperty("line.separator");
            fos = new FileOutputStream(CaminhoArquivoLog, true);
            fos.write(TEXTO.getBytes());
            fos.close();
        } catch (IOException ex) {
        }
    }

    /**
     * Especifica o caminho completo onde o arquivo log ficará,
     * caso nao especificado receberar o nome padrao LogSistema.txt
     * @param NomeArquivoLog
     */
    public void setNomeArquivoLog(String NomeArquivoLog){
        CaminhoArquivoLog = NomeArquivoLog;
    }
}
