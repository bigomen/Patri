package com.patri.plataforma.restapi.repository;

import com.patri.plataforma.restapi.model.Usuario;
import com.patri.plataforma.restapi.model.UsuarioBackOffice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UsuarioBackOfficeRepository extends CrudRepository<UsuarioBackOffice, Long>
{
    @Query(value = "select u from UsuarioBackOffice u where lower(u.nome) like lower(concat('%', :param, '%'))" +
            "or lower(u.sobrenome) like lower(concat('%', :param, '%')) or lower(u.login) like lower(concat('%', :param, '%'))" )
    public Collection<UsuarioBackOffice> listaUsuario(@Param("param") String param);

//    @Query(value = "select u from UsuarioBackOffice u join fetch u.unidadeNegocio un")
//    public Collection<UsuarioBackOffice> listaAllUsuario();
    
    @Query(value = "select u from UsuarioBackOffice u join fetch u.grupo g join fetch g.permissoes p where u.login = :login and u.ativo = :ativo and p.tipo = 'B'")
    public Optional<Usuario> findByLoginAndAtivo(@Param("login") String login, @Param("ativo") boolean ativo);
    
    public Optional<UsuarioBackOffice> findByLoginAndAtivoTrue(String login);

	public Optional<UsuarioBackOffice> findByIdAndAtivoFalse(Long id);
}
