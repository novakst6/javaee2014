var ItemModel = Backbone.Model.extend({
	urlRoot: window.app.api + 'item',
	defaults: {
		"id": null,
		"name": "",
		"shortText": "",
		"longText": "",
		"category": null,
		"price": 0,
		"terminalType": null,
		"image": null
	},
	name: "Položka sortimentu",
	fields: {
		"name": {title: "Název", type: "string"},
		"shortText": {title: "Krátký text", type: "string"},
		"longText": {title: "Dlouhý text", type: "string", extra: "long"},
		"terminalType": {title: "Typ terminálu", type: "select", collection: "termTypes"},
		"price": {title: "Cena v Kč", type: "number"}
	}
});

var ItemCollection = Backbone.Collection.extend({
	url: window.app.api + 'item',
    model: ItemModel
});

var TermTypeModel = Backbone.Model.extend({
	urlRoot: window.app.api + 'term-type',
	defaults: {
		"id": null,
		"name": "",
		"screenType": 0,
		"isStacionary": false,		
		"isVendingPost": false
	}
});

var TermTypeCollection = Backbone.Collection.extend({
	url: window.app.api + 'term-type',
    model: TermTypeModel
});

var CatModel = Backbone.Model.extend({
	urlRoot: window.app.api + 'item-cat',
	defaults: {
		"id": null,
		"name": "",
		"memo": "",
		"parent": null,
		"image": null
	}
});

var CatCollection = Backbone.Collection.extend({
	url: window.app.api + 'item-cat',
    model: CatModel
});

var ItemView = Backbone.View.extend({

	name: "Sortiment",
	fieldPre: 		"item_form_",
	fieldHashPre: 	"#item_form_",
	
	termTypes: null,
	
	model: null, 
	modelClass: "ItemModel",
	collection: null,
	collectionClass: "ItemCollection",
	
	dialog: null,
	form: null,
	
	nestable: null,
	imageDialog: null,

	initialize: function(){
	
		// nestable
		this.nestable = new NestableView();
		
		// výběr obrázku
		this.imageDialog = new ImageDialogView();
		
		// model
		this.model = new window[this.modelClass];
		
		// typy terminálů
		this.termTypes = new TermTypeCollection;
		this.termTypes.on("sync", this.createDialog, this); // <- <- createDialog() zde
		this.termTypes.fetch();
		
		// kolekce
		this.collection = new window[this.collectionClass];
		this.collection.on("sync", this.render, this);
		this.collection.fetch();
	},
	
	destroy: function(){
		this.dialog.dialog('destroy').remove();
		this.nestable.remove();
		this.imageDialog.dialog.dialog('destroy').remove();
		this.imageDialog.remove();
		this.remove();
	},
	
	render: function(){
		
		// title
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
		var holder = $("<div />", {class: "listHolder haveImagePickerButton"});
		thisObject.$el.append(holder);	
		this.collection.each(function(item){
			
			var div = $("<div />", {class: "listItem"});
			
			var iddiv = $("<div />", {class: "listItemId"});
			iddiv.append(item.get("id"));
			
			var txt = $("<div />", {class: "listItemName"});
			txt.append(item.get("name"));
			
			var txt = $("<div />", {class: "listItemName"});
			txt.append(item.get("name"));
			
			
			var imageButton = $('<a>', { text: "Změnit obrázek", title: "Upravit", href: "#", class: "listPickImage" }).click(function(){
				thisObject.imageDialog.open(item);				
			});
			var editButton = $('<a>', { text: "Upravit", title: "Upravit", href: "#", class: "listEdit" }).click(function(){
				thisObject.editItem(item.get("id"));
			});
			var deleteButton = $('<a>', { text: "Smazat", title: "Smazat", href: "#", class: "listDelete" }).click(function(){
				thisObject.deleteItem(item.get("id"));
			});
			
			//
			div.append(iddiv);
			div.append(txt);
			div.append(imageButton);
			div.append(editButton);
			div.append(deleteButton);
			
			//
			holder.append(div);	
		});
		
		// nestable		
		this.$el.append(this.nestable.$el);
		this.nestable.renderCollection(this.collection);
		
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
			
			// textarea
			if(this.model.fields[id].extra && this.model.fields[id].extra == "long"){
				html += "<textarea "
							+" name='"+this.fieldPre+id
							+"' id='"+this.fieldPre+id
							+"' value='' class='text ui-widget-content ui-corner-all'></textarea>";
				continue;
			}
			
			// ne-select
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
				thisObject.collection.fetch();
			},
			error: function(model, response, options){
				console.log("Model save ERROR");
			}
		});	
	}
	
});

var NestableView = Backbone.View.extend({
	
	name: "Zařazení do kategorií",
	id: "nestableView",
	
	cats: null,
	catsClass: "CatCollection",
	
	collection: null,	
	
	initialize: function(){
		this.cats = new window[this.catsClass];
		this.cats.on("sync", this.render, this);
		//this.cats.fetch();
	},
	
	renderCollection: function(col){
		this.collection = col;
		this.cats.fetch();	// <- render() zde
	},
	
	render: function(){
	
		// title
		this.$el.empty();
		this.$el.append($("<div />", {class: "pageSeparator"}));
		this.$el.append("<h1>" + this.name + "</h1>");
		
		var holder = $("<div />", {class: "nestableHolder"});
		this.$el.append(holder);
		
		holder.append(this.nestableJSON2HTML(this.collection.toJSON(), this.cats.toJSON()));
		
		// nestable
		console.log("Applying nestable");
		console.log($('.dd'));
		$('.dd').nestable({});
		$('.dd').nestable('collapseAll');
		
		/*this.$el.append(
			$("<button>Debug</button>").button({
				icons: {
					primary: "ui-icon-wrench"
				}
			}).click(function(){
				$('.dd').nestable({});
				$('.dd').nestable('collapseAll');
			})
		);*/
		
		this.$el.append(
			$("<button>Rozbalit vše</button>").button({
				icons: {
					primary: "ui-icon-plus"
				}
			}).click(function(){
				$('.dd').nestable('expandAll');
			})
		);
		
		this.$el.append(
			$("<button>Sbalit vše</button>").button({
				icons: {
					primary: "ui-icon-minus"
				}
			}).click(function(){
				$('.dd').nestable('collapseAll');
			})
		);
		
		var thisObject = this;
		this.$el.append(
			$("<button>Storno</button>").button({
				icons: {
					primary: "ui-icon-closethick"
				}
			}).click(function(){
				thisObject.render();
			})
		);
		
		this.$el.append(
			$("<button>Uložit</button>").button({
				icons: {
					primary: "ui-icon-check"
				}
			}).click(function(){
				thisObject.readAndSave();
			})
		);
	},
	
	readAndSave: function(){
		//console.log("Nestable SAVE, serialised:");
		this.processList($('.dd').nestable('serialize'), null);
		//this.collection.fetch();
	},
	
	processList: function(list, parent){
		//console.log("Nestable, serialised ");
		//console.log(list);
		//return;
		
		if(list == null){return;}
		if(list.length <= 0){return;}
		var thisObject = this;
		jQuery.each(list, function(index, item){
			thisObject.processItem(item, parent);
		});
	},
	
	processItem: function(obj, parent){
	
		//
		console.log("Nestable process item: ");
		
		// typ
		var type = "item";
		var objID = obj.id;
		console.log("Orig ID: "+objID);
		if((typeof objID) == "string"){
			if(obj.id.charAt(0) == "c"){
				type = "cat";
				objID = objID.substr(1);
			}
		}		
		console.log("Type: "+type+" ID: "+objID);
		if(parent == null){
			console.log("Parent ID: null");			
		}else {
			console.log("Parent ID: "+parent.id);
		}
		
		// model z collection
		var item = null;
		if(type == "item"){
			item =  this.collection.find(function(model){ return model.get('id') == obj.id; });
		}
		if(type == "cat"){
			item =  this.cats.find(function(model){ return model.get('id') == objID; });
		}
		
		// uložit změnu - item
		if(type == "item"){		
			if(item.get("category") == null){
				if(parent == null){
					// ok
				}else{
					item.set("category", {id: parent.id});
					console.log("New parent of "+item.get("id")+" is "+parent.id);
					item.save();
				}
			} else {
				if(parent == null){
					item.set("parent", null);
					console.log("New parent of "+item.get("id")+" is [null]");
					item.save();
				}else{
					if(item.get("category").id == parent.id){
						// ok
					} else {
						item.set("category", {id: parent.id});
						console.log("New parent of "+item.get("id")+" is "+parent.id);
						item.save();
					}
				}	
			}
		}

		// uložit změnu - cat
		if(type == "cat"){
			if(item.get("parent") == null){
				if(parent == null){
					// ok
				}else{
					item.set("parent", {id: parent.id});
					console.log("New parent of "+item.get("id")+" is "+parent.id);
					item.save();
				}
			} else {
				if(parent == null){
					item.set("parent", null);
					console.log("New parent of "+item.get("id")+" is [null]");
					item.save();
				}else{
					if(item.get("parent").id == parent.id){
						// ok
					} else {
						item.set("parent", {id: parent.id});
						console.log("New parent of "+item.get("id")+" is "+parent.id);
						item.save();
					}
				}	
			}
		}
		
		// zpracovat potomky
		if(type == "cat"){
			this.processList(obj.children, item);
		}
		if(type == "item"){
			this.processList(obj.children, parent);
		}
	},
	
	nestableGetHTML: function (obj){
		//console.log("obj: "+JSON.stringify(obj));
		if(obj == undefined){return "";}
		
		// úprava id
		var id = obj.id;
		if(obj.type == "cat"){
			id = "c"+obj.id;
		}

		var myhtml = '<li class="dd-item" data-id="'+id+'">';
		myhtml = myhtml + '<div class="dd-handle dd-handle-'+obj.type+'">'+obj.name+" ("+id+") "+'</div>';

		if(obj.children != undefined){
			myhtml = myhtml + '<ol class="dd-list">';
			thisObject = this;
			jQuery.each(obj.children, function(index, item){
				myhtml = myhtml + thisObject.nestableGetHTML(item);
			});
			myhtml = myhtml + '</ol>';
		}

		myhtml = myhtml + '</li>';	
		return myhtml;
	},

	nestableJSON2HTML: function(json, catsJson){
	
		//
		console.log("nestableJSON2HTML, json:");
		console.log(json);
		
		//
		console.log("nestableJSON2HTML, catsJson:");
		console.log(catsJson);
	
		// uloží do indexovaného listu
		var object_list = {};
		jQuery.each(json, function(index, item){
			item.type = "item";
			item.parent = item.category;
			object_list[item.id] = item;
		});
		jQuery.each(catsJson, function(index, item){
			item.type = "cat";
			object_list["c"+item.id] = item;
		});
		console.log("Nestable object list: ");
		console.log(object_list);
		//app.data.nestable.orig_list = object_list;
		
		// přidá objekty k nadřazeným, jako jejich potomky
		jQuery.each(object_list, function(index, item){
			if(item == undefined){return;}			
			//alert(JSON.stringify(item));	
			
			// cat
			if(item.type == "cat"){
				if(item.parent != null && item.parent != undefined){
					var id = "c"+item.parent.id;
					if(object_list[id].children == undefined){
						object_list[id].children = [];
					}
					var len = object_list[id].children.length;
					object_list[id].children[len] = item;
				}
				return;
			}

			// item
			if(item.category != null && item.category != undefined){
				var id = "c"+item.category.id;
				if(object_list[id].children == undefined){
					object_list[id].children = [];
				}
				var len = object_list[id].children.length;
				object_list[id].children[len] = item;
			}
		});
		
		// vyrobí html
		var myhtml = '<div class="dd" id="mainNestable">';
		myhtml =  myhtml + '<ol class="dd-list">';
		thisObject = this;
		jQuery.each(object_list, function(index, item){
			if(item == undefined){return;}
			if(item.parent != null){return;}
			myhtml = myhtml + thisObject.nestableGetHTML(item);
		});
		myhtml = myhtml + '</ol>';
		myhtml = myhtml + '</div>';
		
		console.log("Generated HTML:");
		console.log(myhtml);
		
		return myhtml;
	}

});

var ImageModel = Backbone.Model.extend({
	urlRoot: window.app.api + 'image',
	defaults: {
		"id": null,
		"name": "Nový obrázek",
		"small": null,
		"medium": null,
		"large": null,
		"original": null
	}
});

var ImageCollection = Backbone.Collection.extend({
	url: window.app.api + 'image',
    model: ImageModel
});

var ImageDialogView = Backbone.View.extend({
	
	name: "Výběr obrázku",
	
	collection: null,	
	
	dialog: null,
	
	model: null,
	image: null,
	
	initialize: function(){
	
		// kolekce
		this.collection = new ImageCollection;
		this.collection.on("sync", this.render, this);
		//this.collection.fetch();
		
		this.createDialog();
		
		
	},
	
	open: function(myModel){
		console.log("Image picker open with item: "+myModel.get("name"));
		
		this.model = myModel;
		this.collection.fetch();		
	},
	
	render: function(){
	
		// seznam
		var holder = $("#imagePickHolder");
		holder.empty();
		
		// smažeme vybraný obrázek
		this.image = null;
		
		var thisObject = this;
		this.collection.each(function(item){
		
			
			var div = $("<div />", {class: "imageItem"});
			
			var iddiv = $("<div />", {class: "listItemId"});
			iddiv.append("ID: "+item.get("id"));
			
			var txt = $("<div />", {class: "listItemName"});
			txt.append(item.get("name"));
			
			var img = $("<div />", {class: "imageItemImage"});
			img.css("background-image", "url('"+window.app.files + item.get("original").filePath+"')");
			
			//
			div.append(iddiv);
			div.append(txt);
			div.append(img);
			
			//
			holder.append(div);	
			
			// přidat dvi do modelu
			//item.set("div", div);
			
			// aktuální?
			if( thisObject.model.get("image") != null ){
				if( thisObject.model.get("image").id != null ){
					if( thisObject.model.get("image").id == item.get("id") ){
						div.addClass("imageSelectedNow");
					}
				}
			}
			
			// onCLick
			div.click(function(){
				console.log("Image clicked: " + item.get("name"));
				thisObject.image = item;
				$(".imageItem").removeClass("imageSelectedNow");
				$(this).addClass("imageSelectedNow");
			});
		});
	
		//
		this.dialog.dialog('option', 'title', this.name+": "+this.model.get("name"));
		this.dialog.dialog("open");
	},
	
	save: function(){
	
		// nic nevybráno
		if(this.image == null){return;}
		
		// vybranný stejný obrázek jako byl
		if(this.model.get("image") != null){
			if(this.model.get("image").id == this.image.get("id")){
				return;
			}		
		}
		
		// update
		this.model.set("image", {id: this.image.get("id")});
		this.model.save();
	},
	
	createDialog: function(){
		var thisObject = this;
		this.dialog = this.createHtml().dialog({
			autoOpen: false,
			height: 600,
			width: 964,
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
					thisObject.save();
					thisObject.dialog.dialog("close");
				},
				"Storno": function() {
					thisObject.dialog.dialog("close");
				}
			},
			close: function(){
				//
			}
		});
	},
	
	createHtml: function(){
		this.form = $("<div />", {id:"imagePickHolder", class: "formholder", title: this.name});
		var html = "";
		
			
		this.form.append(html);
		return this.form;
	},
	
});