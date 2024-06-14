package com.projects.backend.login;

import java.util.ArrayList;
import java.util.Collection;




public class AuthenticationResponse {
private boolean authenticated;
private Collection<?> authorities;
private String name;
public AuthenticationResponse(boolean authenticated, Collection<?> authorities, String name) {
  this.authenticated = authenticated;
  this.authorities = authorities;
  this.name = name;
}
@Override
public String toString() {
  
  String  json =  "{\"authenticated\":" + authenticated +", \"authorities\":[";
  for(int i = 0;i < this.authorities.size();++i)
  {
    json += "\""+authorities.toArray()[i].toString()+"\"";
    if(i != authorities.size()-1) json+=",";
  }
  json+= "], \"name\":\"" + name+"\"}";
      return json;
}

}
