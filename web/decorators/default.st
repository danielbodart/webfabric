<html>
<head>
    <title>Decorating $title$</title>
    <script type="text/javascript" src="$base$/js/jquery-1.3.2.min.js"></script>
    <script type="text/javascript" src="$base$/js/core.js"></script>
    <script type="text/javascript" src="$base$/js/behaviour.js"></script>
    <script type="text/javascript" src="$base$/js/ahah.js"></script>
    <script type="text/javascript" src="$base$/js/bindings.js"></script>
</head>
<body>
    <div>Extracted Div Contents: $page.properties.("div.target")$</div>

    <div>Decorating $body$</div>

    <p>Fully qualified url include: expected: 'Google' actual: '$include.("http://www.google.co.uk/").title$'</p>

    <p>Absolute path include: expected: 'Test' actual: '$include.("/test.html").title$'</p>

    <p>Relative path include: expected: 'Relative' actual: '$include.("relative.html").title$'</p>

    <a class="include" href="test.html">test.html</a>

</body>
</html>