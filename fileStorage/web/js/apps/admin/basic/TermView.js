var TermModel = Backbone.Model.extend({
	urlRoot: window.app.api + 'term',
	defaults: {
		"id": null,
		"name": "",
		"type": null	
	},
	name: "Terminál",
	fields: {
		"name": {title: "Název", type: "string"},
		"type": {title: "Typ terminálu", type: "select", collection: "termTypes"}
	}
});

var TermCollection = Backbone.Collection.extend({
	url: window.app.api + 'term',
    model: TermModel
});

var TermView = Backbone.View.extend({

	name: "Terminály",
	fieldPre: 		"term_form_",
	fieldHashPre: 	"#term_form_",
	
	termTypes: null,
	
	model: null, 
	modelClass: "TermModel",
	collection: null,
	
	dialog: null,
	form: null,

	initialize: function(){
		this.collection = new TermCollection();
		this.collection.on("sync", this.render, this);
	},
	
	destroy: function(){
		this.dialog.dialog('destroy').remove();
		this.remove();
	},
	
	setTypesAndRender: function(termTypeView){
		this.termTypes = termTypeView.collection;
		
		// kill dialog
		if(this.dialog){
			this.dialog.dialog('destroy').remove();
		}
		
		// make new
		this.model = new window[this.modelClass];
		this.createDialog();
		
		//
		this.collection.fetch();
	},
	
	render: function(){
		console.log("TermView.render()");
		
		this.$el.empty();		
		this.$el.append("<h1>" + this.name + "</h1>");		
		
		// přidat
		var thisObject = this;
		this.$el.append(
			$("<button>Přidat položku</button>").button({
				icons: {
					primary: "ui-icon-plus"
				}
			}).click(function(){
				thisObject.editItem(null);
			})
		);
		
		// seznam
		var holder = $("<div />", {class: "listHolder"});
		thisObject.$el.append(holder);	
		this.collection.each(function(item){
			
			var div = $("<div />", {class: "listItem"});
			
			var iddiv = $("<div />", {class: "listItemId"});
			iddiv.append(item.get("id"));
			
			var txt = $("<div />", {class: "listItemName"});
			txt.append(item.get("name"));
			
			var txt = $("<div />", {class: "listItemName"});
			txt.append(item.get("name"));
			
			var editButton = $('<a>', { text: "Upravit", title: "Upravit", href: "#", class: "listEdit" }).click(function(){
				thisObject.editItem(item.get("id"));
			});
			var deleteButton = $('<a>', { text: "Smazat", title: "Smazat", href: "#", class: "listDelete" }).click(function(){
				thisObject.deleteItem(item.get("id"));
			});
			
			//
			div.append(iddiv);
			div.append(txt);
			div.append(editButton);
			div.append(deleteButton);
			
			//
			holder.append(div);	
		});
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
		this.form = $("<div />", {class: "formholder", title: this.model.name});
		var html = "";
		
		// otevírací tagy
		html += "<form class='entityForm'><fieldset>\n";
			
		// položky
		var title;
		var type;
		for( id in this.model.fields ){
			title = this.model.fields[id].title;
			type = this.model.fields[id].type;
			html += "<label for='"+this.fieldPre+id+"'>"+title+"</label>";
			
			if(type != "select"){
				html += "<input type='"+type
							+"' name='"+this.fieldPre+id
							+"' id='"+this.fieldPre+id
							+"' value='' class='text ui-widget-content ui-corner-all'>";
				continue;
			}
			
			// select
			var col = this[this.model.fields[id].collection];			
			html += "<select name='"+this.fieldPre+id
						+"' id='"+this.fieldPre+id
						+"' class='text ui-widget-content ui-corner-all'>";
						
			col.each(function(item){
				html += "<option value='"+item.get("id")+"'>"+item.get("name")+"</option>";			
			});
			html += "</select>";
		}			
			
		// zavírací tagy
		//html += "<input type='submit' tabindex='-1'>\n";
		html += "</fieldset></form>\n";
			
		this.form.append(html);
		return this.form;
	},
	
	deleteItem: function(id){
		console.log("Delete item: " + id);
		
		this.model = this.collection.get(id);
		console.log(this.model);
		
		thisObject = this;
		this.model.destroy({success: function(model, response) {
			thisObject.collection.fetch();
		}});
	},
	
	editItem: function(id){
		console.log("Edit item: " + id);
		this.copyDataAndEdit(id);
	},
	
	// data do formuláře
	copyDataAndEdit: function(mid){
		
		// načteme z kolekce model nebo uděláme nový
		if( mid == null){
			this.model = new window[this.modelClass]; 
		}else{
			this.model = this.collection.get(mid);
		}
		
		// zkopíruje data
		for( id in this.model.fields ){
		
			// podle typu
			switch(this.model.fields[id].type) {
				case "number":
					$(this.fieldHashPre+id).val(
						this.model.get(id)
					);
					console.log("Field: " + id 
						+ " Value: " + $(this.fieldHashPre+id).val()
						+ " Model: " + this.model.get(id)
					);
					break;
				case "checkbox":
					console.log(this.fieldHashPre+id);
					$(this.fieldHashPre+id)[0].checked = this.model.get(id);
					console.log("Field: " + id 
						+ " Value: " + $(this.fieldHashPre+id)[0].checked
						+ " Model: " + this.model.get(id)
					);
					break;
				case "select":
					if(this.model.get(id) != null){
						$(this.fieldHashPre+id).val(
							this.model.get(id).id
						);
						console.log("Field: " + id 
							+ " Value: " + $(this.fieldHashPre+id).val()
							+ " Model: " + this.model.get(id).id
						);
					}
					break;
				default:
					$(this.fieldHashPre+id).val(
						this.model.get(id)
					);
					console.log("Field: " + id 
						+ " Value: " + $(this.fieldHashPre+id).val()
						+ " Model: " + this.model.get(id)
					);
			} 
		}
		
		// otevře dialog
		this.dialog.dialog("open");
	},
	
	// data z formuláře
	copyAndSendData: function(){
	
		// zkopíruje data
		for( id in this.model.fields ){
		
			// podle typu
			switch(this.model.fields[id].type) {
				case "number":
					this.model.set(id, parseFloat($(this.fieldHashPre+id).val()));
					console.log("Field: "+id+" Value: "+$(this.fieldHashPre+id).val());
					break;
				case "checkbox":
					this.model.set(id, $(this.fieldHashPre+id)[0].checked);
					console.log("Field: "+id+" Value: "+$(this.fieldHashPre+id)[0].checked);
					break;
				case "select":
					var selID = parseFloat($(this.fieldHashPre+id).val());
					this.model.set(id, {id: selID});
					console.log("Field: "+id+" Value: "+$(this.fieldHashPre+id).val());
					break;
				default:
					this.model.set(id, $(this.fieldHashPre+id).val());
					console.log("Field: "+id+" Value: "+$(this.fieldHashPre+id).val());
			} 
		}
		
		// log
		console.log("Values read from form");
		console.log(this.model);
		
		// uloží
		var thisObject = this
		this.model.save(null, {
			success: function(model, response, options){
				console.log("Model save OK");
				thisObject.dialog.dialog("close");
			},
			error: function(model, response, options){
				console.log("Model save ERROR");
			}
		});	
		
		//
		this.collection.fetch();
	}
	
});