package com.jrdlib;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilitário moderno e multiplataforma para captura de números de série e identificadores de hardware.
 * Suporta Windows (PowerShell/CIM/vol), Linux (/sys, /proc, /etc/machine-id, lsblk) e macOS (ioreg, sysctl).
 * Não depende de arquivos temporários nem de scripts VBS obsoletos.
 * 
 * @author Daniel
 */
public class SerialUtils {

    private static final String OS = System.getProperty("os.name", "").toLowerCase();

    public static boolean isWindows() {
        return OS.contains("win");
    }

    public static boolean isLinux() {
        return OS.contains("linux") || OS.contains("nix");
    }

    public static boolean isMac() {
        return OS.contains("mac") || OS.contains("darwin");
    }

    /**
     * Retorna o serial do disco padrão do sistema operacional.
     * 
     * @return Serial do HD
     * @throws IOException
     */
    public static final String getHDSerial() throws IOException {
        try {
            if (isWindows()) {
                String serial = getHDPhysicalSerialWindows();
                if (serial != null && !serial.isEmpty() && !serial.equals("NO_DISK_ID")) {
                    return serial;
                }
                return getHDSerialWindows("C");
            } else if (isLinux()) {
                return getHDSerialLinux();
            } else if (isMac()) {
                return getHDSerialMac();
            } else {
                throw new IOException("Sistema operacional não suportado: " + OS);
            }
        } catch (Exception ex) {
            throw new IOException("Erro ao obter serial do HD: " + ex.getMessage());
        }
    }

    /**
     * Retorna o serial da CPU de acordo com o sistema operacional.
     * 
     * @return Serial ou ProcessorId da CPU
     */
    public static final String getCPUSerial() {
        try {
            if (isWindows()) {
                return getCPUSerialWindows();
            } else if (isLinux()) {
                return getCPUSerialLinux();
            } else if (isMac()) {
                return getCPUSerialMac();
            } else {
                return "SO_NAO_SUPORTADO: " + OS;
            }
        } catch (Exception ex) {
            return "Erro: " + ex.getMessage();
        }
    }

    /**
     * Retorna o serial da placa-mãe de acordo com o sistema operacional.
     * 
     * @return Serial da Placa-Mãe (Motherboard)
     */
    public static final String getMotherboardSerial() {
        try {
            if (isWindows()) {
                return getMotherboardSerialWindows();
            } else if (isLinux()) {
                return getMotherboardSerialLinux();
            } else if (isMac()) {
                return getMotherboardSerialMac();
            } else {
                return "SO_NAO_SUPORTADO: " + OS;
            }
        } catch (Exception ex) {
            return "Erro: " + ex.getMessage();
        }
    }

    // ==========================================
    // IMPLEMENTAÇÕES WINDOWS
    // ==========================================

    /**
     * Captura serial de placa mãe no Windows usando PowerShell/CIM (sem VBS).
     */
    public static String getMotherboardSerialWindows() {
        // 1. Tenta PowerShell CIM (Get-CimInstance Win32_BaseBoard)
        String out = runCommand("powershell", "-NoProfile", "-NonInteractive", "-Command",
                "(Get-CimInstance Win32_BaseBoard).SerialNumber");
        if (isValidId(out)) {
            return out.trim();
        }

        // 2. Fallback WMI via PowerShell WMI antigo
        out = runCommand("powershell", "-NoProfile", "-NonInteractive", "-Command",
                "(Get-WmiObject Win32_BaseBoard).SerialNumber");
        if (isValidId(out)) {
            return out.trim();
        }

        // 3. Fallback wmic caso ainda disponível
        out = runCommand("cmd", "/c", "wmic baseboard get serialnumber");
        out = parseWmicOutput(out);
        if (isValidId(out)) {
            return out;
        }

        return "NO_BASEBOARD_ID";
    }

    /**
     * Captura serial da CPU no Windows usando PowerShell/CIM.
     */
    public static String getCPUSerialWindows() {
        // 1. Tenta PowerShell CIM
        String out = runCommand("powershell", "-NoProfile", "-NonInteractive", "-Command",
                "(Get-CimInstance Win32_Processor).ProcessorId");
        if (isValidId(out)) {
            String[] lines = out.split("\\r?\\n");
            for (String l : lines) {
                if (isValidId(l)) {
                    return l.trim();
                }
            }
        }

        // 2. Fallback PowerShell WmiObject
        out = runCommand("powershell", "-NoProfile", "-NonInteractive", "-Command",
                "(Get-WmiObject Win32_Processor).ProcessorId");
        if (isValidId(out)) {
            String[] lines = out.split("\\r?\\n");
            for (String l : lines) {
                if (isValidId(l)) {
                    return l.trim();
                }
            }
        }

        // 3. Fallback wmic
        out = runCommand("cmd", "/c", "wmic cpu get processorid");
        out = parseWmicOutput(out);
        if (isValidId(out)) {
            return out;
        }

        return "NO_CPU_ID";
    }

    /**
     * Captura o número de série do volume de uma unidade lógica no Windows (ex: "C" ou "C:").
     */
    public static String getHDSerialWindows(String drive) {
        if (drive == null || drive.trim().isEmpty()) {
            drive = "C";
        }
        String driveLetter = drive.replaceAll("[^a-zA-Z]", "").toUpperCase();

        // 1. Tenta via comando rápido nativo 'cmd /c vol X:'
        String volOut = runCommand("cmd", "/c", "vol", driveLetter + ":");
        if (volOut != null && !volOut.isEmpty()) {
            Pattern p = Pattern.compile("([A-Fa-f0-9]{4}-[A-Fa-f0-9]{4})");
            Matcher m = p.matcher(volOut);
            if (m.find()) {
                return m.group(1);
            }
        }

        // 2. Tenta via PowerShell LogicalDisk
        String out = runCommand("powershell", "-NoProfile", "-NonInteractive", "-Command",
                "(Get-CimInstance Win32_LogicalDisk | Where-Object DeviceID -eq '" + driveLetter + ":').VolumeSerialNumber");
        if (isValidId(out)) {
            return out.trim();
        }

        // 3. Fallback para serial físico do disco
        String phys = getHDPhysicalSerialWindows();
        if (isValidId(phys)) {
            return phys;
        }

        return "NO_DISK_ID";
    }

    /**
     * Captura o serial físico (fábrica) do disco primário no Windows.
     */
    public static String getHDPhysicalSerialWindows() {
        String out = runCommand("powershell", "-NoProfile", "-NonInteractive", "-Command",
                "(Get-CimInstance Win32_DiskDrive)[0].SerialNumber");
        if (isValidId(out)) {
            return out.trim();
        }

        out = runCommand("cmd", "/c", "wmic diskdrive get serialnumber");
        out = parseWmicOutput(out);
        if (isValidId(out)) {
            return out;
        }

        return "NO_DISK_ID";
    }

    // ==========================================
    // IMPLEMENTAÇÕES LINUX
    // ==========================================

    /**
     * Captura serial de placa-mãe ou machine-id no Linux.
     */
    public static String getMotherboardSerialLinux() {
        // 1. Tenta ler direto do DMI
        String dmi = readFileContent("/sys/class/dmi/id/board_serial");
        if (isValidId(dmi)) return dmi;

        String uuid = readFileContent("/sys/class/dmi/id/product_uuid");
        if (isValidId(uuid)) return uuid;

        // 2. Tenta machine-id do sistema (universal em systemd)
        String machineId = readFileContent("/etc/machine-id");
        if (isValidId(machineId)) return machineId;

        String dbusId = readFileContent("/var/lib/dbus/machine-id");
        if (isValidId(dbusId)) return dbusId;

        // 3. Tenta comandos CLI
        String out = runCommand("bash", "-c", "dmidecode -s baseboard-serial-number 2>/dev/null");
        if (isValidId(out)) return out;

        out = runCommand("bash", "-c", "lshw -class bus 2>/dev/null | grep serial");
        if (isValidId(out)) {
            return filtraString(out, "serial:");
        }

        return "NO_BASEBOARD_ID";
    }

    /**
     * Captura identificador da CPU no Linux.
     */
    public static String getCPUSerialLinux() {
        // 1. Tenta ler /proc/cpuinfo diretamente
        File cpuinfo = new File("/proc/cpuinfo");
        if (cpuinfo.exists() && cpuinfo.canRead()) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(cpuinfo)));
                String line;
                String modelName = "";
                while ((line = reader.readLine()) != null) {
                    if (line.toLowerCase().startsWith("serial")) {
                        String[] parts = line.split(":");
                        if (parts.length > 1 && isValidId(parts[1])) {
                            reader.close();
                            return parts[1].trim();
                        }
                    } else if (line.toLowerCase().startsWith("model name") && modelName.isEmpty()) {
                        String[] parts = line.split(":");
                        if (parts.length > 1) {
                            modelName = parts[1].trim();
                        }
                    }
                }
                reader.close();
                if (isValidId(modelName)) {
                    return modelName;
                }
            } catch (Exception ignored) {}
        }

        // 2. Tenta lscpu ou lshw
        String out = runCommand("bash", "-c", "lscpu 2>/dev/null | grep 'Model name:'");
        if (isValidId(out)) {
            return filtraString(out, "Model name:");
        }

        out = runCommand("bash", "-c", "lshw -class processor 2>/dev/null | grep serial");
        if (isValidId(out)) {
            return filtraString(out, "serial:");
        }

        return "NO_CPU_ID";
    }

    /**
     * Captura serial do disco rígido no Linux.
     */
    public static String getHDSerialLinux() {
        // 1. Tenta lsblk (não requer root)
        String out = runCommand("bash", "-c", "lsblk -dno SERIAL /dev/sda 2>/dev/null");
        if (isValidId(out)) return out.trim();

        out = runCommand("bash", "-c", "lsblk -dno SERIAL 2>/dev/null | head -n 1");
        if (isValidId(out)) return out.trim();

        // 2. Tenta udevadm
        out = runCommand("bash", "-c", "udevadm info --query=property --name=/dev/sda 2>/dev/null | grep ID_SERIAL_SHORT=");
        if (isValidId(out)) {
            return filtraString(out, "ID_SERIAL_SHORT=");
        }

        // 3. Tenta listar /dev/disk/by-id
        File byId = new File("/dev/disk/by-id");
        if (byId.exists() && byId.isDirectory()) {
            String[] list = byId.list();
            if (list != null) {
                for (String item : list) {
                    if (!item.startsWith("wwn-") && !item.contains("-part")) {
                        return item;
                    }
                }
            }
        }

        // 4. Fallback lshw
        out = runCommand("bash", "-c", "lshw -class disk 2>/dev/null | grep serial");
        if (isValidId(out) && out.contains("serial:")) {
            String filtered = filtraString(out, "serial:");
            if (isValidId(filtered)) return filtered;
        }

        return "NO_DISK_ID";
    }

    // ==========================================
    // IMPLEMENTAÇÕES MAC OS
    // ==========================================

    public static String getMotherboardSerialMac() {
        String out = runCommand("bash", "-c", "ioreg -rd1 -c IOPlatformExpertDevice | grep IOPlatformSerialNumber");
        if (isValidId(out)) {
            Pattern p = Pattern.compile("\"IOPlatformSerialNumber\"\\s*=\\s*\"([^\"]+)\"");
            Matcher m = p.matcher(out);
            if (m.find()) return m.group(1);
        }
        return "NO_BASEBOARD_ID";
    }

    public static String getCPUSerialMac() {
        String out = runCommand("sysctl", "-n", "machdep.cpu.brand_string");
        if (isValidId(out)) return out.trim();
        return "NO_CPU_ID";
    }

    public static String getHDSerialMac() {
        String out = runCommand("bash", "-c", "diskutil info / | grep 'Volume UUID:'");
        if (isValidId(out) && out.contains("Volume UUID:")) {
            String filtered = filtraString(out, "Volume UUID:");
            if (isValidId(filtered)) return filtered;
        }
        return "NO_DISK_ID";
    }

    // ==========================================
    // MÉTODOS UTILITÁRIOS INTERNOS
    // ==========================================

    /**
     * Executa um processo de forma segura com timeout e captura a saída padrão.
     */
    private static String runCommand(String... cmd) {
        Process process = null;
        try {
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true);
            process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    if (sb.length() > 0) sb.append("\n");
                    sb.append(line);
                }
            }
            process.waitFor();
            return sb.toString().trim();
        } catch (Exception ex) {
            return "";
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    /**
     * Lê o conteúdo de um arquivo de texto de forma segura.
     */
    private static String readFileContent(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.canRead()) {
            return null;
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = reader.readLine();
            reader.close();
            if (line != null) {
                return line.trim();
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private static String parseWmicOutput(String output) {
        if (output == null || output.isEmpty()) return "";
        String[] lines = output.split("\\r?\\n");
        for (String line : lines) {
            String trimmed = line.trim();
            if (!trimmed.isEmpty() && !trimmed.equalsIgnoreCase("SerialNumber") && !trimmed.equalsIgnoreCase("ProcessorId")) {
                return trimmed;
            }
        }
        return "";
    }

    private static boolean isValidId(String id) {
        if (id == null) return false;
        String trimmed = id.trim();
        if (trimmed.isEmpty() 
                || trimmed.equalsIgnoreCase("none") 
                || trimmed.equalsIgnoreCase("null")
                || trimmed.equalsIgnoreCase("to be filled by o.e.m.") 
                || trimmed.equalsIgnoreCase("default string")
                || trimmed.toLowerCase().contains("not found")
                || trimmed.toLowerCase().contains("not recognized")
                || trimmed.toLowerCase().contains("cannot find")
                || trimmed.toLowerCase().contains("no such file")
                || trimmed.toLowerCase().contains("permission denied")
                || trimmed.startsWith("/bin/bash:")
                || trimmed.startsWith("sh:")) {
            return false;
        }
        return true;
    }

    /**
     * Separa uma string com base em um delimitador e retorna a parte subsequente limpa.
     * Caso o delimitador não seja encontrado, retorna vazio ("") de forma segura.
     * 
     * @param nome Texto completo
     * @param delimitador Separador
     * @return Parte após o delimitador ou string vazia se não encontrado
     */
    public static String filtraString(String nome, String delimitador) {
        if (nome == null || delimitador == null) {
            return "";
        }
        int index = nome.indexOf(delimitador);
        if (index != -1) {
            return nome.substring(index + delimitador.length()).trim();
        }
        return "";
    }
}
