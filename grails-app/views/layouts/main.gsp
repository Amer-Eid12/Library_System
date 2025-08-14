<!doctype html>
<%@ page import="grails.plugin.springsecurity.SpringSecurityService" %>
<g:set var="springSecurityService" bean="springSecurityService"/>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>

    <asset:stylesheet src="application.css"/>

    <g:layoutHead/>
</head>

<body>

<nav class="navbar navbar-expand-lg navbar-dark navbar-static-top" role="navigation">
    <div class="container-fluid">
        <a class="navbar-brand" href="/#"><asset:image src="library-icon.png" alt="Grails Logo"/></a>
    </div>
    <div class="container-fluid" style="margin-left: 1000px">

        <g:if test="${springSecurityService.isLoggedIn()}">
        <g:form uri="/logout" method="POST">
            <g:submitButton style="background-color: transparent; border-color: transparent; color: white; font-size: large" name="logout" value="Logout"/>
        </g:form>
        </g:if>
        <g:else>
        <g:form uri="/register/registerStudent" method="POST">
            <g:submitButton style="background-color: transparent; border-color: transparent; color: white; font-size: large" name="registerStudent" value="registerStudent"></g:submitButton>
        </g:form>

        <g:form uri="/register/registerLibrarian" method="POST">
            <g:submitButton style="background-color: transparent; border-color: transparent; color: white; font-size: large" name="registerLibrarian" value="registerLibrarian"></g:submitButton>
        </g:form>
        </g:else>
</div>

</nav>

<g:layoutBody/>


<div id="spinner" class="spinner" style="display:none;">
    <g:message code="spinner.alt" default="Loading&hellip;"/>
</div>

<asset:javascript src="application.js"/>

</body>
</html>
