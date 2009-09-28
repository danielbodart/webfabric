/* String */

String.prototype.contains = function(value) {
    return this.indexOf(value) > -1;
};

String.prototype.startsWith = function(value) {
    return this.indexOf(value) == 0;
};

String.prototype.endsWith = function(value) {
    var searchFrom = this.length - value.length;
    return this.indexOf(value, searchFrom) == searchFrom;
};

String.prototype.each = function(handler){
    for(var i = 0; i < this.length; i++){
        handler(this.charAt(i), i);
    }
    return this;
};

String.prototype.trim = function() {
    return this.replace(/^\s+|\s+$/g, '');
};

String.Empty = "";

/* Array */

if ( typeof(Array.prototype.indexOf) == "undefined"){
    Array.prototype.indexOf = function(item) {
        for( var i = 0 ; i < this.length ; i++ ) {
            if ( this[i] == item ) {
                return i;
            }
        }
        return -1;
    };
}

Array.prototype.add = function(item) {
    this.push(item);
    return this;
};

Array.prototype.remove = function(item) {
    var i = this.indexOf(item);
    while ( i != -1 ) {
        this.splice(i, 1);
        i = this.indexOf(item);
    }
    return this;
};

Array.prototype.contains = function(value) {
    return this.indexOf(value) > -1;
};

Array.prototype.last = function(){
    return this[this.length - 1];
};

Array.each = function(items, handler){
    for(var i = 0; i < items.length; i++){
        handler(items[i], i);
    }
    return this;
};

Array.prototype.each = function(handler){
    return Array.each(this, handler);
};

Array.filter = function(items, handler){
    var results = [];
    Array.each(items, function(item){
        if(handler(item)){
            results.add(item);
        }
    });
    return results;
};

if (typeof(Array.prototype.filter) == "undefined"){
    Array.prototype.filter = function(handler){
        return Array.filter(this, handler);
    };
}

Array.map = function(items, handler){
    var results = [];
    Array.each(items, function(item, index){
        results.add(handler(item, index));
    });
    return results;
};

if (typeof(Array.prototype.map) == "undefined"){
    Array.prototype.map = function(handler){
        return Array.map(this, handler);
    };
}


/* StringBuilder */

function StringBuilder() {
    this.buffer = [];
}

StringBuilder.prototype.append = function(value){
    this.buffer.add(value);
    return this;
};

StringBuilder.prototype.toString = function(separator) {
    return this.buffer.join(separator || String.Empty);
};

/* Object */

Object.each = function(object, handler){
    for(var property in object){
        handler(property, object[property]);
    }
};
