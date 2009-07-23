function Include(element){
    var params = {'decorator': 'body'};
    if( element.href.contains("#") ) {
        var id = element.href.split("#")[1];
        params = { 'decorator': 'div', 'id': id }
    }
    jQuery.get(element.href, params, function(data) {
        jQuery(element).replaceWith(data);
    });
}