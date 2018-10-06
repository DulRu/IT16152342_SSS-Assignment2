<%--
  Created by IntelliJ IDEA.
  User: athma
  Date: 9/18/18
  Time: 8:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login page</title>
    <script>
        function aouth() {
            var CLIENT_ID = "1987099361350841";
            var RESPONSE_TYPE = "code";
            var REDIRECT_URI = "https://localhost:8443/callback";
            var AUTH_ENDPOINT = "https://www.facebook.com/dialog/oauth";
            var SCOPE = "public_profile user_friends user_gender";
            var REQUEST_ENDPOINT = AUTH_ENDPOINT + "?" + "client_id=" + encodeURIComponent(CLIENT_ID) + "&" + "response_type=" + encodeURIComponent(RESPONSE_TYPE) + "&" +
                "redirect_uri=" + encodeURIComponent(REDIRECT_URI) + "&" + "scope=" + encodeURIComponent(SCOPE);
            window.location.href= REQUEST_ENDPOINT;
        }
    </script>
</head>
<body>
     <br><button value="Facebook Login" name="login" onclick="aouth()"></button>
</body>
</html>
