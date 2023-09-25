/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.patri.plataforma.restapi.restmodel;

import java.io.Serializable;

/**
 *
 * @author rcerqueira
 */

public class RestEmpresa  extends BaseRestModel implements Serializable {

    private String empRazaoSocial;
    private String empNomeFantasia;
    private String empCNPJMatriz;
    private Long logId;
    private String empNumeroMatriz;
    private String empComplementoMatriz;
    private String empDDDMatriz;
    private String empNumTelefoneMatriz;
    private String empStatus;

    // Para registro de nova empresa, precisamos criar o usuario de acesso tambem
    private String usuNome;
    private String usuLogin;
    private String usuSenha;
    
    // Para devolver os dados de endereco completo da empresa
    private String logTipo;
    private String logNome;
    private String logBairro;
    private String logCidade;
    private String logUf;

    public String getEmpRazaoSocial() {
        return empRazaoSocial;
    }

    public void setEmpRazaoSocial(String empRazaoSocial) {
        this.empRazaoSocial = empRazaoSocial;
    }

    public String getEmpNomeFantasia() {
        return empNomeFantasia;
    }

    public void setEmpNomeFantasia(String empNomeFantasia) {
        this.empNomeFantasia = empNomeFantasia;
    }

    public String getEmpCNPJMatriz() {
        return empCNPJMatriz;
    }

    public void setEmpCNPJMatriz(String empCNPJMatriz) {
        this.empCNPJMatriz = empCNPJMatriz;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getEmpNumeroMatriz() {
        return empNumeroMatriz;
    }

    public void setEmpNumeroMatriz(String empNumeroMatriz) {
        this.empNumeroMatriz = empNumeroMatriz;
    }

    public String getEmpComplementoMatriz() {
        return empComplementoMatriz;
    }

    public void setEmpComplementoMatriz(String empComplementoMatriz) {
        this.empComplementoMatriz = empComplementoMatriz;
    }

    public String getEmpDDDMatriz() {
        return empDDDMatriz;
    }

    public void setEmpDDDMatriz(String empDDDMatriz) {
        this.empDDDMatriz = empDDDMatriz;
    }

    public String getEmpNumTelefoneMatriz() {
        return empNumTelefoneMatriz;
    }

    public void setEmpNumTelefoneMatriz(String empNumTelefoneMatriz) {
        this.empNumTelefoneMatriz = empNumTelefoneMatriz;
    }

    public String getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(String empStatus) {
        this.empStatus = empStatus;
    }

    public String getUsuNome() {
        return usuNome;
    }

    public void setUsuNome(String usuNome) {
        this.usuNome = usuNome;
    }

    public String getUsuLogin() {
        return usuLogin;
    }

    public void setUsuLogin(String usuLogin) {
        this.usuLogin = usuLogin;
    }

    public String getUsuSenha() {
        return usuSenha;
    }

    public void setUsuSenha(String usuSenha) {
        this.usuSenha = usuSenha;
    }

    public String getLogTipo() {
        return logTipo;
    }

    public void setLogTipo(String logTipo) {
        this.logTipo = logTipo;
    }

    public String getLogNome() {
        return logNome;
    }

    public void setLogNome(String logNome) {
        this.logNome = logNome;
    }

    public String getLogBairro() {
        return logBairro;
    }

    public void setLogBairro(String logBairro) {
        this.logBairro = logBairro;
    }

    public String getLogCidade() {
        return logCidade;
    }

    public void setLogCidade(String logCidade) {
        this.logCidade = logCidade;
    }

    public String getLogUf() {
        return logUf;
    }

    public void setLogUf(String logUf) {
        this.logUf = logUf;
    }
    
    
   

    
    
}
