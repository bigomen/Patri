/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.patri.plataforma.restapi.utility;

import com.google.gson.Gson;
import com.patri.plataforma.restapi.constants.Constantes;
//import com.patri.plataforma.restapi.jwt.JWTAuthentication;
import com.patri.plataforma.restapi.model.BaseModel;
import com.patri.plataforma.restapi.restmodel.BaseRestModel;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.core.env.Environment;

/**
 *
 * @author rcerqueira
 */

@Deprecated
public class RestSecurity<D extends BaseModel, R extends BaseRestModel> {
    private Class baseModelClass = null; 
    private Class restModelClass = null; 
    private Environment env = null;
    
    public RestSecurity(Class<D> baseModel, Class<R> restModel, Environment env ) {
        this.baseModelClass = baseModel;
        this.restModelClass = restModel;
        this.env = env;
    }
    
    
    public void copyToRestObject(R dest, D source) throws Exception {
        copyData(source, dest);
    }

    
    public R copyToRestObject(D source) throws Exception {
        R dest = (R) this.restModelClass.newInstance();
        copyData(source, dest);
        return (R) dest;
    }
    

    
    public List<R> copyToRestObject(List<D> source) throws Exception {
        List<R> list = new ArrayList<>();
        for(BaseModel model : source) {
            R dest = (R) this.restModelClass.newInstance();
            copyData(model, dest);
            list.add( dest );
        }
        return list;
    }

    public List<R> copyToRestObject(Iterable<D> source) throws Exception {
        List<R> list = new ArrayList<>();
        Iterator<D> it = source.iterator();
        while (it.hasNext()) {
            R dest = (R) this.restModelClass.newInstance();
            copyData(it.next(), dest);
            list.add( dest );
        }
        return list;
    }


    public void copyToDbObject(D dest, R source) throws Exception {
        copyData(source, dest);
    }

    public D copyToDbObject(R source) throws Exception {
        D dest = (D) this.baseModelClass.newInstance();
        copyData(source, dest);
        return (D) dest;
    }

    

    
    private void copyData(Object source, Object destination) throws Exception {
        Method[] methSource = source.getClass().getMethods();
        Method[] methDest = destination.getClass().getMethods();

        for (Method ms : methSource) {
            if (ms.getName().startsWith("get")) {
                for (Method md : methDest) {
                    if (md.getName().startsWith("set") &&  ms.getName().substring(3).equals(md.getName().substring(3))) {

                        Object param = ms.invoke(source);
                        if (param != null) {
                            if (param instanceof String && md.getParameters()[0].getType() == String.class) {
                                md.invoke(destination, ((String)param).trim());
                            } else if ((param instanceof String) && md.getParameters()[0].getType() == Long.class) {
                                param = this.decryptId((String)param);
                                md.invoke(destination, param);
                            } else if (param instanceof Long && md.getParameters()[0].getType() == String.class) {
                                param = this.encryptId((Long)param);
                                md.invoke(destination, param);
                            } else if (param instanceof Set || param instanceof List) {
                                copyData(destination, param);
                            } else {
                                md.invoke(destination, param);
                            }
                        }

                        break;
                    }
                }
            }
        }
    }

    public Long decryptId(String param) throws Exception {
        
        if (param == null)
            return null;
        
        String crypto = env.getProperty("security.obfuscate_secret");
//        Authentication jwtAuth = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        String user = Constantes.ANONYMOUS_USER;
//        if (jwtAuth instanceof JWTAuthentication) {
//            user = jwtAuth.getPrincipal().toString();
//        }

        byte[] keyData = crypto.getBytes();
        SecretKeySpec KS = new SecretKeySpec(keyData, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.DECRYPT_MODE, KS);

        //byte[] crypto_data = Base64.getMimeDecoder().decode(param);
        byte[] crypto_data = Hex.decodeHex(param);
        String param_decrypto = new String(cipher.doFinal(crypto_data));

        if (!user.equals(param_decrypto.split("#")[1]))
            throw new Exception("Data tampering");

        return Long.valueOf(param_decrypto.split("#")[0]);
        
    }
    
    public String encryptIdJson(Long param) throws Exception {
        HashMap map = new HashMap();
        map.put("id", encryptId(param));
        Gson gson = new Gson();
        return gson.toJson(map);
    }
    
    public String encryptId(Number param) throws Exception {
        String crypto = env.getProperty("security.obfuscate_secret");
//        Authentication jwtAuth = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        String user = Constantes.ANONYMOUS_USER;
//        if (jwtAuth instanceof JWTAuthentication) {
//            user = jwtAuth.getPrincipal().toString();
//        }

        byte[] keyData = crypto.getBytes();
        SecretKeySpec KS = new SecretKeySpec(keyData, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.ENCRYPT_MODE, KS);

        byte[] param_crypto = param.toString().concat("#").concat(user).getBytes("utf-8");
        byte[] crypto_data = cipher.doFinal(param_crypto);
        return Hex.encodeHexString(crypto_data);
    }
    
}
