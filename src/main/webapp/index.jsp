<%@ page language="java"  contentType="text/html; charset=UTF-8" %>
<html>
<body>
<h2>Hello World!</h2>


<%--测试springmvc上传单个文件，
其中upload_file为要上传的文件名与/manage/product/upload.do中的MultipartFile对象的对象名一致。--%>
springmvc上传单个文件
<form name="form1" action="/manage/product/upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file"/>
    <input type="submit" value="springmvc上传文件"/>
</form>

富文本的文件上传
<form name="form1" action="/manage/product/richtext_img_upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file"/>
    <input type="submit" value="富文本的文件上传件"/>
</form>orderItemList

</body>
</html>
