package com.jrdlib;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FuncoesRede {

    public String GetMacAddress() throws SocketException, UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        NetworkInterface netInter = NetworkInterface.getByInetAddress(localHost);
        byte[] macAddressBytes = netInter.getHardwareAddress();

        String macAddress = String.format(
                "%1$02x-%2$02x-%3$02x-%4$02x-%5$02x-%6$02x",
                macAddressBytes[0], macAddressBytes[1], macAddressBytes[2],
                macAddressBytes[3], macAddressBytes[4], macAddressBytes[5])
                .toUpperCase();

        return macAddress;
    }

    public String GetIpAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public String getHostName() {
        String hostname = "HostnameError";
        try {

            InetAddress addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();

        } catch (Exception ex) {
            Logger.getLogger(FuncoesRede.class.getName()).log(Level.SEVERE, null, ex);
        }

        return hostname;

    }

}
