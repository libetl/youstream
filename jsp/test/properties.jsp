<%@ page contentType="text/html;charset=UTF-8" language="java" %>

  Je t'affiche ici les properties java :
  <ul>
  <% java.util.Properties prop = java.lang.System.getProperties ();
     for (Object key : prop.keySet ()){
       out.print ("<li>" + key + " = " + prop.get (key) + "</li>");
     }
  %>
  </ul>
