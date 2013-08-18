<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Administration: Users List</title>
<link href="<c:url value="/webjars/bootstrap/2.3.2/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" media='screen' />
<link href="<c:url value="/webjars/bootstrap/2.3.2/css/bootstrap-responsive.min.css" />" rel="stylesheet" type="text/css" media='screen' />
</head>
<body>
	<section>
		<div id="messages" class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<div class="span2">
						<!-- Just for spacing -->
					</div>
					<div class="span8">
	                    <c:if test="${not empty message}">
							<div class="alert">
							    <button type="button" class="close" data-dismiss="alert">×</button>
							    ${message}<br/>
							</div>
						</c:if>
						<c:if test="${not empty errorMessage}">
							<div class="alert alert-error">
							    <button type="button" class="close" data-dismiss="alert">×</button>
							    ${errorMessage}<br/>
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	<section>
		<div class="container-fluid">
			<div class="row-fluid">
		    	<div class="span12">
		    		<div class="span2">
		    			<!-- Place Holder -->
		    		</div>
		        	<div class="span8">
		            	<div class="hero-unit">
							<table border="1px" cellpadding="0" cellspacing="0" >
							<thead>
							<tr>
							<th width="10%">id</th>
							<th width="10%">First Name</th>
							<th width="10%">Last Name</th>
							<th width="25%">User Name</th>
							<th width="25%">Short User Name</th>
							<th width="10%">Enabled</th>
							<th width="10%">Role</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach var="user" items="${users}">
							<tr>
								<td>${user.id}</td>
								<td>${user.firstName}</td>
								<td>${user.lastName}</td>
								<td>${user.username}</td>
								<td>${user.shortUsername}</td>
								<td>${user.enabled}</td>
								<td>${user.role.role}</td>
								<td>
								<a href="${pageContext.request.contextPath}/user/edit?id=${user.id}"><spring:message code="link.edit" text="Default Edit"/></a><br/>
								<a href="${pageContext.request.contextPath}/user/delete?id=${user.id}"><spring:message code="link.delete" text="Default Delete"/></a><br/>
								</td>
							</tr>
							</c:forEach>
							</tbody>
							</table>
						</div>                           
	              </div>
	          </div>      	
	      </div>
		</div>
	</section>
		
		<p><a href="${pageContext.request.contextPath}/">Home page</a></p>
</body>
</html>