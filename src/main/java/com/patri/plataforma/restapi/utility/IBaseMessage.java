/*
Copyright Luxfacta Solucoes de TI Ltda
 */
package com.patri.plataforma.restapi.utility;

/**
 *
 * @author rcerqueira
 */

@Deprecated
public interface IBaseMessage {
    
    public String getMessage();

    public String getStatus();

    public boolean getError();

}
