<html>
<head>
    <title>Decorating $title$</title>
</head>
<body>

    <div>Extracted Div Contents: $page.properties.("div.target")$</div>

    <div>Decorating $body$</div>


    $scala$

    Googles title: $include.("http://www.google.co.uk/").title$

</body>
</html>