<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Admin - JEE Bar 2014</title>
                <link href='http://fonts.googleapis.com/css?family=Open+Sans:600,400,300&subset=latin,cyrillic-ext' rel='stylesheet' type='text/css'>
                <link rel="stylesheet" type="text/css" href="files/web/css/admin.css" />
                <link rel="stylesheet" type="text/css" href="files/web/css/jquery-ui.min.css" />
		<script src="files/web/js/libs/require.js" type="text/javascript"></script>
                <link id="favicon" rel="shortcut icon" type="image/x-icon" href="files/web/css/fav1.ico?v=11" />
	</head>
	<body>
		<div id="frameholder">
			<div id="centerframe">	
                                <div id="menuholder"></div>
				<div id="mainview"></div>			
				<div id="footer" style="">
					Čas serveru: ${time} &nbsp;&nbsp;&nbsp;&nbsp;
					Vaše IP adresa: ${ip}		
				</div>
			</div>
		</div>
		<script type="text/javascript">
		
			// require config
			require.config({
				baseUrl: "files/web/js/",
				urlArgs: "pVersion=" + (new Date()).getTime(),
				paths: {
					jquery: 'libs/jquery-1.11.1.min',
                                        jqui: 'libs/jquery-ui.min',
                                        jqnest: 'libs/jquery.nestable',
					underscore: 'libs/underscore-1.7.0.min',
					json: 'libs/json2.min',
					backbone: 'libs/backbone-1.1.2.min'
					},
				shim: {
						jquery: {
							exports: '$'
						},
                                                jqnest: {
                                                    deps: ['jquery']
                                                },
                                                jqui: {
                                                    deps: ['jquery']
                                                },
						backbone: {
							deps: ['underscore','json','jqui','jqnest'],
							exports: 'Backbone'
						}
					}
			});

			// app init
			require(['backbone'], function(){
				//Backbone.emulateJSON = true;
				require(['apps/admin/Menu'], function(){
                                    
                                        // root adresa stránky
                                        var pageURL = window.location.protocol + "//" + window.location.host + window.location.pathname;
                                        var protocolLessPageURL = window.location.host + window.location.pathname;
                                        
                                        // root adresa aplikace
                                        var rootURL = pageURL.substring(0, pageURL.lastIndexOf('/'));
                                        var protocolLessRootURL  = protocolLessPageURL.substring(0, protocolLessPageURL.lastIndexOf('/'));
                                        
					window.app = {
                                                protocolLessRoot: protocolLessRootURL + "/",
                                                root: rootURL + "/",
                                                url: pageURL + "/",
                                                api: rootURL + "/api/",
                                                files: rootURL + "/files/",
                                                pages: rootURL + "/files/web/js/apps/admin/",                                                
                                                router: new Router(),
						menu: new AdminMenu()
					};
                                        
                                        console.log("Application Root URL: " + window.app.root);
                                        console.log("Document URL: " + window.app.url);
                                        console.log("Document File Root URL: " + window.app.files);
                                        console.log("Document Page Root URL: " + window.app.pages);
                                        
                                        console.log("Document URL pathname: " + window.location.pathname);
                                        Backbone.history.start({ pushState: true, root: window.location.pathname });
				});
			});
		</script>
	</body>