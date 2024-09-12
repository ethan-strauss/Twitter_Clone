<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Your Feed</title>
</head>
<body>
    <nav>
    <ul>
        <li><a href="TweetController?action=list_tweets">Home</a></li>
        <li><a href="Profile">Profile</a></li>
    </ul>
</nav>
    <h2>Your Feed</h2>
    <c:if test="${not empty tweets}">
        <c:forEach var="tweet" items="${tweets}">
            <div>
                <p><strong>${tweet.username}</strong>: ${tweet.text}</p>
                <p><em>${tweet.timestamp}</em></p>
                <p>Likes: ${tweet.like_count}</p>
                <!-- Like Button -->
                <form action="TweetController" method="post">
                    <input type="hidden" name="action" value="likeTweet">
                    <input type="hidden" name="tweetId" value="${tweet.id}">
                    <button type="submit">Like</button>
                </form>
                <hr>
            </div>
        </c:forEach>
    </c:if>

    <c:if test="${empty tweets}">
        <p>No tweets found.</p>
    </c:if>

    <a href="createTweet.jsp">Post a new tweet</a>
</body>
</html>
