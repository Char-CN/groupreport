var page = require('webpage').create();
var system = require('system');
var url = system.args[1];
var path = system.args[2];

page.open(url, function(){
    console.log('open url over ~');
    window.setTimeout(function() {
        page.render(path);
        phantom.exit();
    }, 1000);
});