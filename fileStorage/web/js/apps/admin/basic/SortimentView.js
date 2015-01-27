var SortimentView = Backbone.View.extend({

	el: "#mainview",

	initialize: function(){
		this.render();
	},
	
	render: function(){
		this.$el.empty();
		
		this.$el.append($("<h1>DUDE!</h1>"));
	}
	
});