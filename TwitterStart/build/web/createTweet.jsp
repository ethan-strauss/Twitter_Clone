<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Tweets</title>
    </head>
    <body>
 <nav>
    <ul>
        <li><a href="TweetController?action=list_tweets">Home</a></li>
        <li><a href="Profile">Profile</a></li>
    </ul>
</nav>
        <h2>Post a Tweet!</h2>
        <form action="TweetController" method="post">
            <label>Tweet: </label>
            <input type="text" name="tweetText"/>
            <input type="hidden" name="action" value="createTweet">
            <input type="submit" value="Add Tweet"/>
        </form>
        <c:if test="${filename != null}">
        <img src="GetImage?username=${username}" width = "240" height="330"/>
        
        <h3>Add A Pic!</h3>
        <form action="Upload" method="post" enctype="multipart/form-data">
            <div id="data">
            <input type="file" accept="image/*" name="file">
            </div>
            <div id="buttons">
                <label>&nbsp;</label>
                <input type="submit" value="Upload">
            </div>
        </form>
        </c:if>
    </body>
</html>
