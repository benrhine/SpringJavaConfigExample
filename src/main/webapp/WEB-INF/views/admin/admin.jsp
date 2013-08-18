<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>Spring XML Example Administration</title>
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
								<p><a href="${pageContext.request.contextPath}/user/list">List Users</a></p>
								<p><a href="${pageContext.request.contextPath}/role/list">List Roles</a></p>
								<p><a href="${pageContext.request.contextPath}/role/add">Add Role</a></p>
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