var LogBoxModel = Backbone.Model.extend({
	url: 'api/loginGetUser.php',

	defaults: {
		"name": "",
		"level": "",
		"time":  "",
		"status": ""
		}
});

var LogBoxView = Backbone.View.extend({
	
	tagName: "div",
	id: "authbox",
	
	model: null,
	
	events: {
		//"click a#regbtn": "onRegClick",
		//"click input#btnloginok": "onLoginClick"	
	},
	
	initialize: function() {	
		this.model = new LogBoxModel();
		this.model.on("sync", this.render, this);
		$("#mainview").append(this.$el);
		this.model.fetch();
		this.setPosition("right");
	},
	
	render: function(){
		this.$el.empty();
		
		// udělej box
		this.$el.append(
			"Uživatel: "+this.model.get("name")+"<br /> \
			Status: "+this.model.get("status")+"<br /> \
			Přihlášen: "+this.model.get("time")+"<br /> \
			[ <a style='display:inline;margin:0px;' href='api/logoff.php'>Odhlásit</a> ]<br /> \
			"
		);		

		//
		this.$el.fadeIn();
	},
	
	setPosition: function(pos){
	
		if( pos == "right" ){
			this.$el.css("background-color", "#fefefe");
			this.$el.css("right", "-290px");
		}else{
			this.$el.css("background-color", "#f3f3f3");
			this.$el.css("right", "20px");
		}
	
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