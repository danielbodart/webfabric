function Include(element){
    jQuery.get(element.href, {'decorator': 'body'}, function(data) {
        jQuery(element).replaceWith(data);
    });
}