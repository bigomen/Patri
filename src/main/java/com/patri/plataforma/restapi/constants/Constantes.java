/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.patri.plataforma.restapi.constants;

/**
 *
 * @author rcerqueira
 */
public class Constantes {
    public static final String ANONYMOUS_USER = "anonymous_user000";

    public static final String PATTERN_TEXTO_SEM_CARACTERES_ESPECIAIS = "^([a-zA-Zà-üÀ-Ú0-9]|\\s){0,}+$";
    
    public static final String PATTERN_SENHA = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#%-_?!|<=>+,:;\\s()\\[\\]{}\\./\\\\~^])[0-9a-zA-Z$*&@#%-_?!|<=>+,:;\\s()\\[\\]{}\\./\\\\~^]{8,}$";

    public static final String REFRESH_TOKEN = "refresh";
    
}


//\ (33 caracteres)

