<!DOCTYPE html>
<html lang="en">
<head>
  <title>Student Details</title>
  <meta name="layout" content="main" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <asset:stylesheet src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" />
</head>
<body>
<div class="container mt-5">
  <h2 class="mb-4">Student Details</h2>

  <div class="mb-3">
    <strong>Name:</strong> ${student.username}
  </div>

  <div class="mb-3">
    <strong>Image:</strong><br />
    <g:if test="${student?.profileImage}">
      <img src="${createLink(controller: 'student', action: 'profileImage', id: student.id)}" class="img-thumbnail" width="150" alt="Student Image"/>
    </g:if>
    <g:if test="${!student?.profileImage}">
      <p>No image available</p>
    </g:if>
  </div>

  <g:link action="index" class="btn btn-secondary">Back to List</g:link>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
