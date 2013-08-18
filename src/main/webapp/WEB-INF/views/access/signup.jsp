<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"	uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>Spring XML Example Sign-up</title>
<link href="<c:url value="/webjars/bootstrap/2.3.2/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" media='screen' />
<link href="<c:url value="/webjars/bootstrap/2.3.2/css/bootstrap-responsive.min.css" />" rel="stylesheet" type="text/css" media='screen' />
</head>
	<body onload='document.f.firstName.focus();'>
	
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
			                      <form:form method="POST" commandName="userDto" cssClass="form-horizontal" action="${pageContext.request.contextPath}/auth/signup">
			                        <div class="heading">
			                            <h4 class="form-heading">Spring XML Example Sign-up</h4>
			                        </div>
			                        <div class="control-group">
			                            <form:label path="firstName" class="control-label"><spring:message code="label.firstName" text="Default First Name"/></form:label>
			                            <div class="controls">
			                                <form:input path="firstName" name="firstName" maxlength="30" type="text" placeholder="First Name"/>
			                                <form:errors path="firstName" cssClass="error" />
			                            </div>
			                        </div>
			                        <div class="control-group">
			                            <form:label path="lastName" class="control-label"><spring:message code="label.lastName" text="Default Last Name"/></form:label>
			                            <div class="controls">
			                                <form:input path="lastName" name="lastName" maxlength="30" type="text" placeholder="Last Name"/>
			                                <form:errors path="lastName" cssClass="error" />
			                            </div>
			                        </div>
			                        <div class="control-group">
			                            <form:label path="userName" class="control-label"><spring:message code="label.username" text="Default Username"/></form:label>
			                            <div class="controls">
			                                <form:input path="userName" name="userName" maxlength="50" type="text" placeholder="example@email.com"/>
			                                <form:errors path="userName" cssClass="error" />
			                            </div>
			                        </div>
			                        <div class="control-group">
			                            <form:label path="reUserName" class="control-label"><spring:message code="label.username" text="Default ReUsername"/></form:label>
			                            <div class="controls">
			                                <form:input path="reUserName" name="reUserName" maxlength="50" type="email" placeholder="example@email.com"/>
			                                <form:errors path="reUserName" cssClass="error" />
			                            </div>
			                        </div>
			                        <div class="control-group">
			                            <form:label path="userPass" class="control-label"><spring:message code="label.password" text="Default Password"/></form:label>
			                            <div class="controls">
			                                <form:input path="userPass" name="userPass" maxlength="30" type="password" placeholder="Min. 8 Characters"/>
			                                <form:errors path="userPass" cssClass="error" />
			                            </div>
			                        </div>
			                        <div class="control-group">
			                            <form:label path="reUserPass" class="control-label"><spring:message code="label.password" text="Default RePassword"/></form:label>
			                            <div class="controls">
			                                <form:input path="reUserPass" name="reUserPass" maxlength="30" type="password" placeholder="Min. 8 Characters"/>
			                                <form:errors path="reUserPass" cssClass="error" />
			                            </div>
			                        </div>
			                        <div class="control-group">
			                            <div class="controls">
			                                <!-- <label class="checkbox">
			                                    <input type="checkbox"> I agree all your <a href="#">Terms of Services</a>
			                                </label> -->
			                                <button type="submit" class="btn btn-success"><spring:message code="button.signup" text="Default Signup"/></button>
			                                <button type="button" class="btn"><spring:message code="button.help" text="Default Help"/></button>
			                            </div>
			                        </div>
			                    </form:form>	
			                    <p><a class="btn" href="${pageContext.request.contextPath}/auth/login" ><spring:message code="button.existing.account" text="Default Already have an account? Sign in!"/></a></p>
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