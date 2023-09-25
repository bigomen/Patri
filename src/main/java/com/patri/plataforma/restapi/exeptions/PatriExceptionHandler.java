package com.patri.plataforma.restapi.exeptions;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import javax.crypto.BadPaddingException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.patri.plataforma.restapi.constants.MensagensID;
import com.patri.plataforma.restapi.jwt.JWTAutenticationException;
import com.patri.plataforma.restapi.utility.Translator;

@ControllerAdvice
public class PatriExceptionHandler extends ResponseEntityExceptionHandler
{
	@Autowired
	private ObjectMapper om;

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(PatriRuntimeException.class)
	protected ResponseEntity<Object> patriRuntimeValidation(PatriRuntimeException e)
	{
		String msg = Translator.toLocale(e.getMessageId());
		MessageResponseInterface result = new MessageResponse(msg);
		return ResponseEntity.status(e.getStatus()).body(result);
	}

	@ExceptionHandler(ObjectNotFoundException.class)
	protected ResponseEntity<Object> objectNotFound(ObjectNotFoundException e){
		String msg = MessageFormat.format(Translator.toLocale(MensagensID.PTR024), e.getParam());
		MessageResponseInterface result = new MessageResponse(msg);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
	}

	@ExceptionHandler(ValidationException.class)
	protected ResponseEntity<Object> validationException(ValidationException e){
		String msg = Translator.toLocale(MensagensID.PTR003);
		MessageResponseInterface result = new MessageResponse(msg);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
	}

	@ExceptionHandler(DecoderException.class)
	protected ResponseEntity<Object> decoderException(DecoderException e){
		String msg = Translator.toLocale(MensagensID.PTR102);
		MessageResponseInterface result = new MessageResponse(msg);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

	@ExceptionHandler(BadPaddingException.class)
	protected ResponseEntity<Object> badPaddingException(BadPaddingException e){
		String msg = Translator.toLocale(MensagensID.PTR024);
		MessageResponseInterface result = new MessageResponse(msg);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
	}

	@ExceptionHandler(BadRequest.class)
	protected ResponseEntity<Object> badRequest(BadRequest e)
	{
		String msg = Translator.toLocale(MensagensID.PTR041);
		MessageResponseInterface result = new MessageResponse(msg);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

	@ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(BadCredentialsException e)
	{
		String msg = Translator.toLocale(MensagensID.ACESSO_NEGADO);
		MessageResponseInterface result = new MessageResponse(msg);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
    }
	
	@ExceptionHandler(JWTAutenticationException.class)
	protected ResponseEntity<Object> handleInvalidTokenException(JWTAutenticationException e)
	{
		String msg = Translator.toLocale(MensagensID.INVALID_ACCESS_TOKEN);
		MessageResponseInterface result = new MessageResponse(msg);
		return ResponseEntity.status(e.getHttpStatus()).body(result);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exc,
			HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		String msg;
		Throwable cause = exc.getCause();
		Throwable rootCause = ExceptionUtils.getRootCause(exc);
		if (cause instanceof InvalidFormatException)
		{
			InvalidFormatException ex = (InvalidFormatException) cause;
			String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
			msg = MessageFormat.format(Translator.toLocale(MensagensID.PTR010), path,
					ex.getValue(), ex.getTargetType().getSimpleName());
		} else if (cause instanceof PropertyBindingException)
		{
			PropertyBindingException ex = (PropertyBindingException) cause;
			String propertyName = ex.getPropertyName();
			// String path = ex.getPath().stream().map(ref ->
			// ref.getFieldName()).collect(Collectors.joining("."));
			Collection<Object> knownPropertyIds = ex.getKnownPropertyIds();
			msg = MessageFormat.format(Translator.toLocale(MensagensID.PTR011),
					propertyName, knownPropertyIds);
		} else
		{
			if (rootCause instanceof UnrecognizedPropertyException)
			{
				UnrecognizedPropertyException ex = (UnrecognizedPropertyException) rootCause;
				Collection<Object> knownPropertyIds = ex.getKnownPropertyIds();
				String propertyName = ex.getPropertyName();
				msg = MessageFormat.format(Translator.toLocale(MensagensID.PTR011),
						propertyName, knownPropertyIds);
			} else
			{
				msg = Translator.toLocale(MensagensID.PTR001);
			}

		}
		MessageResponseInterface result = obtemMessageResponse(exc, request, status, msg, true);
		return handleExceptionInternal(exc, result, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException exc, HttpHeaders headers,
			HttpStatus status, WebRequest request)
	{
		String msg;
		if (exc instanceof MethodArgumentTypeMismatchException)
		{
			MethodArgumentTypeMismatchException ex = (MethodArgumentTypeMismatchException) exc;
			msg = MessageFormat.format(Translator.toLocale(MensagensID.PTR015), ex.getName(),
					ex.getValue(), ex.getValue(), ex.getRequiredType().getSimpleName());
		} else
		{
			msg = Translator.toLocale(MensagensID.PTR005);
		}
		MessageResponseInterface response = obtemMessageResponse(exc, request, status, msg, true);
		return handleExceptionInternal(exc, response, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException exc, HttpHeaders headers,
			HttpStatus status, WebRequest request)
	{
		String msg = Translator.toLocale(MensagensID.PTR005);
		MessageResponseInterface response = obtemMessageResponse(exc, request, status, msg, false);
		return handleExceptionInternal(exc, response, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exc,
			HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		String msg = MessageFormat.format(Translator.toLocale(MensagensID.PTR006),
				exc.getMethod());
		MessageResponseInterface response = obtemMessageResponse(exc, request, status, msg, false);
		return handleExceptionInternal(exc, response, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException exc,
			HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		String parameterName = exc.getParameterName();
		String msg = MessageFormat.format(Translator.toLocale(MensagensID.PTR009), parameterName);
		MessageResponseInterface response = obtemMessageResponse(exc, request, status, msg, true);
		return handleExceptionInternal(exc, response, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException exc, HttpHeaders headers, HttpStatus status,
			WebRequest request)
	{
		List<String> msgs = new ArrayList<String>();
		List<ObjectError> allErrors = exc.getAllErrors();
		for (ObjectError objectError : allErrors)
		{
			String msg = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
			msgs.add(msg);
		}
		MessageResponseInterface response = obtemMessageResponse(exc, request, status, msgs, true);
		return handleExceptionInternal(exc, response, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exc,
			HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		List<ObjectError> allErrors = exc.getBindingResult().getAllErrors();
		List<String> msgs = new ArrayList<String>();
		
		for (ObjectError objectError : allErrors)
		{
			String msg = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
			msgs.add(msg);
		}
		MessageResponseInterface response = obtemMessageResponse(exc, request, HttpStatus.BAD_REQUEST, msgs, false);
		return handleExceptionInternal(exc, response, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException exc, HttpHeaders headers,
			HttpStatus status, WebRequest request)
	{
		String msg = MessageFormat.format(Translator.toLocale(MensagensID.PTR011), exc.getRequestURL());
		MessageResponseInterface response = obtemMessageResponse(exc, request, status, msg, true);
		return handleExceptionInternal(exc, response, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException exc,
			HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		MessageResponseInterface response = obtemMessageResponse(exc, request, status, true);
		return handleExceptionInternal(exc, response, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException exc,
			HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		List<MediaType> mediaTypes = exc.getSupportedMediaTypes();
		if (!CollectionUtils.isEmpty(mediaTypes))
		{
			headers.setAccept(mediaTypes);
		}
		MessageResponseInterface response = obtemMessageResponse(exc, request, status, true);
		return handleExceptionInternal(exc, response, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException exc,
			HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		MessageResponseInterface response = obtemMessageResponse(exc, request, status, true);
		return handleExceptionInternal(exc, response, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException exc,
			HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		MessageResponseInterface response = obtemMessageResponse(exc, request, status, true);
		return handleExceptionInternal(exc, response, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException exc,
			HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		MessageResponseInterface response = obtemMessageResponse(exc, request, status, true);
		return handleExceptionInternal(exc, response, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException exc,
			HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		MessageResponseInterface response = obtemMessageResponse(exc, request, status, true);
		return handleExceptionInternal(exc, response, headers, status, request);
	}

	@ExceptionHandler({ TimeoutException.class })
	protected ResponseEntity<Object> handleTimeout(TimeoutException exc, WebRequest request)
	{
		String msg = Translator.toLocale(MensagensID.PTR002);
		MessageResponseInterface response = obtemMessageResponse(exc, request, HttpStatus.REQUEST_TIMEOUT, msg, true);
		return handleExceptionInternal(exc, response, new HttpHeaders(), HttpStatus.REQUEST_TIMEOUT, request);
	}

	@ExceptionHandler(DataAccessException.class)
	protected ResponseEntity<Object> handleDataAccessException(DataAccessException e, WebRequest request)
	{
		String msg = Translator.toLocale(MensagensID.PTR004);
		MessageResponseInterface response = obtemMessageResponse(e, request, HttpStatus.INTERNAL_SERVER_ERROR, msg,
				true);
		return handleExceptionInternal(e, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler({ DataAccessResourceFailureException.class })
	protected ResponseEntity<Object> handleDataAccessResourceFailureException(DataAccessResourceFailureException e,
			WebRequest request)
	{
		String msg = Translator.toLocale(MensagensID.PTR010);
		MessageResponseInterface response = obtemMessageResponse(e, request, HttpStatus.INTERNAL_SERVER_ERROR, msg,
				true);
		return handleExceptionInternal(e, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	protected ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException e,
			WebRequest request)
	{
		String msg = Translator.toLocale(MensagensID.PTR018);
		MessageResponseInterface response = obtemMessageResponse(e, request, HttpStatus.NOT_FOUND, msg, true);
		return handleExceptionInternal(e, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler({ JDBCConnectionException.class })
	protected ResponseEntity<Object> handleJDBCConnectionException(JDBCConnectionException e, WebRequest request)
	{
		String msg = Translator.toLocale(MensagensID.PTR010);
		MessageResponseInterface response = obtemMessageResponse(e, request, HttpStatus.INTERNAL_SERVER_ERROR, msg,
				true);
		return handleExceptionInternal(e, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exc,
			WebRequest request)
	{
		List<String> msgs = new ArrayList<String>();
		Set<ConstraintViolation<?>> constraintViolations = exc.getConstraintViolations();
		for (ConstraintViolation<?> cv : constraintViolations)
		{
			String message = cv.getMessage();
			msgs.add(message);

		}
		MessageResponseInterface response = obtemMessageResponse(exc, request, HttpStatus.CONFLICT, msgs, true);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	@ExceptionHandler({ PropertyReferenceException.class })
	protected ResponseEntity<Object> handlePropertyReferenceException(PropertyReferenceException exc,
			WebRequest request)
	{
		String propertyName = exc.getPropertyName();
		String msg = MessageFormat.format(Translator.toLocale(MensagensID.PTR013),
				propertyName);

		MessageResponseInterface response = obtemMessageResponse(exc, request, HttpStatus.BAD_REQUEST, msg, true);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler({ HttpClientErrorException.class })
	protected ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException exc, WebRequest request)
	{
		HttpStatus status = null;
		MessageResponseInterface response = null;
		String json = exc.getResponseBodyAsString();
		if (json != null)
		{
			status = exc.getStatusCode();
			try
			{
				response = om.readValue(json, MessageResponse.class);
			} catch (Exception e1)
			{
				e1.printStackTrace();
			}
		} else
		{
			status = exc.getStatusCode() != null ? exc.getStatusCode() : HttpStatus.INTERNAL_SERVER_ERROR;
			response = obtemMessageResponse(exc, request, status, true);
		}
		return handleExceptionInternal(exc, response, new HttpHeaders(), status, request);
	}

	@ExceptionHandler({ Exception.class })
	protected ResponseEntity<Object> handleDefault(Exception exc, WebRequest request)
	{
		String msg = Translator.toLocale(MensagensID.PTR003);
		MessageResponseInterface response = obtemMessageResponse(exc, request, HttpStatus.INTERNAL_SERVER_ERROR, msg,
				true);
		return handleExceptionInternal(exc, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler({ RuntimeException.class })
	protected ResponseEntity<Object> handleDefaultRuntime(RuntimeException exc, WebRequest request)
	{
		String msg = Translator.toLocale(MensagensID.PTR003);
		MessageResponseInterface response = obtemMessageResponse(exc, request, HttpStatus.INTERNAL_SERVER_ERROR, msg,
				true);
		return handleExceptionInternal(exc, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	protected MessageResponseInterface obtemMessageResponse(Exception exc, HttpStatus status, boolean stackTrace)
	{
		return obtemMessageResponse(exc, null, status, (List<String>) null, stackTrace);
	}

	protected MessageResponseInterface obtemMessageResponse(Exception exc, WebRequest request, HttpStatus status,
			boolean stackTrace)
	{
		return obtemMessageResponse(exc, request, status, (List<String>) null, stackTrace);
	}

	protected MessageResponseInterface obtemMessageResponse(Exception exc, WebRequest request, HttpStatus status,
			String msg, boolean stackTrace)
	{
		List<String> msgs = new ArrayList<String>();
		msgs.add(msg);
		return obtemMessageResponse(exc, request, status, msgs, stackTrace);
	}

	protected MessageResponseInterface obtemMessageResponse(Exception exc, WebRequest request, HttpStatus status,
			List<String> msgs, boolean stackTrace)
	{
		return obtemMessageResponse(exc, request, status, msgs, null, stackTrace);
	}

	protected MessageResponseInterface obtemMessageResponse(Exception exc, WebRequest request, HttpStatus status,
			List<String> msgs, String rootCauseMessage, boolean stackTrace)
	{
		MessageResponseInterface result = new MessageResponse();

		String path = null;
		if (request != null)
		{
			path = request.getDescription(false);
		}

//		String user = null;
//
//		if (user != null)
//		{
//			Principal userPrincipal = request.getUserPrincipal();
//																	
//			if (userPrincipal != null)
//			{
//				user = userPrincipal.getName();
//			}
//		}

		if (rootCauseMessage == null)
		{
			rootCauseMessage = ExceptionUtils.getRootCauseMessage(exc);
		}

		if (msgs == null || msgs.size() == 0)
		{
			String msg = null;
			if (rootCauseMessage != null)
			{
				int indexOf = rootCauseMessage.indexOf(":");
				int length = rootCauseMessage.length();
				if (indexOf > 0 && length > 2)
				{
					msg = rootCauseMessage.substring(indexOf + 2, length);
				}
			}
			if (msg == null || msg.trim().length() == 0)
			{
				msg = rootCauseMessage;
			}
			result.addMensagem(msg);
		} else
		{
			result.setMsgs(msgs);
		}

		String stack = ExceptionUtils.getStackTrace(exc);
		result.setCausa(rootCauseMessage);
		result.setStackTrace(stack);

		result.setStatus(status);
//		result.setUser(user);
		result.setPath(path);
		result.setDate(LocalDateTime.now());

		return result;
	}
}
