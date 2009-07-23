function Include(element){
    var url = element.href;
    var params = {'decorator': 'body'};
    if( url.contains("#") ) {
        var parts = url.split("#");
        url = parts[0];
        var id = parts[1];
        params = { 'decorator': 'div', 'id': id }
    }
    jQuery.get(url, params, function(data) {
        jQuery(element).replaceWith(data);
    });
}