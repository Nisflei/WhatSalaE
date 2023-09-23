package com.aulas.whatsalae.tools;

import java.util.Base64;

public class Base64custom {
    public static String condificar(String texto){
        return Base64.getEncoder().encodeToString(texto.getBytes()).replaceAll("\\n|\\r","");
    }

    public static String decodificar(String texto){
        return new String(Base64.getDecoder().decode(texto));
    }


}
