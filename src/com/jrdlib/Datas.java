package com.jrdlib;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.joda.time.*;
import org.joda.time.format.*;

/**
 *
 * @author Rafael Daniel
 * @version 0.5
 *
 */
public class Datas {

    /**
     * Verifica se a data informada, é uma data válida
     * @param dateToValidate
     * @param patternDate
     * @return 
     */
    public boolean isValidDate(String dateToValidate, String patternDate) {
        try {
            DateTimeFormatter fmt = DateTimeFormat.forPattern(patternDate);
            fmt.parseDateTime(dateToValidate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtem a data atual no formato Data ou Hora usando uma Maskara. yyyy dia
     * MM mes dd ano HH hora Formarto 24 ou hh Hora formato 12 mm minutos ss
     * segundos
     *
     * @return java.util.Date Data ou Hora atual
     */
    public String DataOrHora(String Maskara) {
        return new SimpleDateFormat(Maskara).format(new Date());
    }

    /**
     * Converte Date em String com maskara desejada. yyyy dia MM mes dd ano HH
     * hora Formarto 24 ou hh Hora formato 12 mm minutos ss segundos
     *
     * @return String
     */
    public String FormartarDateEmString(Date dt, String Maskara) {
        String s = "";
        if (dt == null) {
            s = "";
        } else {
            DateFormat formatter;
            formatter = new SimpleDateFormat(Maskara);
            s = formatter.format(dt);
        }
        return s;
    }

    /**
     * Retorna a Data atual no formato Date
     *
     * @return Date
     */
    public Date DataTipoDate() {
        return new java.util.Date();
    }

    /**
     * Retorna a data/hora no formato Time
     *
     * @return java.sql.Timestamp
     */
    public java.sql.Timestamp SqlDataHoraAtualTimestamp() {
        return (new java.sql.Timestamp(new java.util.Date().getTime()));
    }

    /**
     * Retorna a data/hora no formato Date
     *
     * @return java.sql.Date
     */
    public java.sql.Date SqlDataHoraAtualDate() {
        return (new java.sql.Date(new java.util.Date().getTime()));
    }

    /**
     * Retorna a data/hora no formato Time
     *
     * @return java.sql.Time
     */
    public java.sql.Time SqlHoraAtualTime() {
        return (new java.sql.Time(new java.util.Date().getTime()));
    }

    /**
     * Obtem a Hora atual no formato SQL.
     *
     * @return java.sql.Time Hora atual
     * @deprecated use agora SqlHoraAtualTime()
     */
    public java.sql.Time SqlHoraAtual() {
        return (new java.sql.Time(new java.util.Date().getTime()));
    }

    /**
     * Obtem a data atual no formato SQL.
     *
     * @return java.sql.Date Data atual
     * @deprecated use agora SqlDataHoraAtualDate()
     */
    public java.sql.Date SqlDataAtual() {
        return (new java.sql.Date(new java.util.Date().getTime()));
    }

    /**
     * Converte uma Data Util em uma Data Sql
     *
     * @return java.sql.Date Data
     */
    public java.sql.Date ConverterUtilDateEmSqlDate(java.util.Date Data) {
        if (Data == null) {
            return null;
        } else {
            return (new java.sql.Date(Data.getTime()));
        }
    }

    /**
     * Soma N dias de uma data especificada
     *
     * @return java.util.Date Data
     */
    public Date SomeDias(Date date, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dias);
        return calendar.getTime();

    }

    /**
     * Soma N meses de uma data especificada
     *
     * @return java.util.Date Data
     */
    public Date SomeMeses(Date date, int mes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, mes);
        return calendar.getTime();
    }

    /**
     * Soma N dias e meses de uma data especificada
     *
     * @return java.util.Date Data
     */
    public Date SomeDiasMeses(Date date, int mes, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dias);
        calendar.add(Calendar.MONTH, mes);
        return calendar.getTime();
    }

    /**
     * Calcula o Periodo que passou entre duas datas por extenso
     *
     * @return String Periodo
     */
    public String DiasDecorridos(Date DataJaPassada, Date DataAtual) {

        DateTime start = new DateTime(DataJaPassada);
        DateTime end = new DateTime(DataAtual);
        Period per = new Period(start, end);
        PeriodFormatter pf = new PeriodFormatterBuilder().appendYears().appendSuffix(" ano, ", " anos, ").appendMonths().appendSuffix(" mês, ", " meses, ").appendWeeks().appendSuffix(" semana e ", " semanas e ").appendDays().appendSuffix(" dia ", " dias ").toFormatter();

        String dias = pf.print(per);

        return dias;
    }

    /**
     * Retorna o numero Dia da semana em Int * Usando biblioteca JodaTime
     *
     * @param Data
     * @return
     */
    public int DiaDaSemanaInt(Date Data) {
        DateTime data = new DateTime(Data);
        return data.getDayOfWeek();
    }

    /**
     * Retorna o Nome do dia da semana da data especificada Usando biblioteca
     * JodaTime
     *
     * @param Data
     * @return
     */
    public String DiaDaSemanaString(Date Data) {
        DateTime data = new DateTime(Data);
        return pesquisarDiaSemana(data.getDayOfWeek());
    }

    /**
     * Usado com auxilio no metodo acima
     *
     * @param _diaSemana
     * @return
     */
    private String pesquisarDiaSemana(int _diaSemana) {
        String diaSemana = null;

        switch (_diaSemana) {

            case 0: {
                diaSemana = "Domingo";
                break;
            }
            case 1: {
                diaSemana = "Segunda";
                break;
            }
            case 2: {
                diaSemana = "Terça";
            }
            case 3: {
                diaSemana = "Quarta";
                break;
            }
            case 4: {
                diaSemana = "Quinta";
                break;
            }
            case 5: {
                diaSemana = "Sexta";
                break;
            }
            case 6: {
                diaSemana = "Sábado";
                break;
            }

        }
        return diaSemana;

    }

    /**
     * Retorna um Date de uma data em String baseado na mascara escolhida
     *
     * @param Data
     * @return Date
     */
    public Date DataStringEmDate(String Data, String Maskara) {

        if (Data == null || Data.isEmpty() || Maskara == null || Maskara.isEmpty()) {
            return null;
        }

        Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat(Maskara);
            date = (java.util.Date) formatter.parse(Data);
        } catch (Exception ex) {
            return null;
        }

        return date;

    }

    /**
     * Retorna Bom dia, Boa Tarde ou Boa Noite baseado no horário da máquina
     *
     * @return Saudacao
     */
    public String saudacao() {

        Calendar calendar = Calendar.getInstance();
        int hourNow = calendar.get(Calendar.DAY_OF_MONTH);

        if ((hourNow >= 5) && (hourNow < 13)) {
            return "Bom dia";
        } else if ((hourNow >= 13) && (hourNow < 19)) {
            return "Boa tarde";
        } else if ((hourNow >= 19) && (hourNow < 24)) {
            return "Boa Noite";
        } else {
            return "Erro periodo não detectado";
        }
    }

    /**
     * Retorna uma data sendo no primeiro dia do mes da mesma
     *
     * @param Data
     * @return
     */
    public Date PrimeiroDiaMesData(Date Data) {
        if (Data == null) {
            return null;
        }
        DateTime data = new DateTime(Data);
        Calendar calendar = Calendar.getInstance();
        calendar.set(data.getYear(), data.getMonthOfYear() - 1, 1);
        return calendar.getTime();
    }

    /**
     * Retorna uma data sendo no ultimo dia do mes da mesma
     *
     * @param Data
     * @return
     */
    public Date UltimoDiaMesData(Date Data) {
        if (Data == null) {
            return null;
        }
        DateTime data = new DateTime(Data);
        Calendar calendar = Calendar.getInstance();
        calendar.set(data.getYear(), data.getMonthOfYear() - 1, 1);
        calendar.set(data.getYear(), data.getMonthOfYear() - 1, calendar.getActualMaximum(calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * Exibe em INT o intervalo de DIAS entre duas datas.
     *
     * @param dataInicial
     * @param dataFinal
     * @return
     */
    public int IntervaloDiasDuasDatas(Date dataInicial, Date dataFinal) {
        DateTime inicio = new DateTime(dataInicial);
        DateTime fim = new DateTime(dataFinal);
        return Days.daysBetween(inicio, fim).getDays() + 1;
    }

    /**
     * Exibe em INT o intervalo de MESES entre duas datas.
     *
     * @param dataInicial
     * @param dataFinal
     * @return
     */
    public int IntervaloMesesDuasDatas(Date dataInicial, Date dataFinal) {
        DateTime inicio = new DateTime(dataInicial);
        DateTime fim = new DateTime(dataFinal);

        return Months.monthsBetween(inicio, fim).getMonths() + 1;
    }

    /**
     * Exibe em INT o intervalo de ANOS entre duas datas.
     *
     * @param dataInicial
     * @param dataFinal
     * @return
     */
    public int IntervaloAnosDuasDatas(Date dataInicial, Date dataFinal) {
        DateTime inicio = new DateTime(dataInicial);
        DateTime fim = new DateTime(dataFinal);

        return Years.yearsBetween(inicio, fim).getYears() + 1;
    }

    /**
     * Some horas a partir de um periodo
     *
     * @param Horas
     * @param Minutos
     * @param Segundos
     * @return
     */
    public Date SomeHora(Date dataAtual, int Horas, int Minutos, int Segundos) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataAtual);
        calendar.add(Calendar.HOUR, Horas);
        calendar.add(Calendar.MINUTE, Minutos);
        calendar.add(Calendar.SECOND, Segundos);

        return calendar.getTime();
    }

    /**
     * Retorna o formato de uma maskara padrao
     *
     * @return String dd/MM/yyyy
     */
    public String MaskaraDataPadrao() {
        return "dd/MM/yyyy";
    }

    /**
     * Retorna o formato de uma maskara padrao
     *
     * @return String dd/MM/yyyy HH:mm:ss
     */
    public String MaskaraDataHoraPadrao() {
        return "dd/MM/yyyy HH:mm:ss";
    }

    /**
     * Retorna o formato de uma maskara SQL
     *
     * @return String yyyy-MM-dd
     */
    public String MaskaraDataSQL() {
        return "yyyy-MM-dd";
    }

    /**
     * Retorna o formato de uma maskara padrao
     *
     * @return String HH:mm:ss
     */
    public String MaskaraHoraPadrao() {
        return "HH:mm:ss";
    }

}
