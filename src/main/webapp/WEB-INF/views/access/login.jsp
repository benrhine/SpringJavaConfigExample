<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>Spring XML Example Login</title>
<link href="<c:url value="/webjars/bootstrap/2.3.2/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" media='screen' />
<link href="<c:url value="/webjars/bootstrap/2.3.2/css/bootstrap-responsive.min.css" />" rel="stylesheet" type="text/css" media='screen' />
</head>
	<body onload='document.f.j_username.focus();'>

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
			                    <form name='f' class="form-horizontal" action="<c:url value='../j_spring_security_check' />" method="POST">
			                        <div class="heading">
			                            <h4 class="form-heading">Spring XML Example Sign-in</h4>
			                        </div>
			                        <div class="control-group">
			                            <label class="control-label" for='j_username'><spring:message code="label.username" text="Default Username"/></label>
			                            <div class="controls">
			                                <input type="text" name='j_username' placeholder="Username">
			                            </div>
			                        </div>
			                        <div class="control-group">
			                            <label class="control-label" for='j_password'><spring:message code="label.password" text="Default Password"/></label>
			                            <div class="controls">
			                                <input type="password" name='j_password' placeholder="Min. 8 Characters">
			                            </div>
			                        </div>
			                        <div class="control-group">
			                            <div class="controls">
			                                <!-- <label class="checkbox">
			                                    <input type="checkbox"> Keep me signed in   ¦
			                                    <a href="#" class="btn btn-link">Forgot my password</a>
			                                </label> -->
			                                <button type="submit" class="btn btn-success">Sign In</button>
			                                <button type="button" class="btn">Help</button>
			                            </div>
			                        </div>
			                    </form>
			                    <p><a class="btn" href="${pageContext.request.contextPath}/auth/signup"><spring:message code="button.no.account" text="Default No account? Create one now!"/></a></p>
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