var SortimentModel = Backbone.Model.extend({
	url: window.app.api + 'item',
	defaults: {
		"id": null,
		"name": "",
		"shortText": "",
		"longText": ""		
	},
	name: "Položka sortimentu",
	names: {
		"name": "Název",
		"shortText": "Krátký text",
		"longText": "Dlouhý text"
	}
});

var SortimentCollection = Backbone.Collection.extend({
	url: window.app.api + 'item',
    model: SortimentModel
});

var SortimentView = Backbone.View.extend({

	el: "#mainview",
	
	model: null, 
	dialog: null,
	form: null,

	initialize: function(){
	
		this.model = new SortimentModel();
		
		this.createDialog();
	
		this.render();
	},
	
	render: function(){
		this.$el.empty();
		
		var thisObject = this;
		this.$el.append(
			$("<button>Přidat položku</button>").button({
				icons: {
					primary: "ui-icon-plus"
				}
			}).click(function(){
				thisObject.dialog.dialog("open");
			})
		);
		
		this.$el.append($("<h1>DUDE!</h1>"));
	},
	
	createDialog: function(){
		var thisObject = this;
		this.dialog = this.createHtml().dialog({
			autoOpen: false,
			//height: 450,
			//width: 350,
			modal: true,
			show: {
				effect: "fade",
				duration: 400
			},
			hide: {
				effect: "fade",
				duration: 400
			},
			buttons: {
				"Uložit": function(){
					//thisObject.showErrorDialog("Problém s .....");
					thisObject.copyAndSendData();
				},
				"Storno": function() {
					thisObject.dialog.dialog("close");
				}
			},
			close: function() {
				//form[ 0 ].reset();
				//allFields.removeClass( "ui-state-error" );
			}
		});
	},
	
	createHtml: function(){
		this.form = $("<div />", {id: "formholder", title: this.model.name});
		var html = "";
		
		// otevírací tagy
		html += "<form class='entityForm'><fieldset>\n";
			
		// položky
		var title;
		for( id in this.model.names ){
			title = this.model.names[id];
			html += "<label for='"+id+"'>"+title+"</label>";
			html += "<input type='text' name='"+id+"' id='ffield_"+id+"' value='' class='text ui-widget-content ui-corner-all'>";
		}
			
			
		// zavírací tagy
		//html += "<input type='submit' tabindex='-1'>\n";
		html += "</fieldset></form>\n";
			
		this.form.append(html);
		return this.form;
	},
	
	copyAndSendData: function(){
	
		this.model
	
		for( id in this.model.names ){
			this.model.set(id, $("#ffield_"+id).val());
		}
		
		console.log("Values read from form");
		console.log(this.model);
		
		this.model.save(null, {
			success: function(model, response, options){
				console.log("Model save OK");
			},
			error: function(model, response, options){
				console.log("Model save ERROR");
			}
		});	
	}
	
});