package com.example.experimental;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Enumeration;

/**
 * Created by yy on 9/12/15.
 */
public class NetworkUtilsTest {
    @Test
    public void howToGetLanIp() throws Exception {
        System.out.println("Your Host addr: " + InetAddress.getLocalHost().getHostAddress());
        // often returns "127.0.0.1"
        // Yang Yang：我这里返回的是我本机的局域网 IP
        Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
        for (; n.hasMoreElements(); ) {
            NetworkInterface e = n.nextElement();

            Enumeration<InetAddress> a = e.getInetAddresses();
            for (; a.hasMoreElements(); ) {
                InetAddress addr = a.nextElement();
                if (addr instanceof Inet4Address) {
                    System.out.println("  " + e.getDisplayName() + " " + addr.getHostAddress());
                }
            }
        }
    }

    @Test
    public void howToGetWanIp() throws Exception {
        //URL whatismyip = new URL("http://checkip.amazonaws.com");
        URL whatismyip = new URL("http://icanhazip.com/");
        BufferedReader in = new BufferedReader(new InputStreamReader(
                whatismyip.openStream()));

        String ip = in.readLine(); //you get the IP as a String
        System.out.println(ip);
    }
}
