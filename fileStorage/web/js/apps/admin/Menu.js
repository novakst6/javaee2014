var adminTopLevel = [
	{title: "Administrace", id: "admin-tasks"},
	{title: "Sklad", id: "storage"},
	{title: "Statistiky", id: "stats"}	
];

var adminSecLevel = [
	{title: "Sortiment", id: "items", parentID: "admin-tasks", path: "basic", view: "SortimentView"},
	{title: "Uživatelé", id: "users", parentID: "admin-tasks"},
	{title: "Terminály", id: "terminals", parentID: "admin-tasks"}
];

var Router = Backbone.Router.extend({
	
	// routes configuration
	routes: {
		'' : 'init',
		':top' : 'loadSecondMenu',
		':top/:sec' : 'loadPage',
		':top/:sec/:action' : 'loadAction'
	},
	
	// na začátku - top menu
	init: function(){
		console.log("Router: init");
		window.app.menu.render();
	},
	
	// update druhého menu po zvolení v prvním
	loadSecondMenu: function(top){
		console.log("Router: loadSecondMenu");
		window.app.menu.renderSecMenu(top);
	},
	
	// načtení stránky výběrem v prvním menu
	loadPage: function(top, sec){
		console.log("Router: loadPage("+top+", "+sec+")");
		window.app.menu.loadPage(sec);
	},
	
	// provedení akce stránky
	loadAction: function(top, sec, action){
		console.log("Router: loadAction");
		window.app.menu.loadPageAction(action);
	}
});

var AdminMenu = Backbone.View.extend({
	
	el: "#menuholder",
	
	topDiv: null,
	secDiv: null,
	
	actualTop: null, 
	actualSec: null,
	actualAction: null,
	
	pageView: null,
	
	events: {
		//"click a#regbtn": "onRegClick",
		//"click input#btnloginok": "onLoginClick"	
	},
	
	initialize: function(){
		console.log("Welcome to AdminMenu");
		this.setupLinkHandling();
	},
	
	init: function(){	
		this.render();
	},
	
	setupLinkHandling: function(){
		console.log("Setting up click handling");
	
		$(document).on("click", "a:not([data-bypass])", function(evt){
			console.log("Handling click");
			
			// srovnáme jestli odkaz začíná rootem naší aplikace
			var href = $(this).attr("href");
			var root = window.app.url;
			var hrefroot = href.slice(0, root.length);
			
			console.log("href: "+href);
			console.log("root: "+root);
			console.log("hrefroot: "+hrefroot);

			// když jo, tak přesměrujeme samy v backbone, jinak necháme dokončit defaultně
			if (hrefroot == root) {
				evt.preventDefault();
				
				// zjistíme relativní adresu
				var rel = href.slice(root.length);
				console.log("rel: "+rel);
				
				Backbone.history.navigate(rel, true);
			}
		});

	},
	
	render: function(){
		this.$el.empty();
		
		// holdery menu
		this.topDiv = $("<div id='topLevelMenuHolder'></div>");
		this.secDiv = $("<div id='secLevelMenuHolder'></div>");
		
		// append
		this.$el.append( this.topDiv );		
		this.$el.append( this.secDiv );	

		//		
		this.renderTopMenu();
	},
	
	// vykreslí menu první úrovně
	renderTopMenu: function(){
		this.topDiv.empty();
		this.topDiv.hide();
		
		// 
		for( key in adminTopLevel ){
			//this.topDiv.append("<a href> " + adminTopLevel[key].title + "</span>");
			
			this.topDiv.append(
				$('<a>',{
					text: adminTopLevel[key].title,
					title: adminTopLevel[key].title,
					href: window.app.url + adminTopLevel[key].id
				})
			);
		}
		
		// vyrenderujeme první kategorii, na kt. narazíme
		this.renderSecMenu(adminSecLevel[0].parentID);
		
		//
		this.topDiv.fadeIn();
	},
	
	// vykreslí menu 2. úrovně
	renderSecMenu: function(id){
		this.secDiv.empty();
		this.secDiv.hide();
		
		//
		this.actualTop = id;
		console.log("Menu 2nd level: "+id);
		
		// 
		for( key in adminSecLevel ){
			if( adminSecLevel[key].parentID == id ){
				this.secDiv.append(				
					$('<a>',{
						text: adminSecLevel[key].title,
						title: adminSecLevel[key].title,
						href: window.app.url + id + "/" + adminSecLevel[key].id
					})
				);
			}
		}
		
		//
		this.secDiv.fadeIn();
	},
	
	// načte stránku (view)
	loadPage: function(id){	

		//
		this.actualSec = id;
		
		//
		var obj = _.find(adminSecLevel, function(itm){ return (itm.id == id); });
		console.log("Menu loading page: " + obj.path + "/" + obj.view + ".js");
		
		//
		var thisObj = this;
		require([window.app.pages + obj.path + "/" + obj.view + ".js"], function(){ 
			thisObj.pageView = new window[obj.view];		
		});
		
	},
	
	loadAction: function(id){
		console.log("Menu loading action: " + id);
	},
	
	
	update: function(){
		
		// načteme ze serveru stav přihlášení
		this.model.fetch({
			success: function(){
				this.render();
			}
		});
	},
	
	
	// --- extrenal events handlers
	onRouterEvent: function(route, router){
	
		console.log("onRouterEvent");
		//console.log(route);
		//console.log(router);
	
		// update server info model
		this.update();
	},
	
	// --- UI event handlers
	onRegClick: function(){
		if(window.app['regForm'] == undefined){
			require(['index/view/RegisterUserView'], function(){
				window.app.regForm = new RegisterUserView();
			});
		}else{
			window.app.regForm.render();
		}
	},
	
	onLoginClick: function(evt){
		evt.preventDefault();
		this.model.set("userclick", "yes");
		this.update();
	}
	
});