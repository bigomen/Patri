package com.patri.plataforma.restapi.controller.v1;
//
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//import java.util.regex.Pattern;
//import javax.validation.Valid;
//import javax.validation.ValidationException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.env.Environment;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import com.patri.plataforma.restapi.base.BaseController;
//import com.patri.plataforma.restapi.base.BaseRestModel;
//import com.patri.plataforma.restapi.constants.Constantes;
//import com.patri.plataforma.restapi.jwt.AccountCredentials;
//import com.patri.plataforma.restapi.model.Perfil;
//import com.patri.plataforma.restapi.model.Usuario;
//import com.patri.plataforma.restapi.repository.PerfilRepository;
//import com.patri.plataforma.restapi.repository.UsuarioRepository;
//import com.patri.plataforma.restapi.restmodel.RestPerfil;
//import com.patri.plataforma.restapi.restmodel.RestUsuario;
//import com.patri.plataforma.restapi.utility.EnviaEmail;
//import com.patri.plataforma.restapi.utility.RestSecurity;
//
//@RestController
////@RequestMapping(value = "")
//public class UsuarioController extends BaseController {
//
//	@Autowired
//	private UsuarioRepository repository;
//
//	@Autowired
//	private PerfilRepository perfilRepository;
//
//	@Autowired
//	private EnviaEmail sendEmail;
//
//	@Autowired
//	private Environment env;
//
//	@Bean
//	public BCryptPasswordEncoder passwordEncoderUsuario() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Override
//	protected CrudRepository getRepository() {
//		return repository;
//	}
//
//	@Override
//	protected RestSecurity<Usuario, RestUsuario> getRestSecurity() {
//		return new RestSecurity<>(Usuario.class, RestUsuario.class, env);
//	}
//
//	protected RestSecurity<Perfil, RestPerfil> getRestSecurityPerfil() {
//		return new RestSecurity<>(Perfil.class, RestPerfil.class, env);
//	}
//
//
//	@Transactional
//	@GetMapping(value = "public/v1/usuario/checaEmail/{email}")
//	public ResponseEntity<HashMap> novo(@PathVariable String email) {
//		try {
//			Usuario usuCheck = repository.findByUsuLogin(email.toLowerCase());
//			HashMap<String, String> map = new HashMap();
//			if (usuCheck == null) {
//				map.put("disponivel", "S");
//				return new ResponseEntity<>(map, HttpStatus.OK);
//			}
//
//			map.put("disponivel", "N");
//			return new ResponseEntity<>(map, HttpStatus.OK);
//
//		} catch (Exception e) {
//			throw new ValidationException(e.getMessage());
//		}
//	}
//
//	@Transactional
//	@PostMapping(value = "empresa/v1/usuario/novo")
//	public ResponseEntity<String> novo(@Valid @RequestBody RestUsuario value) {
//		try {
//			ResponseEntity<String> ret;
//			ret = _novo(value, null, super.getIdEmpresa());
//			_resetSenha(value.getUsuLogin());
//			return ret;
//		} catch (Exception e) {
//			throw new ValidationException(e.getMessage());
//		}
//	}
//
//	private boolean validaComplexidade(String senha) {
//		Pattern ptUpperCase = Pattern.compile(".*[A-Z].*");
//		Pattern ptLowerCase = Pattern.compile(".*[a-z].*");
//		Pattern ptNumbers = Pattern.compile(".*\\d.*");
//
//		boolean hasUpperCase = ptUpperCase.matcher(senha).matches();
//		boolean hasLowerCase = ptLowerCase.matcher(senha).matches();
//		boolean hasNumbers = ptNumbers.matcher(senha).matches();
//		boolean hasLength = senha.length() > 7;
//
//		if (!hasLowerCase || !hasUpperCase || !hasNumbers || !hasLength) {
//			return false;
//		}
//		return true;
//	}
//
//	@Transactional
//	public ResponseEntity<String> _novo(RestUsuario value, String password, Long empId) {
//		try {
//			Usuario usuCheck = repository.findByUsuLogin(value.getUsuLogin());
//			if (usuCheck != null) {
//				throw new ValidationException("Email alredy in use by another user");
//			}
//
//			if (password != null && !validaComplexidade(password)) {
//				return new ResponseEntity<>("Senha deve ter letras maiúsculas, minúsculas e números",
//						HttpStatus.BAD_REQUEST);
//			}
//
//			if (value.getId() == null) {
//				Usuario usu = getRestSecurity().copyToDbObject(value);
//
//				usu.setEmpId(empId);
//				usu.setUsuRole(Constantes.ROLE_EMPRESA_ADMIN);
//				usu.setUsuLogin(usu.getUsuLogin().toLowerCase());
//
//
//				if (password != null) {
//					usu.setUsuSenha(passwordEncoderUsuario().encode(password));
//					usu.setUsuStatus(Constantes.USUARIO_PENDENTE);
//				} else {
//					usu.setUsuStatus(Constantes.USUARIO_ATIVO);
//				}
//				repository.save(usu);
//
//				return new ResponseEntity<>(getRestSecurity().encryptIdJson(usu.getId()), HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new ValidationException(e.getMessage());
//		}
//		throw new ValidationException("Object can not be created");
//	}
//
//	@Transactional
//	@PutMapping(value = "empresa/v1/usuario/atualiza")
//	public ResponseEntity<String> atualiza(@Valid @RequestBody RestUsuario value) {
//		try {
//			if (!Constantes.USUARIO_ATIVO.equals(value.getUsuStatus())
//					&& !Constantes.USUARIO_INATIVO.equals(value.getUsuStatus()))
//				throw new ValidationException("Invalid status");
//
//			/*
//			 * if (!Constantes.ROLE_EMPRESA_ADMIN.equals(value.getUsuRole()) &&
//			 * !Constantes.ROLE_EMPRESA_OPERADOR.equals(value.getUsuRole())) throw new
//			 * ValidationException("Invalid role");
//			 */
//
//			Long usuId = getRestSecurity().decryptId(value.getId());
//
//			Usuario usuCheck = repository.findByUsuLogin(value.getUsuLogin());
//			if (usuCheck != null && !usuCheck.getId().equals(usuId)) {
//				throw new ValidationException("Email alredy in use by another user");
//			}
//
//			if (value.getId() != null) {
//				Usuario usu = repository.findByIdAndEmpId(usuId, super.getIdEmpresa());
//				if (usu != null) {
//					usu.setUsuNome(value.getUsuNome());
//					usu.setUsuStatus(value.getUsuStatus());
//					usu.setUsuRole(Constantes.ROLE_EMPRESA_ADMIN);
//					usu.setGusId(getRestSecurity().decryptId(value.getGusId()));
//
//
//
//					repository.save(usu);
//					return new ResponseEntity<>(null, HttpStatus.OK);
//				}
//			}
//		} catch (Exception e) {
//			throw new ValidationException(e.getMessage());
//		}
//		throw new ValidationException("Object can not be updated");
//	}
//
//	@Transactional
//	@DeleteMapping(value = "empresa/v1/usuario/remove/{id}")
//	public ResponseEntity<String> remove(@PathVariable String id) {
//		try {
//			if (id != null) {
//				Usuario usu = repository.findByIdAndEmpId(getRestSecurity().decryptId(id), super.getIdEmpresa());
//				if (usu != null) {
//					repository.delete(usu);
//					return new ResponseEntity<>(null, HttpStatus.OK);
//				}
//			}
//		} catch (Exception e) {
//			throw new ValidationException(e.getMessage());
//		}
//		throw new ValidationException("Object can not be deleted");
//	}
//
//	@Transactional
//	@GetMapping(value = "empresa/v1/usuario/recupera/{id}")
//	public ResponseEntity<BaseRestModel> recupera(@PathVariable String id) {
//		try {
//			Usuario usu = repository.findByIdAndEmpId(getRestSecurity().decryptId(id), super.getIdEmpresa());
//			RestUsuario rUsu = getRestSecurity().copyToRestObject(usu);
//
//
//			return new ResponseEntity<>(rUsu, HttpStatus.OK);
//		} catch (Exception e) {
//			throw new ValidationException(e.getMessage());
//		}
//	}
//
//	@Transactional
//	@GetMapping(value = "empresa/v1/usuario/listaAtivos")
//	public ResponseEntity<Iterable<RestUsuario>> listaAtivos() {
//		try {
//			List<Usuario> itUsu = repository.findByEmpIdOrderByUsuNomeAsc(super.getIdEmpresa());
//			List<RestUsuario> itRestUsu = getRestSecurity().copyToRestObject(itUsu);
//
//			return new ResponseEntity<>(itRestUsu, HttpStatus.OK);
//		} catch (Exception e) {
//			throw new ValidationException(e.getMessage());
//		}
//	}
//
//	@Transactional
//	@GetMapping(value = "empresa/v1/usuario/perfil")
//	public ResponseEntity<Iterable<RestPerfil>> perfil() {
//		try {
//			Iterable<Perfil> itPer = perfilRepository.findAll();
//			List<RestPerfil> itRestPer = getRestSecurityPerfil().copyToRestObject(itPer);
//
//			return new ResponseEntity<>(itRestPer, HttpStatus.OK);
//		} catch (Exception e) {
//			throw new ValidationException(e.getMessage());
//		}
//	}
//
//	@Transactional
//	@PostMapping(value = "/forgot")
//	public ResponseEntity<HashMap> forgot(@Valid @RequestBody AccountCredentials value) {
//		String mensagem = _resetSenha(value.getUsername());
//		HashMap<String, String> map = new HashMap();
//		map.put("mensagem", mensagem);
//		return new ResponseEntity<>(map, HttpStatus.OK);
//	}
//
//	@Transactional
//	public Iterable<Usuario> _ativaTodos(Long empId) {
//		Iterable<Usuario> itUsu = repository.findByEmpIdOrderByUsuNomeAsc(empId);
//		for (Usuario usu : itUsu) {
//            usu.setUsuStatus(Constantes.USUARIO_ATIVO);
//            repository.save(usu);
//		}
//		return itUsu;
//	}
//
//	@Transactional
//	public Iterable<Usuario> _inativaTodos(Long empId) {
//		Iterable<Usuario> itUsu = repository.findByEmpIdOrderByUsuNomeAsc(empId);
//		for (Usuario usu : itUsu) {
//			if (usu.getUsuStatus().equals(Constantes.USUARIO_ATIVO)) {
//				usu.setUsuStatus(Constantes.USUARIO_INATIVO);
//				repository.save(usu);
//
//			}
//		}
//		return itUsu;
//	}
//
//	@Transactional
//	@PostMapping(value = "/reset/{uuid}")
//	public ResponseEntity<HashMap> resetSenha(@PathVariable String uuid, @Valid @RequestBody AccountCredentials value) {
//		HashMap<String, String> map = new HashMap();
//		try {
//			Usuario usu = repository.findByUsuResetToken(uuid);
//			if (usu != null && value.getPassword() != null) {
//
//				if (usu.getUsuResetExp().getTime() < Calendar.getInstance().getTimeInMillis()) {
//					usu.setUsuResetToken(null);
//					usu.setUsuResetExp(null);
//					map.put("mensagem", "Token expirado, solicite novamente a senha");
//					return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
//				}
//
//				if (!validaComplexidade(value.getPassword())) {
//					map.put("mensagem", "Senha deve ter letras maiúsculas, minúsculas e números");
//					return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
//				}
//
//				usu.setUsuResetToken(null);
//				usu.setUsuResetExp(null);
//
//				usu.setUsuSenha(passwordEncoderUsuario().encode(value.getPassword()));
//				repository.save(usu);
//
//				try {
//					EnviaEmail.Mensagem mensagem = sendEmail.novaMensagem();
//					mensagem.setAssunto(env.getProperty("email.senhaAtualizada.assunto"));
//					String corpo = env.getProperty("email.senhaAtualizada.corpo");
//					corpo = corpo.replaceAll("#usuario#", usu.getUsuNome());
//					mensagem.setMensagem(corpo);
//					mensagem.setEmailOrigem(env.getProperty("email.senhaAtualizada.emailOrigem"));
//					mensagem.setNomeOrigem(env.getProperty("email.senhaAtualizada.nomeOrigem"));
//					mensagem.setNomeDestinatario(usu.getUsuNome());
//					mensagem.setEmailDestinatario(usu.getUsuLogin());
//					sendEmail.enviar(mensagem);
//				} catch (Exception e) {
//				}
//
//				map.put("mensagem", "Senha atualizada com sucesso");
//				return new ResponseEntity<>(map, HttpStatus.OK);
//			}
//			map.put("mensagem", "Token inválido ou senha não informada");
//			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
//		} catch (Exception e) {
//			throw new ValidationException(e.getMessage());
//		}
//	}
//
//	@Transactional
//	@PostMapping(value = "empresa/v1/usuario/alterarSenha")
//	public ResponseEntity<HashMap> alterarSenha(@Valid @RequestBody AccountCredentials value) {
//		HashMap<String, String> map = new HashMap();
//		try {
//			Optional<Usuario> optUsu = repository.findById(super.getIdUsuario());
//			if (optUsu.isPresent() && value.getPassword() != null
//					&& passwordEncoderUsuario().matches(value.getPassword(), optUsu.get().getUsuSenha())) {
//
//                Usuario usu = optUsu.get();
//
//				Pattern ptUpperCase = Pattern.compile(".*[A-Z].*");
//				Pattern ptLowerCase = Pattern.compile(".*[a-z].*");
//				Pattern ptNumbers = Pattern.compile(".*\\d.*");
//
//				boolean hasUpperCase = ptUpperCase.matcher(value.getNewPassword()).matches();
//				boolean hasLowerCase = ptLowerCase.matcher(value.getNewPassword()).matches();
//				boolean hasNumbers = ptNumbers.matcher(value.getNewPassword()).matches();
//				boolean hasLength = value.getNewPassword().length() > 7;
//
//				if (!hasLowerCase || !hasUpperCase || !hasNumbers || !hasLength) {
//					map.put("mensagem", "Senha deve ter letras maiúsculas, minúsculas e números");
//					return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
//				}
//
//				usu.setUsuResetToken(null);
//				usu.setUsuResetExp(null);
//				usu.setUsuSenha(passwordEncoderUsuario().encode(value.getNewPassword()));
//				repository.save(usu);
//
//				map.put("mensagem", "Senha atualizada com sucesso");
//				return new ResponseEntity<>(map, HttpStatus.OK);
//			}
//			map.put("mensagem", "Senha inválida ou não informada");
//			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
//		} catch (Exception e) {
//			throw new ValidationException(e.getMessage());
//		}
//	}
//
//	private String _resetSenha(String usuLogin) {
//		Usuario usu = repository.findByUsuLogin(usuLogin.toLowerCase());
//		if (usu != null && Constantes.USUARIO_ATIVO.equals(usu.getUsuStatus())) {
//
//			Calendar cal = Calendar.getInstance();
//			if (usu.getUsuResetExp() == null || usu.getUsuResetExp().before(cal.getTime())) {
//				cal.add(Calendar.MINUTE, 30);
//				usu.setUsuResetToken(UUID.randomUUID().toString());
//				usu.setUsuResetExp(cal.getTime());
//				repository.save(usu);
//			}
//
//			try {
//				EnviaEmail.Mensagem mensagem = sendEmail.novaMensagem();
//
//				mensagem.setAssunto(env.getProperty("email.resetsenha.assunto"));
//
//				String corpo = env.getProperty("email.resetsenha.corpo");
//				corpo = corpo.replaceAll("#usuario#", usu.getUsuNome());
//				corpo = corpo.replaceAll("#token#", usu.getUsuResetToken());
//				mensagem.setMensagem(corpo);
//
//				mensagem.setEmailOrigem(env.getProperty("email.resetsenha.emailOrigem"));
//				mensagem.setNomeOrigem(env.getProperty("email.resetsenha.nomeOrigem"));
//
//				mensagem.setNomeDestinatario(usu.getUsuNome());
//				mensagem.setEmailDestinatario(usu.getUsuLogin());
//
//				sendEmail.enviar(mensagem);
//			} catch (Exception e) {
//			}
//
//			return "Token gerado com sucesso";
//		}
//		return "Usuário inválido";
//
//	}
//
//}

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.controller.v1.doc.UsuarioControllerDoc;
import com.patri.plataforma.restapi.restmodel.RestUsuarioAssinante;
import com.patri.plataforma.restapi.service.UsuarioAssinanteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/usuarioassinante/v1/")
@Validated
@Tag(name = SwaggerConstantes.USUARIOASSINANTE_TAG)
public class UsuarioAssinanteController implements UsuarioControllerDoc {

    private final UsuarioAssinanteService usuarioAssinanteService;

    @Autowired
    public UsuarioAssinanteController(UsuarioAssinanteService usuarioAssinanteService) {
        super();
        this.usuarioAssinanteService = usuarioAssinanteService;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("lista")
    public Collection<RestUsuarioAssinante> listar()
    {
        return usuarioAssinanteService.listar();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("pesquisar/{login}")
    public Collection<RestUsuarioAssinante> pesquisarPorLogin(@PathVariable(value = "login") String login)
    {
        return usuarioAssinanteService.pesquisarPorLogin(login);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("{id}")
    public RestUsuarioAssinante pesquisarPorId(@PathVariable String id) {
        return usuarioAssinanteService.obterPorId(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("pesquisar")
    public Collection<RestUsuarioAssinante> pesquisar(@RequestParam(value = "param") String param) {
        return usuarioAssinanteService.pesquisar(param);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping
    public void novo(@Valid @RequestBody RestUsuarioAssinante restUsuarioAssinante) {
         usuarioAssinanteService.novo(restUsuarioAssinante);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("{id}")
    public void alterar(@Valid @RequestBody RestUsuarioAssinante restUsuarioAssinante, @PathVariable String id) {
        usuarioAssinanteService.alterar(restUsuarioAssinante, id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable String id) {
        usuarioAssinanteService.deletar(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("{id}/{status}")
    public void ativarDesativar(@PathVariable String id, @PathVariable boolean status)
    {
        usuarioAssinanteService.alterar(id, status);
    }
}