<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"  %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Role</title>
<style>.error{color:#ff0000}</style>
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
								<form:form commandName="role" cssClass="form-horizontal" method="POST">
							       <div class="control-group">
							           <form:label path="role" class="control-label"><spring:message code="label.role" text="Default Role"/></form:label>
							           <div class="controls">
							               <form:input path="role" name="role" maxlength="5" type="text" placeholder="Role Value"/>
							               <form:errors path="role" cssClass="error" />
							           </div>
							       </div>
							       <div class="control-group">
							           <div class="controls">
							               <button type="submit" class="btn btn-success"><spring:message code="button.submit" text="Default Submit"/></button>
							               <button type="button" class="btn"><spring:message code="button.help" text="Default Help"/></button>
							           </div>
							       </div>
							   </form:form>
							 </div>                           
			              </div>
			          </div>      	
			      </div>
			</div>
		</section>
</body>
</html>