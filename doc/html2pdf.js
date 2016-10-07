var page = require('webpage').create();
var url = 'http://localhost:8050/print.html';

page.open(url, function(){
	console.log('open ~');
	window.setTimeout(function() {
		page.render('test.pdf');
		phantom.exit();
	}, 500);
});

