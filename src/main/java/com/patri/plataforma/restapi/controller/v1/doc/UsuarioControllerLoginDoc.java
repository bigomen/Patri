package com.patri.plataforma.restapi.controller.v1.doc;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.model.enums.TipoPermissao;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

public interface UsuarioControllerLoginDoc
{
    @Operation
    (
        method = "PATCH",
        tags = SwaggerConstantes.USUARIO_TAG,
        summary = "Salva nova senha.",
        description = "Salva nova senha para um determinado usuário."
    )
    public void salvarSenha(String id, TipoPermissao tipo, String token, String novaSenha);

    @Operation
    (
        method = "POST",
        tags = SwaggerConstantes.USUARIO_TAG,
        summary = "Endpoint de refresh do token de login.",
        description = "Endpoint para renovar o token de autorização de um usuário. "
    )
    public ResponseEntity<String> refreshToken();

    @Operation
    (
        method = "POST",
        tags = SwaggerConstantes.USUARIO_TAG,
        summary = "Endpoint para recuperação de senha.",
        description = "Endpoint para recuperação de senha para um determinado usuário."
    )
    public void esqueceuSenha(String login);
}
