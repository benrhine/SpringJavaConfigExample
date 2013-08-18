<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>404</title>
<link href="<c:url value="/webjars/bootstrap/2.3.2/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" media='screen' />
<link href="<c:url value="/webjars/bootstrap/2.3.2/css/bootstrap-responsive.min.css" />" rel="stylesheet" type="text/css" media='screen' />
</head>
<body>
	<section>
		<div class="container-fluid">
			<div class="row-fluid">
		    	<div class="span12">
		    		<div class="span2">
		    			<!-- Place Holder -->
		    		</div>
		        	<div class="span8">
		            	<div class="hero-unit">
		            		<h1>404</h1>
		            		<p>Sorry. This is not the page your looking for.</p>
		            		<p><a class="btn" href="${pageContext.request.contextPath}/"><spring:message code="link.home" text="Default Home"/></a></p>
		            	</div>                           
		            </div>
		        </div>      	
		    </div>
		</div>
	</section>
	<script type="text/javascript" src="<c:url value="/webjars/jquery/1.10.1/jquery.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/webjars/bootstrap/2.3.2/js/bootstrap.min.js" />"></script>
</body>
</html>