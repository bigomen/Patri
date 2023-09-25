package com.patri.plataforma.restapi.controller.v1;

import com.google.gson.Gson;
import com.patri.plataforma.restapi.constants.Constantes;
import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.controller.v1.doc.UsuarioControllerLoginDoc;
import com.patri.plataforma.restapi.jwt.JWTAuthentication;
import com.patri.plataforma.restapi.model.enums.TipoPermissao;
import com.patri.plataforma.restapi.service.UsuarioAssinanteService;
import com.patri.plataforma.restapi.service.UsuarioBackOfficeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@Tag(name = SwaggerConstantes.USUARIO_TAG)
@RestController
@RequestMapping("/usuarios/v1/")
public class UsuarioController implements UsuarioControllerLoginDoc
{
    protected final UsuarioAssinanteService usuarioAssinanteService;
    protected final UsuarioBackOfficeService usuarioBackOfficeService;
    
    @Autowired
    public UsuarioController(UsuarioAssinanteService usuarioAssinanteService,
                             UsuarioBackOfficeService usuarioBackOfficeService)
    {
        this.usuarioAssinanteService = usuarioAssinanteService;
        this.usuarioBackOfficeService = usuarioBackOfficeService;
    }

    @PatchMapping("{id}/{tipo}/{token}/novaSenha")
    public void salvarSenha(@PathVariable(name = "id") String id, @PathVariable(name = "tipo") TipoPermissao tipo, @PathVariable(name = "token") String token,
    		@RequestBody(required = true) String novaSenha)
    {
        if(tipo.equals(TipoPermissao.C))
        {
            usuarioAssinanteService.novaSenha(id, novaSenha);
        }
        else
        {
            usuarioBackOfficeService.novaSenha(id, token, novaSenha);
        }
    }
    
	@PostMapping(Constantes.REFRESH_TOKEN)
	public ResponseEntity<String> refreshToken()
	{
        HashMap<String, Object> map = new HashMap<String, Object>();

        JWTAuthentication authentication = (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
        map.put("name", authentication.getPrincipal().toString());
        map.put("grupo", authentication.getGrupo());
        Gson gson = new Gson();
        String json = gson.toJson(map);

        return new ResponseEntity<>(json, HttpStatus.OK);
	}
	
	@PostMapping("forgot")
	public void esqueceuSenha(@RequestBody String login)
	{
//		if(tipo.equals(TipoPermissao.C))
//        {
////            usuarioAssinanteService.esqueceuSenha(id);
//        }
//        else
//        {
            usuarioBackOfficeService.esqueceuSenha(login);
//        }
	}
}
