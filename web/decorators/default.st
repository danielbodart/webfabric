<html>
<head>
    <title>Decorating $title$</title>
</head>
<body>

    <div>Extracted Div Contents: $page.properties.("div.target")$</div>

    <div>Decorating $body$</div>

    <p>Fully qualified url include: expected: 'Google' actual: '$include.("http://www.google.co.uk/").title$'</p>

    <p>Absolute path include: expected: 'Test' actual: '$include.("/test.html").title$'</p>

    <p>Relative path include: expected: 'Relative' actual: '$include.("relative.html").title$'</p>

</body>
</html>