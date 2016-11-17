window.fbAsyncInit = function() {
	FB.init({
//		appId : '507032346119076', //test
		appId : '506993132789664', //production
		xfbml : true,
		cookie : true,
		version : 'v2.5'
	});
};

(function(d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id)) {
		return;
	}
	js = d.createElement(s);
	js.id = id;
	js.src = "//connect.facebook.net/zh_TW/sdk.js";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

function statusChangeCallback(response) {
//	console.log('statusChangeCallback');
//	console.log(response);
	if (response.status === 'connected') {
		facebookSignIn();
	} else if (response.status === 'not_authorized') {
		FB.login(function(response) {
			statusChangeCallback(response);
		}, {
			scope : 'public_profile,email',
		});
	} else {
		FB.login(function(response) {
			statusChangeCallback(response);
		}, {
			scope : 'public_profile,email',
		});
	}
}
var linkurl = 'https://www.facebook.com/app_scoped_user_id/';
function facebookSignIn() {
//	console.log("fb login");
	FB.api('/me?fields=id,name,email,picture,gender,link', function(response) {
//		console.log(response);
		if (response.id != undefined) {
			
			$.cookie.json = true;
			
			$.cookie('fb',response);
			
//			$.cookie('fb_login','fb');
//			$.cookie('fb_userid', response.id);
//			$.cookie('fb_user_name', response.name);
//			$.cookie('fb_email', response.email);
//			$.cookie('fb_pic', response.picture.data.url);
//			$.cookie('fb_sex', response.gender);
//			$.cookie('fb_link', ((response.link == undefined) ? linkurl + response.id : response.link));
//			console.log($.cookie());
			
			var obj = $.cookie('fb');
			
//			console.log(obj.id);
			
			$.ajax({
				url : "addUserAction.html",
				async : false,
				type : "POST",
				data : {
					login_tp : "fb",
					userid : response.id,
					fb_name : response.name,
					email : response.email,
					fb_pic : response.picture.data.url,
					sex : (response.gender == 'male'?'1':'2'),
					link : (response.link == undefined) ? linkurl + response.id : response.link
				},
				dataType : "json",
				success : function(data) {
//					console.log(data.status);
					if (data.status) {
						location.reload();
					} else {
						alert("登入錯誤，請確認帳號密碼。");
					}
				},
				error : function() {
					alert("[sys_userchk ajax error]");
				}
			});
		}
	});
}

function facebookLogin() {
	FB.getLoginStatus(function(response) {
		statusChangeCallback(response);
	});
}