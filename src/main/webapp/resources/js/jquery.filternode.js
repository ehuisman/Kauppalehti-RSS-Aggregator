$.fn.filterNode = function(name) {
    return this.find('*').filter(function() {
        return this.nodeName === name;
    });
};