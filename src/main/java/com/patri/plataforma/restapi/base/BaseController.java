package com.patri.plataforma.restapi.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ValidationException;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;

//import com.patri.plataforma.restapi.jwt.JWTAuthentication;
import com.patri.plataforma.restapi.model.BaseModel;
import com.patri.plataforma.restapi.restmodel.BaseRestModel;
import com.patri.plataforma.restapi.utility.RestSecurity;

public abstract class BaseController<D extends BaseModel, R extends BaseRestModel>  {
 
    protected abstract CrudRepository getRepository();
    protected abstract RestSecurity<D,R> getRestSecurity();



//    protected Long getIdEmpresa() {
//        JWTAuthentication jwtAuth = (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
//        return jwtAuth.getIdEmpresa();
//    }
//
//    protected Long getIdUsuario() {
//        JWTAuthentication jwtAuth = (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
//        return jwtAuth.getIdUsuario();
//    }
    
    /*
    protected String getRoleUsuario() {
        JWTAuthentication jwtAuth = (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
        if (jwtAuth.getAuthorities().iterator().hasNext())
            return jwtAuth.getAuthorities().iterator().next().getAuthority();
        return null;
    }
    */

//    protected String getLoginUsuario() {
//        JWTAuthentication jwtAuth = (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
//        return jwtAuth.getPrincipal().toString();
//    }
    
    //@GetMapping(value = "object")
    protected ResponseEntity<Iterable<R>> getAll() {
        try {
            Iterable<D> modAr = getRepository().findAll();
            List<D> result = new ArrayList<D>();
            modAr.forEach(result::add);
                    
;            return new ResponseEntity<>(getRestSecurity().copyToRestObject(result), HttpStatus.OK);
        } catch (Exception e) {
        }
        throw new ValidationException("Object can not be found");
    }

    //@GetMapping(value = "object/count")
    protected ResponseEntity<Long> getCount() {
        return new ResponseEntity<>(getRepository().count(), HttpStatus.OK);
    }

    //@GetMapping(value="object/{id}")
    protected ResponseEntity<R> getById(String idCrypt){
        try {
            Long id = getRestSecurity().decryptId(idCrypt);
            Optional<D> entity = getRepository().findById(id);
            if (entity.isPresent()) {
                R restModel = (R) getRestSecurity().copyToRestObject(entity.get());
                return new ResponseEntity<>(restModel, HttpStatus.OK);
            }
        } catch (Exception e) {
            
        }
        throw new ValidationException("Object can not be found");
    }

    //@PostMapping(value = "object")
    protected ResponseEntity<R> create(R object) {
        try {
            object.setId(null);
            D entity = (D) getRestSecurity().copyToDbObject(object);
            entity.setId(null);
            getRepository().save(entity);
            R returnValue = getRestSecurity().copyToRestObject(entity);
            return new ResponseEntity<>(returnValue, HttpStatus.OK);
        } catch (Exception e) {
        }
        throw new ValidationException("Object can not be created");
    }

    //@PutMapping(value = "object")
    protected ResponseEntity<R> update(R object){
        try {
            if (object.getId() != null) {
                D entity = (D) getRestSecurity().copyToDbObject(object);
                if(getRepository().findById(entity.getId()).isPresent()){
                    getRepository().save(entity);
                    R returnValue = getRestSecurity().copyToRestObject(entity);
                    return new ResponseEntity<>(returnValue, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            
        }
        throw new ValidationException("Object can not be updated");
        
    }

    //@DeleteMapping("object/{id}")
    protected ResponseEntity<String> delete(String idCrypt) {
        try {
            Long id = getRestSecurity().decryptId(idCrypt);
            Optional<D> obj = this.getRepository().findById(id);
            if(obj.isPresent()){
                getRepository().delete(obj.get());
                return new ResponseEntity<>(idCrypt, HttpStatus.OK);
            } 
        } catch (Exception e) {
            
        }
        throw new ValidationException("Object can not be deleted");
    }
}