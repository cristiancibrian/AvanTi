<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/sample.css">
<title>SAML2 Demo App</title>
</head>
<body>	
	<div id="header-area">    
    	<table>
    		<tr>
    			<td>    		
		        	<img src="resources/imagenes/logonew.png" alt="Logo"  vspace="10" width="167" height="75" />					
				</td>
    			<td>
    				<h1 style="color:#14566d;">SSO</h1>
    			</td>  	    		
			</tr>
		</table>		
    </div>
    <!-- Header-are end -->

    <div id="content-area">                    
        <div class="box"> <!--Displaying one product-->
           <h1>Public Area!</h1>
          <%
response.sendRedirect("consumer");
%>
        </div>

    </div>
    <!-- content-area end -->


    <div id="footer-area"><p>©2012 CertuIT </p></div>

</body>
</html>