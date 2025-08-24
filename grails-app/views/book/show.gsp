<html>
<head>
    <title>Book Details</title>
    <meta name="layout" content="main">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">

<h1 class="mb-4 text-primary">Book Details</h1>

<div class="card">
    <div class="card-body">
        <h5 class="card-title">${book.title}</h5>
        <h6 class="card-subtitle mb-2 text-muted">${book.author}</h6>
        <p class="card-text"><strong>ISBN:</strong> ${book.isbn}</p>
        <p class="card-text">
            <strong>Status:</strong>
            <span class="badge ${book.isAvailable ? 'bg-success' : 'bg-danger'}">
                ${book.isAvailable ? 'Available' : 'Not Available'}
            </span>
        </p>


        <g:if test="${book.type == 'digital' && book.file}">
            <a href="${createLink(action:'downloadFile', id:book.id)}" class="btn btn-outline-primary">Download PDF</a>
        </g:if>
        <g:else>
        <div class="mt-3">
            <g:if test="${book.isAvailable}">
                <g:link action="borrow" id="${book.id}" class="btn btn-primary">Borrow this book</g:link>
            </g:if>
            <g:if test="${!book.isAvailable}">
                <g:link action="returnBook" id="${book.id}" class="btn btn-warning">Return this book</g:link>
            </g:if>
        </div>
        </g:else>
    </div>
</div>

<div class="mt-4">
    <g:link action="index" class="btn btn-secondary">← Back to Book List</g:link>
</div>

</body>
</html>
