function Include(element){
    var url = element.href;
    var params = {'decorator': 'body' };
    if( url.contains("#") ) {
        var parts = url.split("#");
        url = parts[0];
        var ids = parts[1];
        params = { 'decorator': 'div', 'id': ids.split(',') }
    }
    jQuery.get(url, params, function(data) {
        jQuery(element).replaceWith(data);
    });
}