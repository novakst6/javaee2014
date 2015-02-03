var FrontApp = Backbone.View.extend({
	
	el: "#headview",
	
	items: null,
	
	events: {
		//"click a#regbtn": "onRegClick",
		//"click input#btnloginok": "onLoginClick"	
	},
	
	initialize: function(){
		console.log("Welcome to FrontApp");
		
		this.requireViews();
		
		this.render();
	},
	
	render: function(){
		
		//
		$("#favicon").remove();
		$('<link id="favicon" type="image/x-icon" rel="shortcut icon" href="files/web/css/fav1.ico?v=15" />').appendTo('head');
	
		//
		this.$el.empty();
		
		//
		this.$el.empty();
		this.$el.append("<h1>Vítejte</h1>");
		this.$el.fadeIn().css("display","inline-block");;
		
		//
		
	},
	
	requireViews: function(){
		
		var thisObject = this;
		require([window.app.pages + "ItemView" + ".js"], function(){ 
			
			//
			thisObject.items = new ItemView;	
		});
	}	

	
});