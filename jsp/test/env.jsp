<%@ page contentType="text/html;charset=UTF-8" language="java" %>

  Je t'affiche ici les variables d'environnement :
  <ul>
  <% java.util.Map<String, String> env = java.lang.System.getenv ();
     for (String key : env.keySet ()){
       out.print ("<li>" + key + " = " + env.get (key) + "</li>");
     }
  %>
  </ul>
