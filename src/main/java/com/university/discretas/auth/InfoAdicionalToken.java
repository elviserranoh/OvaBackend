package com.university.discretas.auth;

import com.university.discretas.entity.Usuario;
import com.university.discretas.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class InfoAdicionalToken implements TokenEnhancer {
	
	@Autowired
	IUsuarioService usuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		Usuario usuario = usuarioService.findByIdentityDocument(authentication.getName());
		
		Map<String, Object> info = new HashMap<>();

		info.put("id", usuario.getId());
		info.put("firstName", usuario.getFirstName());
		info.put("lastName", usuario.getLastName());
		info.put("identityDocument", usuario.getIdentityDocument());
		info.put("image", usuario.getImage());
		info.put("rol", usuario.getRol());

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}

}
