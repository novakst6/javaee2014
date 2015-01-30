var TermPage = Backbone.View.extend({

	className: "pageholder",
	
	terms: null,
	termTypes: null,
	
	initialize: function(){	
		
		//
		var thisObject = this;
		require([
			window.app.pages + "basic/TermView.js",
			window.app.pages + "basic/TermTypeView.js"
		], function(){ 
		
			//			
			thisObject.terms = new TermView();		
			thisObject.termTypes = new TermTypeView({terms: thisObject.terms});		
			
			//
			thisObject.render();
		});	
		
	},
	
	destroy: function(){
		this.terms.destroy();
		this.termTypes.destroy();
		this.remove();
	},
	
	render: function(){
		this.$el.empty();
		
		this.$el.append(this.terms.$el);
		this.$el.append($("<div />", {class: "pageSeparator"}));
		this.$el.append(this.termTypes.$el);
	}
	
});