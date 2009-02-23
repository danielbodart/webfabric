var Behaviour = {
    register: function(sheet) {
        Behaviour.list.add(sheet);
    },

    list: [],

    apply: function() {
        Behaviour.list.each(function(sheet){
            Object.each(sheet, function(selector, funct){
                jQuery(selector).each(function(){
                    funct(this);
                })
            });
        });
    }
}

jQuery(document).ready(function(){
    Behaviour.apply();
});