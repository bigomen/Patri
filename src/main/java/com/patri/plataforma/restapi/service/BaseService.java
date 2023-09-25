package com.patri.plataforma.restapi.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.validation.ValidationException;
import com.patri.plataforma.restapi.constants.MensagensID;
import com.patri.plataforma.restapi.exeptions.BadRequest;
import com.patri.plataforma.restapi.exeptions.ObjectNotFoundException;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.security.core.context.SecurityContextHolder;
//import com.patri.plataforma.restapi.jwt.JWTAuthentication;
import com.patri.plataforma.restapi.model.BaseModel;
import com.patri.plataforma.restapi.restmodel.BaseRestModel;
import com.patri.plataforma.restapi.utility.RestSecurity;

public abstract class BaseService<D extends BaseModel, R extends BaseRestModel>
{
	protected abstract CrudRepository<D, Long> getRepository();

	protected abstract RestSecurity<D, R> getRestSecurity();

//	protected Long getIdEmpresa()
//	{
//		JWTAuthentication jwtAuth = (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
//		return jwtAuth.getIdEmpresa();
//	}
//
//	protected Long getIdUsuario()
//	{
//		JWTAuthentication jwtAuth = (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
//		return jwtAuth.getIdUsuario();
//	}

//	protected String getLoginUsuario()
//	{
//		JWTAuthentication jwtAuth = (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
//		return jwtAuth.getPrincipal().toString();
//	}

	protected Collection<R> getAll()
	{
		try
		{
			Iterable<D> modAr = getRepository().findAll();
			List<D> result = new ArrayList<D>();
			modAr.forEach(result::add);

			;
			return getRestSecurity().copyToRestObject(result);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		throw new ObjectNotFoundException("Object can not be found");
	}

	protected Long getCount()
	{
		return getRepository().count();
	}

	protected R getById(String idCrypt)
	{
		try
		{
			Long id = getRestSecurity().decryptId(idCrypt);
			Optional<D> entity = getRepository().findById(id);
			if (entity.isPresent())
			{
				R restModel = (R) getRestSecurity().copyToRestObject(entity.get());
				return restModel;
			}
		} catch (Exception e)
		{
		}

		throw new ObjectNotFoundException(MensagensID.PTR024);

	}

	protected R create(R object)
	{
		try
		{
			object.setId(null);
			D entity = (D) getRestSecurity().copyToDbObject(object);
			entity.setId(null);
			getRepository().save(entity);
			R returnValue = getRestSecurity().copyToRestObject(entity);
			return returnValue;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		throw new BadRequest("");
	}

	protected R update(R object)
	{
		try
		{
			if (object.getId() != null)
			{
				D entity = (D) getRestSecurity().copyToDbObject(object);
				if (getRepository().findById(entity.getId()).isPresent())
				{
					getRepository().save(entity);
					R returnValue = getRestSecurity().copyToRestObject(entity);
					return returnValue;
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		throw new ValidationException("Object can not be updated");

	}

	protected String delete(String idCrypt)
	{
		try
		{
			Long id = getRestSecurity().decryptId(idCrypt);
			Optional<D> obj = this.getRepository().findById(id);
			if (obj.isPresent())
			{
				getRepository().delete(obj.get());
				return idCrypt;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		throw new ValidationException("Object can not be deleted");
	}
}