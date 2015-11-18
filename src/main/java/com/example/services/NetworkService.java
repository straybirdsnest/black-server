package com.example.services;

import java.net.InetAddress;

public interface NetworkService {
    String getLocationFromInetAddress(InetAddress address);
}
