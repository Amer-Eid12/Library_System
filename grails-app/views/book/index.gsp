<!DOCTYPE html>
<%@ page import="grails.plugin.springsecurity.SpringSecurityService" %>
<g:set var="springSecurityService" bean="springSecurityService"/>
<html>
<head>
    <title>Books</title>
    <meta name="layout" content="main">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <asset:stylesheet src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

    <script>
        function searchBooks() {
            let query = document.getElementById("title").value;
            fetch("/book/search?q=" + encodeURIComponent(query))
                .then(response => response.json())
                .then(data => {
                    let tbody = document.querySelector("table tbody");
                    tbody.innerHTML = "";
                    data.forEach(book => {
                        if (!book) return;
                        let tr = document.createElement("tr");
                        tr.innerHTML = `
                        <td><a href="/book/show/${'$'}{book.id}" class="text-decoration-none">${'$'}{book.title}</a></td>
                        <td>${'$'}{book.author}</td>
                        <td>${'$'}{book.isbn}</td>
                        <td><span class="badge bg-info">Book</span></td>
                        <td><span class="badge ${'$'}{book.isAvailable ? 'bg-success' : 'bg-danger'}">${'$'}{book.isAvailable ? 'Yes' : 'No'}</span></td>
                        <td>
                        <a href="/book/edit/${'$'}{book.id}" class="btn btn-sm btn-secondary">Edit</a>
                        </td>
                        `;
                        tbody.appendChild(tr);
                    });
                });
        }
    </script>

</head>
<body>
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1><strong>Books in Library</strong></h1>

        <g:if test="${springSecurityService.isLoggedIn() &&
                springSecurityService.currentUser.authorities*.authority.contains('ROLE_LIBRARIAN')}">
            <div class="d-flex gap-2">
                <g:link action="create" class="btn btn-success">Add New Book</g:link>
                <g:link controller="report" action="exportExcel" class="btn btn-primary">Download Excel Report</g:link>
            </div>
        </g:if>
    </div>

    <g:if test="${flash.message}">
        <div class="alert alert-success">${flash.message}</div>
    </g:if>

    <div>
        <div class="mb-3 row g-2 align-items-center">
            <label for="title" class="col-auto col-form-label"><strong>Title:</strong></label>
            <div class="col-auto">
                <input type="text" id="title" onkeyup="searchBooks()" placeholder="Search By Title..." name="title" class="form-control">
            </div>
        </div>
    </div>

    <div>
        <g:link action="availabilityFilter" class="btn btn-sm btn-secondary">Filter By Availability</g:link>

        <g:link action="myBorrowedFilter" class="btn btn-sm btn-secondary">My Borrowed Books</g:link>

        <g:link action="physicalFilter" class="btn btn-sm btn-secondary">Physical Books</g:link>
        
        <g:link action="digitalFilter" class="btn btn-sm btn-secondary">Digital Books</g:link>

        <g:if test="${filtered}">
            <g:link controller="book" action="index" class="btn-close" aria-label="Close">
                <i class="bi bi-x-lg"></i>
            </g:link>
        </g:if>

    </div>

    <div class="table-responsive">
        <table class="table table-bordered table-striped align-middle">
            <thead class="table-light">
            <tr>
                <th>Title</th>
                <th>Author</th>
                <th>ISBN</th>
                <th>Type</th>
                <th>Available</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${books}" var="book">
                <g:if test="${book}">
                <tr>
                    <td>
                        <g:link action="show" id="${book.id}" class="text-decoration-none">
                            ${book.title}
                        </g:link>
                    </td>
                    <td>${book.author}</td>
                    <td>${book.isbn}</td>
                    <td>
                        <span class="badge bg-info">
                            ${book.type}
                        </span>
                    </td>
                    <td>
                        <span class="badge ${book.isAvailable ? 'bg-success' : 'bg-danger'}">
                            ${book.isAvailable ? 'Yes' : 'No'}
                        </span>
                    </td>
                    <td>
                        <div class="d-flex flex-wrap gap-2">
                            <g:if test="${book.type == 'digital' && book.file}">
                                <a href="${createLink(action:'downloadFile', id:book.id)}" class="btn btn-sm btn-outline-primary">
                                    Download PDF
                                </a>
                            </g:if>
                            <g:else>

                            <g:if test="${book.isAvailable}">
                                <g:link action="borrow" id="${book.id}" class="btn btn-sm btn-primary">
                                    Borrow
                                </g:link>
                            </g:if>
                            <g:if test="${!book.isAvailable}">
                                <g:link action="returnBook" id="${book.id}" class="btn btn-sm btn-warning">
                                    Return
                                </g:link>
                            </g:if>
                            </g:else>

                            <g:link action="edit" id="${book.id}" class="btn btn-sm btn-secondary">
                                Edit
                            </g:link>

                            <g:form action="delete" method="POST" class="d-inline">
                                <g:hiddenField name="id" value="${book.id}" />
                                <g:submitButton name="delete" value="Delete" class="btn btn-sm btn-danger"
                                                onclick="return confirm('Are you sure?')" />
                            </g:form>
                        </div>
                    </td>
                </tr>
                </g:if>
            </g:each>
            </tbody>
        </table>
        <g:paginate total="${total}" />
    </div>
</div>
</body>
</html>
