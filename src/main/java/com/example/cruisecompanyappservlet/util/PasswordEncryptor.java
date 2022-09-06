package com.example.cruisecompanyappservlet.util;


import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;

public class PasswordEncryptor {
    static public String getEncrypted(String password)  {
        Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(password.getBytes());
    }
    static public String getDecrypted(String encodedPassword){
        Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(encodedPassword));
    }

}
