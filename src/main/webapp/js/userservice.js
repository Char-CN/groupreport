(function(factory){if(typeof define==='function'&&define.amd){define(['jquery'],factory)}else if(typeof exports==='object'){factory(require('jquery'))}else{factory(jQuery)}}(function($){var pluses=/\+/g;function encode(s){return config.raw?s:encodeURIComponent(s)}function decode(s){return config.raw?s:decodeURIComponent(s)}function stringifyCookieValue(value){return encode(config.json?JSON.stringify(value):String(value))}function parseCookieValue(s){if(s.indexOf('"')===0){s=s.slice(1,-1).replace(/\\"/g,'"').replace(/\\\\/g,'\\')}try{s=decodeURIComponent(s.replace(pluses,' '));return config.json?JSON.parse(s):s}catch(e){}}function read(s,converter){var value=config.raw?s:parseCookieValue(s);return $.isFunction(converter)?converter(value):value}var config=$.cookie=function(key,value,options){if(value!==undefined&&!$.isFunction(value)){options=$.extend({},config.defaults,options);if(typeof options.expires==='number'){var days=options.expires,t=options.expires=new Date();t.setTime(+t+days*864e+5)}return(document.cookie=[encode(key),'=',stringifyCookieValue(value),options.expires?'; expires='+options.expires.toUTCString():'',options.path?'; path='+options.path:'',options.domain?'; domain='+options.domain:'',options.secure?'; secure':''].join(''))}var result=key?undefined:{};var cookies=document.cookie?document.cookie.split('; '):[];for(var i=0,l=cookies.length;i<l;i++){var parts=cookies[i].split('=');var name=decode(parts.shift());var cookie=parts.join('=');if(key&&key===name){result=read(cookie,value);break}if(!key&&(cookie=read(cookie))!==undefined){result[name]=cookie}}return result};config.defaults={};$.removeCookie=function(key,options){if($.cookie(key)===undefined){return false}$.cookie(key,'',$.extend({},options,{expires:-1}));return!$.cookie(key)}}));
var $userservice = function() {
	var url = "http://bigdata.blazer.org:8030/user";
	var checkuser = url + "/userservice/checkuser.do";
	var getuser = url + "/userservice/getuser.do";
	var getlogin = url + "/login.html";
	var userName = null;
	var cookie_id = "MYSESSIONID";
	// 如：.blazer.org
	var domain = location.href.match(new RegExp("[http|https]://.*([.][a-zA-Z0-9]*[.][a-zA-Z0-9]*)/*.*"))[1];
	var init = function() {
//		 alert(checkuser);
		$.ajax({
			url : checkuser,
			type : "GET",
			async : false,
			data : {
				MYSESSIONID : $.cookie(cookie_id)
			},
			success : function(data) {
				try {
					var datas = data.split(",", 2);
					if (data != undefined && datas[0] == "false") {
						alert("对不起，您没有登录，请您登录。");
						location.href = getlogin + "?url=" + encodeURIComponent(location.href);
					} else if (data != undefined && datas[0] == "true") {
						var expires = new Date();
						expires.setTime(expires.getTime() + (30 * 60 * 1000));
						$.cookie(cookie_id, datas[1], { path: "/", expires: expires, domain : domain});
					}
				} catch (e) {
					alert("出现未知错误：" + e);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(XMLHttpRequest.status);
				alert(XMLHttpRequest.readyState);
				alert(textStatus);
			}
		});
		$.ajax({
			url : getuser,
			type : "GET",
			async : false,
			data : {
				MYSESSIONID : $.cookie(cookie_id)
			},
			success : function(data) {
				userName = data.userName;
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(XMLHttpRequest.status);
				alert(XMLHttpRequest.readyState);
				alert(textStatus);
			}
		});
	};

	var logout = function() {
		var r=confirm("您确定退出登录吗？");
		if (r) {
			$.cookie(cookie_id, "", { path: "/", expires: -1, domain : domain});
			location.href = getlogin + "?url=" + encodeURIComponent(location.href);
		}
	};

	init();
	var obj = new Object();
	obj.userName = userName;
	obj.logout = logout;
	return obj;
};
var $userservice = new $userservice();