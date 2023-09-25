package com.patri.plataforma.restapi.model;

import java.util.List;
import com.patri.plataforma.restapi.model.enums.TipoPermissao;

public interface Usuario
{
    public String getNome();
    public String getSenha();
    public String getGrupoUsuario();
    public String getLogin();
    public Long getId();
    public Long getGrupoId();
    public List<String> getPermissoes();
    public TipoPermissao getTipoPermissaoAcesso();
}
