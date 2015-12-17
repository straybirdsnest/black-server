package org.team10424102.blackserver.services;

public interface TokenService {
    String generateToken(Object obj);
    Object getObjectFromToken(String token);
    boolean isTokenValid(String token);
}
