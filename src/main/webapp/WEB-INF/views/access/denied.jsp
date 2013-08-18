<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Access Denied</title>
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
							<p><a href="${pageContext.request.contextPath}/">Home</a></p>
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